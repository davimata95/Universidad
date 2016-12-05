package tp.pr4.control;


import tp.pr4.logica.Ficha;
import tp.pr4.logica.Movimiento;
import tp.pr4.logica.MovimientoInvalido;
import tp.pr4.logica.Observador;
import tp.pr4.logica.Partida;

public class ControlSwing { 

	private Partida partida;
	private FactoriaTipoJuego factoria;
	Jugador jugador1 ;
	
	
	public void poner(){
		Jugador jugadoraleat=factoria.creaJugadorAleatorio();
		Movimiento mov =partida.MovimientoJugador(jugadoraleat);
		
		partida.ejecutaMovimiento(mov);
		partida.getGanador();
	
	}
	
	public ControlSwing (Partida p, FactoriaTipoJuego f){
		//poner aleatorio
		partida=p;
		factoria=f;
		jugador1=factoria.creaJugadorHumanoConsola();
	}
	
	public void poner(int col, int fila){
	
		Movimiento mov =partida.MovimientoJugador(jugador1,col+1,fila+1);

		partida.ejecutaMovimiento(mov);
		partida.getGanador();
	}
	
	public void reset(FactoriaTipoJuego f){
		factoria=f;
		jugador1=factoria.creaJugadorHumanoConsola();
		partida.reset(factoria.creaReglas());
		
	}
	
	public void undo(){
		partida.undo();
	}
	
	public void finalizar(){
		System.exit(0);
	}
	
	public void reiniciar(){
		partida.reiniciar();
	}
	
	public void addObservador(Observador l) {
		partida.addObserver(l);
		
	}
	
	public void tamanopartida(){
		partida.infortamano();
			
		
	}
	
	
}
