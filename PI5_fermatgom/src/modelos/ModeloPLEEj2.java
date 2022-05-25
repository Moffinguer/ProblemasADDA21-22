package modelos;

import java.util.LinkedList;
import java.util.List;

import us.lsi.common.Files2;

public class ModeloPLEEj2 {
	public record Candidato(String name, List<String> cualidades, Double costo, List<String> incompatible, Integer valoracion) {
		public static Candidato make(String args) {
			String name; Double costo; Integer valoracion;
			List<String> cualidades, incompatible;
			String[] temp = args.split(":");
			name = temp[0].replaceAll(" ", "");
			temp = temp[1].split(";");
			cualidades = List.of(temp[0].replaceAll(" ", "").split(","));
			costo = Double.valueOf(temp[1].replaceAll(" ", ""));
			valoracion = Integer.parseInt(temp[2].replaceAll(" ", ""));
			incompatible = List.of(temp[3].replaceAll(" ", "").split(","));
			return new Candidato(name, cualidades, costo, incompatible, valoracion);	
		}
	}
	public static List<Candidato> candidatos;
	public static Double presupuesto;
	public static List<String> cualidades;
	public static void readFile(String rute) {
		List<String> lines = Files2.linesFromFile(rute);
		cualidades = new LinkedList<>();
		candidatos = new LinkedList<>();
		cualidades.addAll(List.of(lines.get(0).split(":")[1].replaceAll(" ", "").split(",")));
		presupuesto = Double.valueOf(lines.get(1).split(":")[1].replaceAll(" ", ""));
		for(String line : lines.subList(2, lines.size())) candidatos.add(Candidato.make(line));
	}
	public static Integer esInCompatible(Integer i, Integer j) {
		return  candidatos.get(i).incompatible.contains(candidatos.get(j).name) ? 1 : 0;	
	}
	public static Double  presupuestoMinimo() { return presupuesto;}
	public static Integer numOfCandidatos() { return candidatos.size(); }
	public static Double  getCandidatoSueldo(Integer i) { return candidatos.get(i).costo;}
	public static Integer tieneCualidad(Integer i, Integer j) { 
	return  candidatos.get(i).cualidades.contains(cualidades.get(j)) ? 1 : 0;	
	}
	public static Integer numCualidades() { return cualidades.size();}
	public static Integer getValoracion(Integer i) { return candidatos.get(i).valoracion;}
}
