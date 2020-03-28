package PreciseSchedule.InterfacesGraficas;

import PreciseSchedule.ConexaoBanco;
import PreciseSchedule.EventoAgendado;
import PreciseSchedule.Executavel;
import PreciseSchedule.GerenciadoresArquivos.ArquivoConfiguracoes;
import PreciseSchedule.Relatorio.RelatorioDia;
import PreciseSchedule.Relatorio.RelatorioMes;
import PreciseSchedule.InterfacesGraficas.Cadastros.CadastroUsuario;
import PreciseSchedule.InterfacesGraficas.Cadastros.CadastroEvento;
import PreciseSchedule.RetornaData;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JFrame;

public class Principal extends JFrame
{
    //array do nome dos meses
    public static String[] NOMEMES = {Executavel.getProp("M1"),Executavel.getProp("M2"),Executavel.getProp("M3"),Executavel.getProp("M4"),Executavel.getProp("M5"),Executavel.getProp("M6"),Executavel.getProp("M7"),Executavel.getProp("M8"),Executavel.getProp("M9"),Executavel.getProp("M10"),Executavel.getProp("M11"),Executavel.getProp("M12")};
    
    //código do usuário atual
    private final int idUsuario;
    
    //variáveis pra quando o usuário estiver em outras telas
    public boolean novoEvento;
    private boolean ativaTabela = true;
    
    //quando o método é chamado pelo EventoAgendado usa essa variável
    public boolean isNotDuplicata = true;
    
    //guardam qual linha e coluna o usuário clicou
    private int linhaTabela = -1, colunaTabela = -1;
    
    //dados atuais
    private int ano, mes, dia, hora, minuto;
    //dados do usuario
    private int anoUsuario, mesUsuario;
    //variáveis usadas para a tabela
    private int linha, coluna, diaDaSemana, contDia, valTamanMes,valTamanMesAnte, deContDia;
    //variável que vai armazenar os dias que estão sendo mostrados na tabela
    private final int diasMostrados[][] = new int[6][7];
    
    //classe que pega a data
    private final RetornaData retorna;
    //objeto do banco de dados
    private final ConexaoBanco conecta;
    private ResultSet resultado;
    //objeto cadastroevento
    private CadastroEvento cadastro;
    
    private final Principal principal;
    
    private int sair;
    
    public Principal(int idUsuario)
    {      
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());

        initComponents();
        setVisible(true);
        setTitle("Precise Schedule 1.0");
        setExtendedState(MAXIMIZED_BOTH);
        
        atualizaSair();
        
        //seta o texto com componentes
        atualizaTexto();
        
        //passa a variável
        this.idUsuario = idUsuario;
        principal = this;
        
        //estiliza cada coluna da tabela
        for(coluna = 0; coluna < 7; coluna++)
        {
            TableColumn col = tabelaMes.getColumnModel().getColumn(coluna);
            col.setCellRenderer(new Estiliza());
        }

        //tira a ação do enter na tabela, atrvés de algo que parece um HashMap
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        tabelaMes.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "Solve");
        tabelaMes.getActionMap().put("Solve", new EnterTabela());
        
        //cria o objeto de data
        retorna = new RetornaData();
                
        //pega os dados mais voláteis primeiro, por uma questão de performance
        hora = retorna.getHora();
        minuto = retorna.getMinuto();
        
        int tempoInicial = 60-retorna.getSegundo();
        
        //atualiza essas variáveis a cada execução
        final ScheduledExecutorService agendaTempo = Executors.newScheduledThreadPool(1);
        agendaTempo.scheduleAtFixedRate(new AtualizaTempo(), tempoInicial, 60, TimeUnit.SECONDS);
        
        //pega os campos de data
        anoUsuario = ano = retorna.getAno();
        mesUsuario = mes = retorna.getMes();
        dia = retorna.getDia();

        //cria o objeto do banco
        conecta = new ConexaoBanco();
        
        conecta.deletaAntigos(ano, mes, dia);
        
        
        //chama os métodos pra mostrar os dados na tela
        atualizaAno(0);
    }

    /**Atualiza o ano na tela e nas variáveis*/
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

    /**Atualiza o mês na tela e nas variáveis*/
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
        LMes.setText( NOMEMES[mesUsuario - 1] );
        //atualiza a tabela
        atualizaTabela();
    }

    /**Mostra os dias relativos aos mês na tabela*/
    public void atualizaTabela()
    {
        try
        {
            //transforma a string do mes atual em uma data
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dataMes = formatter.parse(anoUsuario + "-" + mesUsuario + "-01");
            //por fim pega o dia da semana que corresponde a essa data
            formatter = new SimpleDateFormat("u");
            diaDaSemana = Integer.parseInt(formatter.format(dataMes));
            if(diaDaSemana >= 7)
            {
                diaDaSemana = 0;
            }
        }
        catch(ParseException ex)
        {
            Executavel.erro(ex);
        }
        
        //preenche a tabela por linhas

        //inicia as variáveis
        contDia = linha = 0;
        valTamanMes = tamanhoMes(anoUsuario, mesUsuario);
        if(mesUsuario > 1)
        {
            valTamanMesAnte = tamanhoMes(anoUsuario, mesUsuario - 1);
        }
        else
        {
            valTamanMesAnte = tamanhoMes(anoUsuario - 1, 1);
        }
 
        //o restante do mês anterior
        deContDia = -1;
        for(coluna = diaDaSemana - 1; coluna >= 0; coluna--)
        {
            deContDia++;
            adicionaNaTabela(linha, coluna, valTamanMesAnte - deContDia);
        }
 
        //a primeira linha começa na metade da coluna
        for(coluna = diaDaSemana; coluna < 7; coluna += 1)
        {
            contDia++;
            adicionaNaTabela(linha, coluna, contDia);
        }

        //as linhas seguintes são necessáriamente preenchidas normalmente
        for(linha = 1; linha < 4; linha++)
        {
            for(coluna = 0; coluna < 7; coluna += 1)
            {
                contDia += 1;
                adicionaNaTabela(linha, coluna, contDia);
            }
        }

        //as linhas finais são as em que há transição de mês
        for(linha = 4; linha < 6; linha++)
        {
            for(coluna = 0; coluna < 7; coluna++)
            {
                contDia++;
                adicionaNaTabela(linha, coluna, contDia);
                //se o dia for maior é pq acabou o mês
                if(contDia >= valTamanMes)
                {
                    contDia = 0;
                }
            }
        }
        
        //pega os eventos do mês e joga na tabela
        pegaEventos();
    }
    
    /**Automatiza a ação de por o valor na tabela*/
    private void adicionaNaTabela(int linha, int coluna, int valor)
    {
        tabelaMes.getModel().setValueAt("<html><body><p style=\"text-align: center;\">" + valor + "</p></body></html>", linha, coluna);
        diasMostrados[linha][coluna] = valor;
    }
    
    /**Pega os eventos e joga na tabela*/
    private void pegaEventos()
    {
        resultado = conecta.getEventos(anoUsuario, mesUsuario, idUsuario);
        adicionaEvento(resultado);
    }
    
    /**verifica tão-somente se data pertence ao mês*/
    private boolean isDataNoMes(int linha, int coluna)
    {
        int diaMostrado = diasMostrados[linha][coluna];
        
        return ((linha == 0)&&(diaMostrado <= 7)||((linha > 0)&&(linha < 4))||((linha >= 4)&&(diaMostrado >= 23)));
    }
    
    /**retorna se a data pode ser selecionada*/
    private boolean isDiaSelecionavel(int diaMostrado)
    {
        return ((anoUsuario > ano)||((anoUsuario == ano)&&(mesUsuario > mes))||((anoUsuario == ano)&&(mesUsuario == mes)&&(diaMostrado >= dia)));
    }

    /**retorna se a data pode ser selecionada ou não*/
    private boolean isDataSelecionavel(int linha, int coluna)
    {
        int diaMostrado = diasMostrados[linha][coluna];
        return (((linha == 0)&&(diaMostrado <= 7)||((linha > 0)&&(linha < 4))||((linha >= 4)&&(diaMostrado >= 23)))&&
               ((anoUsuario > ano)||((anoUsuario == ano)&&(mesUsuario > mes))||((anoUsuario == ano)&&(mesUsuario == mes)&&(diaMostrado >= dia))));
    }

    /**adiciona os eventos para a tabela*/
    private void adicionaEvento(ResultSet resultado)
    {
        try
        {
            while(resultado.next())
            {
                int diaEvento = resultado.getInt("DAY(data_evento)");
                
                //a primeira linha é a partir do dia que começa o mês
                linha = 0;
                for(coluna = diaDaSemana; coluna < 7; coluna++)
                {
                    eventoParaTabela(linha, coluna, diaEvento, resultado);
                }
                
                //essas três linhas estão no mês
                for(linha = 1; linha < 4; linha++)
                {
                    for(coluna = 0; coluna < 7; coluna++)
                    {
                        eventoParaTabela(linha, coluna, diaEvento, resultado);
                    }
                }
                
                //nessas linhas quando acaba o mês pode parar
                for(linha = 4; linha < 6; linha++)
                {
                    for(coluna = 0; coluna < 7; coluna++)
                    { 
                        if(isDataNoMes(linha, coluna))
                        {
                            eventoParaTabela(linha, coluna, diaEvento, resultado);
                        } 
                        else
                        {
                            break;
                        } 
                    }
                }
                
                //pra agendar tem que estar na mesma data e estar ativo e não pode haver duplicatas por chamar o método mais de uma vez
                if((resultado.getInt("YEAR(data_evento)") == ano)&&(resultado.getInt("MONTH(data_evento)") == mes)&&(resultado.getInt("DAY(data_evento)") == dia)&&(resultado.getString("status_ativo").equals("A"))&&(isNotDuplicata))
                {
                    EventoAgendado evento = new EventoAgendado(this, resultado, ano, mes, dia);
                }
            }
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        //após chamar uma vez não há risco de haver duplicata
        isNotDuplicata = true;
    }
    
    /**Esse método existe por questões de organização*/
    private void eventoParaTabela(int linha, int coluna, int diaEvento, ResultSet resultado) throws SQLException
    {
        if(diasMostrados[linha][coluna] == diaEvento)
        {
            String cor = "#606060";
            if((resultado.getString("status_ativo").equals("A"))&&(isDiaSelecionavel(diasMostrados[linha][coluna])))
            {
                switch(resultado.getInt("id_importancia"))
                {
                    case 1:
                        cor = "#002359";
                        break;
                    case 2:
                        cor = "#005D46";
                        break;
                    case 3:
                        cor = "#00561F";
                        break;
                    case 4:
                        cor = "#543500";
                        break;
                    case 5:
                        cor = "#510800";
                }
            }
            //pega o texto atual, remove as tags html e adiciona a nova string pra tabela
            String modelo = String.valueOf(tabelaMes.getModel().getValueAt(linha, coluna));
            modelo = modelo.substring(0, modelo.length() - 14);
            tabelaMes.getModel().setValueAt(modelo + "<p style=\"background-color: "+cor+"; color: white;text-align: center; font-size: 10px;\" >" + resultado.getString("nome") + "</p></body></html>" , linha, coluna);
        }
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
        BtAnteMes.setEnabled(ativa);
        BtProxMes.setEnabled(ativa);
        BtAnteAno.setEnabled(ativa);
        BtProxAno.setEnabled(ativa);
        
        //muda o estado do menu
        MSistema.setEnabled(ativa);
        MAdicionar.setEnabled(ativa);
        MConfiguracoes.setEnabled(ativa);
        MRelatorio.setEnabled(ativa);
        MSobre.setEnabled(ativa);
    }
    
    /**Atualiza os textos da tela, já que esta não é recriada ao voltar da tela de configurações*/
    private void atualizaTexto()
    {
        MSistema.setText(Executavel.getProp("Sistema"));
        MILogout.setText(Executavel.getProp("Deslogar"));
        MISair.setText(Executavel.getProp("SairSistema"));
        MAdicionar.setText(Executavel.getProp("Adicionar"));
        MIEvento.setText(Executavel.getProp("Evento"));
        MIUsuario.setText(Executavel.getProp("Usuario"));
        MConfiguracoes.setText(Executavel.getProp("Config"));
        MRelatorio.setText(Executavel.getProp("RelatorioMes"));
        MSobre.setText(Executavel.getProp("Sobre"));
        btNovo.setText(Executavel.getProp("NovoEvento"));
        
        for(coluna = 0; coluna < 7; coluna += 1)
        {
            tabelaMes.getTableHeader().getColumnModel().getColumn(coluna).setHeaderValue(Executavel.getProp("D" + (coluna+1)));
        }
        tabelaMes.repaint();
    }
    
    public int getAno()
    {
        return ano;
    }
    
    public int getMes()
    {
        return mes;
    }
    
    public int getDia()
    {
        return dia;
    }
    
    public int getHora()
    {
        return hora;
    }
    
    public int getMinuto()
    {
        return minuto;
    }
    
    int getId()
    {
        return idUsuario;
    }
    
    public void atualizaSair()
    {
        sair = Integer.parseInt(new ArquivoConfiguracoes().getPropriedades().getProperty("fechar"));
    }
    
    public void novoEvento()
    {
        adicionaEvento(conecta.getUltimoEvento());
    }
    
    /**Vai questionar se é pra fechar ou não*/
    public void sairSistema()
    {
        Object[] opcoes = {Executavel.getProp("Sim"),Executavel.getProp("Nao")};
        if(JOptionPane.showOptionDialog(this, Executavel.getProp("QuerSair") + "?", this.getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]) == 0)
        {
            this.dispose(); 
            //sai do sistema
            System.exit(0);
        }
    }
    
    protected void adicionarEvento(int diaUsuario, int mesUsuario, int anoUsuario)
    {
        cadastro = new CadastroEvento(this ,idUsuario, ano, mes, dia, hora, minuto, diaUsuario, mesUsuario, anoUsuario);
    }
    
    protected void adicionarRelatorio(int dia)
    {
        RelatorioDia relatorio = new RelatorioDia(principal, anoUsuario, mesUsuario, dia, idUsuario);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtAnteMes = new javax.swing.JButton();
        BtProxAno = new javax.swing.JButton();
        BtAnteAno = new javax.swing.JButton();
        BtProxMes = new javax.swing.JButton();
        LMes = new javax.swing.JLabel();
        LAno = new javax.swing.JLabel();
        SPTabela = new javax.swing.JScrollPane();
        tabelaMes = new javax.swing.JTable();
        btNovo = new javax.swing.JButton();
        barraMenu = new javax.swing.JMenuBar();
        MSistema = new javax.swing.JMenu();
        MILogout = new javax.swing.JMenuItem();
        MISair = new javax.swing.JMenuItem();
        MAdicionar = new javax.swing.JMenu();
        MIEvento = new javax.swing.JMenuItem();
        MIUsuario = new javax.swing.JMenuItem();
        MConfiguracoes = new javax.swing.JMenu();
        MRelatorio = new javax.swing.JMenu();
        MSobre = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setName("framePrincipal"); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        BtAnteMes.setText("<");
        BtAnteMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAnteMesActionPerformed(evt);
            }
        });

        BtProxAno.setText(">");
        BtProxAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtProxAnoActionPerformed(evt);
            }
        });

        BtAnteAno.setText("<");
        BtAnteAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAnteAnoActionPerformed(evt);
            }
        });

        BtProxMes.setText(">");
        BtProxMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtProxMesActionPerformed(evt);
            }
        });

        LMes.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        LMes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        LAno.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        LAno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        SPTabela.setBorder(null);
        SPTabela.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SPTabela.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SPTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SPTabela.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        tabelaMes.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        tabelaMes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaMes.setAutoscrolls(false);
        tabelaMes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabelaMes.setDoubleBuffered(true);
        tabelaMes.setRowSelectionAllowed(false);
        tabelaMes.getTableHeader().setResizingAllowed(false);
        tabelaMes.getTableHeader().setReorderingAllowed(false);
        tabelaMes.setUpdateSelectionOnSort(false);
        tabelaMes.setVerifyInputWhenFocusTarget(false);
        tabelaMes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelaMesMousePressed(evt);
            }
        });
        SPTabela.setViewportView(tabelaMes);
        if (tabelaMes.getColumnModel().getColumnCount() > 0) {
            tabelaMes.getColumnModel().getColumn(0).setResizable(false);
            tabelaMes.getColumnModel().getColumn(1).setResizable(false);
            tabelaMes.getColumnModel().getColumn(2).setResizable(false);
            tabelaMes.getColumnModel().getColumn(3).setResizable(false);
            tabelaMes.getColumnModel().getColumn(4).setResizable(false);
            tabelaMes.getColumnModel().getColumn(5).setResizable(false);
            tabelaMes.getColumnModel().getColumn(6).setResizable(false);
        }

        btNovo.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        btNovo.setText("Novo");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        MSistema.setText("Sistema");

        MILogout.setText("Deslogar");
        MILogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MILogoutActionPerformed(evt);
            }
        });
        MSistema.add(MILogout);

        MISair.setText("Sair do sistema");
        MISair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MISairActionPerformed(evt);
            }
        });
        MSistema.add(MISair);

        barraMenu.add(MSistema);

        MAdicionar.setText("Adicionar");

        MIEvento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        MIEvento.setText("Evento");
        MIEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIEventoActionPerformed(evt);
            }
        });
        MAdicionar.add(MIEvento);

        MIUsuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        MIUsuario.setText("Usuario");
        MIUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MIUsuarioActionPerformed(evt);
            }
        });
        MAdicionar.add(MIUsuario);

        barraMenu.add(MAdicionar);

        MConfiguracoes.setText("Configurações");
        MConfiguracoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MConfiguracoesMouseClicked(evt);
            }
        });
        barraMenu.add(MConfiguracoes);

        MRelatorio.setText("Relatório");
        MRelatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MRelatorioMouseClicked(evt);
            }
        });
        barraMenu.add(MRelatorio);

        MSobre.setText("Sobre");
        MSobre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MSobreMouseClicked(evt);
            }
        });
        barraMenu.add(MSobre);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtAnteMes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LMes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtProxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtAnteAno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LAno, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtProxAno, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SPTabela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BtAnteAno, BtAnteMes, BtProxAno, BtProxMes});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(BtAnteMes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtProxAno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtAnteAno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtProxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LMes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LAno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SPTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //+1 ano
    private void BtProxAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtProxAnoActionPerformed
        atualizaAno(+1);
    }//GEN-LAST:event_BtProxAnoActionPerformed

    //-1 ano
    private void BtAnteAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAnteAnoActionPerformed
        atualizaAno(-1);
    }//GEN-LAST:event_BtAnteAnoActionPerformed

    //+1 mês
    private void BtProxMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtProxMesActionPerformed
        atualizaMes(+1);
    }//GEN-LAST:event_BtProxMesActionPerformed

    //-1 mês
    private void BtAnteMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAnteMesActionPerformed
        atualizaMes(-1);
    }//GEN-LAST:event_BtAnteMesActionPerformed

    private void tabelaMesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMesMousePressed
        if(ativaTabela)
        {
            Point ponto = evt.getPoint();
            if((linhaTabela == tabelaMes.rowAtPoint(ponto))&&(colunaTabela == tabelaMes.columnAtPoint(ponto)))
            {
                if(isDataSelecionavel(linhaTabela, colunaTabela))
                {
                    Intermediario intermediario = new Intermediario(principal, anoUsuario, mesUsuario, diasMostrados[tabelaMes.getSelectedRow()][tabelaMes.getSelectedColumn()]);
                    //RelatorioDia relatorio = new RelatorioDia(principal, anoUsuario, mesUsuario, diasMostrados[tabelaMes.getSelectedRow()][tabelaMes.getSelectedColumn()], idUsuario);
                }
                
                linhaTabela = -1;
                colunaTabela = -1;
            }
            else
            {
                linhaTabela = tabelaMes.rowAtPoint(ponto);
                colunaTabela = tabelaMes.columnAtPoint(ponto);
            }
        }
    }//GEN-LAST:event_tabelaMesMousePressed

    private void MIUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIUsuarioActionPerformed
        CadastroUsuario cadastro = new CadastroUsuario(this, ano);
    }//GEN-LAST:event_MIUsuarioActionPerformed
    //quando muda o tamanho da tela
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if((SPTabela.getHeight() - tabelaMes.getTableHeader().getHeight() ) / 6 > 0)
        {
            tabelaMes.setRowHeight((SPTabela.getHeight() - tabelaMes.getTableHeader().getHeight()) / 6);
        }
        else
        {
            tabelaMes.setRowHeight(1);
        }
    }//GEN-LAST:event_formComponentResized
    //adiciona um cadastroevento
    private void MIEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MIEventoActionPerformed
        cadastro = new CadastroEvento(this ,idUsuario, ano, mes, dia, hora, minuto);
    }//GEN-LAST:event_MIEventoActionPerformed
    /**Quando a tela ganha foco pode ser que os dados tenham se alterado*/
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
    }//GEN-LAST:event_formWindowGainedFocus

    /**Nova tela de configurações*/
    private void MConfiguracoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MConfiguracoesMouseClicked
        if(ativaTabela)
        {
            Configuracoes configuracoes = new Configuracoes(this);
        }
    }//GEN-LAST:event_MConfiguracoesMouseClicked

    /**Nova tela de relatorio*/
    private void MRelatorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MRelatorioMouseClicked
        if(ativaTabela)
        {
            RelatorioMes relatorio = new RelatorioMes(this, anoUsuario, mesUsuario, idUsuario, ano, mes);
        }
    }//GEN-LAST:event_MRelatorioMouseClicked

    private void MISairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MISairActionPerformed
        sairSistema();
    }//GEN-LAST:event_MISairActionPerformed

    private void MSobreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MSobreMouseClicked
        if(ativaTabela)
        {
            Sobre sobre = new Sobre(this);
        }
    }//GEN-LAST:event_MSobreMouseClicked

    private void MILogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MILogoutActionPerformed
        Object[] opcoes = {Executavel.getProp("Sim"),Executavel.getProp("Nao")};
        if(JOptionPane.showOptionDialog(this, Executavel.getProp("QuerDeslogar") + "?", this.getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]) == 0)
        {
            this.dispose();
            Login login = new Login();
        }
    }//GEN-LAST:event_MILogoutActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        switch(sair)
        {
            case 0:
                sairSistema();
                break;
            case 1:
                //obtem uma única instância do objeto SystemTray
                SystemTray bandeja = SystemTray.getSystemTray();

                //ícone da notificação
                Image icone = Toolkit.getDefaultToolkit().createImage(this.getClass().getClassLoader().getResource(Executavel.ICONE));
                TrayIcon notificacao = new TrayIcon(icone, "icone");
                notificacao.setImageAutoSize(true);
                notificacao.setToolTip("Precise Schedule");

                //tenta adicionar o objeto de notificação à "bandeja" do sistema
                try
                {
                    bandeja.add(notificacao);
                }
                catch (AWTException ex)
                {
                    Executavel.erro(ex);
                }

                setVisible(false);

                notificacao.addActionListener((ActionEvent e) -> {
                    bandeja.remove(notificacao);
                    setVisible(true);
                });
        }
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //tenta fechar a conexão com o banco
        conecta.fecha();
    }//GEN-LAST:event_formWindowClosed

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        cadastro = new CadastroEvento(this ,idUsuario, ano, mes, dia, hora, minuto);        
    }//GEN-LAST:event_btNovoActionPerformed
    /**@param ano o ano em questão
     * @param mes o mês em questão
     * @return a quantidade de dias do mês(28, 29, 30 ou 31)*/
    public static int tamanhoMes(int ano, int mes)
    {
        switch(mes)
        {
            //sempre 31
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            //sempre 30
            case 4: case 6: case 9: case 11:
                return 30;
            //entra as regras do ano bissexto
            case 2:
                if((ano % 400 == 0)||((ano % 4 == 0)&&(ano % 100 != 0)))
                {
                    return 29;
                }
                else
                {
                    return 28;
                }
        }
        return 0;
    }

    /**Dá estilo à tabela*/
    public class Estiliza extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
        {
            //pega o componente
            Component c = super.getTableCellRendererComponent(table, value,isSelected, hasFocus,row, column);
            //posiciona ele
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(TOP);
            //e o colore
            if(isDataSelecionavel(row, column))
            {
                c.setBackground(Color.decode("#E5E5FF"));
                c.setForeground(Color.decode("#526272"));
            }
            else
            {
                c.setBackground(Color.decode("#D8D8D8"));
                c.setForeground(Color.decode("#7F7F7F"));
            }
            return c;
        }
    }
    
    /**vai atualizar os segundos, minutos e horas*/
    private class AtualizaTempo extends Thread
    {
        @Override
        public void run()
        {
            minuto += 1;

            if(minuto >= 60)
            {
                minuto = 0;
                hora += 1;

                //atualiza a tela de cadastro

                if(hora >= 24)
                {
                    hora = 0;
                    dia += 1;

                    if(dia  > tamanhoMes(ano, mes))
                    {
                        dia = 1;
                        mes += 1;
                        
                        if(mes > 12)
                        {
                            mes = 1;
                            ano += 1;
                        }
                        //atualiza a tabela e também agenda os eventos do dia, pois os eventos são agendados dia a dia
                        atualizaAno(0);
                        //atualiza a tela de cadastro também
                        try
                        {
                            if((cadastro.isVisible())||(cadastro.isValid()))
                            {
                                cadastro.atualiza(ano, mes, dia, hora, minuto);
                            }
                        }
                        //exceção lançada quando o cadastro ainda não foi instanciado
                        catch(NullPointerException ex) { }
                    }
                }
            }

            //atualiza a tela de cadastro caso esteja visível ou válido
            try
            {
                if((cadastro.isVisible())||(cadastro.isValid()))
                {
                    cadastro.atualiza(hora, minuto);
                }
            }
            //exceção lançada quando o cadastro ainda não foi instanciado
            catch(java.lang.NullPointerException ex) { }
        }
    }
    
    private class EnterTabela extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(isDataSelecionavel(tabelaMes.getSelectedRow(), tabelaMes.getSelectedColumn()))
            {
                Intermediario intermediario = new Intermediario(principal, anoUsuario, mesUsuario, diasMostrados[tabelaMes.getSelectedRow()][tabelaMes.getSelectedColumn()]);
                //RelatorioDia relatorio = new RelatorioDia(principal, anoUsuario, mesUsuario, diasMostrados[tabelaMes.getSelectedRow()][tabelaMes.getSelectedColumn()], idUsuario);
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtAnteAno;
    private javax.swing.JButton BtAnteMes;
    private javax.swing.JButton BtProxAno;
    private javax.swing.JButton BtProxMes;
    private javax.swing.JLabel LAno;
    private javax.swing.JLabel LMes;
    private javax.swing.JMenu MAdicionar;
    private javax.swing.JMenu MConfiguracoes;
    private javax.swing.JMenuItem MIEvento;
    private javax.swing.JMenuItem MILogout;
    private javax.swing.JMenuItem MISair;
    private javax.swing.JMenuItem MIUsuario;
    private javax.swing.JMenu MRelatorio;
    private javax.swing.JMenu MSistema;
    private javax.swing.JMenu MSobre;
    private javax.swing.JScrollPane SPTabela;
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton btNovo;
    private javax.swing.JTable tabelaMes;
    // End of variables declaration//GEN-END:variables
}