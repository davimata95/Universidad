package abd.p1.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Mensaje {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int idM;
	@ManyToOne(cascade = CascadeType.ALL)
    private Usuario usuarioEmisor;
	@ManyToOne(cascade = CascadeType.ALL)
    private Usuario usuarioReceptor;
	@Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
	@Column(nullable = false)
    private boolean leido;
	
	// Constructor sin parámetros
	public Mensaje() {

	}

	// Constructor con parámetros
	public Mensaje(int idM, Usuario usuarioEmisor, Usuario usuarioReceptor, Date fecha, boolean leido) {
		this.idM = idM;
		this.usuarioEmisor = usuarioEmisor;
		this.usuarioReceptor = usuarioReceptor;
		this.fecha = fecha;
		this.leido = leido;
	}
	
	// Getters and setters
	public int getIdM() {
		return idM;
	}

	public void setIdM(int idM) {
		this.idM = idM;
	}

	public Usuario getUsuarioEmisor() {
		return usuarioEmisor;
	}

	public void setUsuarioEmisor(Usuario usuarioEmisor) {
		this.usuarioEmisor = usuarioEmisor;
	}

	public Usuario getUsuarioReceptor() {
		return usuarioReceptor;
	}

	public void setUsuarioReceptor(Usuario usuarioReceptor) {
		this.usuarioReceptor = usuarioReceptor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}
 
}