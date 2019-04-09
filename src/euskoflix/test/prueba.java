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
		HMIntegerDouble matrizValoraciones;
		HMStringDouble modeloProducto;
		HMStringDouble modeloPersona;
		HMIntegerDouble matrizSimilitud;
		
		@Before
		public void setUp() throws IOException {
			app=Euskoflix.getEuskoflix();
			app.cargarEstrucutras();
			matrizValoraciones=MatrizValoraciones.getValoracionesUsuario().getMatriz();
			modeloProducto=ModeloProducto.getModeloProducto().crearModeloProducto();
			modeloPersona=ModeloPersonas.getModeloPersonas().crearModeloPersona(matrizValoraciones, modeloProducto, 3.5);
			matrizSimilitud=ModeloPersonas.getModeloPersonas().crearSimilitud(modeloProducto, modeloPersona);
		}
		
		@Test
		public void test() {
			System.out.println("Id. Persona 4045\t Id. Pelicula 2164\t Valoracion 0.1899");
			assertEquals((Double) 0.1899, matrizSimilitud.get(2164).get(4045));
			
			System.out.println("Id. Persona 4045\t Id. Pelicula 63\t Valoracion 0.2612");
			assertEquals((Double) 0.2612, matrizSimilitud.get(63).get(4045));
			
			System.out.println("Id. Persona 4045\t Id. Pelicula 807\t Valoracion 0.2363");
			assertEquals((Double) 0.2363, matrizSimilitud.get(807).get(4045));
			
			System.out.println("Id. Persona 4045\t Id. Pelicula 187\t Valoracion 0.2059");
			assertEquals((Double) 0.2059, matrizSimilitud.get(187).get(4045));
		
			System.out.println("Id. Persona 4045\t Id. Pelicula 11\t Valoracion 0.3596");
			assertEquals((Double) 0.3596, matrizSimilitud.get(11).get(4045));
		}

}
