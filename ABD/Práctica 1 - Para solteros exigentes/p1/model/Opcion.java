package abd.p1.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Opcion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(cascade = CascadeType.ALL)
	private Pregunta preguntaMadre;
	@Column(nullable = false, length = 11)
	private int numeroOrden;
	@Column(nullable = false, length = 100)
	private String texto;
	
	// Constructor sin parámetros
	public Opcion() {
		
	}

	// Constructor con parámetros
	public Opcion(Integer id, Pregunta preguntaMadre, int numeroOrden, String texto) {
		this.id = id;
		this.preguntaMadre = preguntaMadre;
		this.numeroOrden = numeroOrden;
		this.texto = texto;
	}
	
	// Getters and setters
	public Integer getIdP() {
		return this.preguntaMadre.getId();
	}

	public void setIdP(Integer idP) {
		this.preguntaMadre.setId(idP);
	}

	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Pregunta getPreguntaMadre() {
		return preguntaMadre;
	}

	public void setPreguntaMadre(Pregunta preguntaMadre) {
		this.preguntaMadre = preguntaMadre;
	}

	public int getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(int numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + numeroOrden + ") " + texto;
	}
	
}