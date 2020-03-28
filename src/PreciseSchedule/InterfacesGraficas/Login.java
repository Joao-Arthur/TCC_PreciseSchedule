package PreciseSchedule.InterfacesGraficas;

import PreciseSchedule.ConexaoBanco;
import PreciseSchedule.Executavel;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class Login extends JFrame
{
    //booleans pra mudar o texto inicial dos campos
    private boolean mudaLogin, mudaSenha;
    //guarda o caractere do campo de senha
    private final char chSenha;
    
    public Login()
    {
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        initComponents();
        setTitle(Executavel.getProp("Login") + " - Precise Schedule 1.0");
        setVisible(true); 
        
        tfLogin.setText(Executavel.getProp("Login"));
        PfSenha.setText(Executavel.getProp("Senha"));
        btLogin.setText(Executavel.getProp("Entrar"));
        btTrocarSenha.setText(Executavel.getProp("EsqueciSenha"));
        btCancela.setText(Executavel.getProp("Cancelar"));
        btMostraSenha.setText(Executavel.getProp("Ver"));

        //inicia com foco no botão
        btLogin.requestFocus();
        //começa mostrando o texto que está no botão
        chSenha = PfSenha.getEchoChar();
        PfSenha.setEchoChar((char) 0);
    }
    
    /**Atualiza o estado do botão de ver a senha*/
    private void atualizaBotao()
    {
        btMostraSenha.setEnabled(mudaSenha);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btLogin = new javax.swing.JButton();
        tfLogin = new javax.swing.JTextField();
        PfSenha = new javax.swing.JPasswordField();
        btTrocarSenha = new javax.swing.JButton();
        btMostraSenha = new javax.swing.JButton();
        btCancela = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setName("frameLogin"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btLogin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btLogin.setText("Entrar");
        btLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLoginActionPerformed(evt);
            }
        });

        tfLogin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tfLogin.setForeground(new java.awt.Color(120, 120, 120));
        tfLogin.setText("Login");
        tfLogin.setToolTipText("");
        tfLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfLogin.setName(""); // NOI18N
        tfLogin.setPreferredSize(new java.awt.Dimension(48, 24));
        tfLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfLoginFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfLoginFocusLost(evt);
            }
        });
        tfLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfLoginActionPerformed(evt);
            }
        });

        PfSenha.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        PfSenha.setForeground(new java.awt.Color(120, 120, 120));
        PfSenha.setText("Senha");
        PfSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PfSenhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PfSenhaFocusLost(evt);
            }
        });
        PfSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PfSenhaActionPerformed(evt);
            }
        });

        btTrocarSenha.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btTrocarSenha.setText("Esqueci Minha Senha");
        btTrocarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTrocarSenhaActionPerformed(evt);
            }
        });

        btMostraSenha.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btMostraSenha.setText("Ver");
        btMostraSenha.setToolTipText("");
        btMostraSenha.setEnabled(false);
        btMostraSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btMostraSenhaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btMostraSenhaMouseReleased(evt);
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
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btCancela, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btTrocarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btMostraSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btMostraSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PfSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(btLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btTrocarSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btCancela)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {PfSenha, btLogin, tfLogin});

        tfLogin.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**Tenta fazer o login*/
    private void btLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoginActionPerformed
        //pega os valores tratados
        String login = tfLogin.getText().trim().toUpperCase().replace("'", "''");
        if(login.length() > 100)
        {
            login = login.substring(0, 100);
        }
        String senha = String.valueOf(PfSenha.getPassword()).trim().toUpperCase().replace("'", "''");
        if(senha.length() > 100)
        {
            senha = senha.substring(0, 100);
        }
        
        //se não for um valor vazio ou padrão
        if(!((login.equals(Executavel.getProp("Login").toUpperCase()))||(login.equals(""))||(senha.equals(Executavel.getProp("Senha").toUpperCase()))||(senha.equals(""))))
        {
            //tenta fazer login
            ConexaoBanco conecta = new ConexaoBanco();
            
            int idUsuario = conecta.login(login, senha);
            
            if(idUsuario > 0)
            {
                this.dispose();
                Principal principal = new Principal(idUsuario);
            }
            else
            {
                JOptionPane.showMessageDialog(null, Executavel.getProp("LoginIncorreto") + "!", Executavel.getProp("ErroGeral") + "!", JOptionPane.WARNING_MESSAGE);
            }
            //fecha a conexão
            conecta.fecha();
        }
        else
        {
            //"obriga" o usuário a digitar seus dados
            JOptionPane.showMessageDialog(null, Executavel.getProp("CamposVazios") + "!", Executavel.getProp("Aviso") + "!", JOptionPane.WARNING_MESSAGE);
            tfLogin.requestFocus();
        }
    }//GEN-LAST:event_btLoginActionPerformed

    /**Redundância pra quando aperta enter no campo de login*/
    private void tfLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfLoginActionPerformed
        btLoginActionPerformed(evt);
    }//GEN-LAST:event_tfLoginActionPerformed

    /**Deixa o campo login digitável*/
    private void tfLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfLoginFocusGained
        if(!mudaLogin)
        {
            tfLogin.setText("");
            tfLogin.setForeground(Color.DARK_GRAY);
            mudaLogin = true;
        }
    }//GEN-LAST:event_tfLoginFocusGained

    /**Deixa o campo senha digitável*/
    private void PfSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PfSenhaFocusGained
        if(!mudaSenha)
        {
            PfSenha.setText("");
            PfSenha.setForeground(Color.DARK_GRAY);
            PfSenha.setEchoChar(chSenha);
            mudaSenha = true;
            
            atualizaBotao();
        }
    }//GEN-LAST:event_PfSenhaFocusGained

    /**Redundância pra quando aperta enter no campo de senha*/
    private void PfSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PfSenhaActionPerformed
        btLoginActionPerformed(evt);
    }//GEN-LAST:event_PfSenhaActionPerformed

    /**mostra os caracteres do campo senha*/
    private void btMostraSenhaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btMostraSenhaMousePressed
        if(btMostraSenha.isEnabled())
        {
            PfSenha.setEchoChar((char) 0);
        }
    }//GEN-LAST:event_btMostraSenhaMousePressed

    /**esconde os caracteres do campo senha*/
    private void btMostraSenhaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btMostraSenhaMouseReleased
        if(btMostraSenha.isEnabled())
        {
            PfSenha.setEchoChar(chSenha);
        }
    }//GEN-LAST:event_btMostraSenhaMouseReleased

    /**Instancia uma "MudaSenha"*/
    private void btTrocarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTrocarSenhaActionPerformed
        MudaSenha mudasenha = new MudaSenha();
        this.dispose();
    }//GEN-LAST:event_btTrocarSenhaActionPerformed

    /**Instancia uma "TelaInicial"*/
    private void btCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelaActionPerformed
        Inicial inicial = new Inicial();
        this.dispose();
    }//GEN-LAST:event_btCancelaActionPerformed

    /**Põe o texto inicial no campo login se estiver vazio*/
    private void tfLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfLoginFocusLost
        if(tfLogin.getText().equals(""))
        {
            tfLogin.setText("Login");
            tfLogin.setForeground(Color.GRAY);
            mudaLogin = false;
        }
    }//GEN-LAST:event_tfLoginFocusLost

    /**Põe o texto inicial no campo senha se estiver vazio*/
    private void PfSenhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PfSenhaFocusLost
        if(String.valueOf(PfSenha.getPassword()).equals(""))
        {
            PfSenha.setText("Senha");
            PfSenha.setForeground(Color.GRAY);
            PfSenha.setEchoChar((char) 0);
            mudaSenha = false;
            
            atualizaBotao();
        }
    }//GEN-LAST:event_PfSenhaFocusLost

    /**Vai questionar se é pra fechar ou não*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Object[] opcoes = {Executavel.getProp("Sim"),Executavel.getProp("Nao")};
        if(JOptionPane.showOptionDialog(this, Executavel.getProp("QuerSair") + "?", "Precise Schedule", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]) == 0)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField PfSenha;
    private javax.swing.JButton btCancela;
    private javax.swing.JButton btLogin;
    private javax.swing.JButton btMostraSenha;
    private javax.swing.JButton btTrocarSenha;
    private javax.swing.JTextField tfLogin;
    // End of variables declaration//GEN-END:variables
}