package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez{

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		
		//NOROESTE
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setValores(p.getLinhas() - 1, p.getColunas() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// NORDESTE
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setValores(p.getLinhas() - 1, p.getColunas() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// SUDESTE
		p.setValores(posicao.getLinhas() + 1, posicao.getColunas() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setValores(p.getLinhas() + 1, p.getColunas() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// SUDOESTE
		p.setValores(posicao.getLinhas() + 1, posicao.getColunas() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setValores(p.getLinhas() + 1, p.getColunas() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		return mat;
	}
}