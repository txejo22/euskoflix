package euskoflix.principal;



import java.io.IOException;


import euskoflix.modelo.MatrizValoraciones;
import euskoflix.modelo.ModeloPersonas;
import euskoflix.modelo.ModeloProducto;
import euskoflix.modelo.MovieTitles;
import euskoflix.vista.VentanaPrincipal;

public class Euskoflix {
	
	
	//ATRIBITOS
	private static String movie_ratings="./src/euskoflix/archivos/movie_ratings.csv";
	private static String movie_tags="./src/euskoflix/archivos/movie_tags.csv";
	private static String movie_titles="./src/euskoflix/archivos/movie_titles.csv";
	

	
	private static Euskoflix miEuskoflix;
	
	//CONTRUCTORA
	private Euskoflix() {
		
	}
	
	//MAIN
	public static void main(String[] args) throws IOException {	
		Euskoflix.getEuskoflix().cargarEstrucutras();
		
		ModeloProducto.getModeloProducto().crearModeloProducto();
		ModeloPersonas.getModeloPersonas().crearModeloPersona(MatrizValoraciones.getValoracionesUsuario().getMatriz(), ModeloProducto.getModeloProducto().getModelo(), 3.5);
		ModeloPersonas.getModeloPersonas().crearSimilitud(ModeloProducto.getModeloProducto().getModelo(), ModeloPersonas.getModeloPersonas().getModelo());
		
		VentanaPrincipal v=new VentanaPrincipal();	
	}
	
	//METODOS
	public static Euskoflix getEuskoflix() {
		if(miEuskoflix==null) {
			miEuskoflix=new Euskoflix();
		}
		return miEuskoflix;
	}
	
	public void cargarEstrucutras() throws IOException {
		MatrizValoraciones.getValoracionesUsuario().cargar(movie_ratings);
		ModeloProducto.getModeloProducto().cargar(movie_tags);
		MovieTitles.getMovieTitles().cargar(movie_titles);
	}	
}
