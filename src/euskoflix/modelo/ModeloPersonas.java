package euskoflix.modelo;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ModeloPersonas {

	//ATRIBUTOS
	private HMStringDouble modeloPersona;
	private HMIntegerDouble matrizSimilitud;
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
	
	public HMIntegerDouble getMatrizSimilitud() {
		return matrizSimilitud;
	}
	
	public HMStringDouble crearModeloPersona(HMIntegerDouble pHMMatrizVal, HMStringDouble pHMModeloProducto, Double pUmbral) throws IOException {
		System.out.println("--> CREANDO MODELO PERSONA...");
		HashMap<Integer, Double> movieVal=new HashMap<Integer, Double>();
		Double puntuacion;
		HMStringDouble pHMModeloProductoReducido;
		HashMap<String, Double> hmAux;
		for (Map.Entry<?, ?> entry : pHMMatrizVal.entrySet()) { //por cada usuario
			movieVal=(HashMap<Integer, Double>) entry.getValue(); //Obtenemos por cada usuario las peliculas y sus puntuaciones
			pHMModeloProductoReducido=new HMStringDouble();
			hmAux=new HashMap<String, Double>();
			for (Map.Entry<?, ?> entry2 : movieVal.entrySet()) { //por cada pelicula
				puntuacion=(Double) entry2.getValue(); //obtenemos su puntuacion
				if(puntuacion>=pUmbral) { //si la putuaciï¿½n es superior al umbral
					hmAux=pHMModeloProducto.get((Integer) entry2.getKey());
					if(hmAux!=null) {
						pHMModeloProductoReducido.put((Integer) entry2.getKey(), hmAux); //se anade al modelo reducido las peliculas con un umbral > 3.5
					}	
				}
			}
			sumar(pHMModeloProductoReducido, (Integer) entry.getKey());
		}	
		System.out.println("<-- FINALIZADO MODELO PERSONA\n");
		modeloPersona.hashmap2txt("./src/euskoflix/archivos/modeloPersonas.txt");
		return modeloPersona;
	}
	
	private void sumar(HMStringDouble pHMModeloProductoReducido, int pUserId) {
		HashMap<String, Double> hMtfidf=new HashMap<String, Double>();
		Double tfidf;
		Double tfidfAux;
		
		for (Map.Entry<?, ?> entry : pHMModeloProductoReducido.entrySet()) { //por cada pelicula
			hMtfidf=(HashMap<String, Double>) entry.getValue(); //obtenemos por cada pelicula su tfidf(vector)
			HashMap<String, Double> hmAux=new HashMap<String, Double>();
			for (Map.Entry<?, ?> entry2 : hMtfidf.entrySet()) { //por cada etiqueta
				if(!modeloPersona.containsKey(pUserId)) { //si el modeloPersona no contiene el userId 
					hmAux.put((String)entry2.getKey(), (Double)entry2.getValue());
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
		if (denominador==0.0){
			rdo=0.0;
		}else{
			rdo=(Double)suma1/(Double)denominador;
		}
		return rdo;
	}
	
	public HMIntegerDouble crearSimilitud(HMStringDouble pHMModeloProducto, HMStringDouble pModeloPersona){
		System.out.println("--> CREANDO MATRIZ DE SIMILITUD...");
		matrizSimilitud=new HMIntegerDouble();
		HashMap<Integer, Double> hm=new HashMap<Integer, Double>();
		
		HashMap<String, Double> vi=new HashMap<String, Double>();
		HashMap<String, Double> wi=new HashMap<String, Double>();
		Double rdo=0.0;
		DecimalFormat format=new DecimalFormat("#.####");
		for (Map.Entry<?, ?> entry : pHMModeloProducto.entrySet()) { //por cada pelicula
			vi=(HashMap<String, Double>) entry.getValue();
			hm=new HashMap<Integer, Double>();
			for (Map.Entry<?, ?> entry2 : pModeloPersona.entrySet()) { //por cada usuario
				wi=(HashMap<String, Double>) entry2.getValue();
				rdo=cosV_W(vi, wi);
				hm.put((Integer) entry2.getKey(), Double.parseDouble(format.format(rdo).replace(",",".")));
			}
			matrizSimilitud.put((Integer) entry.getKey(), hm);
		}
		System.out.println("<-- FINALIZADA MATRIZ DE SIMILITUD\n");
		return matrizSimilitud;
	}
	
	public Map ordenar(Integer pId,HashMap<Integer, Double> pVector) {
		System.out.println("\t --> INICIANDO ORDENACION...");
		HashMap<Integer, Double> pVectorF=ModeloPersonas.getModeloPersonas().filtrar(pId, pVector);
		List list = new LinkedList(pVectorF.entrySet());
		//Para ordenar descendentemente
		 Collections.sort(list, new Comparator() {
			 public int compare(Object o1, Object o2) {
				 return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
			 }
		 });
		 
		 //Poner la lista ordenada otra vez en el hashmap
		 Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
		 Integer x=0;
		 	for (Iterator it = list.iterator(); it.hasNext();) {
		 		Map.Entry<Integer, Double> entry = (Map.Entry<Integer, Double>)it.next();
		 		sortedMap.put(entry.getKey(), entry.getValue());
		 		if(x==9) {
		 			break;
		 		}
		 		x++;
		 	}
		 	System.out.println("\t <-- ORDENACION FINALIZADA");
		 return sortedMap;
	 }
	
	public HashMap<Integer, Double> filtrar(Integer pId, HashMap<Integer, Double> pVector) {
		System.out.println("\t\t --> INICIANDO FILTRADO...");
		//Se comprueba que no haya visto la película
		HashMap<Integer, Double> aux=new HashMap<Integer, Double>();
		for (Map.Entry<?, ?> entry : pVector.entrySet()) { 
			if(!MatrizValoraciones.getValoracionesUsuario().getMatriz().get(pId).containsKey((Integer)entry.getKey())) {
				aux.put((Integer)entry.getKey(), (Double)entry.getValue());
			}
		}
		System.out.println("\t\t <-- FINALIZADO EL FILTRADO");
		return aux;
	}
	
	public TableModel toTableRdo(Map<?,?> pVector) {
		System.out.println("\t --> HASHMAP TO JTABLE TABLA RESULTADO");
	    DefaultTableModel model = new DefaultTableModel(
	        new Object[] { "MovieId", "Title", "Rating" },0);
	    DecimalFormat f= new DecimalFormat("#.####");
	    for (Map.Entry<?, ?> entry : pVector.entrySet()) {
	    		model.addRow(new Object[] { entry.getKey(), MovieTitles.getMovieTitles().buscarPorId((Integer)entry.getKey()) , f.format((Double)entry.getValue()*10.0)});
	    }   
	    
	    System.out.println("\t <-- HASHMAP TO JTABLE MODEL TABLA RESULTADO COMPLETADO");
	    
	    return model;   
	}
	
	public HashMap<Integer, Double> obtenerVect(Integer pId) {
		System.out.println("\t --> OBTENIENDO VECTOR...");
		HashMap<Integer, Double> rdo=new HashMap<Integer, Double>();
		HashMap<Integer, Double> hm=new HashMap<Integer, Double>();
		for (Map.Entry<?, ?> entry : matrizSimilitud.entrySet()) {
			hm=(HashMap<Integer, Double>) entry.getValue();
	    	rdo.put((Integer)entry.getKey(), hm.get(pId));
	    }  
		System.out.println("\t <-- VECTOR OBTENIDO");
		return rdo;
		
	}
}
