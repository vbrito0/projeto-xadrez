package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);

		// ACIMA
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setLinhas(p.getLinhas() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// ESQUERDA
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setColunas(p.getColunas() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// DIREITA
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setColunas(p.getColunas() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// ABAIXO
		p.setValores(posicao.getLinhas() - 1, posicao.getColunas());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setLinhas(p.getLinhas() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// NOROESTE
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
