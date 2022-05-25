package modelos;

import java.util.LinkedList;
import java.util.List;

import us.lsi.common.Files2;

public class ModeloPLEEj1 {
	public record Memory(String name, Double capacity, Double tam_max) {
		public static Memory of(String args) {
			String name;
			Double capacity, tam_max;
			name = args.split(":")[0].replaceAll(" ", "");
			capacity = Double.parseDouble(args.replaceAll(" ", "").split(":")[1].split(";")[0].split("=")[1]);
			tam_max = Double.parseDouble(args.replaceAll(" ", "").split(":")[1].split(";")[1].split("=")[1]);
			return new Memory(name, capacity, tam_max);
		}
	}
	public  record File(String name, Double size) {
		public static File of(String args) {
			String name; Double size;
			name = args.split(":")[0].replaceAll(" ", "");
			size = Double.parseDouble(args.replaceAll(" ", "").split(":")[1].replaceAll(" ", ""));
			return new File(name, size);
		}
	}
	public static List<Memory> mems;
	public static List<File> files;
	public static void readFile(String rute) {
		List<String> lines = Files2.linesFromFile(rute);
		mems = new LinkedList<>();
		files = new LinkedList<>();
		int i;
		for(i = 1; !lines.get(i).contains("//"); i++) mems.add(Memory.of(lines.get(i)));
		for(i = i + 1 ; i < lines.size(); i++) files.add(File.of(lines.get(i)));
		
	}
	public static Double getTamArch(Integer i) { return files.get(i).size;}
	public static Double getTamMaxArchivo(Integer j) { return mems.get(j).tam_max;}
	public static Double getCapArchivo(Integer j) { return mems.get(j).capacity;}
	public static Integer getNumArchivos() { return files.size();}
	public static Integer getNumMem() { return mems.size();}
}
