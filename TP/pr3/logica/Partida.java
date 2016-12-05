package tp.pr3.logica;

import tp.pr3.control.Jugador;

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
	
	//crea el tablero y da las dimensiones
	public Partida(ReglasJuego reglas){ 
		
		this.reglaspartida=reglas;
		tablero=reglaspartida.iniciaTablero();
		turno=reglaspartida.jugadorInicial();
		undoStack=new Movimiento[10];
		numUndo=0;
		movrestantes=0;
	}
	
	//ejecuta el mov. si se puede
	public boolean ejecutaMovimiento(Movimiento mov)throws MovimientoInvalido{	
		boolean posible = false;
			
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
		//Si hay menos de 10 movimientos 
		if (numUndo <= 9){
			//Si hay 10
		if(numUndo!=9){
			
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
		if(movrestantes!=10){
			movrestantes++;
		}
	}
	
	
	
	public Movimiento MovimientoJugador(Jugador ju ){
		Movimiento mov;
		mov = ju.getMovimiento(tablero, turno);
		return mov;
		
		
		
	}
	
	//Reinicia la partida
	public void reset(ReglasJuego reglas){
		
		//Inicializa los valores necesarios para inicializar la partida
		tablero= reglas.iniciaTablero();
		numUndo = 0 ;
		movrestantes =0;
		undoStack=new Movimiento[10];
		
		//La partida siempre la empiezan las blancas
		turno=reglas.jugadorInicial();	
	}
	
	//Deshace el ultimo mov.
	public boolean undo(){	
		
		boolean deshecho=true;
		Movimiento movi ;

		//Si hay movimientos que deshacer
		if (movrestantes>0) {
			
			if(numUndo==0){
				movi= undoStack[9];
				movi.undo(tablero);
				numUndo=9;
			}
			else {
				movi= undoStack[numUndo-1];
				movi.undo(tablero);
				numUndo--;
				
			}
			turno=reglaspartida.siguienteTurno(turno, tablero);
			movrestantes--;
		}
		//Si no hay movimientos que deshacer
		else {
			deshecho = false;
		}
		return deshecho; 
	}	
	
	//Muestra el tablero
	public void mostrarTablero () {
	/*	
		String fila;
		int ancho=tablero.getAncho();
		int alto=tablero.getAlto();
	
		for (int j = 0 ; j<alto;j++){
			fila="|";
			for (int i =0; i < ancho; i++) {
				
				Ficha fichita = tablero.getCasilla(i+1,j+1);
				
				fila=fila +turno.dibuja(fichita);
			}
			fila = fila +"|"; 
			System.out.println(fila);
		}
		
		fila="+";
		String ffinal=" ";
		int num = 1;
		for (int j =0; j < ancho; j++) {
			fila=fila +"-";
			ffinal=ffinal + (num);
			num++;
			if (num == 10){
				num=0;
			}
			
		}
		System.out.println(fila + "+");
		System.out.println(ffinal + "\n");
	
	
	*/
		System.out.println(tablero);
	}
	
	
	

}
