package abd.p1.bd;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import abd.p1.model.Contesta;
import abd.p1.model.Genero;
import abd.p1.model.Interes;
import abd.p1.model.Pregunta;
import abd.p1.model.Usuario;

public class DBUsuario {
	
	private SessionFactory sf;
	
	public DBUsuario(SessionFactory sf) {
		this.sf = sf;
	}
	
	// Se llama al pulsar el botón 'Aceptar' y se comprueba el correo y la contraseña
	public Usuario obtenerUsuarioLogin(String correo, String password) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		
		Query query = session.createQuery("FROM Usuario AS u WHERE u.correo = :correo AND u.password = :password");
		query.setString("correo", correo);
		query.setString("password", password);
		Usuario usuario = (Usuario) query.uniqueResult();
		
		tr.commit();
		session.close();
		
		return usuario;
	}
	
	// Se llama al pulsar el botón 'Nuevo usuario' y se comprueba el correo
	public Usuario obtenerCorreoLogin(String correo) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		
		Query query = session.createQuery("FROM Usuario AS u WHERE u.correo = :correo");
		query.setString("correo", correo);
		Usuario usuario = (Usuario) query.uniqueResult();
		
		tr.commit();
		session.close();
		
		return usuario;
	}
	
	// Se llama al pulsar el botón 'Guardar cambios'
	public void almacenarUsuario(Usuario usuario) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		
		if (obtenerCorreoLogin(usuario.getCorreo()) == null) {
			session.save(usuario);
		} else {
			session.update(usuario);
		}
		
		tr.commit();
		session.close();
	}
	
	// Devuelve la lista de usuarios de la base de datos
	public List<Usuario> obtenerListaUsuarios(Usuario usuario, String cadena) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		
		Query query;
		if (usuario.getInteres().equals(Interes.hombres)) { // Si el usuario busca hombres
			query = session.createQuery("FROM Usuario AS u WHERE u.genero = :genero AND u.nombre LIKE :text AND u.nombre != :nombre AND (u.interes = :miGenero OR u.interes = :ambos)");
			query.setString("genero", "masculino");
			query.setString("text", "%" + cadena + "%");
			query.setString("nombre", usuario.getNombre());
			if (usuario.getGenero().equals(Genero.masculino)) {
				query.setString("miGenero", "hombres");
			} else {
				query.setString("miGenero", "mujeres");
			}
			query.setString("ambos", "ambos");
		} else if (usuario.getInteres().equals(Interes.mujeres)) { // Si el usuario busca mujeres
			query = session.createQuery("FROM Usuario AS u WHERE u.genero = :genero AND u.nombre LIKE :text AND u.nombre != :nombre AND (u.interes = :miGenero OR u.interes = :ambos)");
			query.setString("genero", "femenino");
			query.setString("text", "%" + cadena + "%");
			query.setString("nombre", usuario.getNombre());
			if (usuario.getGenero().equals(Genero.masculino)) {
				query.setString("miGenero", "hombres");
			} else {
				query.setString("miGenero", "mujeres");
			}
			query.setString("ambos", "ambos");
		} else { // Si el usuario busca ambos
			query = session.createQuery("FROM Usuario AS u WHERE u.nombre LIKE :text AND u.nombre != :nombre AND (u.interes = :miGenero OR u.interes = :ambos)");
			query.setString("text", "%" + cadena + "%");
			query.setString("nombre", usuario.getNombre());
			if (usuario.getGenero().equals(Genero.masculino)) {
				query.setString("miGenero", "hombres");
			} else {
				query.setString("miGenero", "mujeres");
			}
			query.setString("ambos", "ambos");
		}
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = (List<Usuario>) query.list();
		
		tr.commit();
		session.close();
		
		return usuarios;
	}
	
	// Devuelve la lista de usuarios de la base de datos ordenada por ubicación
	public List<Usuario> obtenerListaUsuariosPorUbicacion(Usuario usuario, String cadena) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		
		String d = "POW(u.latitud - " + usuario.getLatitud() + ", 2) + POW(u.longitud - " + usuario.getLongitud() + ", 2)";
		
		Query query;
		if (usuario.getInteres().equals(Interes.hombres)) { // Si el usuario busca hombres
			query = session.createQuery("FROM Usuario AS u WHERE u.genero = :genero AND u.nombre LIKE :text AND u.nombre != :nombre AND (u.interes = :miGenero OR u.interes = :ambos) ORDER BY " + d);
			query.setString("genero", "masculino");
			query.setString("text", "%" + cadena + "%");
			query.setString("nombre", usuario.getNombre());
			if (usuario.getGenero().equals(Genero.masculino)) {
				query.setString("miGenero", "hombres");
			} else {
				query.setString("miGenero", "mujeres");
			}
			query.setString("ambos", "ambos");
			query.setMaxResults(20);
		} else if (usuario.getInteres().equals(Interes.mujeres)) { // Si el usuario busca mujeres
			query = session.createQuery("FROM Usuario AS u WHERE u.genero = :genero AND u.nombre LIKE :text AND u.nombre != :nombre AND (u.interes = :miGenero OR u.interes = :ambos) ORDER BY " + d);
			query.setString("genero", "femenino");
			query.setString("text", "%" + cadena + "%");
			query.setString("nombre", usuario.getNombre());
			if (usuario.getGenero().equals(Genero.masculino)) {
				query.setString("miGenero", "hombres");
			} else {
				query.setString("miGenero", "mujeres");
			}
			query.setString("ambos", "ambos");
			query.setMaxResults(20);
		} else { // Si el usuario busca ambos
			query = session.createQuery("FROM Usuario AS u WHERE u.nombre LIKE :text AND u.nombre != :nombre AND (u.interes = :miGenero OR u.interes = :ambos) ORDER BY " + d);
			query.setString("text", "%" + cadena + "%");
			query.setString("nombre", usuario.getNombre());
			if (usuario.getGenero().equals(Genero.masculino)) {
				query.setString("miGenero", "hombres");
			} else {
				query.setString("miGenero", "mujeres");
			}
			query.setString("ambos", "ambos");
			// Fijamos el número máximo de resultados a 20
			query.setMaxResults(20);
		}
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = (List<Usuario>) query.list();
		
		tr.commit();
		session.close();
		
		return usuarios;
	}
	
	public List<Pregunta> obtenerListaPreguntas() {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		
		Query query = session.createQuery("SELECT p"
				+ " FROM Contesta AS c RIGHT JOIN c.preguntaMadre AS p"
				+ " GROUP BY c.preguntaMadre"
				+ " ORDER BY AVG(c.relevancia) DESC");
		
		@SuppressWarnings("unchecked")
		List<Pregunta> preguntas = (List<Pregunta>) query.list();
		
		tr.commit();
		session.close();
		
		return preguntas;
	}

	public void guardarRespuesta(Contesta contestacion) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		
		session.save(contestacion);
		
		tr.commit();
		session.close();
	}
	
	public void actualizarRespuesta(Contesta contestacion, Contesta contestacionAnterior) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		
		session.delete(contestacionAnterior);
		session.save(contestacion);
		
		tr.commit();
		session.close();
	}

	public Contesta comprobarRespuesta(int id, String correo) {
		Session session = sf.openSession();
		
		Query query = session.createQuery("SELECT c FROM Contesta AS c JOIN c.preguntaMadre AS p WHERE p.id = :id AND c.usuario = :correo").setMaxResults(1);
		query.setInteger("id", id);
		query.setString("correo", correo);
		Contesta contestacion = (Contesta) query.uniqueResult();
		
		session.close();
		
		return contestacion;
	}
	
	public Pregunta obtenerPreguntaAleatoria() {
		Session session = sf.openSession();
		
		Query query = session.createQuery("FROM Pregunta ORDER BY RAND()").setMaxResults(1);
		Pregunta pregunta = (Pregunta) query.uniqueResult();
		
		session.close();
		
		return pregunta;
	}
	
}
