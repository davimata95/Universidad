package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import objetosTransfer.TipoUsuario;
import objetosTransfer.Usuario;
import controladores.ControladorUsuario;
import excepciones.UsuarioInvalido;

public class PanelEliminarUsuario extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private Principal p;
	private ControladorUsuario c;
	private final JComboBox<TipoUsuario> comboBox;
	private final JList<Usuario> list;

	/**
	 * @param prin - Panel Principal.
	 * @param cu - Controlador usuario.
	 */
	public PanelEliminarUsuario(Principal prin , final ControladorUsuario cu) {
		setLayout(null);
		
		this.p = prin;
		this.c = cu;
		
		JLabel label = new JLabel("Identificado como:");
		label.setBounds(68, 26, 107, 14);
		add(label);
		
		JLabel label_1 = new JLabel("Administrador");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_1.setBounds(193, 24, 204, 14);
		add(label_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(22, 61, 545, 37);
		add(panel);
		panel.setLayout(null);
		
		comboBox = new JComboBox<TipoUsuario>(TipoUsuario.values());
		comboBox.setBounds(299, 8, 137, 18);
		panel.add(comboBox);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 11, 84, 14);
		panel.add(lblNombre);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Nombre Usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(16, 102, 557, 237);
		add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 16, 545, 214);
		panel_1.add(scrollPane);
		
		list = new JList<Usuario>();
		scrollPane.setViewportView(list);
		
		txtNombre = new JTextField();
		txtNombre.setText("");
		txtNombre.setBounds(92, 7, 197, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Usuario> listaUsu = new ArrayList<Usuario>();
				Usuario usu = new Usuario(txtNombre.getText(), null , (TipoUsuario)comboBox.getSelectedItem());
				listaUsu = cu.getListaUsuarios(usu);
				
				DefaultListModel<Usuario> modelo = new DefaultListModel<Usuario>();
				for(int i = 0; i<listaUsu.size(); i++){
			       modelo.addElement(listaUsu.get(i));
				}
				list.setModel(modelo);
			}
		});
		btnBuscar.setBounds(446, 0, 99, 37);
		panel.add(btnBuscar);
		
		
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(22, 343, 107, 49);
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onCambiarAPanelAdmin();
			}
		});
		add(btnAtras);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(Color.WHITE);
		btnEliminar.setBounds(454, 343, 107, 49);
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!list.isSelectionEmpty()){
					try {
						int i = JOptionPane.showConfirmDialog(null,
						"�Desea Eliminar este Usuario?",
						"Eliminar",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE
						);
						if(i == JOptionPane.YES_OPTION){
							c.eliminarUsuario(list.getSelectedValue());
							JOptionPane.showMessageDialog(
									null,
									"Usuario eliminado",
									null,
									JOptionPane.INFORMATION_MESSAGE
							);
							p.onCambiarAPanelAdmin();
						}
						
					} catch (UsuarioInvalido e1) {
						JOptionPane.showMessageDialog(
								null,
								e1.getMessage(),
								null,
								JOptionPane.INFORMATION_MESSAGE
						);
					}
				}
			}
		});
		add(btnEliminar);
		
		

	}
	
}
