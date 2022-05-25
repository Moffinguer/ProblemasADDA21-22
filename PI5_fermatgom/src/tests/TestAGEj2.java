package tests;

import java.io.IOException;
import java.util.List;

import org.apache.commons.math3.genetics.StoppingCondition;

import modelos.ModeloGeneticoEj1;
import modelos.ModeloGeneticoEj2;
import modelos.ModeloPLEEj1;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class TestAGEj2 {

	public static void main(String[] args) throws IOException {
		String header = "./ficheros/PI5Ej2DatosEntrada";
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		StoppingConditionFactory.NUM_GENERATIONS = 400;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		for(int i = 1; i <= 3; i++) {
			var alg = AlgoritmoAG.of(ModeloGeneticoEj2.create(header + i + ".txt"));
			alg.ejecuta();
			System.out.println("****************SOLUCION GENÉTICO" + i + "****************");
			System.out.println("Mejor cromosoma: " + alg.getBestChromosome());
			System.out.println("Mejor solucion: " + alg.bestSolution());
			System.out.println("**************************************************");
		}
	}

}
