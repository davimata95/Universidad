package tp.pr4.logica;

public class MovimientoConecta4 extends Movimiento {
	
	private Ficha turno;
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
	
	
	//Ejecuta el movimiento
	public boolean ejecutaMovimiento (Tablero tab) throws MovimientoInvalido {

		boolean end=false;
		alto=tab.getAlto();
		ancho=tab.getAncho();
		boolean salida=false;
		
		//Si la partida no acaba
		if(end==false){
			
			//el jugador introduce un valor entre 1 y ancho pero el vector empieza en 0
			int i=alto-1;
						
			//Si la columna introducida es correcta
			if (col >0 && col <= ancho){
				do{
					estado=tab.getCasilla(col, i+1);	
					//si la casilla esta vacia la pintara y hara el cambio de turno
					if(estado==Ficha.VACIA){	
						tab.setCasilla(col, i+1, turno);
						ultcol=col;
						ultfila=i+1;
						salida=true;
					}
					i--;
					
				}while(i>=0 && salida==false);
				//Mientras haya huecos en la columna
				
				//Si la salida es false, significa que la columna esta llena y saltara la excepcion
				if(salida==false) throw new MovimientoInvalido("Columna llena.");
					
				
			}	
			//Si la columna no es correcta
			else{
				
				throw new MovimientoInvalido("Columna incorrecta. Debe estar entre 1 y 7.");
			}
		}
		
		return salida;
	}
	
	//devuelve la ultima fila
	public int getultfil(){
		return ultfila;
	}
	
	//devuelve la ultima columna
	public int getultcol(){
		return ultcol;
	}
	
	//devuelve el jugador
	public Ficha getJugador(){
		return turno;
	}

	//deshace el ultimo movimiento
	public void undo(Tablero tab) {

		int i=0;
		Ficha casilla;

		//j sera la columna del ultimo mov.
		int j=col; 
			
		//mientras no encuentre la ultima ficha puesta
		while(i<alto){
			//da a casilla el valor de la ficha
			casilla=tab.getCasilla(j, i+1);	
			
			//Si la casilla esta ocupada
			if(casilla!=Ficha.VACIA){		
				//Pinta la casilla de color a vacia
				tab.setCasilla(j, i+1, Ficha.VACIA);	
				i=alto;	
			}
			i++;
		}
	}
}
