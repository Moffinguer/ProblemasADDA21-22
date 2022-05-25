package test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ejercicios.Ej5;
import us.lsi.common.*;

public class Ej5_test {

	public static void llamaFunciones(List<Integer> line) {
		int a = line.get(0), b = line.get(1), c = line.get(2);
		String res = "(a, b, c) =  " + "(" + a + ", " + b + ", " + c + ")\n";
		res += "Sol. Rec. sin memoria: " + Ej5.ej5RecSM(a, b, c) + "\n";
		res += "Sol. Rec con memoria: " + Ej5.ej5RCM(a, b, c) + "\n";
		res += "Sol. Iterativa: " + Ej5.ej5I(a, b, c) + "\n";
		System.out.println(res);
	}
	public static void main(String[] args) throws IOException {
		// Leer fichero
			List<String> lines = Files2.linesFromFile(".\\ficheros\\PI2Ej5DatosEntrada.txt");
			List<List<Integer>> file = lines.stream().
									map(x 
											->
									Arrays.asList(x.split(",")).stream().
									map(y
											->
									Integer.parseInt(y))
									.collect(Collectors.toList()))
									.collect(Collectors.toList());
			System.out.println("######################################################");
			System.out.println("####################Ejercicio 5#######################");
			System.out.println("###############Fichero DatosEntrada5##################");
			System.out.println("######################################################");
			for(List<Integer> i: file) {
				llamaFunciones(i);
			}
	}

}
