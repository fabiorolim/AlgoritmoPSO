package algoritmos.PSO;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//import algoritmos.PSO.Swarm_Ex2b.Particle;

public class Swarm_Ex2b {
	public static int PARTICLE_COUNT; // original valor era fixo = 10;

	/* variável criada para a versão de Kleber do algoritmo PSO. */
	public static int PARTICLE_COUNT_FINAL;

	private static final int V_MAX = 4; // Maximum velocity change allowed.
										// Range: 0 >= V_MAX < CITY_COUNT

	public static int MAX_EPOCHS; // original era = 10000;

	private static ArrayList<Particle> particles = new ArrayList<Particle>();
	// para rodar o reduzido com a mesma aleatoriedade
	private static ArrayList<Particle> copy_particles = new ArrayList<Particle>();

	// private static ArrayList<cCity> map = new ArrayList<cCity>();

	// original: private static final int CITY_COUNT = 8;
	public static int CITY_COUNT; // = 17;

	private static final double TARGET = 40.00;
	// original
	// private static final double TARGET = 86.63; // Number for algorithm to
	// find.
	// private static int XLocs[] = new int[] {30, 40, 40, 29, 19, 9, 9, 20};
	// private static int YLocs[] = new int[] {5, 10, 20, 25, 25, 19, 9, 5};

	private static int matrizDistancia[][]; // = new
											// int[CITY_COUNT][CITY_COUNT];

	// Arquivo de entrada dos dados
	public static File arqEntradaDados = null;

	/*
	 * public static void initializeMap() { for(int i = 0; i < CITY_COUNT; i++)
	 * { cCity city = new cCity(); city.x(XLocs[i]); city.y(YLocs[i]);
	 * map.add(city); } return; }
	 */

	public void initializeMap() {
		lerDadosDoArquivo();
		return;
	}

	// metodo novo criado para ler os dados de distância do arquivo.
	private static void lerDadosDoArquivo() {

		Scanner scanner = null;
		matrizDistancia = new int[CITY_COUNT][CITY_COUNT];

		try {
			scanner = new Scanner(arqEntradaDados);

			// 7 linhas do cabeçalho serão descartadas
			@SuppressWarnings("unused")
			String descartar = scanner.nextLine();

			int ii = 0;
			while (ii < 6) {
				descartar = scanner.nextLine();
				ii++;
			}

			for (int i = 0; i < CITY_COUNT; i++) {
				for (int j = 0; j < CITY_COUNT; j++) {
					matrizDistancia[i][j] = scanner.nextInt();
					// System.out.println("@@@valor:"+matrizDistancia[i][j]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		scanner.close();
	}

	public void PSOAlgorithm() {
		Particle aParticle = null;
		int epoch = 0;
		boolean done = false;

		// initialize();

		while (!done) {
			// Two conditions can end this loop:
			// if the maximum number of epochs allowed has been reached, or,
			// if the Target value has been found.
			if (epoch < MAX_EPOCHS) {

				for (int i = 0; i < PARTICLE_COUNT; i++) {
					aParticle = particles.get(i);
					/*
					 * System.out.print("Route: "); for(int j = 0; j <
					 * CITY_COUNT; j++) { System.out.print(aParticle.data(j) +
					 * ", "); } // j
					 */

					getTotalDistance(i);
					// System.out.print("Distance: " + aParticle.pBest() +
					// "\n");
					if (aParticle.pBest() <= TARGET) {
						done = true;
					}
				} // i

				// bubbleSort(); // sort particles by their pBest scores, best
				// to worst.

				// getVelocity();

				// updateparticles();

				System.out.println("epoch number: " + epoch);

				epoch++;

			} else {
				done = true;
			}
		}
		return;
	}

	public void PSOAlgorithmRed() {
		Particle aParticle = null;
		int epoch = 0;
		boolean done = false;

		int[] variaveis = retornaVariaveisDeDecrementoDeIteracoesPSO();
		int taxaDecremento = variaveis[0];
		int decrementarAPartirDaIteracao = variaveis[1];

		// Recuperar inicialização do PSO normal
		// initialize();

		while (!done) {
			// Two conditions can end this loop:
			// if the maximum number of epochs allowed has been reached, or,
			// if the Target value has been found.
			if (epoch < MAX_EPOCHS) {

				/*
				 * limiteFinalDecrementavel substituir� a variavel
				 * PARTICLE_COUNT no limite final do for abaixo. Ser�
				 * decrementado per�odicamente dentro do for. vari�vel foi
				 * criada para a vers�o de Kleber do algoritmo PSO.
				 */
				int limiteFinalDecrementavel = PARTICLE_COUNT;

				for (int i = 0; i < limiteFinalDecrementavel; i++) {
					aParticle = particles.get(i);
					/*
					 * System.out.print("Route: "); for(int j = 0; j <
					 * CITY_COUNT; j++) { System.out.print(aParticle.data(j) +
					 * ", "); } // j
					 */

					getTotalDistance(i);
					// System.out.print("Distance: " + aParticle.pBest() +
					// "\n");
					if (aParticle.pBest() <= TARGET) {
						done = true;
					}
					/*
					 * Mudança criada para a versão de Kleber do algoritmo PSO.
					 */

					if (decrementarAPartirDaIteracao <= (i + 1)) {
						limiteFinalDecrementavel = limiteFinalDecrementavel - taxaDecremento;
					}

				} // i

				// bubbleSort(); // sort particles by their pBest scores, best
				// to worst.

				// getVelocity();

				// updateparticles();

				System.out.println("epoch number: " + epoch);

				epoch++;

			} else {
				done = true;
			}
		}
		return;
	}

	public static void initialize() {
		for (int i = 0; i < PARTICLE_COUNT; i++) {
			Particle newParticle = new Particle();
			for (int j = 0; j < CITY_COUNT; j++) {
				newParticle.data(j, j);
			} // j
			particles.add(newParticle);
			copy_particles.add(newParticle);
			for (int j = 0; j < 10; j++) {
				randomlyArrange(particles.indexOf(newParticle));
			}
			getTotalDistance(particles.indexOf(newParticle));
		} // i

	}

	private static void randomlyArrange(final int index) {
		int cityA = new Random().nextInt(CITY_COUNT);
		int cityB = 0;
		boolean done = false;
		while (!done) {
			cityB = new Random().nextInt(CITY_COUNT);
			if (cityB != cityA) {
				done = true;
			}
		}

		int temp = particles.get(index).data(cityA);
		particles.get(index).data(cityA, particles.get(index).data(cityB));
		particles.get(index).data(cityB, temp);

		return;
	}

	public static void getVelocity() {
		double worstResults = 0;
		double vValue = 0.0;

		// after sorting, worst will be last in list.
		worstResults = particles.get(PARTICLE_COUNT - 1).pBest();

		for (int i = 0; i < PARTICLE_COUNT; i++) {
			vValue = (V_MAX * particles.get(i).pBest()) / worstResults;

			if (vValue > V_MAX) {
				particles.get(i).velocity(V_MAX);
			} else if (vValue < 0.0) {
				particles.get(i).velocity(0.0);
			} else {
				particles.get(i).velocity(vValue);
			}
		}
		return;
	}

	public static void updateparticles() {
		// Best is at index 0, so start from the second best.
		for (int i = 1; i < PARTICLE_COUNT; i++) {
			// The higher the velocity score, the more changes it will need.
			int changes = (int) Math.floor(Math.abs(particles.get(i).velocity()));
			// System.out.println("Changes for particle " + i + ": " + changes);
			for (int j = 0; j < changes; j++) {
				if (new Random().nextBoolean()) {
					randomlyArrange(i);
				}
				// Push it closer to it's best neighbor.
				copyFromParticle(i - 1, i);
			} // j

			// Update pBest value.
			getTotalDistance(i);
		} // i

		return;
	}

	public String printBestSolution() {
		String resultado = "";

		if (particles.get(0).pBest() <= TARGET) {
			// Print it.
			System.out.println("Target reached.");
			resultado += "Target reached.\n";
		} else {
			System.out.println("Target not reached");
			resultado += "Target not reached.\n";
		}
		System.out.print("Shortest Route: ");
		resultado += "Shortest Route: ";

		for (int j = 0; j < CITY_COUNT; j++) {
			System.out.print(particles.get(0).data(j) + ", ");
			resultado += particles.get(0).data(j) + ", ";
		} // j
		System.out.println("Distance: " + particles.get(0).pBest() + "\n");
		resultado += "\n Distance: " + particles.get(0).pBest() + "\n";
		return resultado;
	}

	private static void copyFromParticle(final int source, final int destination) {
		// push destination's data points closer to source's data points.
		Particle best = particles.get(source);
		int targetA = new Random().nextInt(CITY_COUNT); // source's city to
														// target.
		int targetB = 0;
		int indexA = 0;
		int indexB = 0;
		int tempIndex = 0;

		// targetB will be source's neighbor immediately succeeding targetA
		// (circular).
		int i = 0;
		for (; i < CITY_COUNT; i++) {
			if (best.data(i) == targetA) {
				if (i == CITY_COUNT - 1) {
					targetB = best.data(0); // if end of array, take from
											// beginning.
				} else {
					targetB = best.data(i + 1);
				}
				break;
			}
		}

		// Move targetB next to targetA by switching values.
		for (int j = 0; j < CITY_COUNT; j++) {
			if (particles.get(destination).data(j) == targetA) {
				indexA = j;
			}
			if (particles.get(destination).data(j) == targetB) {
				indexB = j;
			}
		}
		// get temp index succeeding indexA.
		if (indexA == CITY_COUNT - 1) {
			tempIndex = 0;
		} else {
			tempIndex = indexA + 1;
		}

		// Switch indexB value with tempIndex value.
		int temp = particles.get(destination).data(tempIndex);
		particles.get(destination).data(tempIndex, particles.get(destination).data(indexB));
		particles.get(destination).data(indexB, temp);

		return;
	}

	private static void getTotalDistance(final int index) {
		Particle thisParticle = null;
		thisParticle = particles.get(index);
		thisParticle.pBest(0.0);

		for (int i = 0; i < CITY_COUNT; i++) {
			if (i == CITY_COUNT - 1) {
				thisParticle.pBest(
						thisParticle.pBest() + getDistance(thisParticle.data(CITY_COUNT - 1), thisParticle.data(0))); // Complete
																														// trip.
			} else {
				thisParticle.pBest(thisParticle.pBest() + getDistance(thisParticle.data(i), thisParticle.data(i + 1)));
			}
		}
		return;
	}

	// original
	/*
	 * private static double getDistance(final int firstCity, final int
	 * secondCity) { cCity cityA = null; cCity cityB = null; double a2 = 0;
	 * double b2 = 0; cityA = map.get(firstCity); cityB = map.get(secondCity);
	 * a2 = Math.pow(Math.abs(cityA.x() - cityB.x()), 2); b2 =
	 * Math.pow(Math.abs(cityA.y() - cityB.y()), 2);
	 * 
	 * return Math.sqrt(a2 + b2); }
	 */

	private static double getDistance(final int firstCity, final int secondCity) {
		return matrizDistancia[firstCity][secondCity];
	}

	public static void bubbleSort() {
		boolean done = false;
		while (!done) {
			int changes = 0;
			int listSize = particles.size();
			for (int i = 0; i < listSize - 1; i++) {
				if (particles.get(i).compareTo(particles.get(i + 1)) == 1) {
					Particle temp = particles.get(i);
					particles.set(i, particles.get(i + 1));
					particles.set(i + 1, temp);
					changes++;
				}
			}
			if (changes == 0) {
				done = true;
			}
		}
		return;
	}

	private static int[] retornaVariaveisDeDecrementoDeIteracoesPSO() {
		int[] variaveis = new int[2];
		int taxaDecremento = 0;
		int decrementarAPartirDaIteracao;

		/*
		 * (A-B)/C = resultado. onde A:PARTICLE_COUNT B:PARTICLE_COUNT_FINAL
		 * C:MAX_EPOCHS
		 */
		BigDecimal resultado = new BigDecimal((PARTICLE_COUNT - PARTICLE_COUNT_FINAL));
		resultado = resultado.divide(new BigDecimal(MAX_EPOCHS), 10, RoundingMode.HALF_UP);
		System.out.println("@RESULTADO DA EQUAÇÃO");
		System.out.println("(A-B)/C = (" + PARTICLE_COUNT + "-" + PARTICLE_COUNT_FINAL + ")/" + MAX_EPOCHS + "=");
		System.out.printf("%1.9f", resultado);
		System.out.println("");

		if (resultado.compareTo(new BigDecimal(1)) == -1) {
			System.out.println("RESULTADO < 1");
			taxaDecremento = 1;
			decrementarAPartirDaIteracao = MAX_EPOCHS - (PARTICLE_COUNT - PARTICLE_COUNT_FINAL);
			System.out.println("taxaDecremento=" + taxaDecremento);
			System.out.println("decrementarAPartirDaIteracao=" + decrementarAPartirDaIteracao);
		} else {
			System.out.println("RESULTADO >= 1");
			taxaDecremento = resultado.intValue(); // (int)
													// Math.floor(resultado);
			decrementarAPartirDaIteracao = 1;
			System.out.println("taxaDecremento=" + taxaDecremento);
			System.out.println("decrementarAPartirDaIteracao=" + decrementarAPartirDaIteracao);
		}
		variaveis[0] = taxaDecremento;
		variaveis[1] = decrementarAPartirDaIteracao;

		return variaveis;
	}

	private static class Particle implements Comparable<Particle> {
		private int mData[] = new int[CITY_COUNT];
		private double mpBest = 0;
		private double mVelocity = 0.0;

		public Particle() {
			this.mpBest = 0;
			this.mVelocity = 0.0;
		}

		public int compareTo(Particle that) {
			if (this.pBest() < that.pBest()) {
				return -1;
			} else if (this.pBest() > that.pBest()) {
				return 1;
			} else {
				return 0;
			}
		}

		public int data(final int index) {
			return this.mData[index];
		}

		public void data(final int index, final int value) {
			this.mData[index] = value;
			return;
		}

		public double pBest() {
			return this.mpBest;
		}

		public void pBest(final double value) {
			this.mpBest = value;
			return;
		}

		public double velocity() {
			return this.mVelocity;
		}

		public void velocity(final double velocityScore) {
			this.mVelocity = velocityScore;
			return;
		}
	} // Particle

	/*
	 * private static class cCity { private int mX = 0; private int mY = 0;
	 * 
	 * public int x() { return mX; }
	 * 
	 * public void x(final int xCoordinate) { mX = xCoordinate; return; }
	 * 
	 * public int y() { return mY; }
	 * 
	 * public void y(final int yCoordinate) { mY = yCoordinate; return; } } //
	 * cCity
	 */

	/*
	 * public static void main(String[] args) { initializeMap(); PSOAlgorithm();
	 * printBestSolution(); return; }
	 */

}
