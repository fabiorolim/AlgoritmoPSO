package algoritmos.PSO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javafx.css.PseudoClass;

/**
 * 
 * Implementação do Algoritmo PSO, ele lê os dados a partir de um arquivo txt e
 * grava o resultado em um arquivo de texto de saída. Todo o histórico de
 * resultados é adicionado no arquivo de saída.
 * 
 * @author f197714
 *
 */
public class ExecutarPSO {

	private static String nomeArquivo = new String("");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("");
		System.out.println("=================");
		System.out.println("  Execução do Algoritmo PSO  ");
		System.out.println("=================");

		Swarm_Ex2b novaExecucao = new Swarm_Ex2b();

		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			System.out.print("Informe o nome do arquivo contendo a Instância (.txt): ");
			nomeArquivo = sc.next();
			novaExecucao.arqEntradaDados = new File(
					"///Users/fabiorolim/Documents/workspace/AlgoritmoPSO-Kleber/entradas/" + nomeArquivo);

			System.out.print("Informe o valor do PARTICLES_COUNT (número inteiro): ");
			novaExecucao.PARTICLE_COUNT = sc.nextInt();

			System.out.print("Informe o valor do PARTICLES_COUNT_final (número inteiro): ");
			novaExecucao.PARTICLE_COUNT_FINAL = sc.nextInt();

			System.out.print("Informe o valor do MAX_EPOCHS (número inteiro): ");
			novaExecucao.MAX_EPOCHS = sc.nextInt();

			System.out.print("Informe o valor do CITY_COUNT (número inteiro): ");
			novaExecucao.CITY_COUNT = sc.nextInt();

		} catch (Exception e) {
			System.out.println("Parametos invalidos. Inicie o processo novamente. Detalhes: " + e.getMessage());
		}

		novaExecucao.initializeMap();
		long tempoInicial = System.currentTimeMillis();
		Swarm_Ex2b.initialize();

		//// IMPRESSÃO DO ALGORITMO COM REDUÇÃO
		novaExecucao.PSOAlgorithmRed();
		Swarm_Ex2b.bubbleSort();
		Swarm_Ex2b.getVelocity();
		Swarm_Ex2b.updateparticles();

		long tempoFinal = System.currentTimeMillis();
		System.out.printf("TEMPO DE EXECUÇÃO ALGORITMO COM REDUÇÃO :" + (tempoFinal - tempoInicial) + "ms \n");// em
																												// milisegundos

		String PARTICLE_COUNT = "\n PARTICLE_COUNT: " + String.valueOf(novaExecucao.PARTICLE_COUNT);
		String PARTICLE_COUNT_FINAL = "\n PARTICLE_COUNT_FINAL: " + String.valueOf(novaExecucao.PARTICLE_COUNT_FINAL);
		String MAX_EPOCHS = "\n MAX_EPOCHS: " + String.valueOf(novaExecucao.MAX_EPOCHS);
		String CITY_COUNT = "\n CITY_COUNT: " + String.valueOf(novaExecucao.CITY_COUNT);

		String tempo_com_reducao = "\n TEMPO DE EXECUÇÃO ALGORITMO COM REDUÇÃO: " + (tempoFinal - tempoInicial)
				+ "ms \n";

		String resultado_reduzido = novaExecucao.printBestSolution();

		// adicionei para gravar no arquivo com historico de resultados.
		gravarNoArquivo(resultado_reduzido + PARTICLE_COUNT + PARTICLE_COUNT_FINAL + MAX_EPOCHS + CITY_COUNT
				+ tempo_com_reducao, true);

		// IMPRESSÃO DO ALGORITMO SEM REDUÇÃO
		long tempoInicial_nao_reduzido = System.currentTimeMillis();
		novaExecucao.PSOAlgorithm();
		Swarm_Ex2b.bubbleSort();
		Swarm_Ex2b.getVelocity();
		Swarm_Ex2b.updateparticles();

		long tempoFinal_nao_reduzido = System.currentTimeMillis();
		System.out.printf("TEMPO DE EXECUÇÃO ALGORITMO SEM REDUÇÃO: "
				+ (tempoFinal_nao_reduzido - tempoInicial_nao_reduzido) + "ms \n");// em
																					// milisegundos

		String tempo = "\n TEMPO DE EXECUÇÃO ALGORITMO SEM REDUÇÃO: "
				+ (tempoFinal_nao_reduzido - tempoInicial_nao_reduzido) + "ms \n";

		String resultado_nao_reduzido = novaExecucao.printBestSolution();

		// adicionei para gravar no arquivo com histórico de resultados.
		gravarNoArquivo(
				resultado_nao_reduzido + PARTICLE_COUNT + PARTICLE_COUNT_FINAL + MAX_EPOCHS + CITY_COUNT + tempo,
				false);

		return;

	}

	private static void gravarNoArquivo(String resultadoPSO, boolean reduzido) {
		File arquivo = new File("///Users/fabiorolim/Documents/workspace/AlgoritmoPSO-Kleber/saidas/resultados.txt");
		String tipo;
		if (reduzido) {
			tipo = "***RESULTADO DO PSO COM REDUÇÃO***";
		} else {
			tipo = "***RESULTADO DO PSO SEM REDUÇÃO***";
		}

		try {
			if (!arquivo.exists()) {
				arquivo.createNewFile();
			}
			FileWriter arq = new FileWriter(arquivo, true);

			PrintWriter gravarArq = new PrintWriter(arq);

			gravarArq.printf("+--------" + tipo + "----------+%n");

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			String dataHora = dateFormat.format(date);

			gravarArq.print("Data/Hora: " + dataHora + "\n");
			gravarArq.print("Arquivo de instância usado: " + nomeArquivo + "\n");
			// gravando o resultado
			gravarArq.print(resultadoPSO);

			gravarArq.printf("\n");

			arq.close();
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo:" + e.getMessage());
		}
	}

}
