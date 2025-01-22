package br.com.milDevs.atividades.jdbc;

import java.sql.SQLException;

public class AppTester {
    public static void main(String[] args) throws SQLException {
        Gerenciador gerenciador = new Gerenciador();

        gerenciador.createConnection();

        //gerenciador.insert("Ray", "83986288924");
        //gerenciador.executeConsult();

        gerenciador.delete(9);
        //gerenciador.update(7,"Weller Pereira", "83986249714");
    }
}
