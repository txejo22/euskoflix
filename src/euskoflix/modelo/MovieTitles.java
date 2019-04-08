package euskoflix.modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MovieTitles {
		
		//ATRIBUTOS
		private HashMap<Integer,String> hashmap;
		private static MovieTitles miHashMap;
		
		//CONSTRUCTORA
		private MovieTitles() {
			this.hashmap=new HashMap<Integer,String>();
		}
		
		//METODOS
		public static MovieTitles getMovieTitles() {
			if(miHashMap==null) {
				miHashMap=new MovieTitles();
			}
			return miHashMap;
		}
		
		public void cargar(String pPath) throws IOException {
			BufferedReader br=null;
			try {
				System.out.println("--> CARGANDO MOVIE TITLES...");
				br=new BufferedReader(new FileReader(pPath));
				String linea=br.readLine();
				while(linea!=null) {
					String[] datos=linea.split(";");
					int movieId=Integer.parseInt(datos[0]);
					String title=datos[1];
					hashmap.put(movieId, title);
					linea=br.readLine();
				}
				br.close();
				System.out.println("<-- MOVIE TITLES CARGADO\n");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		public TableModel toTableModelTitle() {
			System.out.println("--> HASHMAP TO JTABLE MODEL TITLES");
		    DefaultTableModel model = new DefaultTableModel(
		        new Object[] { "MovieId", "Title" }, 0
		    );
		    int x=0;
		    for (Map.Entry<?, ?> entry : hashmap.entrySet()) {
		        model.addRow(new Object[] { entry.getKey(), entry.getValue() });
		        x++;
		    }
		    
		    System.out.println("<-- HASHMAP TO JTABLE MODEL TITLES COMPLETADO");
		    System.out.println("El número total de títulos es " + x +" y deberían ser 100\n");
		    
		    return model;   
		}
	}
