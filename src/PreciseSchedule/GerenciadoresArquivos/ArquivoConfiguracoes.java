package PreciseSchedule.GerenciadoresArquivos;

import PreciseSchedule.Executavel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class ArquivoConfiguracoes
{
    //Array das propriedades e valores-padrão disponíveis
    public final static String[] PROPRIEDADE = {"notifAgenda","notifSistema","tempoAntec","somAlarme"                           ,"volume", "lingua", "fechar"};
    public final static String[] VALORPADRAO = {"true"       ,"false"       ,"30"        ,"Everythings Gonna Be Forgotten Remix","0"     , "0"     , "1"}; 
    //Lista de músicas disponíveis
    public final static String[] LISTAMUSICA = {"Everythings Gonna Be Forgotten Remix", "Venus And The Moon Remix", "What Am I Going To Remix 1", "What Am I Going To Remix 2"};
    
    private int contador;
    
    //Propriedades do arquivo
    private Properties propriedades;
    
    /**Lida com tudo referente ao arquivo de configurações*/
    public ArquivoConfiguracoes()
    {
        //Não iniciar o arquivo de propriedades é NullPointerException
        propriedades = new Properties();
    }
    
    /** @return retorna um Properties com as propriedades do arquivo*/
    public Properties getPropriedades()
    {
        //Lê as propriedades, mas pode haver alguma propriedade faltando
        try
        {
            //propriedades.load(this.getClass().getClassLoader().getResourceAsStream(Executavel.CONFIGURACOES));

            propriedades.load(new FileInputStream(System.getProperty("user.dir") + "\\configuracoes.properties"));
            //propriedades.load(this.getClass().getClassLoader().getResourceAsStream(Executavel.CONFIGURACOES));
            propriedadeFaltante();
        }
        //Quando não acha o arquivo, cria ele com os valores padrão
        catch(FileNotFoundException ex)
        {
            for(contador = 0; contador < PROPRIEDADE.length; contador += 1)
            {
                propriedades.setProperty(PROPRIEDADE[contador], VALORPADRAO[contador]);
            }
            salvaArquivo();
        }
        catch (IOException ex)
        {
            Executavel.erroArquivo(ex);
        }
        
        return propriedades;
    }
    
    /**Restaura as propriedades padrão do arquivo
     * @return retorna um Properties com as propriedades do arquivo*/
    public Properties restauraPadrao()
    {
        for(contador = 0; contador < PROPRIEDADE.length; contador += 1)
        {
            propriedades.setProperty(PROPRIEDADE[contador], VALORPADRAO[contador]);
        }
        salvaArquivo();
        
        return propriedades;
    }
    
    /**Verifica se alguma propridade está faltando*/
    private void propriedadeFaltante()
    {
        //Pra saber se ao menos uma propriedade está faltando, precisa de um boolean
        boolean faltando = false;
        
        for(contador = 0; contador < PROPRIEDADE.length; contador += 1)
        {
            if(propriedades.getProperty(PROPRIEDADE[contador]) == null)
            {
                propriedades.setProperty(PROPRIEDADE[contador], VALORPADRAO[contador]);
                faltando = true;
            }
        }
        if(faltando)
        {
            salvaArquivo();
        }
    }
    
    /**Método para as classes externas salvarem os dados do usuário
     * @param propriedades as propriedades a sem salvas
     * @return se inseriu / não houve nenhuma exceção no rodar*/
    public boolean salvaArquivo(Properties propriedades)
    {
        try
        {
            this.propriedades = propriedades;
            
            //salva os dados para o arquivo de configuração
            

            this.propriedades.store(new FileOutputStream(System.getProperty("user.dir") + "\\configuracoes.properties"), "user settings");
            
//            try(FileWriter escritor = new FileWriter(new File(this.getClass().getClassLoader().getResource(Executavel.CONFIGURACOES))))
//            {
//                this.propriedades.store(escritor, "user settings");
//            }
            
            //Retorna true se não houver exceção
            return true;
        }
        catch(IOException ex)
        {
            Executavel.erroArquivo(ex);
        }
        
        return false;
    }
    
    /**Salva os dados do usuário internamente*/
    private void salvaArquivo()
    {
        try
        {
            this.propriedades.store(new FileOutputStream(System.getProperty("user.dir") + "\\configuracoes.properties"), "user settings");
            //salva os dados para o arquivo de configuração
//            try(FileWriter escritor = new FileWriter(new File(this.getClass().getClassLoader().getResource(Executavel.CONFIGURACOES).toURI())))
//            {
//                propriedades.store(escritor, "user settings");
//            }
        }
        catch(IOException ex)
        {
            Executavel.erroArquivo(ex);
        }
    }
}