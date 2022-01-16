package ejercicios;

import java.util.function.Function;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.common.List2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTreeImpl.BreadthPathBinaryTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TreeType;
import us.lsi.tiposrecursivos.TreeImpl.DepthPathTree;

public class Ej {
	public static <T> Set<T> ej1v1(Tree<T> tree, Predicate<T> cmp) {
		Set<T> temp = new HashSet();
		ej1R(tree, cmp, temp);
		return temp;
	}

	private static <T> void ej1R(Tree<T> tree, Predicate<T> cmp, Set<T> temp) {
		if(!tree.isEmpty()) {
			T label = tree.getLabel();
			boolean test = cmp.test(label) && !label.equals(null);
		
		switch (tree.getType()) {
		case Leaf: {
			if (test)
				temp.add(label);
			break;
		}
		case Nary: {
			if (test)
				temp.add(label);

			for (Tree i : tree.getChildren())
				ej1R(i, cmp, temp);
		}
		}
		}
	}

	public static <T> Set<T> ej1v2(Tree<T> tree, Predicate<T> cmp) {
		return ej1F(tree, cmp);

	}

	private static <T> Set<T> ej1F(Tree<T> tree, Predicate<T> cmp) {
		return tree.getPostOrder().stream().filter(x -> cmp.test(x) && !x.equals(null)).collect(Collectors.toSet());
	}

	public static <T> Set<T> ej1v3(Tree<T> tree, Predicate<T> cmp) {
		Set<T> dumb = new HashSet<T>();
		List<T> temp = tree.getPostOrder();
		T val = null;
		int i = temp.size() - 1;
		boolean check;
		while (i > -1) {
			while(i > -1 && cmp.test(temp.get(i))) {
				dumb.add(temp.get(i--));
			}
			i--;
		}
		return dumb;
	}

	public static Set<Integer> ej2(BinaryTree<Integer> bTree, Predicate<Integer> cmp) {
		Set<Integer> temp = new HashSet();
		ej2(bTree, cmp, temp);
		return temp;
	}

	private static void ej2(BinaryTree<Integer> bTree, Predicate<Integer> cmp, Set<Integer> temp) {
		if(!bTree.isEmpty()) {
			Integer label = bTree.getLabel();
			boolean test = cmp.test(label) && !label.equals(null);
			switch (bTree.getType()) {
			case Leaf: {
				if (test)
					temp.add(label);
				break;
			}
			case Binary: {
				if (test)
					temp.add(label);
				ej2(bTree.getLeft(), cmp, temp);
				ej2(bTree.getRight(), cmp, temp);
			}
			}
		}
	}

	public static List<Integer> ej3(BinaryTree<Integer> bTree) {
		List<Integer> ls = new LinkedList<Integer>();
		ej3(bTree, ls);
		return ls;
	}

	private static void ej3(BinaryTree<Integer> bTree, List<Integer> ls1) {
		if(!bTree.isEmpty()) {
			Integer label = bTree.getLabel();
			switch (bTree.getType()) {
			case Leaf: {
				ls1.add(label);
				break;
			}
			case Binary: {
				ls1.add(label);
				List<Integer> dumb = new LinkedList<>(ls1);
				BinaryTree<Integer> right = bTree.getRight(), left = bTree.getLeft();
				ej3(left, dumb);
				ej3(right, ls1);
				if(!product(ls1, dumb)) {
					ls1.clear();
					ls1.addAll(dumb);
				}
				}
			}
		}
	}
	private static boolean product(List<Integer> ls1, List<Integer> ls2) {
		double p1 = 1, p2 = 1;
		for(Integer i: ls1) p1 *= i.equals(null) ? 1 : i;
		for(Integer j: ls2) p2 *= j.equals(null) ? 1 : j;
		return p1 >= p2;
	}
	public static Set<List<Character>> ej4(Tree<Character> tree) {
		Set<List<Character>> temp = new HashSet<>();
		ej4(tree, temp, new LinkedList<Character>());
		return temp;
	}

	private static void ej4(Tree<Character> tree, Set<List<Character>> temp, List<Character> ls) {
		if(!tree.isEmpty()) {
			Character label = tree.getLabel();
			switch (tree.getType()) {
			case Leaf:
				ls.add(label);
				if (isPalindrome(ls, 0, true))
					temp.add(ls);
				break;
			case Nary:
				ls.add(label);
				for (Tree<Character> t : tree.getChildren()) {
					List<Character> dumb = new LinkedList<>(ls);
					ej4(t, temp, dumb);
				}
			}
		}
	}

	private static boolean isPalindrome(List<Character> ls, int i, boolean answer) {
		if(i < ls.size() / 2 && answer) {
			answer = isPalindrome(ls, i + 1, ls.get(i).equals(ls.get(ls.size() - i - 1)));
		}
		return answer;

	}

	public enum Paridad {
		Par, Impar
	}

	public static Map<Paridad, List<Integer>> ej5v1(BinaryTree<Integer> bTree) {
		Map<Paridad, List<Integer>> temp = new HashMap();
		ej5R(bTree, temp);
		return temp;
	}

	private static boolean condition(BinaryTree<Integer> right, BinaryTree<Integer> left, BinaryTree<Integer> bTree,
			Integer label) {
		boolean check = !right.isEmpty() && !left.isEmpty();
		if (check) {
			Integer labelR = right.getLabel(), labelL = left.getLabel();
			check = !labelR.equals(null) && !labelL.equals(null) && label.compareTo(labelL) > 0 && label.compareTo(labelR) < 0 && check;
		}
		return check;
	}

	private static void ej5R(BinaryTree<Integer> bTree, Map<Paridad, List<Integer>> temp) {
		if (bTree.isBinary()) {
			BinaryTree<Integer> right = bTree.getRight(), left = bTree.getLeft();
			Integer label = bTree.getLabel();
			boolean check = condition(right, left, bTree, label);
			if (check) update(temp, check(label), label);
			ej5R(right, temp);
			ej5R(left, temp);
		}
	}

	public static Map<Paridad, List<Integer>> ej5v2(BinaryTree<Integer> bTree){
		Function<Integer, Paridad> f = x -> check(x);
		return bTree.stream()
				.filter(x -> x.isBinary() && condition(x.getRight(), x.getLeft(), x, x.getLabel()))
				.map(x -> x.getLabel())
				.collect(Collectors.groupingBy(f, Collectors.toList()));
	}
	public static Map<Paridad, List<Integer>> ej5v3(BinaryTree<Integer> bTree) {
		int i = bTree.getHeight() - 1;
		int j;
		boolean test;
		BinaryTree<Integer> branch;
		Map<Paridad, List<Integer>> map = new HashMap();
		while(i > -1) {
			List<BinaryTree<Integer>> temp = bTree.getLevel(i);
			j = temp.size() - 1;
			while(j > -1) {
				branch = temp.get(j);
				test = branch.isBinary() && condition(branch.getRight(), branch.getLeft(), branch, branch.getLabel());
				if(test) {
					update(map, check(branch.getLabel()), branch.getLabel());
				}
				j--;
			}
			i--;
		}
		return map;
	}

	private static Paridad check(int val) {
		return val % 2 == 0 ? Paridad.Par : Paridad.Impar;
	}

	private static void update(Map<Paridad, List<Integer>> temp, Paridad key, Integer value) {
		if (!temp.containsKey(key)) temp.put(key, new LinkedList<Integer>());
		List<Integer> dumb = temp.get(key);
		dumb.add(value);
		temp.put(key, dumb);
	}
}
