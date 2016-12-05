package p1admin.model;

public class SolicitudAmistad extends Mensaje {
	 
    private int idM;
    private boolean aceptada;
     
    public int getIdM() {
        return idM;
    }
    public void setIdM(int idM) {
        this.idM = idM;
    }
    public boolean isAceptada() {
        return aceptada;
    }
    public void setAceptada(boolean aceptada) {
        this.aceptada = aceptada;
    }
     
}