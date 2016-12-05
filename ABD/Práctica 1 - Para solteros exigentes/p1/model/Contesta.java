package abd.p1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(ClavesContesta.class)
public class Contesta {
	
	@Id
	@ManyToOne
	private Usuario usuario;
	@Id
	@ManyToOne
	private Opcion opcion;
	@Id
	@ManyToOne
	private Pregunta preguntaMadre;
	@Column(nullable = false)
    private int relevancia;

	// Constructor sin parámetros
	public Contesta() {

	}

	// Constructor con parámetros
	public Contesta(Usuario usuario, Pregunta preguntaMadre, Opcion opcion, int relevancia) {
		this.usuario = usuario;
		this.preguntaMadre = preguntaMadre;
		this.opcion = opcion;
		this.relevancia = relevancia;
	}

	// Getters and setters
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Pregunta getPreguntaMadre() {
		return preguntaMadre;
	}

	public void setPreguntaMadre(Pregunta preguntaMadre) {
		this.preguntaMadre = preguntaMadre;
	}

	public Opcion getOpcion() {
		return opcion;
	}

	public void setOpcion(Opcion opcion) {
		this.opcion = opcion;
	}

	public int getRelevancia() {
		return relevancia;
	}

	public void setRelevancia(int relevancia) {
		this.relevancia = relevancia;
	}
	
}