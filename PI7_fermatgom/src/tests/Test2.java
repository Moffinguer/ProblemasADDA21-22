package tests;

import java.io.IOException;

import backTracking.Ej2;
import modelos.Modelo2;


public class Test2 {

	public static void main(String[] args) throws IOException {
	    String header = "./ficheros/PI7Ej2DatosEntrada";
		for(int i = 1; i < 3; i++) {
			System.out.println(Ej2.bt(header + i + ".txt"));
		}
	}

}
