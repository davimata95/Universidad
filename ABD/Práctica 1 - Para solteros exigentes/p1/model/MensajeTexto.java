package abd.p1.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MensajeTexto extends Mensaje {

	@Column(nullable = false, length = 250)
    private String contenido;

	// Constructor sin parámetros
	public MensajeTexto() {

	}

	// Constructor con parámetros
	public MensajeTexto(String contenido) {
		this.contenido = contenido;
	}

	// Getters and setters
	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
    
}