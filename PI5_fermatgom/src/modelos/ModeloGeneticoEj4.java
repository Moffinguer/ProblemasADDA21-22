package modelos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import us.lsi.ag.BinaryData;
import us.lsi.ag.ChromosomeData;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class ModeloGeneticoEj4 implements ValuesInRangeData<Integer, String>{

	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return ModeloPLEEj4.numElementos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Auto-generated method stub
		Double goal = 0., errTotal = 0.;
		Map<Integer,List<Integer>> elOnContainer = new HashMap<>();
		for(int i = 0; i < value.size(); i++) {
			if(!value.get(i).equals(0)) {
				if(!elOnContainer.containsKey(value.get(i)-1)) elOnContainer.put(value.get(i) - 1, new LinkedList<>());
				var temp = elOnContainer.get(value.get(i) - 1);
				temp.add(i);
				elOnContainer.put(value.get(i) - 1, temp);
			}
		}
		int err = 0;
		for(Entry<Integer, List<Integer>> par : elOnContainer.entrySet()) {
			int estimado = 0;
			double equivalente = - ModeloPLEEj4.getCapacidad(par.getKey());
			for(int i = value.size() - 1; i > -1 ; i--) {
				estimado += ModeloPLEEj4.elementoEnContenedor(i, par.getKey()) == 1 ? ModeloPLEEj4.getPeso(i) : 0; 
			}
			double dif = estimado + equivalente;
			if (dif == 0) 
				goal += 100;
			else
				err += dif;
		}
		return goal - 10 * errTotal;
	}

	@Override
	public String solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return value.toString();
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Range;
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return ModeloPLEEj4.numContenedores() + 1;
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}

	private ModeloGeneticoEj4(String string) {
		ModeloPLEEj4.readFile(string);
	}
	public static ModeloGeneticoEj4 create(String string) {
		// TODO Auto-generated method stub
		return new ModeloGeneticoEj4(string);
	}

}
