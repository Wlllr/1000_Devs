package br.com.milDevs.projetos.gerenciadorVersao2;

public class Telefone {
    public static int contador; //constante compartilhada em cada objeto Telefone

    int id;
    private String ddd;
    private String telefone;

    public Telefone(String ddd, String telefone) {
        contador++;
        id = contador;
        this.ddd = ddd;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getDdd() {
        return ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return "ID: " + id + " (" + ddd + ") " + telefone;
    }
}