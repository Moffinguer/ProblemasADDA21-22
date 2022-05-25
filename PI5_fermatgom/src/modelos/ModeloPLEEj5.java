package modelos;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class ModeloPLEEj5 {
public record Ciudad(String ciudad, Double poblacion) {
	public static Ciudad make(String[] args) {
		return new Ciudad(args[0], Double.parseDouble(args[1]));
	}
}
public record Carretera(String origen, String destino, String autovia, Double carretera) {
	public static Carretera make(String[] args) {
		return new Carretera(args[0], args[1], args[2], Double.parseDouble(args[3]));
	}
}
public static IntegerVertexGraphView<Ciudad, Carretera> mapaInt;
public static Graph<Ciudad, Carretera> mapa;
public static Double habitantes,  distCarretera;
public static String origen, destino;
public static Predicate<Double> checkHabitantes;
public static Predicate<Double> checkDistCarretera;

public static void readFile(String file, int i) {
	Function<Carretera, Double> wr = e -> e.carretera;
	Function<String[], Ciudad> vr = v -> Ciudad.make(v);
	Function<String[], Carretera> er = e -> Carretera.make(e);
	List<String> lines = Files2.linesFromFile("./ficheros/" + file + ".txt").stream().map(x -> x.strip()).toList();
	int startRead = lines.indexOf(file + i + ".txt:");
	String[] temp = lines.get(startRead + 1).split(" ");
	habitantes = Double.parseDouble(temp[temp.length - 2]);
	temp = lines.get(startRead + 2).split(" ");
	distCarretera = Double.parseDouble(temp[temp.length - 2]);
	temp = lines.get(startRead + 3).split(";");
	origen = temp[0].split(":")[1].replaceAll(" ", "");
	destino = temp[1].split(":")[1].replaceAll(" ", "");
	mapa = GraphsReader.newGraph("./ficheros/" + file + i + ".txt", vr, er, Graphs2::simpleWeightedGraph, wr);
	mapaInt = IntegerVertexGraphView.of(ModeloPLEEj5.mapa);
	if(i == 1) {
		checkHabitantes = allRestrictions.get(i - 1); 
		checkDistCarretera = allRestrictions.get(i);
	}else if(i == 2) {
		checkDistCarretera = allRestrictions.get(i + 1); 
		checkHabitantes  = allRestrictions.get(i);
	}else if(i == 3) {
		checkDistCarretera = allRestrictions.get(i + 2); 
		checkHabitantes  = allRestrictions.get(i + 1);
	}
}
private static List<Predicate<Double>> allRestrictions = List.of(v -> v.compareTo(getHabitantes()) > 0,
		e -> e.compareTo(getDistCarretera()) > 0,
		v -> v.compareTo(getHabitantes()) <= 0,
		e -> e.compareTo(getDistCarretera()) >= 0,
		v -> v.compareTo(getHabitantes()) > 0,
		e -> e.compareTo(getDistCarretera()) < 0
		);

public static Integer getNumCarreteras() { return getCarreteras().size();}
public static Integer getNumCiudades() { return getCiudades().size();}
public static List<Carretera> getCarreteras(){ return mapa.edgeSet().stream().toList();}
public static List<Ciudad> getCiudades() { return mapa.vertexSet().stream().toList();}
public static Double getPesoCarretera(Integer i, Integer j) { return mapaInt.getEdgeWeight(i,j);}
public static Ciudad getCiudad(Integer i) { return mapaInt.getVertex(i);} 
public static Double getHabitantes() { return habitantes;}
public static Double getDistCarretera() { return distCarretera;}
public static String getDestino() { return destino;}
public static String getOrigen() { return origen;}
public static Integer esOrigen(Integer i) { return getCiudad(i).ciudad.equals(getOrigen()) ? 1 : 0;}
public static Integer esDestino(Integer i) { return getCiudad(i).ciudad.equals(getDestino()) ? 1 : 0;}
public static Integer checkeaHab(Integer i) { return checkHabitantes.test(getCiudad(i).poblacion) ? 1 : 0;}
public static Integer checkeaCarretera(Integer i, Integer j) { return checkDistCarretera.test(mapaInt.getEdgeWeight(i, j)) ? 1 : 0;}
}
