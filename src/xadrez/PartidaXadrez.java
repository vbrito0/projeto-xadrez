package xadrez;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		initialSetup();
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i = 0; i < tabuleiro.getLinhas(); i++) {
			for(int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
			}
		}
		return mat;
	}
	
	public PecaXadrez movimentoPeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		return (PecaXadrez)pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, destino);
		return pecaCapturada;
	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if(!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem");
		}
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezException("Não é possível realizar o movimento para a peça escolhida");
		}
	}

	private void lugarNovaPeca(char coluna, int linha, PecaXadrez pecaXadrez) {
		tabuleiro.lugarPeca(pecaXadrez, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	private void initialSetup() {
		lugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
//        lugarNovaPeca('b', 1, new Knight(tabuleiro, Cor.BRANCO));
//        lugarNovaPeca('c', 1, new Bishop(tabuleiro, Cor.BRANCO));
//        lugarNovaPeca('d', 1, new Queen(tabuleiro, Cor.BRANCO));
//        lugarNovaPeca('e', 1, new King(tabuleiro, Cor.BRANCO, this));
//        lugarNovaPeca('f', 1, new Bishop(tabuleiro, Cor.BRANCO));
//        lugarNovaPeca('g', 1, new Knight(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
//        lugarNovaPeca('a', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
//        lugarNovaPeca('b', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
//        lugarNovaPeca('c', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
//        lugarNovaPeca('d', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
//        lugarNovaPeca('e', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
//        lugarNovaPeca('f', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
//        lugarNovaPeca('g', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
//        lugarNovaPeca('h', 2, new Pawn(tabuleiro, Cor.BRANCO, this));

        lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
//        lugarNovaPeca('b', 8, new Knight(tabuleiro, Cor.PRETO));
//        lugarNovaPeca('c', 8, new Bishop(tabuleiro, Cor.PRETO));
//        lugarNovaPeca('d', 8, new Queen(tabuleiro, Cor.PRETO));
//        lugarNovaPeca('e', 8, new King(tabuleiro, Cor.PRETO, this));
//        lugarNovaPeca('f', 8, new Bishop(tabuleiro, Cor.PRETO));
//        lugarNovaPeca('g', 8, new Knight(tabuleiro, Cor.PRETO));
        lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
//        lugarNovaPeca('a', 7, new Pawn(tabuleiro, Cor.PRETO, this));
//        lugarNovaPeca('b', 7, new Pawn(tabuleiro, Cor.PRETO, this));
//        lugarNovaPeca('c', 7, new Pawn(tabuleiro, Cor.PRETO, this));
//        lugarNovaPeca('d', 7, new Pawn(tabuleiro, Cor.PRETO, this));
//        lugarNovaPeca('e', 7, new Pawn(tabuleiro, Cor.PRETO, this));
//        lugarNovaPeca('f', 7, new Pawn(tabuleiro, Cor.PRETO, this));
//        lugarNovaPeca('g', 7, new Pawn(tabuleiro, Cor.PRETO, this));
//        lugarNovaPeca('h', 7, new Pawn(tabuleiro, Cor.PRETO, this));
	}
}
