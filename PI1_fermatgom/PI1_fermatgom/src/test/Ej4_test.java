package test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ejercicios.Ej4;
import us.lsi.common.Files2;

public class Ej4_test {

	public static void llamaFunciones(List<Double> lines) {
		String res = "Los datos que entran son: " + lines + "\n";
		res += "Iterativa (while): " + Ej4.ejercicio4I(lines.get(0), lines.get(1))+ "\n";
		res += "Recursiva final: " + Ej4.ejercicio4(lines.get(0), lines.get(1))+ "\n";
		res += "Funcional: " + Ej4.ejercicio4F(lines.get(0), lines.get(1))+ "\n";
		System.out.println(res);
	}
	public static void main(String[] args) throws IOException {
		// Leer fichero
			List<String> lines = Files2.linesFromFile(".\\ficheros\\PI1E4_DatosEntrada.txt");
			List<List<Double>> file = lines.stream().map(x -> Arrays.asList(x.split(",")).stream().map(j -> Double.parseDouble(j)).collect(Collectors.toList())).collect(Collectors.toList());
			System.out.println("######################################################");
			System.out.println("####################Ejercicio 4#######################");
			System.out.println("###############Fichero DatosEntrada4##################");
			System.out.println("######################################################");
			for(List<Double> i: file) {
				llamaFunciones(i);
			}
	}

}
