package fdi.ucm.cuentacuentas;

import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DAOCuentaCuentas {
    private static final DAOCuentaCuentas dao = new DAOCuentaCuentas();
    private String IP = "10.0.2.2:3306";
    private String baseDatos = "/cuentacuentas";
    private String usbd="root";
    private String contbd="";

    public DAOCuentaCuentas() {


    }

    public final static  DAOCuentaCuentas getInstance(){
        return dao;
    }
    public ArrayList<grupo> buscarGrupos(usuario usu, Boolean admin) {


        try {
            Connection conn;
         //   Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + IP + baseDatos, usbd, contbd);
            Statement estado = conn.createStatement();
            System.out.println("Conexion establecida");
            String peticion;
            if (!admin) {
                peticion = "select * from grupo g JOIN grupousuario gp ON g.id = gp.idGrupo WHERE gp.idUsuario=" + usu.getId();
            } else {
                peticion = "select * from grupo g JOIN grupousuario gp ON g.id = gp.idGrupo WHERE gp.idUsuario=" + usu.getId() + " AND" +
                        " gp.admin=true";
            }
            ResultSet result = estado.executeQuery(peticion);
            if (result != null){
                if (!result.next()) {

                }else{
                    ArrayList<grupo> grupos= new ArrayList<>();
                    grupo grup= new grupo();
                    grup.setNombre(result.getString("nombre"));
                    grup.setDeben(result.getFloat("deben"));
                    grup.setPagado(result.getFloat("pagado"));
                    grup.setId(result.getInt("id"));
                    grup.setLatitud(result.getDouble("latitud"));
                    grup.setLongitud(result.getDouble("longitud"));

                    Blob bl=result.getBlob("imagen");
                    if(bl!=null){

                        grup.setImagen(bl.getBytes(1, (int)bl.length()));
                    }
                    grupos.add(grup);
                    while(result.next()){
                        grup= new grupo();
                        grup.setNombre(result.getString("nombre"));
                        grup.setDeben(result.getFloat("deben"));
                        grup.setPagado(result.getFloat("pagado"));
                        grup.setId(result.getInt("id"));

                        grup.setLatitud(result.getDouble("latitud"));
                        grup.setLongitud(result.getDouble("longitud"));
                        bl=result.getBlob("imagen");
                        if(bl!=null){
                            grup.setImagen(bl.getBytes(1, (int)bl.length()));
                        }
                        grupos.add(grup);
                    }

                   /* grupo gruposarr[]=new grupo[grupos.size()];

                    for(int i=0;i<grupos.size();i++){
                        gruposarr[i]=grupos.get(i);
                    }*/

                    return grupos;

                }
            }else{

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

       return null;
    }
    public usuario login(String usun, String cont){
        usuario usu=null;
        try {

            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);

            String peticion = "select * from usuario WHERE nickname= ? AND password = ?";
            PreparedStatement consulta = conn.prepareStatement(peticion);
            System.out.println("Conexion establecida");
            consulta.setString(1,usun);
            consulta.setString(2,cont);
            ResultSet result = consulta.executeQuery();
            if (result != null) {
                if (!result.next()) {
                   return null;
                } else {

                    usu= new usuario();
                    usu.setId(result.getInt("id"));
                    usu.setNickname(result.getString("nickname"));
                    usu.setNombre(result.getString("nombre"));
                    usu.setApellidos(result.getString("apellidos"));
                    usu.setTelefono(result.getString("telefono"));
                    usu.setPassword(result.getString("password"));

                    Blob bl=result.getBlob("imagen");
                    if(bl!=null){
                        usu.setImagen(bl.getBytes(1, (int)bl.length()));
                    }
                  return usu;
                }
            }else{
              return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<grupousuario> SelectGrupoUsuario(grupo grup){

        try {
            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
            Statement estado = conn.createStatement();
            System.out.println("Conexion establecida");
            String peticion = "select * from grupousuario g  JOIN usuario u ON g.idUsuario = u.id " + "WHERE g.idGrupo=" + grup.getId();
            ResultSet result = estado.executeQuery(peticion);
            if (result != null) {
                if (!result.next()) {

                } else {
                    ArrayList<grupousuario>  miembros = new ArrayList<>();

                    grupousuario miembro = new grupousuario();
                    miembro.setIdGrupo(grup.getId());
                    miembro.setDebe(result.getFloat("debe"));
                    miembro.setPagado(result.getFloat("pagado"));
                    miembro.setAdmin(result.getBoolean("admin"));

                    usuario usu = new usuario();
                    usu.setId(result.getInt("id"));
                    usu.setNombre(result.getString("nombre"));
                    usu.setNickname(result.getString("nickname"));
                    usu.setApellidos(result.getString("apellidos"));
                    usu.setTelefono(result.getString("telefono"));
                    Blob bl=result.getBlob("imagen");
                    if(bl!=null){

                        usu.setImagen(bl.getBytes(1, (int)bl.length()));
                    }

                    miembro.setUsu(usu);
                    miembros.add(miembro);

                    while (result.next()) {

                        miembro = new grupousuario();
                        miembro.setIdGrupo(grup.getId());
                        miembro.setDebe(result.getFloat("debe"));
                        miembro.setPagado(result.getFloat("pagado"));
                        miembro.setAdmin(result.getBoolean("admin"));

                        usu = new usuario();
                        usu.setId(result.getInt("id"));
                        usu.setNombre(result.getString("nombre"));
                        usu.setNickname(result.getString("nickname"));
                        usu.setApellidos(result.getString("apellidos"));
                        usu.setTelefono(result.getString("telefono"));
                        bl=result.getBlob("imagen");
                        if(bl!=null){

                            usu.setImagen(bl.getBytes(1, (int)bl.length()));
                        }
                        miembro.setUsu(usu);
                        miembros.add(miembro);
                    }
                    return miembros;

                }
            } else {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarDebeUsu(String opcion,String cantidad ,String grupo,String usu ,String opcgrupo ){

    try {
        Connection conn;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
        Statement estado = conn.createStatement();
        System.out.println("Conexion establecida");
        String peticion = "UPDATE grupousuario SET " + opcion + "="+opcion+"+" + cantidad + " WHERE idGrupo=" + grupo + " AND idUsuario=" +
                usu;
        estado.executeUpdate(peticion);

        String update2="UPDATE grupo SET "+opcgrupo+"="+opcgrupo+"+"+ cantidad+ " WHERE id="+grupo;
        estado.executeUpdate(update2);
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    return false;
}

    public boolean actualizarGrupoTotal(String opcion,String cantidad ,String grupo,String total ,String opcgrupo){

        try {
            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
            Statement estado = conn.createStatement();
            System.out.println("Conexion establecida");
            String update1 = "UPDATE grupousuario SET " + opcion + "=" + opcion +"+"+cantidad+ " WHERE idGrupo=" +
                    grupo;
            estado.executeUpdate(update1);

            String update = "UPDATE grupo SET " + opcgrupo + "=" + opcgrupo +"+"+total+ " WHERE id=" +
                    grupo;
            estado.executeUpdate(update);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;


    }



    public boolean actualizarPago(String pago, String grupo, String usu){


        try {
            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
            Statement estado = conn.createStatement();
            System.out.println("Conexion establecida");
            String peticion = "UPDATE grupousuario SET pagado=pagado +" + pago+", debe=debe-"+pago + " WHERE idGrupo=" + grupo + " AND idUsuario=" +
                    usu;
            estado.executeUpdate(peticion);

            String update2="UPDATE grupo SET pagado=pagado+"+ pago+" , deben=deben-"+pago+" WHERE id="+grupo;
            estado.executeUpdate(update2);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean reiniciarCuentas(String grupo){



        try {
            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
            Statement estado = conn.createStatement();
            System.out.println("Conexion establecida");
            String peticion = "UPDATE grupousuario SET pagado=0,  debe=0 WHERE idGrupo=" + grupo;
            estado.executeUpdate(peticion);

            String update2="UPDATE grupo SET pagado=0 ,deben=0 WHERE id="+grupo;
            estado.executeUpdate(update2);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean borraGrupo(String grupo){

        try {
            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
            Statement estado = conn.createStatement();
            System.out.println("Conexion establecida");
            String peticion = "DELETE FROM grupo WHERE id=" + grupo;
            estado.executeUpdate(peticion);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean borrarMiembro(String grupo, String usuario, String gropc, String cantidad) {
        try {
            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
            Statement estado = conn.createStatement();
            System.out.println("Conexion establecida");
            String peticion = "DELETE FROM grupousuario WHERE idGrupo=" + grupo + " AND idUsuario=" + usuario;
            estado.executeUpdate(peticion);
            String update = "UPDATE grupo SET " + gropc + "=" + gropc + "+" + cantidad + " WHERE id=" + grupo;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;

    }

    public ArrayList<usuario> buscarUsuarios(String nick){
        try {
            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
            Statement estado = conn.createStatement();
            System.out.println("Conexion establecida");
            String peticion = "select * from usuario WHERE nickname LIKE '"+ nick+"%'";
            ResultSet result = estado.executeQuery(peticion);
            if (result != null) {
                if (!result.next()) {

                } else {
                    ArrayList<usuario> usuarios= new ArrayList<>();
                    usuario usu = new usuario();
                    usu.setId(result.getInt("id"));
                    usu.setNombre(result.getString("nombre"));
                    usu.setNickname(result.getString("nickname"));
                    usu.setApellidos(result.getString("apellidos"));
                    usu.setTelefono(result.getString("telefono"));
                    Blob bl=result.getBlob("imagen");
                    if(bl!=null){

                        usu.setImagen(bl.getBytes(1, (int)bl.length()));
                    }
                    usuarios.add(usu);
                    while (result.next()) {

                        usu = new usuario();
                        usu.setId(result.getInt("id"));
                        usu.setNombre(result.getString("nombre"));
                        usu.setNickname(result.getString("nickname"));
                        usu.setApellidos(result.getString("apellidos"));
                        usu.setTelefono(result.getString("telefono"));
                        bl=result.getBlob("imagen");
                        if(bl!=null){

                            usu.setImagen(bl.getBytes(1, (int)bl.length()));
                        }

                        usuarios.add(usu);

                    }
                    return usuarios;


                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean insertarUsuario(grupousuario u){

        try {


            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
            String insertusu = "INSERT INTO grupousuario (idGrupo,idUsuario, debe,admin,pagado ) VALUES (? , ? , ? ,? ,?)";

            PreparedStatement estatementusu = conn.prepareStatement(insertusu);
            estatementusu.setInt(1, u.getIdGrupo());
            estatementusu.setFloat(2, u.getusu().getId());
            estatementusu.setFloat(3, u.getDebe());
            estatementusu.setBoolean(4,u.isAdmin());
            estatementusu.setFloat(5, u.getPagado());
            estatementusu.executeUpdate();


            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean insertarGrupo(grupo grup){
        try {
            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
            String sentencia = "INSERT INTO grupo (nombre, deben, pagado ,imagen,latitud, longitud) VALUES (? , ? , ?, ?, ? , ? )";
            PreparedStatement estatement = conn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
            estatement.setString(1, grup.getNombre().toString());
            estatement.setFloat(2, grup.getDeben());
            estatement.setFloat(3, grup.getPagado());
            estatement.setBinaryStream(4, new ByteArrayInputStream(grup.getImagen()), grup.getImagen().length);
            estatement.setDouble(5,grup.getLatitud());
            estatement.setDouble(6,grup.getLongitud());

            estatement.executeUpdate();
            ResultSet rest = estatement.getGeneratedKeys();

            Integer key = null;
            if (rest.next()) {
                key = rest.getInt(1);
                String insertusu = "INSERT INTO grupousuario (idGrupo,idUsuario, debe,admin,pagado ) VALUES (? , ? , ? ,? ,?)";
                for (grupousuario u : grup.getMiembros()) {
                    PreparedStatement estatementusu = conn.prepareStatement(insertusu);
                    estatementusu.setInt(1, key);
                    estatementusu.setFloat(2, u.getusu().getId());
                    estatementusu.setFloat(3, u.getDebe());
                    estatementusu.setBoolean(4, u.isAdmin());
                    estatementusu.setFloat(5, u.getPagado());
                    estatementusu.executeUpdate();
                }

            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public usuario nuevoUsuario(String nick, String pass, String telefono, String nombre , String apellidos, byte[] imagen){
        usuario usu=null;
        try {

            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);
            // Comprobamos que el nick no esté ya registrado
            String sentencia = "SELECT nickname FROM usuario WHERE nickname = ?";
            PreparedStatement estatement = conn.prepareStatement(sentencia);
            estatement.setString(1, nick);
            ResultSet rs = null;
            rs = estatement.executeQuery();
            System.out.println(rs);

            // Insertamos el usuario en la base de datos
            if (rs != null) {
                if (!rs.next()) {

                    sentencia = "INSERT INTO usuario (nickname, password, telefono, nombre, apellidos, imagen) VALUES (?, ?, ?, ?, ?, ?)";
                    estatement = conn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
                    estatement.setString(1, nick);
                    estatement.setString(2, pass);
                    estatement.setString(3, telefono);
                    estatement.setString(4, nombre);
                    estatement.setString(5, apellidos);
                    estatement.setBinaryStream(6, new ByteArrayInputStream(imagen), imagen.length);
                    estatement.executeUpdate();
                    rs = estatement.getGeneratedKeys();
                    usu= new usuario();
                    // Fijamos el usuario
                    usu.setNickname(nick);
                    usu.setNombre(nombre);
                    usu.setApellidos(apellidos);
                    usu.setTelefono(telefono);
                    usu.setPassword(pass);

                    sentencia = "SELECT id, imagen FROM usuario WHERE nickname = ?";
                    estatement = conn.prepareStatement(sentencia);
                    estatement.setString(1,usu.getNickname());
                    rs = estatement.executeQuery();
                    if (rs.next()) {
                        usu.setId(rs.getInt("id"));
                        Blob bl = rs.getBlob("imagen");
                        if(bl != null){
                            usu.setImagen(bl.getBytes(1, (int)bl.length()));
                        }
                    }

                }
            }
            return usu;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}


