package tests;

import java.io.IOException;

import modelos.Modelo2;


public class Test2 {

	public static void main(String[] args) throws IOException {
	    String header = "./ficheros/PI6Ej2DatosEntrada";
		for(int i = 1; i < 3; i++) {
			Modelo2.GrafoVirtual2.solutionPD(header + i + ".txt", i);
			Modelo2.GrafoVirtual2.solutionAStar(header + i + ".txt", i);
			Modelo2.GrafoVirtual2.solutionBT(header + i + ".txt", i);
		}
	}

}
