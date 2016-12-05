package tp.pr4.logica;

import tp.pr4.control.Jugador;

public class Partida {
	private Tablero tablero;
	private Ficha turno;
	private Ficha ganador;
	private boolean term=false;
	private ReglasJuego reglaspartida;
	private Movimiento ultimovimiento;
	private int numUndo;
	private int movrestantes;
	private Movimiento[] undoStack;
	private static final int tamPila = 10;
	private Observador obs;
	
	//crea el tablero y da las dimensiones
	public Partida(ReglasJuego reglas){ 
		
		this.reglaspartida=reglas;
		tablero=reglaspartida.iniciaTablero();
		turno=reglaspartida.jugadorInicial();
		undoStack=new Movimiento[tamPila];
		numUndo=0;
		movrestantes=0;
		
		
	}
	public void addObserver(Observador h){
		obs=h;
	}
	
	//ejecuta el mov. si se puede
	public boolean ejecutaMovimiento(Movimiento mov){	
		boolean posible = false;
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
		

		//Si se realizo el movimiento correctamente
		if (posible){
			ultimovimiento=mov;
			
			//mira si hay ganador
			ganador=reglaspartida.hayGanador(ultimovimiento, tablero);
			//Si el ganador no es VACIA
			if(ganador!=Ficha.VACIA){
				term=true;
			}
			else{
				//Si ganador es vacia, mira a ver si hay tablas
				if(reglaspartida.tablas(ultimovimiento.getJugador(), tablero)==true)
				{
					term=true;
				}
			}
			//Guarda el movimiento, para los deshacer de complica
			guardaMov(ultimovimiento);
			turno=reglaspartida.siguienteTurno(turno, tablero);			
		}
		
		obs.onMovimientoEnd(tablero);
		obs.onCambioTurno(turno);

	}catch(MovimientoInvalido e){
			
		obs.onMovimientoIncorrecto(e);	
		}
		return posible;
	}
	
	//da el ganador de la partida (si lo hay)
	public Ficha getGanador(){	
		
		Ficha gan;
		//Si la partida acaba
		if(isTerminada()==true){
			gan=ganador;
			obs.onPartidaTerminada(tablero,gan);
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
		Movimiento mov;
		mov = ju.getMovimiento(tablero, turno);
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
		obs.onCambioJuego(tablero, turno);
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
		obs.onResetPartida(tablero, turno);
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
			movrestantes--;
	
		boolean haymas=false;
			if(movrestantes>0){
			haymas=true;
			}
			
		obs.onUndo(tablero, haymas);
		obs.onCambioTurno(turno);
		}
		
		//Si no hay movimientos que deshacer
		else {
			deshecho = false;
			obs.onUndoNotPossible();
		}
		
	
		return deshecho; 
	}	
	
	public void infortamano(){
		
		obs.onCambioDimension(tablero.getAncho(), tablero.getAlto());
		
	}
	//Muestra el tablero
	public String mostrarTablero () {

		return tablero.toString();
	}
	
	
	

}
