package PreciseSchedule.InterfacesGraficas.Cadastros;
import PreciseSchedule.ConexaoBanco;
import PreciseSchedule.Executavel;
import PreciseSchedule.InterfacesGraficas.Inicial;
import PreciseSchedule.InterfacesGraficas.Login;
import PreciseSchedule.InterfacesGraficas.Principal;
import PreciseSchedule.RetornaData;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class CadastroUsuario extends JFrame
{
    //armazena o caracter do campo de senha
    private final char chSenha;
    
    //variáveis da classe
    private int contador, anoUsuario, mesUsuario, diaUsuario;
    private int tamanho;
    //booleans que provam que o usuário não fechou a tela
    private boolean cancelou, salvou;
    
    //objetos da classe
    private Principal principal;
    private ConexaoBanco conecta;
    
    /**Cadastra usuários a partir da tela principal
     * @param principal objeto da tela principal
     * @param ano ano atual*/
    public CadastroUsuario(Principal principal, int ano)
    {
        inicializador();
        //tira o foco da tela principal
        this.principal = principal;
        //atualiza o estado da tabela
        principal.atualizaHabilitado(false);
        
        //valor do ano
        for(contador = ano - 100; contador <= ano - 10; contador += 1)
        {
            boxAno.addItem(String.valueOf(contador));
        }
        
        chSenha = PfSenha.getEchoChar();
    }
    
    /**Cadastra usuários a partir da tela inicial*/
    public CadastroUsuario()
    {
        inicializador();
        //instancia um RetornaData, porque é improvável que em menos de 4 segundos vá pro principal
        RetornaData retorna = new RetornaData();
        int ano = retorna.getAno();
        
        //valor do ano
        for(contador = ano - 100; contador <= ano - 10; contador += 1)
        {
            boxAno.addItem(String.valueOf(contador));
        }
        
        chSenha = PfSenha.getEchoChar();
    }
    
    /**códido que é comum a ambos os construtores*/
    private void inicializador()
    {
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        initComponents();
        setTitle( Executavel.getProp("Cadastrar") + " - Precise Schedule 1.0");
        setVisible(true);
        
        lNome.setText(Executavel.getProp("Nome"));
        lDtNasc.setText(Executavel.getProp("Nascimento"));
        lEmail.setText(Executavel.getProp("Email"));
        lLogin.setText(Executavel.getProp("Login"));
        lSenha.setText(Executavel.getProp("Senha"));
        BtEnvia.setText(Executavel.getProp("Enviar"));
        BtCancela.setText(Executavel.getProp("Cancelar"));
        BtMostraSenha.setText(Executavel.getProp("Ver"));
        
        //preenche os campos com os valores
        for(contador = 1; contador <= 31; contador += 1)
        {
            boxDia.addItem(String.valueOf(contador));
        }
        for(contador = 1; contador <= 12; contador += 1)
        {
            boxMes.addItem(String.valueOf(contador));
        }
        
        //cria a conexão com o banco
        conecta = new ConexaoBanco();
    }
    
    private void atualizaSelecionados()
    {
        //dia e ano selecionados
        anoUsuario = Integer.valueOf((String) boxAno.getSelectedItem());
        mesUsuario = Integer.valueOf((String) boxMes.getSelectedItem());
        diaUsuario = Integer.valueOf((String) boxDia.getSelectedItem());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lNome = new javax.swing.JLabel();
        lDtNasc = new javax.swing.JLabel();
        lEmail = new javax.swing.JLabel();
        lLogin = new javax.swing.JLabel();
        lSenha = new javax.swing.JLabel();
        BtEnvia = new javax.swing.JButton();
        TfNome = new javax.swing.JTextField();
        TfEmail = new javax.swing.JTextField();
        TfLogin = new javax.swing.JTextField();
        boxDia = new javax.swing.JComboBox<>();
        boxMes = new javax.swing.JComboBox<>();
        boxAno = new javax.swing.JComboBox<>();
        PfSenha = new javax.swing.JPasswordField();
        BtMostraSenha = new javax.swing.JButton();
        BtCancela = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("frameCadUsuario"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        lNome.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lNome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lNome.setText("Nome");

        lDtNasc.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lDtNasc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lDtNasc.setText("Nascimento");

        lEmail.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lEmail.setText("E-mail");

        lLogin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lLogin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lLogin.setText("Login");

        lSenha.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lSenha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lSenha.setText("Senha");

        BtEnvia.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        BtEnvia.setText("Enviar");
        BtEnvia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEnviaActionPerformed(evt);
            }
        });

        TfNome.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        TfNome.setForeground(new java.awt.Color(64, 64, 64));

        TfEmail.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        TfEmail.setForeground(new java.awt.Color(64, 64, 64));

        TfLogin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        TfLogin.setForeground(new java.awt.Color(64, 64, 64));

        boxDia.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        boxMes.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        boxMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxMesActionPerformed(evt);
            }
        });

        boxAno.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        boxAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxAnoActionPerformed(evt);
            }
        });

        PfSenha.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        PfSenha.setForeground(new java.awt.Color(64, 64, 64));
        PfSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PfSenhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PfSenhaFocusLost(evt);
            }
        });

        BtMostraSenha.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        BtMostraSenha.setText("Ver");
        BtMostraSenha.setToolTipText("");
        BtMostraSenha.setEnabled(false);
        BtMostraSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BtMostraSenhaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                BtMostraSenhaMouseReleased(evt);
            }
        });

        BtCancela.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        BtCancela.setText("Cancelar");
        BtCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCancelaActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel11.setText("/");

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel12.setText("/");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lDtNasc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lSenha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TfLogin)
                    .addComponent(TfEmail)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boxDia, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11)
                        .addGap(10, 10, 10)
                        .addComponent(boxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel12)
                        .addGap(10, 10, 10)
                        .addComponent(boxAno, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TfNome)
                    .addComponent(PfSenha, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtEnvia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtCancela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtMostraSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lNome))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lDtNasc)
                    .addComponent(boxDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lEmail)
                    .addComponent(TfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lLogin)
                    .addComponent(TfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lSenha)
                    .addComponent(PfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtMostraSenha))
                .addGap(10, 10, 10)
                .addComponent(BtEnvia)
                .addGap(5, 5, 5)
                .addComponent(BtCancela)
                .addGap(7, 7, 7))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BtCancela, BtEnvia, BtMostraSenha, PfSenha, TfEmail, TfLogin, TfNome, boxAno, boxDia, boxMes});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**Atualiza os dados dos combobox*/
    private void boxMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxMesActionPerformed
        //if pois o método é chamado quando o objeto é criado
        if((boxAno.getSelectedItem() != null)&&(boxMes.getSelectedItem() != null)&&(boxDia.getSelectedItem() != null))
        {
            atualizaSelecionados();

            //limpa o combobox pra não empilhar valores obsoletos
            boxDia.removeAllItems();
            
            //meses com 30 ou 31 dias
            System.out.println("m" + mesUsuario);
            System.out.println("a" + anoUsuario);
            tamanho = Principal.tamanhoMes(anoUsuario, mesUsuario);
            System.out.println(tamanho);
            
            for(contador = 1; contador <= tamanho; contador += 1)
            {
                boxDia.addItem(String.valueOf(contador));
            }
            if(diaUsuario < tamanho)
            {
                boxDia.setSelectedIndex(diaUsuario - 1);
            }
        }
    }//GEN-LAST:event_boxMesActionPerformed

    /**método que critica se é ano bissesto ou não para o combobox*/
    private void boxAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxAnoActionPerformed
        if(boxMes.getSelectedIndex() == 1)
        {
            atualizaSelecionados();
            
            //limpa o campo de dias
            boxDia.removeAllItems();
            
            //regra ano bissesto pelo calendário gregoriano
            if((anoUsuario % 400 == 0)||((anoUsuario % 4 == 0)&&(anoUsuario % 100 != 0)))
            {
                for(contador = 1; contador <= 29; contador += 1)
                {
                    boxDia.addItem(String.valueOf(contador));
                }
                if(diaUsuario <= 29)
                {
                    boxDia.setSelectedIndex(diaUsuario - 1);
                }
            }
            //não-bissesto
            else
            {
                for(contador = 1; contador <= 28; contador += 1)
                {
                    boxDia.addItem(String.valueOf(contador));
                }
                if(diaUsuario <= 28)
                {
                    boxDia.setSelectedIndex(diaUsuario - 1);
                }
            }
        }
    }//GEN-LAST:event_boxAnoActionPerformed

    /**método que cadastra no banco*/
    private void BtEnviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEnviaActionPerformed
        //critica se estão vazios
        if(("".equals(TfNome.getText()))||("".equals(TfEmail.getText()))||("".equals(TfLogin.getText()))||("".equals(String.valueOf(PfSenha.getPassword()))))
        {
            JOptionPane.showMessageDialog(this, Executavel.getProp("CamposVazios") + "!", Executavel.getProp("Aviso") +  "!", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            //variáveis dos campos
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
            String ano = (String) boxAno.getSelectedItem();
            String mes = (String) boxMes.getSelectedItem();
            String dia = (String) boxDia.getSelectedItem();
            String data = ano + "-" + mes + "-" + dia;
            String stLogin = TfLogin.getText().trim().toUpperCase().replace("'", "''");
            if(stLogin.length() > 100)
            {
                stLogin = stLogin.substring(0, 100);
            }
            String senha = String.valueOf( PfSenha.getPassword() ).trim().toUpperCase().replace("'", "''");
            if(senha.length() > 100)
            {
                senha = senha.substring(0, 100);
            }
            //confirmação do usuário
            Object[] opcoes = {Executavel.getProp("Sim"),Executavel.getProp("Nao")};
            if(JOptionPane.showOptionDialog(this, Executavel.getProp("Certeza") + "?", this.getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]) == 0)
            {
                //mostra o resultado pro usuário
                if(conecta.insereUsuario(nome, data, email, stLogin, senha))
                {
                    //mensagem para o usuário
                    JOptionPane.showMessageDialog(this, Executavel.getProp("UsuarioSucesso") + "!", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
                    //volta para a tela inicial
                    salvou = true;
                    this.dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(this, Executavel.getProp("CadastroErro") + "!", this.getTitle(), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_BtEnviaActionPerformed

    /**muda o caractér mostrado para a tecla pressionada*/
    private void BtMostraSenhaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtMostraSenhaMousePressed
        PfSenha.setEchoChar((char) 0);
    }//GEN-LAST:event_BtMostraSenhaMousePressed

    /**muda o caractér mostrado para o padão de senha*/
    private void BtMostraSenhaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtMostraSenhaMouseReleased
        PfSenha.setEchoChar(chSenha);
    }//GEN-LAST:event_BtMostraSenhaMouseReleased

    /**quando o usuário está digitando*/
    private void PfSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PfSenhaFocusGained
        BtMostraSenha.setEnabled(true);
    }//GEN-LAST:event_PfSenhaFocusGained

    /**cancela e volta para a tela inicial*/
    private void BtCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCancelaActionPerformed
        cancelou = true;
        this.dispose();
    }//GEN-LAST:event_BtCancelaActionPerformed

    /**muda para a tela principal, se ela existir*/
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //tenta fechar a conexão com o banco
        conecta.fecha();
        
        if(principal != null)
        {
            //atualiza o estado da tabela
            principal.atualizaHabilitado(true);
        }
        else
        {
            if(cancelou)
            {
                Inicial inicial = new Inicial();
            }
            else if(salvou)
            {
                Login telaLogin = new Login();
            }
            else
            {
                System.exit(0);
            }
        }
    }//GEN-LAST:event_formWindowClosed

    /**Quando perde o foco o botão pode não mais poder ser usado*/
    private void PfSenhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PfSenhaFocusLost
        if(String.valueOf(PfSenha.getPassword()).equals(""))
        {
            BtMostraSenha.setEnabled(false);
        }
    }//GEN-LAST:event_PfSenhaFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCancela;
    private javax.swing.JButton BtEnvia;
    private javax.swing.JButton BtMostraSenha;
    private javax.swing.JPasswordField PfSenha;
    private javax.swing.JTextField TfEmail;
    private javax.swing.JTextField TfLogin;
    private javax.swing.JTextField TfNome;
    private javax.swing.JComboBox<String> boxAno;
    private javax.swing.JComboBox<String> boxDia;
    private javax.swing.JComboBox<String> boxMes;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel lDtNasc;
    private javax.swing.JLabel lEmail;
    private javax.swing.JLabel lLogin;
    private javax.swing.JLabel lNome;
    private javax.swing.JLabel lSenha;
    // End of variables declaration//GEN-END:variables
}