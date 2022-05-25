package tests;

import java.io.IOException;
import java.util.List;

import org.apache.commons.math3.genetics.StoppingCondition;

import modelos.ModeloGeneticoEj1;
import modelos.ModeloPLEEj1;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class TestPLEEj1 {

	public static void main(String[] args) throws IOException {
		String header = "./ficheros/PI5Ej1DatosEntrada";
		for(int i = 1; i <= 3; i++) {
			ModeloPLEEj1.readFile(header + i + ".txt");
			AuxGrammar.generate(ModeloPLEEj1.class, "modelosLSI/Ej1.lsi", "modelosGurobi/Ej1ex" + i + ".lp");
			GurobiSolution gs = GurobiLp.gurobi("modelosGurobi/Ej1ex" + i + ".lp");
			System.out.println("****************SOLUCION PLE" + i + "****************");
			System.out.println(String.format("Test " + i + " solucion PLE:%s", gs.toString((s,d) -> d > 0.)).substring(2));
			System.out.println("**************************************************");
		}
	}

}
