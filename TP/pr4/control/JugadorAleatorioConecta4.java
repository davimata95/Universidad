package tp.pr4.control;

import java.util.Random;

import tp.pr4.logica.*;

//Jugador que juega de forma aleatoria a Complica
//En este caso cualquier columna es válida, pues si está llena, se hará hueco.
public class JugadorAleatorioConecta4 extends java.lang.Object implements Jugador {
	
	private FactoriaTipoJuego factoria;
	
	public JugadorAleatorioConecta4(FactoriaTipoJuego f){
		
		factoria=f;
	}
	public Movimiento getMovimiento(Tablero tab, Ficha color){
		
		//crea la factoria para jugar conecta4
	
		
		//Obtiene el ancho
		int ancho=tab.getAncho();
		
		//Crea un movimiento y un boolean para controlar si el mov. se realiza
		Movimiento mov=null;
		boolean hecho=false;
		
		//Mientras el mov. no se realice
		while(hecho==false){
			//Tomara un valor aleatorio
			Random r=new Random();
			//Otorga a x el aleatorio+1 (tablero empieza en 1, vector empieza en 0)
			int x=r.nextInt(ancho)+1;
			
			//Si la casilla esta vacia, hace el movimiento
			if(tab.getCasilla(x, 1) == Ficha.VACIA){
				mov=factoria.creaMovimiento(x, 0, color);
				hecho=true;
			}
		}
		return mov;
	
	}
	public Movimiento getMovimiento(Tablero tab,Ficha color,int a, int b)
	{
		Movimiento mov=factoria.creaMovimiento(1, 0, color);
		return mov;
		
	}
		
}
	
	