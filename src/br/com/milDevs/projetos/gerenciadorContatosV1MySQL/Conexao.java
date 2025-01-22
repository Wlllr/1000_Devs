package br.com.milDevs.projetos.gerenciadorContatosV1MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/teste";
    private static final String USUARIO = "root";
    private static final String SENHA = "1234567";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
