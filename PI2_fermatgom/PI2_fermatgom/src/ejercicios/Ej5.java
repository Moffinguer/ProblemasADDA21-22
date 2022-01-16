package ejercicios;

import java.util.HashMap;
import java.util.Map;

public class Ej5 {
	public static int ej5RecSM(int a, int b, int c) {
		return ej5RecSM(a, b, c , 0);
	}
	private static int ej5RecSM(int a, int b, int c, int res) {
		if (a < 3 || b < 3 || c < 3) {
			res = a + b * b + 2 * c; 
		}else if( a % b == 0) {
			res = ej5RecSM(--a, b / 2, c / 2, res);
			res += ej5RecSM(a - 2, b / 3, c / 3, res);
		}else {
			res = ej5RecSM(a / 2, b - 2, c - 2);
			res += ej5RecSM(a / 3, b - 3, c - 3);
		}
		return res;
	}
	public record Triplet(int a, int b, int c) {
		public static Triplet of(int a, int b, int c) {
			return new Triplet(a, b, c);
		}
		public boolean check() {
			return a < 3 || b < 3 || c < 3;
		}
		public int value() {
			return a + b * b + 2 * c; 
		}
	}
	public static int ej5I(int a, int b, int c) {
		int i = 0, j = 0, k = 0;
		Triplet temp = null;
		Map<Triplet, Integer> storage = new HashMap<Triplet, Integer>();
		while (i <= a) {
			while (j <= b) {
				while (k <= c) {
					temp = Triplet.of(i, j, k);
					int value;
					if(temp.check()) {
						value = temp.value();
					}else if( i % j == 0){
						Triplet first = Triplet.of(i - 1, j / 2, k / 2);
						Triplet second = Triplet.of(i - 3, j / 3, k / 3);
						value = storage.get(first) + storage.get(second);	
					}else {
						Triplet first = Triplet.of(i / 3, j - 3, k - 3);
						Triplet second = Triplet.of(i / 2, j - 2, k - 2);
						value = storage.get(first) + storage.get(second);
					}
					storage.put(temp, value);
					k++;
				}
				j++;
				k = 0;
			}
			j = 0;
			i++;
		}
		return storage.get(Triplet.of(a, b, c));
	}
	public static int ej5RCM(int a, int b, int c) {
		Map<Triplet, Integer> indexedElements = new HashMap<Triplet, Integer>();
		return ej5RCM(a, b, c, indexedElements, 0);
	}
	private static int ej5RCM(int a, int b, int c, Map<Triplet, Integer> mapa, int res) {
		Triplet temp = Triplet.of(a, b, c);
		if (!mapa.containsKey(temp)) {
			if(temp.check()) {
				res = temp.value();
			}else if( a % b == 0) {
				res = ej5RCM(a - 1, b / 2, c / 2, mapa, res);
				res += ej5RCM(a - 3, b / 3, c / 3, mapa, res);
			}else {
				res = ej5RCM(a / 2, b - 2, c - 2, mapa, res);
				res += ej5RCM(a / 3, b - 3, c - 3, mapa, res);
			}
			mapa.put(temp, res);
		}else {
			res = mapa.get(temp);
		}
		return res;
	}
}