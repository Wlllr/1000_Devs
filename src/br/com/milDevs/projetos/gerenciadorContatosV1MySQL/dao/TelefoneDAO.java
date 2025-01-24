package br.com.milDevs.projetos.gerenciadorContatosV1MySQL.dao;

import br.com.milDevs.projetos.gerenciadorContatosV1MySQL.Telefone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelefoneDAO {
    public static Connection conexao = null;

    public static ArrayList<Telefone> consultarPorId(int id) throws SQLException {
        ArrayList<Telefone> telefones = new ArrayList<>();

        String sql = "SELECT * FROM telefones WHERE id_pessoa = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                telefones.add(new Telefone(
                        resultado.getString("ddd"),
                        resultado.getString("numero"),
                        Telefone.Tipo.valueOf(resultado.getString("tipo"))));
            }
            return telefones;
        }
    }

    public static void inserir(int id, ArrayList<Telefone> telefones) throws SQLException{
        String sql = "INSERT INTO telefones (id_pessoa, ddd, numero, tipo) VALUES (?, ?, ?, ?)";

        for (Telefone cadaTelefone : telefones) {
            try (PreparedStatement comando = conexao.prepareStatement(sql)) {
                comando.setInt(1, id);
                comando.setString(2, cadaTelefone.getDdd());
                comando.setString(3, cadaTelefone.getNumero());
                comando.setString(4, cadaTelefone.getTipo().toString());
                comando.executeUpdate();
            }
        }
    }

    public static void excluirPorId(int id) throws SQLException{
        String sql = "DELETE FROM telefones WHERE id_pessoa = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            comando.executeUpdate();
        }
    }
}
