package es.ucm.abd.practica2.dao;

import es.ucm.abd.practica2.model.Abedemon;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.w3c.dom.Element;

/**
 * Implementación concreta del DAO que hace llamadas a eXist.
 * 
 * @author Manuel Montenegro (mmontene@ucm.es)
 */
public class AbedemonDAOImpl implements AbedemonDAO {

    private final XQDataSource ds;

    public AbedemonDAOImpl(XQDataSource ds) {
        this.ds = ds;
    }

    
    /**
     * Obtiene los tipos de especies disponibles en la BD.
     * 
     * @return Lista de tipos de especies (sin duplicados)
     */
    @Override
    public List<String> getTypes() {
    	List<String> types = new ArrayList<>();
    	InputStream is = getClass().getResourceAsStream("Abedemon1.xquery");
    	
    	XQConnection con = null;
    	XQPreparedExpression exp = null;
    	XQResultSequence rs = null;
    	
    	try {
    		con = ds.getConnection();
    		exp = con.prepareExpression(is);
    		rs = exp.executeQuery();
			while (rs.next()) {
				types.add(rs.getItemAsString(null));
			}
			rs.close();
			exp.close();
			con.close();
		} catch (XQException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println(types);
		
        return types;
    }

    /**
     * Obtiene las especies de abedemon de un determinado.
     * 
     * @param type Tipo a buscar
     * @return Especies encontradas del tipo pasado como parámetro.
     */
    @Override
    public List<Abedemon> getAbedemonsOf(String type) {
        List<Abedemon> abedemons = new ArrayList<Abedemon>();
    	InputStream is = getClass().getResourceAsStream("Abedemon2.xquery");
    	
    	XQConnection con = null;
    	XQPreparedExpression exp = null;
    	XQResultSequence rs = null;
    	
    	try {
    		con = ds.getConnection();
    		exp = con.prepareExpression(is);
    		exp.bindString(new QName("tipo"), type, null);
    		rs = exp.executeQuery();
			while (rs.next()) {
				Element e = (Element) rs.getObject();
				String id = e.getAttribute("id");
				String nombre = e.getAttribute("nombre");
				int numPoderes = Integer.valueOf(e.getAttribute("num-ataques"));
				Abedemon a = new Abedemon(id, nombre, numPoderes);
				abedemons.add(a);
			}
			rs.close();
			exp.close();
			con.close();
		} catch (XQException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return abedemons;
    }

    /**
     * Obtiene la descripción de una especie de abedemon.
     * 
     * @param id ID de la especie a describir
     * @return Código XHTML con la descripción de la especie
     */
    @Override
    public String getAbedemonDescription(String id) {
    	String abedemon = null;
    	InputStream is = getClass().getResourceAsStream("Abedemon3.xquery");
    	
    	try {
    		XQConnection con = ds.getConnection();
    		XQPreparedExpression exp = con.prepareExpression(is);
    		exp.bindString(new QName("id"), id, null);
    		XQResultSequence rs = exp.executeQuery();
			while (rs.next()) {
				abedemon = rs.getItemAsString(null);
			}
			rs.close();
			exp.close();
			con.close();
		} catch (XQException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return abedemon;
    }

    /**
     * Obtiene el daño máximo recibido por un abedemon de una especie dada al
     * ser atacado por otro.
     * 
     * @param idAttacker ID de la especie del atacante.
     * @param idReceiver ID de la especie que recibe el daño.
     * @return Máximo daño que puede infligir el atacante.
     */
    @Override
    public Integer getDamage(String idAttacker, String idReceiver) {
        // TODO: Implementar (parte opcional)
        System.err.println("getDamage() not implemented yet");
        return null;
    }
}
