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
	
	private HMStringDouble crearMatrizEtiqProd() throws IOException {
		System.out.println("\t--> CREANDO MATRIZ DE ETIQUETAS DE LOS PRODUCTOS...");
		HMStringDouble matrizEtiqProd=new HMStringDouble();
		HashMap<String, Double> hm;
		ArrayList<String> etiquetas=new ArrayList<String>();
		
		Double valor;
		String e;
		
		 for (Map.Entry<?, ?> entry : hashmap.entrySet()) {
			hm=new HashMap<String, Double>();
			etiquetas=(ArrayList<String>) entry.getValue();
			for(int j=0; j<etiquetas.size(); j++) {
				e=etiquetas.get(j);
				if(hm.containsKey(e)) {
					valor=new Double(hm.get(e));
					hm.put(e, (Double)valor+1.0);
				}
				else {
					hm.put(e, (Double)1.0);
				}
			}
			matrizEtiqProd.put((Integer) entry.getKey(), hm);
		}
		 System.out.println("\t<-- FINALIZADA MATRIZ DE ETIQUETAS DE LOS PRODUCTOS");
		 matrizEtiqProd.hashmap2txt("./src/euskoflix/archivos/matrizEtiqProd.txt");
		 return matrizEtiqProd;
	}
	
	private HMStringDouble crearMatrizEtiqProdAux(HMStringDouble pMatrizEtiqProd) throws IOException {
		System.out.println("\t--> CREANDO MATRIZ DE ETIQUETAS DE LOS PRODUCTOS AUXILIAR...");
		HMStringDouble matrizEtiqProdAux=new HMStringDouble();
		HashMap<String, Double> hm;
		 HashMap<String, Double> hmAux;
		Double mod;
		Double val;
		Double valAux;
		 for (Map.Entry<?, ?> entry : pMatrizEtiqProd.entrySet()) {
			 mod=calcularModulo((HashMap<String, Double>) entry.getValue());
			 hm=(HashMap<String, Double>) entry.getValue();
			 hmAux=new HashMap<String, Double>();
			 for (Map.Entry<?, ?> entry2 : hm.entrySet()) {
				 val=(new Double((Double) entry2.getValue()))/(Double)mod;
				 hmAux.put((String)entry2.getKey(), val);
			 }
			 matrizEtiqProdAux.put((Integer) entry.getKey(), hmAux);
		 }
		System.out.println("\t<-- FINALIZADA MATRIZ DE ETIQUETAS DE LOS PRODUCTOS AUXILIAR");
		matrizEtiqProdAux.hashmap2txt("./src/euskoflix/archivos/matrizEtiqProdAux.txt");
		return matrizEtiqProdAux;
	}
	
	public HMStringDouble crearModeloProducto() throws IOException {
		System.out.println("--> CREANDO MODELO DE PRODUCTO...");
		HMStringDouble matrizEtiqProd=crearMatrizEtiqProd();
		HMStringDouble matrizEtiqProdAux=crearMatrizEtiqProdAux(matrizEtiqProd);
		modeloProducto=new HMStringDouble();
		HashMap<String, Double> hm;
		HashMap<String, Double> hm2;
		Integer movieId;
		String tag;
		Double valor;
		 for (Map.Entry<?, ?> entry : matrizEtiqProdAux.entrySet()) {
			 movieId=(Integer) entry.getKey();
			 hm=(HashMap<String, Double>) entry.getValue();
			 hm2=new HashMap<String, Double>();
			 for (Map.Entry<?, ?> entry2 : hm.entrySet()) {
				 tag=(String) entry2.getKey();
				 valor=tfidf(tag, movieId, matrizEtiqProdAux);
				 hm2.put(tag, valor);
			 }
			 modeloProducto.put(movieId, hm2);
			}
		 System.out.println("<-- FINALIZADO MODELO DE PRODUCTO\n");
		 modeloProducto.hashmap2txt("./src/euskoflix/archivos/modeloProducto.txt");
		 return modeloProducto;
	}
	
	private Double tfidf(String pTag, Integer pMovieId, HMStringDouble pMatrizEtiqProdAux) {
		Double tfidf;
		//formula tfidf(t)=tf x log(N/Nt)
		Double tf=new Double(pMatrizEtiqProdAux.get(pMovieId).get(pTag)); //el numero de veces que aparece la etiqueta t en una pelicula
		Double N=new Double(pMatrizEtiqProdAux.size()); //el numero total de productos
		Double Nt=new Double(numAparicionesTag(pTag,pMatrizEtiqProdAux)); //el numero de productos a los que se aplica la etiqueta t
		tfidf=tf*(Math.log10((Double) N/(Double) Nt));
		return tfidf;
	}
	
	private Integer numAparicionesTag(String pTag, HMStringDouble pMatrizEtiqProd) {
		Integer rdo=0;
		HashMap<String, Double> hm;
		 for (Map.Entry<?, ?> entry : pMatrizEtiqProd.entrySet()) {
			 hm=(HashMap<String, Double>) entry.getValue();
			 if(hm.containsKey(pTag)) {
				 rdo=rdo+1;
			 }
		 }
		 return rdo;
	}
	
	private Double calcularModulo(HashMap<String, Double> pVectMatrizEtiqProd) {
		Double rdo=0.0;
		for (Map.Entry<?, ?> entry : pVectMatrizEtiqProd.entrySet()) {
			rdo=rdo+Math.pow((Double) entry.getValue(), 2);
		}
		rdo=Math.sqrt(rdo);
		return rdo;
	}
}
