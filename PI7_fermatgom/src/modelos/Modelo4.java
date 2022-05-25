package modelos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import modelos.Modelo1.ConjuntoDatosEdge1;
import modelos.Modelo1.File;
import modelos.Modelo1.GrafoVirtual1;
import modelos.Modelo1.Heuristica1;
import modelos.Modelo1.Memory;
import modelos.Modelo3.ConjuntoDatosEdge3;
import modelos.Modelo3.GrafoVirtual3;
import modelos.Modelo3.Heuristica3;
import us.lsi.colors.GraphColors;
import us.lsi.common.Files2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.VirtualVertex;

public class Modelo4 {
	public record Heuristica4() {
		public static Double heuristic(GrafoVirtual4 v1, Predicate<GrafoVirtual4> goal, GrafoVirtual4 v2) {
			Double numLlenos = v1.capRestante.stream().filter(x -> !x.equals(0.)).collect(Collectors.toList()).size() * 1.;
			if(numLlenos.equals(0.)) {
				return  0.;
			}else {
				return (Modelo4.numElementos() - v1.index()) / numLlenos;
			}
		}
	}
	public record GrafoVirtual4(Integer index, List<Double> capRestante) implements VirtualVertex<GrafoVirtual4, ConjuntoDatosEdge4, Integer>{
		public static GrafoVirtual4 firstIteration() {
			List<Double> capRestante = Modelo4.contenedores.stream().map(x -> x.capacidad).collect(Collectors.toList());
			return new GrafoVirtual4(0, capRestante);
		}
		@Override
		public List<Integer> actions() {
			if(index.equals(Modelo4.numElementos())) return new LinkedList<>();
			List<Integer> options = new LinkedList<Integer>();
			for(int i = 0; i < Modelo4.numContenedores(); i++) {
				if(Modelo4.elementoEnContenedor(index, i).equals(1) && !this.capRestante.get(i).equals(0.)) {
					if(capRestante.get(i).compareTo(Modelo4.getPeso(index)) >= 0) {
						options.add(i);
					}
				}
			}
			if(index.compareTo(Modelo4.numElementos() - 1) < 0) options.add(-1);
//			options.sort(Comparator.reverseOrder());
			return options;
		}
		public static Predicate<GrafoVirtual4> goal(){
			return x -> x.index.equals(Modelo4.numElementos());
		}
		@Override
		public GrafoVirtual4 neighbor(Integer a) {
			List<Double> tempCap = new LinkedList<>(this.capRestante);
			if(!a.equals(-1)) {
				tempCap.set(a, tempCap.get(a) - Modelo4.getPeso(index));
			}
			return new GrafoVirtual4(index + 1,tempCap);
		}

		@Override
		public ConjuntoDatosEdge4 edge(Integer a) {
			// TODO Auto-generated method stub
			 return ConjuntoDatosEdge4.of(this, this.neighbor(a), 1);
		}
		public static void solutionBT(String route, int i) {
			Modelo4.readFile(route);
			GrafoVirtual4 start = GrafoVirtual4.firstIteration();
			Predicate<GrafoVirtual4> goal = GrafoVirtual4.goal();
			EGraph<GrafoVirtual4, ConjuntoDatosEdge4> graph;
			System.out.println("####ALGORITMO BT" + i + "####");
			graph = SimpleVirtualGraph.last(start, goal, x -> 1. * x.capRestante.stream().filter(v -> v.equals(0.)).collect(Collectors.toList()).size());
			BackTracking<GrafoVirtual4, ConjuntoDatosEdge4, List<GrafoVirtual4>> bt = BackTracking.
			of(graph,
				Heuristica4::heuristic,
				x -> x.getVertexList(),
				BTType.Max);
			bt.search();
			System.out.println(bt.getSolution());
		System.out.println("####HECHO####");
		}
		public static void solutionPD(String route, int i) {
			Modelo4.readFile(route);
			GrafoVirtual4 start = GrafoVirtual4.firstIteration();
			Predicate<GrafoVirtual4> goal = GrafoVirtual4.goal();
			EGraph<GrafoVirtual4, ConjuntoDatosEdge4> graph;
			System.out.println("####ALGORITMO PD" + i +  "####");
			graph = SimpleVirtualGraph.last(start, goal, x -> 1. * x.capRestante.stream().filter(v -> v.equals(0.)).collect(Collectors.toList()).size());
			DynamicProgrammingReduction<GrafoVirtual4, ConjuntoDatosEdge4> pdr = DynamicProgrammingReduction
					.of(graph,Heuristica4::heuristic , PDType.Max);
			System.out.println(pdr.search().get().getEndVertex());
			System.out.println("####HECHO####");
		}
		public static void solutionAStar(String route, int i) {
			Modelo4.readFile(route);
			GrafoVirtual4 start = GrafoVirtual4.firstIteration();
			Predicate<GrafoVirtual4> goal = GrafoVirtual4.goal();
			EGraph<GrafoVirtual4, ConjuntoDatosEdge4> graph;
			System.out.println("####ALGORITMO A STAR" + i + "####");
			graph = SimpleVirtualGraph.last(start, goal, x -> 1. * x.capRestante.stream().filter(v -> v.equals(0.)).collect(Collectors.toList()).size());
			AStar<GrafoVirtual4, ConjuntoDatosEdge4> as = AStar.of(graph,Heuristica4::heuristic , AStarType.Max);
			System.out.println(as.search().get().getVertexList());
			System.out.println("####HECHO####");
		}
		
	}
	public record ConjuntoDatosEdge4(GrafoVirtual4 source,GrafoVirtual4 target,Integer action, Double weight) 
    implements SimpleEdgeAction<GrafoVirtual4, Integer>{
		public static ConjuntoDatosEdge4 of(GrafoVirtual4 c1, GrafoVirtual4 c2, Integer action) {
			ConjuntoDatosEdge4 a = new ConjuntoDatosEdge4(c1, c2, action, action * 1.0);
			return a;
		}
		
	}
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
