package PreciseSchedule.Desenhistas;

import PreciseSchedule.ConexaoBanco;
import PreciseSchedule.Executavel;
import PreciseSchedule.InterfacesGraficas.Principal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;

public class DesenhaGraficoLinha extends JPanel
{
    //Dados do banco
    private final int idUsuario, idCategoria;
    private final int corR, corG, corB;
    //tamanho da caixa onde será desenhado o gráfico
    final int RECUO = 20;
    
    //Coordenadas para preencher o gráfico
    private int[] x, y;
    
    //variaveis dos métodos
    private double x1, x2, y1, y2, escalaX;
    private int largura, altura, contadorString, contadorImpo;
    private int contadorDia;
    private int tamanhoMes, totalImportancia;   
    
    private int[][] nEventosFinal;
    private boolean[] indiceOcorrencias;
    private int maior;
    
    float strAltura;
    String texto;
    float strLargula;
    
    double escalaY;
    float strX;
    
    
    private Polygon poligono;
    private Graphics2D gFinal;
    
    ConexaoBanco conecta;
    
    ResultSet resultado;
    
    /**Desenha o gráfico de uma categoria de relatório do mês*/
    public DesenhaGraficoLinha(int anoUsuario, int mesUsuario, int idUsuario, int idCategoria)
    {
        //seta os valores de tamanho
        setMinimumSize(new Dimension(800, 200));
        setPreferredSize(new Dimension(800, 200));
        setMaximumSize(new Dimension(800, 200));
        
        //constantes
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        
        //objeto do banco de dados
        conecta = new ConexaoBanco();
        
        atualiza(anoUsuario, mesUsuario);
        corR = conecta.getCor(ConexaoBanco.R, idCategoria);
        corG = conecta.getCor(ConexaoBanco.G, idCategoria);
        corB = conecta.getCor(ConexaoBanco.B, idCategoria);
        
        //vetor para preencher os 
        x = new int[tamanhoMes+2];
        y = new int[tamanhoMes+2];
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
        largura = getWidth() - 100;
        altura = getHeight();
        
        //Essas fórmula são finais
        x[0] = RECUO;
        y[0] = altura-RECUO;
        
        x[tamanhoMes+1] = largura - RECUO;
        y[tamanhoMes+1] = altura - RECUO;
        
        //Define os padrões para desenhar texto
        Font fonte = gFinal.getFont();
        FontRenderContext contextoFonte = gFinal.getFontRenderContext();
        LineMetrics lm = fonte.getLineMetrics("0", contextoFonte);
        
        strAltura = lm.getAscent() + lm.getDescent();
        texto = Executavel.getProp("Qtde") + " " + Executavel.getProp("C" + idCategoria) + " " + Executavel.getProp("Dia");
        strLargula = (float)fonte.getStringBounds(texto, contextoFonte).getWidth();
        strX = (largura - strLargula)/2;
        
        escalaX = (double)(largura - 2 * RECUO) / (tamanhoMes - 1);
        escalaY = (double)(altura - 2 * RECUO) / maior;
        
        //escreve o nome da categoria
        gFinal.drawString(texto, strX, (RECUO-lm.getDescent()));
        
        //desenha os dias
        for(contadorDia = 0; contadorDia < tamanhoMes; contadorDia += 1)
        {
            texto = String.valueOf(contadorDia + 1);
            float sy = altura - RECUO + (RECUO - strAltura)/2 + lm.getAscent();
            strLargula = (float)fonte.getStringBounds(texto, contextoFonte).getWidth();
            strX = (float) ((RECUO + contadorDia*escalaX)-(strLargula/2));
            gFinal.drawString(texto, strX, sy);
        }

        gFinal.setFont(new Font("Verdada", Font.PLAIN, 13));
        //desenha a quantidade de eventos
        for(contadorString = maior; contadorString >= 0; contadorString -= 1)
        {
            texto = String.valueOf(contadorString);
            
            strLargula = (float) fonte.getStringBounds(texto, contextoFonte).getWidth();
            strX = (RECUO - strLargula) / 2;
            
            gFinal.drawString(texto, strX, (float)((maior - contadorString) * escalaY + ((RECUO + strAltura)/2) + strAltura/2));
        }
        
        int ocorrencias = 0;
        for(contadorImpo = 0; contadorImpo < totalImportancia; contadorImpo += 1)
        {
            for(contadorDia = 0; contadorDia < tamanhoMes; contadorDia += 1)
            {
                x1 = RECUO + contadorDia * escalaX;
                y1 = altura - RECUO - escalaY * nEventosFinal[contadorDia][contadorImpo];

                x[contadorDia + 1] = (int) Math.round(x1);
                y[contadorDia + 1] = (int) Math.round(y1);
            }

            poligono = new Polygon(x, y, tamanhoMes+2);

            switch(contadorImpo)
            {
                case 0:
                    gFinal.setPaint(new Color(corR, corG, corB, 255).brighter().brighter());
                    break;
                case 1:
                    gFinal.setPaint(new Color(corR, corG, corB, 255).brighter());
                    break;
                case 2:
                    gFinal.setPaint(new Color(corR, corG, corB, 255));
                    break;
                case 3:
                    gFinal.setPaint(new Color(corR, corG, corB, 255).darker());
                    break;
                case 4:
                    gFinal.setPaint(new Color(corR, corG, corB, 255).darker().darker());
            }
            gFinal.fillPolygon(poligono);
            
            if(indiceOcorrencias[contadorImpo])
            {
                gFinal.fillRect((int) largura + RECUO / 2, ocorrencias * RECUO + 20, 13, 13);
                
                gFinal.setColor(gFinal.getColor().darker().darker());
                gFinal.drawString(Executavel.getProp("I" + (contadorImpo + 1)), largura + RECUO / 2 + 18, ocorrencias * RECUO + 20 + 13);
                
                ocorrencias += 1;
            }
        }
        
        gFinal.setFont(fonte);
                
        //faz a linha reta
        gFinal.setPaint(new Color(corR, corG, corB));

        for(contadorDia = 0; contadorDia < tamanhoMes - 1; contadorDia += 1)
        {
            x1 = RECUO + contadorDia * escalaX;
            y1 = altura - RECUO - escalaY * nEventosFinal[contadorDia][0];
            x2 = RECUO + (contadorDia+1) * escalaX;
            y2 = altura - RECUO - escalaY * nEventosFinal[contadorDia + 1][0];

            gFinal.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        
        //desenha as elipses de cada dia
        gFinal.setPaint(new Color(corR, corG, corB).darker().darker().darker());
        for(contadorDia = 0; contadorDia < tamanhoMes; contadorDia += 1)
        {
            double xx = RECUO + contadorDia * escalaX;
            double yy = altura - RECUO - escalaY * nEventosFinal[contadorDia][0];
            gFinal.fill(new Ellipse2D.Double(xx-2, yy-2, 4, 4));
        }
        
        gFinal.setPaint(Color.BLACK);
        //desenha a abcissa ( x )
        gFinal.draw(new Line2D.Double(RECUO, altura - RECUO, largura - RECUO, altura - RECUO));
        //desenha a ordenada( y )
        gFinal.draw(new Line2D.Double(RECUO, RECUO, RECUO, altura - RECUO));
    }
    
    
    public void atualiza(int anoUsuario, int mesUsuario)
    {
        tamanhoMes = Principal.tamanhoMes(anoUsuario, mesUsuario);
        totalImportancia = Executavel.getImportancia();
        //[dia][importancia]
        nEventosFinal = new int[tamanhoMes][totalImportancia];
        indiceOcorrencias = new boolean[totalImportancia];
        resultado = conecta.getEventosGraficoMes(anoUsuario, mesUsuario, tamanhoMes, idCategoria, idUsuario);
        
        try
        {
            while(resultado.next())
            {
                nEventosFinal[resultado.getInt("DAY(data_evento)") - 1][resultado.getInt("id_importancia") - 1] += 1;
            }
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        
        //verifica quais tem ocorrência
        for(contadorImpo = 0; contadorImpo < totalImportancia; contadorImpo += 1)
        {
            for(contadorDia = 0; contadorDia < tamanhoMes; contadorDia += 1)
            {
                if(nEventosFinal[contadorDia][contadorImpo] > 0)
                {
                    indiceOcorrencias[contadorImpo] = true;
                    break;
                }
            }
        }

        //atualiza o valor pro gráfico
        for(contadorDia = 0; contadorDia < tamanhoMes; contadorDia += 1)
        {
            for(contadorImpo = totalImportancia - 2; contadorImpo >= 0; contadorImpo -= 1)
            {
                nEventosFinal[contadorDia][contadorImpo] += nEventosFinal[contadorDia][contadorImpo + 1]; 
            }
        }
        
        //começa pelo menor valor possível
        maior = 0;
        for(contadorDia = 0; contadorDia < tamanhoMes; contadorDia += 1)
        {
            //se for maior que o maior, então esse é o novo maior
            if(nEventosFinal[contadorDia][0] > maior)
            {
                maior = nEventosFinal[contadorDia][0];
            }
        }
    }
}