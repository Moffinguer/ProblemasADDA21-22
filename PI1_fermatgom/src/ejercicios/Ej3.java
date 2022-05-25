package ejercicios;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ej3 {
	public record Par(int v1, int v2) {
		public static Par of(int v1, int v2) {
			return new Par(v1, v2);
		}
	}

	public static String ejercicio3I(Integer a, Integer limit) {
		List<Par> lista = new LinkedList<Par>();
		Par item = Par.of(0, a);
		while (item.v1() < limit) {
			lista.add(item);
			item = Par.of(item.v1() + 1, item.v1() % 3 == 1 ? item.v2() : item.v1() + item.v2());
		}
		return lista.toString();
	}
	public static String ejercicio3(Integer a, Integer limit) {
		List<Par> lista = new LinkedList<Par>();
		ejercicio3R(a, limit, Par.of(0, a), lista);
		return lista.toString();
	}
	private static void ejercicio3R(Integer a, Integer limit, Par item, List<Par> lista) {
		if(item.v1() < limit) {
			lista.add(item);
			ejercicio3R(a, limit,
					Par.of(
							item.v1() + 1, item.v1() % 3 == 1 ?
									item.v2() : item.v1() + item.v2()), lista);
		
		}
	}
	public static String ejercicio3F(Integer a, Integer limit) {
		return Stream
		.iterate(Par.of(0, a),
		t -> t.v1() < limit,
		t -> Par.of(t.v1()+1, t.v1() % 3 == 1 ? t.v2() : t.v1()+t.v2()))
		.collect(Collectors.toList())
		.toString();
		}
}
