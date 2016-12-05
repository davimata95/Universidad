package tp.pr1.control;

import tp.pr1.logica.Ficha;
import tp.pr1.logica.Partida;
import tp.pr1.logica.Tablero;


public class Controlador {
	
	private Tablero tablero;
	private Partida partida;
	private Ficha turno;
	private java.util.Scanner in;
	private int ancho ;
	private int alto ;
	private Ficha ganador=null;
	
	public Controlador(Partida p,java.util.Scanner in){
		this.partida=p;
		this.in = in ;
		turno = Ficha.BLANCA;
		
	}
	
	public void run(){
		
		turno=Ficha.BLANCA ;	//Empiezan siempre las blancas
		tablero=partida.getTablero();	//obtiene la partida
		ancho=tablero.getAncho();	//obtiene el ancho del tablero
		alto=tablero.getAlto();	//obtiene el alto del tablero
		String turnoDe ;	//string que dara por pantalla blancas o negras segun de quien sea el turno
		
		
		while(ganador==null){
			
			//Para que salga Juegan blancas y no juega BLANCA
			if (turno == Ficha.BLANCA){
				turnoDe = "blancas" ;
			}
			else{
				turnoDe = "negras" ;
			}

			mostrarTablero () ;	//Muestra el tablero 
			System.out.print ("Juegan " + turnoDe + "\n"); 	
			System.out.print("Qué quieres hacer? ");
		
			String opcion;
			opcion = in.nextLine();
			
			switch(opcion){
				case "poner" : 
					
		   			System.out.print("Introduce la columna: ");
		   			
		   			int entrada = 0 ;
					entrada = in.nextInt() ;
		   			in.nextLine();
		   			
		  			boolean posible;
		   				
		   			posible = partida.ejecutaMovimiento(turno, entrada) ; // bool si se puede ejecutar el mov.
		   				 				
		   			if (!posible){
		   				System.err.print("Movimiento incorrecto" + "\n");	//Si no se puede ejecutar salta error		
		   			}
		   			else{	
		   				turno = partida.getTurno();	//En caso contrario cambia el turno del jugador
		   			}
		   				   				
		   			ganador=partida.getGanador();	//Nos dira si hay un ganador de la partida
		   					   			
		   			//Si hay ganador nos dira quien es (Blancas, Negras o si hay tablas)
		   			if(ganador==Ficha.BLANCA){
		   				mostrarTablero () ;
		   				System.out.print ("Ganan las blancas" + "\n");
		   			}
		   			else if(ganador==Ficha.NEGRA){
		   				mostrarTablero () ;
		   				System.out.print ("Ganan las negras" + "\n");
		   			}
		   			else if(ganador==Ficha.VACIA){
		   				System.out.print ("La partida acaba en tablas" + "\n");
		   			}	   				 						   			
		   		break;	//Fin opcion poner
		   
				case "deshacer" :
			   
					boolean err =partida.undo();	//Deshace el anterior mov.
					
					if(!err){
						System.err.print("Imposible deshacer." + "\n"); //Si no hay movimientos que deshacer
					}
					turno=partida.getTurno();					
					
				break;	//Fin opcion dehacer
		   
				case "reiniciar" :
			
					partida.reset();	//Reinicia la partida
					System.out.print ("Partida reiniciada." + "\n");

				break;	   //Fin opcion reiniciar
		
				case "salir":
					System.exit(0);	//	Acaba el programa
				break ;	//Fin opcion salir
				
				default:
				
				System.err.print ("No te entiendo." + "\n");	//Si se introduce cualquier otra opcionel programa no lo entendera
				break;

			} // fin switch		
		} //fin while
		  //Ejecuta la partida hasta que �sta termina.
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

