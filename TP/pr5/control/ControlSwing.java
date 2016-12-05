package tp.pr5.control;

import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Observador;
import tp.pr5.logica.Partida;
import tp.pr5.logica.TipoJuego;

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
	
	public void reset(TipoJuego tipo){
		
		if(tipo==TipoJuego.CONECTA4){
			factoria=new FactoriaConecta4();
		}
		if(tipo==TipoJuego.COMPLICA){
			factoria=new FactoriaComplica();
		}
		if(tipo==TipoJuego.GRAVITY){
			if(tipo.getalto()==0 || tipo.getalto()==0)
			{
				factoria=new FactoriaGravity(10,10);
			}
			else
			factoria=new FactoriaGravity(tipo.getancho(),tipo.getalto());
		}
		if(tipo==TipoJuego.REVERSI){
			factoria=new FactoriaReversi();
		}
		jugador1=factoria.creaJugadorHumanoConsola();
		partida.reset(factoria.creaReglas());
		
	}
	
	public void avisaCambioTipo(){
		
		partida.aviscambiotio();
	}
	
	public void undo(){
		partida.undo();
	}
	
	public void finalizar(){
		System.exit(0);
	}
	
	public void addObservador(Observador l) {
		partida.addObserver(l);
		
	}

	public void reiniciar() {
		partida.reiniciar();
		
	}
	
	public void inicioPartida(){
		partida.inicioPartida();
	}
	
	
}
