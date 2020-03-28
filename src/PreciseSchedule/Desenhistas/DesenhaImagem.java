package PreciseSchedule.Desenhistas;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**Classe que desenha uma imagem na tela*/
public class DesenhaImagem extends JPanel
{
    //Objeto da imagem
    Image Iimagem;
    //largura que a imagem vai ser desenhada
    public int largura;
    
    //construtor que pega a imagem e define os parâmetros
    public DesenhaImagem(String local, int largura)
    {
        initComponents();
        
        //pega um imageicon e transforma em imagem
        Iimagem = new ImageIcon(this.getClass().getClassLoader().getResource(local)).getImage();
        
        //pega a variável de largura
        this.largura = largura;
    }
    
    /**Desenha a imagem na tela
     * @param g o objeto de gráfico*/
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //desenha com o tamanho proporcial
        g.drawImage(Iimagem, 0, 0, largura, (int) (Iimagem.getHeight(null) * (double) ( (double) largura / (double) Iimagem.getWidth(null) )), this);      
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