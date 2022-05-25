package modelos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import modelos.Modelo1.ConjuntoDatosEdge1;
import modelos.Modelo1.GrafoVirtual1;
import modelos.Modelo2.ConjuntoDatosEdge2;
import modelos.Modelo2.GrafoVirtual2;
import modelos.Modelo2.Heuristica2;

import java.util.Set;

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

public class Modelo3 {
	public record Heuristica3() {
		public static Double heuristic(GrafoVirtual3 v1, Predicate<GrafoVirtual3> goal, GrafoVirtual3 v2) {
			Double heuristica = 0D;
			for(int i = v1.index ; i < Modelo3.numProductos(); i++) {
				Integer manual = (int)(v1.tiempoRestante.get(0) / Modelo3.getTElabComp(i));
				Integer prod =  (int)(v1.tiempoRestante.get(1) / Modelo3.getTProdComp(i));
				if(manual.compareTo(prod) > 0) {
					heuristica += prod * Modelo3.getprecioProducto(i);
				}else {
					heuristica += manual * Modelo3.getprecioProducto(i);
				}
				
			}
			return heuristica;
		}
	}
	public record GrafoVirtual3(Integer index, List<Double> tiempoRestante) implements VirtualVertex<GrafoVirtual3, ConjuntoDatosEdge3, Integer>{

		public static GrafoVirtual3 firstIteration() {
			List<Double> tiempos = new ArrayList<Double>(2);
			tiempos.add(Modelo3.tManual);
			tiempos.add(Modelo3.tProd);
			return new GrafoVirtual3(0, tiempos);
		}
		public static Predicate<GrafoVirtual3> goal(){
			return x -> x.index.equals(Modelo3.numProductos());
		}
		@Override
		public List<Integer> actions() {
			// TODO Auto-generated method stub
			if(index.equals(Modelo3.numProductos())) {
				return new LinkedList<>();
			}else{
				List<Integer> options = new LinkedList<Integer>();
				if(index.compareTo(Modelo3.numProductos() - 1) < 0) {
					for(int i = 0; i <= (int)Modelo3.getMaxUnProd(index); i++) if (conditions(i)) options.add(i);
				}else {
					options.add(Math.min((int)(this.tiempoRestante.get(0) / Modelo3.getTElabComp(index)),
							(int)(this.tiempoRestante.get(1) / Modelo3.getTProdComp(index))));
				}
				options.sort(Comparator.reverseOrder());
				return options;
			}
		}
		private boolean conditions(int i) {
			return Modelo3.getTElabComp(index) * i  <= tiempoRestante.get(0).doubleValue()
					&&
					Modelo3.getTProdComp(index) * i  <= tiempoRestante.get(1).doubleValue();
		}
		public static void solutionBT(String route, int i) {
			Modelo3.readFile(route);
			GrafoVirtual3 start = GrafoVirtual3.firstIteration();
			Predicate<GrafoVirtual3> goal = GrafoVirtual3.goal();
			EGraph<GrafoVirtual3, ConjuntoDatosEdge3> graph;
			System.out.println("####ALGORITMO BT" + i + "####");
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight);
			BackTracking<GrafoVirtual3, ConjuntoDatosEdge3, List<GrafoVirtual3>> bt = BackTracking.
			of(graph,
				Heuristica3::heuristic,
				x -> x.getVertexList(),
				BTType.Max);
			bt.search();
			System.out.println(bt.getSolution());
		System.out.println("####HECHO####");
		}
		public static void solutionPD(String route, int i) {
			Modelo3.readFile(route);
			GrafoVirtual3 start = GrafoVirtual3.firstIteration();
			Predicate<GrafoVirtual3> goal = GrafoVirtual3.goal();
			EGraph<GrafoVirtual3, ConjuntoDatosEdge3> graph;
			System.out.println("####ALGORITMO PD####");
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight);
			DynamicProgrammingReduction<GrafoVirtual3, ConjuntoDatosEdge3> pdr = DynamicProgrammingReduction
					.of(graph,Heuristica3::heuristic , PDType.Max);
			System.out.println(pdr.search().get().getEdgeList().stream().map(x -> x.weight).collect(Collectors.toList()));
			System.out.println("####HECHO####");
		}
		public static void solutionAStar(String route, int i) {
			Modelo3.readFile(route);
			GrafoVirtual3 start = GrafoVirtual3.firstIteration();
			Predicate<GrafoVirtual3> goal = GrafoVirtual3.goal();
			EGraph<GrafoVirtual3, ConjuntoDatosEdge3> graph;
			System.out.println("####ALGORITMO A STAR####");
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight);
			AStar<GrafoVirtual3, ConjuntoDatosEdge3> as = AStar.of(graph,Heuristica3::heuristic , AStarType.Max);
			System.out.println(as.search().get().getEdgeList().stream().map(x -> x.weight).collect(Collectors.toList()));
			System.out.println("####HECHO####");
		}
		@Override
		public GrafoVirtual3 neighbor(Integer a) {
			// TODO Auto-generated method stub
			Double man = Modelo3.getTElabComp(index), prod = Modelo3.getTProdComp(index);
			List<Double> tempTiempo = new ArrayList<Double>(this.tiempoRestante);
				tempTiempo.set(0,  tiempoRestante.get(0) - man * a);
				tempTiempo.set(1, tiempoRestante.get(1) - prod * a);
			return new GrafoVirtual3(index + 1, tempTiempo );
		}
		@Override
		public ConjuntoDatosEdge3 edge(Integer a) {
			// TODO Auto-generated method stub
			return ConjuntoDatosEdge3.of(this, this.neighbor(a), a);
		}
	}
	
	public record ConjuntoDatosEdge3(GrafoVirtual3 source,GrafoVirtual3 target,Integer action, Double weight) 
    implements SimpleEdgeAction<GrafoVirtual3, Integer>{
		public static ConjuntoDatosEdge3 of(GrafoVirtual3 c1, GrafoVirtual3 c2, Integer action) {
			ConjuntoDatosEdge3 a = new ConjuntoDatosEdge3(c1, c2, action, Modelo3.getprecioProducto(c1.index) * action);
			return a;
		}
		
	}
	public record Componente(String name, Double tElab, Double tProd) {
		public static Componente make(String args) {
			String name;
			Double tElab, tProd;
			args = args.replaceAll(" ", "");
			String[] temp = args.split(":");
			name = temp[0];
			temp = temp[1].split(";");
			tProd = Double.parseDouble(temp[0].split("=")[1]);
			tElab = Double.parseDouble(temp[1].split("=")[1]);
			return new Componente(name, tElab, tProd);	
		}
	}
	public record Producto(String name, Double precio, Map<String, Double> componentes, Integer max_u) {
		public static Producto make(String args) {
			String name; Integer max_u; Double precio;
			Map<String, Double> componentes = new HashMap<>();
			String[] temp = args.replaceAll(" ", "").split("->");
			name = temp[0];
			temp = temp[1].split(";");
			precio = Double.valueOf(temp[0].split("=")[1]);
			max_u = Integer.valueOf(temp[2].split("=")[1]);
			temp = temp[1].split("=")[1].split(",");
			for(int i = 0; i < temp.length; i++) {
				String producto; Double unidades;
				String[] tuple = temp[i].split(":");
				unidades = Double.parseDouble(tuple[1].substring(0, tuple[1].length() - 1));
				producto = tuple[0].substring(1);
				componentes.put(producto, unidades);
			}
			return new Producto(name, precio, componentes, max_u);	
		}
	}
	public static List<Componente> componentes;
	public static Double tProd, tManual;
	public static List<Producto> productos;
	public static void readFile(String rute) {
		List<String> lines = Files2.linesFromFile(rute);
		tProd = Double.valueOf(lines.get(0).replaceAll(" ", "").split("=")[1]);
		tManual = Double.valueOf(lines.get(1).replaceAll(" ", "").split("=")[1]);
		componentes = new LinkedList<>();
		productos = new LinkedList<>();
		int to = lines.indexOf("// PRODUCTOS");
		for(int i = 3; i < to; ++i) componentes.add(Componente.make(lines.get(i)));
		for(int i = to + 1; i < lines.size(); ++i) productos.add(Producto.make(lines.get(i)));
	}
	public static Integer numComponentes() { return componentes.size();}
	public static Integer numProductos() { return productos.size();}
	public static Double getTProd() { return tProd;}
	public static Double getTMan() { return tManual;}
	public static Double getprecioProducto(Integer i) { return productos.get(i).precio;}
	public static Double getTProdComp(Integer i) { 
		Double sum = 0.;
		Map<String, Double> p = productos.get(i).componentes;
		for(Entry<String, Double> j : p.entrySet()) {
			for(Componente c: componentes) {
				if(c.name.equals(j.getKey())) {
					sum += c.tProd * j.getValue(); 
				}
			}
		}
		return sum;
	}
	public static Double getTElabComp(Integer i) { 
		Double sum = 0.;
		Map<String, Double> p = productos.get(i).componentes;
		for(Entry<String, Double> j : p.entrySet()) {
			for(Componente c: componentes) {
				if(c.name.equals(j.getKey())) {
					sum += c.tElab * j.getValue(); 
				}
			}
		}
		return sum;
	}
	public static Integer getMaxUnProd(Integer i) { return productos.get(i).max_u;}
	public static Double sumPrecioProducto(Integer i) { return (double) productos.get(i).componentes.values().stream().mapToDouble(x -> (Double)x).sum();}
	public static Integer componenteEnProducto(Integer  i) { return productos.stream().anyMatch(x -> x.componentes.keySet().contains(componentes.get(i))) ? 1 : 0;}
}
