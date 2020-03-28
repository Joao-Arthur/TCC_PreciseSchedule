/**@author João Arthur Lothamer Fernandes*/
package PreciseSchedule;

import PreciseSchedule.GerenciadoresArquivos.ArquivoLinguas;
import PreciseSchedule.InterfacesGraficas.Inicial;
import PreciseSchedule.InterfacesGraficas.Principal;
import PreciseSchedule.InterfacesGraficas.Splash;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**cria o splash e a tela inicial*/
public class Executavel
{
    //string do local dos arquivos
    public static final String ICONE = "imagens/icone.png", LOGO = "imagens/logo.png", CCSA = "imagens/CCBYSA.png", CCNC = "imagens/CCBYNC.png", CONFIGURACOES = "arquivos/configuracoes.properties";
    public static final String[] SRCLINGUAS = new String[]{"arquivos/portugues.properties", "arquivos/english.properties"};
    
    private static int CATEGORIA, FREQUENCIA, IMPORTANCIA, OPCOESFECHAR;
    
    private static Properties propriedades = new ArquivoLinguas().getPropriedades();
    private static int contador;
    
    /**método principal que instancia as classe
     * @param args comando "embutido"*/
    public static void main(String[] args)
    {
        atualizaPropriedades();
        numeroOcorrencias();
        
        //tenta colocar o estilo do programa para o Nimbus
        try
        {
            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            erro(ex);
        }
        
        //cria a tela splash
        Splash splash = new Splash();
        //mostra ela por um tempo
        try
        {
            TimeUnit.SECONDS.sleep(5);
        }
        catch(InterruptedException ex)
        {
            erro(ex);
        }
        //tira o splash
        splash.dispose();
        //cria a tela inicial
        Inicial inicial = new Inicial();
        Principal principal = new Principal(1);
    }
    
    public static String getProp(String prop)
    {
        return propriedades.getProperty(prop);
    }
    
    /**TODO passar as mensagens de erro todas para essa classe*/
    
    /**Mostra uma mensagem de erro para o usuário
     * @param ex o objeto da excessão*/
    public static void erro(Exception ex)
    {
        JOptionPane.showMessageDialog(null, "<html><body><p style = \" width: 400px \">" + propriedades.getProperty("Erro") + ": " + ex + "</p></body></html>", propriedades.getProperty("ErroGeral") + "!", JOptionPane.ERROR_MESSAGE);
    }
    
    /**Mostra uma mensagem de erro do banco de Dados
     *@param ex exceção lançada pelo catch*/
    public static void erroBancoDeDados(SQLException ex)
    { 
        JOptionPane.showMessageDialog(null, "<html><body><p style = \" width: 400px \">" + propriedades.getProperty("Erro") + ": " + ex + "</p></body></html>", propriedades.getProperty("ErroBanco") + "!", JOptionPane.ERROR_MESSAGE);
    }
    
    /**Mostra uma mensagem de erro do banco de Dados
     *@param ex exceção lançada pelo catch*/
    static void erroBancoDeDados(MySQLNonTransientConnectionException ex)
    { 
        JOptionPane.showMessageDialog(null, "<html><body><p style = \" width: 400px \">" + propriedades.getProperty("ErroConectarBanco") + ": " + ex + "</p></body></html>", propriedades.getProperty("ErroBanco") + "!", JOptionPane.ERROR_MESSAGE);
    }
    
    /**Mostra uma mensagem de erro para o usuário
     * @param ex exceção lançada pelo catch*/
    public static void erroArquivo(Exception ex)
    {
        JOptionPane.showMessageDialog(null, "<html><body><p style = \" width: 400px \">" + propriedades.getProperty("Erro") + ": " + ex + "</p></body></html>", propriedades.getProperty("ErroConfig") + "!", JOptionPane.WARNING_MESSAGE);
    }
    
    /**Mostra uma mensagem de erro para o usuário
     * @param ex exceção lançada pelo catch*/
    public static void erroMusica(Exception ex)
    {
        JOptionPane.showMessageDialog(null, "<html><body><p style = \" width: 400px \">" + propriedades.getProperty("ErroMusica") + ": " + ex + "</p></body></html>", propriedades.getProperty("ErroConfig") + "!", JOptionPane.WARNING_MESSAGE);
    }
    
    /**Mostra uma mensagem de erro para o usuário
     * @param ex exceção lançada pelo catch
     * @param local URL do arquivo*/
    public static void acharArquivo(Exception ex, String local)
    {
        JOptionPane.showMessageDialog(null, "<html><body><p style = \" width: 200px \">" + Executavel.propriedades.getProperty("ErroArquivo") + ": " + local + " : " + ex + "</p></body></html>", "Erro ao procurar um arquivo!", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void atualizaPropriedades()
    {
        propriedades = new ArquivoLinguas().getPropriedades();
    }
    
    private static void numeroOcorrencias()
    {
        contador = 1;
        while(propriedades.containsKey("C" + contador))
        {
            contador += 1;
        }
        
        CATEGORIA = contador - 1;
        
        contador = 1;
        while(propriedades.containsKey("F" + contador))
        {
            contador += 1;
        }
        
        FREQUENCIA = contador - 1;
        
        contador = 1;
        while(propriedades.containsKey("I" + contador))
        {
            contador += 1;
        }
        
        IMPORTANCIA = contador - 1;
        
        contador = 1;
        while(propriedades.containsKey("OF" + contador))
        {
            contador += 1;
        }
        
        OPCOESFECHAR = contador - 1;
    }
    
    public static int getCategoria()
    {
        return CATEGORIA;
    }

    public static int getFrequencia()
    {
        return FREQUENCIA;
    }

    public static int getImportancia()
    {
        return IMPORTANCIA;
    }
    
    public static int getFechar()
    {
        return OPCOESFECHAR;
    }
}