package progDinamica;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import modelos.Modelo1;
import modelos.Modelo1.GrafoVirtual1;
import modelos.Modelo1.Heuristica1;
import modelos.Modelo3;
import modelos.Modelo3.GrafoVirtual3;
import modelos.Modelo3.Heuristica3;

public class Ej3 {
	public static record Spm(Integer action, Double weight) implements Comparable<Spm>{
		public static Spm of(Integer action, Double weight) {
			return new Spm(action, weight);
		}
		@Override
		public int compareTo(Spm o) {
			// TODO Auto-generated method stub
			return this.weight.compareTo(o.weight);
		}
		
	}
	public static Double maxValue;
	public static GrafoVirtual3 start;
	public static Map<GrafoVirtual3, Spm> memory;
	public static List<Integer> pd(String route){
		Modelo3.readFile(route);
		maxValue = Double.MIN_VALUE;
		start = GrafoVirtual3.firstIteration();
		memory = new HashMap<>();
		Ej3.pd(start, 0.);
		return Ej3.solucionPD();
	}
	private static Spm pd(GrafoVirtual3 start2, double i) {
			Spm r;
			Boolean esUltimoVertice = GrafoVirtual3.goal().test(start2);
			if(memory.containsKey(start2)) {
				r = memory.get(start2);
			}else if(esUltimoVertice) {
				r = Spm.of(null, 0.);
				memory.put(start2, r);
				if(i > maxValue) {
					maxValue = i;
				}
			}else {
				List<Spm> soluciones = new LinkedList<>();
				for(Integer action : start2.actions()) {
					GrafoVirtual3 neighbour = start2.neighbor(action);
					Double cota = i + start2.edge(action).weight() + Heuristica3.heuristic(neighbour, null, null);
					if (cota > maxValue) {
						Spm s;
					     s = pd(neighbour, i + start2.edge(action).weight());
						if(s != null) {
							Spm amp = Spm.of(action, s.weight + start2.edge(action).weight());
							soluciones.add(amp);
						}
					}
				}
				r = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
				if(r != null) {
					memory.put(start2, r);
				}
			}
			return r;
		
	}
	private static List<Integer> solucionPD() {
		List<Integer> acciones = new LinkedList<>();
		GrafoVirtual3 v = start;
		Spm s = memory.get(v);
		while(s != null && s.action != null) {
			GrafoVirtual3 old = v;
			acciones.add(s.action);
			v = old.neighbor(s.action);
			s = memory.get(v);
		}
		return acciones;
	}
}
