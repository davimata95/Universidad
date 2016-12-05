package p1admin.adminDB;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import p1admin.model.Opcion;
import p1admin.model.Pregunta;

public class OpcionMapper extends AbstractMapper<Opcion, Integer> {

	public OpcionMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return "respuestas";
	}

	@Override
	protected String[] getKeyColumnNames() {
		return new String[] { "IdR" };
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] {
				"IdP", "IdR", "NumeroOrden", "Texto"
		};
	}

	@Override
	protected Opcion buildObjectFromResultSet(ResultSet rs) throws SQLException {
		Integer idP = rs.getInt("IdP");
		Integer idR = rs.getInt("IdR");
		int numeroOrden = rs.getInt("NumeroOrden");
		String texto = rs.getString("Texto");
		
		return new Opcion(idP, idR, numeroOrden, texto);
	}

	@Override
	protected Object[] decomposeKey(Integer key) {
		return new Object[] { key };
	}

	@Override
	protected Opcion buildObject(Object[] o) {
		return new Opcion((Integer)o[0], (Integer)o[1], (int)o[2], (String)o[3]);
	}

	@Override
	protected Object[] decomposeObject(Opcion object) {
		return new Object[] {
				object.getIdP(), object.getId(), object.getNumeroOrden(), object.getTexto()
		};
	}

	@Override
	protected Integer getKey(Opcion object) {
		return object.getId();
	}

	@Override
	protected void setKey(Opcion object, Integer key) {
		object.setId(key);
	}

	@Override
	protected void actualizarOpciones(Opcion object) {
		Pregunta p = new Pregunta();
		p.setId(object.getIdP());
		for (int i = object.getNumeroOrden() + 1; i <= p.getNumOpciones(); i++) {
			p.getOpciones().get(i - 1).setNumeroOrden(i - 1);
		}
	}

}
