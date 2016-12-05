package tp.pr2.logica;

public class Tablero {
	private Ficha [ ][ ] tablero;
	private int ancho;
	private int alto;
	
	//inicializa tablero VACIA
	public Tablero (int ancho, int alto) { 
		
		//Crearia el tablero 1,1 si introduces algun valor < 0
		if(ancho<1 || alto<1){ 
			ancho=1;
			alto=1;
		}

		this.ancho=ancho;
		this.alto=alto;
		//Crearia el tablero de las dimensiones indicadas (ancho, alto).
		tablero = new Ficha [ancho][alto];	
		
		for (int i = 0 ; i<ancho;i++){
			for (int j=0;j<alto;j++) {
				tablero[i][j]=Ficha.VACIA;				
			}
		}
	}
	
	//da el ancho
	public int getAncho(){ 
		return ancho;
	}
	
	//da el alto
	public int getAlto(){ 
		return alto;
	}
	
	//da la ficha de la casilla indicada
	public Ficha getCasilla(int x, int y){ 
		
		Ficha fichaSalida = Ficha.VACIA;
		//El array empieza en 0 por lo que x-- e y--
		x--;
		y--;
		if(x >= 0 && x < ancho){
			if(y >= 0 && y < alto){
				fichaSalida = tablero[x][y];	
			}
		}
		return fichaSalida;	
	}
	
	//pinta la casilla del color indicado
	public void setCasilla(int x, int y, Ficha color){ 
	
		//El array empieza en 0 por lo que x-- e y--
		x--;
		y--;
		if(x>= 0 && x < ancho){
			if(y >= 0 && y < alto){
				tablero[x][y]=color;
					
			}
		}
	}
}


