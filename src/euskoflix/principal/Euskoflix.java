package euskoflix.principal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import euskoflix.modeloBueno.MatrizValoraciones;
import euskoflix.modeloBueno.ModeloPersonas;
import euskoflix.modeloBueno.ModeloProducto;
import euskoflix.modeloBueno.MovieTitles;
import euskoflix.vista.VentanaMostrarDatos;

public class Euskoflix {
	
	
	//ATRIBITOS
	private static String movie_ratings="./src/euskoflix/archivos/movie_ratings.csv";
	private static String movie_tags="./src/euskoflix/archivos/movie_tags.csv";
	private static String movie_titles="./src/euskoflix/archivos/movie_titles.csv";
	
	private static Euskoflix miEuskoflix;
	
	//CONTRUCTORA
	private Euskoflix() {}
	
	//MAIN
	public static void main(String[] args) throws IOException, URISyntaxException {	
		Euskoflix.getEuskoflix().cargarEstrucutras();	
		VentanaMostrarDatos v=new VentanaMostrarDatos();
		ModeloProducto.getModeloProducto().crearMatrizEtiqProd();
		ModeloProducto.getModeloProducto().crearModeloProducto();
		//MatrizValoraciones.getValoracionesUsuario().normalizarValoraciones();
		ModeloPersonas.getModeloPersonas().crearModeloPersona2(MatrizValoraciones.getValoracionesUsuario().getHMSinNormalizar(), ModeloProducto.getModeloProducto().getmodeloProducto(), 3.5);
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
