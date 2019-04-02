package euskoflix.modeloBueno;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ModeloPersonas {

	//ATRIBUTOS
	private HashMap<Integer, HashMap<Integer, Double>> hashmap;
	private static ModeloPersonas miModeloPersonas;
	
	//CONSTRUCTORA
	private ModeloPersonas() {
		hashmap=new HashMap<Integer, HashMap<Integer, Double>>();
	}
	
	//METODOS
	public static ModeloPersonas getModeloPersonas() {
		if(miModeloPersonas==null) {
			miModeloPersonas=new ModeloPersonas();
		}
		return miModeloPersonas;
	}
	
	public void crearModeloPersona(HashMap<Integer,HashMap<Integer, Double>> pHMNormalizado, HashMap<Integer,HashMap<Integer, Double>> pHMModeloProductos, Double pUmbral) {
		
		
	}
	
	public Double cosV_W(Double pV, Double pW) {
		Double rdo=0.0;
		
		Double suma1; //sumatorio(vi*wi)
		Double suma2; //srqt(sumatorio(vi^2))*sqrt(sumatorio(wi^2))
		//cos(V,W)=suma1/suma2
		return rdo;
	}

}
