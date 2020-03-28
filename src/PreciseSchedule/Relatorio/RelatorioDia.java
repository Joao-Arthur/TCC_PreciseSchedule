package PreciseSchedule.Relatorio;

import PreciseSchedule.ConexaoBanco;
import PreciseSchedule.Desenhistas.DesenhaGraficoBarra;
import PreciseSchedule.Executavel;
import PreciseSchedule.InterfacesGraficas.Cadastros.CadastroEvento;
import PreciseSchedule.InterfacesGraficas.Principal;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public final class RelatorioDia extends JFrame
{
    private static final String[] CABECALHO = new String[]{"id", Executavel.getProp("Nome"), Executavel.getProp("Data"), Executavel.getProp("Inicio"), Executavel.getProp("Fim"), Executavel.getProp("Importancia"), Executavel.getProp("Frequencia"), Executavel.getProp("Categoria")};
    
    private final int idUsuario;
    
    private final int anoUsuario, mesUsuario, diaUsuario;
    
    private int linha, coluna, contador;
    
    //vetor pra não precisar botar o id diretamente na tabela
    private int[] idEvento;
    private int nEventos;
    private boolean ativaTabela = true;
    
    private HashMap<String, DesenhaGraficoBarra> listaDesenhos;
    
    private final JPanel painel;
    private final DefaultTableModel modelo;
    
    private final ConexaoBanco conecta;
    private final Principal principal;
    public RelatorioDia(Principal principal, int anoUsuario, int mesUsuario, int diaUsuario, int idUsuario)
    {
        this.idUsuario = idUsuario;
        
        this.anoUsuario = anoUsuario;
        this.mesUsuario = mesUsuario;
        this.diaUsuario = diaUsuario;
        
        conecta = new ConexaoBanco();
        
        GridLayout grade = new GridLayout(1, 0);
        painel = new JPanel(grade);
        
        listaDesenhos = new HashMap<>();
        
        try
        {
            ResultSet resultado = conecta.getCategoriaDia(anoUsuario, mesUsuario, diaUsuario, idUsuario);
            while(resultado.next())
            {
                DesenhaGraficoBarra novo = new DesenhaGraficoBarra(anoUsuario, mesUsuario, diaUsuario, idUsuario, resultado.getInt("categoria.id"));
                painel.add(novo);
                
                listaDesenhos.put(String.valueOf(contador), novo);
                contador += 1;
            }
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        
        nEventos = conecta.getCountEventoDia(anoUsuario, mesUsuario, diaUsuario, idUsuario);
        
        modelo = (new javax.swing.table.DefaultTableModel(new Object[conecta.getCountEventoDia(anoUsuario, mesUsuario, diaUsuario, idUsuario)][], CABECALHO)
        {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }
        });
        
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        initComponents();
        setVisible(true);
        setTitle(Executavel.getProp("RelatorioDia") +" - Precise Schedule 1.0");
        
        this.principal = principal;
        //atualiza o estado da tabela
        principal.atualizaHabilitado(false);
        
        atualiza();
        
        for(contador = 0 ; contador < tabelaEventos.getColumnCount(); contador += 1)
        {
            tabelaEventos.getColumnModel().getColumn(contador).setResizable(false);
        }
        tabelaEventos.setSelectionModel(new ListaSelecao());

        
        btEdita.setEnabled(false);
        btDeleta.setEnabled(false);
    }
    
    public void atualiza()
    {
        nEventos = conecta.getCountEventoDia(anoUsuario, mesUsuario, diaUsuario, idUsuario);
        
        idEvento = new int[nEventos];
        
        modelo.setRowCount(nEventos);
        tabelaEventos.setModel(modelo);

        ResultSet resultado = conecta.getEventosParaTabelaDia(anoUsuario, mesUsuario, diaUsuario, idUsuario);
        for(linha = 0; linha < tabelaEventos.getModel().getRowCount(); linha += 1)
        {
            try
            {
                if(resultado.next())
                {
                    //idEvento[linha] = resultado.getInt("id");
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
        tabelaEventos.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaEventos.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaEventos.getColumnModel().getColumn(0).setWidth(0);
        
        for(contador = 0; contador < listaDesenhos.size(); contador += 1)
        {
            if(listaDesenhos.get(String.valueOf(contador)).setValores() == 0)
            {
                
                painel.remove(listaDesenhos.get(String.valueOf(contador)));
                SPEventos.removeAll();
                SPEventos.add(painel);
                
                listaDesenhos.remove(String.valueOf(contador));
            }
        }
        
        SPEventos.repaint();
        
        principal.atualizaTabela();
    }
    
    /*Desabilita o foco e os botões*/
    public void atualizaHabilitado(boolean ativa)
    {
        //muda o foco e a possibilidade de usar a tabela
        this.ativaTabela = ativa;
        setFocusableWindowState(ativa);
        
        //se for ativar pega o foco para si
        if(ativa)
        {
            requestFocus();
        }
        
        //muda o estado dos botões
        btDeleta.setEnabled(ativa);
        btEdita.setEnabled(ativa);
    }
    
    private class ListaSelecao extends DefaultListSelectionModel
    {
        public ListaSelecao()
        {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        @Override
        public void clearSelection(){}

        @Override
        public void removeSelectionInterval(int index0, int index1){}

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaEventos = new javax.swing.JTable();
        btEdita = new javax.swing.JButton();
        btDeleta = new javax.swing.JButton();
        SPEventos = new javax.swing.JScrollPane(painel);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("frameRelatDia"); // NOI18N
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
        tabelaEventos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaEventosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaEventos);

        btEdita.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        btEdita.setText("Editar");
        btEdita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditaActionPerformed(evt);
            }
        });

        btDeleta.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        btDeleta.setText("Deletar");
        btDeleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeletaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btEdita)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btDeleta)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SPEventos, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btEdita)
                    .addComponent(btDeleta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SPEventos, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btDeletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeletaActionPerformed
        Object[] opcoes = {Executavel.getProp("Sim"),Executavel.getProp("Nao")};
        if(JOptionPane.showOptionDialog(this, Executavel.getProp("Certeza") + "?", this.getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]) == 0)
        {
            if(conecta.deletaEvento(Integer.parseInt(String.valueOf(tabelaEventos.getModel().getValueAt(tabelaEventos.getSelectedRow(), 0)))))
            {
                JOptionPane.showMessageDialog(this, Executavel.getProp("DeletaSucesso") + "!", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
                atualiza();
            }
            else
            {
                JOptionPane.showMessageDialog(this, Executavel.getProp("DeletaErro") + "!", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btDeletaActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //tenta fechar a conexão com o banco
        conecta.fecha();
        
        //atualiza o estado da tabela
        principal.atualizaHabilitado(true);
    }//GEN-LAST:event_formWindowClosed

    private void tabelaEventosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaEventosMouseClicked
        if(ativaTabela)
        {
            if(tabelaEventos.getSelectedRowCount() > 0)
            {
                btDeleta.setEnabled(true);
                btEdita.setEnabled(true);
            }
        }
    }//GEN-LAST:event_tabelaEventosMouseClicked

    private void btEditaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditaActionPerformed
        CadastroEvento cadastra = new CadastroEvento(this, principal, Integer.parseInt((String) tabelaEventos.getValueAt(tabelaEventos.getSelectedRow(), 0)));
    }//GEN-LAST:event_btEditaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane SPEventos;
    private javax.swing.JButton btDeleta;
    private javax.swing.JButton btEdita;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaEventos;
    // End of variables declaration//GEN-END:variables
}