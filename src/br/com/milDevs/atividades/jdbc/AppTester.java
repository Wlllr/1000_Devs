package br.com.milDevs.atividades.jdbc;

public class AppTester {
    public static void main(String[] args) {
        Gerenciador gerenciador = new Gerenciador();

        gerenciador.createConnection();

        //gerenciador.consultar();
        //gerenciador.insert();
        //gerenciador.delete();
        //gerenciador.update();
    }
}
