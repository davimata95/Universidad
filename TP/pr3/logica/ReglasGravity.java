package tp.pr3.logica;

public class ReglasGravity implements ReglasJuego{

	private Tablero tablero;
	private Ficha turno=Ficha.BLANCA;
	private boolean terminada;
	private int ancho;
	private int alto;
	private int ultcol;
	private int ultfila;
	private int acertadas;
	private boolean tablas;
	
	public ReglasGravity(int numCols, int numFilas){
		this.ancho=numCols;
		this.alto=numFilas;
		
	}
	
	@Override
	//Comprueba si hay ganador
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t) {
		terminada=false;
		boolean compro=false;
		Ficha casilla;
		Ficha vencedor=Ficha.VACIA;
		
		if(ultimoMovimiento!=null){
			
			turno=ultimoMovimiento.getJugador();
			ultcol=ultimoMovimiento.getultcol();
			ultfila=ultimoMovimiento.getultfil();
		
			acertadas=0;
			int i=ultfila;
			
			// comprobacion por columna
			
			while(i<=alto && compro==false){
				casilla=t.getCasilla(ultcol, i);
				compro=comprobaciertos(casilla);
				
				i++;
			}
			
			if(terminada==false){
				compro=false;
				i=ultfila-1;
			
				while(i>0 && compro==false){
					casilla=t.getCasilla(ultcol, i);
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
					casilla=t.getCasilla(i, ultfila);	
					compro=comprobaciertos(casilla);
				
					i++;
				}
			}
		
			if (terminada==false){
				i=ultcol-1;
				compro=false;
			
				while(i>0 && compro==false){	
					casilla=t.getCasilla(i, ultfila);
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
					casilla=t.getCasilla(j,i);
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
					casilla=t.getCasilla(j,i);
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
					casilla=t.getCasilla(j,i);
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
					casilla=t.getCasilla(j,i);
					compro=comprobaciertos(casilla);
					
					i--;
					j++;	
				}
			}	

			if(terminada==true){	
				vencedor=turno;
			}
			else{
				vencedor=Ficha.VACIA;
			}
		}
		
		return vencedor;	
	}

	//Comprueba si hay 4 aciertos seguidos
	public boolean comprobaciertos(Ficha casilla){  

		boolean finalizado =false;
		
		if(casilla==turno){
			
			acertadas++;
			//Si hay 4 seguidos dira que la partida acaba
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
	
	@Override
	//Inicia el tablero con las dimensiones
	public Tablero iniciaTablero() {
		tablero= new Tablero(ancho,alto);
		return tablero;
	}

	@Override
	public Ficha jugadorInicial() {
		// TODO Auto-generated method stub
		return Ficha.BLANCA;
	}

	@Override
	//Cambia el turno
	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t) {
		Ficha siguiente;
		if(ultimoEnPoner==Ficha.BLANCA){
			siguiente=Ficha.NEGRA;
		}
		else{
			siguiente=Ficha.BLANCA;
		}
		
		return siguiente;
	} 

	@Override
	
	//Comprueba si hay tablas
	public boolean tablas(Ficha ultimoEnPoner, Tablero t) {
		Ficha casilla;
		ancho=t.getAncho();
		alto=t.getAlto();
		tablas=true;
          
        for(int i=alto;i>0;i--){
        	for(int j=ancho;j>0;j--){
        		casilla=t.getCasilla(j,i);
        		if(casilla==Ficha.VACIA){
      	   			tablas=false;
      	   			
      	   			j=-1;
      	   			i=-1;
      	   		}
      	   	}
         }
		
        return tablas;
	}
	

}
