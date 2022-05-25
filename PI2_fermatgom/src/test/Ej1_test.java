package test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ejercicios.Ej1;
import us.lsi.common.Files2;

public class Ej1_test {

	public static void llamaFunciones(List<Integer> line) {
		int a = line.get(0), b = line.get(1), c = line.get(2);
		String res = "(a, b, c) = (" + a + ", " + b + ", " + c + ")\n";
		res += "Sol. Rec. No Final: " + Ej1.ej1RNF(a, b, c) + "\n";
		res += "Sol. Iterativa: " + Ej1.ej1Iter(a, b, c) + "\n";
		res += "Sol. Rec. Final: " + Ej1.ej1RF(a, b, c) + "\n";
		res += "Sol. Funcional: " + Ej1.ej1Fun(a, b, c) + "\n";
		System.out.println(res);
	}
	public static void main(String[] args) throws IOException {
		// Leer fichero
		
		List<String> lines = Files2.linesFromFile(".\\ficheros\\PI2Ej1DatosEntrada.txt");
		List<List<Integer>> file = lines.stream().
								map(
										x -> Arrays.asList(x.split(",")).stream().
											map(k -> Integer.parseInt(k)).
											collect(Collectors.toList())).
								collect(Collectors.toList());
		System.out.println("######################################################");
		System.out.println("####################Ejercicio 1#######################");
		System.out.println("###############Fichero DatosEntrada1##################");
		System.out.println("######################################################");
		for(List<Integer> line: file) {
			llamaFunciones(line);
		}
		
	}

}
