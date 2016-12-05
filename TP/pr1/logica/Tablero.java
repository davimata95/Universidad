package tp.pr1.logica;

public class Tablero {
	private Ficha [ ][ ] tablero;
	private int ancho;
	private int alto;
	
	public Tablero (int ancho, int alto) { //inicializa tablero VACIA
		
		
		if(ancho<1 || alto<1){ //Crearia el tablero 1,1 si introduces algun valor < 0
			ancho=1;
			alto=1;
		}

		this.ancho=ancho;
		this.alto=alto;
		tablero = new Ficha [ancho][alto];	//Crearia el tablero de las dimensiones indicadas (ancho, alto).
		
		for (int i = 0 ; i<ancho;i++){
			for (int j=0;j<alto;j++) {
				tablero[i][j]=Ficha.VACIA;				
			}
		}
	}
	
	public int getAncho(){ //da el ancho

		return ancho;
	}
	
	public int getAlto(){ //da el alto
		return alto;
	}
	
	public Ficha getCasilla(int x, int y){ //da la ficha de la casilla indicada
		
		//El array empieza en 0 por lo que x-- e y--

		Ficha fichaSalida = Ficha.VACIA;
		x--;
		y--;
		if(x >= 0 && x < ancho){
			if(y >= 0 && y < alto){
				fichaSalida = tablero[x][y];	
			}
		}
		return fichaSalida;	
	}
	
	public void setCasilla(int x, int y, Ficha color){ //pinta la casilla del color indicado
	
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


