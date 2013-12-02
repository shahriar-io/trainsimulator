package jts.moteur.train;

import java.util.ArrayList;
import java.util.List;

import jts.moteur.ligne.voie.elements.CourbeElementaire;

/**Cette classe représente un train, composé éventuellement d'une locomotive et de plusieurs voitures.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Train {
	
	private List<Locomotive> locomotives;
	private List<Wagon> wagons;
	
	private double masse;
	private double vitesse;
	private double commandeTraction;
	private int tamponCommande;
	private double commandeFrein;
	
	private Chemin chemin;
	
	public Train(){
		this.locomotives = new ArrayList<Locomotive>();
		this.wagons = new ArrayList<Wagon>();
		this.chemin = new Chemin();
	}
	
	public void initTrain(CourbeElementaire element, double abscisse, boolean sensDirect){
		this.locomotives.get(0).initPosition(element, abscisse, sensDirect);
		this.chemin.recalculerChemin(this.locomotives.get(0).getBogieAvant().getPositionCourbe());
	}
	
	public void avancer(double dt){
		double acceleration = 0;
		for(Locomotive locomotive : locomotives){
			acceleration += locomotive.getForceTraction(vitesse, commandeTraction) - 3.6*Math.signum(vitesse);
		}
		acceleration = acceleration * 1000 / masse;
		this.vitesse += acceleration*dt;
		boolean chgtElmt = wagons.get(0).avancer(vitesse*dt);
		if(chgtElmt){
			chemin.recalculerChemin(this.locomotives.get(0).getBogieAvant().getPositionCourbe());
		}
		for(int i=1; i<wagons.size(); i++){
			wagons.get(i).avancer(vitesse*dt);
		}
	}
	
	public Locomotive getLocomotiveTete(){ return this.locomotives.get(0); }
	
	public void addWagon(Wagon wagon){
		if(wagon instanceof Locomotive){
			this.locomotives.add((Locomotive)wagon);
		}
		this.wagons.add(wagon);
		this.masse += wagon.getMasse();
	}
	
	public List<Wagon> getWagons(){ return this.wagons; }
	
	public double getVitesse(){ return this.vitesse; }
	
	public double getCommandeTraction(){ return this.commandeTraction; }
	
	public void modifierCommandeVolant(double deltaCV){
		double nouvelleCommande = commandeTraction + (0.2*deltaCV);
		if (nouvelleCommande*commandeTraction<=0){
			if(tamponCommande < 20){
				tamponCommande++;
				nouvelleCommande = 0;
			} else {
				tamponCommande = 0;
			}
		}
		//Commande bornée entre -1 et 1
		commandeTraction = Math.min(Math.max(nouvelleCommande, -1), 1);
	}
	
	public void modifierCommandeFrein(double deltaCF){
		commandeFrein += 0.2*deltaCF;
		commandeFrein = Math.max(commandeFrein, -1);
		commandeFrein = Math.min(commandeFrein, 1);
	}
	
	public void switchNextDivergence(){
		this.chemin.getDivergences().get(0).switcher();
		this.chemin.recalculerChemin(this.locomotives.get(0).getBogieAvant().getPositionCourbe());
	}
}
