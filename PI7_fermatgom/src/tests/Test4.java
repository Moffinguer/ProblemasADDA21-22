package tests;

import java.io.IOException;

import modelos.Modelo3;
import modelos.Modelo4;
import progDinamica.Ej4;

public class Test4 {
	public static void main(String[] args) throws IOException {
	    String header = "./ficheros/PI7Ej4DatosEntrada";
		for(int i = 1; i < 3; i++) {
			System.out.println(Ej4.pd(header + i + ".txt"));
		}
	}
}
