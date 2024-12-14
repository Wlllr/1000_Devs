package br.com.milDevs.projetos.jogoDaVelha.metodo01;

import java.util.Scanner;

public class CaractereComputador {


    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        char caractereUsuario = 'X';

        System.out.println(obterCaractereComputador(teclado, caractereUsuario));
    }

    static char obterCaractereComputador(Scanner teclado, char caractereUsuario) {
        final char[] CARACTERES_IDENTIFICADORES_ACEITOS = {'X', 'O'};
        char caractereComputador;

        while (true) {
            System.out.print("Escolha o caractere para o computador (X ou O): ");
            caractereComputador = teclado.nextLine().toUpperCase().charAt(0);

            boolean valido = false;

            // Verifica se o caractere é permitido
            for (char permitido : CARACTERES_IDENTIFICADORES_ACEITOS) {
                if (caractereComputador == permitido) {
                    valido = true;
                    break;
                }
            }

            // Se não for permitido ou for igual ao do usuário, peça novamente
            if (!valido) {
                System.out.println("Caractere inválido. Por favor, escolha X ou O.");
            } else if (caractereComputador == caractereUsuario) {
                System.out.println("Caractere já escolhido pelo usuário. Escolha outro.");
            } else {
                break; // Se for válido e diferente do caractere do usuário, sai do loop
            }
        }

        return caractereComputador; // Retorna o caractere válido
    }
}
