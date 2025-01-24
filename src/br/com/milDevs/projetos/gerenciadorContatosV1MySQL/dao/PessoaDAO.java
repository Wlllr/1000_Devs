package br.com.milDevs.projetos.gerenciadorContatosV1MySQL.dao;
import br.com.milDevs.projetos.gerenciadorContatosV1MySQL.Pessoa;

import java.sql.*;
import java.util.ArrayList;

public class PessoaDAO {
    public static Connection conexao = null;

    public static void inserir(Pessoa pessoa) throws SQLException{
        String sql = "INSERT INTO pessoa (nome, email) VALUES (?, ?)";

        //try with resources
        try(PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, pessoa.getNome());
            comando.setString(2, pessoa.getEmail());
            //comando.setString(3, pessoa.getTelefone());

            conexao.setAutoCommit(false);

            int qtdLinhas = comando.executeUpdate();

            if (qtdLinhas > 0) {
                try (ResultSet idGerado = comando.getGeneratedKeys()) {
                    if (idGerado.next()) {
                        TelefoneDAO.inserir(idGerado.getInt(1), pessoa.getTelefones());
                    }

                    conexao.commit();
                } catch (SQLException e) {
                    conexao.rollback();
                    throw e;
                } finally {
                    conexao.setAutoCommit(true);
                }
            }
        }
    }

    public static ArrayList<Pessoa> consultarTodosContatos() throws SQLException{
        ArrayList<Pessoa> lista = new ArrayList<Pessoa>();

        String sql = "SELECT * FROM pessoa";

        try (Statement comando = conexao.createStatement();
             ResultSet resultado = comando.executeQuery(sql);) {

            while(resultado.next()){
                lista.add(new Pessoa(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        TelefoneDAO.consultarPorId(resultado.getInt("id")),
                        resultado.getString("email")));
            }
        }
        return lista;
    }

    public static Pessoa consultarPorID(int id) throws SQLException{
        Pessoa pessoa = null;

        String sql = "SELECT * FROM pessoa WHERE id = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)){
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()){
                pessoa = new Pessoa(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        TelefoneDAO.consultarPorId(resultado.getInt("id")),
                        resultado.getString("email")
                );
            }
        }

        return pessoa;
    }

    public static ArrayList<Pessoa> consultarPorNome(String nome) throws SQLException {
        ArrayList<Pessoa> lista = new ArrayList<>();

        String sql = "SELECT * FROM pessoa WHERE nome LIKE ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, "%" + nome + "%");

            try (ResultSet resultado = comando.executeQuery()){
                while (resultado.next()) {
                    lista.add(new Pessoa(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            TelefoneDAO.consultarPorId(resultado.getInt("id")),
                            resultado.getString("email")
                    ));
                }
            }
        }
        return lista;
    }

    public static int excluirPorID(int id) throws SQLException{
        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            conexao.setAutoCommit(false);
            TelefoneDAO.excluirPorId(id);

            comando.setInt(1, id);
            int resultado = comando.executeUpdate();

            conexao.commit();

            return resultado;
        } catch (Exception e) {
            conexao.rollback();
            throw e;
        } finally {
            conexao.setAutoCommit(true);
        }
    }

    public static int atualizarContatoPorID(Pessoa pessoa) throws SQLException{
        String sql = "UPDATE pessoa SET nome = ?, email = ?, telefone = ? WHERE id = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, pessoa.getNome());
            comando.setString(2, pessoa.getEmail());
            comando.setString(3, pessoa.getTelefone());
            comando.setInt(4, pessoa.getId());

            int resultado = comando.executeUpdate();
            return resultado;
        }
    }
}
