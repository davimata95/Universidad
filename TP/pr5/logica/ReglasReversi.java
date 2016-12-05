package tp.pr5.logica;

public class ReglasReversi implements ReglasJuego {

	
	private int ancho;
	private int alto;
	private final TipoJuego juego = TipoJuego.REVERSI;

	public ReglasReversi(){
		
		this.ancho=8;
		this.alto=8;
	}

	@Override
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t) {
		
		Ficha ganador = Ficha.VACIA;
		int fichasBlancas = 0;
		int fichasNegras = 0;
		
		//Comprobara si hay alguna casilla que no este ocupada 
		//De estar completo sumara las fichas para saber el ganador
		boolean lleno= true;	
		
		for (int i = 1; i< alto+1; i++){
			for(int j = 1; j< ancho+1; j++){
				
				Ficha fichaComprobar = t.getCasilla(i, j);
				
				if (fichaComprobar == Ficha.BLANCA){
					fichasBlancas++;

				}
				else if (fichaComprobar == Ficha.NEGRA){
					fichasNegras ++;
				}
				else {
					lleno=false;
				}
			}
		}
		
		if (lleno){		
			if (fichasBlancas>fichasNegras){
				ganador=Ficha.BLANCA;
			}
			else{
				ganador=Ficha.NEGRA;
			}
		}
	
		return ganador;
	}

	@Override
	public Tablero iniciaTablero() {

		//crea el tablero vacio
		Tablero tablero =  new Tablero(ancho,alto);
		
		//pinta las casillas del medio
		tablero.setCasilla(4, 4, Ficha.BLANCA);
		tablero.setCasilla(4, 5, Ficha.NEGRA);
		tablero.setCasilla(5, 4, Ficha.NEGRA);
		tablero.setCasilla(5, 5, Ficha.BLANCA);
		
		return tablero;
	}

	@Override
	public Ficha jugadorInicial() {
		return Ficha.NEGRA ;
	}

	@Override
	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t) {
		
		Ficha siguiente;
		if(ultimoEnPoner==Ficha.BLANCA){
			siguiente=Ficha.NEGRA;
		}
		else {
			siguiente=Ficha.BLANCA;
		}
		
		return siguiente;
		
	}

	@Override
	public boolean tablas(Ficha ultimoEnPoner, Tablero t) {

		//Hay tablas cuando al rellenar todo el tablero hay mismas fichas de cada color
		
		boolean tablas = false;
		
		int fichasBlancas = 0;
		int fichasNegras = 0;
		
		for (int i = 0; i< alto; i++){
			for(int j = 0; j< ancho; j++){
			
				Ficha fichaComprobar = t.getCasilla(alto, ancho);
				
				if (fichaComprobar == Ficha.BLANCA){
					fichasBlancas++;
				}
				else{
					fichasNegras ++;
				}
			}
		}
		
		if (fichasBlancas==fichasNegras){
			tablas=true;
		}
		return tablas;
	}
	
	//Devolvera un int con las fichas que revertiria
	public static int movimientoCorrecto (int columna, int fila, Ficha colocar, Tablero t){
		
		int contador=0;
				
		//comprobaremos si hay movimientos hacia la izq
		contador = contador+compruebaIzq(columna, fila, colocar, t);
		
		//comprobaremos si hay movimientos hacia la drch
		contador = contador+compruebaDrch(columna, fila, colocar, t);
		
		//comprobaremos si hay movimientos hacia abajo
		contador = contador+compruebaAbajo(columna, fila, colocar, t);
		
		//comprobaremos si hay movimientos hacia la arriba
		contador = contador+compruebaArriba(columna, fila, colocar, t);
		
		//comprobaremos si hay movimientos DIAGONALES
		contador = contador+compruebaDiagArribaDrch(columna, fila, colocar, t);
		contador = contador+compruebaDiagArribaIzq(columna, fila, colocar, t);
		contador = contador+compruebaDiagAbajoDrch(columna, fila, colocar, t);
		contador = contador+compruebaDiagAbajoIzq(columna, fila, colocar, t);
		
		return contador;
	}
	
	//Comprube que hay casillas que voltear a su izquierda
	public static int compruebaIzq(int columna, int fila, Ficha colocar, Tablero t){
		
		Ficha contrario = contrario(colocar);
		
		int cont=0;
		boolean valido=false;
		
		//sino no hay fichas que comprobar, llega al borde
		if (columna<t.getAncho()){
			//Si la ficha de al lado es diferente seguira comprobando 
			if (t.getCasilla(columna+1, fila) == contrario){
			
				int i=1;
				//empieza a comprobar hasta que ficha cambia o llega al borde
				while (t.getCasilla(columna+i, fila)==contrario && columna+i <= t.getAncho()){
					if (t.getCasilla(columna+(i+1), fila)==colocar){
					valido=true;
					}
					i++;
					cont++;
				}
			}
		}
		int ret=0;
		if (valido){
			ret=cont;
		}
		return ret;
	}
	
	public static int compruebaDrch(int columna, int fila, Ficha colocar, Tablero t){
		
		Ficha contrario = contrario(colocar);
		int cont=0;
		boolean valido=false;
		
		if (columna>0){
			//Si la ficha de al lado es diferente
			if (t.getCasilla(columna-1, fila) == contrario){
				int i=1;
				//empieza a comprobar hasta que ficha cambia o llega al borde
				while (t.getCasilla(columna-i, fila)== contrario && columna-i >= 0){
					if (t.getCasilla(columna-(i+1), fila)==colocar){
						valido = true;
					}
					i++;
					cont++;
				}
			}
		}
		int ret=0;
		if (valido){
			ret=cont;
		}
		return ret;
	}
	
	public static int compruebaAbajo (int columna, int fila, Ficha colocar, Tablero t){
	
		Ficha contrario = contrario(colocar);
		int cont=0;
		boolean valido=false;
		
		if (fila>0){
			//Si la ficha de al lado es diferente
			if (t.getCasilla(columna, fila-1) == contrario){
				int i=1;
				//empieza a comprobar hasta que ficha cambia o llega al borde
				while (t.getCasilla(columna, fila-i)==contrario && fila-i >=0){
					if (t.getCasilla(columna, fila-(i+1))==colocar){
						valido = true;
					}
					i++;
					cont++;
				}
			}
		}
		int ret=0;
		if (valido){
			ret=cont;
		}
		return ret;
	}

	public static int compruebaArriba (int columna, int fila, Ficha colocar, Tablero t){
		
		Ficha contrario = contrario(colocar);
		int cont=0;
		boolean valido=false;

		if (fila<t.getAlto()){
		
			//Si la ficha de al lado es diferente
			if (t.getCasilla(columna, fila+1) == contrario){
				int i=1;
				//empieza a comprobar hasta que ficha cambia o llega al borde
				while (t.getCasilla(columna, fila+i)== contrario && fila+i <= t.getAlto()){
					if (t.getCasilla(columna, fila+(i+1))==colocar){
						valido = true;

					}
					i++;
					cont++;
				}
			}
		}
		int ret=0;
		if (valido){
			ret=cont;
		}
		return ret;
	}
	
	//Diagonales
	public static int compruebaDiagAbajoIzq (int columna, int fila, Ficha colocar, Tablero t){
		
		Ficha contrario = contrario(colocar);
		int cont=0;
		boolean valido=false;
		
		if ((columna<=t.getAncho()) && (fila<=t.getAlto())){
			//Si la ficha de al lado es diferente
			if (t.getCasilla(columna+1, fila-1) == contrario){
				int i=1;
				//empieza a comprobar hasta que ficha cambia o llega al borde
				while (t.getCasilla(columna+i, fila-i) == contrario && columna+i <= t.getAncho() && fila-i >= 0){
					if (t.getCasilla(columna+(i+1), fila-(i+1))==colocar){
						valido = true;
					}
					i++;
					cont++;
				}
			}
		}
		int ret=0;

		if (valido){
			ret=cont;
		}
		return ret;
	}

	public static int compruebaDiagAbajoDrch (int columna, int fila, Ficha colocar, Tablero t){

		Ficha contrario = contrario(colocar);
		int cont=0;
		boolean valido=false;
		
		if ((fila<=t.getAlto()) && columna >= 0){
			//Si la ficha de al lado es diferente
			if (t.getCasilla(columna-1, fila-1) == contrario){
				int i=1;
				//empieza a comprobar hasta que ficha cambia o llega al borde
				while (t.getCasilla(columna-i, fila-i)==contrario && columna-i >= 0 && fila-i >= 0){
					if (t.getCasilla(columna-(i+1), fila-(i+1))==colocar){
						valido = true;
					}
					i++;
					cont++;
				}
			}
		}
		int ret=0;
		if (valido){
			ret=cont;
		}
		return ret;
	}

	public static int compruebaDiagArribaIzq (int columna, int fila, Ficha colocar, Tablero t){
		
		Ficha contrario = contrario(colocar);
		int cont=0;
		boolean valido=false;
		
		if (fila>0 && columna<t.getAncho()){
			//Si la ficha de al lado es diferente
			if (t.getCasilla(columna+1, fila+1) == contrario){
				int i=1;
				//empieza a comprobar hasta que ficha cambia o llega al borde
				while (t.getCasilla(columna+i, fila+i)==contrario && fila+i <= t.getAlto() && columna+i <= t.getAncho()){
					if (t.getCasilla(columna+(i+1), fila+(i+1))==colocar){
						valido = true;
					}
					i++;
					cont++;
				}
			}
		}
		int ret=0;
		if (valido){
			ret=cont;
		}
		return ret;
		
	}

	public static int compruebaDiagArribaDrch (int columna, int fila, Ficha colocar, Tablero t){

		Ficha contrario = contrario(colocar);
		int cont=0;
		boolean valido=false;
		
		if (fila>0 && columna>0){
			//Si la ficha de al lado es diferente
			if (t.getCasilla(columna-1, fila+1) == contrario){
				int i=1;
				//empieza a comprobar hasta que ficha cambia o llega al borde
				while (t.getCasilla(columna-i, fila+i)==contrario && fila+i <= t.getAlto() && columna-i >= 0){
					if (t.getCasilla(columna-(i+1), fila+(i+1))==colocar){
						valido = true;
					}
					i++;
					cont++;
				}
			}
		}
		int ret=0;
		if (valido){
			ret=cont;
		}
		return ret;
		
	}

	public static Ficha contrario (Ficha ficha){
		Ficha contrario = null;
		if (ficha==Ficha.BLANCA) {
			contrario=Ficha.NEGRA;
		}
		else if (ficha==Ficha.NEGRA){
			contrario = Ficha.BLANCA;
		}
		return contrario;
	}
	
	public TipoJuego getJuego(){
		return juego;	
	}

}
