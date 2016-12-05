package tp.pr3.control;

import java.util.Random;

import tp.pr3.logica.*;

//Jugador que juega de forma aleatoria a Complica
//En este caso cualquier columna es válida, pues si está llena, se hará hueco.
public class JugadorAleatorioGravity extends java.lang.Object implements Jugador {

	private FactoriaTipoJuego factoria;

	public Movimiento getMovimiento(Tablero tab, Ficha color){
		
		//crea la factoria para jugar gravity
		factoria= new FactoriaGravity(tab.getAncho(),tab.getAlto());
		
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
		
			//Si la ficha esta vacia
			if(tab.getCasilla(x, y) == Ficha.VACIA){
				//Realiza el mov.
				mov=factoria.creaMovimiento(x, y, color);
				hecho=true;
			}
		}
		return mov;
	}
}
	
	