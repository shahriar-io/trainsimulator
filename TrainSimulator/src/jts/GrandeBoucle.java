package jts;


public class GrandeBoucle extends Thread {
	private Controleur controleur;
	private long duree;
	
	public GrandeBoucle(Controleur controleur, long ms){
		this.controleur = controleur;
		this.duree = ms;
	}
	
	public void run(){
		while(true){
			this.controleur.boucler();
			try {
				Thread.sleep(duree);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
