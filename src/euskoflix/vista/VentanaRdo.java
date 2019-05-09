package euskoflix.vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

import euskoflix.modelo.ModeloPersonas;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class VentanaRdo extends JFrame{

	private JFrame frame;
	private JTable table;

	public VentanaRdo(Map pV) {
		initialize(pV);
	}
	
	private void initialize(Map pV) {
		System.out.println("--> CARGANDO RESULTADOS DE BUSQUEDA...");
		frame = new JFrame();
		frame.setTitle("RESULTADOS");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		table = new JTable(ModeloPersonas.getModeloPersonas().toTableRdo(pV));
		frame.getContentPane().add(table, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		
		JButton btnAtras = new JButton("ATRAS");
		panel_1.add(btnAtras);
		btnAtras.setVerticalAlignment(SwingConstants.BOTTOM);
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBusqueda vB=new VentanaBusqueda();
				frame.dispose();
			}
		});
		frame.setVisible(true);
		
		
		System.out.println("<-- RESULTADOS DE BUSQUEDA CARGADOS");
		
		
	
	}
}
