package tp.pr4.control;

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

import tp.pr4.Main;
import tp.pr4.logica.Ficha;
import tp.pr4.logica.MovimientoInvalido;
import tp.pr4.logica.Observador;
import tp.pr4.logica.Partida;

public class VistaSwing extends JFrame implements Observador{
	
	private ControlSwing control;
	private Container PanelPrincipal;
	private JPanel panelizquierdo;
	private JPanel panelderechosuperior;
	private JPanel panelderechoinferior;
	private JPanel Panelcentroabajo;
	private JPanel Panelcentro;
	private JPanel Panelizquierdoarriba;
	private JPanel Panelabajo;
	private JPanel Panelaux;
	private JButton Btndeshacer;
	private JButton Btnsalir;
	private JButton Btnaleat;
	private JButton Btnreiniciar;
	private JButton Btncambiar;
	private JComboBox<String> CmbBoxtipojuego;
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
	
	
	
	public VistaSwing(ControlSwing control){
		super("Practica4-TP");
		
		this.control=control;

		iniPanelPrinci();
	
	}

	private void iniPanelPrinci(){
			
		this.control.addObservador(this);
		control.tamanopartida();
		PanelPrincipal=new JPanel();
		PanelPrincipal=this.getContentPane();
		PanelPrincipal.setLayout(new BorderLayout());
		panelizquierdo= new JPanel();
		panelderechosuperior= new JPanel();
		panelderechoinferior= new JPanel();
			
		PanelIzquierdo();
		PanelDerechoSuperior();
		PanelDerechoInferior();
			
		Panelcentroabajo= new JPanel();
		Panelcentroabajo.setLayout(new FlowLayout(FlowLayout.CENTER));
			
		ImageIcon icon;
		URL url=Main.class.getResource("icon/exit.png");
		icon=new ImageIcon(url);
		Btnsalir = new JButton("Salir");
		Btnsalir.setIcon(icon);
		//Btnsalir.setPreferredSize(new Dimension(90,40));
		Panelcentroabajo.add(Btnsalir);
			
		Panelcentro =new JPanel();
		Panelcentro.setLayout(new BorderLayout());
		Panelcentro.add(panelderechosuperior,BorderLayout.NORTH);
		Panelcentro.add(panelderechoinferior,BorderLayout.CENTER);
			
		Panelcentro.add(Panelcentroabajo,BorderLayout.SOUTH);

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
		
		Btndeshacer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				control.undo();
			}
		});
		
		Btnreiniciar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0){
				
				control.reiniciar();
			}
			
		});
		
		Btnsalir.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0){
				
				control.finalizar();
				
			}
		});
				
		
		Btncambiar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0){
				
				
				if(CmbBoxtipojuego.getSelectedItem()=="CONECTA4"){
					control.reset(new FactoriaConecta4());
				}
				if(CmbBoxtipojuego.getSelectedItem()=="COMPLICA"){
					control.reset(new FactoriaComplica());
				}
				if(CmbBoxtipojuego.getSelectedItem()=="GRAVITY"){
					int tancho;
					int tfilas;
				
					if(TxtAlto.getText().equals("")){
						tfilas=10;
					}
					else tfilas=Integer.parseInt(TxtAlto.getText());
					
					if(TxtAncho.getText().equals("")){
						
						tancho=10;
					}else tancho=Integer.parseInt(TxtAncho.getText());
					control.reset(new FactoriaGravity(tancho,tfilas));
				}
				
			}
		});
			
		CmbBoxtipojuego.addItemListener(new ItemListener(){
		
			public void itemStateChanged(ItemEvent e){ 
	                   
				if(CmbBoxtipojuego.getSelectedItem()=="CONECTA4"){
					PanelGravity.setVisible(false);
				}
				if(CmbBoxtipojuego.getSelectedItem()=="COMPLICA"){
					PanelGravity.setVisible(false);
				}
				if(CmbBoxtipojuego.getSelectedItem()=="GRAVITY"){
					PanelGravity.setVisible(true);
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

	private void PanelDerechoSuperior(){
			
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
	
	private void PanelDerechoInferior(){

			panelderechoinferior.setLayout(new BorderLayout());
			CmbBoxtipojuego = new JComboBox<String>();
			CmbBoxtipojuego.addItem("CONECTA4");
			CmbBoxtipojuego.addItem("COMPLICA");
			CmbBoxtipojuego.addItem("GRAVITY");
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
	
	private void PanelIzquierdo(){
		
		panelizquierdo.setLayout(new BorderLayout());
		
		Panelizquierdoarriba = new JPanel();
		
		Panelizquierdoarriba.setLayout(new GridLayout(alto,ancho));
		
		Casillas=new JButton[ancho][alto];
		for(int i=0 ;i<ancho;i++){
			
			for(int j=0;j<alto;j++){
			
				JButton  boton =new JButton();
				boton.setName(""+i);
				boton.setBackground(Color.GREEN);
				
				boton.setPreferredSize(new Dimension(40,40));
				Casillas[i][j]=boton;		
			}
					
		}
		
		for(int i=0 ;i<alto;i++){
				
			for(int j=0;j<ancho;j++){
			
				Panelizquierdoarriba.add(Casillas[j][i]);
				
			}
		}
			
			
			
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
			
			
			control.addObservador(this);
			//this.pack();
			//this.revalidate();
			//panelizquierdo.updateUI();		
	}
	
	private void pintarTablero(TableroInmutable tab){
		
		Ficha a1;
		for(int i=0 ;i<ancho;i++){
			
			for(int j=0;j<alto;j++){	
				 a1=tab.getCasilla(i+1, j+1);
				 
				 if(a1==Ficha.BLANCA){
					 Casillas[i][j].setBackground(Color.WHITE); 
					 
				 }
				 if(a1==Ficha.NEGRA){
					 Casillas[i][j].setBackground(Color.BLACK);
				 }
				 
				 if(a1==Ficha.VACIA){
					 Casillas[i][j].setBackground(Color.GREEN);
				 }
			}
		}
	
	}	
	

	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		// TODO Auto-generated method stub
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
	public void onUndo(TableroInmutable tab, boolean hayMas) {
		// TODO Auto-generated method stub
		pintarTablero(tab);
		
		Btndeshacer.setEnabled(hayMas);
	}


	@Override
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		// TODO Auto-generated method stub
		ancho=tabIni.getColumnas();
		alto=tabIni.getFilas();
		this.getContentPane().removeAll();
		iniPanelPrinci();
		
		TxtTurno.setText("JUEGAN " +turno.toString()+"S");
		JOptionPane.showMessageDialog(this, "Reiniando partida", "Reiniciar",
                JOptionPane.YES_OPTION);
		
	}


	@Override
	public void onUndoNotPossible() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "Imposible deshacer", "Advertencia",
                JOptionPane.WARNING_MESSAGE);
	}


	@Override
	public void onCambioTurno(Ficha turno) {
		
		TxtTurno.setText("JUEGAN " +turno.toString()+"S");
		
	}


	@Override
	public void onMovimientoIncorrecto(MovimientoInvalido movimientoException) {
		// TODO Auto-generated method stub
		
		JOptionPane.showMessageDialog(this,movimientoException.getMessage(),"Movimiento Invalido", JOptionPane.ERROR_MESSAGE);
	}


	@Override
	public void onCambioJuego(TableroInmutable tab,  Ficha turno) {
		// TODO Auto-generated method stub
		ancho=tab.getColumnas();
		alto=tab.getFilas();
		this.getContentPane().removeAll();
		iniPanelPrinci();
		
		TxtTurno.setText("JUEGAN " +turno.toString()+"S");
		
		JOptionPane.showMessageDialog(this, "Cambiando de Juego", "Cambio",
                JOptionPane.YES_OPTION);
		
	}
	
	
	public void onCambioDimension(int x ,int y){
		ancho=x;
		alto=y;
	}


	@Override
	public void onMovimientoEnd(TableroInmutable tab) {
		// TODO Auto-generated method stub
		
		pintarTablero(tab);
		Btndeshacer.setEnabled(true);
		
	}
	
	

}
