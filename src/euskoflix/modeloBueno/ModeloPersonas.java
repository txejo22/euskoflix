package euskoflix.modeloBueno;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ModeloPersonas {

	//ATRIBUTOS
	private HashMap<Integer, HashMap<String, Double>> modeloPersona;
	private static ModeloPersonas miModeloPersonas;
	
	//CONSTRUCTORA
	private ModeloPersonas() {
		modeloPersona=new HashMap<Integer, HashMap<String, Double>>();
	}
	
	//METODOS
	public static ModeloPersonas getModeloPersonas() {
		if(miModeloPersonas==null) {
			miModeloPersonas=new ModeloPersonas();
		}
		return miModeloPersonas;
	}
	
	public void crearModeloPersona(HashMap<Integer,HashMap<Integer, Double>> pHMMatrizVal, HashMap<Integer,HashMap<String, Double>> pHMModeloProductos, Double pUmbral) {
		
		for (Map.Entry<?, ?> entry : pHMMatrizVal.entrySet()) {
			HashMap<String, Double> val= (HashMap<String, Double>) entry.getValue();
			//ArrayList<Integer> movies=new ArrayList<Integer>();
			HashMap<Integer,HashMap<String, Double>> mPReducido=new HashMap<Integer,HashMap<String, Double>>();
			for (Map.Entry<?, ?> entry2 : val.entrySet()) {
				if(val.get(entry2.getValue())>=3.5){
					mPReducido.put((Integer) entry2.getKey(), pHMModeloProductos.get(entry2.getKey()));
					//movies.add((Integer) entry2.getKey());
				}
			}
			ArrayList<String> rdo=guardarEtiquetas(mPReducido);
			Iterator<String> itr=rdo.iterator();
			String act;
			while(itr.hasNext()){
				act=itr.next();
				double suma=0.0;
				HashMap<String, Double> hm=new HashMap<String, Double>();
				for(Map.Entry<?, ?> entry3 : mPReducido.entrySet()){
					HashMap<String, Double> e=(HashMap<String, Double>) entry3.getValue();
					if(e.containsKey(act)){
						suma=suma+e.get(act);
					}else{
						suma=e.get(act);
					}
				}
				hm.put(act, suma);
				modeloPersona.put((Integer) entry.getKey(), hm);
			}
		}
		print();
	}
	
	private ArrayList<String> guardarEtiquetas(HashMap<Integer,HashMap<String, Double>> pMPReducido){
		HashMap<String, Double> etiquetas;
		ArrayList<String> rdo=new ArrayList<String>();
		for (Map.Entry<?, ?> entry : pMPReducido.entrySet()) {
			etiquetas=(HashMap<String, Double>) entry.getValue();
			for (Map.Entry<?, ?> entry2 : etiquetas.entrySet()) {
					if(!rdo.contains(entry2.getKey())){
						rdo.add((String) entry2.getKey());
					}
			}
		}
		return rdo;
	}
	
	public Double cosV_W(Double pV, Double pW) {
		Double rdo=0.0;
		
		Double suma1; //sumatorio(vi*wi)
		Double suma2; //srqt(sumatorio(vi^2))*sqrt(sumatorio(wi^2))
		//cos(V,W)=suma1/suma2
		return rdo;
	}
	
	public void print() {
		HashMap<String, Double> hm;
		 for (Map.Entry<?, ?> entry : modeloPersona.entrySet()) {
		    	hm=(HashMap<String, Double>) entry.getValue();
		    	System.out.println(entry.getKey()+" "+hm);
		    }
	}

}
