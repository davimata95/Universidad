package tp.pr3.control;

import tp.pr3.logica.*;

public class Controlador {
	
	private Partida partida;
	private Ficha turno;
	private java.util.Scanner in;
	private Ficha ganador;
	private Movimiento movimiento;
	private ReglasJuego reglas;
	private boolean terminada;
	private FactoriaTipoJuego factoria;
	
	 public Controlador(FactoriaTipoJuego f, Partida p,java.util.Scanner in){
		this.partida=p;
		this.in = in ;
		this.factoria=f;
		turno = partida.getTurno();
		reglas=factoria.creaReglas();
		ganador=Ficha.VACIA;
		terminada=false;
	}
	
	public void run(){
		
		//Empiezan siempre las blancas
		turno=Ficha.BLANCA ;
		
		//string que dara por pantalla blancas o negras segun de quien sea el turno
		String turnoDe ;	
		
		//Inicialmente los jugadores seran humanos.
		Jugador jugador1 = factoria.creaJugadorHumanoConsola(in);
				
		Jugador jugador2 = factoria.creaJugadorHumanoConsola(in);
		
		
		while(ganador==Ficha.VACIA && terminada==false){

			//Para que salga Juegan blancas y no juega BLANCA
			if (turno == Ficha.BLANCA){
				turnoDe = "blancas" ;
			}
			//Para que salga Juegan negras y no juega NEGRA
			else{
				turnoDe = "negras" ;
			}
			
			partida.mostrarTablero () ;
			
			System.out.println ("Juegan " + turnoDe); 	

			System.out.print("Qué quieres hacer? ");
			
			//entrada que coge la opcion del usuario
			//Con .split separa las palabras de la entrada en diferentes elementos
			String[] opcion = in.nextLine().toLowerCase().split(" ");
			
			//switch con las posibles funciones del jugador humano
			switch(opcion[0]){			
				
				//Si la opcion es poner
				case "poner" : 
					
		  			boolean posible;
		  			
		  			//si el turno es de blancas(color del jugador 1)
		  			if (turno == Ficha.BLANCA){
		  				movimiento = partida.MovimientoJugador(jugador1);
		  			}
		  			//si el turno es de NEGRAS(color del jugador 2)
		  			else {
		  				movimiento = partida.MovimientoJugador(jugador2);
		   			}
		  			
		   			// bool si se puede ejecutar el mov.
		  			
		  			//intenta ejecutar el movimiento
		  			try {
		   			
		  				posible = partida.ejecutaMovimiento(movimiento) ; 
		   			
		  			}
		  			//Si no es posible, salta mensaje de error
		  			catch(MovimientoInvalido e){
		  				
		  				System.err.println(e.getMessage());
		  				posible =false;
		  				
		  			}
		   			
		   			//Si el mov. se ejecuto correctamente
		   			if (posible){
		   				turno = partida.getTurno();	
		   			}
		   			
		   			//Nos dira si hay un ganador de la partida
		   			ganador=partida.getGanador();	
		   			terminada=partida.isTerminada();
		   			
		   			//funcion que muestra, en caso de que haya ganador quien es el campeon
		   			muestraGanador(ganador,terminada);
		   			
	 			//Fin opcion poner			   			
		   		break;	
		   		
		   		//Deshace el anterior mov.
				case "deshacer" :
					
					boolean err =partida.undo();	
					
					//Si no hay movimientos que deshacer
					if(!err){
						System.err.println("Imposible deshacer."); 
					}
					turno=partida.getTurno();					
				//Fin opcion dehacer	
				break;	
								
				//Reinicia la partida
				case "reiniciar" :
					partida.reset(reglas);
					System.out.println("Partida reiniciada.");
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
								reglas=new ReglasConecta4();
								factoria=new FactoriaConecta4();
							
								//juego disponible
								valido=true;
								
								//Fin opcion jugar c4
								break;	
								
							//Si el tipo de juego es co
							case "co":
								//Reinicia la partida
								reglas=new ReglasComplica();
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
									reglas=new ReglasGravity(anch,alt);
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
						
						//Parte comun al seleccionar cualquiera de los juegos
						
						partida=new Partida(reglas);
						
						//los jugadores vuelven a ser humanos
						jugador1 = factoria.creaJugadorHumanoConsola(in);
						jugador2 = factoria.creaJugadorHumanoConsola(in);
						
						//obtiene el turno
						turno=partida.getTurno();	
						
						System.out.println("Partida reiniciada.");
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
					
						//Segun el color que sea
						switch (color){
							//Si se introduce Blancas
							case "blancas":
								//Se cambia el tipo de jugador de la ficha seleccionada 
								switch (tipo){
								//Cambia a jugador humano
									case "humano":
										jugador1 = factoria.creaJugadorHumanoConsola(in);
										break;
										//Cambia a jugador aleatorio
									case "aleatorio":
										jugador1 = factoria.creaJugadorAleatorio();
										break;
								
									//Si se quiere poner un tipo de jugador diferente de humano o aleatorio
									default:
										System.err.println ("No te entiendo.");	
									break;
								//Fin switch tipo
								}
								//Fin case "blancas"
								break;
						
							case "negras":
								//Se cambia el tipo de jugador de la ficha seleccionada 
								switch (tipo){
									//Cambia a jugador humano
									case "humano":
										jugador2 = factoria.creaJugadorHumanoConsola(in);
										break;
										//Cambia a jugador aleatorio
									case "aleatorio":
										jugador2 = factoria.creaJugadorAleatorio();
										break;
									
									//Si se quiere poner un tipo de jugador diferente de humano o aleatorio
									default:
										System.err.println ("No te entiendo.");	
										break;
										//Fin switch tipo
								}	
								// fin case "negras"
								break; 
					
							//Si se quiere cambiar el tipo de otro jugador que no sea blancas o negras
							default:
								System.err.println ("No te entiendo.");	
							break;
							//Fin switch color
						}
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
	

	//Si hay ganador nos dira quien es (Blancas, Negras o si hay tablas)
	public void muestraGanador(Ficha ganador, boolean terminada){
				
		//Si el ganador es el jugador Blanco
		if(ganador==Ficha.BLANCA){
			partida.mostrarTablero () ;
			System.out.println ("Ganan las blancas");
		}
		//Si el jugador es el jugador Negro
		else if(ganador==Ficha.NEGRA){
			partida.mostrarTablero () ;
			System.out.println ("Ganan las negras");
		}
		//Si no hay ganador. Partida en Tablas 
		else if(ganador==Ficha.VACIA && terminada){
			partida.mostrarTablero () ;
			System.out.println ("Partida terminada en tablas.");
		}	 	
	}
}

