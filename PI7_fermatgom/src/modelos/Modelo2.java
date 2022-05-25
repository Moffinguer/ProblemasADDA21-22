package modelos;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import modelos.Modelo1.ConjuntoDatosEdge1;
import modelos.Modelo1.GrafoVirtual1;
import modelos.Modelo1.Heuristica1;
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

public class Modelo2 {
	public record Heuristica2() {
		public static Double heuristic(GrafoVirtual2 v1, Predicate<GrafoVirtual2> goal, GrafoVirtual2 v2) {
			return Modelo2.numOfCandidatos() - v1.index * 1.;
		}
	}
	public record ConjuntoDatosEdge2(GrafoVirtual2 source,GrafoVirtual2 target,Integer action, Double weight) 
    implements SimpleEdgeAction<GrafoVirtual2, Integer>{
		public static ConjuntoDatosEdge2 of(GrafoVirtual2 c1, GrafoVirtual2 c2, Integer action) {
			ConjuntoDatosEdge2 a = new ConjuntoDatosEdge2(c1, c2, action, action * Modelo2.getValoracion(c1.index));
			return a;
		}
		
	}
	public record GrafoVirtual2(Integer index, Set<String> incompatibles, Set<String> cualidades, Double presupuesto) implements VirtualVertex<GrafoVirtual2, ConjuntoDatosEdge2, Integer>{
		public static GrafoVirtual2 firstIteration() {
			return new GrafoVirtual2(0, new HashSet<String>(), Modelo2.cualidades.stream().collect(Collectors.toSet()),  0.);
		}
		@Override
		public List<Integer> actions() {
			if(Modelo2.numOfCandidatos().equals(index)) {
				return new LinkedList<Integer>();
			}else {
				List<Integer> options = new LinkedList<Integer>();
				if(conditions(index)) {
					if(index.compareTo(Modelo2.numOfCandidatos() - 1) < 0) {
						options.addAll(List.of(0,1));
					}else{
						options.add(1);
					}
				}else {
					options.add(0);
				}
				return options;
			}
		}
		public static Predicate<GrafoVirtual2> goal(){
			return x -> x.index.equals(Modelo2.numOfCandidatos());
		}
		public static Predicate<GrafoVirtual2> constraint(){
			return x -> x.cualidades.isEmpty();
		}
		@Override
		public GrafoVirtual2 neighbor(Integer a) {
			// TODO Auto-generated method stub
			Set<String> tempElegidos = new HashSet<String>(incompatibles);
			Set<String> tempCualidades = new HashSet<>(this.cualidades);
			Double press = this.presupuesto;
			if(a.equals(1)) {
				tempElegidos.addAll(Modelo2.candidatos.get(index).incompatible);
				press += Modelo2.getCandidatoSueldo(a);
				tempCualidades.removeAll(Modelo2.candidatos.get(index).cualidades);
			}
			return new GrafoVirtual2(index + 1, tempElegidos,tempCualidades ,press);
		}
		private boolean conditions(int i) {
			Set<String> elegidosTemp = new HashSet<String>(incompatibles);
			return !elegidosTemp.contains(Modelo2.candidatos.get(i).name)
					&&
					presupuesto.compareTo(Modelo2.presupuestoMinimo()) <= 0
					&&
					Modelo2.candidatos.get(i).cualidades.stream().anyMatch(x -> Modelo2.cualidades.contains(x))
					&&
					this.cualidades.stream().anyMatch(x -> Modelo2.candidatos.get(i).cualidades.contains(x));
		}
		public static void solutionBT(String route, int i) {
			Modelo2.readFile(route);
			GrafoVirtual2 start = GrafoVirtual2.firstIteration();
			Predicate<GrafoVirtual2> goal = GrafoVirtual2.goal();
			EGraph<GrafoVirtual2, ConjuntoDatosEdge2> graph;
			System.out.println("####ALGORITMO BT" + i + "####");
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight);
			BackTracking<GrafoVirtual2, ConjuntoDatosEdge2, List<GrafoVirtual2>> bt = BackTracking.
			of(graph,
				Heuristica2::heuristic,
				x -> x.getVertexList(),
				BTType.Max);
			bt.search();
			System.out.println(bt.getSolution());
		System.out.println("####HECHO####");
		}
		public static void solutionPD(String route, int i) {
			Modelo2.readFile(route);
			GrafoVirtual2 start = GrafoVirtual2.firstIteration();
			Predicate<GrafoVirtual2> goal = GrafoVirtual2.goal();
			EGraph<GrafoVirtual2, ConjuntoDatosEdge2> graph;
			System.out.println("####ALGORITMO PD" + i + "####");
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight);
			DynamicProgrammingReduction<GrafoVirtual2, ConjuntoDatosEdge2> pdr = DynamicProgrammingReduction
					.of(graph,Heuristica2::heuristic , PDType.Max);
			System.out.println(pdr.search().get().getEdgeList().stream().map(x -> x.weight).collect(Collectors.toList()));
			System.out.println("HECHO");
		}
		public static void solutionAStar(String route, int i) {
			Modelo2.readFile(route);
			GrafoVirtual2 start = GrafoVirtual2.firstIteration();
			Predicate<GrafoVirtual2> goal = GrafoVirtual2.goal();
			EGraph<GrafoVirtual2, ConjuntoDatosEdge2> graph;
			System.out.println("####ALGORITMO A STAR" + i + "####");
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight);
			AStar<GrafoVirtual2, ConjuntoDatosEdge2> as = AStar.of(graph,Heuristica2::heuristic , AStarType.Max);
			System.out.println(as.search().get().getEdgeList().stream().map(x -> x.weight).collect(Collectors.toList()));
			System.out.println("HECHO");
		}
		@Override
		public ConjuntoDatosEdge2 edge(Integer a) {
			// TODO Auto-generated method stub
			return ConjuntoDatosEdge2.of(this, this.neighbor(a), a);
		}

	}
	public record Candidato(String name, List<String> cualidades, Double costo, List<String> incompatible, Double valoracion) {
		public static Candidato make(String args) {
			String name; Double costo; Double valoracion;
			List<String> cualidades, incompatible;
			String[] temp = args.split(":");
			name = temp[0].replaceAll(" ", "");
			temp = temp[1].split(";");
			cualidades = List.of(temp[0].replaceAll(" ", "").split(","));
			costo = Double.valueOf(temp[1].replaceAll(" ", ""));
			valoracion = Double.valueOf(temp[2].replaceAll(" ", ""));
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
	public static Double getValoracion(Integer i) { return candidatos.get(i).valoracion;}
}
