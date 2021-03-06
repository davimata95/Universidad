package tp.pr3.logica;

public class ReglasConecta4 implements ReglasJuego  {

	private Tablero tablero;
	private Ficha turno=Ficha.BLANCA;
	private boolean terminada;
	private int ancho;
	private int alto;
	private boolean tablas;
	private int ultcol;
	private int ultfila;
	private int acertadas;
	
	public ReglasConecta4() {
		this.ancho=7;
		this.alto=6;
	}
	
	//Permite averiguar si en la partida ya tenemos un ganador o no.
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t){
		
		terminada=false;
		boolean compro=false;
		Ficha casilla;
		Ficha vencedor=Ficha.VACIA;
		
		//Si se realizo movimiento
		if(ultimoMovimiento!=null){
			
			turno=ultimoMovimiento.getJugador();
			ultcol=ultimoMovimiento.getultcol();
			ultfila=ultimoMovimiento.getultfil();
		
			acertadas=0;
			int i=ultfila;
			
			// comprobacion por columna
			
			//comprobacion hacia derecha
			while(i<=alto && compro==false){
				casilla=t.getCasilla(ultcol, i);
				compro=comprobaciertos(casilla);
				
				i++;
			}
			
			if(terminada==false){
				compro=false;
				i=ultfila-1;
			
				//comprobacion hacia izquierda
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
			
				//comprobacion hacia arriba
				while(i<=ancho && compro==false){
					casilla=t.getCasilla(i, ultfila);	
					compro=comprobaciertos(casilla);
				
					i++;
				}
			}
		
			if (terminada==false){
				i=ultcol-1;
				compro=false;
			
				//comprobacion hacia abajo
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
				
				//comprobacion por diagonal hacia arriba derecha
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
		   
				//comprobacion por diagonal hacia abajo izquierda
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
			
				//comprobacion por diagonal hacia arriba izquierda
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
				
				//Comprueba la diagonal hacia abajo derecha
				while(j<=ancho && i>0 &&compro==false){
					casilla=t.getCasilla(j,i);
					compro=comprobaciertos(casilla);
					
					i--;
					j++;	
				}
			}	

			//Si la partida acaba otorga el vencedor
			if(terminada==true){	
				vencedor=turno;
			}
			else{
				vencedor=Ficha.VACIA;
			}
		}
		
		return vencedor;	
	}
	
	//comprueba si hay 4 fichas iguales seguidas 
	public boolean comprobaciertos(Ficha casilla){  

		boolean finalizado =false;
		
		//Comprueba si hay 4 seguidas del mismo color
		if(casilla==turno){
			
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
		
	//Construye el tablero que hay que utilizar para la partida, según las reglas del juego.
	public Tablero iniciaTablero() {
		
		tablero= new Tablero(ancho,alto);
		return tablero;
	}
		
	//Devuelve el color del jugador que comienza la partida.
	public Ficha jugadorInicial() {
		return Ficha.BLANCA;		
	}
		
	//Devuelve el color del jugador al que le toca poner.
	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t){
		
		Ficha siguiente;
		if(ultimoEnPoner==Ficha.BLANCA){
			siguiente=Ficha.NEGRA;
		}
		else {
			siguiente=Ficha.BLANCA;
		}
		
		return siguiente;
	}
		
	//Devuelve true si, con el estado del tablero dado, la partida ha terminado en tablas.
	public boolean tablas(Ficha ultimoEnPoner, Tablero t){
		
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
