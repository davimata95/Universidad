package p1admin.model;

public class Opcion {
	private Pregunta preguntaMadre;
	private Integer id;
	private int numeroOrden;
	private String texto;
	
	public Opcion() {
		
	}

	// Hemos creado un constructor con parámetros para la clase "OpcionMapper".
	public Opcion(Integer idP, Integer idR, int numeroOrden, String texto) {
		this.preguntaMadre = new Pregunta();
		preguntaMadre.setId(idP);
		this.id = idR;
		this.numeroOrden = numeroOrden;
		this.texto = texto;
	}
	
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
