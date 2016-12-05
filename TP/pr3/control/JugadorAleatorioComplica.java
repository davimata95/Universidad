package tp.pr3.control;

import java.util.Random;

import tp.pr3.logica.*;

//Jugador que juega de forma aleatoria a Complica

public class JugadorAleatorioComplica extends java.lang.Object implements Jugador {

	private FactoriaTipoJuego factoria;
	
	public Movimiento getMovimiento(Tablero tab, Ficha color){
		
		//crea la factoria para jugar complica
		factoria = new FactoriaComplica();
		 
		//Obtiene el ancho
		int ancho=tab.getAncho();
		
		//Crea un valor aleatorio r
		Random r=new Random();
		
		//Da a x el valor de r+1 ya que el tablero empieza en 1 y no en 0 como el vector
		int x=r.nextInt(ancho)+1;
		
		//Crea el movimiento necesario
		Movimiento mov=factoria.creaMovimiento(x, 0, color);
		
		return mov;
	}	
}
