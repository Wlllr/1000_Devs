//no meu metodo eu coloquei esse import random para fazer o teste
/*import java.util.Random;

static int[] obterJogadaComputador(String posicoesLivres) {
    //alocando as posicoes nos vetor
    String[] vetorPosicoes = posicoesLivres.split(";");

    //numero aleatorio para a jogada
    Random random = new Random();
    int indiceSorteado = random.nextInt(vetorPosicoes.length);

    //posicao sorteada e no formato certo
    String posicaoSorteada = vetorPosicoes[indiceSorteado];

    return converterJogadaStringParaVetorInt(posicaoSorteada);
}
    //converter para [x, y]
static int[] converterJogadaStringParaVetorInt(String jogada) {
    int linha = Character.getNumericValue(jogada.charAt(0)); // Obtém o primeiro caractere e converte para inteiro
    int coluna = Character.getNumericValue(jogada.charAt(1)); // Obtém o segundo caractere e converte para inteiro
    return new int[]{linha, coluna};
}
*/