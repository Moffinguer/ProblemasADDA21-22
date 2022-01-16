package tests;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ejercicios.Ej;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;


public class TestPI3 {
	public static <T> List<T> lecturaFichero(Function<String, T> parse, int ej){
		List<String> lines = Files2.linesFromFile(".\\ficheros\\PI3E" + ej + "_DatosEntrada.txt");
		return lines.stream().map(x -> parse.apply(x)).collect(Collectors.toList());
	}
	private static void ej1() {
		List<Tree<Integer>> tree = lecturaFichero(x -> Tree.parse(x, j -> Integer.parseInt(j)),1);
		Predicate<Integer> cmp= x -> x % 2 == 0;
		String res = "";
		for(Tree<Integer> t: tree) {
			res += "Pares: " + t + "\n"; 
			res += "Versión Recursiva: " + Ej.ej1v1(t, cmp) + "\n";
			res += "Versión Iterativa: " + Ej.ej1v3(t, cmp) + "\n";
//			res += "Versión Funcional: " + Ej.ej1v2(t, cmp) + "\n";
		}
		cmp= x -> x < 5;
		for(Tree<Integer> t: tree) {
			res += "Menores que 5: " + t + "\n"; 
			res += "Versión Recursiva: " + Ej.ej1v1(t, cmp) + "\n";
			res += "Versión Iterativa: " + Ej.ej1v3(t, cmp) + "\n";
//			res += "Versión Funcional: " + Ej.ej1v2(t, cmp) + "\n";
		}
		System.out.println(res);
	}
	private static void ej2() {
		List<String> lines = Files2.linesFromFile(".\\ficheros\\PI3E" + 2 + "_DatosEntrada.txt");
		String res = "";
		for(String t: lines) {
			res += "Ej2: " + t + "\n";
			Integer k = Integer.parseInt(t.split("#")[1]);
			BinaryTree<Integer> tree = BinaryTree.parse(t.split("#")[0], x -> Integer.parseInt(x));
			res += "Versión Recursiva: " + Ej.ej2(tree, x -> x >= k) + "\n\n";
		}
		System.out.println(res);
	}
	private static void ej3() {
		List<BinaryTree<Integer>> tree = lecturaFichero(x -> BinaryTree.parse(x, j -> Integer.parseInt(j)),3);
		String res = "";
		for(BinaryTree<Integer> t: tree) {
			res += "Ej3: " + t + "\n";
			List<Integer> l = Ej.ej3(t);
			res += "Versión Recursiva: " + l + "(" + l.stream().reduce(1, (a,b) -> a * b) + ")" + "\n";
		}
		System.out.println(res);
	}
	private static void ej4() {
		List<Tree<Character>> tree = lecturaFichero(x -> Tree.parse(x, j -> j.charAt(0)),4);
		String res = "";
		for(Tree<Character> t: tree) {
			res += "Ej4: " + t + "\n";
			Set<List<Character>> l = Ej.ej4(t);
			res += "Versión Recursiva: " + l + "\n";
		}
		System.out.println(res);
	}
	private static void ej5() {
		List<BinaryTree<Integer>> tree = lecturaFichero(x -> BinaryTree.parse(x, j -> Integer.parseInt(j)),5);
		String res = "";
			for(BinaryTree<Integer> t: tree) {
				res += "Ej5: " + t + "\n";
				res += "Versión Recursiva: " + Ej.ej5v1(t)+ "\n";
//				res += "Versión Iterativa: " + Ej.ej5v3(t) + "\n";
				res += "Versión Funcional: " + Ej.ej5v2(t) + "\n";
				res += "\n\n";	
			}
			System.out.println(res);
		
	}
	public static void main(String[] args) {
		ej1();
		ej2();
		ej3();
		ej4();
		ej5();
	}

}
