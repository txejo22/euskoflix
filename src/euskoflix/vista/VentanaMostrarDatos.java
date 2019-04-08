package euskoflix.vista;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import euskoflix.modelo.MatrizValoraciones;
import euskoflix.modelo.ModeloPersonas;
import euskoflix.modelo.ModeloProducto;
import euskoflix.modelo.MovieTitles;

public class VentanaMostrarDatos extends JFrame{

	private JFrame frmEuskoflix;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;

	public VentanaMostrarDatos() {
		initialize();		
	}

	private void initialize() {
		frmEuskoflix = new JFrame();
		frmEuskoflix.setTitle("EUSKOFLIX");
		frmEuskoflix.setBounds(100, 100, 450, 300);
		frmEuskoflix.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEuskoflix.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmEuskoflix.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		table_1 = new JTable(MatrizValoraciones.getValoracionesUsuario().toTableMatrizValoraciones());
		JScrollPane scrollPane_1 = new JScrollPane(table_1);
		tabbedPane.addTab("Ratings", null, scrollPane_1, null);
		
		table_2 = new JTable(ModeloProducto.getModeloProducto().toTableModelProducto());
		JScrollPane scrollPane_2 = new JScrollPane(table_2);
		tabbedPane.addTab("Tags", null, scrollPane_2, null);
		
		table_3 = new JTable(MovieTitles.getMovieTitles().toTableModelTitle());
		JScrollPane scrollPane_3 = new JScrollPane(table_3);
		tabbedPane.addTab("Titles", null, scrollPane_3, null);
		
		frmEuskoflix.setVisible(true);
	}

}
