package tp.pr5.control;

public enum TipoJugador {
	HUMANO, AUTOMATICO;
	
	private Thread ThJug=null;

	
	public String toString(){
		String a=null;
		
		if(this==TipoJugador.HUMANO){
			a="HUMANO";
			
		}
		if(this==TipoJugador.AUTOMATICO){
			a="AUTOMATICO";
			
		}
		return a;
		
		
		
		
	}
	
	public boolean getActivo(){
		boolean ena=true;
		if(this==TipoJugador.HUMANO){
			ena=true;
		}
		if(this==TipoJugador.AUTOMATICO){
			
			ena=false;
		}
		
		return ena;
		
		
		
		
	}
	public boolean posibleDehacer(){
		boolean ena=true;
		if(this==TipoJugador.HUMANO){
			ena=true;
		}
		if(this==TipoJugador.AUTOMATICO){
			
			ena=false;
		}
		
		return ena;
		
	}
	
	public void iniciar(ControlSwing cntrl){
		
		if(this==TipoJugador.AUTOMATICO){
			
			ThJug = new Thread(new HebraJugador(cntrl));
			ThJug.start();
	
		
		}
			
		
		
		
		
	}
	public void parar(){
		if(this==TipoJugador.AUTOMATICO){
			if(ThJug!=null)  	ThJug.interrupt();
		}
		
		
		
	
	}
	
	
public class HebraJugador  implements Runnable{
		
		
		private ControlSwing cnt;
		
		public HebraJugador(ControlSwing cntl) {
			this.cnt=cntl;
		}
		@Override
		public void run() {
			
						try {
							Thread.sleep(500);
							cnt.poner();
						} catch (InterruptedException e) {
							Thread.interrupted();
						}
						
						
						
		
						
						
			
			}		
		}


}
