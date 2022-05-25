package backTracking;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import modelos.Modelo2;
import modelos.Modelo2.GrafoVirtual2;
import modelos.Modelo2.Heuristica2;

public class Ej2 {
	public static class Ej2BT{
		private GrafoVirtual2 vertice;
		private Double valorAcumulado;
		private List<Integer> acctions;
		private List<GrafoVirtual2> vertices;
		
		private  Ej2BT(GrafoVirtual2 vertice, Double valAcum, List<Integer> acctions, List<GrafoVirtual2> vertices) {
			this.vertice = vertice;
			this.valorAcumulado = valAcum;
			this.acctions = acctions;
			this.vertices = vertices;
		}
		
		public static Ej2BT of(GrafoVirtual2 vertex) {
			var vt = new LinkedList<GrafoVirtual2>();
			vt.add(vertex);
			return new Ej2BT(vertex, 0D, new LinkedList<>(), vt);
		}
		void forward(Integer a) {
			this.acctions.add(a);
			GrafoVirtual2 vcn = this.vertice.neighbor(a);
			this.vertices.add(vcn);
			Double addSueldo =a * Modelo2.getValoracion(this.vertice.index());
			this.valorAcumulado += addSueldo;
			this.vertice = vcn;
		}
		
		void back(Integer a) {
			this.acctions.remove(this.acctions.size() - 1);
			this.vertices.remove(this.vertices.size() - 1);
			this.vertice = this.vertices.get(this.vertices.size() - 1);
			Double addSueldo = a * Modelo2.getValoracion(this.vertice.index());
			this.valorAcumulado -= addSueldo;
		}
		List<Integer> solucion(){
			return this.acctions;
		}
		public GrafoVirtual2 vertice() {
			return this.vertice;
		}
		
		public Double valAcum() {
			return this.valorAcumulado;
		}
	}
	
	public static GrafoVirtual2 start;
	public static Ej2BT estado;
	public static Set<List<Integer>> soluciones = new HashSet<List<Integer>>();
	public static List<Integer> mejorSol = null;
	public static Double maxValue = Double.MIN_VALUE;
	
	public static List<Integer> bt(String route) {
		Modelo2.readFile(route);
		mejorSol = null;
		maxValue = Double.MIN_VALUE;
		start = GrafoVirtual2.firstIteration();
		estado = Ej2BT.of(start);
		soluciones = new HashSet<List<Integer>>();
		btm();
		System.out.println(maxValue);
		return mejorSol;
	}
	public static void btm() {
		boolean checkIsSol = GrafoVirtual2.goal().test(estado.vertice) ;
		if(checkIsSol) {
			Double valEstado = estado.valAcum();
			if(valEstado.compareTo(maxValue) > 0 && GrafoVirtual2.constraint().test(estado.vertice)) {
				maxValue = valEstado;
				List<Integer> mejorSolucion = estado.solucion();
				soluciones.add(new LinkedList<>(mejorSolucion));
				mejorSol = new LinkedList<>(mejorSolucion);
			}
		}else {
			List<Integer> alternativas = estado.vertice().actions();
			alternativas.sort(Comparator.reverseOrder());
			for(Integer a : alternativas) {
				Double cota = estado.valorAcumulado + a * Modelo2.getValoracion(estado.vertice.index()) + Heuristica2.heuristic(estado.vertice().neighbor(a), null, null);
				if(cota.compareTo(maxValue) > 0) {
					estado.forward(a);
					btm();
					estado.back(a);
				}
			}
		}
	}
}
