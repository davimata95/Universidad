package abd.p1.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClavesContesta implements Serializable {
	
	//@ManyToOne(cascade = CascadeType.ALL)
	private Usuario usuario;
	//@ManyToOne//(cascade = CascadeType.ALL)
	private Pregunta preguntaMadre;
	//@ManyToOne//(cascade = CascadeType.ALL)
	private Opcion opcion;
	
	// Constructor sin parámetros
	public ClavesContesta() {

	}

	// Constructor con parámetros
	public ClavesContesta(Usuario usuario, Pregunta preguntaMadre, Opcion opcion) {
		super();
		this.usuario = usuario;
		//this.preguntaMadre = preguntaMadre;
		this.opcion = opcion;
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
	
}