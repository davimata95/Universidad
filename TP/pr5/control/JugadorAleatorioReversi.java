package tp.pr5.control;

import java.util.Random;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.ReglasReversi;
import tp.pr5.logica.Tablero;

public class JugadorAleatorioReversi implements Jugador {

	private FactoriaTipoJuego factoria;
	

	public JugadorAleatorioReversi(FactoriaTipoJuego f){
		
		factoria=f;
	}

	@Override
	public Movimiento getMovimiento(Tablero tab, Ficha color) {
						
		Movimiento mov=null;
		
		//Movimiento totalmente aleatorio
		//mov=aleatorioTotal(tab, color);
		//Movimiento inteligente.
		//Colocara en la casilla que mas fichas revierta
		mov=aleatorioInteligente(tab, color);
			
		return mov;
	}
	
	public Movimiento aleatorioTotal(Tablero tab, Ficha color){
		
		//Obtiene el ancho y el alto necesario
		int ancho=tab.getAncho();
		int alto=tab.getAlto();
		
		//Crea un mov. y un bool para comprobar si se realiza el mov.
		Movimiento mov=null;
		boolean hecho=false;
			
		//Mientras no se realice el mov.
		while(hecho==false){
			
			//Crea un r aleatorio
			Random r=new Random();
			
			//x aleatorio dentro del ancho
			int x=r.nextInt(ancho)+1;
			//y aleatorio dentro del alto
			int y=r.nextInt(alto)+1;
						
			int valido=ReglasReversi.movimientoCorrecto(x, y, color, tab);
			//=reglas.movimientoCorrecto(x, y, color, tab);
					
			//Devolvera la mejor opcion para poner
			//La que mas fichas le revierta al otro jugador
					
			if(tab.getCasilla(x, y) ==Ficha.VACIA && valido>0){
				//Realiza el mov.
				mov=factoria.creaMovimiento(x, y, color);
				hecho=true;
			}
			
		}
		return mov;
	}

	public Movimiento aleatorioInteligente (Tablero tab, Ficha color){
		
		Movimiento mov=null;
		int maximo = 0;
		int columnaInteligente = 0;
		int filaInteligente = 0;
		
		for (int i = 1 ; i < tab.getAncho()+1 ; i++){
			for(int j = 1 ; j < tab.getAncho()+1 ; j++){
				int revertidas = ReglasReversi.movimientoCorrecto(i,j, color, tab);
				if (tab.getCasilla(i, j) == Ficha.VACIA && revertidas>maximo){
					//comprueba maximo
					maximo=revertidas;
					columnaInteligente=i;
					filaInteligente=j;
				}
			}
		}
		
		if (maximo>0){
			mov=factoria.creaMovimiento(columnaInteligente, filaInteligente, color);
		}
		return mov;
	}
	
	@Override
	public Movimiento getMovimiento(Tablero tab, Ficha color, int fil, int col) {
		Movimiento mov=factoria.creaMovimiento(1, 0, color);
		return mov;
	}

}
