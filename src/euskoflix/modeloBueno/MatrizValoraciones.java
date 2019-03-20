package euskoflix.modeloBueno;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MatrizValoraciones {
	
	//ATRIBUTOS
	private HashMap<Integer,HashMap<Integer, Double>> hashmap;
	private static MatrizValoraciones miMatrizValoraciones;
	
	//CONSTRUCTORA
	private MatrizValoraciones() {
		hashmap=new HashMap<Integer,HashMap<Integer, Double>>();
	}
	
	//METODOS
	public static MatrizValoraciones getValoracionesUsuario() {
		if(miMatrizValoraciones==null) {
			miMatrizValoraciones=new MatrizValoraciones();
		}
		return miMatrizValoraciones;
	}
	
	public void cargar(String pPath) throws IOException {
		BufferedReader br=null;
		try {
			System.out.println("--> CARGANDO MATRIZ VALORACIONES...");
			br=new BufferedReader(new FileReader(pPath));
			String linea=br.readLine();
			HashMap<Integer, Double> valoraciones=null;
			int x=0;
			while(linea!=null) {
				String[] datos=linea.split(",");
				
				int userId=Integer.parseInt(datos[0]);
				int movieId=Integer.parseInt(datos[1]);
				Double valoracion=Double.parseDouble(datos[2]);
				
				if(MatrizValoraciones.getValoracionesUsuario().hashmap.containsKey(movieId)) {
					valoraciones=MatrizValoraciones.getValoracionesUsuario().hashmap.get(movieId);
					valoraciones.put(userId, valoracion);
				}
				else {
					valoraciones=new HashMap<Integer, Double>();
				}
				MatrizValoraciones.getValoracionesUsuario().hashmap.put(movieId,valoraciones);
				linea=br.readLine();
			}
			br.close();
			System.out.println("<-- MATRIZ VALORACIONES CARGADO\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public TableModel toTableMatrizValoraciones() {
		System.out.println("--> HASHMAP TO JTABLE MATRIZ VALORACIONES");
	    DefaultTableModel model = new DefaultTableModel(
	        new Object[] { "UserId", "MovieId", "Rating" }, 0
	    );
	   
	    HashMap<Integer, Double> valoraciones;
	    int x=0;
	  
	    for (Map.Entry<?, ?> entry : hashmap.entrySet()) {
	    	valoraciones=(HashMap<Integer, Double>) entry.getValue();
	    	for (Map.Entry<?, ?> entryAux : valoraciones.entrySet()) {
	    		model.addRow(new Object[] { entryAux.getKey(), entry.getKey() , entryAux.getValue()});
	    		x++;
	    	}
	    	x++;
	    }    
	    
	    System.out.println("<-- HASHMAP TO JTABLE MODEL MATRIZ VALORACIONES COMPLETADO");
	    System.out.println("El número total de valoraciones es " + x +" y deberían ser 338355\n");
	    
	    return model;   
	}
	
}
