package tp.pr2.logica;

public class Partida {
	private Tablero tablero;
	private Ficha turno;
	private Ficha ganador;
	private int ancho;
	private int alto;
	private boolean term=false;
	private ReglasJuego reglaspartida;
	private Movimiento ultimovimiento;
	private int numUndo=0;
	private Movimiento[] undoStack=new Movimiento[10];
	
	//crea el tablero y da las dimensiones
	public Partida(ReglasJuego reglas){ 
		
		this.reglaspartida=reglas;
		tablero=reglaspartida.iniciaTablero();
		turno=reglaspartida.jugadorInicial();
		ancho= tablero.getAncho();
		alto=tablero.getAlto();
	}
	
	//ejecuta el mov. si se puede
	public boolean ejecutaMovimiento(Movimiento mov){	
		boolean posible = false;

		if (turno == mov.getJugador() && !isTerminada()) {
		
				posible=mov.ejecutaMovimiento(tablero);	
		}
			
		if (posible){
			ultimovimiento=mov;
			ganador=reglaspartida.hayGanador(ultimovimiento, tablero);
			if(ganador!=Ficha.VACIA)
			{
				term=true;
			}
			else{
				if(reglaspartida.tablas(ultimovimiento.getJugador(), tablero)==true)
				{
					term=true;
				}
			}
			guardaMov(ultimovimiento);
			turno=reglaspartida.siguienteTurno(turno, tablero);			
		}
		
		return posible;
	}
	
	//da el ganador de la partida (si lo hay)
	public Ficha getGanador(){	
		
		Ficha gan;
		//Si la partida acaba
		if(isTerminada()==true)	gan=ganador;
		else					gan=Ficha.VACIA;
		
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
	
	private void guardaMov(Movimiento mov) {
		
		if (numUndo <= 10){
			if (numUndo == 10){
				for(int i=0;i<numUndo-1;i++){
					undoStack[i]=undoStack[i+1];
				}
				undoStack [numUndo-1] = mov;
			}
			else{
				undoStack [numUndo] = mov;
				numUndo++;
			}
		}
	}
	
	public void reset(ReglasJuego reglas){
		
		//Inicializa los valores necesarios para inicializar la partida
		tablero= new Tablero(ancho,alto);
		numUndo = 0 ;
		undoStack=new Movimiento[10];
		
		//La partida siempre la empiezan las blancas
		turno=Ficha.BLANCA;	
	}
	
	//Deshace el ultimo mov.
	public boolean undo(){	
		
		boolean deshecho=true;
		Movimiento movi ;

		if (numUndo > 0) {
			movi= undoStack[numUndo-1];
			movi.undo(tablero);
		
			numUndo--;
			
			turno=reglaspartida.siguienteTurno(turno, tablero);
		}
		else {
			deshecho = false;
		}
		return deshecho; 
	}		

}
