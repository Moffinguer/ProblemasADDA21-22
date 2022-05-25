	package ejercicios;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.traverse.BreadthFirstIterator;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.SubGraphView;

public class Ej {
	public record Investigator(Integer id, Integer year, String university) {
		public static Investigator of(String[] line) {
			return new Investigator(Integer.parseInt(line[0]), Integer.parseInt(line[1]), line[2]);
		}
	}

	public record Publication(Integer idInv1, Integer idInv2, Integer publications) {
		public static Publication of(String[] line) {
			return new Publication(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
		}
	}

	public static void ej1(String rute, Predicate<Investigator> cond1, Predicate<Publication> cond2, int limit) {
		Function<Publication, Double> wr = e -> (double) e.publications;
		Function<String[], Investigator> vf = e -> Investigator.of(e);
		Function<String[], Publication> ef = e -> Publication.of(e);
		Graph<Investigator, Publication> investigators = GraphsReader.newGraph(rute, vf, ef,
				Graphs2::simpleWeightedGraph, wr);
		Function<Investigator, String> vl = v -> "INV-" + v.id;
		Function<Publication, String> el = e -> Integer.toString(e.publications);
		ej1A(investigators, cond1, cond2, vl, el);
		ej1B(investigators, limit, vl, el);
		ej1C(investigators, vl, el);
		ej1D(investigators, vl, el);
		ej1E(investigators, vl, el);
	}

	private static <V, E> void ej1A(Graph<V, E> investigators, Predicate<V> conditionsV, Predicate<E> conditionsE,
			Function<V, String> vl, Function<E, String> el) {
		Predicate<E> checkE = e -> conditionsE.test(e);
		Predicate<V> checkV = v -> conditionsV.test(v)
				|| investigators.edgesOf(v).stream().anyMatch(e -> checkE.test(e));
		GraphColors.toDot(investigators, "./respuestas/Ej1A.dot", vl, el,
				v -> GraphColors.color(checkV.test(v) ? Color.blue : Color.black),
				e -> GraphColors.color(checkE.test(e) ? Color.blue : Color.black));
	}

	private static <V, E> void ej1B(Graph<V, E> investigators, Integer limit, Function<V, String> vl,
			Function<E, String> el) {
		Set<V> listOfInvestigators = investigators.vertexSet().stream()
				.sorted(Comparator.comparing(v -> -investigators.degreeOf(v))).limit(limit).collect(Collectors.toSet());
		Predicate<V> checkV = v -> listOfInvestigators.contains(v);
		System.out.println(listOfInvestigators.stream().map(v -> vl.apply(v)).collect(Collectors.toSet()));
		GraphColors.toDot(investigators, "./respuestas/Ej1B.dot", vl, el,
				v -> GraphColors.color(checkV.test(v) ? Color.blue : Color.green), e -> GraphColors.color(Color.green));
	}

	private static <V, E> void ej1C(Graph<V, E> investigators, Function<V, String> vl,
			Function<E, String> el) {
		Map<V, List<V>> mapa = new HashMap<>();
		List<E> firsts = new LinkedList<>();
		for (V v : investigators.vertexSet()) {
			V key = v;
			if (!mapa.containsKey(key))
				mapa.put(key, new LinkedList<V>());
			List<V> temp = Graphs.predecessorListOf(investigators, key);
			temp.addAll(Graphs.successorListOf(investigators, key));
			firsts.add(investigators.getEdge(key, temp.stream().distinct()
					.sorted(Comparator.comparing(j -> -ej1Cfunction(investigators, j, key))).toList().get(0)));
			mapa.put(key, temp.stream().distinct()
					.sorted(Comparator.comparing(j -> -ej1Cfunction(investigators, j, key))).toList());
		}
		System.out.println(mapa);
		GraphColors.toDot(investigators, "./respuestas/Ej1C.dot", vl, el,
		v -> GraphColors.color(Color.black),
		e -> GraphColors.color(firsts.contains(e) ? Color.blue : Color.black));

	}

	private static <V, E> int ej1Cfunction(Graph<V, E> g, V source, V dest) {
		return ((Publication) g.getAllEdges(source, dest).toArray()[0]).publications;
	}

	private static void ej1D(Graph<Investigator, Publication> g, Function<Investigator, String> vl,
			Function<Publication, String> el) {
		List<Investigator> vRes = new LinkedList<>();
		List<Publication> eRes = new LinkedList<>();
		int length = 0;
		for(Publication p : g.edgeSet()) {
			g.setEdgeWeight(p, 1D);
		}
		var dspath = new DijkstraShortestPath<>(g);
		for (Investigator vsource : g.vertexSet()) {
			for (Investigator vdest : g.vertexSet()) {
				if (dspath.getPath(vsource, vdest).getLength() > length) {
					length = dspath.getPath(vsource, vdest).getLength();
					vRes.clear();
					eRes.clear();
					vRes.addAll(dspath.getPath(vsource, vdest).getVertexList());
					eRes.addAll(dspath.getPath(vdest, vsource).getEdgeList());
				}
			} // El camino es el correcto, pero los vértices no los marca como debe
		}
		GraphColors.toDot(g, "./respuestas/Ej1D.dot", vl, el,
				v -> GraphColors.color(vRes.contains(v) ? Color.blue : Color.black),
				e -> GraphColors.color(eRes.contains(e) ? Color.blue : Color.black));

	}

	private static <V, E> void ej1E(Graph<V, E> g, Function<V, String> vl, Function<E, String> el) {
		Graph<V, E> gTemp = Graphs2.simpleWeightedGraph();
		for (V v : g.vertexSet()) {
			gTemp.addVertex(v);
		}
		int i = 0;
		for (V v : g.vertexSet()) {
			for (V e : g.vertexSet()) {
				if (!v.equals(e) && ((Investigator) v).university.equals(((Investigator) e).university)) {
					String args = ((Investigator) v).id.toString() + ",";
					args += ((Investigator) e).id + ",";
					args += i;
					gTemp.addEdge(v, e, (E) Publication.of(args.split(",")));
					i++;
				}
			}
		}
		var gc = new GreedyColoring(gTemp);
		GraphColors.toDot(g, "./respuestas/Ej1E.dot", vl, el,
				v -> GraphColors.color((Integer) gc.getColoring().getColors().get(v)),
				e -> GraphColors.color(Color.black));

	}

	public record Books(String book1, String book2) {
		public static Books of(String[] line) {
			return new Books(line[0], line[1]);
		}
	}

	public record Book(String book) {
		public static Book of(String[] line) {
			return new Book(line[0]);
		}

		public static Book make(String el) {
			return new Book(el);
		}
	}

	public static void ej2(String rute, List<List<Book>> testing, List<Book> test) {
		Graph<Book, Books> g = GraphsReader.newGraph(rute, Book::of, Books::of, Graphs2::simpleDirectedWeightedGraph,
				null);
		ej2A(g);
		int i = 1;
		for (List<Book> books : testing)
			ej2B(g, books, i++);
		i = 1;
		for (Book book : test)
			ej2C(g, book, i++);
	}

	private static <V, E> void ej2A(Graph<V, E> g) {
		List<V> temp = g.vertexSet().stream().filter(vertex -> Graphs.predecessorListOf(g, vertex).size() == 0
				&& Graphs.successorListOf(g, vertex).size() > 0).toList();
		System.out.println(temp);
		GraphColors.toDot(g, "./respuestas/Ej2A.dot", v -> v.toString(), e -> "",
				v -> GraphColors.color(temp.contains(v) ? Color.blue : Color.black),
				e -> GraphColors.color(Color.black));
	}

	private static <V, E> void ej2B(Graph<V, E> g, List<V> testing, int i) {
		if (ej2BCheckRute(g, testing)) {
			System.out.println("Hay solución para " + testing);
		} else {
			System.out.println("No hay solución para " + testing);
		}
	}

	private static <V, E> boolean ej2BCheckRute(Graph<V, E> g, List<V> testing) {
		boolean isReadable = true;
		var bfi = new BreadthFirstIterator<V,E>(g, testing.get(0));
		List<V> answ = new LinkedList<>();
		bfi.forEachRemaining(v -> answ.add(v));
		int i = 1, lastIndex = -1;
		while(isReadable && i < testing.size()) {
			if(!answ.contains(testing.get(i)) || answ.indexOf(testing.get(i)) <= lastIndex) {
				isReadable = false;
			}else{
				lastIndex = answ.indexOf(testing.get(i));
			}
			i++;
		}
		return isReadable;
	}

	private static <V, E> void ej2C(Graph<V, E> g, V start, int i) {
		List<V> answ = new LinkedList<>();
		ej2Cansw(g, Graphs.predecessorListOf(g, start), answ);
		List<V> res = answ.stream().distinct().toList();
		System.out.println(res + "\n");
		GraphColors.toDot(g, "./respuestas/Ej2C" + i + ".dot", v -> v.toString(), e -> "",
				v -> GraphColors.color(res.contains(v) ? Color.blue : Color.black),
				e -> GraphColors.color(Color.black));

	}

	private static <V, E> void ej2Cansw(Graph<V, E> g, List<V> next, List<V> answ) {
		for (V v : next) {
			ej2Cansw(g, Graphs.predecessorListOf(g, v), answ);
			answ.add(v);
		}
	}

	public record Street(Integer intersect1, Integer intersect2, Double duration, Double effort) {
		public static Street of(String[] line) {
			Integer int1 = Integer.parseInt(line[0]), int2 = Integer.parseInt(line[1]);
			Double dur = Double.parseDouble(line[2].replace("min", ""));
			Double eff = Double.parseDouble(line[3].replace("esf", ""));
			return new Street(int1, int2, dur, eff);
		}
	}

	public record Intersecction(Integer id, Boolean hasMonument, String nameMonument, Integer interest) {
		public static Intersecction of(String[] line) {
			Integer id = Integer.parseInt(line[0]);
			Boolean hasMonument = Boolean.parseBoolean(line[1]);
			String name = line[2];
			Integer interest = Integer.parseInt(line[3].replace("int", ""));
			return new Intersecction(id, hasMonument, name, interest);
		}
	}

	public static void ej3(String rute, List<List<String>> monuments, List<List<Street>> streets) {
		ej3A(monuments, rute);
		ej3B(rute);
		int i = 0;
		for (List<Street> street : streets)
			ej3C(rute, street, i++);

	}

	private static void ej3A(List<List<String>> monuments, String rute) {
		Function<Street, Double> wr = e -> e.duration;
		Graph<Intersecction, Street> gA = GraphsReader.newGraph(rute, Intersecction::of, Street::of,
				Graphs2::simpleWeightedGraph, wr);
		int j = 0;
		for (List<String> monument : monuments) {
			Intersecction start = null;
			Intersecction end = null;
			for (Intersecction i : gA.vertexSet()) {
				if (i.nameMonument.equals(monument.get(0))) {
					start = i;
				} else if (i.nameMonument.equals(monument.get(1))) {
					end = i;
				}
			}
			var dsp = DijkstraShortestPath.findPathBetween(gA, start, end);
			GraphColors.toDot(gA, "./respuestas/Ej3A" + j + ".dot", v -> v.nameMonument, e -> e.duration.toString(),
					v -> GraphColors.color(dsp.getVertexList().contains(v) ? Color.blue : Color.black),
					e -> GraphColors.color(dsp.getEdgeList().contains(e) ? Color.blue : Color.black));
			j++;
		}
	}

	private static void ej3B(String rute) {
		Function<Street, Double> wr = e -> e.effort;
		Graph<Intersecction, Street> gB = GraphsReader.newGraph(rute, Intersecction::of, Street::of,
				Graphs2::simpleWeightedGraph, wr);
		var hr = new HeldKarpTSP<Intersecction, Street>();
		GraphColors.toDot(gB, "./respuestas/Ej3B.dot", v -> v.nameMonument, e -> e.effort.toString(),
				v -> GraphColors.color(hr.getTour(gB).getVertexList().contains(v) ? Color.blue : Color.black),
				e -> GraphColors.color(hr.getTour(gB).getEdgeList().contains(e) ? Color.blue : Color.black));

	}

	private static void ej3C(String rute, List<Street> streets, int i) {
		Function<Street, Double> wr = e -> e.duration;
		Graph<Intersecction, Street> gC = GraphsReader.newGraph(rute, Intersecction::of, Street::of,
				Graphs2::simpleWeightedGraph, wr);
		gC.removeAllEdges(streets);
		Set<Intersecction> resV = new HashSet<>();
		Set<Street> resE = new HashSet<>();
		var ci = new ConnectivityInspector<>(gC);
		int sumRel = 0;
		int sumVar = 0;
		if (!ci.isConnected()) {
			for (Set<Intersecction> j : ci.connectedSets()) {
				for (Intersecction k : j) {
					sumRel += k.interest;
				}
				if (sumRel > sumVar) {
					sumVar = sumRel;
					resV.clear();
					resV.addAll(j);
				}
				sumRel = 0;
			}
			for (Intersecction v : resV) {
				resE.addAll(gC.edgesOf(v));
			}
			GraphColors.toDot(gC, "./respuestas/Ej3C" + i + ".dot", v -> v.nameMonument, e -> e.duration.toString(),
					v -> GraphColors.color(resV.contains(v) ? Color.blue : Color.black),
					e -> GraphColors.color(resE.contains(e) ? Color.blue : Color.black));
		}
	}
	public static void ej4(String rute, Predicate<Distancia> check, int i) {
		Graph<Persona, Distancia> g = ej4Read(rute);
		ej4A(g, check, i);
		ej4B(g, check, i);

	}

	private static Graph<Persona, Distancia> ej4Read(String rute) {
		int n = 0, m = 0;
		double distF = 0, distC = 0;
		Graph<Persona, Distancia> g = Graphs2.simpleWeightedGraph();
		List<String> temp = Files2.linesFromFile(rute).stream().filter(x -> !x.contains("#")).toList();
		for (String line : temp.subList(0, 4)) {
			if (line.contains("n=")) {
				n = Integer.parseInt(line.split("=")[1]);
			} else if (line.contains("m=")) {
				m = Integer.parseInt(line.split("=")[1]);
			} else if (line.contains("distFilas")) {
				distF = Double.parseDouble(line.split("=")[1]);
			} else if (line.contains("distColumnas")) {
				distC = Double.parseDouble(line.split("=")[1]);
			}
		}
		for (String line : temp.subList(4, temp.size())) {
			int x = Math.abs(Integer.parseInt(line.split(",")[0]));
			int y = Integer.parseInt(line.split(",")[1]);
			if (line.contains("+")) {
				g.addVertex(Persona.of(x, y, true));
			} else if (line.contains("-")) {
				g.addVertex(Persona.of(x, y, false));
			}

		}
		for (Persona i : g.vertexSet()) {
			int xi = i.cordX();
			int yi = i.cordY();
			for (Persona j : g.vertexSet()) {
				if (!i.equals(j)) {
					int xj = j.cordX();
					int yj = j.cordY();
						double distY = distC * (yi - yj);
						double distX = distF * (xi - xj);
						Double h = Math.sqrt(Math.pow(distY, 2) + Math.pow(distX, 2));
						g.addEdge(i, j, Distancia.of(i, j, h));
					
				}
			}
		}
		return g;
	}

	private static void ej4A(Graph<Persona, Distancia> g, Predicate minDist, int i) {
		Map<Persona, Integer> chart = new HashMap<>();
		ej4BTime(g, g.vertexSet(), chart, 0, minDist, false);
		GraphColors.toDot(g, "./respuestas/Ej4A" + i + ".dot",
				v -> v.cordX + "," + v.cordY + (chart.containsKey(v) ? " -> " + chart.get(v) : ""), j -> "",
				v -> GraphColors.color(v.isSick ? Color.red : chart.keySet().contains(v) ? Color.orange : Color.green),
				e -> GraphColors.color(Color.blue));
	}

	private static void ej4B(Graph<Persona, Distancia> g, Predicate minDist, int i) {
		Map<Persona, Integer> chart = new HashMap<>();
		ej4BTime(g, g.vertexSet(), chart, 0, minDist, false);
		GraphColors.toDot(g, "./respuestas/Ej4B" + i + ".dot",
				v -> v.cordX + "," + v.cordY + (chart.containsKey(v) ? " -> " + chart.get(v) : ""), j -> "",
				v -> GraphColors.color(v.isSick ? Color.red : chart.keySet().contains(v) ? Color.orange : Color.green),
				e -> GraphColors.color(Color.blue));

	}

	@SuppressWarnings("unchecked")
	private static void ej4BTime(Graph<Persona, Distancia> g, Set<Persona> people, Map<Persona, Integer> chart,
			Integer time, Predicate minDist, boolean isSecondRound) {
		Function<Distancia, Persona> returnPeople2 = v -> v.p2();
		Function<Distancia, Persona> returnPeople1 = v -> v.p1();
		for (Persona p : people) {
			Set<Persona> returners = (Set<Persona>) g.edgesOf(p).stream().filter(minDist).map(returnPeople2)
					.filter(v -> !chart.containsKey(v)).collect(Collectors.toSet());
			returners.addAll((Set<Persona>) g.edgesOf(p).stream().filter(minDist).map(returnPeople1)
					.filter(v -> !chart.containsKey(v)).collect(Collectors.toSet()));
			if (p.isSick()) {
				chart.put(p, 0);
				ej4BTime(g, returners, chart, 1, minDist, true);
			} else {
				if (isSecondRound) {
					chart.put(p, time);
					ej4BTime(g, returners, chart, time + 1, minDist, true);
				}
			}
		}
	}

	public static record Persona(Integer cordX, Integer cordY, Boolean isSick) {
		public static Persona of(int x, int y, boolean sick) {
			return new Persona(x, y, sick);
		}
	}

	public static record Distancia(Persona p1, Persona p2, Double distancia) {
		public static Distancia of(Persona p1, Persona p2, double distancia) {
			return new Distancia(p1, p2, distancia);
		}
	}
}
