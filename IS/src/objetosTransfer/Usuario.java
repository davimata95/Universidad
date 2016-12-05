package objetosTransfer;

public class Usuario {
	private String nombre;
	private String contrasena;
	private TipoUsuario tipoUser;
	
	public Usuario(String n, String c, TipoUsuario tipo){
		this.contrasena = c;
		this.nombre = n;
		this.tipoUser = tipo;
	}

	public TipoUsuario getTipoUsuario() {
		return this.tipoUser;
	}
	
	public void setTipoUsuario(TipoUsuario tipo) {
		this.tipoUser = tipo;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String n){
		this.nombre = n;
	}
	
	public String getContrasena(){
		return this.contrasena;
	}
	
	public void setContrasena(String c){
		this.contrasena = c;
	}
	public String toString(){
		return this.nombre;
	}
}
