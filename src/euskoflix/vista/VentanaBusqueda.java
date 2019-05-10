package euskoflix.vista;


import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import euskoflix.modelo.MatrizValoraciones;
import euskoflix.modelo.ModeloPersonas;

public class VentanaBusqueda extends JFrame{

	private JFrame frmBsqueda;
	private JTextField textField_1;

	public VentanaBusqueda() {
		initialize();
	}
	
	private void initialize() {
		frmBsqueda = new JFrame();
		
		frmBsqueda.setTitle("B\u00DASQUEDA");
		frmBsqueda.setBounds(100, 100, 450, 300);
		frmBsqueda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBsqueda.getContentPane().setLayout(null);
		
		JLabel lblIdUsuario = new JLabel("ID USUARIO");
		lblIdUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdUsuario.setBounds(165, 80, 89, 33);
		frmBsqueda.getContentPane().add(lblIdUsuario);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setBounds(137, 124, 146, 27);
		frmBsqueda.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("BUSCAR");
		btnNewButton.setBounds(165, 180, 89, 23);
		frmBsqueda.getContentPane().add(btnNewButton);
		frmBsqueda.setVisible(true);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//comprobar id
				if(!MatrizValoraciones.getValoracionesUsuario().getMatriz().containsKey(Integer.parseInt(textField_1.getText()))) {
					JOptionPane.showMessageDialog(null, "El id introducido es incorrecto", "MENSAJE DE ERROR", JOptionPane.WARNING_MESSAGE);
				}
				else {
					System.out.println("--> INICIANDO BUSQUEDA DEL PARA EL USUARIO "+textField_1.getText());
					
					HashMap<Integer, Double> vector=ModeloPersonas.getModeloPersonas().obtenerVect(Integer.parseInt(textField_1.getText()));
					Map v=ModeloPersonas.getModeloPersonas().ordenar(Integer.parseInt(textField_1.getText()),vector);
					
					System.out.println("<-- FINALIZADA LA BUSQUEDA\n");
					
					VentanaRdo vR=new VentanaRdo(v);
					frmBsqueda.dispose();
				}	
			}
		});
	}
}
