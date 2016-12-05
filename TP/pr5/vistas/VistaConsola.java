package tp.pr5.vistas;

import tp.pr5.control.ControlConsola;
import tp.pr5.control.FactoriaTipoJuego;
import tp.pr5.control.Jugador;
import tp.pr5.control.TableroInmutable;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.MovimientoInvalido;
import tp.pr5.logica.Observador;
import tp.pr5.logica.Partida;
import tp.pr5.logica.ReglasJuego;

public class VistaConsola extends java.lang.Object implements Observador {

	private ControlConsola ctrl;
	private java.util.Scanner in;

	private Ficha ganador = Ficha.VACIA	;
	private boolean terminada;
	private FactoriaTipoJuego factoria;
	private Ficha turno;
	
	public VistaConsola (java.util.Scanner in, FactoriaTipoJuego f, Partida p){			
		this.in=in;
		this.ctrl=new ControlConsola(p, in, f);
		ctrl.addObservador(this);
		this.factoria=f;
	}
		
	public void run (){
		//desde aqui llamo a las funciones de controlConsola
				
		ctrl.NecMostrarTable();
		
		ReglasJuego reglas=factoria.creaReglas();
			
		turno = reglas.jugadorInicial();
		
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
				//Fin opcion reiniciar	
				break;	
				
				//	Acaba el programa
				case "salir":
					System.exit(0);	
				//Fin opcion salir
				break ;	
				
				//opcion jugar ..... segun el tipo de juego introducido
				case "jugar":
									
					//Comprueba que hay dimensiones en gravity
					//No se puede mostrar mensajes desde controlConsola
	
					boolean grValido = true;
					
					try{
						if (opcion[1].equalsIgnoreCase("gr")){
							if (opcion.length != 4){
								System.err.println ("No te entiendo.");
								grValido=false;
							}
						}
						
						//si pone algo mas que jugar P.E jugar c4 (length>1)
						if (opcion.length > 1 && grValido){	
							try {
								boolean noJuego=ctrl.cambiarJuego(opcion);
								if (!noJuego){
									System.err.println ("No te entiendo.");
								}
							}catch(NumberFormatException e){
								System.err.println ("No te entiendo.");
							}
						}
					}
					// si solo pone jugar
					catch(java.lang.ArrayIndexOutOfBoundsException e){
						System.err.println ("No te entiendo.");
					}

				//Fin opcion jugar
				break; 

				//Una vez introducido jugador
				case "jugador" :
					
					//Si se introducen 3 datos
					if (opcion.length == 3){
						//Toma el valor del color y el tipo de jugador seleccionado
						String color = opcion[1];
						String tipoJugador = opcion[2];

						Jugador jugador=null;
						
						if (color.equalsIgnoreCase("blancas") && tipoJugador.equalsIgnoreCase("humano"))
							jugador = factoria.creaJugadorHumanoConsola(in);
						else if (color.equalsIgnoreCase("blancas") && tipoJugador.equalsIgnoreCase("aleatorio"))
							jugador = factoria.creaJugadorAleatorio();
						else if (color.equalsIgnoreCase("negras") && tipoJugador.equalsIgnoreCase("humano"))
							jugador = factoria.creaJugadorHumanoConsola(in);
						else if (color.equalsIgnoreCase("negras") && tipoJugador.equalsIgnoreCase("aleatorio"))
							jugador = factoria.creaJugadorAleatorio();		
						else 
							System.err.print("No te entiendo.\n");
							
						ctrl.cambiarJugador(color, jugador);
						
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
					System.out.println ("JUGAR [c4|co|gr][rv] [tamX tamY]: cambia el tipo de juego.");
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
		
		terminada=true;
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

		System.out.println("Qué quieres hacer? ");
	}

	public void onMovimientoEnd(TableroInmutable tab) {
		System.out.println(tab);
	}

	public void onUndo(TableroInmutable tab, boolean hayMas,Ficha turno) {
		System.out.println(tab);
		this.turno=turno;
	}

	public void onUndoNotPossible() {
		System.err.println("Imposible deshacer."); 
	}
	
	public void onResetPartida(TableroInmutable tabIni, Ficha turnoIni) {
		System.out.println(tabIni);
		this.turno=turnoIni;
	}

	public void onMovimientoIncorrecto(MovimientoInvalido e) {
		System.err.println(e.getMessage());	
		ctrl.NecMostrarTable();
		in.nextLine();
	
	}
	
	public void onMostrarTablero(TableroInmutable tab){
		
		System.out.println(tab);
		
	}

	@Override
	public void onCambioJuego(TableroInmutable tab, Ficha turn) {
		System.out.println(tab);	
		this.turno=turn;
	}

	
	@Override
	public void onCambioTurno(Ficha turno) {
		// TODO Auto-generated method stub
		this.turno=turno;
	}

	@Override
	public void onInicioPartida(TableroInmutable tab) {
		// TODO Auto-generated method stub
		
	}

}

	

