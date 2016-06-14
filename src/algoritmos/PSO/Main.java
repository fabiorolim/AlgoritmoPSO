package algoritmos.PSO;

import java.util.Scanner;

public class Main {

	public static Scanner sc = new Scanner(System.in);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			int opcao = 0;
			do {
				opcao = showMenu();

				switch (opcao) {

				case 1:
					ExecutarPSO.main(args);
					break;
				case 2:
					LerResultados.main(args);
					break;
				case 3:
					System.out.println("Deixando Programa...");
					break;
				default:
					System.out.println("Opcao invalida.");
				}

			} while (opcao != 3);

			System.out.println("Obrigado por usar o Programa.");

		} catch (Exception ex) {
			System.out.println("Parametos invalidos. Inicie o processo novamente. Detalhes: " + ex.getMessage());

			sc.next();

		} finally {
			sc.close();
		}

	}

	public static int showMenu() {

		int option = 0;

		System.out.println("========================================");
		System.out.println("            Algoritmo PSO               ");
		System.out.println("            Versao 1.0.1                ");
		System.out.println("             12/05/2015                 ");
		System.out.println("========================================");

		System.out.println("******MENU*******");
		System.out.println("1 - Executar o algoritmo");
		System.out.println("2 - Ver resultados anteriores");
		System.out.println("3 - Sair");
		System.out.print("Opcao: ");

		option = sc.nextInt();

		return option;

	}

}
