package ejercicios;

import java.util.stream.Stream;

public class Ej1 {
	public static String ej1RNF(int a, int b, int c) {
		return ej1RecNoFinal(a, b, c);
	}
	private static String ej1RecNoFinal(int a, int b, int c) {
		String answ = "";
		if (a < 3 && b < 3 && c < 3) {
			answ = "(" + Integer.toString(a * b * c) + ")";
		}else if(a < 5 || b < 5 || c < 5) {
			answ = "(" + Integer.toString(a + b + c) + ")";
		}else if(a % 2 == 0 && b % 2 == 0 && c % 2 == 0) {
			answ = Integer.toString(a * b * c) + ej1RecNoFinal(a / 2, b - 2, c / 2);
		}else {
			answ = Integer.toString(a + b + c) + ej1RecNoFinal(a / 3, b - 3, c / 3); 
		}
		return answ;
	}
	public static String ej1Iter(int a, int b, int c){
		String answ = "";
		boolean threePair;
		while (a > 4 && b > 4 && c > 4) {
			threePair = a % 2 == 0 && b % 2 == 0 && c % 2 == 0; // a + b + c MOD 2 != 0 EN ALGUNOS CASOS
			if(threePair) {
				answ += Integer.toString(a * b * c);
				a /= 2;
				b -= 2;
				c /= 2;
			}else {
				answ += Integer.toString(a + b + c);
				a /= 3;
				b -= 3;
				c /= 3;
			}
		}
		if (a < 3 && b < 3 && c < 3) {
			answ += "(" + Integer.toString(a * b * c) + ")";
		}else {
			answ += "(" + Integer.toString(a + b + c) + ")";
		}
		return answ;
	
	}
	public record Triplet(int a, int b, int c, String fullString) {
		public static Triplet of(int a, int b, int c) {
			return new Triplet(a, b, c, Triplet.getString(a,b,c, ""));
		}
		private static Triplet of(int a, int b, int c, String fullstring) {
			return new Triplet(a, b, c, Triplet.getString(a, b, c, fullstring));
		}
		public static String getString(int a, int b, int c, String prev) {
			String answ = prev;
			if (a < 3 && b < 3 && c < 3) {
				answ += "(" + Integer.toString(a * b * c) + ")";
			}else if(a < 5 || b < 5 || c < 5) {
				answ += "(" + Integer.toString(a + b + c) + ")";
			}else if(a % 2 == 0 && b % 2 == 0 && c % 2 == 0) {
				answ += Integer.toString(a * b * c);
			}else {
				answ += Integer.toString(a + b + c); 
			}
			return answ;
		}
		public Triplet next() {
			return a % 2 == 0 && b % 2 == 0 && c % 2 == 0 ?
					Triplet.of(a / 2, b - 2, c / 2, fullString) :
					Triplet.of(a / 3, b - 3, c / 3, fullString);
		}
		public boolean check() {
			return a < 5 || b < 5 || c < 5;
		}
	}
	public static String ej1Fun(int a, int b, int c){
		return Stream.iterate(Triplet.of(a, b, c), Triplet::next).
				filter(Triplet::check)
				.findFirst()
				.get()
				.fullString;
	}
	private static String ej1RecFinal(int a, int b, int c, String answ) {
		if (a < 3 && b < 3 && c < 3) {
			answ += "(" + Integer.toString(a * b * c) + ")";
		}else if(a < 5 || b < 5 || c < 5) {
			answ += "(" + Integer.toString(a + b + c) + ")";
		}else if(a % 2 == 0 && b % 2 == 0 && c % 2 == 0) {
			answ = ej1RecFinal(a / 2, b - 2, c / 2, answ + Integer.toString(a * b * c));
		}else {
			answ = ej1RecFinal(a / 3, b - 3, c / 3, answ + Integer.toString(a + b + c)); 
		}
		return answ;
	}
	public static String ej1RF(int a, int b, int c) {
		return ej1RecFinal(a, b, c, "");
	}
}
