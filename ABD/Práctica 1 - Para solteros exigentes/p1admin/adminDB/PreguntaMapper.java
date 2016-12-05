package p1admin.adminDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import p1admin.model.Opcion;
import p1admin.model.Pregunta;

public class PreguntaMapper extends AbstractMapper<Pregunta, Integer> {

	public PreguntaMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return "preguntas";
	}

	@Override
	protected String[] getKeyColumnNames() {
		return new String[] {"IdP"};
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] {
				"IdP", "Enunciado"
		};
	}

	@Override
	protected Pregunta buildObjectFromResultSet(ResultSet rs) throws SQLException {
		Integer id = rs.getInt("IdP");
		String enunciado = rs.getString("Enunciado");
		
		return new Pregunta(id, enunciado, null);
	}

	@Override
	protected Object[] decomposeKey(Integer key) {
		return new Object[] { key };
	}

	@Override
	protected Pregunta buildObject(Object[] o) {
		return new Pregunta((Integer)o[0], (String)o[1], null);
	}

	@Override
	protected Object[] decomposeObject(Pregunta object) {
		return new Object[] {
				object.getId(), object.getEnunciado()
		};
	}

	@Override
	protected Integer getKey(Pregunta object) {
		return object.getId();
	}

	@Override
	protected void setKey(Pregunta object, Integer key) {
		object.setId(key);
	}

	@Override
	protected void actualizarOpciones(Pregunta object) {
		// TODO Auto-generated method stub
	}
	
	protected List<Pregunta> obtenerListaPreguntas(String text) {
		List<Pregunta> preguntas = new ArrayList<Pregunta>();
		List<Opcion> opciones = new ArrayList<Opcion>();
		String sql = "SELECT p.idP, p.enunciado, r.idR, r.numeroOrden, r.texto "
				+ "FROM preguntas p LEFT JOIN respuestas r "
				+ "ON p.idP = r.idP "
				+ "WHERE p.enunciado LIKE '%" + text + "%' "
				+ "ORDER BY p.idP, r.numeroOrden";
		Integer ultimoId = -1;
		Pregunta pregunta = null;
		
		try (Connection con = ds.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Integer idP = rs.getInt("IdP");
				boolean preguntaNueva = false;
				if (!ultimoId.equals(idP)) {
					opciones = new ArrayList<Opcion>();
					preguntaNueva = true;
				}
				ultimoId = idP;
				String enunciado = rs.getString("Enunciado");
				Integer idR = rs.getInt("IdR");
				int numeroOrden = -1;
				String texto = "-1";
				if (idR != 0) {
					/*int*/ numeroOrden = rs.getInt("NumeroOrden");
					/*String*/ texto = rs.getString("Texto");
					Opcion opcion = new Opcion(idP, idR, numeroOrden, texto);
					opciones.add(opcion);
					if (preguntaNueva) {
						pregunta = new Pregunta(idP, enunciado, opciones);
						preguntas.add(pregunta);
					}
					else {
						preguntas.remove(pregunta);
						preguntas.add(pregunta);
					}
				} else {
					pregunta = new Pregunta();
					pregunta.setId(idP);
					pregunta.setEnunciado(enunciado);
					preguntas.add(pregunta);
				}
				//System.out.println("IdP: " + idP + " Enunciado: " + enunciado + " IdR " + idR + " NumeroOrden " + numeroOrden + " Texto: " + texto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return preguntas;
	}
	
}
