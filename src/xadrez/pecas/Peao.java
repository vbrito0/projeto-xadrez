package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		
		if(getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinhas() - 1, posicao.getColunas());
			if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() - 2, posicao.getColunas());
			Posicao p2 = new Posicao(posicao.getLinhas() - 1, posicao.getColunas());
			if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temUmaPeca(p2) && getQtdMovimento() == 0) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() - 1, posicao.getColunas() - 1);
			if(getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() + 1, posicao.getColunas() + 1);
			if(getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
		} else {
			p.setValores(posicao.getLinhas() + 1, posicao.getColunas());
			if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() + 2, posicao.getColunas());
			Posicao p2 = new Posicao(posicao.getLinhas() + 1, posicao.getColunas());
			if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temUmaPeca(p2) && getQtdMovimento() == 0) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() + 1, posicao.getColunas() - 1);
			if(getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			p.setValores(posicao.getLinhas() + 1, posicao.getColunas() + 1);
			if(getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
		}
		
		return mat;
	}

}
