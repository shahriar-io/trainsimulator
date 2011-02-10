package jts;

import java.io.File;
import java.io.IOException;

import jts.ihm.Ihm;
import jts.ihm.InterfaceHommeMachine;
import jts.io.LigneLoader;
import jts.io.ScenarioLoader;
import jts.log.Log;
import jts.log.LogMode;
import jts.moteur.MoteurPhysique;
import jts.moteur.ligne.Ligne;

public class Controleur {
	
	/**Duree de boucle en millisecondes*/
	private static long DUREE = 50;
	
	private InterfaceHommeMachine ihm;
	private MoteurPhysique moteurPhysique;

	public Controleur(){
		
	}
	
	public void initLogiciel(){
		try {
			Log.init(new File("log.txt"), LogMode.WARNING);
		} catch (IOException e) {
			System.out.println("Unable to create a log file");
			e.printStackTrace();
		}
		this.ihm = new Ihm(this);
		this.ihm.afficherEcranDemarrage();
	}
	
	public void lancerSimu(File fichierLigne, File fichierScenario){
		this.ihm.detruireEcranDemarrage();
		this.ihm.initIhm();
		this.moteurPhysique = new MoteurPhysique(DUREE);
		
		Log.getInstance().logInfo("**********  Chargement de la ligne  **********");
		Ligne ligne = LigneLoader.load(fichierLigne);
		this.moteurPhysique.setLigne(ligne);
		this.ihm.getInterfaceGraphique().chargerTerrain(ligne.getCircuit());
		
		Log.getInstance().logInfo("**********  Chargement du scénario  **********");
		ScenarioLoader.loadScenario(ligne.getCircuit(), fichierScenario);
		Thread gb = new GrandeBoucle(this, DUREE);
		gb.start();
	}
	
	public void boucler(){
		float[] valeurJoystick = this.ihm.getJoystick().getValeursVolantFrein();
		boolean[] touchesClavier = this.ihm.getClavier().getTouchePressee();
		this.moteurPhysique.nextStep(valeurJoystick[0], valeurJoystick[1], touchesClavier);
		this.ihm.getInterfaceGraphique().afficherLigne(moteurPhysique.getLigne());
	}
}
