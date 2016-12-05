package tp.pr4.control;

import java.util.Scanner;

import tp.pr4.logica.Ficha;
import tp.pr4.logica.Movimiento;
import tp.pr4.logica.Observador;
import tp.pr4.logica.Partida;

public class ControlConsola {

	private Partida partida;
	private FactoriaTipoJuego factoria;
	private java.util.Scanner in;
	private Jugador jugador1;
	private Jugador jugador2;
	private Ficha turno;
	private Movimiento movimiento;
	
	public ControlConsola(Partida p, Scanner in, FactoriaTipoJuego f){
		this.partida = p;
		this.factoria= f;
		this.in = in;
		this.jugador1 = f.creaJugadorHumanoConsola(in);
		this.jugador2 = f.creaJugadorHumanoConsola(in);
		turno=Ficha.BLANCA;
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
	
	public void cambiarJugador(String color, String tipoJugador) {

		//Segun el color que sea
		switch (color){
			//Si se introduce Blancas
			case "blancas":
				//Se cambia el tipo de jugador de la ficha seleccionada 
				switch (tipoJugador){
				//Cambia a jugador humano
					case "humano":
						jugador1 = factoria.creaJugadorHumanoConsola(in);
						break;
						//Cambia a jugador aleatorio
					case "aleatorio":
						jugador1 = factoria.creaJugadorAleatorio();
						break;
				
					//Si se quiere poner un tipo de jugador diferente de humano o aleatorio
					default:
						System.err.println ("No te entiendo.");	
					break;
				//Fin switch tipo
				}
				//Fin case "blancas"
				break;
		
			case "negras":
				//Se cambia el tipo de jugador de la ficha seleccionada 
				switch (tipoJugador){
					//Cambia a jugador humano
					case "humano":
						jugador2 = factoria.creaJugadorHumanoConsola(in);
						break;
						//Cambia a jugador aleatorio
					case "aleatorio":
						jugador2 = factoria.creaJugadorAleatorio();
						break;
					
					//Si se quiere poner un tipo de jugador diferente de humano o aleatorio
					default:
						System.err.println ("No te entiendo.");	
						break;
						//Fin switch tipo
				}	
				// fin case "negras"
				break; 
	
			//Si se quiere cambiar el tipo de otro jugador que no sea blancas o negras
			default:
				System.err.println ("No te entiendo.");	
			break;
			//Fin switch color
		}
	}
	
	public void poner() {

		//si el turno es de blancas(color del jugador 1)
		if (turno == Ficha.BLANCA){
			movimiento = partida.MovimientoJugador(jugador1);
		}
		//si el turno es de NEGRAS(color del jugador 2)
		else if (turno == Ficha.NEGRA) {
			movimiento = partida.MovimientoJugador(jugador2);
		}
		
		partida.ejecutaMovimiento(movimiento) ; 
		partida.getGanador();
	}
	
	public void reiniciar() {		
		partida.reiniciar();
	}
	
	public void finalizar() {
		System.exit(0);		
	}

	//metodos de manejo de observadores
	public void addObservador(Observador o) {
		partida.addObserver(o);
	}
	
}
