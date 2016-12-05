package tp.pr3.logica;

public class MovimientoGravity extends Movimiento{

	private Tablero tablero;
	private Ficha turno;
	private boolean end;
	private int ancho;
	private int alto;
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
		
		tablero=tab;
		alto=tablero.getAlto();
		ancho=tablero.getAncho();
		
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
					if(Ficha.VACIA==tablero.getCasilla(col, fila)){
						
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
									MovimientoDerecha();

								}
								//Si por el contrario esta mas cerca de abajo que de la derecha
								else if(derecha>abajo){
									//movimiento abajo
									MovimientoAbajo();
								}
							}
							//Si por el contrario esta mas cerca de arriba que de abajo
							else if(arriba<abajo){
								
								//Si esta mas cerca de la derecha que de arriba
								if(derecha<arriba){
									//movimiento derecha
									MovimientoDerecha();
									
								}
								//Si por el contrario esta mas cerca de arriba que de la derecha
								else if(derecha>arriba){
									//movimiento arrriba
									MovimientoArriba();
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
									MovimientoIzquierda();
								}
								//Si esta mas cerca de abajo que de la izquierda
								else if(izquierda>abajo){
								//movimiento abajo
									MovimientoAbajo();
								}
							}
							//Si por el contrario, esta mas cerca de arriba que de abajo
							else if(arriba<abajo){
								//Si esta mas cerca de la izquierda que de arriba
								if(izquierda<arriba){
									//movimiento izquierda
									MovimientoIzquierda();								
								}
								//Si por el contrario esta mas cerca de arriba que de la izquierda
								else if(izquierda>arriba){
									////movimiento arriba
									MovimientoArriba();
								}
							}
						}
						
						if(end==false){

							//Si coinciden los siguientes casos
							if(arriba==abajo && derecha==izquierda && arriba==derecha){
							
								//pinta la casilla y end = true
								tablero.setCasilla(col, fila, turno);
								ultcol=col;
								ultfila=fila;
								end=true;
							}
							else{
						
								if(end==false){
									if(abajo==arriba && derecha==izquierda){
										
										tablero.setCasilla(col, fila, turno);
										ultcol=col;
										ultfila=fila;
										end=true;	
										
									}else{
									//Si abajo es igual a arriba
										if(abajo==arriba){
											//Si la distancia es menor hacia la izquierda
											if(derecha>izquierda){
											//movimiento izquierda
												MovimientoIzquierda();
											}
										//Si la distancia es menor hacia la derecha
											else{
											//movimientoderecha
												MovimientoDerecha();
											}
										}
									//Si la distancia hacia los lados es igual 
										if(derecha==izquierda){
										//Si la distancia es menor hacia abajo
											if(abajo<arriba){
												MovimientoAbajo();
											}	
										//Si la distancia es menor hacia arriba
											else{
												MovimientoArriba();
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
											DiagonalIzquiArriba();
										}
									}
									//Diagonal superior derecha
									if(arriba==derecha){
										//Decide si ir hacia "arriba" o hacia "abajo"
										if(arriba+derecha<abajo+izquierda){
											DiagonalDereArriba();
										}
									}
									//Diagonal inferior izquierda
									if(abajo==izquierda){
										//Decide si ir hacia "arriba" o hacia "abajo"
										if(abajo+izquierda<arriba+derecha){
											DiagonalIzquiAbajo();
										}
									}
									//Diagonal inferior derecha
									if(abajo==derecha){
										//Decide si ir hacia "arriba" o hacia "abajo"
										if(abajo+derecha<arriba+izquierda){
											DiagonalDereAbajo();
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
	private void DiagonalIzquiArriba(){
		int x=col;
		int y=fila;
		
		//Mientras y sea menor o igual al alto y x sea mayor a 0
		while(y<=alto && x>0 && end==false){
			
			//Si la casilla no esta vacia
			if(Ficha.VACIA==tablero.getCasilla(x, y)){
				//Si y es igual a alto y x es igual a 1
				if(y==alto && x==1){
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
	}
	
	//Realizael movimiento con la gravedad hacia la diogonal inferior izquierda
	private void DiagonalIzquiAbajo(){
		
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
	}
	
	//Realizael movimiento con la gravedad hacia la diogonal superior derecha
	private void DiagonalDereArriba(){
		
		int x=col;
		int y=fila;
		
		//Mientras y sea menor o igual al alto y x al ancho
		while(y<=alto && x<=ancho && end==false){
			
			//Si la casilla esta vacia
			if(tablero.getCasilla(x, y) == Ficha.VACIA){
				//Si y es igual al alto y x es igual al ancho
				if(y==alto && x==ancho){
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
	}
	
	//Realizael movimiento con la gravedad hacia la diogonal inferioe derecha
	private void DiagonalDereAbajo(){
		
		int x=col;
		int y=fila;
		
		//Mientras y sea mayor que 0 y x menor o igual que el ancho
		while(y>0 && x<=ancho && end==false){
			//Si la casilla esta vacia
			if(tablero.getCasilla(x, y) == Ficha.VACIA){
				//Si y es igual a 1 y x es igual al ancho
				if(y == 1 && x == ancho){
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
	}
	
	//Realizael movimiento con la gravedad hacia abajo
	private void MovimientoAbajo(){
		
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
	}
	
	//Realizael movimiento con la gravedad hacia la derecha
	private void MovimientoDerecha(){
		
		int x=col;
		
		//Mientras x sea menor o igual al ancho
		while(x<=ancho && end==false){
			//Si la casilla esta vacia
			if(tablero.getCasilla(x, fila) == Ficha.VACIA){
				//Si x es igual al ancho
				if(x==ancho){
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
	}

	//Realizael movimiento con la gravedad hacia la izquierda
	private void MovimientoIzquierda(){
		
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
	}
	
	//Realizael movimiento con la gravedad hacia arriba
	private void MovimientoArriba(){
		
		int y=fila;
		
		//Mientras y sea menor o igual que el alto
		while(y<=alto && end==false){
			//Si la casilla esta vacia
			if(tablero.getCasilla(col, y) == Ficha.VACIA){
				//Si y es igual al alto
				if(y==alto){
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
	}

	@Override
	
	//Deshace el movimiento 
	public void undo(Tablero tab) {
		// TODO Auto-generated method stub
		
		tablero.setCasilla(ultcol, ultfila, Ficha.VACIA);
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
