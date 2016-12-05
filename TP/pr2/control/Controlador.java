package tp.pr2.control;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Movimiento;
import tp.pr2.logica.MovimientoComplica;
import tp.pr2.logica.MovimientoConecta4;
import tp.pr2.logica.Partida;
import tp.pr2.logica.ReglasComplica;
import tp.pr2.logica.ReglasConecta4;
import tp.pr2.logica.ReglasJuego;
import tp.pr2.logica.Tablero;

public class Controlador {
	
	private Tablero tablero;
	private Partida partida;
	private Ficha turno;
	private java.util.Scanner in;
	private int ancho ;
	private int alto ;
	private Ficha ganador=Ficha.VACIA;
	private Movimiento movimiento;
	private ReglasJuego reglas;
	private String tipoJuego;
	private boolean terminada=false;
	
	 public Controlador(Partida p,java.util.Scanner in){
		this.partida=p;
		this.in = in ;
		turno = partida.getTurno();
		reglas=new ReglasConecta4();
		tipoJuego = "conecta4";
	}
	
	public void run(){
		
		//Empiezan siempre las blancas
		turno=Ficha.BLANCA ;	
		//obtiene la partida, el ancho y el alto del tablero
		tablero=partida.getTablero();
		ancho=tablero.getAncho();	
		alto=tablero.getAlto();	
		
		//string que dara por pantalla blancas o negras segun de quien sea el turno
		String turnoDe ;	
		
		while(ganador==Ficha.VACIA && terminada==false){
			
			//Para que salga Juegan blancas y no juega BLANCA
			if (turno == Ficha.BLANCA){
				turnoDe = "blancas" ;
			}
			else{
				turnoDe = "negras" ;
			}
			
			mostrarTablero () ;
			System.out.print ("Juegan " + turnoDe + "\n"); 	
			System.out.print("Qué quieres hacer? ");
			
			String opcion;
			opcion = in.nextLine().toLowerCase();	
			
			switch(opcion){			
			
				case "poner" : 
					
		   			System.out.print("Introduce la columna: ");
		   			
		   			int entrada = 0 ;
					entrada = in.nextInt() ;
		   			in.nextLine();
		   			
		  			boolean posible;
		  			
		  			//determinara que tipo de juego es y crea el mov.
		   			if (tipoJuego == "conecta4"){
		   				movimiento = new MovimientoConecta4(entrada, turno);
		   			}
		   			else{
		   				movimiento = new MovimientoComplica(entrada, turno);
		   			}

		   			// bool si se puede ejecutar el mov.
		   			posible = partida.ejecutaMovimiento(movimiento) ; 
		   			
		   			//Si no se puede ejecutar salta error			
		   			if (!posible){
		   				System.err.print("Movimiento incorrecto" + "\n");			
		   			}
		   			//En caso contrario obtiene el turno del jugador (actualizado)
		   			else{	
		   				turno = partida.getTurno();	
		   			}
		   			
		   			//Nos dira si hay un ganador de la partida
		   			ganador=partida.getGanador();	
		   			terminada=partida.isTerminada();
		   					   			
		   			//Si hay ganador nos dira quien es (Blancas, Negras o si hay tablas)
		   			if(ganador==Ficha.BLANCA){
		   				mostrarTablero () ;
		   				System.out.print ("Ganan las blancas" + "\n");
		   			}
		   			else if(ganador==Ficha.NEGRA){
		   				mostrarTablero () ;
		   				System.out.print ("Ganan las negras" + "\n");
		   			}
		   			else if(ganador==Ficha.VACIA && terminada){
		   				mostrarTablero () ;
		   				System.out.print ("Partida terminada en tablas." + "\n");
		   			}	   				 						   			
		   		break;	//Fin opcion poner
		   		
		   		//Deshace el anterior mov.
				case "deshacer" :
					
					boolean err =partida.undo();	
					
					//Si no hay movimientos que deshacer
					if(!err){
						System.err.print("Imposible deshacer." + "\n"); 
					}
					turno=partida.getTurno();					
					
				break;	//Fin opcion dehacer
				
				//Reinicia la partida
				case "reiniciar" :
					partida.reset(reglas);
					System.out.print ("Partida reiniciada." + "\n");
				break;	   //Fin opcion reiniciar
				
				//	Acaba el programa
				case "salir":
					System.exit(0);	
				break ;	//Fin opcion salir
				
				//cambia el tipo de juego a c4
				case "jugar c4" :
					
					tipoJuego = "conecta4" ; 
					//Reinicia la partida
					
					reglas=new ReglasConecta4();
					partida=new Partida(reglas);

					System.out.print ("Partida reiniciada." + "\n");
					tablero=partida.getTablero();
					//obtiene el ancho y el alto del tablero
					ancho=tablero.getAncho();	
					alto=tablero.getAlto();	
					turno=partida.getTurno();
				break;
				
				//cambia el tipo de juego a co
				case "jugar co" :
										
					tipoJuego = "complica" ; 
					//Reinicia la partida
						reglas=new ReglasComplica();
						partida=new Partida(reglas);
					System.out.print ("Partida reiniciada." + "\n");
					tablero=partida.getTablero();
					//obtiene el ancho del tablero
					ancho=tablero.getAncho();	
					//obtiene el alto del tablero
					alto=tablero.getAlto();	
					turno=partida.getTurno();
				break;

				//Si se introduce cualquier otra opcionel programa no lo entendera
				default:
				System.err.print ("No te entiendo." + "\n");	
				break;

			} // fin switch		
		} //fin while
	 //Ejecuta la partida hasta que ï¿½sta termina.
	}
	
	public void mostrarTablero () {
				
		String fila;
		tablero=partida.getTablero();
	
		for (int j = 0 ; j<alto;j++){
			fila="|";
			for (int i =0; i < ancho; i++) {
				
				Ficha fichita = tablero.getCasilla(i+1,j+1);
				
				fila=fila +turno.dibuja(fichita);
			}
			fila = fila +"|"; 
			System.out.print(fila + "\n");
		}
		
		fila="+";
		String ffinal=" ";
		for (int j =0; j < ancho; j++) {
			fila=fila +"-";
			ffinal=ffinal + (j+1);
		}
		System.out.print(fila + "+" + "\n");
		System.out.print(ffinal + "\n" + "\n");
	}
}

