package tp.pr5.control;

import tp.pr5.logica.*;

//Implementación de la factoría para el juego del Gravity
public class FactoriaGravity extends java.lang.Object implements FactoriaTipoJuego {

	private int ancho=10;
	private int alto=10;
	
	public FactoriaGravity(int ancho, int alto){
		this.ancho=ancho;
		this.alto=alto;	
	}
	
	//Construye las reglas del juego concreto.
	public ReglasJuego creaReglas(){

		//Gravity empezara por defecto con tablero 10x10
		return new ReglasGravity(ancho, alto);
	}


	//Construye un movimiento para el juego concreto.
	public Movimiento creaMovimiento(int col, int fila, Ficha color){

		return new MovimientoGravity(col, fila, color);
	}
		
	//Construye el objeto Jugador que se encarga de preguntar el siguiente movimiento a realizar.
	public Jugador creaJugadorHumanoConsola(java.util.Scanner in){
				
		return  new JugadorHumanoGravity(in, this);
	}
	public Jugador creaJugadorHumanoConsola(){
		
		return  new JugadorHumanoGravity( this);
	}
		
	//Construye el objeto Jugador capaz de jugar al juego concreto de forma aleatoria.
	public Jugador creaJugadorAleatorio(){
		return new JugadorAleatorioGravity(this);			
	}
}
