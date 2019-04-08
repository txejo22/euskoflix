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
	
	public HashMap<Integer, HashMap<String, Double>> getModeloPersona() {
		return modeloPersona;
	}
	public void crearModeloPersona(HashMap<Integer,HashMap<Integer, Double>> pHMMatrizVal, HashMap<Integer,HashMap<String, Double>> pHMModeloProductos, Double pUmbral) {
		System.out.println("--> CREANDO MODELO PERSONA");
		for (Map.Entry<?, ?> entry : pHMMatrizVal.entrySet()) {
			HashMap<Integer, Double> val= (HashMap<Integer, Double>) entry.getValue();
			//ArrayList<Integer> movies=new ArrayList<Integer>();
			HashMap<Integer,HashMap<String, Double>> mPReducido=new HashMap<Integer,HashMap<String, Double>>();
			for (Map.Entry<?, ?> entry2 : val.entrySet()) {
				if((Double) entry2.getValue()>=3.5){
					mPReducido.put((Integer) entry2.getKey(), pHMModeloProductos.get(entry2.getKey()));
					
					//movies.add((Integer) entry2.getKey());
				}
			}
			System.out.println(mPReducido);
			/*ArrayList<String> rdo=guardarEtiquetas(mPReducido);
			Iterator<String> itr=rdo.iterator();
			String act;
			while(itr.hasNext()){
				System.out.println("HOLA");
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
			}*/
		}
		
		//print();
		System.out.println("<-- FINALIZADO MODELO PERSONA\n");
	}
	
	public void crearModeloPersona2(HashMap<Integer, HashMap<Integer, Double>> pHMMatrizVal, HashMap<Integer,HashMap<String, Double>> pHMModeloProductos, Double pUmbral) {
		System.out.println("--> CREANDO MODELO PERSONA");
		HashMap<Integer, Double> movieVal=new HashMap<Integer, Double>();
		Double puntuacion;
		HashMap<String, Double> hmAux=new HashMap<String, Double>();
		
		for (Map.Entry<?, ?> entry : pHMMatrizVal.entrySet()) { //por cada usuario
			movieVal=(HashMap<Integer, Double>) entry.getValue(); //Obtenemos por cada usuario las peliculas y sus puntuaciones
			HashMap<Integer, HashMap<String, Double>> pHMModeloProductoReducido=new HashMap<Integer, HashMap<String, Double>>();
			for (Map.Entry<?, ?> entry2 : movieVal.entrySet()) { //por cada pelicula
				puntuacion=(Double) entry2.getValue(); //obtenemos su puntuacion
				if(puntuacion>=pUmbral) {
					hmAux=pHMModeloProductos.get(entry2.getKey());
					if(hmAux!=null) {
						pHMModeloProductoReducido.put((Integer) entry2.getKey(), hmAux); //se a�ade al modelo reducido las peliculas con un umbral > 3.5
					}	
				}
			}
			sumar(pHMModeloProductoReducido, (Integer) entry.getKey());
		}	
		System.out.println("<-- FINALIZADO MODELO PERSONA");
	}
	
	public void sumar(HashMap<Integer, HashMap<String, Double>> pHMModeloProductoReducido, int pUserId) {
		HashMap<String, Double> hMtfidf=new HashMap<String, Double>();
		Double tfidf;
		Double tfidfAux;
		HashMap<String, Double> hmAux=null;
		for (Map.Entry<?, ?> entry : pHMModeloProductoReducido.entrySet()) { //por cada pelicula
			hMtfidf=(HashMap<String, Double>) entry.getValue();
			for (Map.Entry<?, ?> entry2 : hMtfidf.entrySet()) { //por cada etiqueta
				tfidf=(Double) entry2.getValue(); //obtenemos su tfidf
				if(!modeloPersona.containsKey(pUserId)) { //si el modeloPersona no contiene el userId se crea
					hmAux=new HashMap<String, Double>();
					hmAux=hMtfidf;
				}
				else if(tfidf==null) {
					tfidf=0.0;
				}
				else { //si lo tiene actualiza el valor
					hmAux=modeloPersona.get(pUserId);
					tfidfAux=(Double)hmAux.get(entry2.getKey());
					if(tfidfAux==null) {
						tfidfAux=0.0;
					}
					tfidf=(Double)tfidf+(Double)tfidfAux;
					hmAux.put((String) entry2.getKey(), (Double)tfidf);
				}
				modeloPersona.put(pUserId, hmAux);		
			}
		}
	}
	
	private ArrayList<String> guardarEtiquetas(HashMap<Integer,HashMap<String, Double>> pMPReducido){
		HashMap<String, Double> etiquetas=new HashMap<String, Double>();

		ArrayList<String> rdo=new ArrayList<String>();
		for (Map.Entry<?, ?> entry : pMPReducido.entrySet()) {
			etiquetas=(HashMap<String, Double>) entry.getValue();
			for (Map.Entry<?, ?> entry2 : etiquetas.entrySet()) {
					if(!rdo.contains(entry2.getKey())){
						rdo.add((String) entry2.getKey());
					}
					else {
						
					}
			}
		}
		return rdo;
	}
	
	public Double cosV_W(HashMap<String, Double> pV, HashMap<String, Double> pW) {
		//sumatorio(vi*wi)
		 //srqt(sumatorio(vi^2))*sqrt(sumatorio(wi^2))
		//cos(V,W)=suma1/suma2
		Double rdo=0.0;
		Double suma1=0.0;
		Double suma2=0.0;
		Double suma3=0.0;
		for (Map.Entry<?, ?> entry : pV.entrySet()) {
			if(pW.containsKey(entry.getKey())) {
				suma1=suma1+((Double)entry.getValue()*pW.get((String)entry.getKey()));
				suma2=suma2+Math.pow((Double)entry.getValue(), 2);
				suma3=suma3+Math.pow(pW.get((String)entry.getKey()), 2);
			}
		}
		Double denominador=Math.sqrt(suma2)*Math.sqrt(suma3);
		rdo=(Double)suma1/(Double)denominador;
		return rdo;
	}
	
	public HashMap<Integer,HashMap<Integer,Double>> crearSimilitud(HashMap<Integer,HashMap<String, Double>> pHMModeloProductos,HashMap<Integer, HashMap<String, Double>> pModeloPersona){
		HashMap<Integer,HashMap<Integer,Double>> matrizSimilitud=new HashMap<Integer,HashMap<Integer,Double>>();
		HashMap<Integer, Double> hm;
		
		HashMap<String, Double> vi=null;
		HashMap<String, Double> wi=null;
		Double rdo=0.0;
		
		for (Map.Entry<?, ?> entry : pHMModeloProductos.entrySet()) {
			vi=(HashMap<String, Double>) entry.getValue();
			hm=new HashMap<Integer, Double>();
			for (Map.Entry<?, ?> entry2 : pModeloPersona.entrySet()) {
				wi=(HashMap<String, Double>) entry2.getValue();
				rdo=cosV_W(vi, wi);
				hm.put((Integer) entry2.getKey(), rdo);
			}
			matrizSimilitud.put((Integer) entry.getKey(), hm);
		}
		 for (Map.Entry<?, ?> entry : matrizSimilitud.entrySet()) {
		    	System.out.println(entry.getKey()+" "+(HashMap<Integer, Double>) entry.getValue());
		}
		return matrizSimilitud;
	}
	
	public void print() {
		HashMap<String, Double> hm;
		 for (Map.Entry<?, ?> entry : modeloPersona.entrySet()) {
		    	hm=(HashMap<String, Double>) entry.getValue();
		    	System.out.println(entry.getKey()+" "+hm);
		    }
	}

}
