package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import objetosTransfer.TipoUsuario;
import controladores.ControladorSocio;
import controladores.ControladorUsuario;

public class PanelVendedor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Principal p;
	@SuppressWarnings("unused")
	private ControladorSocio cs;
	private ControladorUsuario cu;
	
	/**
	 * @param prin - Panel Principal.
	 * @param cons - Controlador Socio.
	 * @param conu - Controlador Usuario.
	 */
	public PanelVendedor(Principal prin, ControladorSocio cons, ControladorUsuario conu) {
setLayout(null);
		
		this.p = prin;
		this.cs = cons;
		this.cu = conu;
		
		
		JLabel label = new JLabel("Identificado como: ");
		label.setBounds(67, 59, 131, 14);
		add(label);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Acciones", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(66, 106, 422, 251);
		add(panel);
		
		JButton buttonAnyadirSocio = new JButton("A\u00F1adir Socio");
		buttonAnyadirSocio.setBackground(Color.WHITE);
		buttonAnyadirSocio.setBounds(34, 101, 142, 47);
		buttonAnyadirSocio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelNuevoSocio();
			}
		});
		panel.add(buttonAnyadirSocio);
		
		JButton buttonCliente = new JButton("Cliente");
		buttonCliente.setBackground(Color.WHITE);
		buttonCliente.setBounds(34, 33, 142, 47);
		buttonCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelCliente(null,0);
				
			}
		});
		panel.add(buttonCliente);
		
		JButton buttonSocio = new JButton("Socio");
		buttonSocio.setBackground(Color.WHITE);
		buttonSocio.setBounds(230, 33, 142, 47);
		buttonSocio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelBuscarSocio();
				
			}
		});
		panel.add(buttonSocio);
		
		JButton buttonAlmacen = new JButton("Almac\u00E9n");
		buttonAlmacen.setBackground(Color.WHITE);
		buttonAlmacen.setBounds(134, 179, 142, 47);
		buttonAlmacen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelAlmacen(TipoUsuario.VENDEDOR);
			}
		});
		panel.add(buttonAlmacen);
		
		JButton btnEliminarSocio = new JButton("Eliminar Socio");
		btnEliminarSocio.setBackground(Color.WHITE);
		btnEliminarSocio.setBounds(230, 101, 142, 47);
		btnEliminarSocio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelEliminarSocio();
				
			}
		});
		panel.add(btnEliminarSocio);
		
		JLabel label_1 = new JLabel("Vendedor");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_1.setBounds(224, 56, 149, 14);
		add(label_1);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesi\u00F3n");
		btnCerrarSesion.setBackground(Color.WHITE);
		btnCerrarSesion.setBounds(166, 385, 207, 23);
		btnCerrarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cu.cerrarSesion();
				p.onCambiarAPanelLogin();
			}
		});
		add(btnCerrarSesion);

	}
}
