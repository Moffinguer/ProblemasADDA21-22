package tests;

import java.io.IOException;

import modelos.Modelo3;
import modelos.Modelo4;

public class Test4 {
	public static void main(String[] args) throws IOException {
	    String header = "./ficheros/PI6Ej4DatosEntrada";
		for(int i = 1; i < 3; i++) {
			Modelo4.GrafoVirtual4.solutionPD(header + i + ".txt", i);
			Modelo4.GrafoVirtual4.solutionAStar(header + i + ".txt", i);
			Modelo4.GrafoVirtual4.solutionBT(header + i + ".txt", i);
		}
	}
}
