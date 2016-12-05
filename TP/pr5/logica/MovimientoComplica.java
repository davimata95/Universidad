package tp.pr5.logica;

public class MovimientoComplica extends Movimiento {

	private Ficha turno;
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
	
	//Ejecuta el movimiento
	public boolean ejecutaMovimiento (Tablero tab) throws MovimientoInvalido {
	
		boolean end=false;
		alto=tab.getAlto();
		ancho=tab.getAncho();
		boolean salida=false;
		
		//Si la partida no esta terminada
		if(end==false){
			
			//el jugador introduce un valor entre 1 y ancho pero el vector empieza en 0	
			int i=alto-1;

			//Si la columna introducida es correcta
			if (col >0 && col <= ancho){				
				do{
					//coge la casilla 
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
				
				
				//Si el bucle acaba con salida false
				if(salida==false) {
					//Significa que el tablero esta lleno
					lleno=true;
					int k=alto-2;
					//Hace los cambio y se "pierde una ficha"
					perdida=tab.getCasilla(col, 1);
					
					//recoloca las fichas en el tablero tras poner la nueva ficha
					while(k>=0){
						estado=tab.getCasilla(col,k+1);
						tab.setCasilla(col, k+2, estado);
						k--;
					}
					
					tab.setCasilla(col, 1, turno);
					salida=true;
					ultcol=col;
					ultfila=1;
				}
			}
			//Si no es correcta la columna devolvera false y saltara el aviso de mov. invalido
			else throw new MovimientoInvalido("Columna incorrecta. Debe estar entre 1 y 4.");
	
		}
		return salida;
	}
	
	//devuelve la ultimafila
	public int getultfil(){
		return ultfila;
	}
	
	//devuelve la ultima columna
	public int getultcol(){
		return ultcol;
	}
	
	//deshace el movimiento
	public void undo(Tablero tab) {
		
		int i=0;
		Ficha casilla;
		
		//j sera la columna del ultimo mov.
		int j=col;
		
		//Si no esta lleno
		if (!lleno){
			
			//comprueba cual es la ultima ficha puesta para eliminarla
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
		//si se perdio alguna ficha
		else{
			//intercambia las fichas para colocar la ficha perdida
			for(int k=0; k < alto;k++){
				estado=tab.getCasilla(col,k+1);
				tab.setCasilla(col, k, estado);
			}	
			//colocamos la ficha perdida en el tablero
			tab.setCasilla(j, alto,perdida);	
		}
	}	
}
