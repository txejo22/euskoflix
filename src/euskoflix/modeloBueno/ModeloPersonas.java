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

}
