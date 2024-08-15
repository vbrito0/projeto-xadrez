package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturas = new ArrayList<>();
		
		while (!partidaXadrez.getCheckMate()) {
			try {
				UI.limparTela();
				UI.printPartida(partidaXadrez, capturas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean[][] possieisMovimentos = partidaXadrez.possiveisMovimentos(origem);
				UI.limparTela();
				UI.printTabuleiro(partidaXadrez.getPecas(), possieisMovimentos);
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaXadrez pecaCapturada = partidaXadrez.movimentoPeca(origem, destino);
				
				if(pecaCapturada != null) {
					capturas.add(pecaCapturada);
				}
				
				if(partidaXadrez.getPromovido() != null) {
					System.out.print("Escolha peças para promoção (B/C/T/Q): ");
					String tipo = sc.nextLine();
					partidaXadrez.trocarPecaPromovida(tipo);
				}
				
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limparTela();
		UI.printPartida(partidaXadrez, capturas);
	}
}
