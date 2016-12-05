package tp.pr4.control;

public enum TipoJuego {

	CONECTA4,COMPLICA,GRAVITY;
	
	
	
	private int alto;
	private int ancho;
	
	public int getalto(){
		
		return alto;
	}
	public int getancho(){
		
		return ancho;
	}
	
	public void setancho(int x){
		
		if(this==TipoJuego.COMPLICA){
			
		}
		
		if(this==TipoJuego.CONECTA4)
			
		ancho=x;
	}
	
	public void setalto(int y){
		alto=y;
	}
	
}
