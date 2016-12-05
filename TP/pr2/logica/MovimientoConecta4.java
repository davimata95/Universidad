package tp.pr2.logica;

public class MovimientoConecta4 extends Movimiento {
	
	private Tablero tablero;
	private Ficha turno;
	private boolean end;
	private int ancho;
	private int alto;
	private Ficha estado;
	private int ultcol;
	private int ultfila;
	private int col;
	
	public MovimientoConecta4(int donde, Ficha color) {
		super(color);
		this.col=donde;
		this.turno=color;
	}
	
	//Abstractos a implementar de la clase Movimiento
	
	public boolean ejecutaMovimiento (Tablero tab) {
		tablero=tab;
		alto=	tablero.getAlto();
		ancho=	tablero.getAncho();
		boolean salida=false;
		
		if(end==false){
				
			int i=alto-1;
			//el jugador introduce un valor entre 1 y ancho pero el vector empieza en 0
			
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
			}	//Si no es correcta la columna devolvera false y saltara el aviso de mov. invalido
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
}
