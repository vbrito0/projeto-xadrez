package xadrez.pecas;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{
	
	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testReiCastling(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p instanceof Rei && p.getCor() == getCor() && p.getQtdMovimento() == 0;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		
		//ACIMA
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas());
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//ABAIXO
		p.setValores(posicao.getLinhas() + 1, posicao.getColunas());
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//ESQUERDA
		p.setValores(posicao.getLinhas(), posicao.getColunas() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//DIREITA
		p.setValores(posicao.getLinhas(), posicao.getColunas() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//NOROESTE
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//NORDESTE
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//SUDOESTE
		p.setValores(posicao.getLinhas() + 1, posicao.getColunas() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//SUDESTE
		p.setValores(posicao.getLinhas() + 1, posicao.getColunas() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//MOVIMENTO ESPECIAL CASTLING
		if(getQtdMovimento() == 0 && !partidaXadrez.getCheck()) {
			//MOVIMENTO ESPECIAL REI AO LADO DE TORRES
			Posicao posT1 = new Posicao(posicao.getLinhas(), p.getColunas() + 3);
			if(testReiCastling(posT1)) {
				Posicao p1 = new Posicao(posicao.getLinhas(), posicao.getColunas() + 1);
				Posicao p2 = new Posicao(posicao.getLinhas(), posicao.getColunas() + 2);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinhas()][posicao.getColunas() + 2] = true;
				}
			}
			//MOVIMENTO ESPECIAL RAINHA AO LADO DE TORRES
			Posicao posT2 = new Posicao(posicao.getLinhas(), p.getColunas() - 4);
			if(testReiCastling(posT2)) {
				Posicao p1 = new Posicao(posicao.getLinhas(), posicao.getColunas() - 1);
				Posicao p2 = new Posicao(posicao.getLinhas(), posicao.getColunas() - 2);
				Posicao p3 = new Posicao(posicao.getLinhas(), posicao.getColunas() - 3);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					mat[posicao.getLinhas()][posicao.getColunas() - 2] = true;
				}
			}
		}
		
		return mat;
	}
}
