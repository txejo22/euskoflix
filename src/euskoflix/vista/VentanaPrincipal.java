package euskoflix.vista;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;

public class VentanaPrincipal {

	private JFrame frmEuskoflix;

	public VentanaPrincipal() {
		initialize();
	}

	private void initialize() {
		frmEuskoflix = new JFrame();
		frmEuskoflix.setTitle("EUSKOFLIX");
		frmEuskoflix.setBounds(100, 100, 450, 300);
		frmEuskoflix.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEuskoflix.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("MOSTRAR DATOS");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(244, 76, 160, 32);
		frmEuskoflix.getContentPane().add(btnNewButton);
		
		JButton button = new JButton("BUSQUEDA");
		button.setBounds(244, 138, 160, 32);
		frmEuskoflix.getContentPane().add(button);
		
		JTextPane txtpnEuskoflix = new JTextPane();
		txtpnEuskoflix.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtpnEuskoflix.setBackground(SystemColor.inactiveCaption);
		txtpnEuskoflix.setToolTipText("");
		txtpnEuskoflix.setEditable(false);
		txtpnEuskoflix.setText("               EUSKOFLIX\r\n\r\n\r\nBienvenido a nuestro sistema de recomendaci\u00F3n. A continuaci\u00F3n, se pueden realizar las opciones que se muestran a la derecha. Disfrute de la experiencia.");
		txtpnEuskoflix.setBounds(30, 33, 184, 196);
		frmEuskoflix.getContentPane().add(txtpnEuskoflix);
		frmEuskoflix.setVisible(true);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaMostrarDatos v=new VentanaMostrarDatos();
			}
			});
		

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBusqueda v=new VentanaBusqueda();
			}
			});
	
	}
}
