package tp.pr5.logica;

import tp.pr5.control.TableroInmutable;

public class Tablero implements TableroInmutable {
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
		//crea tablero vacio
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
		//determina casilla valida y la asigna a fachaSalida
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
		//Si la casilla es valida la pinta
		if(x>= 0 && x < ancho){
			if(y >= 0 && y < alto){
				tablero[x][y]=color;
					
			}
		}
	}
	
	public String toString(){
		String fila;
		Ficha turno=Ficha.VACIA;
		String tab="";;
		for (int j = 0 ; j<alto;j++){
			fila= "|";
			for (int i =0; i < ancho; i++) {
				
				Ficha fichita = getCasilla(i+1,j+1);
				
				fila=fila +turno.dibuja(fichita);
			}
			fila = fila +"|\n"; 
			
			tab=tab+fila;
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
		tab=tab+fila + "+\n";
		tab=tab+ffinal+"\n";
		
		
		return tab;
	}

	@Override
	public int getFilas() {
		// TODO Auto-generated method stub
		return alto;
	}

	@Override
	public int getColumnas() {
		// TODO Auto-generated method stub
		return ancho;
	}
	
	public boolean tableroLleno(Tablero t){
		
		boolean lleno=true;
		
		for (int i = 0; i< alto; i++){
			for(int j = 0; j< ancho; j++){
			
				Ficha fichaComprobar = t.getCasilla(alto, ancho);
				
				if (fichaComprobar == Ficha.VACIA){
					lleno=false;
				}
			
			}
		}
		
		return lleno;
	}
}


