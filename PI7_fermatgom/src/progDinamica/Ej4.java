package progDinamica;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import modelos.Modelo4;
import modelos.Modelo4.GrafoVirtual4;
import modelos.Modelo4.Heuristica4;
import progDinamica.Ej4.Spm;

public class Ej4 {
		public static record Spm(Integer action, Integer weight) implements Comparable<Spm>{
			public static Spm of(Integer action, Integer weight) {
				return new Spm(action, weight);
			}
			@Override
			public int compareTo(Spm o) {
				// TODO Auto-generated method stub
				return this.weight.compareTo(o.weight);
			}
			
		}
		public static Integer maxValue;
		public static GrafoVirtual4 start;
		public static Map<GrafoVirtual4, Spm> memory;
		public static List<Integer> pd(String route){
			Modelo4.readFile(route);
			maxValue = Integer.MIN_VALUE;
			start = GrafoVirtual4.firstIteration();
			memory = new HashMap<>();
			Ej4.pd(start, 0);
			return Ej4.solucionPD();
		}
		private static Spm pd(GrafoVirtual4 start2, int i) {
				Spm r;
				Boolean esUltimoVertice = GrafoVirtual4.goal().test(start2);
				if(memory.containsKey(start2)) {
					r = memory.get(start2);
				}else if(esUltimoVertice) {
					r = Spm.of(null, 0);
					memory.put(start2, r);
					if(i > maxValue) {
						maxValue = i;
					}
				}else {
					List<Spm> soluciones = new LinkedList<>();
					for(Integer action : start2.actions()) {
						GrafoVirtual4 neighbour = start2.neighbor(action);
						int peso = (int) neighbour.capRestante().stream().filter(x -> x.equals(0.)).count();
						Double cota = peso + Heuristica4.heuristic(neighbour, null, null);
						if (cota > maxValue) {
							Spm s = pd(neighbour, peso);
							if(s != null) {
								Spm amp = Spm.of(action, s.weight);
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
			GrafoVirtual4 v = start;
			Spm s = memory.get(v);
			while(s != null && s.action != null) {
				GrafoVirtual4 old = v;
				acciones.add(s.action() + 1);
				v = old.neighbor(s.action);
				s = memory.get(v);
			}
			return acciones;
		}
}
