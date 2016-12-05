package tp.pr5.control;

import java.util.Scanner;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Observador;
import tp.pr5.logica.Partida;

public class ControlConsola {

	private Partida partida;
	private FactoriaTipoJuego factoria;
	private java.util.Scanner in;
	private Jugador jugador1;
	private Jugador jugador2;
	
	public ControlConsola(Partida p, Scanner in, FactoriaTipoJuego f){
		this.partida = p;
		this.factoria= f;
		this.in = in;
		this.jugador1 = f.creaJugadorHumanoConsola(in);
		this.jugador2 = f.creaJugadorHumanoConsola(in);
	}
	
	public void reset(FactoriaTipoJuego f) {
		
		//Parte comun al seleccionar cualquiera de los juegos
		
		partida.reset(f.creaReglas());
		
		//los jugadores vuelven a ser humanos
		jugador1 = f.creaJugadorHumanoConsola(in);
		jugador2 = f.creaJugadorHumanoConsola(in);
		
	}
	
	public void undo() {
		partida.undo();
	}
	
	public void cambiarJugador(String color, Jugador tipoJugador) {
		
		if (color.equalsIgnoreCase("negras")){
			this.jugador2 = tipoJugador;
		}
		if (color.equalsIgnoreCase("blancas")){
			this.jugador1 = tipoJugador;
		}
		
	}
	
	public void poner() {

		Jugador jugador = null;
		Ficha turno = partida.getTurno();
	
		if (turno == Ficha.BLANCA)
			jugador=jugador1;
		else if (turno == Ficha.NEGRA)
			jugador = jugador2;
		
		
		Movimiento movimiento=null;
		//Crea el movimiento
		while(movimiento==null){
			movimiento = partida.MovimientoJugador(jugador);
		}
		partida.ejecutaMovimiento(movimiento) ;
		
		partida.getGanador();
		
	}
	
	public void NecMostrarTable(){
	
		
		partida.mosTablero();
		
	}
	
	public boolean cambiarJuego (String[] opcion)throws NumberFormatException{
		
		boolean valido=true;
		//Mira que tipo de juego se puso
		switch (opcion[1]){

			//Si el tipo de juego es c4
			case "c4":
				factoria=new FactoriaConecta4();
				reset(factoria);
				break;	
				
			//Si el tipo de juego es co
			case "co":
				factoria=new FactoriaComplica();
				reset(factoria);				
			break; 
			
			//Si el tipo de juego es gr
			case "gr":
				
				//Se comprobo en vistaConsola que tiene las pos. 3 y 4
				//coge el ancho y el alto de las posiciones 3 y 4 del vector creado con el split
				int anch = Integer.parseInt(opcion[2]);
				int alt = Integer.parseInt(opcion[3]);
					
				//Reinicia la partida
				factoria=new FactoriaGravity(anch,alt);
				reset(factoria);
				
			break;
		
			case "rv":
				factoria=new FactoriaReversi();
				reset(factoria);
			break;
				
			//Si el tipo de juego es desconocido P.E jugar tp
			default:
				valido=false;
			break;
		}	
		return valido;
	}
		
	public void finalizar() {
		System.exit(0);		
	}

	public void reiniciar() {
		partida.reiniciar();
	}
	
	//metodos de manejo de observadores
	public void addObservador(Observador o) {
		partida.addObserver(o);
	}

	
	
}
