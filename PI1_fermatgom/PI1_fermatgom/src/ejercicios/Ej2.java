package ejercicios;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ej2 {
	public static Map<Integer,List<String>> ejercicio2I(List<List<String>> listas){
		Map<Integer, List<String>> mapa = new HashMap<Integer, List<String>>();
		String item;
		List<String> temp;
		int tamanioCadena;
		int i = 0, j = 0;
		while(i < listas.size()) {
			while(j < listas.get(i).size()) {
				item = listas.get(i).get(j);
				tamanioCadena = item.length();
				if(!mapa.containsKey(tamanioCadena)) 
					mapa.put(tamanioCadena, new LinkedList<String>());
				temp = mapa.get(tamanioCadena);
				temp.add(item);
				mapa.replace(tamanioCadena, temp);
				j++;
			}
			j = 0;
			i++;
		}
		return mapa;
	}
	public static Map<Integer,List<String>> ejercicio2(List<List<String>> listas){
		Map<Integer, List<String>> mapa = new HashMap<Integer, List<String>>();
		ejercicio2R(listas, mapa, 0, 0);
		return mapa;
	}
	private static void ejercicio2R(List<List<String>> listas, Map<Integer, List<String>> mapa, int i, int j){
		if (i < listas.size() && j < listas.get(0).size()) {
			String item = listas.get(i).get(j);
			int tamanioCadena = item.length();
			if(!mapa.containsKey(tamanioCadena)) {
				mapa.put(tamanioCadena, new LinkedList<String>());
			}
			List<String> temp = mapa.get(tamanioCadena);
			temp.add(item);
			mapa.put(tamanioCadena, temp);
			if(++j < listas.get(i).size()) 
				ejercicio2R(listas, mapa, i, j);
			else 
				ejercicio2R(listas, mapa, ++i, 0);
		}
	}
	public static Map<Integer,List<String>> ejercicio2F(List<List<String>> listas) {
		return listas.stream()
		.flatMap(lista -> lista.stream())
		.collect(Collectors.groupingBy(String::length));
		}
	
}
