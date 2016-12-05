package excepciones;

public class FalloConexionBD extends Exception{
	private static final long serialVersionUID = 1L;
	
	public FalloConexionBD(){
		super("Error al conectarse a la Base de Datos");
	}
}
