package test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ejercicios.Ej1;
import us.lsi.common.Files2;

public class Ej1_test {

	public static void llamaFunciones(List<String> line) {
		Predicate<String> pS = t -> t.toLowerCase().contains("a") || t.toLowerCase().contains("e") || t.toLowerCase().contains("o");
		Predicate<Integer> pI = t -> t % 2 == 0;
		Function<String,Integer> f = t -> t.length();
		String res = "Los datos que entran son: " + line + "\n";
		res += "Iterativa (while): " + Ej1.ejercicio1I(line, pS, pI, f)+ "\n";
		res += "Recursiva final: " + Ej1.ejercicio1(line, pS, pI, f)+ "\n";
		res += "Funcional: " + Ej1.ejercicio1F(line, pS, pI, f)+ "\n";
		System.out.println(res);
	}
	public static void main(String[] args) throws IOException {
		// Leer fichero
		
		List<String> lines = Files2.linesFromFile(".\\ficheros\\PI1E1_DatosEntrada.txt");
		lines = lines.subList(3, lines.size());
		List<List<String>> file = lines.stream().map(x -> Arrays.asList(x.split(","))).collect(Collectors.toList());
		System.out.println("######################################################");
		System.out.println("####################Ejercicio 1#######################");
		System.out.println("###############Fichero DatosEntrada1##################");
		System.out.println("######################################################");
		for(List<String> line: file) {
			llamaFunciones(line);
		}
		
	}

}
