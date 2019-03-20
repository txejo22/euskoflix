package euskoflix.principal;

import java.io.IOException;


import euskoflix.modeloBueno.MatrizValoraciones;
import euskoflix.modeloBueno.ModeloProducto;
import euskoflix.modeloBueno.MovieTitles;
import euskoflix.vista.VentanaMostrarDatos;

public class Euskoflix {
	
	//ATRIBITOS
	private static String workspace; //args

	private static String movie_ratings;
	private static String movie_tags;
	private static String movie_titles;
	
	private static Euskoflix miEuskoflix;
	
	//CONTRUCTORA
	private Euskoflix() {}
	
	//MAIN
	public static void main(String[] args) throws IOException {	
		workspace=args[0];
		
		movie_ratings=workspace+"\\euskoflix\\src\\euskoflix\\archivos\\movie_ratings.csv";
		movie_tags=workspace+"\\euskoflix\\src\\euskoflix\\archivos\\movie_tags.csv";
		movie_titles=workspace+"\\euskoflix\\src\\euskoflix\\archivos\\movie_titles.csv";
		

		Euskoflix.getEuskoflix().cargarEstrucutras();	
		VentanaMostrarDatos v=new VentanaMostrarDatos();
	}
	
	//METODOS
	public static Euskoflix getEuskoflix() {
		if(miEuskoflix==null) {
			miEuskoflix=new Euskoflix();
		}
		return miEuskoflix;
	}
	
	private void cargarEstrucutras() throws IOException {
		MatrizValoraciones.getValoracionesUsuario().cargar(movie_ratings);
		ModeloProducto.getModeloProducto().cargar(movie_tags);
		MovieTitles.getMovieTitles().cargar(movie_titles);
	}
	
	

	
		
	
		
	
}
