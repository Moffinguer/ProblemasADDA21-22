package modelos;

import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.ChromosomeData;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class ModeloGeneticoEj3 implements ValuesInRangeData<Integer, String>{

	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return ModeloPLEEj3.numProductos();
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Range;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = 0., errprod = 0., errelab = 0., errmaxunprod = 0.;
		int j = 0;
		Integer err = 0;
		for(Integer i : value) {
			if(i.compareTo(this.max(j)) <= 0) {
				goal += ModeloPLEEj3.getprecioProducto(j) * i;
				errprod += ModeloPLEEj3.getTProdComp(j) * i;
				errelab += ModeloPLEEj3.getTElabComp(j) * i;			
			}else {
				err += 1;
			}
		}
		Double errTotal = AuxiliaryAg.distanceToEqZero(Math.abs(ModeloPLEEj3.tProd - errprod));
		errTotal += AuxiliaryAg.distanceToEqZero(Math.abs(ModeloPLEEj3.tManual - errelab));
		errTotal = AuxiliaryAg.distanceToEqZero(errTotal);
		errTotal += err*err;
		return goal - 10e5 * errTotal;
	}

	@Override
	public String solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return value.toString();
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return ModeloPLEEj3.getMaxUnProd(i);//this.size() + 1;
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}

	private ModeloGeneticoEj3(String string) {
		ModeloPLEEj3.readFile(string);
	}
	public static ModeloGeneticoEj3 create(String string) {
		// TODO Auto-generated method stub
		return new ModeloGeneticoEj3(string);
	}

}
