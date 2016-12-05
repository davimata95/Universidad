package tp.pr5.control;
import tp.pr5.logica.*;

/*
 * Interfaz que representa un jugador; 
 * cuando el controlador quiere saber qué movimiento ejecutar a continuación,
 * le pregunta al jugador que tiene el turno. 
 */
public interface Jugador {

	//Devuelve el siguiente movimiento a efectuar por el jugador. 
	Movimiento getMovimiento(Tablero tab, Ficha color);
	Movimiento getMovimiento(Tablero tab, Ficha color,int fil,int col);
	
}
