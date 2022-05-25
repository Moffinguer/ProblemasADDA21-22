package tests;

import java.io.IOException;

import modelos.ModeloPLEEj2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class TestPLEEj2 {

	public static void main(String[] args) throws IOException {
	    String header = "./ficheros/PI5Ej2DatosEntrada";
		for(int i = 1; i <= 3; i++) {
			ModeloPLEEj2.readFile(header + i + ".txt");
			AuxGrammar.generate(ModeloPLEEj2.class, "modelosLSI/Ej2.lsi", "modelosGurobi/Ej2ex" + i + ".lp");
			GurobiSolution gs = GurobiLp.gurobi("modelosGurobi/Ej2ex" + i + ".lp");
			System.out.println("****************SOLUCION " + i + "****************");
			System.out.println(String.format("Test " + i + " solucion PLE:%s", gs.toString((s,d) -> d > 0.)).substring(2));
			System.out.println("**************************************************");
		}
	}

}
