package euskoflix.modeloBueno;

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
	    System.out.println("El número total de etiquetas es " + x +" y deberían ser 41980\n");
	    
	    return model;   
	}
}
