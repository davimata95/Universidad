package tp.pr3.control;

import tp.pr3.logica.*;

//Interfaz que aglutina los métodos de construcción de los distintos objetos involucrados en un juego concreto
public interface FactoriaTipoJuego {
	
	//Construye las reglas del juego concreto.
	ReglasJuego creaReglas();

	//Construye un movimiento para el juego concreto. 
	Movimiento creaMovimiento(int col, int fila, Ficha color);
	
	//Construye el objeto Jugador que se encarga de preguntar al usuario por consola el siguiente movimiento a realizar.
	Jugador creaJugadorHumanoConsola(java.util.Scanner in);	
	
	//Construye el objeto Jugador capaz de jugar al juego concreto de forma aleatoria.
	Jugador creaJugadorAleatorio();

}
