package fdi.ucm.cuentacuentas;

import java.io.Serializable;

/**
 * Created by Pikup on 03/05/2016.
 */
public class grupousuario implements Serializable {

    private int idGrupo;
    private usuario usu;
    private float debe;
    private float pagado;
    private boolean admin;

    // Constructor sin parámetros
    public grupousuario() {

    }

    // Getters and setters

    public float getPagado() {
        return pagado;
    }

    public void setPagado(float pagado) {
        this.pagado = pagado;
    }

    public float getDebe() {
        return debe;
    }

    public void setDebe(float debe) {
        this.debe = debe;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public usuario getusu() {
        return usu;
    }

    public void setUsu(usuario Usuario) {
        this.usu = Usuario;
    }


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
