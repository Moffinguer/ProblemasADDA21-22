package test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ejercicios.Ej2;
import us.lsi.common.Files2;

public class Ej2_test {

	public static void llamaFunciones(List<List<String>> lines) {
		String res = "Los datos que entran son: " + lines + "\n";
		res += "Iterativa (while): " + Ej2.ejercicio2I(lines)+ "\n";
		res += "Recursiva final: " + Ej2.ejercicio2(lines)+ "\n";
		res += "Funcional: " + Ej2.ejercicio2F(lines)+ "\n";
		System.out.println(res);
	}
	public static void main(String[] args) throws IOException {
		// Leer fichero
		for(int j = 1; j < 3; j++) {
			List<String> lines = Files2.linesFromFile(".\\ficheros\\PI1E2_DatosEntrada" + j +".txt");
			List<List<String>> file = lines.stream().map(x -> Arrays.asList(x.split(","))).collect(Collectors.toList());
			System.out.println("######################################################");
			System.out.println("####################Ejercicio 2#######################");
			System.out.println("###############Fichero DatosEntrada" + j + "##################");
			System.out.println("######################################################");
			llamaFunciones(file);
		}
	}
}
