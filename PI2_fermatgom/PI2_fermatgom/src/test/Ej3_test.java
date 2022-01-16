package test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ejercicios.Ej3;
import us.lsi.common.Files2;

public class Ej3_test {

	public static void llamaFunciones(String lines) {
		List<Integer> listOfValues = Arrays.asList(lines.split("#")[0].split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
		List<Integer> extremes = Arrays.asList(lines.split("#")[1].split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
		String res = "Rango: " + listOfValues + "\n";
		res += "Intervalo: [" + extremes.get(0) + ", " + extremes.get(1) + ")\n";
		res += "Solución: " + Ej3.ej3(listOfValues, extremes.get(0), extremes.get(1)).toString()+ "\n";
		System.out.println(res);
	}
	public static void main(String[] args) throws IOException {
		// Leer fichero
			List<String> file = Files2.linesFromFile(".\\ficheros\\PI2Ej3DatosEntrada.txt");
			System.out.println("######################################################");
			System.out.println("####################Ejercicio 3#######################");
			System.out.println("###############Fichero DatosEntrada3##################");
			System.out.println("######################################################");
			for(String i: file) {
				llamaFunciones(i);
			}
	}

}
