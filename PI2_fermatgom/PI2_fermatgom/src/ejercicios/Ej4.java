package ejercicios;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class Ej4 {
	public static long ej4Iter(int value) {
		List<Long> fn = new LinkedList<Long>();
		fn.add(2L); fn.add(4L); fn.add(6L);
		long item;
		int n = 2;
		while(value > n++) {
			item = 2 * fn.get(n - 1) + 4 * fn.get(n - 2) + 6 * fn.get(n - 3);
			fn.add(item);
		}
		return fn.get(value);
	}
	public static long ej4Rec(int value) {
		return ej4RecF(value, 0);
	}
	private static long ej4RecF(int value, long res) {
		if(value == 0) {
			res = 2;
		}else if(value == 1) {
			res = 4;
		}else if(value == 2) {
			res = 6;
		}else {
			res = 2 * ej4RecF(--value, res);
			res += 4 * ej4RecF(--value, res);
			res += 6 * ej4RecF(--value, res);
		}
		return res;
	}
	public static long ej4RecM(int value) {
		Map<Integer, Long> indexAlreadyFound = new HashMap<Integer, Long>();
		indexAlreadyFound.put(0, 2L);
		indexAlreadyFound.put(1, 4L);
		indexAlreadyFound.put(2, 6L);
		return ej4RecMF(value, indexAlreadyFound, 0);
	}
	private static long ej4RecMF(int value, Map<Integer, Long> indexes, long res) {
		if(!indexes.containsKey(value)) {
			res = 2 * ej4RecMF(value - 1, indexes, res);
			res += 4 * ej4RecMF(value - 2, indexes, res);
			res += 6 * ej4RecMF(value - 3, indexes, res);
			indexes.put(value, res);
		}else {
			res = indexes.get(value);
		}
			return res;
		}
}
