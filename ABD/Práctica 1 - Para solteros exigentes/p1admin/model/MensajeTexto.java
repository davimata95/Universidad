package p1admin.model;

public class MensajeTexto extends Mensaje {
	 
    private int idM;
    private String contenido;
     
    public int getIdM() {
        return idM;
    }
    public void setIdM(int idM) {
        this.idM = idM;
    }
    public String getContenido() {
        return contenido;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
}