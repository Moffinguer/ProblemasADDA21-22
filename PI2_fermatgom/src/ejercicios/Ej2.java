package ejercicios;

import java.util.LinkedList;
import java.util.List;

public class Ej2 {
	public static List<String> ej2Recursivo(String[][] ls) {
		List<String> tmp = new LinkedList<String>();
		ej2R(ls, 0, 0, ls.length - 1, ls[0].length - 1, ls.length, tmp);
		return tmp;
	}
	private static void ej2R(String[][] matrix, int xC1, int yC1, int xC2, int yC2, int dim, List<String> res) {
			if (dim > 1) {
				int xMiddlePoint = (xC2 + xC1) / 2;
				int yMiddlePoint = (yC2 + yC1) / 2;
				dim /= 2;
				res.add(matrix[xC1][yC1] + matrix[xC1][yC2] + matrix[xC2][yC1] + matrix[xC2][yC2]);
				ej2R(matrix, xC1, yC1, xMiddlePoint, yMiddlePoint++, dim ,res); // C2
				ej2R(matrix, xC1 , yMiddlePoint--, xMiddlePoint++, yC2, dim ,res); // C1
				ej2R(matrix, xMiddlePoint, yC1, xC2, yMiddlePoint++, dim, res);
				ej2R(matrix, xMiddlePoint, yMiddlePoint, xC2, yC2, dim, res);
			}
	}
	
}
