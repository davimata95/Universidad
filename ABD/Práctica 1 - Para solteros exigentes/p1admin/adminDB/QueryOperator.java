package p1admin.adminDB;

public enum QueryOperator {
	LEQ("<="), LT("<"), GT(">"), GEQ(">="), EQ("="), NEQ("<>"), LIKE("LIKE");
	
	private String SQLrep;
	
	private QueryOperator(String rep) {
		this.SQLrep = rep;
	}
	
	@Override
	public String toString() {
		return SQLrep;
	}
}