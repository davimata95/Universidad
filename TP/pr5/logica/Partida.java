package tp.pr5.logica;

import java.util.ArrayList;
import java.util.InputMismatchException;

import tp.pr5.control.Jugador;

public class Partida {
	private Tablero tablero;
	private Ficha turno;
	private Ficha ganador;
	private boolean term=false;
	private ReglasJuego reglaspartida;

	private int numUndo;
	private int movrestantes;
	private Movimiento[] undoStack;
	private static final int tamPila = 10;
	private ArrayList<Observador> obs;
	
	//crea el tablero y da las dimensiones
	public Partida(ReglasJuego reglas){ 
		
		this.reglaspartida=reglas;
		tablero=reglaspartida.iniciaTablero();
		turno=reglaspartida.jugadorInicial();
		undoStack=new Movimiento[tamPila];
		numUndo=0;
		movrestantes=0;
		obs=new ArrayList<Observador>();

	}
	public void addObserver(Observador h){
		obs.add(h);
	}
	public void aviscambiotio(){
		
		int h=0;
		while(h<obs.size()){
			obs.get(h).onCambioTurno(turno);
			h++;
		}
	}
	
	//ejecuta el mov. si se puede
	public boolean ejecutaMovimiento(Movimiento mov){	
	
		boolean posible = false;
		
		int h=0;
		
		
		if (mov != null){	
			try{	
				//Si el ganador no es ni negras ni blancas
				if(ganador!=Ficha.NEGRA && ganador!=Ficha.BLANCA){
					//Si el turno y el jugador coincide
					if(turno==mov.getJugador()){
						//intenta realizar el movimiento
						posible=mov.ejecutaMovimiento(tablero);
					} 
					//Si no son iguales el turno y el jugador salta excepcion
					else throw new MovimientoInvalido();
				}
				//Ganador es ficha.vacia
				else throw new MovimientoInvalido();
				
				Movimiento ultimovimiento;
				//Si se realizo el movimiento correctamente
				if (posible){
					ultimovimiento=mov;
			
					//mira si hay ganador
					ganador=reglaspartida.hayGanador(ultimovimiento, tablero);
					//	Si el ganador no es VACIA
					if(ganador!=Ficha.VACIA){
						term=true;
						h=0;
						while(h<obs.size()){
							obs.get(h).onPartidaTerminada(tablero,ganador);
							h++;
						}	
					}
					else{
						//Si ganador es vacia, mira a ver si hay tablas
						if(reglaspartida.tablas(ultimovimiento.getJugador(), tablero)==true){
							term=true;
							h=0;
							while(h<obs.size()){
								obs.get(h).onPartidaTerminada(tablero,Ficha.VACIA);
								h++;
							}
						}
					}
					//Guarda el movimiento, para los deshacer de complica
					guardaMov(ultimovimiento);
					turno=reglaspartida.siguienteTurno(turno, tablero);			
				
					//Si es posible, comprobara si el proximo turno puede poner
					//Se realiza al final del movimiento del anterior.
				
					//Faltaria comprobar que es reversi
					if (reglaspartida.getJuego() == (TipoJuego.REVERSI)){
						boolean pone=false;
						for(int i = 1 ; i < tablero.getAncho()+1; i++ ){
							for(int j=1; j< tablero.getAlto()+1; j++){
								if (tablero.getCasilla(i, j)==Ficha.VACIA){
									int comprueba = ReglasReversi.movimientoCorrecto(i, j, turno, tablero);
									if (comprueba>0){
										pone=true;
									}
								}
							}
						}
						if (!pone){
							//Cambia el turno, ya que no tiene movimientos posibles
							turno=reglaspartida.siguienteTurno(turno, tablero);							
						}
					}
					
					

				}
				if(term==false){
					h=0;
					while(h<obs.size()){
						obs.get(h).onMovimientoEnd(tablero);
						obs.get(h).onCambioTurno(turno);
						h++;
					}
					
				}
		
			}catch(MovimientoInvalido e){
				h=0;
				while(h<obs.size()){	
					obs.get(h).onMovimientoIncorrecto(e);	
					h++;
				}
			}
		}
		else{
			posible=true;
			turno=reglaspartida.siguienteTurno(turno, tablero);	
			h=0;
			while(h<obs.size()){
				obs.get(h).onMovimientoEnd(tablero);
				obs.get(h).onCambioTurno(turno);
				h++;
			}
		}
		
		return posible;
	}
	
	//da el ganador de la partida (si lo hay)
	public Ficha getGanador(){	
		
		Ficha gan;
		//Si la partida acaba
		if(isTerminada()==true){
			gan=ganador;
			
		}
		else{
			gan=Ficha.VACIA;
		}
		
		return gan;
	}
	
	//da el tablero
	public Tablero getTablero(){	
		return tablero;
	}

	//da el turno
	public Ficha getTurno(){	
	   return turno;
	}
	
	//determina si la partida esta acabada
	public boolean isTerminada(){
		
		return term;
	}
	
	//Guarda el movimiento que realiza, para poder deshacerlo despues
	private void guardaMov(Movimiento mov) {
		//Si hay menos de tamPila movimientos 
		if (numUndo <= tamPila-1){
			//Si hay 10
		if(numUndo!=tamPila-1){
			
				undoStack [numUndo] = mov;
				numUndo++;			
		}
			//Guarda el movimiento con normalidad
			else{
				undoStack[numUndo]=mov;
				numUndo=0;
				
			}
		}
		
		//si no hay aun 10 movimientos 
		if(movrestantes!=tamPila){
			movrestantes++;
		}
	}
	
	public Movimiento MovimientoJugador(Jugador ju ){
		Movimiento mov=null;
		try {
			mov = ju.getMovimiento(tablero, turno);
		}
		catch(InputMismatchException e){
			int h=0;
			while(h<obs.size()){
				
				obs.get(h).onMovimientoIncorrecto(new MovimientoInvalido("Debes introducir una columna y fila en formato entero"));

				h++;
			}
			
		}
		
		return mov;

	}
	
	public Movimiento MovimientoJugador(Jugador ju,int col, int filas ){
		Movimiento mov;
		mov = ju.getMovimiento(tablero, turno,col,filas);
		return mov;

	}
	
	
	//Reinicia la partida
	public void reset(ReglasJuego reglas){
		
		//Inicializa los valores necesarios para inicializar la partida
		reglaspartida=reglas;
		tablero= reglas.iniciaTablero();
		numUndo = 0 ;
		movrestantes =0;
		undoStack=new Movimiento[tamPila];
		
		//La partida siempre la empiezan las blancas
		turno=reglas.jugadorInicial();	
		term=false;
		ganador=Ficha.VACIA;
		int h=0;
		while(h<obs.size()){
			obs.get(h).onCambioJuego(tablero, turno);
			h++;
		}
	}
	
	public void reiniciar(){
		
		tablero= reglaspartida.iniciaTablero();
		numUndo = 0 ;
		movrestantes =0;
		undoStack=new Movimiento[tamPila];
		
		//La partida siempre la empiezan las blancas
		turno=reglaspartida.jugadorInicial();	
		term=false;
		ganador=Ficha.VACIA;
		int h=0;
		while(h<obs.size()){
		obs.get(h).onResetPartida(tablero, turno);
		h++;
		}
	}
	
	//Deshace el ultimo mov.
	public boolean undo(){	
		
		boolean deshecho=true;
		Movimiento movi ;

		//Si hay movimientos que deshacer
		if (movrestantes>0) {
			
			if(numUndo==0){
				movi= undoStack[tamPila-1];
				movi.undo(tablero);
				numUndo=tamPila-1;
			}
			else {
				movi= undoStack[numUndo-1];
				movi.undo(tablero);
				numUndo--;
				
			}
			turno=reglaspartida.siguienteTurno(turno, tablero);
			turno=movi.getJugador();
			movrestantes--;
		
			
	
		boolean haymas=false;
			if(movrestantes>0){
			haymas=true;
			}
		int h=0;
		while(h<obs.size()){
		obs.get(h).onUndo(tablero, haymas,turno);
		h++;
		}
		}
		
		//Si no hay movimientos que deshacer
		else {
			deshecho = false;
			int h=0;
			while(h<obs.size()){
			obs.get(h).onUndoNotPossible();
			h++;
			}
		}
		
		return deshecho; 
	}	

	
	//Muestra el tablero
	public String mostrarTablero () {

		return tablero.toString();
	}
	public void mosTablero(){
		int h=0;
		while(h<obs.size()){
		obs.get(h).onMostrarTablero(tablero);
		h++;
		}
	}
	
	public void inicioPartida(){
		int h=0;
		while(h<obs.size()){
			obs.get(h).onInicioPartida(tablero);
			h++;
		}
	}
	
	

}
