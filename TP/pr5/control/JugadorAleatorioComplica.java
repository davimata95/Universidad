package tp.pr5.control;

import java.util.Random;

import tp.pr5.logica.*;

//Jugador que juega de forma aleatoria a Complica

public class JugadorAleatorioComplica extends java.lang.Object implements Jugador {

	private FactoriaTipoJuego factoria;
	public JugadorAleatorioComplica(FactoriaTipoJuego f){
		
		factoria=f;
	}
	
	public Movimiento getMovimiento(Tablero tab, Ficha color){
		
		//crea la factoria para jugar complica
		
		 
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
	
	public Movimiento getMovimiento(Tablero tab,Ficha color,int a, int b)
	{
		Movimiento mov=factoria.creaMovimiento(1, 0, color);
		return mov;
		
	}
}
