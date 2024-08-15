package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez pecaVulneravel;
	private PecaXadrez promovido;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		initialSetup();
	}

	public int gerTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaXadrez getPecaVulneravel() {
		return pecaVulneravel;
	}
	
	public PecaXadrez getPromovido() {
		return promovido;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public boolean[][] possiveisMovimentos(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.toPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}

	public PecaXadrez movimentoPeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("Você não pode se colocar em check");
		}

		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);
		
		//MOVIMENTO ESPECIAL PROMOVIDO
		promovido = null;
		if(pecaMovida instanceof Peao) {
			if((pecaMovida.getCor() == Cor.BRANCO && destino.getLinhas() == 0) || (pecaMovida.getCor() == Cor.PRETO && destino.getLinhas() == 7)) {
				promovido = (PecaXadrez) tabuleiro.peca(destino);
				promovido = trocarPecaPromovida("Q");
			}
		}

		check = (testarCheck(oponente(jogadorAtual))) ? true : false;

		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// MOVIMENTO ESPECIAL VULNERABILIDADE
		if (pecaMovida instanceof Peao && (destino.getLinhas() == origem.getLinhas() - 2)
				|| destino.getLinhas() == origem.getLinhas() + 2) {
			pecaVulneravel = pecaMovida;
		} else {
			pecaVulneravel = null;
		}

		return (PecaXadrez) pecaCapturada;
	}

	public PecaXadrez trocarPecaPromovida(String tipo) {
		if(promovido == null) {
			throw new IllegalStateException("Não há peça promovida");
		}
		if(!tipo.equals("B") && !tipo.equals("C") & !tipo.equals("T") & !tipo.equals("Q")) {
			return promovido;
		}
		
		Posicao pos = promovido.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(tipo, promovido.getCor());
		tabuleiro.lugarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return null;
	}
	
	private PecaXadrez novaPeca(String tipo, Cor cor) {
		if(tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if(tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if(tipo.equals("Q")) return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possiveisMovimento(destino)) {
			throw new XadrezException("A peça não pode mover para a posição de destino");
		}
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
		p.incrementarQtdMovimento();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// MOVIMENTO ESPECIAL CASTLING REI AO LADO DE TORRES
		if (p instanceof Rei && destino.getColunas() == origem.getColunas() + 2) {
			Posicao origemT = new Posicao(origem.getLinhas(), origem.getColunas() + 3);
			Posicao destinoT = new Posicao(origem.getLinhas(), origem.getColunas() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemT);
			tabuleiro.lugarPeca(torre, destinoT);
			torre.incrementarQtdMovimento();
		}

		// MOVIMENTO ESPECIAL CASTLING RAINHA AO LADO DE TORRES
		if (p instanceof Rei && destino.getColunas() == origem.getColunas() - 2) {
			Posicao origemT = new Posicao(origem.getLinhas(), origem.getColunas() - 4);
			Posicao destinoT = new Posicao(origem.getLinhas(), origem.getColunas() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemT);
			tabuleiro.lugarPeca(torre, destinoT);
			torre.incrementarQtdMovimento();
		}

		// MOVIMENTO ESPECIAL VULNERABILIDADE
		if (p instanceof Peao) {
			if (origem.getColunas() != destino.getColunas() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(destino.getLinhas() + 1, destino.getColunas());
				} else {
					posicaoPeao = new Posicao(destino.getLinhas() - 1, destino.getColunas());
				}
				pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
		p.decrementarQtdMovimento();
		tabuleiro.lugarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// MOVIMENTO ESPECIAL CASTLING REI AO LADO DE TORRES
		if (p instanceof Rei && destino.getColunas() == origem.getColunas() + 2) {
			Posicao origemT = new Posicao(origem.getLinhas(), origem.getColunas() + 3);
			Posicao destinoT = new Posicao(origem.getLinhas(), origem.getColunas() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoT);
			tabuleiro.lugarPeca(torre, origemT);
			torre.decrementarQtdMovimento();
		}

		// MOVIMENTO ESPECIAL CASTLING RAINHA AO LADO DE TORRES
		if (p instanceof Rei && destino.getColunas() == origem.getColunas() - 2) {
			Posicao origemT = new Posicao(origem.getLinhas(), origem.getColunas() - 4);
			Posicao destinoT = new Posicao(origem.getLinhas(), origem.getColunas() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoT);
			tabuleiro.lugarPeca(torre, origemT);
			torre.decrementarQtdMovimento();
		}

		// MOVIMENTO ESPECIAL VULNERABILIDADE
		if (p instanceof Peao) {
			if (origem.getColunas() != destino.getColunas() && pecaCapturada == pecaVulneravel) {
				PecaXadrez peao = (PecaXadrez) tabuleiro.removerPeca(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(3, destino.getColunas());
				} else {
					posicaoPeao = new Posicao(4, destino.getColunas());
				}
				tabuleiro.lugarPeca(peao, posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua");
		}
		if (!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezException("Não é possível realizar o movimento para a peça escolhida");
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o rei com a cor " + cor + " no tabuleiro");
	}

	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[posicaoRei.getLinhas()][posicaoRei.getColunas()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return false;
	}

	private void lugarNovaPeca(char coluna, int linha, PecaXadrez pecaXadrez) {
		tabuleiro.lugarPeca(pecaXadrez, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(pecaXadrez);
	}

	private void initialSetup() {
		lugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
	}
}
