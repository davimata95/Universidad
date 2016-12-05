package tp.pr3.logica;

public abstract class Movimiento {

	private Ficha jugador;
	
	public Movimiento(Ficha jug){
		this.jugador=jug;
	}
	
	//Devuelve el color del jugador al que pertenece el movimiento.
	public Ficha getJugador() { 
		return jugador;
	}
	
	//Ejecuta el movimiento sobre el tablero que se recibe como parámetro.
	public abstract boolean ejecutaMovimiento (Tablero tab) throws MovimientoInvalido  ;
		
	//Deshace el movimiento en el tablero recibido como parámetro.
	public abstract void undo(Tablero tab) ;
	
	public abstract int getultfil();
	
	public abstract int getultcol();
	
}
