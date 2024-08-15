package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
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
		return mat;
	}
}
