package jts;

/**Ceci est le <code>Thread</code> qui cadence l'exécution d'une boucle de simulation.
 * 
 * @author Yannick BISIAUX
 *
 */
public class GrandeBoucle extends Thread {
	
	private Controleur controleur;
	private long duree;
	private boolean stop;
	
	public GrandeBoucle(Controleur controleur, long ms){
		this.controleur = controleur;
		this.duree = ms;
	}
	
	public void stopper(){ this.stop = true; }
	
	public void run(){
		while(!stop){
			this.controleur.boucler();
			try {
				Thread.sleep(duree);
			} catch (InterruptedException e) {
				Controleur.LOG.error("Interruption de la grande boucle : " + e.getMessage());
			}
		}
	}
}
