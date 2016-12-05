package abd.p1;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import abd.p1.bd.DBUsuario;
import abd.p1.controller.AccessController;
import abd.p1.controller.ListPreguntaController;
import abd.p1.controller.ListUserController;
import abd.p1.model.Usuario;
import abd.p1.view.AccessDialog;
import abd.p1.view.PrincipalFrame;

/**
 * Ésta es la clase que arranca la aplicación. La ejecución del método main()
 * no reconstruirá la base de datos. La base de datos se supone ya construida
 * por el método CreateDB.main()
 *
 */
public class Main {
    
    private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
            return null;
        }
    }
   
    
    public static void main(String[] args) {

        SessionFactory sf = null;
        
        try {
            sf = buildSessionFactory();
            
            /*Usuario usu1 = new Usuario("rodrinot@ucm.es", "123456", "Rodri", Genero.masculino, Interes.ambos, 20, 20, null, null, "Pa k kieres saber eso? jaja saludos", null, null, null);
            Session session = sf.openSession();
            Transaction tr = session.beginTransaction();
            session.save(usu1);
            tr.commit();*/
            
            // Creamos el DAO para acceder a la base de datos pasándole el SessionFactory
            DBUsuario dao = new DBUsuario(sf);
            // Creamos el usuario
            Usuario usuario = new Usuario();
            // Creamos el controlador pasándole el DAO y el usuario creados anteriormente
            AccessController controller = new AccessController(dao, usuario);
            // Creamos la ventana de acceso
            AccessDialog accessDialog = new AccessDialog(controller);
            // Y la mostramos
            accessDialog.setVisible(true);
            // Obtenemos el usuario
            usuario = controller.getUsuario();
            // Creamos la ventana principal para el usuario obtenido
            if (usuario.getLatitud() != 0) {
            	ListUserController controller2 = new ListUserController(dao, usuario);
            	ListPreguntaController controller3 = new ListPreguntaController(dao, usuario);
            	PrincipalFrame principalFrame = new PrincipalFrame(controller2, controller3);
            	principalFrame.setVisible(true);
        	}
            System.exit(0);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (sf != null) sf.close();
        }
        
    }

}
