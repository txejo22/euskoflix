package euskoflix.test;


import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import euskoflix.modelo.HMIntegerDouble;
import euskoflix.modelo.HMStringDouble;
import euskoflix.modelo.MatrizValoraciones;
import euskoflix.modelo.ModeloPersonas;
import euskoflix.modelo.ModeloProducto;
import euskoflix.principal.Euskoflix;

	public class prueba {
		Euskoflix app;
		
		@Before
		public void setUp() throws IOException {
			app=Euskoflix.getEuskoflix();
			app.cargarEstrucutras();
			ModeloProducto.getModeloProducto().crearModeloProducto();
			ModeloPersonas.getModeloPersonas().crearModeloPersona(MatrizValoraciones.getValoracionesUsuario().getMatriz(), ModeloProducto.getModeloProducto().getModelo(), 3.5);
			ModeloPersonas.getModeloPersonas().crearSimilitud(ModeloProducto.getModeloProducto().getModelo(), ModeloPersonas.getModeloPersonas().getModelo());
		}
		
		@Test
		public void test() {
			System.out.println("Id. Persona 4045\t Id. Pelicula 2164\t Valoracion 0.1899");
			assertEquals((Double) 0.1899, ModeloPersonas.getModeloPersonas().getMatrizSimilitud().get(2164).get(4045));
			
			System.out.println("Id. Persona 4045\t Id. Pelicula 63\t Valoracion 0.2612");
			assertEquals((Double) 0.2612, ModeloPersonas.getModeloPersonas().getMatrizSimilitud().get(63).get(4045));
			
			System.out.println("Id. Persona 4045\t Id. Pelicula 807\t Valoracion 0.2363");
			assertEquals((Double) 0.2363, ModeloPersonas.getModeloPersonas().getMatrizSimilitud().get(807).get(4045));
			
			System.out.println("Id. Persona 4045\t Id. Pelicula 187\t Valoracion 0.2059");
			assertEquals((Double) 0.2059, ModeloPersonas.getModeloPersonas().getMatrizSimilitud().get(187).get(4045));
		
			System.out.println("Id. Persona 4045\t Id. Pelicula 11\t Valoracion 0.3596");
			assertEquals((Double) 0.3596, ModeloPersonas.getModeloPersonas().getMatrizSimilitud().get(11).get(4045));
		}

}
