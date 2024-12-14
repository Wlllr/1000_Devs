package br.com.milDevs.projetos.jogoDaVelha.metodo01;

import java.util.Scanner;

public class JogadaaUsuario {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Exemplo de posições livres em uma string (linha,coluna separadas por espaço)
        String posicoesLivres = "0,0 0,1 1,1 2,2";

        int[] jogada = obterJogadaUsuario(posicoesLivres, teclado);
        System.out.println("Você jogou na linha " + jogada[0] + " e coluna " + jogada[1]);
    }

    static int[] obterJogadaUsuario(String posicoesLivres, Scanner teclado) {
        while (true) {
            try {
                System.out.println("Informe sua jogada (linha e coluna separados por espaço, ex: '1 2'):");
                String entrada = teclado.nextLine().trim();
                String[] partes = entrada.split(" ");

                //faz a verificacao da quantidade de entradas (devem ser duas)
                if (partes.length != 2) {
                    System.out.println("Você deve informar dois números separados por espaço. Tente novamente.");
                    continue;
                }

                // Converte as entradas para inteiros
                int linha = Integer.parseInt(partes[0]) - 1; // Ajusta para índice (ex: 1 -> 0)
                int coluna = Integer.parseInt(partes[1]) - 1; // Ajusta para índice (ex: 1 -> 0)

                // Verifica se os índices estão dentro do tabuleiro (0 a 2)
                if (linha < 0 || coluna < 0 || coluna > 2) {
                    System.out.println("Valores fora do limite do tabuleiro. Digite números entre 1 e 3.");
                    continue;
                }

                // Verifica se a jogada é válida
                if (!jogadaValida(posicoesLivres, linha, coluna)) {
                    System.out.println("A posição já está ocupada ou é inválida. Escolha outra.");
                    continue;
                }

                //mostra a jogada válida
                return new int[]{linha, coluna};

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Certifique-se de digitar dois números.");
            }
        }
    }

    static boolean jogadaValida(String posicoesLivres, int linha, int coluna) {
        // Formata a posição como "linha,coluna"
        String posicao = linha + "," + coluna;
        // Verifica se a posição está na string de posições livres
        return posicoesLivres.contains(posicao);
    }
}
