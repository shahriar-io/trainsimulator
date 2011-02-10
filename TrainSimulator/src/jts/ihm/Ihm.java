package jts.ihm;

import jts.Controleur;
import jts.ihm.clavier.EcouteurClavier;
import jts.ihm.clavier.InterfaceClavier;
import jts.ihm.gui.Gay;
import jts.ihm.gui.InterfaceGraphique;
import jts.ihm.joystick.GestionnaireJoystick;
import jts.log.Log;

public class Ihm implements InterfaceHommeMachine {

	private Controleur controleur;
	private InterfaceGraphique gay;
	//private InterfaceAudio audio;
	private EcouteurClavier clavier;
	private GestionnaireJoystick joystick;
	
	public Ihm(Controleur controleur){
		this.controleur = controleur;
		this.gay = new Gay(this);
		this.clavier = new EcouteurClavier(this.gay);
		this.joystick = new GestionnaireJoystick();
	}
	
	public InterfaceClavier getClavier() {
		return this.clavier;
	}
	
	public GestionnaireJoystick getGestionnaireJoystick() {
		return this.joystick;
	}
	
	public InterfaceGraphique getInterfaceGraphique() {
		return this.gay;
	}
	
	public void afficherEcranDemarrage() {
		this.initJoystick();
		boolean isJoystickActif = joystick.getConfJoystick().isUtiliserJoystick();
		this.gay.afficherEcranDemarrage(isJoystickActif);
		/*this.audio = new AudioDefaut();
		double[] notes = {261, 326, 391, 522};
		
		this.audio.jouerNote(100, 120, 1.0);
		this.audio.jouerNote(112, 120, 1.0);
		this.audio.jouerNote(125, 120, 1.0);
		this.audio.jouerNote(137, 120, 1.0);
		this.audio.jouerNote(150, 120, 1.0);
		this.audio.jouerNote(162, 120, 1.0);
		this.audio.jouerNote(175, 120, 1.0);
		this.audio.jouerNote(187, 120, 1.0);
		this.audio.jouerNote(200, 120, 1.0);*/
		//this.audio.jouerNotes(notes, 500, 1.0);
	}
	
	public void detruireEcranDemarrage() {
		this.gay.detruireEcranDemarrage();
	}
	
	public void initIhm() {
		Log.getInstance().logInfo("**********  Init IHM  **********");
		this.gay.init(this.clavier);
		this.joystick.selectJoystick(this.joystick.getConfJoystick().getNumeroJoystick());
	}
	
	public void initJoystick(){
		Log.getInstance().logInfo("**********  Init Joystick  **********");
		this.joystick.init();
		
		Log.getInstance().logWarning("Liste des joysticks disponibles :", false);
		for (int i=0;i<this.joystick.getJoystickNames().size();i++)	{
			Log.getInstance().logWarning("\t" + this.joystick.getJoystickNames().get(i), false);
		}
	}
	
	public Controleur getControleur() {
		return controleur;
	}

	public GestionnaireJoystick getJoystick() {
		return joystick;
	}
}
