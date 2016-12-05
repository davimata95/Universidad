package tp.pr5.logica;

public abstract class ReglasBasicas implements ReglasJuego{

	protected boolean terminada;
	protected Ficha turno=Ficha.BLANCA;
	protected int ancho;
	protected int alto;
	protected int acertadas;
	
	@Override
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t) {
		int ultfila;
		int ultcol;
		
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
	
	
	protected boolean comprobaciertos(Ficha casilla){  

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

	


	@Override
	public Ficha jugadorInicial() {
		return Ficha.BLANCA;		
	}

	@Override
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




}
