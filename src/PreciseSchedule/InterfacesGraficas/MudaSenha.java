package PreciseSchedule.InterfacesGraficas;

import PreciseSchedule.ConexaoBanco;
import PreciseSchedule.Executavel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

/**Classe que muda a senha do usuário*/
public class MudaSenha extends JFrame
{
    public MudaSenha()
    {
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        initComponents();
        setTitle(Executavel.getProp("MudarSenha") + " - Precise Schedule 1.0");
        setVisible(true);
        
        lNome.setText(Executavel.getProp("Nome"));
        lEmail.setText(Executavel.getProp("Email"));
        lLogin.setText(Executavel.getProp("Login"));
        btVerifica.setText(Executavel.getProp("Verificar"));
        btCancela.setText(Executavel.getProp("Cancelar"));
        
        //desativa o botão ao entrar
        btVerifica.setEnabled(false);
    }

    /**Retorna se ao menos um dos campos está vazio*/
    private boolean camposNaoVazios()
    {
        return (!((TfNome.getText().equals(""))||(TfEmail.getText().equals(""))||(TfLogin.getText().equals(""))));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TfNome = new javax.swing.JTextField();
        TfEmail = new javax.swing.JTextField();
        lNome = new javax.swing.JLabel();
        lEmail = new javax.swing.JLabel();
        btVerifica = new javax.swing.JButton();
        lLogin = new javax.swing.JLabel();
        TfLogin = new javax.swing.JTextField();
        btCancela = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("frameMudaSenha"); // NOI18N
        setResizable(false);

        TfNome.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        TfNome.setForeground(new java.awt.Color(64, 64, 64));
        TfNome.setName(""); // NOI18N
        TfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TfNomeKeyReleased(evt);
            }
        });

        TfEmail.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        TfEmail.setForeground(new java.awt.Color(64, 64, 64));
        TfEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TfEmailKeyReleased(evt);
            }
        });

        lNome.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lNome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lNome.setText("Nome");

        lEmail.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lEmail.setText("E-mail");

        btVerifica.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btVerifica.setText("Verificar");
        btVerifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVerificaActionPerformed(evt);
            }
        });

        lLogin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lLogin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lLogin.setText("Login");

        TfLogin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        TfLogin.setForeground(new java.awt.Color(64, 64, 64));
        TfLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TfLoginKeyReleased(evt);
            }
        });

        btCancela.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btCancela.setText("Cancelar");
        btCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btCancela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TfEmail)
                    .addComponent(TfNome)
                    .addComponent(TfLogin, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btVerifica, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lNome)
                    .addComponent(TfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(TfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lEmail))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lLogin)
                    .addComponent(TfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(btVerifica)
                .addGap(5, 5, 5)
                .addComponent(btCancela)
                .addGap(7, 7, 7))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btVerificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVerificaActionPerformed
        //precisa reverificar aqui pois nos campos só verifica depois de soltar o botão
        if(camposNaoVazios())
        {
            //pega os valores
            String nome = TfNome.getText().trim().toUpperCase().replace("'", "''");
            if(nome.length() > 100)
            {
                nome = nome.substring(0, 100);
            }
            String email = TfEmail.getText().trim().toUpperCase().replace("'", "''");
            if(email.length() > 100)
            {
                email = email.substring(0, 100);
            }
            
            String login = TfLogin.getText().trim().toUpperCase().replace("'", "''");
            if(login.length() > 100)
            {
                login = login.substring(0, 100);
            }
            
            ConexaoBanco conecta = new ConexaoBanco();
            
            //verifica se há alguma conta com esses dados
            int idUsuario = conecta.verificaConta(nome, email, login);
            
            if(idUsuario > 0)
            {
                String novaSenha = (String) JOptionPane.showInputDialog(this, Executavel.getProp("NovaSenha"), this.getTitle(),JOptionPane.PLAIN_MESSAGE);
                //o usuário pode cancelar
                if(novaSenha != null)
                {
                    //passa só o login agora que o usuário já "provou " que é ele
                    if(conecta.mudaSenha(login, novaSenha))
                    {
                        JOptionPane.showMessageDialog(this, Executavel.getProp("SenhaAtualiza") + "!", this.getTitle(),JOptionPane.PLAIN_MESSAGE);
                        Login telaLogin = new Login();
                        this.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, Executavel.getProp("SenhaErro") + "!", this.getTitle(),JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, Executavel.getProp("Incorretos") + "!", this.getTitle(),JOptionPane.PLAIN_MESSAGE);
            }
            //fecha a conexão
            conecta.fecha();
        }
        else
        {
            btVerifica.setEnabled(false);
        }
    }//GEN-LAST:event_btVerificaActionPerformed

    /**Quando solta o botão tem que ver se está preenchido ou não*/
    private void TfEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TfEmailKeyReleased
        TfLoginKeyReleased(evt);
    }//GEN-LAST:event_TfEmailKeyReleased

    /**Quando solta o botão tem que ver se está preenchido ou não*/
    private void TfNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TfNomeKeyReleased
        TfLoginKeyReleased(evt);
    }//GEN-LAST:event_TfNomeKeyReleased

    /**Se os campos não estiverem vazios habilita o botão de verificação*/
    private void TfLoginKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TfLoginKeyReleased
        btVerifica.setEnabled(camposNaoVazios());
    }//GEN-LAST:event_TfLoginKeyReleased

    /**Cancela e volta à tela anterior*/
    private void btCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelaActionPerformed
        Login login = new Login();
        this.dispose();
    }//GEN-LAST:event_btCancelaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TfEmail;
    private javax.swing.JTextField TfLogin;
    private javax.swing.JTextField TfNome;
    private javax.swing.JButton btCancela;
    private javax.swing.JButton btVerifica;
    private javax.swing.JLabel lEmail;
    private javax.swing.JLabel lLogin;
    private javax.swing.JLabel lNome;
    // End of variables declaration//GEN-END:variables
}