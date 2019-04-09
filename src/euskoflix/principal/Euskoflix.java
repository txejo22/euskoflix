package euskoflix.principal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import euskoflix.modelo.HMIntegerDouble;
import euskoflix.modelo.HMStringDouble;
import euskoflix.modelo.MatrizValoraciones;
import euskoflix.modelo.ModeloPersonas;
import euskoflix.modelo.ModeloProducto;
import euskoflix.modelo.MovieTitles;
import euskoflix.vista.VentanaMostrarDatos;

public class Euskoflix {
	
	
	//ATRIBITOS
	private static String movie_ratings="./src/euskoflix/archivos/movie_ratings.csv";
	private static String movie_tags="./src/euskoflix/archivos/movie_tags.csv";
	private static String movie_titles="./src/euskoflix/archivos/movie_titles.csv";
	
	private static String mSim="./src/euskoflix/archivos/matrizSimilitud.txt";
	
	private static Euskoflix miEuskoflix;
	
	//CONTRUCTORA
	private Euskoflix() {
		
	}
	
	//MAIN
	public static void main(String[] args) throws IOException, URISyntaxException {	
		Euskoflix.getEuskoflix().cargarEstrucutras();	
		//VentanaMostrarDatos v=new VentanaMostrarDatos();
		HMIntegerDouble matrizValoraciones=MatrizValoraciones.getValoracionesUsuario().getMatriz();
		System.out.println(matrizValoraciones.size());
		HMStringDouble modeloProducto=ModeloProducto.getModeloProducto().crearModeloProducto();
		HMStringDouble modeloPersona=ModeloPersonas.getModeloPersonas().crearModeloPersona(matrizValoraciones, modeloProducto, 3.5);
		HMIntegerDouble matrizSimilitud=ModeloPersonas.getModeloPersonas().crearSimilitud(modeloProducto, modeloPersona);
		
		System.out.println(matrizSimilitud.get(2164).get(4045));
		System.out.println(matrizSimilitud.get(63).get(4045));
		System.out.println(matrizSimilitud.get(807).get(4045));
		System.out.println(matrizSimilitud.get(187).get(4045));
		System.out.println(matrizSimilitud.get(11).get(4045));
		
		matrizSimilitud.hashmap2txt(mSim);
	}
	
	//METODOS
	public static Euskoflix getEuskoflix() {
		if(miEuskoflix==null) {
			miEuskoflix=new Euskoflix();
		}
		return miEuskoflix;
	}
	
	private void cargarEstrucutras() throws IOException, URISyntaxException {
		MatrizValoraciones.getValoracionesUsuario().cargar(movie_ratings);
		ModeloProducto.getModeloProducto().cargar(movie_tags);
		MovieTitles.getMovieTitles().cargar(movie_titles);
	}	
}
