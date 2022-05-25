package tests;

import java.util.List;
import java.util.function.Predicate;

import ejercicios.Ej;
import ejercicios.Ej.Book;
import ejercicios.Ej.Distancia;
import ejercicios.Ej.Investigator;
import ejercicios.Ej.Publication;
import ejercicios.Ej.Street;
import us.lsi.common.Files2;




public class TestPI4 {
	
	public static void test1(String rute) {
		Predicate<Investigator> cond1 = v -> v.year().compareTo(1982) < 0;
		Predicate<Publication> cond2 = e -> e.publications().compareTo(5) > 0;
		int limit = 5;
		Ej.ej1(rute, cond1, cond2, limit);
	}
	public static void test2(String rute, String testing) {
		List<List<Book>> check = Files2.linesFromFile(testing).stream()
										.map(x -> x.split(" ")[1])
										.map(x -> x.substring(1, x.length() - 1))
										.map(x -> List.of(x.split(",")))
										.map(x -> x.stream().map(k -> Book.make(k)).toList())
										.toList();
		Ej.ej2(rute, check, List.of(Book.make("L3"),Book.make("L9"), Book.make("L7")));
	}
	public static void test3(String rute) {
		List<List<String>> temp = List.of(List.of("m7","m2"), List.of("m4","m9"));
		List<List<Street>> streets = List.of(
				List.of(Street.of("1,6,3min,1esf".split(","))
						,Street.of("4,7,6min,2esf".split(",")),
						Street.of("4,6,5min,1esf".split(",")),
						Street.of("5,8,4min,2esf".split(","))
						),
				List.of(Street.of("1,6,3min,1esf".split(","))
						,Street.of("4,7,6min,2esf".split(",")),
						Street.of("4,6,5min,1esf".split(",")),
						Street.of("5,8,4min,2esf".split(",")),
						Street.of("7,8,6min,1esf".split(","))
						)
				);
		Ej.ej3(rute, temp, streets);
	}
	public static void test4(List<String> rute) {
		Predicate<Distancia> check = v -> v.distancia().compareTo(1.2) <= 0;
		int i = 1;
		for(String line: rute) Ej.ej4(line, check, i++);
	}
	public static void main(String[] args) {
		test1("./ficheros/PI4E1_DatosEntrada.txt");
		test2("./ficheros/PI4E2_DatosEntrada1.txt", "./ficheros/PI4E2_DatosEntrada2.txt");
		test3("./ficheros/PI4E3_DatosEntrada.txt");
		test4(List.of("./ficheros/PI4E4_DatosEntrada1.txt", "./ficheros/PI4E4_DatosEntrada2.txt"));
	}

}
