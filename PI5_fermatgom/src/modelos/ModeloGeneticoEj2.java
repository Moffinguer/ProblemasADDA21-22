package modelos;

import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.BinaryData;
import us.lsi.ag.Chromosome;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class ModeloGeneticoEj2 implements ValuesInRangeData<Integer, String>{
	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return ModeloPLEEj2.numOfCandidatos();
	}
	private ModeloGeneticoEj2(String string) {
		ModeloPLEEj2.readFile(string);
	}
	public static ModeloGeneticoEj2 create(String string) {
		// TODO Auto-generated method stub
		return new ModeloGeneticoEj2(string);
	}
	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Auto-generated method stub
		Integer goal = 0;
		Double errorPres = 0.;
		Double error = 0.;
		Integer err = 0;
		for(int i = 0; i < value.size(); i++) {
			goal += value.get(i) * ModeloPLEEj2.getValoracion(i);
			errorPres += value.get(i) * ModeloPLEEj2.getCandidatoSueldo(i);
		}
		error = errorPres - ModeloPLEEj2.presupuestoMinimo();
		error *= error;
		for(int i = 0; i < value.size(); i++) {
			for(int j = 0; j < this.size(); j++) {
				if(ModeloPLEEj2.esInCompatible(i, j) == 1)  {
					err++;
				}else {
					err = value.get(i) + value.get(j) > 1 && i == j ? err + 1 : err;
				}
			}
			for(int j = 0; j < ModeloPLEEj2.numCualidades(); j++) {
				if(ModeloPLEEj2.tieneCualidad(i, j) == 0) {
					err++;
				}
			}
		}
		error +=  err*err;
		return goal - error * 10e5;
	}

	@Override
	public String solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return value.toString();
	}
	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Binary;
	}
	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}
}
