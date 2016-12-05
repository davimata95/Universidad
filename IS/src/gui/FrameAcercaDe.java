package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Sorin
 *
 */
public class FrameAcercaDe extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Frame para mostrar el "Acerca de" del proyecto.
	 */
	public FrameAcercaDe() {
		getContentPane().setLayout(null);
		
		this.setTitle("Acerca de");
		this.setSize(300, 400);
		this.setVisible(false); 
		this.setResizable(false);
		this.setLocation(100, 100);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		JLabel lblGame = new JLabel("GAME");
		lblGame.setBounds(28, 11, 46, 14);
		getContentPane().add(lblGame);
		
		JLabel lblIngenieraDelSoftware = new JLabel("Ingeniería del Software");
		lblIngenieraDelSoftware.setBounds(28, 33, 194, 14);
		getContentPane().add(lblIngenieraDelSoftware);
		
		JLabel lblGrupo = new JLabel("Grupo 1");
		lblGrupo.setBounds(28, 54, 46, 14);
		getContentPane().add(lblGrupo);
		
		JLabel lblCarlosLpez = new JLabel("Carlos López");
		lblCarlosLpez.setBounds(28, 135, 80, 14);
		getContentPane().add(lblCarlosLpez);
		
		JLabel lblAarnDurn = new JLabel("Aarón Durán");
		lblAarnDurn.setBounds(28, 160, 81, 14);
		getContentPane().add(lblAarnDurn);
		
		JLabel lblMateo = new JLabel("Mateo García");
		lblMateo.setBounds(28, 185, 86, 14);
		getContentPane().add(lblMateo);
		
		JLabel lblDavidQuesada = new JLabel("David Quesada");
		lblDavidQuesada.setBounds(28, 210, 99, 14);
		getContentPane().add(lblDavidQuesada);
		
		JLabel lbllvaroDelfn = new JLabel("Alvaro Delfín");
		lbllvaroDelfn.setBounds(28, 235, 99, 14);
		getContentPane().add(lbllvaroDelfn);
		
		JLabel lblSorinDraghici = new JLabel("Sorin Draghici");
		lblSorinDraghici.setBounds(28, 262, 80, 14);
		getContentPane().add(lblSorinDraghici);
		
		JLabel lblDavidMata = new JLabel("David Mata");
		lblDavidMata.setBounds(28, 287, 80, 14);
		getContentPane().add(lblDavidMata);
		
		JLabel lblJosMara = new JLabel("José María");
		lblJosMara.setBounds(28, 312, 94, 14);
		getContentPane().add(lblJosMara);
		
		JLabel lblUniversidadComplutense = new JLabel("Universidad Complutense");
		lblUniversidadComplutense.setBounds(28, 79, 145, 14);
		getContentPane().add(lblUniversidadComplutense);
		
		JLabel label = new JLabel("----------------");
		label.setBounds(28, 104, 99, 14);
		getContentPane().add(label);
	}
}