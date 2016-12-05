package tp.pr5.logica;

public class ReglasConecta4 extends ReglasBasicas  {

	private static TipoJuego juego = TipoJuego.CONECTA4;

	public ReglasConecta4() {
		
		this.ancho=7;
		this.alto=6;
		
	}
	
	//Permite averiguar si en la partida ya tenemos un ganador o no.
	

		
	//Construye el tablero que hay que utilizar para la partida, según las reglas del juego.
	public Tablero iniciaTablero() {
	
		return new Tablero(ancho,alto);
	}
		
	
	

		
	//Devuelve true si, con el estado del tablero dado, la partida ha terminado en tablas.
	public boolean tablas(Ficha ultimoEnPoner, Tablero t){
		
		Ficha casilla;
		ancho=t.getAncho();
		alto=t.getAlto();
		boolean tablas=true;
          
        for(int i=alto;i>0;i--){
        	for(int j=ancho;j>0;j--){
        		casilla=t.getCasilla(j,i);
        		if(casilla==Ficha.VACIA){
      	   			tablas=false;
      	   			
      	   			j=-1;
      	   			i=-1;
      	   		}
      	   	}
         }
		
        return tablas;
	}
	
	public TipoJuego getJuego(){
		return juego ;	
	}
}
