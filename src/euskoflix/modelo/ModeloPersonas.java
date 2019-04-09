package euskoflix.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ModeloPersonas {

	//ATRIBUTOS
	private HMStringDouble modeloPersona;
	private static ModeloPersonas miModeloPersonas;
	
	//CONSTRUCTORA
	private ModeloPersonas() {
		modeloPersona=new HMStringDouble();
	}
	
	//METODOS
	public static ModeloPersonas getModeloPersonas() {
		if(miModeloPersonas==null) {
			miModeloPersonas=new ModeloPersonas();
		}
		return miModeloPersonas;
	}
	
	public HMStringDouble crearModeloPersona(HMIntegerDouble pHMMatrizVal, HMStringDouble pHMModeloProducto, Double pUmbral) {
		System.out.println("--> CREANDO MODELO PERSONA");
		HashMap<Integer, Double> movieVal=new HashMap<Integer, Double>();
		Double puntuacion;
		
		for (Map.Entry<?, ?> entry : pHMMatrizVal.entrySet()) { //por cada usuario
			movieVal=(HashMap<Integer, Double>) entry.getValue(); //Obtenemos por cada usuario las peliculas y sus puntuaciones
			HMStringDouble pHMModeloProductoReducido=new HMStringDouble();
			for (Map.Entry<?, ?> entry2 : movieVal.entrySet()) { //por cada pelicula
				HashMap<String, Double> hmAux=new HashMap<String, Double>();
				puntuacion=(Double) entry2.getValue(); //obtenemos su puntuacion
				if(puntuacion>=pUmbral) { //si la putuación es superior al umbral
					hmAux=pHMModeloProducto.get((Integer) entry2.getKey());
					if(hmAux!=null) {
						pHMModeloProductoReducido.put((Integer) entry2.getKey(), hmAux); //se anade al modelo reducido las peliculas con un umbral > 3.5
					}	
				}
			}
			sumar(pHMModeloProductoReducido, (Integer) entry.getKey());
		}	
		System.out.println("<-- FINALIZADO MODELO PERSONA\n");
		return modeloPersona;
	}
	
	private void sumar(HMStringDouble pHMModeloProductoReducido, int pUserId) {
		HashMap<String, Double> hMtfidf=new HashMap<String, Double>();
		Double tfidf;
		Double tfidfAux;
		
		for (Map.Entry<?, ?> entry : pHMModeloProductoReducido.entrySet()) { //por cada pelicula
			hMtfidf=(HashMap<String, Double>) entry.getValue(); //obtenemos por cada etiqueta su tfidf
			HashMap<String, Double> hmAux=new HashMap<String, Double>();
			for (Map.Entry<?, ?> entry2 : hMtfidf.entrySet()) { //por cada etiqueta
				if(!modeloPersona.containsKey(pUserId)) { //si el modeloPersona no contiene el userId 
					hmAux=new HashMap<String, Double>();
					modeloPersona.put(pUserId, hmAux);	
				}
				else {
					tfidf=(Double) entry2.getValue(); //obtenemos su tfidf 
					tfidfAux=(Double)hmAux.get(entry2.getKey()); //obtenemos su tfidf antiguo
					
					if(tfidfAux==null && tfidf!=null) {
						tfidfAux=0.0;
					}
					else if(tfidfAux!=null && tfidf==null) {
						tfidf=0.0;
					}
					else if(tfidfAux==null && tfidf==null) {
						tfidf=0.0;
						tfidfAux=0.0;
					}
					tfidf=(Double)tfidf+(Double)tfidfAux;
					hmAux.put((String) entry2.getKey(), (Double)tfidf);
				}		
			}
			modeloPersona.put(pUserId, hmAux);
		}
	}
	
	private Double cosV_W(HashMap<String, Double> pV, HashMap<String, Double> pW) {
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
	
	public HMIntegerDouble crearSimilitud(HMStringDouble pHMModeloProducto,HMStringDouble pModeloPersona){
		System.out.println("--> CREANDO MATRIZ DE SIMILITUD");
		HMIntegerDouble matrizSimilitud=new HMIntegerDouble();
		HashMap<Integer, Double> hm=new HashMap<Integer, Double>();
		
		HashMap<String, Double> vi=new HashMap<String, Double>();
		HashMap<String, Double> wi=new HashMap<String, Double>();
		Double rdo=0.0;
		
		for (Map.Entry<?, ?> entry : pHMModeloProducto.entrySet()) { //por cada pelicula
			vi=(HashMap<String, Double>) entry.getValue();
			hm=new HashMap<Integer, Double>();
			for (Map.Entry<?, ?> entry2 : pModeloPersona.entrySet()) { //por cada usuario
				wi=(HashMap<String, Double>) entry2.getValue();
				rdo=cosV_W(vi, wi);
				hm.put((Integer) entry2.getKey(), rdo);
			}
			matrizSimilitud.put((Integer) entry.getKey(), hm);
		}
		System.out.println("<-- FINALIZADA MATRIZ DE SIMILITUD\n");
		return matrizSimilitud;
	}
}
