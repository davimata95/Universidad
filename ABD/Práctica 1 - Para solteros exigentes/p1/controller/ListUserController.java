package abd.p1.controller;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import abd.p1.bd.DBUsuario;
import abd.p1.model.Usuario;
import abd.p1.view.EditarPerfilDialog;
import abd.p1.view.ListUserPanel;
import abd.p1.view.UsuarioCellRenderer;

public class ListUserController {

	private DBUsuario DB;
	private Usuario usuario;
	private final DefaultListModel<Usuario> modelo = new DefaultListModel<>();
	
	// Constructor sin parámetros
	public ListUserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Constructor con parámetros
	public ListUserController(DBUsuario DB, Usuario usuario) {
		this.DB = DB;
		this.usuario = usuario;
	}
	
	// Métodos usados en la clase 'ListUserPanel'
	public void obtenerListaUsuarios(ListUserPanel parent, String cadena) {
		List<Usuario> usuarios = DB.obtenerListaUsuarios(usuario, cadena);
		
		modelo.clear();
		if (usuarios != null) {
			for (Usuario u : usuarios) {
				modelo.addElement(u);
			}
			parent.setModel(modelo);
			parent.setCellRenderer(new UsuarioCellRenderer());
		}
	}
	
	public void obtenerListaUsuariosPorUbicacion(ListUserPanel parent, String cadena) {
		List<Usuario> usuarios = DB.obtenerListaUsuariosPorUbicacion(usuario, cadena);
		
		modelo.clear();
		if (usuarios != null) {
			for (Usuario u : usuarios) {
				modelo.addElement(u);
			}
			parent.setModel(modelo);
			parent.setCellRenderer(new UsuarioCellRenderer());
		}
	}

	public void modificarPerfil() {
		EditarPerfilController controller2 = new EditarPerfilController(DB, usuario);
		EditarPerfilDialog ep = new EditarPerfilDialog(controller2, usuario, true, 0, 0);
		ep.setVisible(true);
	}

	public void verPerfil(Usuario usuarioSeleccionado) {
		if (usuarioSeleccionado != null) {
			EditarPerfilController controller2 = new EditarPerfilController(DB, usuarioSeleccionado);
			EditarPerfilDialog ep = new EditarPerfilDialog(controller2, usuarioSeleccionado, false, usuario.getLatitud(), usuario.getLongitud());
			ep.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Debes seleccionar un usuario de la lista para ver su perfil.", "Ningún usuario ha sido seleccionado.", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
