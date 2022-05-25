package tests;

import java.io.IOException;

import modelos.Modelo3;
import progDinamica.Ej3;


public class Test3 {
	public static void main(String[] args) throws IOException {
	    String header = "./ficheros/PI7Ej3DatosEntrada";
		for(int i = 1; i < 3; i++) {
			System.out.println(Ej3.pd(header + i + ".txt"));
		}
	}
}
