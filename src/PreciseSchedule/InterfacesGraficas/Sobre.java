package PreciseSchedule.InterfacesGraficas;

import PreciseSchedule.Desenhistas.DesenhaImagem;
import PreciseSchedule.Executavel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Sobre extends JFrame
{
    private final DesenhaImagem CCSA, CCNC;
    
    private final Principal principal;
    
    public Sobre(Principal principal)
    {
        CCSA = new DesenhaImagem(Executavel.CCSA, 200);
        CCNC = new DesenhaImagem(Executavel.CCNC, 200);
        
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        initComponents();
        setTitle(Executavel.getProp("Sobre") + " - Precise Schedule 1.0");
        setVisible(true);
        
        lP1.setText(Executavel.getProp("P1"));
        
        this.principal = principal;
        principal.atualizaHabilitado(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lP1 = new javax.swing.JLabel();
        lCCASA = new javax.swing.JLabel();
        lCCANC = new javax.swing.JLabel();
        pBYSA = CCSA;
        pBYNC = CCNC;
        lP2 = new javax.swing.JLabel();
        lP3 = new javax.swing.JLabel();
        lCCASA1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        lP1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lP1.setText("Todas as músicas usadas no programa estão listadas abaixo com as suas licenças");

        lCCASA.setText("Everythings Gonna Be Forgotten");

        lCCANC.setText("Venus And The Moon");

        javax.swing.GroupLayout pBYSALayout = new javax.swing.GroupLayout(pBYSA);
        pBYSA.setLayout(pBYSALayout);
        pBYSALayout.setHorizontalGroup(
            pBYSALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 213, Short.MAX_VALUE)
        );
        pBYSALayout.setVerticalGroup(
            pBYSALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pBYNCLayout = new javax.swing.GroupLayout(pBYNC);
        pBYNC.setLayout(pBYNCLayout);
        pBYNCLayout.setHorizontalGroup(
            pBYNCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 213, Short.MAX_VALUE)
        );
        pBYNCLayout.setVerticalGroup(
            pBYNCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lP2.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lP2.setText("Attribution-ShareAlike");

        lP3.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lP3.setText("Attribution-NonCommercial");

        lCCASA1.setText("What Am I Going To Do");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lCCANC, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pBYSA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pBYNC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lCCASA1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lCCASA, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lP2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lP3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lP1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lCCANC, lCCASA, lCCASA1, pBYNC, pBYSA});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lP1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lP2, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(pBYSA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lCCASA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lCCASA1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lP3, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(pBYNC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lCCANC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        principal.atualizaHabilitado(true);
    }//GEN-LAST:event_formWindowClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lCCANC;
    private javax.swing.JLabel lCCASA;
    private javax.swing.JLabel lCCASA1;
    private javax.swing.JLabel lP1;
    private javax.swing.JLabel lP2;
    private javax.swing.JLabel lP3;
    private javax.swing.JPanel pBYNC;
    private javax.swing.JPanel pBYSA;
    // End of variables declaration//GEN-END:variables
}