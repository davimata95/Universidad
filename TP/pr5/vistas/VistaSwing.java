package tp.pr5.vistas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import tp.pr5.Main;
import tp.pr5.control.ControlSwing;
import tp.pr5.control.TableroInmutable;
import tp.pr5.control.TipoJugador;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.MovimientoInvalido;
import tp.pr5.logica.Observador;
import tp.pr5.logica.TipoJuego;

@SuppressWarnings("serial")
public class VistaSwing extends JFrame implements Observador{
	
	private ControlSwing control;
	private Container PanelPrincipal;
	private JPanel panelizquierdo;
	private JPanel panelderechosuperior;
	private JPanel panelderechoinferior;
	private JPanel Panelcentroabajo;
	private JPanel Panelcentro;
	private JPanel Panelizquierdoarriba;
	private JPanel Panelaux;
	private JButton Btndeshacer;
	private JButton Btnsalir;
	private JButton Btnaleat;
	private JButton Btnreiniciar;
	private JButton Btncambiar;
	private JComboBox<TipoJuego> CmbBoxtipojuego;
	private JPanel Panelparalabel;
	private JPanel Panelabajo2;
	private JButton Casillas[][];
	private int ancho;
	private int alto;
	//private JLabel TxtTurno;
	private JTextField TxtAncho;
	private JTextField TxtAlto;
	private JLabel LblAncho;
	private JLabel LblAlto;
	private JPanel PanelGravity;
	private JTextField TxtTurno;
	private JLabel TxtJugadorBl;
	private JLabel TxtJugadorNe;
	private JComboBox<TipoJugador> TipoJug;
	private JComboBox<TipoJugador> TipoJug2;
	private JPanel PanelJugador;
	
	private TipoJugador JugBlancas=TipoJugador.HUMANO;
	private TipoJugador JugNegro=TipoJugador.HUMANO;
	
	private final DefaultComboBoxModel<TipoJuego> modelTipo = new DefaultComboBoxModel<TipoJuego>(TipoJuego.values());
	private final DefaultComboBoxModel<TipoJugador> modeljug1 = new DefaultComboBoxModel<TipoJugador>(TipoJugador.values());
	private final DefaultComboBoxModel<TipoJugador> modeljug2 = new DefaultComboBoxModel<TipoJugador>(TipoJugador.values());
	
	
	public VistaSwing(ControlSwing control){
		super("Practica5-TP");
		
		this.control=control;
	
		iniPanelPrinci();
		control.inicioPartida();
	}

	private void iniPanelPrinci(){
			
		this.control.addObservador(this);
		ancho=6;
		alto=6;
		//crea los paneles
		PanelPrincipal=new JPanel();
		PanelPrincipal=this.getContentPane();
		PanelPrincipal.setLayout(new BorderLayout());
		panelizquierdo= new JPanel();
		panelderechosuperior= new JPanel();
		panelderechoinferior= new JPanel();
		PanelJugador=new JPanel();
			
		PanelIzquierdo();
		PanelDerechoSuperior();
		PanelDerechoInferior();
		PanelDerechoMedio();
			
		
		Panelcentroabajo= new JPanel();
		Panelcentroabajo.setLayout(new FlowLayout(FlowLayout.CENTER));
			
		//da a icon la imagen del boton salir
		ImageIcon icon;
		URL url=Main.class.getResource("icon/exit.png");
		icon=new ImageIcon(url);
		
		//configuracion del boton salir
		Btnsalir = new JButton("Salir");
		Btnsalir.setIcon(icon);
		//Btnsalir.setPreferredSize(new Dimension(90,40));
		Panelcentroabajo.add(Btnsalir);
			
		//config. del panel centro
		Panelcentro =new JPanel();
		Panelcentro.setLayout(new BorderLayout());
		Panelcentro.add(panelderechosuperior,BorderLayout.NORTH);
		
		
		JPanel PanelAuxDere=new JPanel();
		PanelAuxDere.setLayout(new BorderLayout());
		PanelAuxDere.add(PanelJugador,BorderLayout.NORTH);
		PanelAuxDere.add(panelderechoinferior,BorderLayout.CENTER);
		Panelcentro.add(PanelAuxDere,BorderLayout.CENTER);
		Panelcentro.add(Panelcentroabajo,BorderLayout.SOUTH);
		
		//añade los paneles al p. principal
		PanelPrincipal.add(panelizquierdo,BorderLayout.WEST);
		PanelPrincipal.add(Panelcentro, BorderLayout.EAST);
			
		PanelPrincipal.revalidate();
		this.setLocation(10, 10);	
		this.setVisible(true);
		this.pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ConfigEventosDerecha();
		ConfigEventosIzquierda();
	}
	
	private void ConfigEventosDerecha(){
		
		
		//Config. del boton deshacer
		Btndeshacer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				control.undo();
			}
		});
		
		//Config. del boton reiniciar
		Btnreiniciar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0){

				JugBlancas.parar();
				JugNegro.parar();
				JugBlancas=TipoJugador.HUMANO;
				JugNegro=TipoJugador.HUMANO;
				control.reiniciar();

			}
			
		});
		
		//Config. del boton salir
		Btnsalir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0){
				
				control.finalizar();
				
			}
		});
				
		//Config. del boton cambiar juego
		Btncambiar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0){
				 TipoJuego tipo=null;
				 JugBlancas.parar();
				 JugNegro.parar();
	               tipo=(TipoJuego) CmbBoxtipojuego.getSelectedItem();
	               
	               if(tipo.getRedimensionable()==true){
	            	   int tancho;
						int tfilas;
					
						if(!TxtAlto.getText().equals("")){
							tfilas=Integer.parseInt(TxtAlto.getText());
							tipo.setalto(tfilas);
						}else tipo.setalto(0);
						
						
						if(!TxtAncho.getText().equals("")){
							tancho=Integer.parseInt(TxtAncho.getText());
							tipo.setancho(tancho);
						}  else tipo.setancho(0);
	            	   
	               }
	               
	               
	           	control.reset(tipo);
				}
		});
			
		CmbBoxtipojuego.addItemListener(new ItemListener(){
		
			public void itemStateChanged(ItemEvent e){ 
	               TipoJuego tipo=null;
	               tipo=(TipoJuego) CmbBoxtipojuego.getSelectedItem();
	               
			if(tipo.getRedimensionable()==true){
				
				
			PanelGravity.setVisible(true);
			}
			else{
				PanelGravity.setVisible(false);
			}
					
			} 
		});
		
		TipoJug.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e){
				{
				if(e.getStateChange()==ItemEvent.SELECTED){
					JugBlancas=(TipoJugador) TipoJug.getSelectedItem();
				
					
					if(!JugBlancas.getActivo()){
						control.avisaCambioTipo();
					}
				}
				}
			}
		});
		
	TipoJug2.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange()==ItemEvent.SELECTED){
				
					JugNegro=(TipoJugador) TipoJug2.getSelectedItem();
			
			
					if(!JugNegro.getActivo())
					{
						control.avisaCambioTipo();
					}
				}
			
			}
	});
		
		TxtAlto.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
					if (!(Character.isDigit(c)||(c == KeyEvent.VK_DELETE))){
						e.consume();
				}
			}
		});
			
		TxtAncho.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_DELETE))){
					e.consume();
				}
			}
		});
			
	}

	//Configura los eventos del panel izq
	private void ConfigEventosIzquierda(){
		
		Btnaleat.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				control.poner();
			}
		});
		
		
		for(int i=0 ;i<ancho;i++){	
			final int x=i;
			for(int j=0;j<alto;j++){
				final int  z=j;
				Casillas[i][j].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0){
						control.poner(x, z);
					
					}
					
				});
			}
		}
	}

	//Configura el panel sup.derecha
	private void PanelDerechoSuperior(){
			
		//Da a el icono el valor del boton undo
		ImageIcon icon;
		URL url=Main.class.getResource("icon/undo.png");
		icon=new ImageIcon(url);
		
		panelderechosuperior.setLayout(new FlowLayout(FlowLayout.CENTER));
		Btndeshacer = new JButton("Deshacer");
		Btnreiniciar = new JButton("Reiniciar");
		//	Btndeshacer.setPreferredSize(new Dimension(90,40));
		Btndeshacer.setEnabled(false);
		Btndeshacer.setIcon(icon);
			
			url=Main.class.getResource("icon/reiniciar.png");
			icon=new ImageIcon(url);
			Btnreiniciar.setIcon(icon);
		//	Btnreiniciar.setPreferredSize(new Dimension(90,40));
			panelderechosuperior.add(Btndeshacer);
			panelderechosuperior.add(Btnreiniciar);
			
			Border bordeder= new TitledBorder(new EtchedBorder(), "Partida");
			
			panelderechosuperior.setBorder(bordeder);
			panelderechosuperior.setSize(200,100);
	
	}
	private void PanelDerechoMedio(){
	
		PanelJugador.setLayout(new BorderLayout());
		TxtJugadorBl=new JLabel("Jugador Blancas");
		TxtJugadorNe=new JLabel("Jugador Negras");
		
		JPanel Panelauxjug=new JPanel();
		JPanel Panelauxjug2=new JPanel();
		Panelauxjug.setLayout(new FlowLayout(FlowLayout.CENTER));
		Panelauxjug2.setLayout(new FlowLayout(FlowLayout.CENTER));
		TipoJug=new JComboBox<TipoJugador>(modeljug1);
		TipoJug2=new JComboBox<TipoJugador>(modeljug2);
		
		TxtJugadorBl.setPreferredSize(new Dimension(100,20));
		TxtJugadorNe.setPreferredSize(new Dimension(100,20));
	

		
		Panelauxjug.add(TxtJugadorBl);
		Panelauxjug.add(TipoJug);
		
		Panelauxjug2.add(TxtJugadorNe);
		Panelauxjug2.add(TipoJug2);
		
		PanelJugador.add(Panelauxjug,BorderLayout.NORTH);
		PanelJugador.add(Panelauxjug2,BorderLayout.CENTER);
		
		Border bordejug= new TitledBorder(new EtchedBorder(), "Gestion de Jugadores");
		PanelJugador.setBorder(bordejug);
		
		
	}
	
	//Configura el panel derecho inferior
	private void PanelDerechoInferior(){

			panelderechoinferior.setLayout(new BorderLayout());
			CmbBoxtipojuego = new JComboBox<TipoJuego>(modelTipo);
		
			CmbBoxtipojuego.setSize(10, 30);
			
			ImageIcon icon;
			URL url=Main.class.getResource("icon/chek.png");
			icon=new ImageIcon(url);
			
				
			Btncambiar = new JButton("Cambiar");
			Btncambiar.setIcon(icon);
			
			TxtAncho= new JTextField(4);
			TxtAlto= new JTextField(4);
			LblAlto=new JLabel("Alto:");
			LblAncho=new JLabel("Ancho:");
			
			PanelGravity=new JPanel();
			PanelGravity.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			PanelGravity.add(LblAncho);
			PanelGravity.add(TxtAncho);
			PanelGravity.add(LblAlto);
			PanelGravity.add(TxtAlto);
			PanelGravity.setVisible(false);
			Panelaux =new JPanel();
			Panelaux.setLayout(new FlowLayout(FlowLayout.CENTER));
			Panelaux.add(Btncambiar);
			panelderechoinferior.add(CmbBoxtipojuego,BorderLayout.NORTH);
			panelderechoinferior.add(PanelGravity);
			panelderechoinferior.add(Panelaux, BorderLayout.SOUTH);
			Border bordeizq= new TitledBorder(new EtchedBorder(), "CambioJuego");
			
			panelderechoinferior.setBorder(bordeizq);

		}
	
	private void PanelIzquierdoArriba(){
		Panelizquierdoarriba = new JPanel();
		
		Panelizquierdoarriba.setLayout(new GridLayout(alto,ancho));
		
		Casillas=new JButton[ancho][alto];
		for(int i=0 ;i<ancho;i++){
			
			for(int j=0;j<alto;j++){
			
				JButton  boton =new JButton();
				boton.setName(""+i);
				boton.setBackground(Color.GREEN);
				
				boton.setPreferredSize(new Dimension(50,50));
				Casillas[i][j]=boton;		
			}
					
		}
		
		for(int i=0 ;i<alto;i++){
				
			for(int j=0;j<ancho;j++){
			
				Panelizquierdoarriba.add(Casillas[j][i]);
				
			}
		}
		
	}
	//Configura el panel izquierfo
	private void PanelIzquierdo(){
		
		panelizquierdo.setLayout(new BorderLayout());
		
		
		PanelIzquierdoArriba();
			
			
		panelizquierdo.add(Panelizquierdoarriba, BorderLayout.NORTH);
		
		TxtTurno=new JTextField("JUEGAN BLANCAS");
		TxtTurno.setForeground(Color.BLUE);
		
		TxtTurno.setHorizontalAlignment(JTextField.CENTER);
		Font fuente=new Font("Dialog",Font.BOLD,15);
		TxtTurno.setFont(fuente);
		TxtTurno.setPreferredSize(new Dimension(200,30));
		TxtTurno.setEditable(false);
		TxtTurno.setBackground(Color.WHITE);
		Panelparalabel =new JPanel();
			
		Panelparalabel.setLayout(new BorderLayout());
			
		Panelparalabel.add(TxtTurno,BorderLayout.CENTER);
		panelizquierdo.add(Panelparalabel, BorderLayout.CENTER);
			
			
		ImageIcon icon;
		URL url=Main.class.getResource("icon/aleat.png");
		icon=new ImageIcon(url);
				
			Btnaleat = new JButton("Aleatorio");
			Btnaleat.setIcon(icon);
		//	Btnaleat.setPreferredSize(new Dimension(90,40));
			Panelabajo2 =new JPanel();
			Panelabajo2.setLayout(new FlowLayout());
			Panelabajo2.add(Btnaleat);
			panelizquierdo.add(Panelabajo2, BorderLayout.SOUTH);
			
		
			//this.pack();
			//this.revalidate();
			//panelizquierdo.updateUI();		
	}
	
	//Pinta el tablero
	private void pintarTablero(TableroInmutable tab){
		
		Ficha a1;
		for(int i=0 ;i<ancho;i++){
			
			for(int j=0;j<alto;j++){	
				 a1=tab.getCasilla(i+1, j+1);
				 final int h=j;
				 final int k=i;
				 if(a1==Ficha.BLANCA){
					 SwingUtilities.invokeLater(new Runnable() {
						 public void run() {
							 	Casillas[k][h].setBackground(Color.WHITE); 
						 }
						 });
					 
					 
				 }
				 if(a1==Ficha.NEGRA){
					 
					 SwingUtilities.invokeLater(new Runnable() {
						 public void run() {
							 	Casillas[k][h].setBackground(Color.BLACK);
						 }
					 });
				 
				 }
				 if(a1==Ficha.VACIA){
					 
					 SwingUtilities.invokeLater(new Runnable() {
						 public void run() {
							 	Casillas[k][h].setBackground(Color.GREEN);
					 
					 
						 		}
					 	});
				 }
			}
		}
	
	}	
	

	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		// TODO Auto-generated method stub
		JugBlancas.parar();
		JugNegro.parar();
		JugBlancas=TipoJugador.HUMANO;
		JugNegro=TipoJugador.HUMANO;
		pintarTablero(tabFin);
		
			for(int i=0 ;i<ancho;i++){
				for(int j=0;j<alto;j++){	
				
				Casillas[i][j].setEnabled(false);

			}
		}
		Btnaleat.setEnabled(false);
		Btndeshacer.setEnabled(false);
		CmbBoxtipojuego.setEnabled(false);
		Btncambiar.setEnabled(false);
					
		JOptionPane.showMessageDialog(this, "Ganador es " + ganador.toString(), "Partida Terminada",
                JOptionPane.OK_OPTION);
		
	}

	@Override
	public void onUndo(TableroInmutable tab, boolean hayMas, Ficha turno) {
		// TODO Auto-generated method stub
		pintarTablero(tab);
		final Ficha turn=turno;
		 SwingUtilities.invokeLater(new Runnable() {
			 public void run() {
					TxtTurno.setText("JUEGAN " +turn.toString()+"S");
					
			 }
			 });
		
		if(turno==Ficha.BLANCA){
			if(!JugBlancas.posibleDehacer()){
				
				control.undo();
			}
			
				
		}
		if(turno==Ficha.NEGRA){
			if(!JugNegro.posibleDehacer()){
				
				control.undo();
		
		}
		}
		Btndeshacer.setEnabled(hayMas);
	}


	@Override
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {

	
		
		ancho=tabIni.getColumnas();
		alto=tabIni.getFilas();
		JugNegro.parar();
		JugBlancas.parar();
		final Ficha turn=turno;
		 SwingUtilities.invokeLater(new Runnable() {
			 public void run() {
		
		JugNegro=TipoJugador.HUMANO;
		JugBlancas=TipoJugador.HUMANO;
		PanelPrincipal.remove(panelizquierdo);
		panelizquierdo=new JPanel();
		PanelIzquierdo();
		ConfigEventosIzquierda();
		PanelPrincipal.add(panelizquierdo);
		CmbBoxtipojuego.setSelectedIndex(0);
		TipoJug.setSelectedIndex(0);
		TipoJug2.setSelectedIndex(0);
		PanelPrincipal.revalidate();
		
		Btnaleat.setEnabled(true);
		Btndeshacer.setEnabled(true);
		CmbBoxtipojuego.setEnabled(true);
		Btncambiar.setEnabled(true);
		
		TxtTurno.setText("JUEGAN " +turn.toString()+"S");
		
			 }
		 });
		 this.pack();
		JOptionPane.showMessageDialog(this, "Reiniando partida", "Reiniciar",
                JOptionPane.YES_OPTION);
		pintarTablero(tabIni);
		
	}


	@Override
	public void onUndoNotPossible() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "Imposible deshacer", "Advertencia",
                JOptionPane.WARNING_MESSAGE);
	}


	@Override
	public void onCambioTurno(Ficha turno) {
		
	
		JugBlancas.parar();
		JugNegro.parar();
		final Ficha turn=turno;
		 SwingUtilities.invokeLater(new Runnable() {
			 public void run() {
				TxtTurno.setText("JUEGAN " +turn.toString()+"S");
			 }
			});
		
		 if(turn==Ficha.BLANCA){
			 final boolean ena = JugBlancas.getActivo();
			 SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						Btndeshacer.setEnabled(ena);
						
						TipoJug.setEnabled(ena);
						TipoJug2.setEnabled(ena);

						}
				});
			 for(int i=0 ;i<ancho;i++){
					for(int j=0;j<alto;j++){
						final int v=i;
						final int w=j;
						 SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								
						
								Casillas[v][w].setEnabled(ena);
							}
							});

				}
			}
				JugBlancas.iniciar(control);
			
				
			}
			if(turn==Ficha.NEGRA){
				
				 final boolean ena = JugNegro.getActivo();
				 SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							Btndeshacer.setEnabled(ena);
							TipoJug.setEnabled(ena);
							TipoJug2.setEnabled(ena);
						}
						});
				 
				 for(int i=0 ;i<ancho;i++){
						for(int j=0;j<alto;j++){
							final int v=i;
							final int w=j;
							 SwingUtilities.invokeLater(new Runnable() {
								public void run() {

									Casillas[v][w].setEnabled(ena);
								}
								});

					}
				}
				JugNegro.iniciar(control);
			}
		
	}


	@Override
	public void onMovimientoIncorrecto(MovimientoInvalido movimientoException) {
		// TODO Auto-generated method stub
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Btndeshacer.setEnabled(true);
			}
			});
		JOptionPane.showMessageDialog(this,movimientoException.getMessage(),"Movimiento Invalido", JOptionPane.ERROR_MESSAGE);
	}


	@Override
	public void onCambioJuego(TableroInmutable tab,  Ficha turno) {
		// TODO Auto-generated method stub
	
	
		JugNegro.parar();
		JugBlancas.parar();
		JugNegro=TipoJugador.HUMANO;
		JugBlancas=TipoJugador.HUMANO;
		
	
		ancho=tab.getColumnas();
		alto=tab.getFilas();
		final Ficha turn=turno;
		/* SwingUtilities.invokeLater(new Runnable() {
			 public void run() {*/
		PanelPrincipal.remove(panelizquierdo);
		panelizquierdo=new JPanel();
		PanelIzquierdo();
		ConfigEventosIzquierda();
		PanelPrincipal.add(panelizquierdo);
		CmbBoxtipojuego.setSelectedIndex(0);
		TipoJug.setSelectedIndex(0);
		TipoJug2.setSelectedIndex(0);
		TipoJug.setEnabled(true);
		TipoJug2.setEnabled(true);
		PanelPrincipal.revalidate();
		
		TxtTurno.setText("JUEGAN " +turn.toString()+"S");
		
	/*		 }
		 });*/
		 this.pack();
		JOptionPane.showMessageDialog(this, "Reiniando partida", "Reiniciar",
                JOptionPane.YES_OPTION);
		
		pintarTablero(tab);
		}
	
	@Override
	public void onMovimientoEnd(TableroInmutable tab) {
		// TODO Auto-generated method stub
		
		
		pintarTablero(tab);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Btndeshacer.setEnabled(true);
			}
			});

		
	}

	@Override
	public void onMostrarTablero(TableroInmutable tab) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onInicioPartida(TableroInmutable tab) {
		// TODO Auto-generated method stub
		ancho=tab.getColumnas();
		alto=tab.getFilas();
		PanelPrincipal.remove(panelizquierdo);
		panelizquierdo=new JPanel();
		PanelIzquierdo();
		ConfigEventosIzquierda();
		PanelPrincipal.add(panelizquierdo);
		PanelPrincipal.revalidate();
		this.pack();
		pintarTablero(tab);
	
		
	}

	@Override
	public void onMovimientoStart(Ficha turno) {
		// TODO Auto-generated method stub
		
	}	
}
