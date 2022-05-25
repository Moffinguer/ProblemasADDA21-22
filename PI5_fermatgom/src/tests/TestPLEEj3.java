package tests;

import java.io.IOException;

import modelos.ModeloPLEEj2;
import modelos.ModeloPLEEj3;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class TestPLEEj3 {
	public static void main(String[] args) throws IOException {
	    String header = "./ficheros/PI5Ej3DatosEntrada";
		for(int i = 1; i <= 3; i++) {
			ModeloPLEEj3.readFile(header + i + ".txt");
			AuxGrammar.generate(ModeloPLEEj3.class, "modelosLSI/Ej3.lsi", "modelosGurobi/Ej3ex" + i + ".lp");
			GurobiSolution gs = GurobiLp.gurobi("modelosGurobi/Ej3ex" + i + ".lp");
			System.out.println("****************SOLUCION " + i + "****************");
			System.out.println(String.format("Test " + i + " solucion PLE:%s", gs.toString((s,d) -> d > 0.)).substring(2));
			System.out.println("**************************************************");
		}
	}
}
