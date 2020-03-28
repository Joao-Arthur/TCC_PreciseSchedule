package PreciseSchedule.Desenhistas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

public class DesenhaCirculo extends JPanel
{
    private Graphics2D gFinal;
    private final int corR, corG, corB;
    
    /**Desenha uma elipse/círculo
     * @param r cor vermelha (0 a 255)
     * @param g cor verde (0 a 255)
     * @param b cor azul (0 a 255)*/
    public DesenhaCirculo(int r, int g, int b)
    {
        initComponents();
        
        this.corR = r;
        this.corG = g;
        this.corB = b;
    }
    
    //método que pinta o componente panel
    @Override
    protected void paintComponent(Graphics g)
    {
        //cria o objeto que desenha
        super.paintComponent(g);
        gFinal = (Graphics2D)g;
        //o tipo de renderização
        gFinal.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //cor usada para pintar
        gFinal.setPaint(new Color(corR, corG, corB, 200));
        
        //cria a elipse 
        gFinal.fill(new Ellipse2D.Double(0, 0, 30, 30));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}