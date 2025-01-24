package br.com.milDevs.projetos.gerenciadorContatosV1MySQL;

import br.com.milDevs.projetos.gerenciadorContatosV1MySQL.dao.PessoaDAO;
import br.com.milDevs.projetos.gerenciadorContatosV1MySQL.dao.TelefoneDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class App {

    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Connection conexao = Conexao.getConexao();

            PessoaDAO.conexao = conexao;
            TelefoneDAO.conexao = conexao;

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //guarda a opcao selecionada pelo usuario no menu
        int opcao;

        do {
            limparTela();
            //obtem a opcao desejada pelo usuario
            opcao = obterEscolhaMenu();

            //executa a funcionalidade conforme escolhido pelo usuario
            processarEscolhaMenu(opcao);
        } while (opcao != 7);
    }

    private static void processarEscolhaMenu(int opcao){
        switch (opcao) {
            case 1:
                incluirContato();
                pausa();
                break;
            case 2:
                alterarContato();
                break;
            case 3:
                consultarContatos();
                break;
            case 4:
                consultarContatosPorId();
                pausa();
                break;
            case 5:
                consultarContatosPorNome();
                pausa();
                break;
            case 6:
                excluirContato();
                pausa();
                break;
            case 7:
                System.out.println("Saindo do sistema...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void consultarContatosPorId() {
        int id;
        Pessoa pessoa = null;

        System.out.print("Informe o id do usuario a ser exibido:  ");
        id = teclado.nextInt();
        teclado.nextLine();//limpeza do buffer

        try {
            pessoa = PessoaDAO.consultarPorID(id);
            if (pessoa != null){
                System.out.println(pessoa);
            } else {
                System.out.println("Não existe contato com este ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error ao consultador os dados no banco de dados!");
            System.out.println(e.getMessage());
        }
    }

    private static void consultarContatosPorNome() {
        String nome;
        ArrayList<Pessoa> listaContatos = new ArrayList<Pessoa>();

        System.out.print("Informe o nome ou parte do nome usuario a ser exibido:  ");
        nome = teclado.nextLine();

        try {
            listaContatos = PessoaDAO.consultarPorNome(nome);
            if (listaContatos.isEmpty()){
                System.out.println("Não existe contato com este nome.");
            } else {
                for (Pessoa cadaContato : listaContatos) {
                    System.out.println(cadaContato);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error ao consultador os dados no banco de dados!");
            System.out.println(e.getMessage());
        }
    }

    private static int obterEscolhaMenu(){
        int opcao;

        System.out.println("\n--- Menu de Gerenciamento de Contatos ---\n");

        System.out.println("1. Incluir Contato");
        System.out.println("2. Alterar Contato");
        System.out.println("3. Consultar todos Contatos");
        System.out.println("4. Consultar Contatos por ID");
        System.out.println("5. Consultar Contatos por Nome");
        System.out.println("6. Excluir Contato");
        System.out.println("7. Sair");

        System.out.print("\nEscolha uma opção: ");
        opcao = teclado.nextInt();
        teclado.nextLine(); // Limpeza buffer

        return opcao;
    }

    private static void incluirContato() {
        System.out.print("Digite o nome: ");
        String nome = teclado.nextLine();

        System.out.print("Digite o email: ");
        String email = teclado.nextLine();

        ArrayList<Telefone> telefones = obterTelefones();

        Pessoa novaPessoa = new Pessoa(nome, telefones, email);

        try {
            PessoaDAO.inserir(novaPessoa);
            System.out.println("Contato incluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao entar inserir os dados no BD. Tente novamente.");
            System.out.println(e.getMessage());
        }
    }

    private static ArrayList<Telefone> obterTelefones() {
        ArrayList<Telefone> telefones = new ArrayList<>();
        String numero, ddd, tipo;
        char resposta;

        do {
            System.out.print("Digite o ddd: ");
            ddd = teclado.nextLine();

            System.out.print("Digite o telefone: ");
            numero = teclado.nextLine();

            System.out.print("Digite o tipo [CELULAR/FIXO]: ");
            tipo = teclado.nextLine().toUpperCase();

            telefones.add(new Telefone(ddd, numero, Telefone.Tipo.valueOf(tipo)));

            System.out.println("Deseja cadastrar outro telefone [s/n]: ");
            resposta = teclado.nextLine().toLowerCase().charAt(0);

        } while (resposta == 's');

        return telefones;
    }

    private static void alterarContato() {
        System.out.print("Digite o ID do contato a ser alterado: ");
        int id = teclado.nextInt();
        teclado.nextLine(); // limpeza buffer

        limparTela();

        Pessoa pessoa = null;

        try {
            pessoa = PessoaDAO.consultarPorID(id);
            if (pessoa != null){
                System.out.println(pessoa);
            } else {
                System.out.println("Não existe contato com este ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error ao consultador os dados no banco de dados!");
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("\nDeseja realmente alterar este contato " + pessoa.getNome() + " [s/n]");
        char resposta = teclado.nextLine().toLowerCase().charAt(0);

        if (resposta == 'n') {
            System.out.println("\nAtualização cancelada.");
            return;
        }

        System.out.println("Nome atual: " + pessoa.getNome());
        System.out.print("Digite o novo nome (ou deixe em branco para manter): ");
        String nome = teclado.nextLine();
        //metodo isBlank retorna true se a string estiver vazia
        //é equivalente a fazer nome.equals("");
        if (!nome.isBlank())
            pessoa.setNome(nome);

        System.out.println("Telefone atual: " + pessoa.getTelefone());
        System.out.print("Digite o novo telefone (ou deixe em branco para manter): ");
        String telefone = teclado.nextLine();
        if (!telefone.isBlank())
            pessoa.setTelefone(telefone);

        System.out.println("Email atual: " + pessoa.getEmail());
        System.out.print("Digite o novo email (ou deixe em branco para manter): ");
        String email = teclado.nextLine();
        if (!email.isBlank())
            pessoa.setEmail(email);

        try {
            int resultado = PessoaDAO.atualizarContatoPorID(pessoa);

            String mensagem = (resultado > 0) ? "Contato alterado com sucesso!" : " Erro!";
            System.out.println(mensagem);
        } catch (SQLException e) {
            System.out.println("Erro ao entar atualizar os dados no BD. Tente novamente.");
            System.out.println(e.getMessage());
        }
        pausa();
    }

    private static void consultarContatos() {
        ArrayList<Pessoa> listaContatos = new ArrayList<Pessoa>();

        try {
            listaContatos = PessoaDAO.consultarTodosContatos();

            //metodo isEmpty verifica se a lista esta vazia
            if (listaContatos.isEmpty()) {
                System.out.println("Nenhum contato cadastrado.");
            } else {
                System.out.println("\n--- Lista de Contatos ---");
                for (Pessoa pessoa : listaContatos) {
                    System.out.println(pessoa);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultador os dados no BD.");
            System.out.println(e.getMessage());
        }
        pausa();
    }

    private static void excluirContato() {
        //obtem o id do contato;
        System.out.print("Digite o ID do contato a ser excluído: ");
        int id = teclado.nextInt();
        teclado.nextLine(); // Consumir quebra de linha

        Pessoa pessoa;

        try {
            pessoa = PessoaDAO.consultarPorID(id);

            if (pessoa != null) {
                System.out.println("Contato encontrado:");
                System.out.println(pessoa);
            } else {
                System.out.println("Não existe contato com este ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar os dados no BD.");
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("\nDeseja realmente excluir este contato " + pessoa.getNome() + " [s/n]: ");
        char resposta = teclado.nextLine().toLowerCase().charAt(0);

        if (resposta == 'n') {
            System.out.println("\nExclusao cancelada.");
            return;
        }

        try {
            int resultado = PessoaDAO.excluirPorID(id);

            if ( resultado == 0) {
                System.out.println("Nenhum registro excluido pois nao existe esse ID no BD");
            } else {
                System.out.println("Registro excluido com sucesso.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir os dados no BD.");
            System.out.println(e.getMessage());
        }

    }

    private static void limparTela(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();

        } catch (IOException | InterruptedException ex) {}
    }

    private static void pausa(){
        System.out.println("\nTecle ENTER para continuar.");
        teclado.nextLine();
    }


}

