package backTracking;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import modelos.Modelo1;
import modelos.Modelo1.GrafoVirtual1;
import modelos.Modelo1.Heuristica1;

public class Ej1 {
	public static class Ej1BT{
		private GrafoVirtual1 vertice;
		private Double valorAcumulado;
		private List<Integer> acctions;
		private List<GrafoVirtual1> vertices;
		
		private  Ej1BT(GrafoVirtual1 vertice, Double valAcum, List<Integer> acctions, List<GrafoVirtual1> vertices) {
			this.vertice = vertice;
			this.valorAcumulado = valAcum;
			this.acctions = acctions;
			this.vertices = vertices;
		}
		
		public static Ej1BT of(GrafoVirtual1 vertex) {
			var vt = new LinkedList<GrafoVirtual1>();
			vt.add(vertex);
			return new Ej1BT(vertex, 0D, new LinkedList<>(), vt);
		}
		void forward(Integer a) {
			this.acctions.add(a);
			GrafoVirtual1 vcn = this.vertice.neighbor(a);
			this.vertices.add(vcn);
			Integer anotherFile = a.equals(-1) ? 0 : 1;
			this.valorAcumulado += anotherFile;
			this.vertice = vcn;
		}
		
		void back(Integer a) {
			this.acctions.remove(this.acctions.size() - 1);
			this.vertices.remove(this.vertices.size() - 1);
			this.vertice = this.vertices.get(this.vertices.size() - 1);
			Integer anotherFile = a.equals(-1) ? 0 : 1;
			this.valorAcumulado -= anotherFile;
		}
		List<Integer> solucion(){
			return this.acctions;
		}
		public GrafoVirtual1 vertice() {
			return this.vertice;
		}
		
		public Double valAcum() {
			return this.valorAcumulado;
		}
	}
	
	public static GrafoVirtual1 start;
	public static Ej1BT estado;
	public static Set<List<Integer>> soluciones = new HashSet<List<Integer>>();
	public static List<Integer> mejorSol = null;
	public static Double maxValue = Double.MIN_VALUE;
	
	public static List<Integer> bt(String route) {
		Modelo1.readFile(route);
		mejorSol = null;
		maxValue = Double.MIN_VALUE;
		start = GrafoVirtual1.firstIteration();
		estado = Ej1BT.of(start);
		soluciones = new HashSet<List<Integer>>();
		btm();
		return mejorSol;
	}
	public static void btm() {
		boolean checkIsSol = GrafoVirtual1.goal().test(estado.vertice);
		if(checkIsSol) {
			Double valEstado = estado.valAcum();
			if(valEstado.compareTo(maxValue) > 0) {
				maxValue = valEstado;
				List<Integer> mejorSolucion = estado.solucion();
				soluciones.add(new LinkedList<>(mejorSolucion));
				mejorSol = new LinkedList<>(mejorSolucion);
			}
		}else {
			List<Integer> alternativas = estado.vertice().actions();
			for(Integer a : alternativas) {
				Double cota = 1 + estado.valorAcumulado + Heuristica1.heuristic(estado.vertice().neighbor(a), null, null);
				if(cota.compareTo(maxValue) > 0) {
					estado.forward(a);
					btm();
					estado.back(a);
				}
			}
		}
	}
}
