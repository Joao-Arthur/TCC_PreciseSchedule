package PreciseSchedule.InterfacesGraficas;

import PreciseSchedule.Executavel;
import PreciseSchedule.GerenciadoresArquivos.ArquivoConfiguracoes;
import PreciseSchedule.ThreadTocarMusica;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class Configuracoes extends JFrame
{
    //índice da língua usada 
    private final int lingua;
    //volume inicial, usado para desfazer a ação de mudar o volume
    private final int volume;
        
    private int contador;
    
    Principal principal;

    //implementa o comportamento do play e pause
    DemoMusica demoMusica;
    
    //objeto que lida com o 
    ArquivoConfiguracoes arquivo;
    
    //variáveis para leitura e gravação
    File configFile;
    FileReader reader;
    //propriedades dos valores do arquivo
    Properties propriedades;
    
    /**Cria uma tela de configurações
     * @param principal*/
    public Configuracoes(Principal principal)
    {
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        initComponents();
        setTitle(Executavel.getProp("Config") + " - Precise Schedule 1.0");
        setVisible(true);
        
        rbNotifAgenda.setText(Executavel.getProp("NotifProg"));
        rbNotifSistema.setText(Executavel.getProp("NotifSisOp"));
        lNotifAntec.setText(Executavel.getProp("Antecedencia"));
        lMinutos.setText(Executavel.getProp("Minutos"));
        lMusica.setText(Executavel.getProp("Musica"));
        lVolume.setText(Executavel.getProp("Volume"));
        lLingua.setText(Executavel.getProp("Lingua"));
        lFechar.setText(Executavel.getProp("Fechar"));
        btSalva.setText(Executavel.getProp("Salvar"));
        btCancela.setText(Executavel.getProp("Cancelar"));
        btRestaura.setText(Executavel.getProp("Restaurar"));
        
        this.principal = principal;
        //atualiza o estado da tabela
        principal.atualizaHabilitado(false);
       
        //valor 
        for(contador = 0; contador <= 60; contador += 1)
        {
            cbTempo.addItem(String.valueOf(contador));
        }
        
        cbMusicas.addItem(Executavel.getProp("Nenhum"));
        
        for(String musica : ArquivoConfiguracoes.LISTAMUSICA)
        {
            cbMusicas.addItem(musica);
        }
        
        for(contador = 1; contador <= Executavel.SRCLINGUAS.length; contador += 1)
        {
            cbLinguas.addItem(Executavel.getProp("L" + contador));
        }
        
        for(contador = 1; contador <= Executavel.getFechar(); contador += 1)
        {
            cbFechar.addItem(Executavel.getProp("OF" + contador));
        }

        arquivo = new ArquivoConfiguracoes();
        propriedades = arquivo.getPropriedades();
        
        //seta os check boxes pelo valor arquivado
        rbNotifAgenda.setSelected(Boolean.parseBoolean(propriedades.getProperty("notifAgenda")));
        rbNotifSistema.setSelected(Boolean.parseBoolean(propriedades.getProperty("notifSistema")));
        cbTempo.setSelectedItem(propriedades.getProperty("tempoAntec"));
        cbMusicas.setSelectedItem(propriedades.getProperty("somAlarme"));
        cbLinguas.setSelectedIndex(Integer.parseInt(propriedades.getProperty("lingua")));
        cbFechar.setSelectedIndex(Integer.parseInt(propriedades.getProperty("fechar")));
        sVolume.setValue(Integer.parseInt(propriedades.getProperty("volume")));
        //seta as variáveis que vão pegar o valor inicial
        volume = Integer.parseInt(propriedades.getProperty("volume"));
        lingua = Integer.parseInt(propriedades.getProperty("lingua"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btSalva = new javax.swing.JButton();
        btRestaura = new javax.swing.JButton();
        btCancela = new javax.swing.JButton();
        cbTempo = new javax.swing.JComboBox<>();
        cbMusicas = new javax.swing.JComboBox<>();
        lMinutos = new javax.swing.JLabel();
        rbNotifAgenda = new javax.swing.JRadioButton();
        rbNotifSistema = new javax.swing.JRadioButton();
        lNotifAntec = new javax.swing.JLabel();
        lMusica = new javax.swing.JLabel();
        BtTocaMusica = new javax.swing.JButton();
        lVolume = new javax.swing.JLabel();
        sVolume = new javax.swing.JSlider();
        lLingua = new javax.swing.JLabel();
        cbLinguas = new javax.swing.JComboBox<>();
        lFechar = new javax.swing.JLabel();
        cbFechar = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("frameConf"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        btSalva.setText("Salvar");
        btSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvaActionPerformed(evt);
            }
        });

        btRestaura.setText("Restaurar Padrão");
        btRestaura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRestauraActionPerformed(evt);
            }
        });

        btCancela.setText("Cancelar");
        btCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelaActionPerformed(evt);
            }
        });

        cbMusicas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMusicasActionPerformed(evt);
            }
        });

        lMinutos.setText("minutos");

        rbNotifAgenda.setText("Notificação no Programa");
        rbNotifAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNotifAgendaActionPerformed(evt);
            }
        });

        rbNotifSistema.setText("Notificação no Sistema Operacional");
        rbNotifSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNotifSistemaActionPerformed(evt);
            }
        });

        lNotifAntec.setText("Notificar com antecedencia de");
        lNotifAntec.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        lMusica.setText("Notificar com a música");
        lMusica.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        BtTocaMusica.setText("►");
        BtTocaMusica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtTocaMusicaActionPerformed(evt);
            }
        });

        lVolume.setText("Volume da música");
        lVolume.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        sVolume.setMaximum(6);
        sVolume.setMinimum(-80);
        sVolume.setValue(0);
        sVolume.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sVolumeMouseExited(evt);
            }
        });

        lLingua.setText("Língua do programa");
        lLingua.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        lFechar.setText("Opcoes Fechar");
        lFechar.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lLingua, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lMusica, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lNotifAntec))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cbMusicas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BtTocaMusica))
                                    .addComponent(sVolume, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cbTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lMinutos))
                                            .addComponent(cbLinguas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(20, 20, 20))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btCancela, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btRestaura, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbNotifAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbNotifSistema))
                        .addContainerGap(347, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rbNotifAgenda, rbNotifSistema});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lMusica, lNotifAntec, lVolume});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(rbNotifAgenda)
                .addGap(10, 10, 10)
                .addComponent(rbNotifSistema)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lMinutos)
                    .addComponent(lNotifAntec))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbMusicas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lMusica)
                    .addComponent(BtTocaMusica))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lVolume)
                    .addComponent(sVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lLingua)
                    .addComponent(cbLinguas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lFechar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btSalva)
                    .addComponent(btCancela)
                    .addComponent(btRestaura))
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    /*ativa a tela principal ao sair*/
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        principal.atualizaHabilitado(true);
    }//GEN-LAST:event_formWindowClosed
    
    /*salva os valores definidos pelo usuário*/
    private void btSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvaActionPerformed
        //pergunta para o usuário
        Object[] opcoes = {Executavel.getProp("Sim"),Executavel.getProp("Nao")};
        if(JOptionPane.showOptionDialog(this, Executavel.getProp("Certeza") + "?", Executavel.getProp("Mudancas"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]) == 0)
        {
            //seta as propriedades
            propriedades.setProperty("notifAgenda", String.valueOf(rbNotifAgenda.isSelected()));
            propriedades.setProperty("notifSistema", String.valueOf(rbNotifSistema.isSelected()));
            propriedades.setProperty("tempoAntec",(String) cbTempo.getSelectedItem());
            
            if(cbMusicas.getSelectedIndex() == 0)
            {
                propriedades.setProperty("somAlarme","0");
            }
            else
            {
                propriedades.setProperty("somAlarme",(String) cbMusicas.getSelectedItem());
            }
            
            
            propriedades.setProperty("lingua", String.valueOf(cbLinguas.getSelectedIndex()));
            propriedades.setProperty("volume", String.valueOf(sVolume.getValue()));
            propriedades.setProperty("fechar", String.valueOf(cbFechar.getSelectedIndex()));
            
            //se conseguir salvar sai da tela
            if(arquivo.salvaArquivo(propriedades))
            {
                JOptionPane.showMessageDialog(this, Executavel.getProp("Salvo") + "!", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
                
                if(lingua != cbLinguas.getSelectedIndex())
                {
                    Executavel.atualizaPropriedades();
                    int id = principal.getId();
                    Principal.NOMEMES = new String[]{Executavel.getProp("M1"),Executavel.getProp("M2"),Executavel.getProp("M3"),Executavel.getProp("M4"),Executavel.getProp("M5"),Executavel.getProp("M6"),Executavel.getProp("M7"),Executavel.getProp("M8"),Executavel.getProp("M9"),Executavel.getProp("M10"),Executavel.getProp("M11"),Executavel.getProp("M12")};
                    principal.dispose();
                    principal = new Principal(id);
                }
                principal.atualizaSair();
                this.dispose();
            }
        }
    }//GEN-LAST:event_btSalvaActionPerformed

    /**Se o usuário sair simplesmente cancela tudo*/
    private void btCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelaActionPerformed
        propriedades.setProperty("volume", String.valueOf(volume));
        arquivo.salvaArquivo(propriedades);
        
        this.dispose();
    }//GEN-LAST:event_btCancelaActionPerformed

    /**Restaura as configurações padrão*/
    private void btRestauraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRestauraActionPerformed
        //pergunta para o usuário
        Object[] opcoes = {Executavel.getProp("Sim"),Executavel.getProp("Nao")};
        if(JOptionPane.showOptionDialog(this,  Executavel.getProp("Certeza") + "?", Executavel.getProp("Restaurar"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]) == 0)
        {
            //lê as propriedades padrão
            propriedades = arquivo.restauraPadrao();
            
            //seta os campos com os valores lidos
            rbNotifAgenda.setSelected(Boolean.parseBoolean(propriedades.getProperty("notifAgenda")));
            rbNotifSistema.setSelected(Boolean.parseBoolean(propriedades.getProperty("notifSistema")));
            cbTempo.setSelectedItem(propriedades.getProperty("tempoAntec"));
            cbMusicas.setSelectedItem(propriedades.getProperty("somAlarme"));
            cbLinguas.setSelectedIndex(Integer.parseInt(propriedades.getProperty("lingua")));
            cbFechar.setSelectedIndex(Integer.parseInt(propriedades.getProperty("fechar")));
            sVolume.setValue(Integer.parseInt(propriedades.getProperty("volume")));
        }
    }//GEN-LAST:event_btRestauraActionPerformed

    /**Ao selecionar um tem que deselecionar outro*/
    private void rbNotifAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNotifAgendaActionPerformed
        if(rbNotifSistema.isSelected())
        {
            rbNotifSistema.setSelected(false);
        }
    }//GEN-LAST:event_rbNotifAgendaActionPerformed

    /**Ao selecionar um tem que deselecionar outro*/
    private void rbNotifSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNotifSistemaActionPerformed
        if(rbNotifAgenda.isSelected())
        {
            rbNotifAgenda.setSelected(false);
        }
    }//GEN-LAST:event_rbNotifSistemaActionPerformed
    
    /**Toca uma demo de alguns segundos da música, podendo o usuário parar*/
    private void BtTocaMusicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtTocaMusicaActionPerformed
        try
        {
            //se já está tocando para
            if(demoMusica.tocando)
            {
                demoMusica.tocaDemo.pararMusica = true;
                demoMusica.tocando = false;
                
                //seta o unidode de play
                BtTocaMusica.setText("\u25BA");
            }
            //se não, começa
            else
            {
                demoMusica = new DemoMusica();
                demoMusica.start();
                
                //seta o unidode de stop
                BtTocaMusica.setText("\u25A0");
            }
        }
        catch(java.lang.NullPointerException e)
        {
            demoMusica = new DemoMusica();
            demoMusica.start();
        }
    }//GEN-LAST:event_BtTocaMusicaActionPerformed

    /**Controla se deve permitir a execução ou não*/
    private void cbMusicasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMusicasActionPerformed
        if(cbMusicas.getSelectedIndex() == 0)
        {
            BtTocaMusica.setEnabled(false);
        }
        else
        {
            BtTocaMusica.setEnabled(true);
        }
    }//GEN-LAST:event_cbMusicasActionPerformed

    /**Salva o volume*/
    private void sVolumeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVolumeMouseExited
        propriedades.setProperty("volume", String.valueOf(sVolume.getValue()));
        arquivo.salvaArquivo(propriedades);
    }//GEN-LAST:event_sVolumeMouseExited
    
    /**Toca a música por alguns segundos*/
    private class DemoMusica extends Thread
    {
        ThreadTocarMusica tocaDemo;
        boolean tocando;
        @Override
        public void run()
        {
            //A música está tocando
            tocando = true;
            
            //código que toca a música
            //File soundFile = new File("src/sons/" + cbMusicas.getSelectedItem() + ".wav");
            try
            {
                AudioFormat audioFormat;
                AudioInputStream audioInputStream;
                SourceDataLine sourceDataLine;
                InputStream buff = new BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream("sons/" + cbMusicas.getSelectedItem() + ".wav"));
                audioInputStream = AudioSystem.getAudioInputStream(buff);
                audioFormat = audioInputStream.getFormat();
                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat);
                sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                tocaDemo = new ThreadTocarMusica(audioFormat, audioInputStream, sourceDataLine);
                tocaDemo.start();

                //inicia a thread que para a execução se o usuário não parar
                new ParaExecucao().start();
            }
            //mostra uma mensagem de erro para o usuário
            catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex)
            {
                ThreadTocarMusica.erro(ex);
            }
        }
        
        //thread que vai controlar a execucao da música e o label do botão caso o usuário não pare antes
        private class ParaExecucao extends Thread
        {
            @Override
            public void run()
            {
                //seta o unidode de stop
                BtTocaMusica.setText("\u25A0");
                
                //espera 3 segundos
                try
                {
                    TimeUnit.SECONDS.sleep(3);
                }
                catch(InterruptedException ex)
                {
                    Executavel.erro(ex);
                }
                
                //para a música
                tocaDemo.pararMusica = true;
            
                //seta o unidode de play
                BtTocaMusica.setText("\u25BA");
                
                tocando = false;
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtTocaMusica;
    private javax.swing.JButton btCancela;
    private javax.swing.JButton btRestaura;
    private javax.swing.JButton btSalva;
    private javax.swing.JComboBox<String> cbFechar;
    private javax.swing.JComboBox<String> cbLinguas;
    private javax.swing.JComboBox<String> cbMusicas;
    private javax.swing.JComboBox<String> cbTempo;
    private javax.swing.JLabel lFechar;
    private javax.swing.JLabel lLingua;
    private javax.swing.JLabel lMinutos;
    private javax.swing.JLabel lMusica;
    private javax.swing.JLabel lNotifAntec;
    private javax.swing.JLabel lVolume;
    private javax.swing.JRadioButton rbNotifAgenda;
    private javax.swing.JRadioButton rbNotifSistema;
    private javax.swing.JSlider sVolume;
    // End of variables declaration//GEN-END:variables
}