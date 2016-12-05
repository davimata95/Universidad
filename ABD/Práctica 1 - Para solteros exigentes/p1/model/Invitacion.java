package abd.p1.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Invitacion extends Mensaje {
	
	@ManyToOne (cascade = CascadeType.ALL)
    private Pregunta pregunta;

	// Constructor sin parámetros
	public Invitacion() {
		
	}

	// Constructor con parámetros
	public Invitacion(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	// Getters and setters
	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}
     
}