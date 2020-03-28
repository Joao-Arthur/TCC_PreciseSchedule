package PreciseSchedule.InterfacesGraficas;

import PreciseSchedule.Desenhistas.DesenhaImagem;
import PreciseSchedule.Executavel;
import PreciseSchedule.InterfacesGraficas.Cadastros.CadastroUsuario;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Inicial extends JFrame
{
    //objeto para desenhar a imagem
    DesenhaImagem desenhaLogo;
    
    /**tela inicial do programa*/
    public Inicial()
    {
        //instancia com a logo
        desenhaLogo = new DesenhaImagem(Executavel.LOGO, 400);

        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        initComponents();
        setVisible(true);
        
        lBemVindo.setText(Executavel.getProp("BemVindo"));
        BtLogin.setText(Executavel.getProp("Entrar"));
        BtCadUsu.setText(Executavel.getProp("Cadastrar"));
        BtSair.setText(Executavel.getProp("Sair"));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtLogin = new javax.swing.JButton();
        BtCadUsu = new javax.swing.JButton();
        lBemVindo = new javax.swing.JLabel();
        PImagem = desenhaLogo;
        BtSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("frameInicial"); // NOI18N
        setUndecorated(true);

        BtLogin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        BtLogin.setText("Entrar");
        BtLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtLoginActionPerformed(evt);
            }
        });

        BtCadUsu.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        BtCadUsu.setText("Cadastrar-se");
        BtCadUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCadUsuActionPerformed(evt);
            }
        });

        lBemVindo.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        lBemVindo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lBemVindo.setText("Bem-Vindo à");

        PImagem.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                PImagemComponentResized(evt);
            }
        });

        javax.swing.GroupLayout PImagemLayout = new javax.swing.GroupLayout(PImagem);
        PImagem.setLayout(PImagemLayout);
        PImagemLayout.setHorizontalGroup(
            PImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );
        PImagemLayout.setVerticalGroup(
            PImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        BtSair.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        BtSair.setText("Sair");
        BtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(PImagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtCadUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lBemVindo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtSair, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(lBemVindo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(PImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(BtLogin)
                .addGap(5, 5, 5)
                .addComponent(BtCadUsu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtSair)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**instancia uma nova tela de cadastro de login*/
    private void BtLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtLoginActionPerformed
        Login login = new Login();
        this.dispose();
    }//GEN-LAST:event_BtLoginActionPerformed

    /**instancia uma nova tela de cadastro de usuário*/
    private void BtCadUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCadUsuActionPerformed
        CadastroUsuario cadastro = new CadastroUsuario();
        this.dispose();
    }//GEN-LAST:event_BtCadUsuActionPerformed

    /**Ao redimensionar o painel atualiza o tamanho*/
    private void PImagemComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PImagemComponentResized
        desenhaLogo.largura = PImagem.getWidth();
        desenhaLogo.repaint();
    }//GEN-LAST:event_PImagemComponentResized

    /**Ao sair encerra a JVM*/
    private void BtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_BtSairActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCadUsu;
    private javax.swing.JButton BtLogin;
    private javax.swing.JButton BtSair;
    private javax.swing.JPanel PImagem;
    private javax.swing.JLabel lBemVindo;
    // End of variables declaration//GEN-END:variables
}