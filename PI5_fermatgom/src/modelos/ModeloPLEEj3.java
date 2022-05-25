package modelos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import us.lsi.common.Files2;

public class ModeloPLEEj3 {
	public record Componente(String name, Double tElab, Double tProd) {
		public static Componente make(String args) {
			String name;
			Double tElab, tProd;
			args = args.replaceAll(" ", "");
			String[] temp = args.split(":");
			name = temp[0];
			temp = temp[1].split(";");
			tProd = Double.parseDouble(temp[0].split("=")[1]);
			tElab = Double.parseDouble(temp[1].split("=")[1]);
			return new Componente(name, tElab, tProd);	
		}
	}
	public record Producto(String name, Double precio, Map<String, Double> componentes, Integer max_u) {
		public static Producto make(String args) {
			String name; Integer max_u; Double precio;
			Map<String, Double> componentes = new HashMap<>();
			String[] temp = args.replaceAll(" ", "").split("->");
			name = temp[0];
			temp = temp[1].split(";");
			precio = Double.valueOf(temp[0].split("=")[1]);
			max_u = Integer.valueOf(temp[2].split("=")[1]);
			temp = temp[1].split("=")[1].split(",");
			for(int i = 0; i < temp.length; i++) {
				String producto; Double unidades;
				String[] tuple = temp[i].split(":");
				unidades = Double.parseDouble(tuple[1].substring(0, tuple[1].length() - 1));
				producto = tuple[0].substring(1);
				componentes.put(producto, unidades);
			}
			return new Producto(name, precio, componentes, max_u);	
		}
	}
	public static List<Componente> componentes;
	public static Double tProd, tManual;
	public static List<Producto> productos;
	public static void readFile(String rute) {
		List<String> lines = Files2.linesFromFile(rute);
		tProd = Double.valueOf(lines.get(0).replaceAll(" ", "").split("=")[1]);
		tManual = Double.valueOf(lines.get(1).replaceAll(" ", "").split("=")[1]);
		componentes = new LinkedList<>();
		productos = new LinkedList<>();
		int to = lines.indexOf("// PRODUCTOS");
		for(int i = 3; i < to; ++i) componentes.add(Componente.make(lines.get(i)));
		for(int i = to + 1; i < lines.size(); ++i) productos.add(Producto.make(lines.get(i)));
	}
	public static Integer numComponentes() { return componentes.size();}
	public static Integer numProductos() { return productos.size();}
	public static Double getTProd() { return tProd;}
	public static Double getTMan() { return tManual;}
	public static Double getprecioProducto(Integer i) { return productos.get(i).precio;}
	public static Double getTProdComp(Integer i) { 
		Double sum = 0.;
		Map<String, Double> p = productos.get(i).componentes;
		for(Entry<String, Double> j : p.entrySet()) {
			for(Componente c: componentes) {
				if(c.name.equals(j.getKey())) {
					sum += c.tProd * j.getValue(); 
				}
			}
		}
		return sum;
	}
	public static Double getTElabComp(Integer i) { 
		Double sum = 0.;
		Map<String, Double> p = productos.get(i).componentes;
		for(Entry<String, Double> j : p.entrySet()) {
			for(Componente c: componentes) {
				if(c.name.equals(j.getKey())) {
					sum += c.tElab * j.getValue(); 
				}
			}
		}
		return sum;
	}
	public static Integer getMaxUnProd(Integer i) { return productos.get(i).max_u;}
	public static Double sumPrecioProducto(Integer i) { return (double) productos.get(i).componentes.values().stream().mapToDouble(x -> (Double)x).sum();}
	public static Integer componenteEnProducto(Integer  i) { return productos.stream().anyMatch(x -> x.componentes.keySet().contains(componentes.get(i))) ? 1 : 0;}
}
