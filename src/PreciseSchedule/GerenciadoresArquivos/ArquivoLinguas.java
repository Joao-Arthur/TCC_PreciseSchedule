package PreciseSchedule.GerenciadoresArquivos;

import PreciseSchedule.Executavel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ArquivoLinguas
{    
    //Propriedades do arquivo
    private final Properties propriedades;
    
    /**Lida com tudo referente ao arquivo de configurações*/
    public ArquivoLinguas()
    {
        //Não iniciar o arquivo de propriedades é NullPointerException
        propriedades = new Properties();
    }
    
    /** @return retorna um Properties com as propriedades do arquivo*/
    public Properties getPropriedades()
    {
        try
        {            
            propriedades.loadFromXML(this.getClass().getClassLoader().getResourceAsStream(Executavel.SRCLINGUAS[Integer.parseInt(new ArquivoConfiguracoes().getPropriedades().getProperty("lingua"))]));
            //propriedades.loadFromXML(this.getClass().getClassLoader().getResource(Executavel.SRCLINGUAS[Integer.parseInt(new ArquivoConfiguracoes().getPropriedades().getProperty("lingua"))]).openConnection().getInputStream());
        }
        //Quando não acha o arquivo, cria ele com os valores padrão
        catch(FileNotFoundException ex)
        {
            Executavel.acharArquivo(ex, "arquivos/portugues.properties");
        }
        catch (IOException ex)
        {
            Executavel.erroArquivo(ex);
        }
        
        return propriedades;
    }
}