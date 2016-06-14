package algoritmos.PSO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Abre o arquide de saída, que contém o histórico de resultados de execução do
 * algoritmo PSO, e exibe para o usuário.
 * 
 * 
 * @author f197714
 *
 */

public class LerResultados {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("");
		System.out.println("=================");
		System.out.println("  Lendo histórico de Resultados...  ");
		System.out.println("=================");

		lerArquivo();

	}

	private static void lerArquivo() {

		File arquivo = new File("///Users/fabiorolim/Documents/workspace/AlgoritmoPSO-Kleber/saidas/resultados.txt");

		if (arquivo.exists()) {
			// construtor que recebe o objeto do tipo arquivo
			FileReader fr;
			try {
				fr = new FileReader(arquivo);
				BufferedReader br = new BufferedReader(fr);

				// equanto houver mais linhas
				while (br.ready()) {
					// l� a proxima linha
					String linha = br.readLine();

					System.out.println(linha);
				}

				fr.close();
			} catch (FileNotFoundException e) {
				System.out.println("Arquivo não encontrado:" + e.getMessage());
			} catch (IOException e2) {
				System.out.println("Erro ao ler o arquivo:" + e2.getMessage());
			}

		}

	}

}
