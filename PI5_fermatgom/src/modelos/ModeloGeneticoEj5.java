package modelos;

import java.util.List;

import org.apache.commons.math3.genetics.PermutationChromosome;

import modelos.ModeloPLEEj5.Ciudad;
import us.lsi.ag.SeqNormalData;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class ModeloGeneticoEj5 implements SeqNormalData<String>{
	private ModeloGeneticoEj5(String string, int i) {
		ModeloPLEEj5.readFile(string, i);
	}
	public static ModeloGeneticoEj5 create(String string, int i) {
		// TODO Auto-generated method stub
		return new ModeloGeneticoEj5(string, i);
	}
	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Permutation;
	}
	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = 0., error = 0.;
		String origen = ModeloPLEEj5.getOrigen(), destino = ModeloPLEEj5.getDestino();
		if(value.size() > 2) {
			for(int i= 0; i < value.size() - 1; i++) {
				Ciudad ciudad1 = ModeloPLEEj5.getCiudad(value.get(i));
				Ciudad ciudad2 = ModeloPLEEj5.getCiudad(value.get(i + 1));
				if(ModeloPLEEj5.mapa.containsEdge(ciudad1, ciudad2)) {
					Double e = ModeloPLEEj5.getPesoCarretera(value.get(i), value.get(i + 1));
					error += ModeloPLEEj5.checkeaCarretera(value.get(i), value.get(i + 1)) == 1 ? 0 : 100;
					goal += e;
				}else {
					error += 100;
				}
				if(!ModeloPLEEj5.getCiudad(i).ciudad().equals(origen)) {
					error += ModeloPLEEj5.checkHabitantes.test(ModeloPLEEj5.getCiudad(value.get(i)).poblacion()) ? 0 : 1000;
				}
				
			}
			error += ModeloPLEEj5.esOrigen(value.get(0)) == 1 ? 0 : 10e4;
			error += ModeloPLEEj5.esDestino(value.get(value.size() - 1)) == 1 ? 0 : 10e4;
		}else {
			error += 100000 * 100000;
		}
		return error < 100 ? - goal : - 10000 * error;
	}
	@Override
	public String solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return value.toString();
	}
	@Override
	public Integer itemsNumber() {
		// TODO Auto-generated method stub
		return ModeloPLEEj5.getNumCiudades();
	}
}
