package robocodeIA;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SpinnerNumberModel;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.AveragingCrossoverOperator;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;
import org.jgap.impl.MutationOperator;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

/**
 * Esta Classe irá rodar varías batalhas no Robocode e fazer uso
 * do Algoritmo Genético para treinar o nosso robô.
 */
public class GPAlgorithm extends FitnessFunction {
	
	private Configuration conf;
	
	private SpinnerNumberModel model = new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1);
	
	private static final int POPULATION_SIZE = 100;
	private static final int MAX_GENERATIONS = 100;
	public static int robotScore, enemyScore;
	
	public void runAlgorithm() throws Exception{
		conf = new DefaultConfiguration();
		BestChromosomesSelector best = new BestChromosomesSelector(conf, 0.6);
		
		// não permitir cromossomos duplicados
		best.setDoubletteChromosomesAllowed(false);
		
		conf.addNaturalSelector(best, false);
		conf.addGeneticOperator(new CrossoverOperator(conf, 0.35));
		final int mutationRate = (Integer) model.getValue();
		conf.addGeneticOperator(new MutationOperator(conf, mutationRate));
		
		conf.setPreservFittestIndividual(false);
		conf.setFitnessFunction(this);
		
		
		Gene[] genes = getGenes();
		IChromosome primeiroCromossomo = new Chromosome(conf, genes);
		conf.setSampleChromosome(primeiroCromossomo);
		conf.setPopulationSize(POPULATION_SIZE);
		
		Genotype populacao = Genotype.randomInitialGenotype(conf);
		IChromosome melhorSolucao = null;
		
		for (int i = 0; i < MAX_GENERATIONS; ++i) {
			populacao.evolve();
			melhorSolucao = populacao.getFittestChromosome();
			System.out.printf("--- Melhor solução após %d gerações: %s  ---\n", i + 1, melhorSolucao);
			salvarInformacoesDaGeracao(i + 1, melhorSolucao.getFitnessValue());
		}
		
		criaRobo(melhorSolucao);
	}
	
	public static void main(String[] args) throws Exception {
		new GPAlgorithm().runAlgorithm();
	}
	
	private void salvarInformacoesDaGeracao(int i, double fitnessValue) {
		FileWriter fw = null;
		try {
			fw = new FileWriter("results/data.csv", true);
			StringBuilder sb = new StringBuilder();
			sb.append(i).append(";").append(fitnessValue).append("\n");
			fw.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Gene[] getGenes() throws InvalidConfigurationException {
		Gene[] sample = new Gene[13];
		sample[0] = new DoubleGene(conf, 0, 1000);
		sample[1] = new DoubleGene(conf, 0, 500);
		sample[2] = new DoubleGene(conf, 0, 500);
		sample[3] = new DoubleGene(conf, 0, 600);
		sample[4] = new DoubleGene(conf, 0, 600);
		sample[5] = new DoubleGene(conf, 0, 600);
		sample[6] = new DoubleGene(conf, 0, 80);
		sample[7] = new DoubleGene(conf, 1, 15);
		sample[8] = new DoubleGene(conf, 1, 15);
		sample[9] = new DoubleGene(conf, 0, 600);
		sample[10] = new DoubleGene(conf, 0, 600);
		sample[11] = new DoubleGene(conf, 0, 150);
		sample[12] = new DoubleGene(conf, 0, 600);
		
		return sample;
	}
	
	/**
	 * Cria um Robô a partir de um determinado cromossomo
	 */
	private void criaRobo(IChromosome chromo) {
		double[] robotConfig = new double[13];
		
		Gene[] genes = chromo.getGenes();
		
		for (int i = 0; i < 13; i++) {
			robotConfig[i] = (double) genes[i].getAllele();
		}
		RobotFactory.create(robotConfig);
	}
	
	public void result(String name, int score) {
		if (name.equals("jeuj.JeujDaPeu*")) {
			robotScore = score;
		} else {
			enemyScore = score;
		}
	}

	@Override
	protected double evaluate(IChromosome chromosome) {
		int numberOfRounds = 2;
		int fitness = 0;

		criaRobo(chromosome);

		RobocodeEngine engine = new RobocodeEngine();
		engine.addBattleListener(new BattleObserver());
		engine.setVisible(false);
		

		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
		RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.Crazy, jeuj.JeujDaPeu*");
		BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);	
		engine.runBattle(battleSpec, true);
		fitness += robotScore;
		
		battlefield = new BattlefieldSpecification(800, 600);
		selectedRobots = engine.getLocalRepository("sample.Walls, jeuj.JeujDaPeu*");
		battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
		engine.runBattle(battleSpec, true);
		
		fitness += robotScore;
		
//		battlefield = new BattlefieldSpecification(800, 600);
//		selectedRobots = engine.getLocalRepository("sample.Walls, jeuj.JeujDaPeu*");
//		battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
//		engine.runBattle(battleSpec, true);
//		
//		fitness += robotScore;
		engine.close();

		
		return fitness / 2;
	}
}
