package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez{

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "C";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas() - 2);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		p.setValores(posicao.getLinhas() - 2, posicao.getColunas() - 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		p.setValores(posicao.getLinhas() - 2, posicao.getColunas() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas() + 2);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		p.setValores(posicao.getLinhas() + 1, posicao.getColunas() + 2);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		p.setValores(posicao.getLinhas() + 2, posicao.getColunas() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		p.setValores(posicao.getLinhas() + 2, posicao.getColunas() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		p.setValores(posicao.getLinhas() + 1, posicao.getColunas() - 2);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		return mat;
	}

}
