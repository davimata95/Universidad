package tp.pr4.control;

import tp.pr4.logica.*;


//Implementación de la factoría para el juego del Conecta4
public class FactoriaConecta4 extends java.lang.Object implements FactoriaTipoJuego {
	
	//Construye las reglas del juego concreto.
	public ReglasJuego creaReglas(){
		
		return new ReglasConecta4();
	}

	//Construye un movimiento para el juego concreto.
	public Movimiento creaMovimiento(int col, int fila, Ficha color){

		return new MovimientoConecta4(col, color);
	}
		
	//Construye el objeto Jugador que se encarga de preguntar el siguiente movimiento a realizar.
	public Jugador creaJugadorHumanoConsola(java.util.Scanner in){
		
		return new JugadorHumanoConecta4(in, this);
	}
	public Jugador creaJugadorHumanoConsola(){
		
		return new JugadorHumanoConecta4(this);
	}
		
	//Construye el objeto Jugador capaz de jugar al juego concreto de forma aleatoria.
	public Jugador creaJugadorAleatorio(){
		return new JugadorAleatorioConecta4(this);		
	}
	
}