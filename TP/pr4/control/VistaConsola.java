package tp.pr4.control;

import tp.pr4.logica.Ficha;
import tp.pr4.logica.Movimiento;
import tp.pr4.logica.MovimientoInvalido;
import tp.pr4.logica.Observador;
import tp.pr4.logica.Partida;
import tp.pr4.logica.ReglasComplica;
import tp.pr4.logica.ReglasConecta4;
import tp.pr4.logica.ReglasGravity;
import tp.pr4.logica.ReglasJuego;
import tp.pr4.logica.Tablero;

public class VistaConsola extends java.lang.Object implements Observador {

	private ControlConsola ctrl;
	private Partida partida;
	private Ficha turno;
	private java.util.Scanner in;
	private Ficha ganador = Ficha.VACIA	;
	private Movimiento movimiento;
	private ReglasJuego reglas;
	private boolean terminada;
	private FactoriaTipoJuego factoria;
	private int ancho;
	private int alto;
	
	public VistaConsola (java.util.Scanner in, FactoriaTipoJuego f, Partida p){		
		
		this.in=in;
		this.ctrl=new ControlConsola(p, in, f);
		ctrl.addObservador(this);
		this.factoria=f;
		this.partida=p;
	}
		
	public void run (){
		//desde aqui llamo a las funciones de controlConsola
	
		//Empiezan siempre las blancas
		turno=Ficha.BLANCA ;
							
		System.out.println(partida.mostrarTablero());
		
		while(ganador==Ficha.VACIA && terminada==false){

			onMovimientoStart(turno);
			
			//entrada que coge la opcion del usuario
			//Con .split separa las palabras de la entrada en diferentes elementos
			String[] opcion = in.nextLine().toLowerCase().split(" ");
	
			//switch con las posibles funciones del jugador humano
			switch(opcion[0]){			
				
				//Si la opcion es poner
				case "poner" : 					
					ctrl.poner();
					turno=partida.getTurno();
					terminada=partida.isTerminada();
	 			//Fin opcion poner			   			
		   		break;	
		   		
		   		//Deshace el anterior mov.
				case "deshacer" :
					ctrl.undo();
				//Fin opcion dehacer	
				break;	
								
				//Reinicia la partida
				case "reiniciar" :
					ctrl.reiniciar();
					turno=Ficha.BLANCA;
				//Fin opcion reiniciar	
				break;	
				
				//	Acaba el programa
				case "salir":
					System.exit(0);	
				//Fin opcion salir
				break ;	
				
				//opcion jugar ..... segun el tipo de juego introducido
				case "jugar":
					
					//En principio valido es false, ya que si se muestra jugar el programa no lo entendera
					boolean valido = false;
				
					//si pone algo mas que jugar P.E jugar c4 (length>1)
					if (opcion.length > 1){
					
						//Mira que tipo de juego se puso
						switch (opcion[1]){
						
							//Si el tipo de juego es c4
							case "c4":
								//Reinicia la partida
								factoria=new FactoriaConecta4();
							
								//juego disponible
								valido=true;
								
								//Fin opcion jugar c4
								break;	
								
							//Si el tipo de juego es co
							case "co":
								//Reinicia la partida
								factoria=new FactoriaComplica();
							
								//juego disponible
								valido=true;
								
								//Fin opcion jugar co	
							break; 
							
							//Si el tipo de juego es gr
							case "gr":
							
								//Si la cadena de entrada es distinta de 4
								//ya que si se juega gravity se deben poner las dimensiones
								if (opcion.length != 4){
									System.err.println ("No te entiendo.");	
								}
								//Si se introducen 4 entradas
								else{
									//Juego disponible
									valido = true;

									//coge el ancho y el alto de las posiciones 3 y 4 del vector creado con el split
									int anch = Integer.parseInt(opcion[2]);
									int alt = Integer.parseInt(opcion[3]);
									
									//Reinicia la partida
									factoria=new FactoriaGravity(anch,alt);
								}

								//Fin opcion jugar gr	
								break;
						
							//Si el tipo de juego es desconocido P.E jugar tp
							default:
								System.err.println ("No te entiendo.");
							break;
						}	
					}	
					
					// si solo pone jugar
					else{
						System.err.println ("No te entiendo.");
					}
					
					//Si el tipo de juego es valido
					if (valido){
						ctrl.reset(factoria);
					}

				//Fin opcion jugar
				break; 

				//Una vez introducido jugador
				case "jugador" :
					
					//Si se introducen mas datos
					if (opcion.length == 3){
						//Toma el valor del color del jugador seleccionado
						String color = opcion[1];
						//Toma el tipo de jugador que se desea usar para el jugador
						String tipo = opcion[2];
					
						ctrl.cambiarJugador(color, tipo);
						
					}
					else{
						System.err.println ("No te entiendo.");
					}
				//Fin opcion "jugador"
				break;				
				
				//muestra los comandos disponibles
				case "ayuda":
					System.out.println ("Los comandos disponibles son:"  + "\n"); 
					System.out.println ("PONER: utilízalo para poner la siguiente ficha.");
					System.out.println ("DESHACER: deshace el último movimiento hecho en la partida.");
					System.out.println ("REINICIAR: reinicia la partida.");
					System.out.println ("JUGAR [c4|co|gr] [tamX tamY]: cambia el tipo de juego.");
					System.out.println ("JUGADOR [blancas|negras] [humano|aleatorio]: cambia el tipo de jugador.");
					System.out.println ("SALIR: termina la aplicación.");
					System.out.println ("AYUDA: muestra esta ayuda." + "\n");
				break;

				//Si se introduce cualquier otra opcion el programa no lo entendera
				default:
					System.err.println ("No te entiendo.");	
				break;

			} // fin switch	
		} //fin while
	 //Ejecuta la partida hasta que Ã¯Â¿Â½sta termina.
	}

	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		
		//muestra tablero y ganador
		System.out.println(tabFin);
		//muestra quien es el campeon
		//Si el ganador es el jugador Blanco
		if(ganador==Ficha.BLANCA){
			System.out.println ("Ganan las blancas");
		}
		//Si el jugador es el jugador Negro
		else if(ganador==Ficha.NEGRA){
			System.out.println ("Ganan las negras");
		}
		//Si no hay ganador. Partida en Tablas 
		else if(ganador==Ficha.VACIA && terminada){
			System.out.println ("Partida terminada en tablas.");
		}	 			
	}

	public void onMovimientoStart(Ficha turno) {
		
		//string que dara por pantalla blancas o negras segun de quien sea el turno
		String turnoDe ;			
		//Para que salga Juegan blancas y no juega BLANCA
		if (turno == Ficha.BLANCA){
			turnoDe = "blancas" ;
		}
		//Para que salga Juegan negras y no juega NEGRA
		else{
			turnoDe = "negras" ;
		}
		
		System.out.println ("Juegan " + turnoDe); 	

		System.out.print("Qué quieres hacer? ");
	}

	public void onMovimientoEnd(TableroInmutable tab) {
		System.out.println(tab);
	}

	public void onUndo(TableroInmutable tab, boolean hayMas) {
		System.out.println(tab);
		turno=partida.getTurno();
	}

	public void onUndoNotPossible() {
		System.err.println("Imposible deshacer."); 
	}
	
	public void onResetPartida(TableroInmutable tabIni, Ficha turnoIni) {
		System.out.println(tabIni);
		turno=turnoIni;
	}

	@Override
	public void onCambioTurno(Ficha turno) {
		// TODO Auto-generated method stub
		
	}

	public void onMovimientoIncorrecto(MovimientoInvalido e) {
		System.out.println(e.getMessage());	
		System.out.println(partida.mostrarTablero());
	}

	public void onCambioJuego(TableroInmutable tab, TipoJuego tipo, Ficha turno) {
		//No muestra nada al cambiar de juego 
	}

	public void onCambioDimension(int x, int y) {
		this.ancho=x;
		this.alto=y;
	}

	@Override
	public void onCambioJuego(TableroInmutable tab, Ficha turno) {
		// TODO Auto-generated method stub
		
	}
}

	

