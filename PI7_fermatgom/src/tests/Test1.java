package tests;

import java.io.IOException;
import java.util.List;

import modelos.Modelo1;
public class Test1 {

	public static void main(String[] args) throws IOException {
		String header = "./ficheros/PI7Ej1DatosEntrada";
		for(int i = 1; i < 3; i++) {
			System.out.println(backTracking.Ej1.bt(header + i + ".txt"));
		}
	}

}
