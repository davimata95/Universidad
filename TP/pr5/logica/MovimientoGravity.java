package tp.pr5.logica;

public class MovimientoGravity extends Movimiento{

	private Ficha turno;
	private boolean end;

	private int ultcol;
	private int ultfila;
	private int col;
	private int fila;
	
	public MovimientoGravity(int columna,int fila,Ficha color){
		
		super(color);

		this.col=columna;
		this.turno=color;
		this.fila=fila;
	}
	
	
	@Override
	
	//Ejecuta el movimiento
	public boolean ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {

		int alto=tab.getAlto();
		int ancho=tab.getAncho();
		
		int izquierda;
		int derecha;
		int arriba;
		int abajo;
		
		//Si la partida no acaba
		if(end==false){

			//Si la columna introducida es correcta
			if (col >0 && col <= ancho){
				//Si la fila introducida es correcta
				if (fila>0 && fila<=alto){
					//Si la casilla esta vacia
					if(Ficha.VACIA==tab.getCasilla(col, fila)){
						
						//calcula la distancia de los bordes 
						derecha=ancho-(col);
						izquierda=col-1;
						arriba=alto-(fila);
						abajo=fila-1;

						//Si esta mas cerca de la derecha que de la izquierda
						if(derecha<izquierda){
							
							//Si esta mas cerca de abajo que de arriba
							if(arriba>abajo){
								
								//Si esta mas cerca de la derecha que de abajo
								if(derecha<abajo){
									//movimiento derecha
									end=MovimientoDerecha(tab, end);

								}
								//Si por el contrario esta mas cerca de abajo que de la derecha
								else if(derecha>abajo){
									//movimiento abajo
									end=MovimientoAbajo(tab, end);
								}
							}
							//Si por el contrario esta mas cerca de arriba que de abajo
							else if(arriba<abajo){
								
								//Si esta mas cerca de la derecha que de arriba
								if(derecha<arriba){
									//movimiento derecha
									end=MovimientoDerecha(tab, end);
									
								}
								//Si por el contrario esta mas cerca de arriba que de la derecha
								else if(derecha>arriba){
									//movimiento arrriba
									end=MovimientoArriba(tab, end);
								}
							}
						}
						//Si por el contrario esta mas cerca de la izquierda que de la derecha
						else if(derecha>izquierda){
							//Si esta mas cerca de abajo que de arriba
							if(arriba>abajo){
								//Si esta mas cerca de la izquierda que de abajo
								if(izquierda<abajo){
									//movimiento izquierda
									end=MovimientoIzquierda(tab, end);
								}
								//Si esta mas cerca de abajo que de la izquierda
								else if(izquierda>abajo){
								//movimiento abajo
									end=MovimientoAbajo(tab, end);
								}
							}
							//Si por el contrario, esta mas cerca de arriba que de abajo
							else if(arriba<abajo){
								//Si esta mas cerca de la izquierda que de arriba
								if(izquierda<arriba){
									//movimiento izquierda
									end=MovimientoIzquierda(tab, end);								
								}
								//Si por el contrario esta mas cerca de arriba que de la izquierda
								else if(izquierda>arriba){
									////movimiento arriba
									end=MovimientoArriba(tab, end);
								}
							}
						}
						
						if(end==false){

							//Si coinciden los siguientes casos
							if(arriba==abajo && derecha==izquierda && arriba==derecha){
							
								//pinta la casilla y end = true
								tab.setCasilla(col, fila, turno);
								ultcol=col;
								ultfila=fila;
								end=true;
							}
							else{
						
								if(end==false){
									if(abajo==arriba && derecha==izquierda){
										
										tab.setCasilla(col, fila, turno);
										ultcol=col;
										ultfila=fila;
										end=true;	
										
									}else{
									//Si abajo es igual a arriba
										if(abajo==arriba){
											//Si la distancia es menor hacia la izquierda
											if(derecha>izquierda){
											//movimiento izquierda
												end=MovimientoIzquierda(tab, end);
											}
										//Si la distancia es menor hacia la derecha
											else{
											//movimientoderecha
												end=MovimientoDerecha(tab, end);
											}
										}
									//Si la distancia hacia los lados es igual 
										if(derecha==izquierda){
										//Si la distancia es menor hacia abajo
											if(abajo<arriba){
												end=MovimientoAbajo(tab, end);
											}	
										//Si la distancia es menor hacia arriba
											else{
												end=MovimientoArriba(tab, end);
											}
										}		
									}	
								}
						
						//////DIAGONALES//////

								if(end==false){

									//Diagonal superior izquierda
									if(arriba==izquierda){
										//Decide si ir hacia "arriba" o hacia "abajo"
										if(arriba+izquierda<abajo+derecha){
											end=DiagonalIzquiArriba(tab, end);
										}
									}
									//Diagonal superior derecha
									if(arriba==derecha){
										//Decide si ir hacia "arriba" o hacia "abajo"
										if(arriba+derecha<abajo+izquierda){
											end=DiagonalDereArriba(tab, end);
										}
									}
									//Diagonal inferior izquierda
									if(abajo==izquierda){
										//Decide si ir hacia "arriba" o hacia "abajo"
										if(abajo+izquierda<arriba+derecha){
											end=DiagonalIzquiAbajo(tab, end);
										}
									}
									//Diagonal inferior derecha
									if(abajo==derecha){
										//Decide si ir hacia "arriba" o hacia "abajo"
										if(abajo+derecha<arriba+izquierda){
											end=DiagonalDereAbajo(tab, end);
										}
									}	
								}
							}
	
						}
					//Saltan las excepciones en caso de que algo falle
					} else throw new MovimientoInvalido("Casilla ocupada.");
				}else throw new MovimientoInvalido("Posición incorrecta.");
			}else throw new MovimientoInvalido("Posición incorrecta.");//Si no es correcta la columna devolvera false y saltara el aviso de mov. invalido
		}
		return true;
	}
	
	//Realizael movimiento con la gravedad hacia la diogonal superior izquierda
	private boolean DiagonalIzquiArriba(Tablero tablero, boolean end){
		int x=col;
		int y=fila;
		
		//Mientras y sea menor o igual al alto y x sea mayor a 0
		while(y<=tablero.getAlto() && x>0 && end==false){
			
			//Si la casilla no esta vacia
			if(Ficha.VACIA==tablero.getCasilla(x, y)){
				//Si y es igual a alto y x es igual a 1
				if(y==tablero.getAlto() && x==1){
					tablero.setCasilla(x, y, turno);
					ultfila=y;
					ultcol=x;
					end=true;
				}
			}
			//Si la casilla NO esta vacia
			else{
				tablero.setCasilla(x+1, y-1, turno);
				ultfila=y-1;
				ultcol=x+1;
				end=true;
			}
			
			//Busca en la siguiente casilla diagonal
			y++;
			x--;
		}
		return end;
	}
	
	//Realizael movimiento con la gravedad hacia la diogonal inferior izquierda
	private boolean DiagonalIzquiAbajo(Tablero tablero, boolean end){
		
		int x=col;
		int y=fila;
		
		//Mientras x e y sean meyores a 0
		while(y>0 && x>0 && end==false){
			
			//Si la ficha esta vacia
			if(tablero.getCasilla(x, y) == Ficha.VACIA){
				//Si x e y son iguales a 1 
				if(y==1 && x==1){
					tablero.setCasilla(x, y, turno);
					ultfila=y;
					ultcol=x;
					end=true;
				}
			}
			//Si la casilla NO esta vacia
			else{
				
				tablero.setCasilla(x+1, y+1, turno);
				ultfila=y+1;
				ultcol=x+1;
				end=true;
			}
			
			//Busca en la siguiente casilla diagonal
			y--;
			x--;
		}		
		return end;
	}
	
	//Realizael movimiento con la gravedad hacia la diogonal superior derecha
	private boolean DiagonalDereArriba(Tablero tablero, boolean end){
		
		int x=col;
		int y=fila;
		
		//Mientras y sea menor o igual al alto y x al ancho
		while(y<=tablero.getAlto() && x<=tablero.getAncho() && end==false){
			
			//Si la casilla esta vacia
			if(tablero.getCasilla(x, y) == Ficha.VACIA){
				//Si y es igual al alto y x es igual al ancho
				if(y==tablero.getAlto() && x==tablero.getAncho()){
					tablero.setCasilla(x, y, turno);
					ultfila=y;
					ultcol=x;
					end=true;
				}
			}
			//Si la casilla NO esta vacia
			else{		
				tablero.setCasilla(x-1, y-1, turno);
				ultfila=y-1;
				ultcol=x-1;
				end=true;
			}
			
			//Busca en la siguiente casilla diagonal
			y++;
			x++;
		}
		return end;
	}
	
	//Realizael movimiento con la gravedad hacia la diogonal inferioe derecha
	private boolean DiagonalDereAbajo(Tablero tablero, boolean end){
		
		int x=col;
		int y=fila;
		
		//Mientras y sea mayor que 0 y x menor o igual que el ancho
		while(y>0 && x<=tablero.getAncho() && end==false){
			//Si la casilla esta vacia
			if(tablero.getCasilla(x, y) == Ficha.VACIA){
				//Si y es igual a 1 y x es igual al ancho
				if(y == 1 && x == tablero.getAncho()){
					tablero.setCasilla(x, y, turno);
					ultfila=y;
					ultcol=x;
					end=true;
				}
			}
			//Si la casilla NO esta vacia
			else{
				tablero.setCasilla(x-1, y+1, turno);
				ultfila=y+1;
				ultcol=x-1;
				end=true;
			}
			//Busca en la siguiente casilla diagonal
			y--;
			x++;
		}
		return end;
	}
	
	//Realizael movimiento con la gravedad hacia abajo
	private boolean MovimientoAbajo(Tablero tablero, boolean end){
		
		int y=fila;
		
		//Mientras y sea mayor que 0
		while(y>0 && end==false){
			//Si la casilla esta vacia
			if(tablero.getCasilla(col, y) == Ficha.VACIA){
				//Si y es igual a 1
				if(y==1){
					tablero.setCasilla(col, y, turno);
					ultfila=y;
					ultcol=col;
					end=true;
				}
				y--;
				
			}
			//Si la casilla NO esta vacia
			else{
				tablero.setCasilla(col, y+1, turno);
				ultfila=y+1;
				ultcol=col;
				y--;
				end=true;
			}
		}
		end=true;
		return end;
	}
	
	//Realizael movimiento con la gravedad hacia la derecha
	private boolean MovimientoDerecha(Tablero tablero, boolean end){
		
		int x=col;
		
		//Mientras x sea menor o igual al ancho
		while(x<=tablero.getAncho() && end==false){
			//Si la casilla esta vacia
			if(tablero.getCasilla(x, fila) == Ficha.VACIA){
				//Si x es igual al ancho
				if(x==tablero.getAncho()){
					tablero.setCasilla(x, fila, turno);
					ultfila=fila;
					ultcol=x;
					end=true;
				}
				
				x++;
			}
			//Si la casilla NO esta vacia
			else{
				tablero.setCasilla(x-1, fila, turno);	
				ultfila=fila;
				ultcol=x-1;
				x++;
				end=true;
			}
		}
		end=true;
		return end;
	}

	//Realizael movimiento con la gravedad hacia la izquierda
	private boolean MovimientoIzquierda(Tablero tablero, boolean end){
		
		int x=col;
		
		//Mientras x sea mayor que 0
		while(x>0 && end==false){
			//Si la casilla esta vacia
			if(tablero.getCasilla(x, fila) == Ficha.VACIA){
				//Si x es igual a 1
				if(x==1){
					tablero.setCasilla(x, fila, turno);
					ultfila=fila;
					ultcol=x;
					end=true;
				}
				x--;
			}
			//Si la casilla NO esta vacia
			else{
				tablero.setCasilla(x+1, fila, turno);
				ultfila=fila;
				ultcol=x+1;
				x--;
				end=true;
			}
		}
		end=true;
		return end;
	}
	
	//Realizael movimiento con la gravedad hacia arriba
	private boolean MovimientoArriba(Tablero tablero, boolean end){
		
		int y=fila;
		
		//Mientras y sea menor o igual que el alto
		while(y<=tablero.getAlto() && end==false){
			//Si la casilla esta vacia
			if(tablero.getCasilla(col, y) == Ficha.VACIA){
				//Si y es igual al alto
				if(y==tablero.getAlto()){
					tablero.setCasilla(col, y, turno);
					ultfila=y;
					ultcol=col;
					end=true;
				}
				y++;
			}
			//Si por el contrario la casilla NO esta vacia
			else{
				tablero.setCasilla(col, y-1, turno);
				ultfila=y-1;
				ultcol=col;
				y++;
				end=true;
			}
		}
		end=true;	
		return end;
	}

	@Override
	
	//Deshace el movimiento 
	public void undo(Tablero tab) {
		// TODO Auto-generated method stub
		
		tab.setCasilla(ultcol, ultfila, Ficha.VACIA);
	}

	@Override
	
	//devuelve la ultima fila
	public int getultfil() {
		// TODO Auto-generated method stub
		return ultfila;
	}

	@Override
	
	//devuelve la ultima columna
	public int getultcol() {
		// TODO Auto-generated method stub
		return ultcol;
	}

}
