package p1admin.model;

import java.sql.Timestamp;

public class Mensaje {
    
    private int idM;
    private String correoEmisor;
    private String correoReceptor;
    private Timestamp fecha;
    private boolean leido;
    
	public int getIdM() {
		return idM;
	}
	public void setIdM(int idM) {
		this.idM = idM;
	}
	public String getCorreoEmisor() {
		return correoEmisor;
	}
	public void setCorreoEmisor(String correoEmisor) {
		this.correoEmisor = correoEmisor;
	}
	public String getCorreoReceptor() {
		return correoReceptor;
	}
	public void setCorreoReceptor(String correoReceptor) {
		this.correoReceptor = correoReceptor;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public boolean isLeido() {
		return leido;
	}
	public void setLeido(boolean leido) {
		this.leido = leido;
	}
 
}