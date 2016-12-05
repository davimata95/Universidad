package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import objetosTransfer.TipoUsuario;
import controladores.ControladorAlmacen;
import controladores.ControladorUsuario;

public class PanelAdmin extends JPanel {
	private static final long serialVersionUID = 1L;
	private Principal p;
	private ControladorUsuario cu;
	private ControladorAlmacen controlAlmacen;

	
	/**
	 * @param prin - Panel Principal.
	 * @param conu - Controlador Usuario.
	 * @param cAlmacen - Controlador Almacen.
	 */
	public PanelAdmin(Principal prin, ControladorUsuario conu, ControladorAlmacen cAlmacen) {
		setLayout(null);
		
		JLabel label = new JLabel("Identificado como: ");
		label.setBounds(73, 58, 131, 14);
		add(label);
		
		this.controlAlmacen = cAlmacen;
		this.p = prin;
		this.cu = conu;
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Acciones", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(71, 104, 422, 186);
		add(panel);
		
		JButton buttonAnyadirAdmin = new JButton("A\u00F1adir Admin");
		buttonAnyadirAdmin.setBackground(Color.WHITE);
		buttonAnyadirAdmin.setBounds(10, 28, 169, 23);
		buttonAnyadirAdmin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelNuevoAdmin();
			}
		});
		panel.add(buttonAnyadirAdmin);
		
		JButton buttonAnyadirVendedor = new JButton("A\u00F1adir Vendedor");
		buttonAnyadirVendedor.setBackground(Color.WHITE);
		buttonAnyadirVendedor.setBounds(10, 104, 169, 23);
		buttonAnyadirVendedor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelNuevoVendedor();
			}
		});
		panel.add(buttonAnyadirVendedor);
		
		JButton buttonAlmacen = new JButton("Almac\u00E9n");
		buttonAlmacen.setBackground(Color.WHITE);
		buttonAlmacen.setBounds(250, 104, 162, 23);
		buttonAlmacen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelAlmacen(TipoUsuario.ADMINISTRADOR);	
			}
		});
		panel.add(buttonAlmacen);
		
		JButton btnEliminarUsuario = new JButton("Eliminar Usuario");
		btnEliminarUsuario.setBackground(Color.WHITE);
		btnEliminarUsuario.setBounds(250, 28, 162, 23);
		btnEliminarUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelEliminarUsuario();
			}
		});
		panel.add(btnEliminarUsuario);
		
		JButton btnAadirSaldoA = new JButton("A\u00F1adir Saldo a la Tienda");
		btnAadirSaldoA.setBackground(Color.WHITE);
		btnAadirSaldoA.setBounds(82, 152, 250, 23);
		btnAadirSaldoA.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String cantidad = JOptionPane.showInputDialog("Cuanto dinero quieres introducir en la Tienda?");
					if(cantidad != null){
						if(!cantidad.equals(""))
						{
							try
							{
								double aumento = (Double.parseDouble(cantidad));
								if(aumento > 0)
								{
									controlAlmacen.anadirSaldo(aumento);
									JOptionPane.showMessageDialog(
											null,
											"Saldo Añadido",
											null,
											JOptionPane.INFORMATION_MESSAGE
									);
								}
								else
									throw new NumberFormatException();
							}
							catch(NumberFormatException e1)
							{
								JOptionPane.showMessageDialog(
										null,
										"Cantidad inválida.",
										null,
										JOptionPane.INFORMATION_MESSAGE
								);
							}
						}
					}
			}
		});
		panel.add(btnAadirSaldoA);
		
		JLabel label_1 = new JLabel("Administrador");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_1.setBounds(225, 55, 160, 14);
		add(label_1);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesi\u00F3n");
		btnCerrarSesion.setBackground(Color.WHITE);
		btnCerrarSesion.setBounds(205, 336, 180, 23);
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
