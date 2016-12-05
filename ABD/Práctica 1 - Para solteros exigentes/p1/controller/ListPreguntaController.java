package abd.p1.controller;

import java.util.List;

import javax.swing.DefaultListModel;

import abd.p1.bd.DBUsuario;
import abd.p1.model.Contesta;
import abd.p1.model.Opcion;
import abd.p1.model.Pregunta;
import abd.p1.model.Usuario;
import abd.p1.view.ContestarPreguntaDialog;
import abd.p1.view.ListPreguntaPanel;
import abd.p1.view.PreguntaCellRenderer;

public class ListPreguntaController {

	private DBUsuario DB;
	private Usuario usuario;
	private final DefaultListModel<Pregunta> modelo = new DefaultListModel<>();
	
	public ListPreguntaController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListPreguntaController(DBUsuario DB, Usuario usuario) {
		super();
		this.DB = DB;
		this.usuario = usuario;
	}
	
	public void obtenerListaPreguntas(ListPreguntaPanel parent) {
		List<Pregunta> preguntas = DB.obtenerListaPreguntas();
		
		if (preguntas != null) {
			for (Pregunta p : preguntas) {
				modelo.addElement(p);
			}
			parent.setModel(modelo);
			parent.setCellRenderer(new PreguntaCellRenderer());
		}
	}

    public void ResponderPregunta(ListPreguntaPanel parent, Pregunta pregunta) {
    	ContestarPreguntaDialog cpd = new ContestarPreguntaDialog(pregunta);
    	cpd.setController(this);
    	cpd.setVisible(true);
    }

	public void guardarRespuesta(ContestarPreguntaDialog parent, Opcion opcion, int relevancia) {
		
		Contesta contestacionAnterior = DB.comprobarRespuesta(opcion.getIdP(), usuario.getCorreo());
		Contesta contestacion = new Contesta(usuario, opcion.getPreguntaMadre(), opcion, relevancia);
		
		if (contestacionAnterior != null) {
			DB.actualizarRespuesta(contestacion, contestacionAnterior);
		} else {
			DB.guardarRespuesta(contestacion);
		}
		
		parent.setVisible(false);
	}

	public void ResponderPreguntaAleatoria(ListPreguntaPanel listPreguntaPanel) {
		Pregunta pregunta = DB.obtenerPreguntaAleatoria();
		
		ContestarPreguntaDialog cpd = new ContestarPreguntaDialog(pregunta);
    	cpd.setController(this);
    	cpd.setVisible(true);
	}
	
}
