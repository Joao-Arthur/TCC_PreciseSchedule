package PreciseSchedule.InterfacesGraficas;

import PreciseSchedule.Executavel;
import PreciseSchedule.GerenciadoresArquivos.ArquivoConfiguracoes;
import java.awt.Color;

public class Intermediario extends javax.swing.JFrame
{
    private int dia, mes, ano;
    
    private Principal principal;
    
    public Intermediario(Principal principal, int ano, int mes, int dia)
    {
        initComponents();
        
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        
        setVisible(true);
        getContentPane().setBackground(new Color(192, 192, 192));
        
        switch(Integer.parseInt(new ArquivoConfiguracoes().getPropriedades().getProperty("lingua")))
        {
            case 0:
                lData.setText(dia + "/" + mes + "/" + ano);
                break;
            case 1:
                lData.setText(mes + "/" + dia + "/" + ano);
        }
        
        this.principal = principal;
        
        btAdicionar.setText(Executavel.getProp("AddEvento"));
        btRelatorio.setText(Executavel.getProp("RelatorioDia"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btAdicionar = new javax.swing.JButton();
        btRelatorio = new javax.swing.JButton();
        lData = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        btAdicionar.setText("adicionar evento");
        btAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarActionPerformed(evt);
            }
        });

        btRelatorio.setText("relatório do dia");
        btRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRelatorioActionPerformed(evt);
            }
        });

        lData.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        lData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lData.setText("01/01/2000");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btAdicionar)
                    .addComponent(btRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lData, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btAdicionar, btRelatorio, lData});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lData)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(btAdicionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btRelatorio)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        this.dispose();
    }//GEN-LAST:event_formWindowLostFocus

    private void btRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatorioActionPerformed
        principal.adicionarRelatorio(dia);
        this.dispose();
    }//GEN-LAST:event_btRelatorioActionPerformed

    private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed
        principal.adicionarEvento(dia, mes, ano);
        this.dispose();
    }//GEN-LAST:event_btAdicionarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdicionar;
    private javax.swing.JButton btRelatorio;
    private javax.swing.JLabel lData;
    // End of variables declaration//GEN-END:variables
}