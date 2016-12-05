package tp.pr5.logica;

public class MovimientoReversi extends Movimiento{

	private boolean end;
	
	private int col;
	private Ficha turno;
	private int fila;

	private int ultcol;
	private int ultfila;
	
	private int[] arrayColumnas;
	private int[] arrayFilas;
	private Ficha[] arrayFichasVolteadas;

	private int contadorReversi;
	
	public MovimientoReversi(int columna,int fila,Ficha color) {
		super(color);

		this.col=columna;
		this.turno=color;
		this.fila=fila;		
		
		//Crear un array con posiciones para hacer el undo con mas facilidad.
		 this.arrayColumnas=new int[100];
		 this.arrayFilas=new int[100];
		 this.arrayFichasVolteadas = new Ficha[100];
		 this.contadorReversi = 0;
	}

	@Override
	public boolean ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {

		int alto=tab.getAlto();
		int ancho=tab.getAncho();
				
		boolean valido=false;
		
		//Si la partida no acaba
		
		if(end==false){
						
			//Si la columna introducida es correcta
			if (col >0 && col <= ancho){
				//Si la fila introducida es correcta
				if (fila>0 && fila<=alto){
					//Si la casilla esta vacia
					if(Ficha.VACIA==tab.getCasilla(col, fila)){
						//Si hay algun posible movimiento
						if (ReglasReversi.movimientoCorrecto(col, fila, turno, tab)>0){
							//Pasamos I, pero nos lo devuelve sumado, e incluye la posicion en el array para el undo.
							
							int izq = ReglasReversi.compruebaIzq(col, fila, turno, tab);
							//Si hay fichas que revertir
							if (izq>0){
								//Añade para el undo y pinta en el tablero
								for (int j = 0; j<izq;j++){
									realiza (col+(j+1), fila, turno, tab);						
								}
							}
							
							int drch = ReglasReversi.compruebaDrch(col, fila, turno, tab);
							//Si hay fichas que revertir
							if (drch>0){
								//Añade para el undo y pinta en el tablero
								for (int j = 0; j<drch;j++){								
									realiza(col-(j+1), fila, turno, tab);
								}
							}
							
							int arriba = ReglasReversi.compruebaArriba(col, fila, turno, tab);
							//Si hay fichas que revertir
							if (arriba>0){
								//Añade para el undo y pinta en el tablero
								for (int j = 0; j<arriba;j++){									
									realiza(col, fila+(j+1), turno, tab);
								}
							}
							
							int abajo = ReglasReversi.compruebaAbajo(col, fila, turno, tab);
							//Si hay fichas que revertir
							if (abajo>0){
								//Añade para el undo y pinta en el tablero
								for (int j = 0; j<abajo;j++){									
									realiza (col,fila-(j+1), turno, tab);									
								}
							}
							
							//DIAGONALES
							
							int diagArribaDrch = ReglasReversi.compruebaDiagArribaDrch(col, fila, turno, tab);					
							
							//Si hay fichas que revertir
							if (diagArribaDrch>0){
								//Añade para el undo y pinta en el tablero
								for (int j = 0; j<diagArribaDrch;j++){									
									realiza (col-(j+1), fila+(j+1), turno, tab);									
								}
							}
							
							int diagArribaIzq = ReglasReversi.compruebaDiagArribaIzq(col, fila, turno, tab);
							if (diagArribaIzq>0){
								//Añade para el undo y pinta en el tablero
								for (int j = 0; j<diagArribaIzq;j++){									
									realiza (col+(j+1), fila+(j+1), turno, tab);						
								}
							}
							
							
							int diagAbajoDrch = ReglasReversi.compruebaDiagAbajoDrch(col, fila, turno, tab);
							if (diagAbajoDrch>0){
								//Añade para el undo y pinta en el tablero
								for (int j = 0; j<diagAbajoDrch;j++){									
									realiza (col-(j+1), fila-(j+1), turno, tab);									
								}
							}
							
							int diagAbajoIzq = ReglasReversi.compruebaDiagAbajoIzq(col, fila, turno, tab);
							if (diagAbajoIzq>0){
								//Añade para el undo y pinta en el tablero
								for (int j = 0; j<diagAbajoIzq;j++){									
									realiza (col+(j+1), fila-(j+1), turno, tab);									
								}
							}
							
							//Si entra aqui esque es valido.
							valido=true;
							//Pinta nuestra casilla seleccionada
							tab.setCasilla(col, fila, turno);
						}
						else{
							throw new MovimientoInvalido("Cailla invalida.");
						}
					}
					else{
						throw new MovimientoInvalido("Casilla ocupada.");
					}
				}
				else{
					throw new MovimientoInvalido("Columna incorrecta. Debe estar entre 1 y 8.");
				}
			}
			else{
				throw new MovimientoInvalido("Fila incorrecta. Debe estar entre 1 y 8.");
			}
			
		}
			
		return valido;
	}


	@Override
	public void undo(Tablero tab) {

		//Pinta las casillas a vacio
		for (int i =0; i < contadorReversi; i++){
			tab.setCasilla(arrayColumnas[i], arrayFilas[i], arrayFichasVolteadas[i]); 
		}
		
		tab.setCasilla(col, fila, Ficha.VACIA);
	}

	@Override
	public int getultfil() {
		
		return ultfila;
	}

	@Override
	public int getultcol() {
		// TODO Auto-generated method stub
		return ultcol;
	}

	public void realiza (int nCol, int nFil, Ficha turno, Tablero tab){
		
		Ficha voltea = tab.getCasilla(nCol, nFil);
		arrayFichasVolteadas[contadorReversi]=voltea;
		tab.setCasilla(nCol, nFil, turno);
		//Guarda las posiciones
		arrayColumnas[contadorReversi] = nCol;
		arrayFilas[contadorReversi] = nFil;
		contadorReversi++;
	}
}
