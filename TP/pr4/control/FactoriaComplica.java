package tp.pr4.control;

import tp.pr4.logica.*;
import tp.pr4.logica.ReglasComplica;


//Implementación de la factoría para el juego del Complica
public class FactoriaComplica extends java.lang.Object implements FactoriaTipoJuego {
	//Construye las reglas del juego concreto.
	public ReglasJuego creaReglas(){
		
		return new ReglasComplica();
	}

	//Construye un movimiento para el juego concreto.
	public Movimiento creaMovimiento(int col, int fila, Ficha color){

		return new MovimientoComplica(col, color);
	}
		
	//Construye el objeto Jugador que se encarga de preguntar el siguiente movimiento a realizar.
	public Jugador creaJugadorHumanoConsola(java.util.Scanner in){

		return  new JugadorHumanoComplica(in, this);
	}
	public Jugador creaJugadorHumanoConsola(){

		return  new JugadorHumanoComplica(this);
	}
		
	//Construye el objeto Jugador capaz de jugar al juego concreto de forma aleatoria.
	public Jugador creaJugadorAleatorio(){
		
		return new JugadorAleatorioComplica(this);
	}
	
}