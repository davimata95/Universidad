package p1admin.adminDB;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public abstract class AbstractMapper<T, K> {

	protected DataSource ds;
	protected DataAccessor da;

	/**
	 * Obtiene el nombre de la tabla que maneja el Mapper.
	 * @return Nombre de tabla
	 */
	protected abstract String getTableName();

	/**
	 * Obtiene el nombre de las columnas de la tabla que conforman la clave
	 *
	 * @return Array con los nombres de las columnas clave de la tabla. Si
	 *   la clave es simple, tan sólo contiene un componente.
	 */
	protected abstract String[] getKeyColumnNames();

	/**
	 * Devuelve los nombres de las columnas de la tabla.
	 * 
	 * @return Array con los nombres de las columnas.
	 */
	protected abstract String[] getColumnNames();

	/**
	 * Construye un objeto T consultando un ResultSet
	 * 
	 * @param rs ResultSet a consultar. No debe cerrarse.
	 * @return Objeto de la clase que maneja el Mapper (T)
	 * @throws SQLException
	 */
	protected abstract T buildObjectFromResultSet(ResultSet rs) throws SQLException;
	
	/**
	 * Descompone una clave simple o compuesta en unos componentes.
	 * 
	 * @param key Clave a descomponer
	 * @return Array con las componentes de la clave. Si la clave es simple,
	 * se devolverá un array unitario. El orden en el que se devuelven las
	 * componentes ha de coincidir con el orden dispuesto por el resultado
	 * de getKeyColumnNames()
	 */
	protected abstract Object[] decomposeKey(K key);
	
	/**
	 * Construye un objeto a partir de sus componentes.
	 * 
	 * @param o Componentes del objeto a componer. Las componentes
	 * van ordenadas según las columnas devueltas por getColumnNames().
	 *  
	 * @return Objeto de la clase que maneja el mapper (T)
	 */
	protected abstract T buildObject(Object[] o);
	
	/**
	 * Descompone un objeto a partir de sus componentes.
	 * 
	 * @param object Objeto a descomponer
	 * @return Array con las componentes del objeto. Han de estar en el orden
	 * especificado por el resultado de la función getColumnNames().
	 */
	protected abstract Object[] decomposeObject(T object);
	
	/**
	 * Dado un objeto, devuelve su clave.
	 * 
	 * @param object Objeto a consultar
	 * @return Clave del objeto consultado.
	 */
	protected abstract K getKey(T object);

	//
	// Añade los métodos abstractos que consideres necesarios.
	// 

	// Utilizado para fijar el id auto_increment que se recupera al insertar una pregunta.
	protected abstract void setKey(T object, Integer key);
	
	protected abstract void actualizarOpciones(T object);

	
	public AbstractMapper(DataSource ds) {
		this.ds = ds;
		this.da = new DataAccessor(ds);
	}

	/**
	 * Obtiene un objeto de la BD a partir de su clave.
	 * 
	 * @param id Clave del objeto a buscar
	 * @return El objeto recuperado de la BD, o null si el objeto no se ha encontrado
	 */
	/*public T findById(K id) {
		String[] columnNames = getColumnNames();
		String[] keyColumnNames = getKeyColumnNames();
		
		String[] keyConditions = new String[keyColumnNames.length];
		for (int i = 0; i < keyConditions.length; i++) {
			keyConditions[i] = keyColumnNames[i] + " = ?";
		}
		
		
		String sql = "SELECT "
				+ String.join(", ", columnNames)
				+ " FROM "
				+ getTableName()
				+ " WHERE "
				+ String.join(" AND ", keyConditions);
		try (Connection con = ds.getConnection();
			 PreparedStatement st = con.prepareStatement(sql)) {
			
			Object[] decKey = decomposeKey(id);
			for (int i = 0; i < keyColumnNames.length; i++) {
				st.setObject(i + 1, decKey[i]);
			}
					
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					return buildObjectFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}*/
	
	/*
	 * Realiza la misma función que findById, pero utilizando un Data Accessor
	 */
	/*public T findByIdDataAccessor(K id) {
		String[] columnNames = getKeyColumnNames();
		Object[] keyDec = decomposeKey(id);
		
		QueryCondition[] conditions = new QueryCondition[columnNames.length];
		for (int i = 0; i < conditions.length; i++) {
			conditions[i] = new QueryCondition(columnNames[i],
					QueryOperator.EQ, keyDec[i]);
		}
		
		List<Object[]> rows = da.query(getTableName(), getColumnNames(), conditions);
		if (rows.isEmpty()) {
			return null;
		} else {
			Object[] row = rows.get(0);
			return buildObject(row);
		}
	}*/
	
	// Inserta una fila en una tabla de la BD.
	public boolean insertRow(T object) {
		Integer id = da.insertRow(getTableName(), getColumnNames(), decomposeObject(object));
		setKey(object, id);
		return true;
	}
	
	// Modifica una fila en una tabla de la BD.
	public boolean updateRow(T object) {
		return da.updateRow(getTableName(), getKeyColumnNames(), decomposeKey(getKey(object)), getColumnNames(), decomposeObject(object));
	}
	
	// Borra una fila en una tabla de la BD.
	public boolean deleteRow(T object) {
		return da.deleteRow(getTableName(), getKeyColumnNames(), decomposeKey(getKey(object)));
	}
	
}