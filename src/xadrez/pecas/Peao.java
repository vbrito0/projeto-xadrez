package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinhas() - 1, posicao.getColunas());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() - 2, posicao.getColunas());
			Posicao p2 = new Posicao(posicao.getLinhas() - 1, posicao.getColunas());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)
					&& getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temUmaPeca(p2)
					&& getQtdMovimento() == 0) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() - 1, posicao.getColunas() - 1);
			if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() + 1, posicao.getColunas() + 1);
			if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}

			// MOVIMENTO ESPECIAL VULNERABILIDADE BRANCA
			if (posicao.getLinhas() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinhas(), posicao.getColunas() - 1);
				if (getTabuleiro().posicaoExistente(esquerda) && existePecaAdversaria(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez.getPecaVulneravel()) {
					mat[esquerda.getLinhas() - 1][esquerda.getColunas()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinhas(), posicao.getColunas() + 1);
				if (getTabuleiro().posicaoExistente(direita) && existePecaAdversaria(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez.getPecaVulneravel()) {
					mat[direita.getLinhas() - 1][direita.getColunas()] = true;
				}
			}
		} else {
			p.setValores(posicao.getLinhas() + 1, posicao.getColunas());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() + 2, posicao.getColunas());
			Posicao p2 = new Posicao(posicao.getLinhas() + 1, posicao.getColunas());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)
					&& getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temUmaPeca(p2)
					&& getQtdMovimento() == 0) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() + 1, posicao.getColunas() - 1);
			if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() + 1, posicao.getColunas() + 1);
			if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			
			// MOVIMENTO ESPECIAL VULNERABILIDADE PRETA
			if (posicao.getLinhas() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinhas(), posicao.getColunas() - 1);
				if (getTabuleiro().posicaoExistente(esquerda) && existePecaAdversaria(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez.getPecaVulneravel()) {
					mat[esquerda.getLinhas() + 1][esquerda.getColunas()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinhas(), posicao.getColunas() + 1);
				if (getTabuleiro().posicaoExistente(direita) && existePecaAdversaria(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez.getPecaVulneravel()) {
					mat[direita.getLinhas() + 1][direita.getColunas()] = true;
				}
			}
		}

		return mat;
	}

}
