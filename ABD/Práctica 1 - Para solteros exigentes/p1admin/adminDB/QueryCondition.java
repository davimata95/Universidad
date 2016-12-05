package p1admin.adminDB;

public class QueryCondition {
	private String columnName;
	private QueryOperator operator;
	private Object value;
	
	public QueryCondition(String columnName, QueryOperator operator, Object value) {
		this.columnName = columnName;
		this.operator = operator;
		this.value = value;
	}

	public String getColumnName() {
		return columnName;
	}
	
	public QueryOperator getOperator() {
		return operator;
	}
	
	public Object getValue() {
		return value;
	}
	
}