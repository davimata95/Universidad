package tp.pr5.logica;

public interface ReglasJuego {

	//Permite averiguar si en la partida ya tenemos un ganador o no.
	Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t);
	
	//Construye el tablero que hay que utilizar para la partida, según las reglas del juego.
	Tablero iniciaTablero();
	
	//Devuelve el color del jugador que comienza la partida.
	Ficha jugadorInicial();
	
	//Devuelve el color del jugador al que le toca poner.
	Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t);
	
	//Devuelve true si, con el estado del tablero dado, la partida ha terminado en tablas.
	boolean tablas(Ficha ultimoEnPoner, Tablero t);
	
	TipoJuego getJuego();
	
}
