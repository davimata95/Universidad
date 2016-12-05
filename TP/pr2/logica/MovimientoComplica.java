package tp.pr2.logica;

public class MovimientoComplica extends Movimiento {

	private Tablero tablero;
	private Ficha turno;
	private boolean end;
	private int ancho;
	private int alto;
	private Ficha estado;
	private int ultcol;
	private int ultfila;
	private int col;

	//para los undo
	private boolean lleno;
	private Ficha perdida;

	public MovimientoComplica(int donde, Ficha color) {
		
		super(color);
		this.col=donde;
		this.turno=color;
		
		this.perdida = Ficha.VACIA;
		this.lleno=false;
	}
	
	//Abstractos a implementar de la clase Movimiento
	
	public boolean ejecutaMovimiento (Tablero tab) {
	
		tablero=tab;
		alto=	tablero.getAlto();
		ancho=	tablero.getAncho();
		boolean salida=false;
		
		if(end==false){
			
			//el jugador introduce un valor entre 1 y ancho pero el vector empieza en 0	
			int i=alto-1;

			//Si la columna introducida es correcta
			if (col >0 && col <= ancho){				
				do{
					estado=tablero.getCasilla(col, i+1);	
					//si la casilla esta vacia la pintara y hara el cambio de turno
					if(estado==Ficha.VACIA){	
						tablero.setCasilla(col, i+1, turno);
						ultcol=col;
						ultfila=i+1;
						salida=true;	
					} 
					i--;
				}while(i>=0 && salida==false);
				if(salida==false) {
					lleno=true;
					int k=alto-2;
					perdida=tablero.getCasilla(col, 1);
					while(k>=0){
						estado=tablero.getCasilla(col,k+1);
						tablero.setCasilla(col, k+2, estado);
						k--;
					}
					tablero.setCasilla(col, 1, turno);
					salida=true;
					ultcol=col;
					ultfila=1;
					
				}
			}//Si no es correcta la columna devolvera false y saltara el aviso de mov. invalido
		}
		return salida;
	}
	
	public int getultfil(){
		return ultfila;
	}
	
	public int getultcol(){
		return ultcol;
	}
	
	public void undo(Tablero tab) {
		
		int i=0;
		Ficha casilla;
		
		//j sera la columna del ultimo mov.
		int j=col; 
		if (!lleno){
			while(i<alto){
				//da a casilla el valor de la ficha
				casilla=tablero.getCasilla(j, i+1);	
		
				//Si la casilla esta ocupada
				if(casilla!=Ficha.VACIA){		
					//Pinta la casilla de color a vacia
					tablero.setCasilla(j, i+1, Ficha.VACIA);	
					i=alto;	
				}
				i++;
			}
		}
		//si se perdio alguna ficha
		else{
			//intercambia las fichas para colocar la ficha perdida
			for(int k=0; k < alto;k++){
				estado=tablero.getCasilla(col,k+1);
				tablero.setCasilla(col, k, estado);
			}	
			//colocamos la ficha perdida en el tablero
			tablero.setCasilla(j, alto,perdida);	
		}
	}	
}
