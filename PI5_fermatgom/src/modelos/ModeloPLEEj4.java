package modelos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import modelos.ModeloPLEEj1.File;
import modelos.ModeloPLEEj1.Memory;
import us.lsi.common.Files2;

public class ModeloPLEEj4 {
	public record Contenedor(String name, Double capacidad, String tipo) {
		public static Contenedor make(String args) {
			String name;
			Double capacidad; String tipo;
			args = args.replaceAll(" ", "");
			String[] temp = args.split(":");
			name = temp[0];
			temp = temp[1].split(";");
			capacidad = Double.parseDouble(temp[0].split("=")[1]);
			tipo = temp[1].split("=")[1];
			return new Contenedor(name, capacidad, tipo);	
		}
	}
	public record Elemento(String name, Double peso, List<String> contenedoresIncompatibles) {
		public static Elemento make(String args) {
			String name; Double peso; List<String> contenedores;
			args = args.replaceAll(" ", "");
			String[] temp = args.split(":");
			name = temp[0];
			temp = temp[1].split(";");
			peso = Double.parseDouble(temp[0]);
			contenedores = List.of(temp[1].split(","));
			return new Elemento(name, peso, contenedores);	
		}
	}
	public static List<Contenedor> contenedores;
	public static List<Elemento> elementos;
	public static void readFile(String rute) {
		List<String> lines = Files2.linesFromFile(rute);
		contenedores = new LinkedList<>();
		elementos = new LinkedList<>();
		int i;
		for(i = 1; !lines.get(i).contains("//"); i++) contenedores.add(Contenedor.make(lines.get(i)));
		for(i = i + 1 ; i < lines.size(); i++) elementos.add(Elemento.make(lines.get(i)));
	}
	public static Integer numContenedores() { return contenedores.size();}
	public static Integer numElementos() { return elementos.size();}
	public static Integer elementoEnContenedor(Integer i, Integer j) {
		return elementos.get(i).contenedoresIncompatibles.contains(contenedores.get(j).tipo) ? 1 : 0;
	}
	public static Double getCapacidad(Integer j) {
		return contenedores.get(j).capacidad;
	}
	public static Double getPeso(Integer i) { return elementos.get(i).peso;}
}
