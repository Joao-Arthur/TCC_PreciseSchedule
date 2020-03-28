package PreciseSchedule.Desenhistas;

import PreciseSchedule.ConexaoBanco;
import PreciseSchedule.Executavel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JPanel;

public class DesenhaGraficoBarra extends JPanel
{
    //Dados do banco
    private final int idUsuario, idCategoria;
    private final int corR, corG, corB;
    //tamanho da caixa onde será desenhado o gráfico
    private final int RECUO = 30;
    private final int LARGURAGRAFICO = 200;
    
    private int contador;
    
    private final int anoUsuario, mesUsuario, diaUsuario;

    private int altura;

    HashMap<String, Integer> mapaImportancia;
    HashMap<String, Integer> valorAbsoluto;

    private Graphics2D gFinal;
    
    ConexaoBanco conecta;
    
    ResultSet resultado;
    public DesenhaGraficoBarra(int anoUsuario, int mesUsuario, int diaUsuario, int idUsuario, int idCategoria)
    {
        //seta os valores de tamanho
        setMinimumSize(new Dimension(300, 200));
        setPreferredSize(new Dimension(300, 200));
        setMaximumSize(new Dimension(300,200));
        
        //constantes
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        
        this.anoUsuario = anoUsuario;
        this.mesUsuario = mesUsuario;
        this.diaUsuario = diaUsuario;
    
//        //objeto do banco de dados
        conecta = new ConexaoBanco();
        
        setValores();
        corR = conecta.getCor(ConexaoBanco.R, idCategoria);
        corG = conecta.getCor(ConexaoBanco.G, idCategoria);
        corB = conecta.getCor(ConexaoBanco.B, idCategoria);

    }
    
    //Desenha todos os gráficos
    @Override
    protected void paintComponent(Graphics g)
    {
        //cria o objeto que desenha
        super.paintComponent(g);
        gFinal = (Graphics2D)g;
        //o tipo de renderização
        gFinal.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //pega as dimensões
        altura = getHeight();

        //Define os padrões para desenhar texto
        Font fonte = gFinal.getFont();
        FontRenderContext contextoFonte = gFinal.getFontRenderContext();
        LineMetrics lm = fonte.getLineMetrics("0", contextoFonte);
        
        float strAltura = lm.getAscent() + lm.getDescent();
        String texto = Executavel.getProp("C" + idCategoria);
        float strLargula = (float)fonte.getStringBounds(texto, contextoFonte).getWidth();
        float strX = ((2 * RECUO + LARGURAGRAFICO) - strLargula)/2;
        
        //escreve o nome da categoria
        gFinal.setFont(new Font("Verdada", Font.PLAIN, 15));
        gFinal.drawString(texto, strX, (altura - RECUO / 2));
        
        int maior = mapaImportancia.get((String) mapaImportancia.keySet().toArray()[0]);
        
        //desenha a quantidade de eventos
        float escalaY2 = (altura - 2 * RECUO) / maior;
        for(int contadorString = maior; contadorString >= 0; contadorString -= 1)
        {
            String s = String.valueOf(contadorString);
            
            strLargula = (float) fonte.getStringBounds(s, contextoFonte).getWidth();
            strX = (RECUO - strLargula) / 2;
            gFinal.setFont(new Font("Verdada", Font.PLAIN, 12));
            gFinal.drawString(s, strX, (maior - contadorString) * escalaY2 + ( 2 * RECUO + strAltura / 2) / 2);
           
        }

        float escalaY = mapaImportancia.get((String) mapaImportancia.keySet().toArray()[0]);

        for(contador = 0; contador < mapaImportancia.size(); contador += 1)
        {
            switch((String) mapaImportancia.keySet().toArray()[contador])
            {
                case "1":
                    gFinal.setPaint(new Color(corR, corG, corB, 255).brighter().brighter());
                    break;
                case "2":
                    gFinal.setPaint(new Color(corR, corG, corB, 255).brighter());
                    break;
                case "3":
                    gFinal.setPaint(new Color(corR, corG, corB, 255));
                    break;
                case "4":
                    gFinal.setPaint(new Color(corR, corG, corB, 255).darker());
                    break;
                case "5":
                    gFinal.setPaint(new Color(corR, corG, corB, 255).darker().darker());
            }
            
            int escala = (int) ((mapaImportancia.get((String) mapaImportancia.keySet().toArray()[contador]) / escalaY) * (altura - 2 * RECUO));
            
            gFinal.fillRect(RECUO, (altura - RECUO) - escala, 200, escala);
            
            gFinal.setPaint(gFinal.getColor().darker().darker());
            
            int escala2 = (int) ((valorAbsoluto.get((String) valorAbsoluto.keySet().toArray()[contador]) / escalaY) * (altura - 2 * RECUO));
            
            float strX2 = strX + RECUO + LARGURAGRAFICO;
            
            String s = Executavel.getProp("I" + Integer.valueOf(String.valueOf(mapaImportancia.keySet().toArray()[contador])));
            
            gFinal.setFont(new Font("Verdada", Font.BOLD, 11));
            gFinal.drawString(s, strX2, ((altura - RECUO - escala) + (altura - RECUO - escala + escala2) + strAltura) / 2);
        }
    }

    public int setValores()
    {
        mapaImportancia = new HashMap<>();
        valorAbsoluto = new HashMap<>();
                
        int importancia, importancia2;
        int valOcorrencias;
        
        resultado = conecta.getImportanciaDia(idCategoria, anoUsuario, mesUsuario, diaUsuario, idUsuario);
        try
        {
            if(resultado.next())
            {
                importancia2 = importancia = resultado.getInt("importancia.id");
                
                valOcorrencias = conecta.getCountEvento(anoUsuario, mesUsuario, diaUsuario, idUsuario, idCategoria, importancia);
                mapaImportancia.put(String.valueOf(importancia), valOcorrencias);
                valorAbsoluto.put(String.valueOf(importancia), valOcorrencias);
                
                while(resultado.next())
                {
                    importancia = resultado.getInt("importancia.id");
                    
                    valOcorrencias = conecta.getCountEvento(anoUsuario, mesUsuario, diaUsuario, idUsuario, idCategoria, importancia);
                    mapaImportancia.put(String.valueOf(importancia), mapaImportancia.get(String.valueOf(importancia2)) + valOcorrencias);
                    valorAbsoluto.put(String.valueOf(importancia), valOcorrencias);
                    
                    importancia2 = importancia;
                }
            }
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        
        return mapaImportancia.size();
    }
}