package br.com.milDevs.atividades.jdbc;

import java.sql.*;

import java.util.Scanner;

public class App {
    static Connection conexao = null;

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        criarConexao();
        //inserir("Carla", "3499956232");
        //deletar(4);
        //consultar();
        //update(3, "Carla Videiro", "34555555556");
        consultar(2);

        if (conexao != null)
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        teclado.close();
    }

    // Estabelece a conexao com o banco de dados
    static public void criarConexao(){
        //Parametros de conexao com o banco de dados
        String url = "jdbc:mysql://localhost:3306/teste";
        String usuario = "root";
        String senha = "@2Darksouls3";
        // Estabelecer a conexao
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void consultar(int id){
        executarConsulta(id);
    }

    static public void consultar(){
        executarConsulta(-1);
    }

    // Realiza um consulta no banco de dados atraves do id
    public static void executarConsulta(int id){
        String sql;

        if (id <= 0){
            sql = "SELECT * FROM pessoa";
        } else {
            sql = "SELECT * FROM pessoa WHERE id = ?";
        }

        try (PreparedStatement comando =  conexao.prepareStatement(sql)) {
            if (id > 0) {
                comando.setInt(1, id);
            }
            // Executar a consulta e obter os resultados
            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()){
                    id = resultado.getInt("id");
                    String nome = resultado.getString("nome");
                    String telefone = resultado.getString("telefone");

                    System.out.println("ID: " + id + ", Nome: " + nome + ", Telefone: " + telefone);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void inserir(String nome, String telefone) {
        String sql = "INSERT INTO pessoa (nome, telefone) values (?, ?)";

        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, nome);
            comando.setString(2, telefone);

            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()){
                    System.out.println("ID do usuario recem inserido: " + resultado.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletar(int id){
        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            comando.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(int id, String novoNome, String novoTelefone){
        String sql = "UPDATE pessoa SET nome = ?, telefone = ? WHERE id = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, novoNome);
            comando.setString(2, novoTelefone);
            comando.setInt(3, id);
            comando.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
