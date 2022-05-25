package test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ejercicios.Ej3;
import us.lsi.common.Files2;

public class Ej3_test {

	public static void llamaFunciones(List<Integer> lines) {
		String res = "Los datos que entran son: " + lines + "\n";
		res += "Iterativa (while): " + Ej3.ejercicio3I(lines.get(0), lines.get(1))+ "\n";
		res += "Recursiva final: " + Ej3.ejercicio3(lines.get(0), lines.get(1))+ "\n";
		res += "Funcional: " + Ej3.ejercicio3F(lines.get(0), lines.get(1))+ "\n";
		System.out.println(res);
	}
	public static void main(String[] args) throws IOException {
		// Leer fichero
			List<String> lines = Files2.linesFromFile(".\\ficheros\\PI1E3_DatosEntrada.txt");
			List<List<Integer>> file = lines.stream().map(x -> Arrays.asList(x.split(",")).stream().map(j -> Integer.parseInt(j)).collect(Collectors.toList())).collect(Collectors.toList());
			System.out.println("######################################################");
			System.out.println("####################Ejercicio 3#######################");
			System.out.println("###############Fichero DatosEntrada3##################");
			System.out.println("######################################################");
			for(List<Integer> i: file) {
				llamaFunciones(i);
			}
	}

}
