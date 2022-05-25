package modelos;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.ChromosomeData;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import modelos.ModeloPLEEj1.File;
import modelos.ModeloPLEEj1.Memory;
public class ModeloGeneticoEj1  implements ValuesInRangeData<Integer, String>{

	

	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return ModeloPLEEj1.getNumArchivos();
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Range;
	}
	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double res = 0.;
		Long archivos = value.stream().filter(x -> !x.equals(0)).count();	
		Map<Integer,List<Integer>> filesOnMem = new HashMap<>();
		int j = 0;
		for(Integer i : value) {
			if(!i.equals(0)) {
				if(!filesOnMem.containsKey(i - 1)) filesOnMem.put(i - 1, new LinkedList<>());
				var temp = filesOnMem.get(i - 1);
				temp.add(j);
				filesOnMem.put(i - 1, temp);
			}
			j++;
		}
		Integer err1 = 0; Double err2 = 0.;
		for(Entry<Integer, List<Integer>> par: filesOnMem.entrySet()) {
			List<Integer> total = par.getValue();
			Integer index = par.getKey();
			Double capOcupada = total.stream().mapToDouble(x -> ModeloPLEEj1.getTamArch(x)).sum();
			err2 = capOcupada.compareTo(ModeloPLEEj1.getCapArchivo(index)) > 0 ?
					err2 + capOcupada : err2;
			for(int i = total.size() - 1; i > -1; i--) {
				err1 = ModeloPLEEj1.getTamArch(total.get(i))
						.compareTo(ModeloPLEEj1.getTamMaxArchivo(index)) <= 0 ? err1 + 1 : err1;
				
			}
		}
		Double totalError = AuxiliaryAg.distanceToEqZero(ModeloPLEEj1.getNumArchivos() - err1 + err2);
		return archivos - 10e5 * totalError;
	}

	@Override
	public String solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return value.toString();
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return ModeloPLEEj1.getNumMem() + 1;
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}
	private ModeloGeneticoEj1(String string) {
		ModeloPLEEj1.readFile(string);
	}
	public static ModeloGeneticoEj1 create(String string) {
		// TODO Auto-generated method stub
		return new ModeloGeneticoEj1(string);
	}

}
