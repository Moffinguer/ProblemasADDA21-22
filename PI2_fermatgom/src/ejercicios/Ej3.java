package ejercicios;

import java.util.List;
import us.lsi.common.*;
public class Ej3 {
	public static IntegerSet ej3(List<Integer> lista, int intVal, int outVal) {
		IntegerSet res = IntegerSet.empty();
		ej3R(lista, intVal, outVal, res);
		return res;
	}
	private static void ej3R(List<Integer> lista, int inVal, int outVal, IntegerSet res) {
		if(inVal < lista.size() && outVal < lista.size()) {
			if(--outVal >= inVal) {
				int value = lista.get(lista.size() - outVal - 1);
				if(!res.add(value)) {
					res.addNew(value);
				}
				ej3R(lista, inVal, outVal, res);
			}
		}
	}
	
}
