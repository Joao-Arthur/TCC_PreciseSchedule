package PreciseSchedule.InterfacesGraficas.Cadastros;

import PreciseSchedule.ConexaoBanco;
import PreciseSchedule.Executavel;
import PreciseSchedule.InterfacesGraficas.Principal;
import PreciseSchedule.Relatorio.RelatorioDia;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class CadastroEvento extends JFrame
{
    
    private final boolean edicao;
    
    //número de ocorrencias
    private final int CATEGORIA, FREQUENCIA, IMPORTANCIA;
    
    private int idUsuario;
    private int idEvento;
    
    //pegam os valores selecionados, para falicitar a sintaxe
    private int horaIni,horaFim, minIni, minFim;
    
    //variáveis de data 
    private int anoUsuario, mesUsuario, diaUsuario;
    private int tamanho;
    
    //diz se pode chamar os métodos que atualizam os componentes
    private boolean atualizavel = false;
    
    private int contador;
    
    public int ano, mes, dia, hora, minuto;
    
    //variáveis do banco
    private final ConexaoBanco conecta;
    private ResultSet resultado;
    
    //objeto tela
    private final Principal principal;
    private RelatorioDia relatorio;
    
    public CadastroEvento(RelatorioDia relatorio, Principal principal, int idEvento)
    {
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        setTitle(Executavel.getProp("Registro") + " - Precise Schedule 1.0");
        setVisible(true);
        
        initComponents();
        
        lNome.setText(Executavel.getProp("Nome"));
        lData.setText(Executavel.getProp("Data"));
        lInicio.setText(Executavel.getProp("Inicio"));
        lFim.setText(Executavel.getProp("Fim"));
        lImport.setText(Executavel.getProp("Importancia"));
        lFrequen.setText(Executavel.getProp("Frequencia"));
        lCatego.setText(Executavel.getProp("Categoria"));
        tbAtivaFreq.setText(Executavel.getProp("Repete"));
        btSalva.setText(Executavel.getProp("Salvar"));
        btCancela.setText(Executavel.getProp("Cancelar"));
        
        CATEGORIA = Executavel.getCategoria();
        FREQUENCIA = Executavel.getFrequencia();
        IMPORTANCIA = Executavel.getImportancia();
        
        this.relatorio = relatorio;
        this.idEvento = idEvento;
        
        this.edicao = true;
        
        this.principal = principal;
        
        //atualiza o estado da tabela
        principal.atualizaHabilitado(false);
        
        //cria uns get do principal e é nois que avoa
        this.ano = principal.getAno();
        this.mes = principal.getMes();
        this.dia = principal.getDia();
        this.hora = principal.getHora();
        this.minuto = principal.getMinuto();
        
        //pega a data atual e adiciona os valores nos campos de seleção
        for(contador = ano; contador <= ano + 5; contador += 1)
        {
            boxAno.addItem(String.valueOf(contador));
        }
        for(contador = mes; contador <= 12; contador += 1)
        {
            boxMes.addItem(String.valueOf(contador));
        }
        for(contador = dia; contador <= 31; contador += 1)
        {
            boxDia.addItem(String.valueOf(contador));
        }
        
        //insere horas e minutos
        for(contador = hora; contador < 24; contador += 1)
        {
            boxIniHora.addItem(String.valueOf(contador));
        }
        for(contador = minuto; contador < 60; contador += 1)
        {
            boxIniMin.addItem(String.valueOf(contador));
        }
        for(contador = hora; contador < 24; contador += 1)
        {
            boxFimHora.addItem(String.valueOf(contador));
        }
        for(contador = minuto; contador < 60; contador += 1)
        {
            boxFimMin.addItem(String.valueOf(contador));
        }
        
        for(contador = 1; contador <= CATEGORIA; contador += 1)
        {
            boxCate.addItem(Executavel.getProp("C" + contador));
        }
        for(contador = 1; contador <= IMPORTANCIA; contador += 1)
        {
            boxImport.addItem(Executavel.getProp("I" + contador));
        }
        boxFrequen.addItem(Executavel.getProp("F1"));
        
        conecta = new ConexaoBanco();
        
        resultado = conecta.getEvento(idEvento);
        
        atualizavel = true;
        
        try
        {
            if(resultado.next())
            {
                this.idUsuario = resultado.getInt("id_usuario");
                
                TfNome.setText(resultado.getString("nome"));
                
                boxAno.setSelectedItem(resultado.getString("YEAR(data_evento)"));
                boxMes.setSelectedItem(resultado.getString("MONTH(data_evento)"));
                boxDia.setSelectedItem(resultado.getString("DAY(data_evento)"));
                boxIniHora.setSelectedItem(resultado.getString("HOUR(hora_inicio)"));
                boxIniMin.setSelectedItem(resultado.getString("MINUTE(hora_inicio)"));
                boxFimHora.setSelectedItem(resultado.getString("HOUR(hora_fim)"));
                boxFimMin.setSelectedItem(resultado.getString("MINUTE(hora_fim)"));
                
                boxImport.setSelectedItem(Executavel.getProp("I" + resultado.getString("id_importancia")));
                boxFrequen.setSelectedItem(Executavel.getProp("F" + resultado.getString("id_frequencia")));
                boxCate.setSelectedItem(Executavel.getProp("C" + resultado.getString("id_categoria")));
            }
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
    }
  
    public CadastroEvento(Principal principal,int idUsuario, int ano, int mes, int dia, int hora, int minuto, int diaUsuarioFinal, int mesUsuarioFinal, int anoUsuarioFinal)
    {
        CATEGORIA = Executavel.getCategoria();
        FREQUENCIA = Executavel.getFrequencia();
        IMPORTANCIA = Executavel.getImportancia();
        
        this.edicao = false;
        
        this.idUsuario = idUsuario;
        
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.minuto = minuto;
        
        this.principal = principal;
        
        conecta = new ConexaoBanco();
        
        inicializador();
        
        boxAno.setSelectedItem(String.valueOf(anoUsuarioFinal));
        boxMes.setSelectedItem(String.valueOf(mesUsuarioFinal));
        boxDia.setSelectedItem(String.valueOf(diaUsuarioFinal));
    }
    
    /**Responsável por lidar com o cadastro e edição de eventos
     * @param principal a tela principal
     * @param idUsuario o id do usuário
     * @param ano o ano atual
     * @param mes o mes atual
     * @param dia o dia atual
     * @param hora a hora atual
     * @param minuto o munuto atual*/
    public CadastroEvento(Principal principal,int idUsuario, int ano, int mes, int dia, int hora, int minuto)
    {
        CATEGORIA = Executavel.getCategoria();
        FREQUENCIA = Executavel.getFrequencia();
        IMPORTANCIA = Executavel.getImportancia();
        
        this.edicao = false;
        
        this.idUsuario = idUsuario;
        
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.minuto = minuto;
        
        this.principal = principal;
        
        conecta = new ConexaoBanco();
        
        inicializador();
    }

    private void inicializador()
    {
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        setTitle(Executavel.getProp("Registro") + " - Precise Schedule 1.0");
        setVisible(true);
        
        initComponents();
        
        lNome.setText(Executavel.getProp("Nome"));
        lData.setText(Executavel.getProp("Data"));
        lInicio.setText(Executavel.getProp("Inicio"));
        lFim.setText(Executavel.getProp("Fim"));
        lImport.setText(Executavel.getProp("Importancia"));
        lFrequen.setText(Executavel.getProp("Frequencia"));
        lCatego.setText(Executavel.getProp("Categoria"));
        tbAtivaFreq.setText(Executavel.getProp("Repete"));
        btSalva.setText(Executavel.getProp("Salvar"));
        btCancela.setText(Executavel.getProp("Cancelar"));
        
        //atualiza o estado da tabela
        principal.atualizaHabilitado(false);
        
        //insere horas e minutos
        for(contador = hora; contador < 24; contador += 1)
        {
            boxIniHora.addItem(String.valueOf(contador));
        }
        for(contador = minuto; contador < 60; contador += 1)
        {
            boxIniMin.addItem(String.valueOf(contador));
        }
        for(contador = hora; contador < 24; contador += 1)
        {
            boxFimHora.addItem(String.valueOf(contador));
        }
        for(contador = minuto; contador < 60; contador += 1)
        {
            boxFimMin.addItem(String.valueOf(contador));
        }
        
        for(contador = 1; contador <= CATEGORIA; contador += 1)
        {
            boxCate.addItem(Executavel.getProp("C" + contador));
        }
        for(contador = 1; contador <= IMPORTANCIA; contador += 1)
        {
            boxImport.addItem(Executavel.getProp("I" + contador));
        }
        boxFrequen.addItem(Executavel.getProp("F1"));
        
        //valores padrão
        tbAtivaFreq.setSelected(false);
        boxFrequen.setEnabled(false);
        
        boxCate.setSelectedItem(Executavel.getProp("C2"));
        boxImport.setSelectedItem(Executavel.getProp("I3"));

        //pega a data atual e adiciona os valores nos campos de seleção
        for(contador = ano; contador <= ano + 5; contador += 1)
        {
            boxAno.addItem(String.valueOf(contador));
        }
        for(contador = mes; contador <= 12; contador += 1)
        {
            boxMes.addItem(String.valueOf(contador));
        }
        for(contador = dia; contador <= 31; contador += 1)
        {
            boxDia.addItem(String.valueOf(contador));
        }
        
        //se atualizar os valores dos combobox antes de adicionar os valores vai dar erro
        atualizavel = true;
    }
    
    /**Atualiza as variáveis da seleção da data*/
    private void selecionadosData()
    {
        anoUsuario = Integer.parseInt((String) boxAno.getSelectedItem());
        mesUsuario = Integer.parseInt((String) boxMes.getSelectedItem());
        diaUsuario = Integer.parseInt((String) boxDia.getSelectedItem());
    }

    /**Atualiza as variáveis da seleção da hora*/
    private void selecionadosHora()
    {
        horaIni = Integer.parseInt((String) boxIniHora.getSelectedItem());
        minIni = Integer.parseInt((String) boxIniMin.getSelectedItem());
        horaFim = Integer.parseInt((String) boxFimHora.getSelectedItem());
        minFim = Integer.parseInt((String) boxFimMin.getSelectedItem());
    }
    
    /**@return se a data selecionada é maior que a data atual*/
    private boolean isDataMaior()
    {
        return ((anoUsuario > ano)||(mesUsuario > mes)||(diaUsuario > dia));
    }
    
    /**Quando muda a data tem que atualizar o horário que o usuário seleciona*/
    private void verificaHora()
    {
        selecionadosData();
        
        //pega o índice
        String iIniHora = (String) boxIniHora.getSelectedItem();
        String iIniMin = (String) boxIniMin.getSelectedItem();
        String iFimHora = (String) boxFimHora.getSelectedItem();
        String iFimMin = (String) boxFimMin.getSelectedItem();
        
        //remove tudo
        boxIniHora.removeAllItems();
        boxIniMin.removeAllItems();
        boxFimHora.removeAllItems();
        boxFimMin.removeAllItems();
        
        if(isDataMaior())
        {
            //adiciona tudo
            for(contador = 0; contador < 24; contador += 1)
            {
                boxIniHora.addItem(String.valueOf(contador));
            }
            for(contador = 0; contador < 60; contador += 1)
            {
                boxIniMin.addItem(String.valueOf(contador));
            }
            for(contador = 0; contador < 24; contador += 1)
            {
                boxFimHora.addItem(String.valueOf(contador));
            }
            for(contador = 0; contador < 60; contador += 1)
            {
                boxFimMin.addItem(String.valueOf(contador));
            }
            
            //seta o índice selecionado
            boxIniHora.setSelectedItem(iIniHora);
            boxIniMin.setSelectedItem(iIniMin);
            boxFimHora.setSelectedItem(iFimHora);
            boxFimMin.setSelectedItem(iFimMin);
        }
        else
        {
            //adiciona limitado
            for(contador = hora; contador < 24; contador += 1)
            {
                boxIniHora.addItem( String.valueOf(contador));
            }
            for(contador = minuto; contador < 60; contador += 1)
            {
                boxIniMin.addItem( String.valueOf(contador));
            }
            for(contador = hora; contador < 24; contador += 1)
            {
                boxFimHora.addItem( String.valueOf(contador));
            }
            for(contador = minuto; contador < 60; contador += 1)
            {
                boxFimMin.addItem( String.valueOf(contador));
            }
            
            //seta o índice selecionado se for maior que o atual
            if( Integer.parseInt(iIniHora) > hora)
            {
                boxIniHora.setSelectedItem(iIniHora);
            }
            if( Integer.parseInt(iIniMin) > minuto)
            {
                boxIniMin.setSelectedItem(iIniMin);
            }
            if( Integer.parseInt(iFimHora) > hora)
            {
                boxFimHora.setSelectedItem(iFimHora);
            }
            if( Integer.parseInt(iFimMin) > minuto)
            {
                boxFimMin.setSelectedItem(iFimMin);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        lNome = new javax.swing.JLabel();
        lData = new javax.swing.JLabel();
        lInicio = new javax.swing.JLabel();
        lFim = new javax.swing.JLabel();
        lImport = new javax.swing.JLabel();
        lFrequen = new javax.swing.JLabel();
        TfNome = new javax.swing.JTextField();
        boxDia = new javax.swing.JComboBox<>();
        boxMes = new javax.swing.JComboBox<>();
        boxAno = new javax.swing.JComboBox<>();
        boxIniHora = new javax.swing.JComboBox<>();
        boxIniMin = new javax.swing.JComboBox<>();
        boxFimHora = new javax.swing.JComboBox<>();
        boxFimMin = new javax.swing.JComboBox<>();
        boxImport = new javax.swing.JComboBox<>();
        boxFrequen = new javax.swing.JComboBox<>();
        boxCate = new javax.swing.JComboBox<>();
        lCatego = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btSalva = new javax.swing.JButton();
        tbAtivaFreq = new javax.swing.JToggleButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btCancela = new javax.swing.JButton();

        jRadioButton1.setText("jRadioButton1");

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("frameCadEvento"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        lNome.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lNome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lNome.setText("Nome");

        lData.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lData.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lData.setText("Data");

        lInicio.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lInicio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lInicio.setText("Inicio");

        lFim.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lFim.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lFim.setText("Fim");

        lImport.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lImport.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lImport.setText("Importancia");

        lFrequen.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lFrequen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lFrequen.setText("Frequência");

        TfNome.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        TfNome.setForeground(new java.awt.Color(64, 64, 64));

        boxDia.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        boxDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxDiaActionPerformed(evt);
            }
        });

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

        boxIniHora.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        boxIniHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxIniHoraActionPerformed(evt);
            }
        });

        boxIniMin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        boxIniMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxIniMinActionPerformed(evt);
            }
        });

        boxFimHora.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        boxFimHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxFimHoraActionPerformed(evt);
            }
        });

        boxFimMin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        boxFimMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxFimMinActionPerformed(evt);
            }
        });

        boxImport.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        boxFrequen.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        boxCate.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        boxCate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxCateActionPerformed(evt);
            }
        });

        lCatego.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lCatego.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lCatego.setText("Categoria");

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel8.setText(":");

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel9.setText(":");

        btSalva.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btSalva.setText("Salvar");
        btSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvaActionPerformed(evt);
            }
        });

        tbAtivaFreq.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tbAtivaFreq.setSelected(true);
        tbAtivaFreq.setText("Repete");
        tbAtivaFreq.setOpaque(true);
        tbAtivaFreq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbAtivaFreqActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel10.setText("/");

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel11.setText("/");

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
                    .addComponent(lNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lFrequen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lImport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lFim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lCatego, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btSalva, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(boxCate, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(boxFrequen, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(boxImport, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btCancela, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbAtivaFreq))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(TfNome, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(boxIniHora, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(boxFimHora, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(boxFimMin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(boxIniMin, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(boxDia, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel11)
                                .addGap(4, 4, 4)
                                .addComponent(boxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(boxAno, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lCatego, lData, lFim, lFrequen, lImport, lInicio, lNome});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lNome)
                    .addComponent(TfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(boxDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lData)
                    .addComponent(boxAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lInicio)
                    .addComponent(boxIniHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxIniMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lFim)
                    .addComponent(boxFimHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxFimMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lImport)
                    .addComponent(boxImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lFrequen)
                    .addComponent(boxFrequen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbAtivaFreq))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCatego)
                    .addComponent(boxCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(btSalva)
                .addGap(5, 5, 5)
                .addComponent(btCancela)
                .addGap(7, 7, 7))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {TfNome, boxAno, boxCate, boxDia, boxFimHora, boxFimMin, boxFrequen, boxImport, boxIniHora, boxIniMin, boxMes, btCancela, btSalva, jLabel10, jLabel11, jLabel8, jLabel9, tbAtivaFreq});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**Atualiza o que é subsequente ao ano*/
    private void boxAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxAnoActionPerformed
        //if pois o método é chamado quando os dados do objeto são mudados
        if(atualizavel)
        {
            atualizavel = false;
            
            //atualiza os valores das variáveis
            selecionadosData();
            
            //remove todos porque vai ser preenchido de qualquer forma
            boxMes.removeAllItems();
            
            //se o ano for maior automaticamente não há limitação de data
            if(anoUsuario > ano)
            {
                for(contador = 1; contador <= 12; contador += 1)
                {
                    boxMes.addItem(String.valueOf(contador));
                }
                boxMes.setSelectedItem(String.valueOf(mesUsuario));
            }
            //se o ano for o atual conta a partir do mês atual
            else
            {
                for(contador = mes; contador <= 12; contador += 1)
                {
                    boxMes.addItem(String.valueOf(contador));
                }
                if(mesUsuario >= mes)
                {
                    boxMes.setSelectedItem(String.valueOf(mesUsuario));
                }
            }
            
            atualizavel = true;
            
            boxMesActionPerformed(evt);
        }
    }//GEN-LAST:event_boxAnoActionPerformed

    /**Atualiza o que é subsequente ao mês*/
    private void boxMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxMesActionPerformed
        //if pois o método é chamado quando os dados do objeto são mudados
        if(atualizavel)
        {               
            atualizavel = false;
            
            //atualiza os valores das variáveis
            selecionadosData();
            
            tamanho = Principal.tamanhoMes(anoUsuario, mesUsuario);
            
            //remove todos porque vai ser preenchido de qualquer forma
            boxDia.removeAllItems();
            
            //se o ano ou mês for maior que o atual vai do início ao fim
            if((anoUsuario > ano)||(mesUsuario > mes))
            {
                for(contador = 1; contador <= tamanho; contador += 1)
                {
                    boxDia.addItem(String.valueOf(contador));
                }
                if(diaUsuario <= tamanho)
                {
                    boxDia.setSelectedItem(String.valueOf(diaUsuario));
                }
            }
            //se o mês for o atual conta a partir do dia atual
            else
            {
                for(contador = dia; contador <= tamanho; contador += 1)
                {
                    boxDia.addItem(String.valueOf(contador));
                }
                if(diaUsuario <= tamanho)
                {
                    boxDia.setSelectedItem(String.valueOf(diaUsuario));
                }
            }
            
            //quando o usuário muda a data muda o esquema do horário
            verificaHora();
            
            atualizavel = true;
        }
    }//GEN-LAST:event_boxMesActionPerformed

    /**Critica o valor dos outros componentes de hora*/
    private void boxIniHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxIniHoraActionPerformed
        //if pois o método é chamado quando os dados do objeto são mudados
        if(atualizavel)
        {
            atualizavel = false;
            
            //atualiza os valores das variáveis
            selecionadosHora();
            
            //pega o item selecionado
            String iIniMin = (String) boxIniMin.getSelectedItem();
            
            //remove todos porque vai ser preenchido de qualquer forma
            boxIniMin.removeAllItems();
            
            //se não está na hora do dia atual não tem limitação, senão tem
            if((isDataMaior())||(horaIni > hora))
            {
                for(contador = 0; contador < 60; contador += 1)
                {
                    boxIniMin.addItem(String.valueOf(contador));
                }
                boxIniMin.setSelectedItem(iIniMin);
            }
            else
            {
                for(contador = minuto; contador < 60; contador += 1)
                {
                    boxIniMin.addItem(String.valueOf(contador));
                }
                if(Integer.parseInt(iIniMin) > minuto)
                {
                    boxIniMin.setSelectedItem(iIniMin);
                }
            }
            
            //a hora e minuto finais tem que ser depois da inicial
            if(horaIni > horaFim)
            {
                boxFimHora.setSelectedItem(String.valueOf(horaIni));
            }
            else if((horaIni == horaFim)&&(minIni > minFim))
            {
                boxFimMin.setSelectedItem(String.valueOf(minIni));
            }
            
            atualizavel = true;
        }
    }//GEN-LAST:event_boxIniHoraActionPerformed

    /**Só critica a minutagem do outro minuto*/
    private void boxIniMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxIniMinActionPerformed
        if(atualizavel)
        {
            //atualiza os valores das variáveis
            selecionadosHora();
            //se estiver lidando com a mesma hora só muda os minutos do final
            if((horaIni == horaFim)&&(minIni > minFim))
            {
                boxFimMin.setSelectedItem(String.valueOf(minIni));
            }
        }
    }//GEN-LAST:event_boxIniMinActionPerformed

    /**Critica o valor dos outros componentes de hora*/
    private void boxFimHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxFimHoraActionPerformed
        if(atualizavel)
        {
            atualizavel = false;
            
            //atualiza os valores das variáveis
            selecionadosHora();

            //pega o item selecionado
            String iFimMin = (String) boxFimMin.getSelectedItem();
            
            //remove todos porque vai ser preenchido de qualquer forma
            boxFimMin.removeAllItems();
            
            //se não está na hora do dia atual não tem limitação, senão tem
            if((isDataMaior())||(horaFim > hora))
            {
                for(contador = 0; contador < 60; contador += 1)
                {
                    boxFimMin.addItem(String.valueOf(contador));
                }
                boxFimMin.setSelectedItem(iFimMin);
            }
            else
            {
                for(contador = minuto; contador < 60; contador += 1)
                {
                    boxFimMin.addItem(String.valueOf(contador));
                }
                if(Integer.parseInt(iFimMin) > minuto)
                {
                    boxFimMin.setSelectedItem(iFimMin);
                }
            }
            
            //a hora final tem que ser depois da inicial
            if(horaIni > horaFim)
            {
                boxIniHora.setSelectedItem(String.valueOf(horaFim));
            }
            else if((horaIni == horaFim)&&(minIni > minFim))
            {
                boxIniMin.setSelectedItem(String.valueOf(minFim));
            }
            
            atualizavel = true;
        }
    }//GEN-LAST:event_boxFimHoraActionPerformed

    /**Só critica a minutagem do outro minuto*/
    private void boxFimMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxFimMinActionPerformed
        if(atualizavel)
        {
            selecionadosHora();
            //se estiver lidando com a mesma hora só muda os minutos
            if((horaIni == horaFim)&&(minIni > minFim))
            {
                boxIniMin.setSelectedItem(String.valueOf(minFim));
            }
        }
    }//GEN-LAST:event_boxFimMinActionPerformed

    /**Vai lidar com tentar salvar no banco de dados*/
    private void btSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvaActionPerformed
        if("".equals(TfNome.getText()))
        {
            JOptionPane.showMessageDialog(this, Executavel.getProp("CamposVazios") + "!", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            //no sql o caracter de escape para ' é ''
            String nome = TfNome.getText().trim().toUpperCase().replace("'", "''");
            if(nome.length() > 100)
            {
                nome = nome.substring(0, 100);
            }
            String data = (String) boxAno.getSelectedItem() + "-"+ boxMes.getSelectedItem() + "-" + boxDia.getSelectedItem();
            
            String horaUsuario = (String) boxIniHora.getSelectedItem();
            String min = (String) boxIniMin.getSelectedItem();
            
            String inicio = horaUsuario + ":" + min;
            
            horaUsuario = (String) boxFimHora.getSelectedItem();
            min = (String) boxFimMin.getSelectedItem();
            
            String fim = horaUsuario + ":" + min;
            
            for(contador = 1; contador <= CATEGORIA; contador += 1)
            {
                if(String.valueOf(boxCate.getSelectedItem()).equals(Executavel.getProp("C" + contador)))
                {
                    break;
                }
            }
            int categoria = contador;
            
            for(contador = 1; contador <= FREQUENCIA; contador += 1)
            {
                if(String.valueOf(boxFrequen.getSelectedItem()).equals(Executavel.getProp("F" + contador)))
                {
                    break;
                }
            }
            int frequencia = contador;
            
            for(contador = 1; contador <= IMPORTANCIA; contador += 1)
            {
                if(String.valueOf(boxImport.getSelectedItem()).equals(Executavel.getProp("I" + contador)))
                {
                    break;
                }
            }
            int importancia = contador;
            
            Object[] opcoes = {Executavel.getProp("Sim"),Executavel.getProp("Nao")};
            if(JOptionPane.showOptionDialog(this, Executavel.getProp("Certeza") + "?", this.getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]) == 0)
            {
                if(edicao)
                {
                    if(conecta.atualizaEvento(idEvento, nome, data, inicio, fim, importancia, frequencia, categoria))
                    {
                        JOptionPane.showMessageDialog(this, Executavel.getProp("AtualizaSucesso") + "!", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
                        principal.atualizaTabela();
                        relatorio.atualiza();
                        relatorio.requestFocus();
                        this.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, Executavel.getProp("AtualizaErro") + "!", this.getTitle(), JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    if(conecta.insereEvento(nome, data, inicio, fim, idUsuario, importancia, frequencia, categoria))
                    {
                        JOptionPane.showMessageDialog(this, Executavel.getProp("CadastraSucesso") + "!", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
                        principal.novoEvento();
                        this.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, Executavel.getProp("CadastroErro") + "!", this.getTitle(), JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_btSalvaActionPerformed

    /**Muda a categoria muda a repetição*/
    private void boxCateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxCateActionPerformed
        if(atualizavel)
        {
            //aniversário sempre vai se repetir todo ano, senão pode sera qualquer um
            if(boxCate.getSelectedItem().equals(Executavel.getProp("C1")))
            {
                boxFrequen.removeAllItems();
                boxFrequen.addItem(Executavel.getProp("F5"));
                
                boxFrequen.setEnabled(false);
                tbAtivaFreq.setEnabled(false);
            }
            else
            {
                //salva o índice que o usuário estava navegando
                int indice = boxFrequen.getSelectedIndex();
                
                //limpa e adiciona os valores
                boxFrequen.removeAllItems();
                
                if(tbAtivaFreq.isSelected())
                {
                    
                    for(contador = 2; contador <= FREQUENCIA; contador += 1)
                    {
                        boxFrequen.addItem(Executavel.getProp("F" + contador));
                    }
                    
                    //seleciona o índice
                    boxFrequen.setSelectedIndex(indice);
                }
                else
                {
                    boxFrequen.addItem(Executavel.getProp("F1"));
                }
                
                //por último habilita o campo e o botão
                boxFrequen.setEnabled(tbAtivaFreq.isSelected());
                tbAtivaFreq.setEnabled(true);
            }
        }
    }//GEN-LAST:event_boxCateActionPerformed

    /**Quando fecha essa tela, muda o foco para a tela principal*/
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //tenta fechar a conexão com o banco
        conecta.fecha();
        
        //atualiza o estado da tabela
        principal.atualizaHabilitado(true);
    }//GEN-LAST:event_formWindowClosed

    /*Muda o conteúdo do campo de seleção pra se adaptar com o valor do botão*/
    private void tbAtivaFreqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbAtivaFreqActionPerformed
        boxFrequen.removeAllItems();
        boolean selecionado = tbAtivaFreq.isSelected();
        if(selecionado)
        {
            for(contador = 2; contador <= FREQUENCIA; contador += 1)
            {
                boxFrequen.addItem(Executavel.getProp("F" + contador));
            }
        }
        else
        {
            boxFrequen.addItem(Executavel.getProp("F1"));
        }
        //ativa ou desativa a seleção do campo caso haja só 1 valor
        boxFrequen.setEnabled(selecionado);
    }//GEN-LAST:event_tbAtivaFreqActionPerformed

    /**Cancela a ação toda, fecha a tela*/
    private void btCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelaActionPerformed
        this.dispose();
    }//GEN-LAST:event_btCancelaActionPerformed

    /**Verifica a seleção de hora através da data*/
    private void boxDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxDiaActionPerformed
        if(atualizavel)
        {
            atualizavel = false;
            verificaHora();
            atualizavel = true;
        }
    }//GEN-LAST:event_boxDiaActionPerformed
    
    public void atualiza(int hora, int minuto)
    {
        atualizavel = false;
        
        //só muda a hora quando o valor mudar
        if(this.hora != hora)
        {
            //remove, se está vazio, repreenche
            boxIniHora.removeItemAt(0);
            if(boxIniHora.getItemCount() == 0)
            {
                for(contador = hora; contador < 24; contador += 1)
                { 
                    boxIniHora.addItem(String.valueOf(contador));
                }
            }

            boxIniHora.setSelectedIndex(0);
            
            //remove, se está vazio, repreenche
            boxFimHora.removeItemAt(0);
            if(boxFimHora.getItemCount() == 0)
            {
                for(contador = hora; contador < 24; contador += 1)
                {
                    boxFimHora.addItem(String.valueOf(contador));
                }
            }
        
            boxFimHora.setSelectedIndex(0);
        }
        
        //esse muda de qualquer forma
        if((boxIniHora.getSelectedIndex() == 0))
        {
            //remove, se está vazio, repreenche
            boxIniMin.removeItemAt(0);
            if(boxIniMin.getItemCount() == 0)
            {
                for(contador = minuto; contador < 60; contador += 1)
                {
                    boxIniMin.addItem(String.valueOf(contador));
                }
            }
        
            boxIniMin.setSelectedIndex(0);
        }

        //esse muda de qualquer forma
        if(boxFimHora.getSelectedIndex() == 0)
        {
            //remove, se está vazio, repreenche
            boxFimMin.removeItemAt(0);
            if(boxFimMin.getItemCount() == 0)
            {
                for(contador = minuto; contador < 60; contador += 1)
                {
                    boxFimMin.addItem(String.valueOf(contador));
                }
            }
            
            boxFimMin.setSelectedIndex(0);
        }
        
        atualizavel = true;
        
        this.hora = hora;
        this.minuto = minuto;
    }
    
    public void atualiza(int ano, int mes, int dia, int hora, int minuto)
    {
        
//        atualizavel = false;
//        
//        //esse muda de qualquer forma
//        if((boxIniHora.getSelectedIndex() == 0))
//        {
//            //remove, se está vazio, repreenche
//            boxIniMin.removeItemAt(0);
//            if(boxIniMin.getItemCount() == 0)
//            {
//                for(contador = minuto; contador < 60; contador += 1)
//                {
//                    boxIniMin.addItem(String.valueOf(contador));
//                }
//            }
//        
//            boxIniMin.setSelectedIndex(0);
//        }
//
//        //esse muda de qualquer forma
//        if(boxFimHora.getSelectedIndex() == 0)
//        {
//            //remove, se está vazio, repreenche
//            boxFimMin.removeItemAt(0);
//            if(boxFimMin.getItemCount() == 0)
//            {
//                for(contador = minuto; contador < 60; contador += 1)
//                {
//                    boxFimMin.addItem(String.valueOf(contador));
//                }
//            }
//            
//            boxFimMin.setSelectedIndex(0);
//        }
//        
//        //só muda a hora quando o valor mudar
//        if(this.hora != hora)
//        {
//            //remove, se está vazio, repreenche
//            boxIniHora.removeItemAt(0);
//            if(boxIniHora.getItemCount() == 0)
//            {
//                for(contador = hora; contador < 24; contador += 1)
//                { 
//                    boxIniHora.addItem(String.valueOf(contador));
//                }
//            }
//
//            boxIniHora.setSelectedIndex(0);
//            
//            //remove, se está vazio, repreenche
//            boxFimHora.removeItemAt(0);
//            if(boxFimHora.getItemCount() == 0)
//            {
//                for(contador = hora; contador < 24; contador += 1)
//                {
//                    boxFimHora.addItem(String.valueOf(contador));
//                }
//            }
//        
//            boxFimHora.setSelectedIndex(0);
//        }
//        
//        //a quantidade de itens no ano não muda
//        if(this.ano != ano)
//        {
//            int indAno = boxAno.getSelectedIndex();
//            
//            boxAno.removeItemAt(0);
//            boxAno.addItem(String.valueOf(ano + 5));
//            
//            boxAno.setSelectedIndex(indAno);
//        }
//        
//        //só muda se for o mesmo ano
//        if((boxAno.getSelectedIndex() == 0)&&(this.mes != mes))
//        {
//            boxMes.removeItemAt(0);
//            if(boxMes.getItemCount() == 0)
//            {
//                for(contador = 1; contador <= 12; contador += 1)
//                {
//                    boxMes.addItem(String.valueOf(contador));
//                }
//            }
//            
//            boxMes.setSelectedIndex(0);
//        }
//        
//        //só muda se for o mesmo mês do mesmo ano
//        if((boxAno.getSelectedIndex() == 0)&&(boxMes.getSelectedIndex() == 0)&&(this.dia != dia))
//        {
//            boxDia.removeItemAt(0);
//            if(boxDia.getItemCount() == 0)
//            {
//                int tamanhoMes = Principal.tamanhoMes(ano, mes);
//                for(contador = 1; contador <= tamanhoMes; contador += 1)
//                {
//                    boxDia.addItem(String.valueOf(contador));
//                }
//            }
//            
//            boxDia.setSelectedIndex(0);
//        }
//        
//        atualizavel = true;
        
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.minuto = minuto;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TfNome;
    private javax.swing.JComboBox<String> boxAno;
    private javax.swing.JComboBox<String> boxCate;
    private javax.swing.JComboBox<String> boxDia;
    private javax.swing.JComboBox<String> boxFimHora;
    private javax.swing.JComboBox<String> boxFimMin;
    private javax.swing.JComboBox<String> boxFrequen;
    private javax.swing.JComboBox<String> boxImport;
    private javax.swing.JComboBox<String> boxIniHora;
    private javax.swing.JComboBox<String> boxIniMin;
    private javax.swing.JComboBox<String> boxMes;
    private javax.swing.JButton btCancela;
    private javax.swing.JButton btSalva;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lCatego;
    private javax.swing.JLabel lData;
    private javax.swing.JLabel lFim;
    private javax.swing.JLabel lFrequen;
    private javax.swing.JLabel lImport;
    private javax.swing.JLabel lInicio;
    private javax.swing.JLabel lNome;
    private javax.swing.JToggleButton tbAtivaFreq;
    // End of variables declaration//GEN-END:variables
}