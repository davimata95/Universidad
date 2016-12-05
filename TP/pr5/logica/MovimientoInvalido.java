package tp.pr5.logica;

@SuppressWarnings("serial")
public class MovimientoInvalido extends Exception{
	
	//String identificador
	private String mensaje;
	
	
	//Segun la excepcion que sea saltara una u otra, dependiendo de los parametros de entrada 
	public MovimientoInvalido(){
		this.mensaje="MovimientoInvalido";
	}
	public MovimientoInvalido(java.lang.String msg){
		this.mensaje=msg;
	}
	
	public MovimientoInvalido(java.lang.String msg, java.lang.Throwable arg){
		this.mensaje=msg;
	}
	public MovimientoInvalido(java.lang.Throwable arg){
		
	}
	public String getMessage(){
		
		return mensaje;
	}
}