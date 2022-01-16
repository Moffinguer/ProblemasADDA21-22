package ejercicios;

import java.util.stream.Stream;
public class Ej4 {
	public record Extremes(double init, double end, double n) {
		public static Extremes of(double init, double end, double n) {
			return new Extremes(init, end, n);
		}
		public double middlePoint() {
			return (this.end + this.init) / 2;
		}
		public double error() {
			return (this.end - this.init) / 2;
		}
		public boolean check(double e) {
			return this.error() < e || f(this.middlePoint(), n) == 0;
		}
		public Extremes next() {
			return f(this.middlePoint(), this.n) > 0 ? 
					Extremes.of(this.init(), this.middlePoint(), this.n()) :
					Extremes.of(this.middlePoint(), this.end(), this.n());
		}
	}
	private static double f(double x, double n) {
		double function = x * x * x - n;
		return function;
	}
	public static double ejercicio4(double n, double e) {
		double init = 0, end = n;
		return Ej4.ejercicio4R(n, e, init, end, (end + init) / 2, (end - init) / 2);
	}
	private static double ejercicio4R(double n, double e, double init, double end, double middlePoint,double error) {
		if (error > e) {
			double functVal = Ej4.f(middlePoint, n);
			if (functVal > 0) 
				middlePoint = Ej4.ejercicio4R(n, e, init, middlePoint,(middlePoint + init) / 2 ,(middlePoint - init) / 2);
			else
				middlePoint = Ej4.ejercicio4R(n, e, middlePoint, end,(end + middlePoint) / 2 ,(end - middlePoint) / 2);
		}
		return middlePoint;
	}
	public static double ejercicio4I(double n, double e) {
		double init = 0, end = n;
		double midPoint = (end + init) / 2;
		double funVal = f(midPoint, n);
		double error = (end - init) / 2;
		while (error > e && funVal != 0) {
			if(funVal > 0) 
				end = midPoint;
			else
				init = midPoint;
			midPoint = (end + init) / 2;
			funVal = f(midPoint, n);
			error = (end - init) / 2;
		}
		return midPoint;
	}
	public static double ejercicio4F(double n, double e) {
		Stream<Extremes> tmp = Stream.iterate(Extremes.of(0, n, n), x -> x.next());
		return tmp.filter(x -> x.check(e)).findFirst().get().middlePoint();
	}
}
