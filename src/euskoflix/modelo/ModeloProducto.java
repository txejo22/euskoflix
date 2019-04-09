package euskoflix.modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ModeloProducto {
	
	//ATRIBUTOS
	private HashMap<Integer,ArrayList<String>> hashmap;
	private HMStringDouble modeloProducto;
	private static ModeloProducto miModeloProducto;
	
	//CONSTRUCTORA
	private ModeloProducto() {
		hashmap=new HashMap<Integer,ArrayList<String>>();
	}
	
	//METODOS
	
	public static ModeloProducto getModeloProducto() {
		if(miModeloProducto==null) {
			miModeloProducto=new ModeloProducto();
		}
		return miModeloProducto;
	}

	public void cargar(String pPath) throws IOException {
		BufferedReader br=null;
		try {
			System.out.println("--> CARGANDO MODELO PRODUCTOS...");
			br=new BufferedReader(new FileReader(pPath));
			String linea=br.readLine();
			ArrayList<String> etiquetas=null;
			while(linea!=null) {
				String[] datos=linea.split(";");
				int movieId=Integer.parseInt(datos[0]);
				String tag=datos[1];
				if(ModeloProducto.getModeloProducto().hashmap.containsKey(movieId)) {
					etiquetas=ModeloProducto.getModeloProducto().hashmap.get(movieId);
					etiquetas.add(tag);
				}
				else {
					etiquetas=new ArrayList<String>();
					etiquetas.add(tag);	
				}
				ModeloProducto.getModeloProducto().hashmap.put(movieId, etiquetas);
				linea=br.readLine();
			}
			br.close();
			System.out.println("<-- MODELO PRODUCTOS CARGADO\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public TableModel toTableModelProducto() {
		System.out.println("--> HASHMAP TO JTABLE MODEL PRODCUTO");
	    DefaultTableModel model = new DefaultTableModel(
	        new Object[] { "MovieId", "Tag" }, 0
	    );
	    ArrayList<String> etiquetas;
	    int x=0;
	    for (Map.Entry<?, ?> entry : hashmap.entrySet()) {
	    	etiquetas=(ArrayList<String>) entry.getValue();
	    	for(int i=0; i<etiquetas.size(); i++) {
	    		model.addRow(new Object[] { entry.getKey(), etiquetas.get(i) });
	    		x++;
	    	}    
	    }
	    
	    System.out.println("<-- HASHMAP TO JTABLE MODEL PRODUCTO COMPLETADO");
	    System.out.println("El n�mero total de etiquetas es " + x +" y deber�an ser 41980\n");
	    
	    return model;   
	}
	
	private HashMap<Integer, HashMap<String, Integer>> crearMatrizEtiqProd() {
		System.out.println("\t--> CREANDO MATRIZ DE ETIQUETAS DE LOS PRODUCTOS");
		HashMap<Integer, HashMap<String, Integer>> matrizEtiqProd=new HashMap<Integer, HashMap<String, Integer>>();
		HashMap<String, Integer> hm;
		ArrayList<String> etiquetas=new ArrayList<String>();
		
		Integer valor;
		String e;
		
		 for (Map.Entry<?, ?> entry : hashmap.entrySet()) {
			hm=new HashMap<String, Integer>();
			etiquetas=(ArrayList<String>) entry.getValue();
			for(int j=0; j<etiquetas.size(); j++) {
				e=etiquetas.get(j);
				if(hm.containsKey(e)) {
					valor=hm.get(e);
					hm.put(e, valor+1);
				}
				else {
					hm.put(e, 1);
				}
			}
			matrizEtiqProd.put((Integer) entry.getKey(), hm);
		}
		 System.out.println("\t<-- FINALIZADA MATRIZ DE ETIQUETAS DE LOS PRODUCTOS");
		 return matrizEtiqProd;
	}
	
	public HMStringDouble crearModeloProducto() {
		System.out.println("--> CREANDO MODELO DE PRODUCTO");
		HashMap<Integer, HashMap<String, Integer>> matrizEtiqProd=crearMatrizEtiqProd();
		modeloProducto=new HMStringDouble();
		HashMap<String, Integer> hm;
		HashMap<String, Double> hm2;
		Integer movieId;
		String tag;
		Double valor;
		 for (Map.Entry<?, ?> entry : matrizEtiqProd.entrySet()) {
			 movieId=(Integer) entry.getKey();
			 hm=(HashMap<String, Integer>) entry.getValue();
			 hm2=new HashMap<String, Double>();
			 for (Map.Entry<?, ?> entry2 : hm.entrySet()) {
				 tag=(String) entry2.getKey();
				 valor=tfidf(tag, movieId, matrizEtiqProd);
				 hm2.put(tag, valor);
			 }
			 modeloProducto.put(movieId, hm2);
			}
		 System.out.println("<-- FINALIZADO MODELO DE PRODUCTO\n");
		 return modeloProducto;
	}
	
	private Double tfidf(String pTag, Integer pMovieId, HashMap<Integer, HashMap<String, Integer>> pMatrizEtiqProd) {
		Double tfidf=0.0;
		//formula tfidf(t)=tf x log(N/Nt)
		Integer tf=pMatrizEtiqProd.get(pMovieId).get(pTag); //el numero de veces que aparece la etiqueta t en una pelicula
		Integer N=pMatrizEtiqProd.size(); //el numero total de productos
		Integer Nt=numAparicionesTag(pTag,pMatrizEtiqProd); //el numero de productos a los que se aplica la etiqueta t
		tfidf=tf*Math.log10((double) N/(double) Nt);
		return tfidf;
	}
	
	private Integer numAparicionesTag(String pTag,HashMap<Integer, HashMap<String, Integer>> pMatrizEtiqProd) {
		Integer rdo=0;
		HashMap<String, Integer> hm;
		 for (Map.Entry<?, ?> entry : pMatrizEtiqProd.entrySet()) {
			 hm=(HashMap<String, Integer>) entry.getValue();
			 if(hm.containsKey(pTag)) {
				 rdo=rdo+1;
			 }
		 }
		 return rdo;
	}
}
