package p1admin.adminDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.mysql.jdbc.Statement;

public class DataAccessor {
	private DataSource ds;

	public DataAccessor(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Inserta una fila en una tabla de la BD.
	 * 
	 * @param tableName Nombre de la tabla en la que insertar la fila.
	 * @param fields Nombres de las columnas a rellenar de la nueva fila.
	 * @param values Valores a insertar en la nueva fila. Este array ha de tener
	 *       tantos elementos como <code>fields</code>
	 * @return <code>true</code> si la inserción tuvo éxito, o <code>false</code>
	 *       en caso contrario.
	 */
	public Integer insertRow(String tableName, String[] fields, Object[] values) {
		Integer id = -1;
		String sql = generateInsertStatement(tableName, fields);
		try (Connection con = ds.getConnection(); PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			for (int i = 0; i < values.length; i++) {
				pst.setObject(i + 1, values[i]);
			}
			//int numRows = pst.executeUpdate();
			//return (numRows == 1);
			pst.executeUpdate();
			ResultSet lastKey = pst.getGeneratedKeys();
			if (lastKey.next()) {
				id = lastKey.getInt(1);
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public String generateInsertStatement(String tableName, String[] fields) {
		String fieldList = String.join(", ", fields);
		String[] marks = new String[fields.length];
		Arrays.fill(marks, "?");
		String markList = String.join(", ", marks);
		return "INSERT INTO " + tableName + " (" + fieldList + ") VALUES (" + markList + ")";
	}
	
	// Modifica una fila en una tabla de la BD.
	public boolean updateRow(String tableName, String[] keyColumnNames, Object[] keyValues, String[] columnNames, Object[] newValues) {
		String sql = generateUpdateStatements(tableName, columnNames, keyColumnNames);
		try (Connection con = ds.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
			int j = 0;
			for (int i = 0; i < newValues.length; i++) {
				pst.setObject(i + 1, newValues[i]);
				j = i + 1;
			}
			for (int i = 0; i < keyValues.length; i++) {
				pst.setObject(j + 1, keyValues[i]);
				j++;
			}
			int numRows = pst.executeUpdate();
			return (numRows == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String generateUpdateStatements(String tableName, String[] marks, String[] conditions) {
		for (int i = 0; i < marks.length; i++) {
			marks[i] = marks[i] + " = ?";
		}
		for (int i = 0; i < conditions.length; i++) {
			conditions[i] = conditions[i] + " = ?";
		}
		String markList = String.join(", ", marks);
		String conditionList = String.join(" AND ", conditions);
				
		return "UPDATE " + tableName + " SET " + markList + " WHERE " + conditionList;
	}
	
	// Borra una fila en una tabla de la BD.
	public boolean deleteRow(String tableName, String[] keyColumnNames, Object[] keyValues) {
		String sql = generateDeleteStatements(tableName, keyColumnNames);
		try (Connection con = ds.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
			for (int i = 0; i < keyValues.length; i++) {
				pst.setObject(i + 1, keyValues[i]);
			}
			int numRows = pst.executeUpdate();
			return (numRows == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String generateDeleteStatements(String tableName, String[] conditions) {
		for (int i = 0; i < conditions.length; i++) {
			conditions[i] = conditions[i] + " = ?";
		}
		String conditionList = String.join(" AND ", conditions);
		
		return "DELETE FROM " + tableName + " WHERE " + conditionList;
	}

	/**
	 * Realiza una consulta en la BD a partir de una conjunción de condiciones simples
	 * 
	 * @param tableName Nombre de la tabla a consultar
	 * @param columns Nombre de las columnas a devolver
	 * @param conditions Condiciones sobre los resultados devueltos
	 * @return Lista con las filas devueltas por la consulta. Las columnas de cada fila
	 * se representan mediante un array de Object.
	 */
	public List<Object[]> query(String tableName, 
			String[] columns, 
			QueryCondition[] conditions) {
		List<Object[]> result = new LinkedList<>();
		
		String sql = generateSelectStatement(tableName, columns, conditions);
		
		try(Connection con = ds.getConnection();
		    PreparedStatement st = con.prepareStatement(sql)) {
			
			for (int i = 0; i < conditions.length; i++) {
				st.setObject(i + 1, conditions[i].getValue());
			}
			
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Object[] obj = new Object[columns.length];
					for (int i = 0; i < obj.length; i++) {
						obj[i] = rs.getObject(columns[i]);
					}
					
					result.add(obj);		
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(sql);
		
		return result;
	}

	private String generateSelectStatement(String tableName, String[] columns, QueryCondition[] conditions) {
		String[] conditionStrings = new String[conditions.length];
		
		for(int i = 0; i < conditionStrings.length; i++) {
			conditionStrings[i] = conditions[i].getColumnName() + " " +
					conditions[i].getOperator() + " ?";
		}
		
		String sql = "SELECT " + String.join(", ", columns) +
				" FROM " + tableName +
				" WHERE " + String.join(" AND ", conditionStrings);
		return sql;
	}
}
