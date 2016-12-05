package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import objetosTransfer.TipoUsuario;
import controladores.ControladorAlmacen;

/**
 * @author Sorin.
 */
public class PanelTransacciones extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JList<String> listTransacciones;
	private DefaultListModel<String> modelListaTransacciones;
	private JButton btnAtras;
	
	private Principal principal;
	@SuppressWarnings("unused")
	private ControladorAlmacen controlAlmacen;
	private TipoUsuario tipoUsuario; 

	/**
	 * @param p - Panel Principal.
	 * @param cAlmacen - Controlador Almacen.
	 * @param tUsuario - Tipo de usuario conectado.
	 */
	public PanelTransacciones(Principal p, ControladorAlmacen cAlmacen, TipoUsuario tUsuario) 
	{
		this.principal = p;
		this.controlAlmacen = cAlmacen;
		this.tipoUsuario = tUsuario;
		setLayout(null);
		
		JLabel lblTransacciones = new JLabel("Transacciones");
		lblTransacciones.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTransacciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransacciones.setBounds(220, 43, 122, 27);
		add(lblTransacciones);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 96, 483, 216);
		add(scrollPane);
		
		modelListaTransacciones = new DefaultListModel<String>();
		String[] transacciones = cAlmacen.verUltimasTransacciones();
		
		for(String s: transacciones)
			if(s != null)
				modelListaTransacciones.addElement(s);
		
		listTransacciones = new JList<String>(modelListaTransacciones);
		scrollPane.setViewportView(listTransacciones);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(220, 328, 122, 23);
		add(btnAtras);
		
		initEventos();
	}

	/**
	 * Inicializar los eventos de los componentes.
	 */
	private void initEventos()
	{
		btnAtras.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				principal.onCambiarAPanelAlmacen(tipoUsuario);
			}
		});
	}
}