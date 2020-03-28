package PreciseSchedule.InterfacesGraficas;

import PreciseSchedule.Desenhistas.DesenhaImagem;
import PreciseSchedule.Executavel;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**primeira tela do programa que possui a logo do mesmo*/
public class Splash extends JFrame
{
    DesenhaImagem desenhaIcone, desenhaLogo;
    public Splash()
    {
        //objetos que desenham imagem
        desenhaIcone = new DesenhaImagem(Executavel.ICONE, 400);
        desenhaLogo = new DesenhaImagem(Executavel.LOGO, 400);

        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        setUndecorated(true);
        initComponents();
        setVisible(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PIcone = desenhaIcone;
        PLogo = desenhaLogo;

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new Color(255, 255, 255, 0));
        setName("frameSplash"); // NOI18N
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        PIcone.setOpaque(false);

        javax.swing.GroupLayout PIconeLayout = new javax.swing.GroupLayout(PIcone);
        PIcone.setLayout(PIconeLayout);
        PIconeLayout.setHorizontalGroup(
            PIconeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        PIconeLayout.setVerticalGroup(
            PIconeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 211, Short.MAX_VALUE)
        );

        PLogo.setOpaque(false);

        javax.swing.GroupLayout PLogoLayout = new javax.swing.GroupLayout(PLogo);
        PLogo.setLayout(PLogoLayout);
        PLogoLayout.setHorizontalGroup(
            PLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
        );
        PLogoLayout.setVerticalGroup(
            PLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(PIcone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(PIcone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**Ao redimensionar redesenha as imagens*/
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        desenhaIcone.largura = PIcone.getWidth();
        desenhaIcone.repaint();
        
        desenhaLogo.largura = PLogo.getWidth();
        desenhaLogo.repaint();
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PIcone;
    private javax.swing.JPanel PLogo;
    // End of variables declaration//GEN-END:variables
}