package br.com.milDevs.atividades.jdbc;
import java.sql.*;

public class Gerenciador {

    Connection conexao = null;

    // Estabelece uma conexao com o banco de dados
    public void createConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/teste";
        String usuario = "root";
        String senha = "1234567";

        conexao = DriverManager.getConnection(url, usuario, senha);
    }

    public void executeConsult() throws SQLException {

        String sql = "SELECT * FROM pessoa";
        PreparedStatement comando = conexao.prepareStatement(sql);

        ResultSet resultado = comando.executeQuery();
        while (resultado.next()) {
            int id = resultado.getInt("id");
            String nome = resultado.getString("nome");
            String telefone = resultado.getString("telefone");

            System.out.println("ID: " + id + ", Nome: " + nome + ", Telefone: " + telefone);
        }
        conexao.close();
    }

    public void insert(String nome, String telefone) throws SQLException {

        String sql = "INSERT INTO pessoa (nome, telefone) values (? , ?)";
        PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        comando.setString(1, nome);
        comando.setString(2, telefone);

        comando.executeUpdate();

        ResultSet resultado = comando.getGeneratedKeys();
        if (resultado.next()) {
            System.out.println("ID do usuario recem inserido: " + resultado.getInt(1));
        }
    }

    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM pessoa WHERE id = ?";
        PreparedStatement comando = conexao.prepareStatement(sql);

        comando.setInt(1, id);
        comando.executeUpdate();
    }

    public void update(int id, String novoNome, String novoTelefone) throws SQLException {

        String sql = "UPDATE pessoa SET nome = ?, telefone = ? WHERE id = ?";
        PreparedStatement comando = conexao.prepareStatement(sql);

        comando.setString(1, novoNome);
        comando.setString(2, novoTelefone);
        comando.setInt(3, id);
        comando.executeUpdate();
    }
}
