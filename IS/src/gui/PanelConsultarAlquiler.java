package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objetosTransfer.Alquiler;
import objetosTransfer.Producto;
import objetosTransfer.Socio;
import controladores.ControladorAlquiler;
import excepciones.ErrorAlquiler;

/**
 * @author Sorin
 */
public class PanelConsultarAlquiler extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldIdProd;
	private JTextField textFieldNombreProd;
	private JTextField textFieldFechaAlquiler;
	private JTextField textFieldFechaDevolucion;
	private JButton btnDevolver, btnAtras;
	private JList<Alquiler> listAlquileres;
	private DefaultListModel<Alquiler> modelAlquileres;
	
	private ControladorAlquiler controlAlquiler;
	private Socio socio;
	private Principal principal;
	private JScrollPane scrollPane;

	/**
	 * @param controlAlquiler - Controlador Alquiler.
	 * @param socio - Un socio.
	 * @param principal - Panel Principal.
	 */
	public PanelConsultarAlquiler(ControladorAlquiler controlAlquiler, Socio socio, Principal principal) {
		this.controlAlquiler = controlAlquiler;
		this.socio = socio;
		this.principal = principal;
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(45, 37, 503, 347);
		add(panel);
		panel.setLayout(null);
		
		JLabel lbl1 = new JLabel("Productos alquilados");
		lbl1.setBounds(21, 11, 174, 14);
		panel.add(lbl1);
		
		JLabel lblNewLabel = new JLabel("Id Producto");
		lblNewLabel.setBounds(288, 65, 70, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNombreProducto = new JLabel("Nombre Producto");
		lblNombreProducto.setBounds(288, 11, 100, 14);
		panel.add(lblNombreProducto);
		
		textFieldIdProd = new JTextField();
		textFieldIdProd.setEditable(false);;
		textFieldIdProd.setColumns(10);
		textFieldIdProd.setBounds(225, 90, 212, 20);
		panel.add(textFieldIdProd);
		
		textFieldNombreProd = new JTextField();
		textFieldNombreProd.setEditable(false);;
		textFieldNombreProd.setColumns(10);
		textFieldNombreProd.setBounds(226, 34, 211, 20);
		panel.add(textFieldNombreProd);
		
		textFieldFechaAlquiler = new JTextField();
		textFieldFechaAlquiler.setEditable(false);;
		textFieldFechaAlquiler.setColumns(10);
		textFieldFechaAlquiler.setBounds(226, 154, 86, 20);
		panel.add(textFieldFechaAlquiler);
		
		textFieldFechaDevolucion = new JTextField();
		textFieldFechaDevolucion.setEditable(false);
		textFieldFechaDevolucion.setColumns(10);
		textFieldFechaDevolucion.setBounds(352, 154, 86, 20);
		panel.add(textFieldFechaDevolucion);
		
		JLabel lblFechaAlquiler = new JLabel("Fecha alquiler");
		lblFechaAlquiler.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaAlquiler.setBounds(225, 127, 86, 14);
		panel.add(lblFechaAlquiler);
		
		JLabel lblFechaDevolucin = new JLabel("Fecha devolución");
		lblFechaDevolucin.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaDevolucin.setBounds(344, 129, 100, 14);
		panel.add(lblFechaDevolucin);
		
		btnDevolver = new JButton("Devolver");
		btnDevolver.setBackground(Color.WHITE);
		btnDevolver.setBounds(225, 202, 212, 23);
		panel.add(btnDevolver);
		
		modelAlquileres = new DefaultListModel<Alquiler>();
		Alquiler alquileres[] = this.controlAlquiler.getAlquileres(socio);
		
		if(alquileres != null)
			for(int i = 0; i < alquileres.length; i++)
				if(alquileres[i] != null)
					modelAlquileres.addElement(alquileres[i]);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 36, 174, 189);
		panel.add(scrollPane);
		
		listAlquileres = new JList<Alquiler>(modelAlquileres);
		scrollPane.setViewportView(listAlquileres);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(225, 236, 212, 23);
		panel.add(btnAtras);
		btnAtras.setBackground(Color.WHITE);

		initEventos();
	}

	/**
	 * Inicializar los eventos de los componentes.
	 */
	private void initEventos() {
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.onCambiarAPanelCliente(socio, 1);
			}
		});
		
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{	
					if(!listAlquileres.isSelectionEmpty())
					{
						int n = listAlquileres.getSelectedIndex();
					
						Alquiler alquileres[] = controlAlquiler.getAlquileres(socio);
					
						try
						{
							controlAlquiler.devolverAlquilado(alquileres, n);
							JOptionPane.showMessageDialog(btnDevolver, "Alquiler devuelto");
							
							modelAlquileres = new DefaultListModel<Alquiler>();
							alquileres = controlAlquiler.getAlquileres(socio);
							
							for(int i = 0; i < alquileres.length; i++)
								if(alquileres[i] != null)
									modelAlquileres.addElement(alquileres[i]);
							
							listAlquileres.setModel(modelAlquileres);
						}
						catch (ErrorAlquiler e1) 
						{
							JOptionPane.showMessageDialog(btnDevolver, e1.getMessage());
						}
					}
				}
				catch(ArrayIndexOutOfBoundsException e2)
				{
				}
				/*catch(ExcepcionPenalizacion e3)
				{
					JOptionPane.showConfirmDialog(btnDevolver, "Penalización de " + a + " días.", "Info", JOptionPane.OK_OPTION);
				}*/
			}
		});
		
		listAlquileres.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if(!listAlquileres.isSelectionEmpty())
				{
					Alquiler alquiler = modelAlquileres.get(listAlquileres.getSelectedIndex());
					Producto producto = alquiler.getProductoAlquiler();
					
					textFieldNombreProd.setText(producto.getNombre());
					textFieldIdProd.setText(Integer.toString(producto.getId()));
					textFieldFechaAlquiler.setText(alquiler.getFechaAlquiler().toString());
					textFieldFechaDevolucion.setText(alquiler.getFechaDevolucion().toString());
				}
			}
		});
	}
}