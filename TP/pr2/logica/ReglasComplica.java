package tp.pr2.logica;

public class ReglasComplica implements ReglasJuego{
	
	private Tablero tablero;
	private Ficha turnotemp;
	private boolean terminada;
	private int ancho;
	private int alto;
	private int ultcol;
	private int ultfila;
	private int acertadas;

	public ReglasComplica() {
			this.ancho=4;
			this.alto=7;
	}
	
	//Permite averiguar si en la partida ya tenemos un ganador o no.
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t){
		terminada=false;
		boolean compro=false;
		Ficha casilla;
		Ficha vencedor=Ficha.VACIA;
		if(ultimoMovimiento!=null){
			ultcol=ultimoMovimiento.getultcol();
			ultfila=ultimoMovimiento.getultfil();	
			int i;
			int k=ultfila;
		
			// comprobacion por columna
			while(k<=alto && terminada==false){
				acertadas=0;
				compro=false;
				turnotemp=t.getCasilla(ultcol, k);
				i=k;
				if(turnotemp!= vencedor){
					while(i<=alto && compro==false){
						casilla=t.getCasilla(ultcol, i);
						compro=comprobaciertos(casilla);
			
						i++;
					}
		
					if(terminada==false){
						compro=false;
						i=k-1;
			
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
							casilla=t.getCasilla(i, k);	
							compro=comprobaciertos(casilla);
				
							i++;
						}
					}
		
					if (terminada==false){
						i=ultcol-1;
						compro=false;
			
						while(i>0 && compro==false){	
							casilla=t.getCasilla(i, k);
							compro=comprobaciertos(casilla);
				
							i--;
						}	
					}
					//FIN comprobacion por filas
			
					//comprobacion en diagonal
					if (terminada==false){
						compro=false;
						acertadas=0;
						i=k;
						int j=ultcol;
				
						while(j<=ancho && i<=alto && compro==false){
							casilla=t.getCasilla(j,i);
							compro=comprobaciertos(casilla);
				
							i++;
							j++;	
						}	
					}
			
					if (terminada==false){
						i=k-1;
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
						i=k;
						int j=ultcol;
			
						while(j>0 && i<=alto && compro==false){
							casilla=t.getCasilla(j,i);
							compro=comprobaciertos(casilla);
				
							i++;
							j--;	
						}	
					}
        
					if (terminada==false){
						i=k-1;
						compro=false;
						int j=ultcol+1;
			
						while(j<=ancho && i>0 &&compro==false){
							casilla=t.getCasilla(j,i);
							compro=comprobaciertos(casilla);
					
							i--;
							j++;	
						}
					}
				}
       
				if(terminada==true){
					if(vencedor==Ficha.VACIA){
						vencedor=turnotemp;
						terminada=false;
					}
					else{
						vencedor=Ficha.VACIA;
					}
				}
				k++;
			}
		}
	
	return vencedor;	
	}
	
	public boolean comprobaciertos(Ficha casilla){  

		boolean finalizado =false;
		
		if(casilla==turnotemp){
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
		if(ultimoEnPoner==Ficha.BLANCA)	siguiente=Ficha.NEGRA;
		else							siguiente=Ficha.BLANCA;

		return siguiente;
	}
			
	//Devuelve true si, con el estado del tablero dado, la partida ha terminado en tablas.
	public boolean tablas(Ficha ultimoEnPoner, Tablero t){
		//no hay tablas
		return false;
	}
}
