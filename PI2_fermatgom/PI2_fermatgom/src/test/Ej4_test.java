package test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import ejercicios.Ej4;
import us.lsi.common.*;

public class Ej4_test {

	public static void llamaFunciones(Integer line) {
		String res = "Iteración: " + line + "\n";
		res += "Iterativa: " + Ej4.ej4Iter(line) + "\n";
		res += "Recursiva sin Memoria: " + Ej4.ej4Rec(line)+ "\n";
		res += "Recursiva Con Memoria: " + Ej4.ej4RecM(line)+ "\n";
		System.out.println(res);
	}
	public static void main(String[] args) throws IOException {
		// Leer fichero
			List<String> lines = Files2.linesFromFile(".\\ficheros\\PI2Ej4DatosEntrada.txt");
			List<Integer> file = lines.stream().map(x -> Integer.parseInt(x.substring(2, x.length()))).collect(Collectors.toList());
			System.out.println("######################################################");
			System.out.println("####################Ejercicio 4#######################");
			System.out.println("###############Fichero DatosEntrada4##################");
			System.out.println("######################################################");
			for(Integer i: file) {
				llamaFunciones(i);
			}
	}

}
