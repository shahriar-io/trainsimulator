package jts.ihm.clavier;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jts.ihm.gui.InterfaceGraphique;
import jts.io.ToucheClavier;


public class EcouteurClavier implements KeyListener, InterfaceClavier{
	
	private boolean[] touches;
	private InterfaceGraphique gay;
	
	public EcouteurClavier(InterfaceGraphique gay){
		this.gay = gay;
		this.touches = new boolean[ToucheClavier.values().length];
	}
	
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		//System.out.println(event.getKeyCode());
		
		//Chiffre haut clavier
		if ((code>=48)&&(code<=57)){
			this.touches[code - 48] = true;
		}
		
		//Fleches
		if ((code>=37)&&(code<=40)){
			this.touches[code - 37 + ToucheClavier.GAUCHE.ordinal()] = true;
		}
		
		//SHIFT CTRL
		if ((code==16)||(code==17)){
			this.touches[code - 16 + ToucheClavier.SHIFT.ordinal()] = true;
		}
		
		//ESPACE
		if (code==32){
			this.touches[code - 32 + ToucheClavier.ESPACE.ordinal()] = true;
		}
		
		//ENTREE
		if (code==10){
			this.touches[code - 10 + ToucheClavier.ENTREE.ordinal()] = true;
		}
		
		//LETTRES
		if ((code>=65)&&(code<=90)){
			this.touches[code - 65 + ToucheClavier.A.ordinal()] = true;
		}
		
		//Plus moins
		if (code==107){
			this.touches[code - 107 + ToucheClavier.PLUS.ordinal()] = true;
		}
		
		if(code==109){
			this.touches[code - 109 + ToucheClavier.MOINS.ordinal()] = true;
		}
	}

	public void keyReleased(KeyEvent event) {
		int code = event.getKeyCode();
		
		//Chiffre haut clavier
		if ((code>=48)&&(code<=57)){
			this.touches[code - 48] = false;
		}
		
		//Fleches
		if ((code>=37)&&(code<=40)){
			this.touches[code - 37 + ToucheClavier.GAUCHE.ordinal()] = false;
		}
		
		//SHIFT CTRL
		if ((code==16)||(code==17)){
			this.touches[code - 16 + ToucheClavier.SHIFT.ordinal()] = false;
		}
		
		//ESPACE
		if (code==32){
			this.touches[code - 32 + ToucheClavier.ESPACE.ordinal()] = false;
		}
		
		//ENTREE
		if (code==10){
			this.touches[code - 10 + ToucheClavier.ENTREE.ordinal()] = false;
		}
		
		//LETTRES
		if ((code>=65)&&(code<=90)){
			this.touches[code - 65 + ToucheClavier.A.ordinal()] = false;
		}
		
		//Plus moins
		if (code==107){
			this.touches[code - 107 + ToucheClavier.PLUS.ordinal()] = false;
		}
		
		if(code==109){
			this.touches[code - 109 + ToucheClavier.MOINS.ordinal()] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	public boolean[] getTouchePressee() {
		return this.touches;
	}

}
