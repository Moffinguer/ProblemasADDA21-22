package tests;

import java.io.IOException;

import modelos.Modelo3;


public class Test3 {
	public static void main(String[] args) throws IOException {
	    String header = "./ficheros/PI6Ej3DatosEntrada";
		for(int i = 1; i < 3; i++) {
			Modelo3.GrafoVirtual3.solutionPD(header + i + ".txt", i);
			Modelo3.GrafoVirtual3.solutionAStar(header + i + ".txt", i);
			Modelo3.GrafoVirtual3.solutionBT(header + i + ".txt", i);
		}
	}
}
