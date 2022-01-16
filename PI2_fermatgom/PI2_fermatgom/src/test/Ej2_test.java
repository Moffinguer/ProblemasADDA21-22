package test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import ejercicios.Ej2;
import us.lsi.common.Files2;

public class Ej2_test {

	public static void llamaFunciones(String[][] lines) {
		String res = "Las cadenas respondidas son: " ;
		res += "\n";
		res += "Recursiva final: \n";
		List<String> sentence= Ej2.ej2Recursivo(lines);
		for(int i = 0; i < sentence.size(); i++) 
			res += i + 1 + ") " + sentence.get(i) + "\n"; 
		
		System.out.println(res);
	}
	public static void main(String[] args) throws IOException {
		// Leer fichero
		for(int j = 1; j < 3; j++) {
			List<String[]> lines = Files2.linesFromFile(".\\ficheros\\PI2Ej2DatosEntrada" + j +".txt").stream().map(x -> x.split(" ")).collect(Collectors.toList());
			String[][] file = new String[lines.size()][lines.get(j).length];
			for(int i = 0; i < file.length; i++) {
				for(int k = 0; k < file[i].length; k++) {
					file[i][k] = lines.get(i)[k];
				}
			}
			System.out.println("######################################################");
			System.out.println("####################Ejercicio 2#######################");
			System.out.println("###############Fichero DatosEntrada" + j + "##################");
			System.out.println("######################################################");
			llamaFunciones(file);
		}
	}
}
