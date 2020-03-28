/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreciseSchedule;

import java.io.IOException;
import java.net.InetAddress;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.NTPUDPClient; 
import org.apache.commons.net.ntp.TimeInfo;

public class RetornaData {

    private static final String LISTASERVIDORES[] = {"time-a-g.nist.gov","time-b-g.nist.gov","time-c-g.nist.gov","time-d-g.nist.gov",
                                            "time-a-wwv.nist.gov","time-b-wwv.nist.gov","time-c-wwv.nist.gov","time-d-wwv.nist.gov",
                                            "time-a-b.nist.gov","time-b-b.nist.gov","time-c-b.nist.gov","time-d-b.nist.gov",
                                            "utcnist.colorado.edu","utcnist2.colorado.edu"};
    private Date data = null;
    
    //contador de quantos servidores não puderam se conectar
    private int contServidor = 0;
    
    //objeto usado para formatar a data
    private Format formatador;
    
    /**O construtor cria as threads para pegar a data*/
    public RetornaData()
    {
        for(String servidor : LISTASERVIDORES)
        {
            if(data == null)
            {
                ManageServidor man = new ManageServidor(servidor);
                man.start();
            }
        }
        //a execução do código só deve prosseguir quando o valor da data for definido
        while(data == null)
        {
            //o while deveria funcionar vazio, mas não funciona
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(RetornaData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**Formata a variável de data*/
    private int formata(String formato)
    {
        formatador = new SimpleDateFormat(formato);
        return Integer.parseInt(formatador.format(data));
    }
    
    /**@return o ano*/
    public int getAno()
    {
        return formata("yyyy");
    }
    
    /**@return o mes*/
    public int getMes()
    {
        return formata("MM");
    }
    
    /**@return o dia*/
    public int getDia()
    {
        return formata("dd");
    }
    
    /**@return a hora*/
    public int getHora()
    {
        return formata("HH");
    }
    
    /**@return os minutos*/
    public int getMinuto()
    {
        return formata("mm");
    }
    
    /**@return os segundos*/
    public int getSegundo()
    {
        return formata("ss");
    }
    
    /**pega a data pela classe Calendar*/
    private void pegaCalendario()
    {
        Calendar calend = Calendar.getInstance();
        data = calend.getTime();
    }
    
    /**Lida com a conexão via internet*/
    private class ManageServidor extends Thread
    {
        private final String servidor;
        public ManageServidor(String servidor)
        {
            this.servidor = servidor;
        }
        @Override
        public void run()
        {
            try
            {
                //cliente da conexão
                NTPUDPClient timeClient = new NTPUDPClient();
                //define o tempo de request time do cliente
                timeClient.setDefaultTimeout(2000);
                //pega a data pelo cliente
                InetAddress inetAddress = InetAddress.getByName(servidor);
                TimeInfo timeInfo = timeClient.getTime(inetAddress);
                long returnTime = timeInfo.getReturnTime();
                
                Date time = new Date(returnTime);
                data = time;
            }
            //caso o host seja inválido ou excessa o tempo limite
            catch(java.net.UnknownHostException | java.net.SocketTimeoutException ex)
            {
                contServidor += 1;
                if(contServidor == LISTASERVIDORES.length - 1)
                {
                    pegaCalendario();
                }
            }
            catch (IOException ex)
            {
                Executavel.erro(ex);
            }
        }
    }
}