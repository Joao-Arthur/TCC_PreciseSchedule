package PreciseSchedule;

import PreciseSchedule.GerenciadoresArquivos.ArquivoConfiguracoes;
import java.io.IOException;
import java.util.Properties;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;

public class ThreadTocarMusica extends Thread
{
    //objetos para tocar a música
    AudioFormat audioFormat;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    
    byte tempBuffer[] = new byte[10000];
    
    //boolen usado para parar a reprodução da música
    public boolean pararMusica = false;
    
    ArquivoConfiguracoes arquivo;
    Properties propriedades;
    
    public ThreadTocarMusica(AudioFormat audioFormat, AudioInputStream audioInputStream, SourceDataLine sourceDataLine)
    {
        this.audioFormat = audioFormat;
        this.audioInputStream = audioInputStream;
        this.sourceDataLine = sourceDataLine;
        
        arquivo = new ArquivoConfiguracoes();
        propriedades = arquivo.getPropriedades();
    }
    
    @Override
    public void run()
    {
        try
        {
            sourceDataLine.open(audioFormat);
            
            ((FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN)).setValue(Float.parseFloat(propriedades.getProperty("volume")));
            
            sourceDataLine.start();
            int leitura;
            
            //Continua o looping até retornar -1 (final) ou a variável de parar música for verdadeira (por conta do usuário)
            while(((leitura = audioInputStream.read(tempBuffer,0,tempBuffer.length)) != -1)&&(pararMusica == false))
            {
                //Escreve os dados para o buffer interno, que vai tocar a música
                if(leitura > 0)
                {
                    sourceDataLine.write(tempBuffer, 0, leitura);
                }
            }
            //Limpa o buffer
            sourceDataLine.drain();
            sourceDataLine.close();
        }
        catch (IOException | LineUnavailableException e)
        {
            System.exit(0);
        }
    }
    
    //mensagem de erro para o usuário
    public static void erro(java.lang.Exception ex)
    {
        JOptionPane.showMessageDialog(null, "<html><body><p style = \" width: 400px \">" + ex + "</p></body></html>", "Erro ao tocar ou parar a música!", JOptionPane.ERROR_MESSAGE);
    }
}