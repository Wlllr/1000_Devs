package br.com.milDevs.projetos.gerenciadorContatosV1MySQL;

import br.com.milDevs.projetos.gerenciadorContatosV1MySQL.dao.PessoaDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private static ArrayList<Pessoa> listaContatos = new ArrayList<Pessoa>();
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            /*PessoaDAO.getConexao();*/
            PessoaDAO.conexao = Conexao.getConexao();
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
        ArrayList<Pessoa> contatos = new ArrayList<Pessoa>();

        System.out.print("Informe o nome ou parte do nome usuario a ser exibido:  ");
        nome = teclado.nextLine();

        try {
            contatos = PessoaDAO.consultarPorNome(nome);
            if (contatos.isEmpty()){
                System.out.println("Não existe contato com este nome.");
            } else {
                for (Pessoa cadaContato : contatos) {
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

        System.out.print("Digite o telefone: ");
        String telefone = teclado.nextLine();

        System.out.print("Digite o email: ");
        String email = teclado.nextLine();

        Pessoa novaPessoa = new Pessoa(nome, telefone, email);
        //listaContatos.add(novaPessoa);
        try {
            PessoaDAO.inserir(novaPessoa);
            System.out.println("Contato incluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao entar inserir os dados no BD. Tente novamente.");
            System.out.println(e.getMessage());
        }
    }

    private static void alterarContato() {
        System.out.print("Digite o ID do contato a ser alterado: ");
        int id = teclado.nextInt();
        teclado.nextLine(); // limpeza buffer

        limparTela();

        //busca a pessoa especificada pelo id
        //Pessoa pessoa = encontrarContatoPorId(id);
        Pessoa pessoa = null;

        if (pessoa != null) {

            System.out.print("Digite o novo nome (ou deixe em branco para manter): ");
            String nome = teclado.nextLine();
            //metodo isBlank retorna true se a string estiver vazia
            //é equivalente a fazer nome.equals("");
            if (!nome.isBlank())
                pessoa.setNome(nome);

            System.out.print("Digite o novo telefone (ou deixe em branco para manter): ");
            String telefone = teclado.nextLine();
            if (!telefone.isBlank())
                pessoa.setTelefone(telefone);

            System.out.print("Digite o novo email (ou deixe em branco para manter): ");
            String email = teclado.nextLine();
            if (!email.isBlank())
                pessoa.setEmail(email);

            System.out.println("Contato alterado com sucesso!");
        } else {
            System.out.println("Contato não encontrado.");
            pausa();
        }
    }

    private static void consultarContatos() {
        ArrayList<Pessoa> contatos = new ArrayList<Pessoa>();

        try {
            contatos = PessoaDAO.consultarTodosContatos();

            //metodo isEmpty verifica se a lista esta vazia
            if (contatos.isEmpty()) {
                System.out.println("Nenhum contato cadastrado.");
            } else {
                System.out.println("\n--- Lista de Contatos ---");
                for (Pessoa pessoa : contatos) {
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

        //excluir o contato
        /*if (pessoa != null) {
            listaContatos.remove(pessoa);
            System.out.println("Contato excluído com sucesso!");
        } else {
            System.out.println("Contato não encontrado.");
        }*/
    }

    private static Pessoa encontrarContatoPorId(int id) {
        //varre o array list para encontrar o id pesquisado
        for (Pessoa pessoa : listaContatos) {
            if (pessoa.getId() == id) {
                //encontrou retorna o objeto pessoa
                return pessoa;
            }
        }
        //se chegou até aqui não existe este id
        return null;
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

