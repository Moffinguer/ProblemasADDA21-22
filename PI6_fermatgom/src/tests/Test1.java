package tests;

import java.io.IOException;
import java.util.List;

import modelos.Modelo1;
public class Test1 {

	public static void main(String[] args) throws IOException {
		String header = "./ficheros/PI6Ej1DatosEntrada";
		for(int i = 1; i < 3; i++) {
			Modelo1.GrafoVirtual1.solutionAStar(header + i + ".txt", i);
			Modelo1.GrafoVirtual1.solutionBT(header + i + ".txt", i);
			Modelo1.GrafoVirtual1.solutionPD(header + i + ".txt", i);
		}
	}

}
