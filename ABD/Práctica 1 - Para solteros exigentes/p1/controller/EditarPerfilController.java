package abd.p1.controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import abd.p1.bd.DBUsuario;
import abd.p1.model.Genero;
import abd.p1.model.Interes;
import abd.p1.model.Usuario;
import abd.p1.view.EditarPerfilDialog;
import abd.p1.view.PerfilPanel;
import abd.p1.view.UserPanel;

public class EditarPerfilController {
	
	private DBUsuario DB;
	private Usuario usuario;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private DefaultListModel<String> aficionesModel = new DefaultListModel();
	
	// Constructor sin parámetros
	public EditarPerfilController() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Constructor con parámetros
	public EditarPerfilController(DBUsuario DB, Usuario usuario) {
		this.DB = DB;
		this.usuario = usuario;
	}
	
	public void setModel(DefaultListModel<String> aficionesModel) {
		this.aficionesModel = aficionesModel;
	}
	
	// Métodos usados en la clase 'UserPanel'
	public void cambiarNombre(UserPanel parent) {
        String nombre = JOptionPane.showInputDialog("Introduce tu nombre:");
        
        if (nombre != null && !nombre.trim().isEmpty()) {
        	parent.setNombre(nombre);
        	// Asignamos al usuario el nombre
        	this.usuario.setNombre(nombre);
        } else {
        	JOptionPane.showMessageDialog(null, "Has introducido un nombre vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void cambiarFechaNacimiento(UserPanel parent) {
		JDateChooser jdc = new JDateChooser();
		String mensaje = "Selecciona tu fecha de nacimiento:\n";
		Object[] parametros = {mensaje, jdc};
		JOptionPane.showConfirmDialog(null, parametros, "Fecha de nacimiento", JOptionPane.PLAIN_MESSAGE);
		Date fechaNacimiento = jdc.getDate();
		
		if (fechaNacimiento != null) {
			int edad = fecha2Edad(fechaNacimiento);
			if (edad < 0) {
				JOptionPane.showMessageDialog(null, "No puedes haber nacido en el futuro.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				parent.setEdad(edad);
				// Asignamos al usuario la fecha de nacimiento
				this.usuario.setNacimiento(fechaNacimiento);
			}
		}
	}
	
	public int fecha2Edad (Date fechaNacimiento) {
		Date fechaHoy = new Date(System.currentTimeMillis());
		Calendar calendarHoy = Calendar.getInstance();
		calendarHoy.setTime(fechaHoy);
		Calendar calendarNacimiento = Calendar.getInstance();
		calendarNacimiento.setTime(fechaNacimiento);
		
		// Calculamos la diferencia entre la fecha de hoy y la fecha de nacimiento
		int day = calendarHoy.get(Calendar.DATE) - calendarNacimiento.get(Calendar.DATE);
		int month = calendarHoy.get(Calendar.MONTH) - calendarNacimiento.get(Calendar.MONTH);
		int year = calendarHoy.get(Calendar.YEAR) - calendarNacimiento.get(Calendar.YEAR);

		if ((month == 0 && day < 0) || month < 0)
			year--;
		
		return year;
	}

	// Métodos del PerfilPanel
	public void cambiarAvatar(PerfilPanel parent) {
		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter extension = new FileNameExtensionFilter("JPG, PNG & GIF", "JPG", "PNG", "GIF");
		jfc.setFileFilter(extension);
		int r = jfc.showOpenDialog(parent);
		
		if (r == JFileChooser.APPROVE_OPTION) {
			File archivo = jfc.getSelectedFile();
			ImageIcon icon = new ImageIcon(archivo.getPath());
			System.out.println(icon);
			// Transformamos la imagen (de ImageIcon a byte[])
			byte[] array = toByteArray(icon);
			// Lo dibujamos en la ventana
			parent.setIcon(new ImageIcon(array));
			// Asignamos al usuario el avatar
			usuario.setAvatar(array);
		}
	}
	
	private byte[] toByteArray(ImageIcon icon) {
		BufferedImage bi = new BufferedImage(
				icon.getIconWidth(), icon.getIconHeight(), 
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		icon.paintIcon(null, g, 0, 0);
		g.dispose();
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "png", byteStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] array = byteStream.toByteArray();
		System.out.println(array);
		
		return array;
	}
	
	public void aniadirAficion(PerfilPanel parent) {
		String aficion = JOptionPane.showInputDialog(parent, "Introduce una afición: ", "Añadir afición", JOptionPane.PLAIN_MESSAGE);
		
		if (aficion != null && !aficion.trim().isEmpty()) {
        	aficionesModel.addElement(aficion);
        	parent.setModel(aficionesModel);
        	// Asignamos al usuario la aficion
    		this.usuario.addAficion(aficion);
        }
	}
	
	public void eliminarAficion(PerfilPanel parent, int indexAficion) { //DefaultListModel aficionesModel
		if (indexAficion != -1) {
			aficionesModel.removeElementAt(indexAficion);
			parent.setModel(aficionesModel);
			// Asignamos al usuario la aficion
			this.usuario.removeAficion(indexAficion);
		} else {
			JOptionPane.showMessageDialog(null, "Por favor, selecciona una afición y luego pulsa el botón 'Eliminar aficion'.", "Ninguna afición seleccionada", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void editarAficion(PerfilPanel parent, int indexAficion) {
		if (indexAficion != -1) {
			String aficion = JOptionPane.showInputDialog(parent, "Introduce una afición: ", "Editar afición", JOptionPane.PLAIN_MESSAGE);
			
			if (aficion != null && !aficion.trim().isEmpty()) {
				aficionesModel.removeElementAt(indexAficion);
	        	aficionesModel.addElement(aficion);
	        	parent.setModel(aficionesModel);
	        	// Asignamos al usuario la aficion
	        	this.usuario.removeAficion(indexAficion);
	        	this.usuario.addAficion(aficion);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Por favor, selecciona una afición y luego pulsa el botón 'Editar aficion'.", "Ninguna afición seleccionada", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void cambiarSexo(PerfilPanel parent) {
		Genero sexo = (Genero) JOptionPane.showInputDialog(
				   parent,
				   "Selecciona tu sexo:",
				   "Sexo",
				   JOptionPane.QUESTION_MESSAGE,
				   null,  // Null para icono por defecto
				   Genero.values(), 
				   Genero.masculino);
		
		if (sexo != null) {
			parent.setSexo(sexo.toString());
			// Asignamos al usuario el sexo
			this.usuario.setGenero(sexo);
		}
	}
	
	public void cambiarPreferencia(PerfilPanel parent) {
		Interes preferencia = (Interes) JOptionPane.showInputDialog(
				   parent,
				   "Selecciona tu preferencia:",
				   "Preferencia",
				   JOptionPane.QUESTION_MESSAGE,
				   null,  // Null para icono defecto
				   Interes.values(), 
				   Interes.mujeres);
		
		if (preferencia != null) {
			parent.setPreferencia(preferencia.toString());
			// Asignamos al usuario la preferencia
			this.usuario.setInteres(preferencia);
		}
	}
	
	// Métodos de EditarPerfilDialog
	public void cambiarContrasenia(EditarPerfilDialog parent) {
		String contrasenia = JOptionPane.showInputDialog("Introduce tu password:");
        
        if (contrasenia != null && !contrasenia.trim().isEmpty()) {
        	parent.setContrasenia(contrasenia);
        	// Asignamos al usuario la contraseña
        	usuario.setPassword(contrasenia);
        } else {
        	JOptionPane.showMessageDialog(null, "Has introducido una contraseña vacía.", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void cancelar(EditarPerfilDialog parent) {
		parent.setVisible(false);
	}

	public void obtenerDescipcion(PerfilPanel parent) {
		usuario.setDescripcion(parent.getDescripcion());
	}
	
	public void guardarCambios(EditarPerfilDialog parent) {
		if (usuario.getNombre() != null && usuario.getGenero() != null && usuario.getInteres() != null) {
			usuario.setLatitud(calcularLatitudAletoria());
			usuario.setLongitud(calcularLongitudAleatoria());
			DB.almacenarUsuario(this.usuario);
			parent.setVisible(false);
			/*PrincipalFrame principalFrame = new PrincipalFrame();
			principalFrame.setVisible(true);*/
		} else {
			JOptionPane.showMessageDialog(null, "Es obligatorio que introduzcas al menos el nombre, el género y la preferencia.", "Revisa los campos", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/* FUNCIONES DE LA OPCIONAL 5 */
	public double calcularLatitudAletoria() {
		double desde = 40;
		double hasta = 41.2;
		double latitud = 0;
		
		latitud = (double)(Math.random() * (hasta - desde) + desde);
		
		return latitud;
	}
	
	public double calcularLongitudAleatoria() {
		double desde = -3;
		double hasta = -4.5;
		double longitud = 0;
		
		longitud = (double)(Math.random() * (hasta - desde) + desde);
		
		return longitud;
	}
		
	public double haversine(double m1, double n1, double m2, double n2) {	
		double R = 6371000; // Radio de la Tierra en metros
		double diferenciaM = Math.toRadians(m1 - m2);
		double diferenciaN = Math.toRadians(n1 - n2);
		
		m1 = Math.toRadians(m1);
		m2 = Math.toRadians(m2);
		
		double a = Math.pow(Math.sin(diferenciaM / 2), 2) + Math.cos(m1) * Math.cos(m2) * Math.pow(Math.sin(diferenciaN / 2), 2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		double d = R * c;
		
		return d;
	}
	
	public void determinarDistancia(EditarPerfilDialog parent) {
		int d = (int) haversine(parent.getLatitudOrigen(), parent.getLongitudOrigen(), usuario.getLatitud(), usuario.getLongitud());
		parent.setDistancia(d);
	}
	
}
