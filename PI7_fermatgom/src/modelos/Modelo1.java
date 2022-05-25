package modelos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;
import us.lsi.colors.GraphColors;
import us.lsi.common.Files2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.VirtualVertex;

public class Modelo1 {
	
	public record Heuristica1() {
		public static Double heuristic(GrafoVirtual1 v1, Predicate<GrafoVirtual1> goal, GrafoVirtual1 v2) {
			return Modelo1.getNumArchivos() * 1. - v1.index; 
		}
	}
	public record GrafoVirtual1(Integer index, List<Double> capRestante) implements VirtualVertex<GrafoVirtual1, ConjuntoDatosEdge1, Integer>{

		public static GrafoVirtual1 firstIteration() {
			List<Double> capRestante = new ArrayList<Double>(Modelo1.getNumMem());
			Integer size = Modelo1.getNumMem();
			for(int i = 0; i < size; i++) capRestante.add(Modelo1.getCapArchivo(i));
			return new GrafoVirtual1(0, capRestante);
		}
		public static Predicate<GrafoVirtual1> goal(){
			return x -> x.index.equals(Modelo1.getNumArchivos());
		}
		@Override
		public List<Integer> actions() {
			List<Integer> options;
			if(index > Modelo1.getNumArchivos() - 1) {
				options = new LinkedList<Integer>();
			}else {
				options = new LinkedList<Integer>();
				for(int i = 0; i < Modelo1.getNumMem(); i++) if(conditions(i)) options.add(i);
				options.add(-1);
			}
			return options;
		}
		private Predicate<Double> checkTamanios(Double archTam, Double archTamMax){
			Predicate<Double> temp = x -> archTam.compareTo(archTamMax) <= 0;
			return temp;
		}
		private Predicate<Double> checkEntra(Double archTam, Double capMemoria){
			return x -> archTam.compareTo(capMemoria) <= 0;
		}
		private boolean conditions(int i) {
			return this.checkTamanios(Modelo1.getTamArch(index), Modelo1.getTamMaxArchivo(i)).test(0.)
					&&
					this.checkEntra(Modelo1.getTamArch(index), capRestante.get(i)).test(0.)
					;
		}
		@Override
		public GrafoVirtual1 neighbor(Integer i) {
			// TODO Auto-generated method stub
			List<Double> tempCap= new LinkedList<>(capRestante);
			if(!i.equals(-1)) tempCap.set(i, tempCap.get(i) - Modelo1.getTamArch(index));
			return new GrafoVirtual1(index + 1, tempCap);
		}
		public static void solutionBT(String route, int i) {
			Modelo1.readFile(route);
			GrafoVirtual1 start = GrafoVirtual1.firstIteration();
			Predicate<GrafoVirtual1> goal = GrafoVirtual1.goal();
			EGraph<GrafoVirtual1, ConjuntoDatosEdge1> graph;
			System.out.println("####ALGORITMO BT" + i + "####");
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight());
			BackTracking<GrafoVirtual1, ConjuntoDatosEdge1, List<GrafoVirtual1>> bt = BackTracking.
			of(graph,
				Heuristica1::heuristic,
				x -> x.getVertexList(),
				BTType.Max);
			bt.search();
			System.out.println(bt.getSolution());
		System.out.println("####HECHO####");
		}
		public static void solutionPD(String route, int i) {
			Modelo1.readFile(route);
			GrafoVirtual1 start = GrafoVirtual1.firstIteration();
			Predicate<GrafoVirtual1> goal = GrafoVirtual1.goal();
			EGraph<GrafoVirtual1, ConjuntoDatosEdge1> graph;
			System.out.println("####ALGORITMO PD" + i + "####");
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight());
			DynamicProgrammingReduction<GrafoVirtual1, ConjuntoDatosEdge1> pdr = DynamicProgrammingReduction
					.of(graph,Heuristica1::heuristic , PDType.Max);
			Optional<GraphPath<GrafoVirtual1, ConjuntoDatosEdge1>> sol = pdr.search();
			if(sol.isPresent()) System.out.println(sol.get().getEdgeList().stream().map(x -> x.source.capRestante).collect(Collectors.toList()));
			System.out.println("####HECHO####");
		}
		public static void solutionAStar(String route, int i) {
			Modelo1.readFile(route);
			GrafoVirtual1 start = GrafoVirtual1.firstIteration();
			Predicate<GrafoVirtual1> goal = GrafoVirtual1.goal();
			EGraph<GrafoVirtual1, ConjuntoDatosEdge1> graph;
			System.out.println("####ALGORITMO A STAR "+ i + "####");
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight());
			AStar<GrafoVirtual1, ConjuntoDatosEdge1> as = AStar.of(graph,Heuristica1::heuristic , AStarType.Max);
			GraphPath<GrafoVirtual1, ConjuntoDatosEdge1> gp = as.search().get();
			System.out.println(gp.getVertexList().stream().map(x -> x.capRestante()).collect(Collectors.toList()));
			System.out.println("####HECHO####");
		}
		@Override
		public ConjuntoDatosEdge1 edge(Integer a) {
			// TODO Auto-generated method stub
			 return ConjuntoDatosEdge1.of(this, this.neighbor(a), 1);
		}
	}
	
	public record ConjuntoDatosEdge1(GrafoVirtual1 source,GrafoVirtual1 target,Integer action, Double weight) 
    implements SimpleEdgeAction<GrafoVirtual1, Integer>{
		public static ConjuntoDatosEdge1 of(GrafoVirtual1 c1, GrafoVirtual1 c2, Integer action) {
			ConjuntoDatosEdge1 a = new ConjuntoDatosEdge1(c1, c2, action, action * 1.0);
			return a;
		}
		
	}
	public record Memory(String name, Double capacity, Double tam_max) {
		public static Memory of(String args) {
			String name;
			Double capacity, tam_max;
			name = args.split(":")[0].replaceAll(" ", "");
			capacity = Double.parseDouble(args.replaceAll(" ", "").split(":")[1].split(";")[0].split("=")[1]);
			tam_max = Double.parseDouble(args.replaceAll(" ", "").split(":")[1].split(";")[1].split("=")[1]);
			return new Memory(name, capacity, tam_max);
		}
	}
	public  record File(String name, Double size) {
		public static File of(String args) {
			String name; Double size;
			name = args.split(":")[0].replaceAll(" ", "");
			size = Double.parseDouble(args.replaceAll(" ", "").split(":")[1].replaceAll(" ", ""));
			return new File(name, size);
		}
	}
	public static List<Memory> mems;
	public static List<File> files;
	public static void readFile(String rute) {
		List<String> lines = Files2.linesFromFile(rute);
		mems = new LinkedList<>();
		files = new LinkedList<>();
		int i;
		for(i = 1; !lines.get(i).contains("//"); i++) mems.add(Memory.of(lines.get(i)));
		for(i = i + 1 ; i < lines.size(); i++) files.add(File.of(lines.get(i)));
		
	}
	public static Double getTamArch(Integer i) { return files.get(i).size;}
	public static Double getTamMaxArchivo(Integer j) { return mems.get(j).tam_max;}
	public static Double getCapArchivo(Integer j) { return mems.get(j).capacity;}
	public static Integer getNumArchivos() { return files.size();}
	public static Integer getNumMem() { return mems.size();}
}
