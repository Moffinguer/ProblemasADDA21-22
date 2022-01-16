package ejercicios;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Ej1 {
	public static boolean ejercicio1F(List<String> ls, Predicate<String> pS, Predicate<Integer> pI,
			Function<String, Integer> f) {
		return ls.stream().filter(pS).map(f).anyMatch(pI);
	}

	public static boolean ejercicio1I(List<String> ls, Predicate<String> pS, Predicate<Integer> pI,
			Function<String, Integer> f) {
		boolean match = false;
		int i = 0;
		int size = ls.size();
		String item;
		int answer;
		while (i < size && !match) {
			item = ls.get(i);
			if (pS.test(item)) {
				answer = f.apply(item);
				match = pI.test(answer);
			}
			i++;
		}
		return match;
	}

	private static boolean ejercicio1R(List<String> ls, Predicate<String> pS, Predicate<Integer> pI,
			Function<String, Integer> f, int i, boolean match) {
		if (!match && i < ls.size()) {
			String item = ls.get(i);
			match = pS.test(item) && pI.test(f.apply(item));
			match = ejercicio1R(ls, pS, pI, f, ++i, match);
		}
		return match;
	}

	public static boolean ejercicio1(List<String> ls, Predicate<String> pS, Predicate<Integer> pI,
			Function<String, Integer> f) {
		return ejercicio1R(ls, pS, pI, f, 0, false);
	}
}
