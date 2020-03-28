package PreciseSchedule;
import PreciseSchedule.InterfacesGraficas.Evento;
import PreciseSchedule.InterfacesGraficas.Principal;
import PreciseSchedule.GerenciadoresArquivos.ArquivoConfiguracoes;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/*Lida com o evento agendado do dia*/
public class EventoAgendado
{
    ConexaoBanco conecta;
    
    //dados herdados do banco de dados
    String id,dataEvento,horaInicio, horaFim;
    int idFrequencia, idImportancia, idCategoria;
    Date usuario;
    //dados lidos do arquivo de configurações
    private boolean notifAgenda,notifSistema;
    private final int tempoAntec;
    private String somAlarme;
    //variáveis do "toca músicas"
    AudioFormat audioFormat;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    boolean stopPlayback = false;
    //construtor que vai tratar as datas e de fato agendar o evento do usuário
    Properties propriedades;
    
    Principal principal;
    
    String nome;
    public EventoAgendado(Principal principal, ResultSet resultado, int ano, int mes, int dia)
    {
        this.principal = principal;
        try
        {
            //cria a conexao
            conecta = new ConexaoBanco();
            //passa as variaveis
            this.id = resultado.getString("id");
            this.dataEvento = resultado.getString("data_evento");
            this.horaInicio = resultado.getString("hora_inicio");
            this.horaFim = resultado.getString("hora_fim");
            this.idFrequencia = Integer.parseInt(resultado.getString("id_frequencia"));
            this.idCategoria = Integer.parseInt(resultado.getString("id_categoria"));
            this.idImportancia = Integer.parseInt(resultado.getString("id_importancia"));
            this.nome = resultado.getString("nome");
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        //pega a data
        String stMes = String.valueOf(mes);
        String stDia = String.valueOf(dia);
        if(mes < 10)
        {
            stMes = "0" + mes;
        }
        if(dia < 10)
        {
            stDia = "0" + dia;
        }
        String data = ano + "-" + stMes + "-" + stDia;
        
        //concatena
        String juntaEvt = dataEvento + " " + horaInicio + ".000";
        String juntaAgr = data+" "+ String.valueOf(LocalTime.now());

        long restanteeEntreHoras = 0;
        
        try
        {
            //formater para Date
            SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
            usuario = formato.parse(juntaEvt.substring(11, juntaEvt.length()));
            Date agora = formato.parse(juntaAgr.substring(11, juntaEvt.length()));
            
            restanteeEntreHoras = (usuario.getTime() - agora.getTime())/1000;
        }
        catch(ParseException ex)
        {
            Executavel.erro(ex);
        }
        
        //formatter pra LocalDate
        DateTimeFormatter forma = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        
        LocalDate dataUsuario = LocalDate.parse(juntaEvt, forma);
        LocalDate dataLocalAtual = LocalDate.parse(juntaAgr,forma);
        
        //compara entre o inicio dos dias, mas e daí?
        long restanteEntreDias = ChronoUnit.SECONDS.between(dataLocalAtual.atStartOfDay(), dataUsuario.atStartOfDay());
        
        int total = (int) ((long)restanteEntreDias + (long) restanteeEntreHoras);
        
        //agenda o evento
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        ArquivoConfiguracoes arquivo = new ArquivoConfiguracoes();

        propriedades = arquivo.getPropriedades();
        tempoAntec = Integer.parseInt(propriedades.getProperty("tempoAntec"));
        
        //157788000 o máximo que vai ter de segundos possível no programa, equivalente a 5 anos
        if(total - (tempoAntec * 60) > 0)
        {
            scheduler.schedule((Runnable) new ClasseInterna(), total - (tempoAntec * 60), TimeUnit.SECONDS);
        }
        else
        {
            scheduler.schedule((Runnable) new ClasseInterna(), 0, TimeUnit.SECONDS);
        }
    }
    
    private class ClasseInterna extends Thread
    {
        @Override
        public void run()
        {
            notifAgenda = Boolean.parseBoolean(propriedades.getProperty("notifAgenda"));
            notifSistema = Boolean.parseBoolean(propriedades.getProperty("notifSistema"));
            somAlarme = propriedades.getProperty("somAlarme");
            
            int r, g, b;
            
            r = conecta.getCor(ConexaoBanco.R, idCategoria);
            g = conecta.getCor(ConexaoBanco.G, idCategoria);
            b = conecta.getCor(ConexaoBanco.B, idCategoria);
            
            //notificação interna, a música é tocada internamente pela classe
            if(notifAgenda)
            {
                Evento evento = new Evento(principal, nome, horaInicio, horaFim, idImportancia, somAlarme, idCategoria, r, g, b);
            }

            //cria a notificação no sistema
            if(notifSistema)
            {
                //obtem uma única instância do objeto SystemTray
                SystemTray bandeja = SystemTray.getSystemTray();

                //ícone da notificação
                Image icone = Toolkit.getDefaultToolkit().createImage(this.getClass().getClassLoader().getResource(Executavel.ICONE));
                TrayIcon notificacao = new TrayIcon(icone, "icone");
                notificacao.setImageAutoSize(true);
                notificacao.setToolTip("Notificacao de evento");

                //tenta adicionar o objeto de notificação à "bandeja" do sistema
                try
                {
                    bandeja.add(notificacao);
                    
                    notificacao.addActionListener((ActionEvent e) -> {
                        bandeja.remove(notificacao);
                    });
                }
                catch (AWTException ex)
                {
                    Executavel.erro(ex);
                }

                //mensagem de evento
                notificacao.displayMessage("Está na hora!", nome, TrayIcon.MessageType.INFO);
                
                System.out.println("diferente " + somAlarme);
                //toca a música selecionada
                if(!(somAlarme.equals("0")))
                {
                    System.out.println("é diferensso");
                    //File soundFile = new File("src/sons/" + somAlarme + ".wav");
                    try
                    {   
                        InputStream buff = new BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream("sons/" + somAlarme + ".wav"));
                        audioInputStream = AudioSystem.getAudioInputStream(buff);
                        audioFormat = audioInputStream.getFormat();
                        DataLine.Info dataLineInfo = new DataLine.Info( SourceDataLine.class,audioFormat );
                        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                        new ThreadTocarMusica(audioFormat, audioInputStream, sourceDataLine).start();
                    }
                    catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex)
                    {                        
                        Executavel.erroMusica(ex);
                    }
                }
            }            

            if(idFrequencia == 1)
            {
                conecta.setEventoInativo(id);
            }
            else
            {
                //vai dar update na data do evento para o seu próximo "ocorrimento
                conecta.setNovaData(id, idFrequencia);
            }

            principal.isNotDuplicata = false;
        }
    }
}