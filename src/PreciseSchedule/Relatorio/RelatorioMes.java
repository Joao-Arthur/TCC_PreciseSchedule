package PreciseSchedule.Relatorio;

import PreciseSchedule.ConexaoBanco;
import PreciseSchedule.Desenhistas.DesenhaGraficoLinha;
import PreciseSchedule.Executavel;
import PreciseSchedule.InterfacesGraficas.Principal;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;

public class RelatorioMes extends JFrame
{ 
    private static final String[] CABECALHO = new String[]{"id", Executavel.getProp("Nome"), Executavel.getProp("Data"), Executavel.getProp("Inicio"), Executavel.getProp("Fim"), Executavel.getProp("Importancia"), Executavel.getProp("Frequencia"), Executavel.getProp("Categoria")};
    
    //diz se pode chamar os métodos que atualizam os componentes
    boolean atualizavel = false;
    
    private int anoUsuario, mesUsuario, ano, mes;
    private final int idUsuario;
    
    private int linha, coluna, contador;
    
    private final Principal principal;
    private final ConexaoBanco conecta;
    private ResultSet resultado;
    
    private JPanel painel;
    private final DefaultTableModel modelo;
    
    public RelatorioMes(Principal principal, int anoUsuario, int mesUsuario, int idUsuario, int ano, int mes)
    {
        conecta = new ConexaoBanco();
        
        this.ano = ano;
        this.mes = mes;        
        
        GridLayout grade = new GridLayout(0, 1);
        
        painel = new JPanel(grade);
        
        try
        {
            resultado = conecta.getCategoriaMes(anoUsuario, mesUsuario, Principal.tamanhoMes(anoUsuario, mesUsuario), idUsuario);
            while(resultado.next())
            {
                DesenhaGraficoLinha novo = new DesenhaGraficoLinha(anoUsuario, mesUsuario, idUsuario, resultado.getInt("categoria.id"));
                painel.add(novo);
            }
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        
        modelo = (new DefaultTableModel(new Object[conecta.getCountEventoMes(anoUsuario, mesUsuario, idUsuario)][], CABECALHO)
        {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }
        });
        
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        setVisible(true);
        setTitle(Executavel.getProp("RelatorioMes") + " - Precise Schedule 1.0");
        initComponents();
        
        this.principal = principal;
        //atualiza o estado da tabela
        principal.atualizaHabilitado(false);
        
        this.anoUsuario = anoUsuario;
        this.mesUsuario = mesUsuario;
        
        this.idUsuario = idUsuario;
        
        LAno.setText(String.valueOf(anoUsuario));
        LMes.setText(Principal.NOMEMES[mesUsuario - 1]);
        
        atualizaAno(0);
        
        for(contador = 0 ; contador < tabelaEventos.getColumnCount(); contador++)
        {
            tabelaEventos.getColumnModel().getColumn(contador).setResizable(false);
        }
        
        atualizavel = true;
    }

    /**Atualiza o ano e os botões*/
    private void atualizaAno(int acumulador)
    {
        //atualiza o ano
        anoUsuario += acumulador;

        //atualiza o estado dos botões
        BtProxAno.setEnabled(anoUsuario - 5 < ano);
        BtAnteAno.setEnabled(anoUsuario > (ano - 5));
        
        //por fim passa o valor ao label
        LAno.setText(String.valueOf(anoUsuario));
        
        //por consequência atualiza o mês
        atualizaMes(0);
    }

    /**Atualiza o mês na tela*/
    private void atualizaMes(int acumulador)
    {
        //muda o mês
        mesUsuario += acumulador;
        
        //critica o dado
        if(mesUsuario > 12)
        {
            mesUsuario = 1;
            atualizaAno(+1);
        }
        if(mesUsuario < 1)
        {
            mesUsuario = 12;
            atualizaAno(-1);
        }
        //seta os botões
        BtProxMes.setEnabled((anoUsuario < ano + 5)||((anoUsuario == ano + 5)&&(mesUsuario < mes)));
        BtAnteMes.setEnabled((anoUsuario > ano - 5)||((anoUsuario == ano - 5)&&(mesUsuario > mes)));
        //seta o texto
        LMes.setText( Principal.NOMEMES[mesUsuario - 1] );
        
        int quantidade = conecta.getCountEventoMes(anoUsuario, mesUsuario, idUsuario);
        modelo.setRowCount(quantidade);
        tabelaEventos.setModel(modelo);

        tabelaEventos.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaEventos.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaEventos.getColumnModel().getColumn(0).setWidth(0);
        
        resultado = conecta.getEventosParaTabelaMes(anoUsuario, mesUsuario, Principal.tamanhoMes(anoUsuario, mesUsuario), idUsuario);
        for(linha = 0; linha < tabelaEventos.getModel().getRowCount(); linha += 1)
        {
            try
            {
                if(resultado.next())
                {
                    for(coluna = 1; coluna <= tabelaEventos.getModel().getColumnCount() - 3; coluna += 1)
                    {
                        tabelaEventos.getModel().setValueAt(resultado.getString(coluna), linha, coluna - 1);
                    }
                    tabelaEventos.getModel().setValueAt(Executavel.getProp("I" + resultado.getString("id_importancia")), linha, coluna - 1);
                    tabelaEventos.getModel().setValueAt(Executavel.getProp("F" + resultado.getString("id_frequencia")), linha, coluna);
                    tabelaEventos.getModel().setValueAt(Executavel.getProp("C" + resultado.getString("id_categoria")), linha, coluna + 1);
                }
                else
                {
                    break;
                }
            }
            catch(SQLException ex)
            {
                Executavel.erroBancoDeDados(ex);
            }
        }

        painel.removeAll();
        painel.repaint();

        try
        {
            //painel.removeAll();
            resultado = conecta.getCategoriaMes(anoUsuario, mesUsuario, Principal.tamanhoMes(anoUsuario, mesUsuario), idUsuario);
            while(resultado.next())
            {
                DesenhaGraficoLinha novo = new DesenhaGraficoLinha(anoUsuario, mesUsuario, idUsuario, resultado.getInt("categoria.id"));
                painel.add(novo);
                //novo.atualiza(anoUsuario, mesUsuario);
                novo.repaint();
            }
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaEventos = new javax.swing.JTable();
        SPEventos = new JScrollPane(painel);
        BtAnteMes = new javax.swing.JButton();
        LMes = new javax.swing.JLabel();
        BtProxMes = new javax.swing.JButton();
        BtAnteAno = new javax.swing.JButton();
        LAno = new javax.swing.JLabel();
        BtProxAno = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("frameRelatMes"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        tabelaEventos.setAutoCreateRowSorter(true);
        tabelaEventos.setModel(modelo);
        tabelaEventos.setDoubleBuffered(true);
        tabelaEventos.getTableHeader().setReorderingAllowed(false);
        tabelaEventos.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(tabelaEventos);
        if (tabelaEventos.getColumnModel().getColumnCount() > 0) {
            tabelaEventos.getColumnModel().getColumn(0).setResizable(false);
        }

        BtAnteMes.setText("<");
        BtAnteMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAnteMesActionPerformed(evt);
            }
        });

        LMes.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        LMes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        BtProxMes.setText(">");
        BtProxMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtProxMesActionPerformed(evt);
            }
        });

        BtAnteAno.setText("<");
        BtAnteAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAnteAnoActionPerformed(evt);
            }
        });

        LAno.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        LAno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        BtProxAno.setText(">");
        BtProxAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtProxAnoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SPEventos, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtAnteMes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LMes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtProxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtAnteAno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LAno, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtProxAno, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BtAnteAno, BtAnteMes, BtProxAno, BtProxMes});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(BtAnteMes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LMes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtProxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtAnteAno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(BtProxAno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(LAno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SPEventos, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**Mês anterior*/
    private void BtAnteMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAnteMesActionPerformed
        if(atualizavel)
        {
            atualizaMes(-1);
        }
    }//GEN-LAST:event_BtAnteMesActionPerformed

    /**Próximo mês*/
    private void BtProxMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtProxMesActionPerformed
        if(atualizavel)
        {
            atualizaMes(+1);
        }
    }//GEN-LAST:event_BtProxMesActionPerformed

    /**Ano anterior*/
    private void BtAnteAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAnteAnoActionPerformed
        if(atualizavel)
        {
            atualizaAno(-1);
        }
    }//GEN-LAST:event_BtAnteAnoActionPerformed

    /**Próximo ano*/
    private void BtProxAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtProxAnoActionPerformed
        if(atualizavel)
        {
            atualizaAno(+1);
        }
    }//GEN-LAST:event_BtProxAnoActionPerformed

    /**Quando fecha o formulário*/
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //tenta fechar a conexão com o banco
        conecta.fecha();
        
        //atualiza o estado da tabela
        principal.atualizaHabilitado(true);
    }//GEN-LAST:event_formWindowClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtAnteAno;
    private javax.swing.JButton BtAnteMes;
    private javax.swing.JButton BtProxAno;
    private javax.swing.JButton BtProxMes;
    private javax.swing.JLabel LAno;
    private javax.swing.JLabel LMes;
    private javax.swing.JScrollPane SPEventos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaEventos;
    // End of variables declaration//GEN-END:variables
}