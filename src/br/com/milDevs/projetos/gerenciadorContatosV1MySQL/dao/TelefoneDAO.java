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
                        Telefone.Tipo.CELULAR));
            }
            return telefones;
        }
    }
}
