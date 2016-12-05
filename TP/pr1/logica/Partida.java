package tp.pr1.logica;

public class Partida {
	private Tablero tablero;
	private Ficha turno=Ficha.BLANCA;
	private boolean terminada;
	private boolean end;
	private Ficha ganador;
	private int ancho;
	private int alto;
	private Ficha estado;
	private boolean tablas;
	private int ultcol;
	private int ultfila;
	private int acertadas;
	private int[ ] undoStack=new int[12];
	private int numUndo=0;
	private int jugbuffer=0;
	
	public Partida(){ //crea el tablero y da las dimensiones
		
		tablero= new Tablero(7,6);  //LAS DIMENSIONES DESEADAS SE INTRODUCEN AQUI (ANCHO, ALTO)
		ancho= tablero.getAncho();
		alto=tablero.getAlto();
		end=false;
	}
	
	public boolean ejecutaMovimiento(Ficha color, int col){	//ejecuta el mov. si se puede

		boolean salida=false;
		
		if(end==false){
				
			int i=alto-1;
			//el jugador introduce un valor entre 1 y ancho pero el vector empieza en 0
		
			if (col >0 && col <= ancho){	//Si la columna introducida es correcta 
				if (color == turno){	//Si el color introducido es igual al del jugador que tiene el turno
					do{
						estado=tablero.getCasilla(col, i+1);	
						if(estado==Ficha.VACIA){	//si la casilla esta vacia la pintara y hara el cambio de turno
							tablero.setCasilla(col, i+1, color);
							ultcol=col;
							ultfila=i+1;
							salida=true;
							if (turno == Ficha.BLANCA){
								turno=Ficha.NEGRA;
							}
							else { 
								turno=Ficha.BLANCA; 
							}
							undoStack[numUndo]=col;	//A�adira el movimiento al buffer
							if(jugbuffer!=12){
								jugbuffer++;
							}
							if(numUndo==11){
								numUndo=0;
							}else{
								numUndo++;
							}
						}
						i--;
					}while(i>=0 && salida==false);
				}	//Si no coinciden turno y color devolvera false y saltara el aviso de mov. invalido
			}	//Si no es correcta la columna devolvera false y saltara el aviso de mov. invalido
		}
		return salida;
	}
	
	public Ficha getGanador(){	//da el ganador de la partida (si lo hay)
		
		if(isTerminada()==true){	//Si la partida acaba
			if(tablas){	//si acaba en tablas
				ganador=Ficha.VACIA;
			}
			else {	//si no acaba en tablas
				Ficha turntemp;
				
				if (turno == Ficha.BLANCA){	
					turntemp=Ficha.NEGRA;
				 }
				 else { 
					 turntemp=Ficha.BLANCA; 
				 }
				ganador=turntemp;	//determina el ganador de la partida
			}
		}else {
			ganador=null;	//Si la partida esta inacabada
		}
		return ganador;
	}
	
	public Tablero getTablero(){	//da el tablero

		return tablero;
	}

	public Ficha getTurno(){	//da el turno
		
	   return turno;
	}
	
	public boolean isTerminada(){	//determina si la partida esta acabada
		terminada=false;
		boolean compro=false;
		Ficha casilla;
		acertadas=0;
		int i=ultfila;
		
		// comprobacion por columna

		while(i<=alto && compro==false){
			casilla=tablero.getCasilla(ultcol, i);
			compro=comprobaciertos(casilla);
			
			i++;
		}
		
		if(terminada==false){
			compro=false;
			i=ultfila-1;
			
			while(i>0 && compro==false){
				casilla=tablero.getCasilla(ultcol, i);
				compro=comprobaciertos(casilla);
				
				i--;
			}
		}
		//FIN comprobacion por columnas
		
		//comprobacion por filas
		if(terminada==false){
			i=ultcol;
			compro=false;
			acertadas=0;
			
			while(i<=ancho && compro==false){
				casilla=tablero.getCasilla(i, ultfila);	
				compro=comprobaciertos(casilla);
				
				i++;
			}
		}
		
		if (terminada==false){
			i=ultcol-1;
			compro=false;
			
			while(i>0 && compro==false){	
				casilla=tablero.getCasilla(i, ultfila);
				compro=comprobaciertos(casilla);
				
				i--;
			}	
		}
		//FIN comprobacion por filas
			
		//comprobacion en diagonal
		if (terminada==false){
			compro=false;
			acertadas=0;
			i=ultfila;
			int j=ultcol;
				
			while(j<=ancho && i<=alto && compro==false){
				casilla=tablero.getCasilla(j,i);
				compro=comprobaciertos(casilla);
				
				i++;
				j++;	
			}	
		}
			
		if (terminada==false){
			i=ultfila-1;
			int j=ultcol-1;
			compro=false;
		   
			while(j>0 && i>0 &&compro==false){
				casilla=tablero.getCasilla(j,i);
				compro=comprobaciertos(casilla);
				
				i--;
				j--;
			}
		}
		
        if (terminada==false){
			compro=false;
			acertadas=0;
			i=ultfila;
			int j=ultcol;
			
			while(j>0 && i<=alto && compro==false){
				casilla=tablero.getCasilla(j,i);
				compro=comprobaciertos(casilla);
					
				i++;
				j--;	
			}	
		}
        
        if (terminada==false){
        	i=ultfila-1;
			compro=false;
			int j=ultcol+1;
			
			while(j<=ancho && i>0 &&compro==false){
				casilla=tablero.getCasilla(j,i);
				compro=comprobaciertos(casilla);
					
				i--;
				j++;	
			}
		}
          
        if(terminada==false){
           tablas=true;
           terminada=true;
           
           for(i=alto;i>0;i--){
        	   	for(int j=ancho;j>0;j--){
        	   		casilla=tablero.getCasilla(j,i);
        	   		if(casilla==Ficha.VACIA){
        	   			tablas=false;
        	   			terminada=false;
        	   			j=-1;
        	   			i=-1;
        	   		}
        	   	}
           }
            	
        }
      //FIN comprobacion en diagonal  
			
        if (terminada==true){	//en el caso de que acabe la partida
        	end=true;	
        }
        
        return terminada;
	}
	
	public boolean comprobaciertos(Ficha casilla){ //comprueba si coinciden las casillas (a esta funcion la llama isTerminada) 
		Ficha turntemp;
		
		if (turno == Ficha.BLANCA){
			turntemp=Ficha.NEGRA;
		}
		else { 
			 turntemp=Ficha.BLANCA; 
		}
		
		boolean finalizado =false;
		
		if(casilla==turntemp){
			
			acertadas++;
			if(acertadas==4){
				terminada=true;
				finalizado=true;
			}
		}
		else{
			finalizado=true;
		}
		
		return finalizado;
	}
	
	public void reset(){
		tablero= new Tablero(7,6);
		undoStack=new int[10]; //crea un nuevo "buffer"
		numUndo=0;	//Inicializa los valores necesarios para inicializar la partida
		jugbuffer=0;
		turno=Ficha.BLANCA;	//La partida siempre la empiezan las blancas
	}
	
	public boolean undo(){	//Deshace el ultimo mov.

		boolean ok=false;
		
		if(jugbuffer>0){	//Si hay algun movimiento que deshacer
			int i=0;
			Ficha casilla;
			if(numUndo==0){
				numUndo=11; }
			else{
				numUndo--;
			}
			
			int j=undoStack[numUndo]; //j sera la columna del ultimo mov.
		
			while(i<alto){
				casilla=tablero.getCasilla(j, i+1);	//da a casilla el valor de la ficha
			
				if(casilla!=Ficha.VACIA){	//Si la casilla esta ocupada	
					tablero.setCasilla(j, i+1, Ficha.VACIA);	//Pinta la casilla de color a vacia
					i=alto;	
					ok=true;
					jugbuffer--;	
				}
				i++;
			}
		}
		
		if (ok){	//Si deshace el mov. cambia el turno
			 if (turno == Ficha.BLANCA){
					turno=Ficha.NEGRA;
				 }
			 else { 
				 turno=Ficha.BLANCA; 
			 }
		}
		
		return ok ;	 
	}
	
}
