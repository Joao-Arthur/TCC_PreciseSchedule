package PreciseSchedule;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBanco
{
    //variáveis pra definir qual cor está sendo puxada
    public static final int R = 0, G = 1, B = 2;
    
    //objetos da classe
    private String comando;
    private String campo;
    
    //objetos da classe
    private PreparedStatement consulta;
    private ResultSet resultado;
    private Connection conexao;
    
    /**Faz a conexão com o banco de dados*/
    public ConexaoBanco()
    {
        try
        {
            conexao = ConexaoBanco.createConnection();
        }
        catch(MySQLNonTransientConnectionException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
    }
    
    /**Chamado pelo construtor para se conectar ao banco de dados
     * @return um objecto coneão pro construtor
     * @throws java.sql.SQLException, ou seja, joga a exceção um nível acima, para o construtor da classe*/
    private static Connection createConnection() throws SQLException
    {
        //conexão com o MySQL pelo JDBC
        String url = "jdbc:mysql://localhost:3306/Agenda";
        
        Properties propriedades = new Properties();
        propriedades.setProperty("user", "root");
        propriedades.setProperty("password", "");
        propriedades.setProperty("useSSL", "false");
        propriedades.setProperty("autoReconnect", "true");
        
        //faz a conexão de fato
        Connection conexao = DriverManager.getConnection(url, propriedades);
        
        return conexao;
    }
    
    /**@return todos os ids de importancias cadastrados no banco*/
    public ResultSet getIdImportancia()
    {
        comando = "SELECT id FROM importancia";
        return executaConsulta(comando);
    }
    
    /**@return todos os ids de categorias cadastradas no banco*/
    public ResultSet getIdCategoria()
    {
        comando = "SELECT id FROM categoria";
        return executaConsulta(comando);
    }
    
    /**@return todos os ids de categorias cadastradas no banco*/
    public ResultSet getIdFrequencia()
    {
        comando = "SELECT id FROM frequencia";
        return executaConsulta(comando);
    }
    
    /**@return o último evento cadastrado*/
    public ResultSet getUltimoEvento()
    {
        comando = "SELECT * , DAY(data_evento), MONTH(data_evento), YEAR(data_evento) FROM evento WHERE id = (SELECT MAX(id) FROM evento);";
        return executaConsulta(comando);
    }
    
    /**Pega os dados para o relatório mensal
     * @param anoUsuario
     * @param mesUsuario
     * @param tamanhoMes
     * @param idUsuario
     * @return os eventos para preencher a tabela*/
    public ResultSet getEventosParaTabelaMes(int anoUsuario, int mesUsuario, int tamanhoMes, int idUsuario)
    {
        comando = "SELECT evento.id, evento.nome, evento.data_evento, evento.hora_inicio, evento.hora_fim, id_importancia, id_frequencia, id_categoria FROM evento "
        + "WHERE evento.data_evento BETWEEN '"+ anoUsuario +"-"+ mesUsuario + "-01' AND '"+ anoUsuario +"-"+ mesUsuario + "-" + tamanhoMes + "' AND evento.id_usuario = "+idUsuario+";";
        return executaConsulta(comando);
    }
    
    /**Pega os dados para o relatório mensal
     * @param anoUsuario
     * @param mesUsuario
     * @param tamanhoMes
     * @param idCategoria
     * @param idUsuario
     * @return os eventos para preencher a tabela*/
    public ResultSet getEventosGraficoMes(int anoUsuario, int mesUsuario, int tamanhoMes, int idCategoria, int idUsuario)
    {
        comando = "SELECT id_importancia, DAY(data_evento) FROM evento WHERE data_evento BETWEEN '"+ anoUsuario +"-"+ mesUsuario + "-01' AND '"+ anoUsuario +"-"+ mesUsuario + "-" + tamanhoMes + "' AND id_usuario = " + idUsuario + " AND id_categoria = " + idCategoria + ";";
        return executaConsulta(comando);
    }
    
    /**Pega os dados para o relatório mensal
     * @param anoUsuario
     * @param mesUsuario
     * @param diaUsuario
     * @param idCategoria
     * @param idUsuario
     * @return os eventos para preencher a tabela*/
    public ResultSet getEventosGraficoDia(int anoUsuario, int mesUsuario, int diaUsuario, int idCategoria, int idUsuario)
    {
        comando = "SELECT id_importancia, DAY(data_evento) FROM evento WHERE data_evento = '"+ anoUsuario +"-"+ mesUsuario + "-" + diaUsuario + "' AND id_usuario = " + idUsuario + " AND id_categoria = " + idCategoria + ";";
        return executaConsulta(comando);
    }
    
    /**Pega os dados para o relatório mensal
     * @param anoUsuario
     * @param mesUsuario
     * @param diaUsuario
     * @param idUsuario
     * @return os eventos para preencher a tabela*/
    public ResultSet getEventosParaTabelaDia(int anoUsuario, int mesUsuario, int diaUsuario, int idUsuario)
    {
        comando = "SELECT id, nome, data_evento, hora_inicio, hora_fim, id_importancia, id_frequencia, id_categoria FROM evento "
        + "WHERE evento.data_evento = '"+ anoUsuario +"-"+ mesUsuario + "-" + diaUsuario + "' AND evento.id_usuario = "+idUsuario+";";
        return executaConsulta(comando);
    }
    
    /**Pega as categorias pro relatorio diário
     * @param anoUsuario
     * @param mesUsuario
     * @param diaUsuario
     * @param idUsuario
     @return o nome e id das categorias que tem registro nesse dia específico*/
    public ResultSet getCategoriaDia(int anoUsuario, int mesUsuario, int diaUsuario, int idUsuario)
    {
        comando = "SELECT DISTINCT categoria.id FROM categoria INNER JOIN evento ON (categoria.id = evento.id_categoria) "
        + "WHERE evento.data_evento = '" + anoUsuario + "-" + mesUsuario + "-" + diaUsuario + "' AND evento.id_usuario = " + idUsuario + ";";
        return executaConsulta(comando);
    }
    
    /**Pega as categorias pro relatorio mensal
     * @param anoUsuario
     * @param mesUsuario
     * @param tamanhoMes
     * @param idUsuario
     @return o nome e id das categorias que tem registro nesse mês específico*/
    public ResultSet getCategoriaMes(int anoUsuario, int mesUsuario, int tamanhoMes, int idUsuario)
    {
        comando = "SELECT DISTINCT categoria.id FROM categoria INNER JOIN evento ON (categoria.id = evento.id_categoria) "
        + "WHERE evento.data_evento BETWEEN '" + anoUsuario + "-" + mesUsuario + "-01' AND '" + anoUsuario + "-" + mesUsuario + "-" + tamanhoMes + "' AND evento.id_usuario = " + idUsuario + ";";
        return executaConsulta(comando);
    }
    
    /**Pega os dados do insert para editar
     * @param idEvento
     * @return todos os dados que foram cadastrados*/
    public ResultSet getEvento(int idEvento)
    {
        comando = "SELECT nome, YEAR(data_evento), MONTH(data_evento), DAY(data_evento), HOUR(hora_inicio), MINUTE(hora_inicio), HOUR(hora_fim), MINUTE(hora_fim), id_usuario, id_importancia, id_frequencia, id_categoria FROM evento WHERE id = " + idEvento + ";";
        return executaConsulta(comando);
    }
    
    /**Pega os dados dos eventos do mês pra tela principal
     * @param anoUsuario ano do evento
     * @param mesUsuario mês do evento
     * @param idUsuario id do usuário logado
     * @return nome, dia, importância e status do evento*/
    public ResultSet getEventos(int anoUsuario, int mesUsuario, int idUsuario)
    {
        comando = "SELECT *, DAY(data_evento), MONTH(data_evento), YEAR(data_evento) "
        + "FROM evento WHERE data_evento BETWEEN '" + anoUsuario + "-" + mesUsuario + "-01' AND '" + anoUsuario + "-" + mesUsuario + "-31' AND id_usuario = " + idUsuario+";";
        return executaConsulta(comando);
    }
    
    /**Pega as importancias para o relatorio mensal
     * @param idCategoria 
     * @param anoUsuario
     * @param mesUsuario
     * @param idUsuario
     * @return o id de importancias com registro em eventos*/
    public ResultSet getImportanciaMes(int idCategoria, int anoUsuario, int mesUsuario,  int idUsuario)
    {
        comando = "SELECT DISTINCT importancia.id FROM importancia INNER JOIN evento ON(importancia.id = evento.id_importancia) WHERE evento.data_evento BETWEEN '"+ anoUsuario + "-" + mesUsuario +"-01' AND '"+ anoUsuario + "-" + mesUsuario +"-31' AND evento.id_categoria = " + idCategoria + "  AND evento.id_usuario = " + idUsuario + " ORDER BY importancia.id DESC;";
        return executaConsulta(comando);
    }
    
    /**Pega as importancias para o relatorio diário
     * @param idCategoria
     * @param anoUsuario
     * @param mesUsuario
     * @param diaUsuario
     * @param idUsuario
     * @return o id de importancias com registro em eventos*/
    public ResultSet getImportanciaDia(int idCategoria, int anoUsuario, int mesUsuario, int diaUsuario, int idUsuario)
    {
        comando = "SELECT DISTINCT importancia.id FROM importancia INNER JOIN evento ON(importancia.id = evento.id_importancia) WHERE evento.data_evento = '"+ anoUsuario + "-" + mesUsuario + "-" + diaUsuario + "' AND evento.id_categoria = " + idCategoria + "  AND evento.id_usuario = " + idUsuario + " ORDER BY importancia.id DESC;";
        return executaConsulta(comando);
    }
    
    /**Conta a quantidade de eventos do dia por categoria e importancia
     * @param anoUsuario
     * @param mesUsuario
     * @param dia
     * @param idUsuario
     * @param idCategoria
     * @param idImportancia
     * @return o valor int de inserts*/
    public int getCountEvento(int anoUsuario, int mesUsuario, int dia, int idUsuario, int idCategoria, int idImportancia)
    {
        comando = "SELECT COUNT(id) FROM evento WHERE data_evento = '"+anoUsuario+"-"+mesUsuario+"-"+dia+"' and id_usuario = "+idUsuario+" and id_categoria = "+idCategoria+" and id_importancia = "+idImportancia+";";
        campo = "COUNT(id)";

        return executaConsulta(comando, campo);
    }
    
    /**Conta a quantidade de eventos do mês
     * @param anoUsuario
     * @param mesUsuario
     * @param idUsuario
     * @return o valor int de inserts*/
    public int getCountEventoMes(int anoUsuario, int mesUsuario, int idUsuario)
    {
        comando = "SELECT COUNT(id) FROM evento WHERE data_evento <= '" + anoUsuario + "-" + mesUsuario + "-31' AND data_evento >= '" + anoUsuario + "-" + mesUsuario + "-01' AND id_usuario = " + idUsuario + ";";
        campo = "COUNT(id)";
        
        return executaConsulta(comando, campo);
    }
    
    public boolean atualizaEvento(int id, String nome, String data, String inicio, String fim, int importancia, int frequencia, int categoria)
    {
        comando = "UPDATE evento set nome = '" + nome + "', data_evento = '" + data + "', hora_inicio = '" + inicio + "', hora_fim = '" + fim + "', id_importancia = '" + importancia + "', id_frequencia = '" + frequencia + "', id_categoria = '" + categoria + "' WHERE id = " + id + ";";
        return executaDML(comando);
    }
    
    public boolean deletaEvento(int id)
    {
        comando = "DELETE FROM evento WHERE id = " + id + ";";
        return executaDML(comando);
    }
    
    public int getCountEventoDia(int anoUsuario, int mesUsuario, int diaUsuario, int idUsuario)
    {
        comando = "SELECT COUNT(id) FROM evento WHERE data_evento = '" + anoUsuario + "-" + mesUsuario + "-" + diaUsuario + "' AND id_usuario = " + idUsuario + ";";
        campo = "COUNT(id)";
        
        return executaConsulta(comando, campo);
    }
    
    public int getCountImportancia()
    {
        comando = "SELECT COUNT(id) FROM importancia";
        campo = "COUNT(id)";
        
        return executaConsulta(comando, campo);
    }
    
    public int getCor(int cor, int idCategoria)
    {
        String corFinal;
        
        switch(cor)
        {
            case R:
                corFinal = "corR";
                break;
            case G:
                corFinal = "corG";
                break;
            case B:
                corFinal = "corB";
                break;
            default: return 0;
        }
        
        comando = "SELECT " + corFinal + " FROM categoria WHERE id = " + idCategoria + ";";
        
        return executaConsulta(comando, corFinal);
    }
    
    /**Verifica se os dados do usuário correspondem a uma conta
     * @param login campo de login
     * @param senha campo da senha
     * @return o id do usuário se encontra-lo ou 0 caso não haja nenhum
     */
    public int login(String login, String senha)
    {
        comando = "SELECT id FROM usuario WHERE login = '" + login + "' AND senha = '" + senha + "';";
        campo = "id";
        
        return executaConsulta(comando, campo);
    }
    
    /**Verifica se os dados do usuário correspondem a uma conta
     * @param nome nome do usuário
     * @param email email do usuário
     * @param login login, que é pk da tabela, porém precisa dos outros dados pra comprovar que é o usuário mesmo
     * @return  o id do usuário se encontra-lo ou 0 caso não haja nenhum*/
    public int verificaConta(String nome, String email, String login)
    {
        comando = "SELECT id FROM usuario WHERE nome = '" + nome + "' AND email = '" + email + "' AND login = '" + login + "';";
        campo = "id";
        
        return executaConsulta(comando, campo);
    }
    
    /**Atualiza a senha no banco de dados
     * @param login login do usuário, usado para localizar o registro
     * @param novaSenha o novo valor da senha
     * @return se atualizou a senha*/
    public boolean mudaSenha(String login, String novaSenha)
    {
        comando = "UPDATE usuario SET senha = '" + novaSenha + "' WHERE login = '" + login + "';";
        return executaDML(comando);
    }
    
    /**Cadastra o usuario
     * @param nome campo do nome
     * @param data campo da data
     * @param email campo do email
     * @param login campo do login
     * @param senha campo da senha
     * @return se inseriu com sucesso*/
    public boolean insereUsuario(String nome, String data, String email, String login, String senha)
    {
        comando = "INSERT INTO usuario VALUES(default, '" + nome + "', '" + data + "', '" + email + "', '" + login + "', '" + senha + "');";
        return executaDML(comando);
    }
    
    /**Cadastra o evento
     * @param nome campo do nome
     * @param data campo da data
     * @param inicio hora que começa
     * @param fim hora que termina
     * @param id id do usuário
     * @param frequencia id da frequencia
     * @param importancia id da importancia
     * @param categoria id da categoria
     * @return se inseriu com sucesso*/
    public boolean insereEvento(String nome, String data, String inicio, String fim, int id, int importancia, int frequencia, int categoria)
    {   
        comando = "INSERT INTO evento VALUES(default, '" + nome + "', '" + data + "', '" + inicio + "', '" + fim + "', default, " + id + ", " + importancia + ", " + frequencia + ", " + categoria + ");";
        return executaDML(comando);
    }
    
    /**Atualiza a data do evento
     * @param id
     * @param tempoAdicionar
     * @return */
    public boolean setNovaData(String id, int tempoAdicionar)
    {
        String intervalo;
        String data;
        
        switch(tempoAdicionar)
        {
            case 2:
                intervalo = "1 DAY";
                break;
            case 3:
                intervalo = "7 DAY";
                break;
            case 4:
                intervalo = "1 MONTH";
                break;
            case 5:
                intervalo = "1 YEAR";
                break;
            default: intervalo = "0 DAY";
        }
        comando = "SELECT data_evento FROM evento WHERE id = "+id+";";
        campo = "data_evento";
        data = executaConsulta(comando, campo, 0);
        
        comando = "UPDATE evento SET data_evento = ADDDATE('"+ data + "', INTERVAL " + intervalo + " ) WHERE id = " + id+";";
        return executaDML(comando);
    }
    
    /**Desativa o evento que já ocorreu
     * @param idEvento*/
    public void setEventoInativo(String idEvento)
    {
        comando = "UPDATE evento SET status_ativo = 'I' WHERE id = "+idEvento+";";
        executaDML(comando);
    }
    
    /**Deleta os eventos antigos, que não são mais válidas
     * @param ano ano atual
     * @param mes mes atual
     * @param dia dia atual*/
    public void deletaAntigos(int ano, int mes, int dia)
    {
        comando = "DELETE FROM evento WHERE data_evento < ADDDATE('" + ano + "-" + mes + "-" + dia + "', INTERVAL -5 YEAR);";
        executaDML(comando);
    }
    
    /**Método que executa as querys/consultas de dados no banco*/
    private int executaConsulta(String comando, String campo)
    {
        try
        {
            consulta = conexao.prepareStatement(comando);
            resultado = consulta.executeQuery();
            
            if(resultado.next())
            {
                return resultado.getInt(campo);
            }
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        
        return 0;
    }
    
    /**Método que executa as querys/consultas de dados no banco*/
    private String executaConsulta(String comando, String campo, int ignorado)
    {
        try
        {
            consulta = conexao.prepareStatement(comando);
            resultado = consulta.executeQuery();
            
            if(resultado.next())
            {
                return resultado.getString(campo);
            }
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        
        return null;
    }
    
    /**Método que executa as querys/consultas de dados no banco*/
    private ResultSet executaConsulta(String comando)
    {
        try
        {
            consulta = conexao.prepareStatement(comando);
            resultado = consulta.executeQuery();
            
            return resultado;
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        
        return null;
    }

    /**Método que executa update/insert/delete no dados no banco*/
    private boolean executaDML(String comando)
    {
        try
        {
            consulta = conexao.prepareStatement(comando);
            consulta.execute();
            
            return (consulta.getUpdateCount() == 1);
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
        
        return false;
    }
    
    /**Tenta encerrar a conexão com o banco de dados*/
    public void fecha()
    {
        try
        {
            conexao.close();
        }
        catch(SQLException ex)
        {
            Executavel.erroBancoDeDados(ex);
        }
    }
}