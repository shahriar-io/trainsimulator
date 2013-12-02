package jts.ihm.gui.render.jme;

import jts.ihm.clavier.InterfaceClavier;
import jts.ihm.clavier.ToucheClavier;

import com.jme3.input.controls.ActionListener;

public class JtsActionListener implements ActionListener {
	
	private InterfaceClavier clavier;

	public JtsActionListener(InterfaceClavier clavier){
		this.clavier = clavier;
	}

	public void onAction(String name, boolean keyPressed, float tpf) {
		if (name.equals("A")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.A);
		}
		if (name.equals("B")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.B);
		}
		if (name.equals("C")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.C);
		}
		if (name.equals("D")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.D);
		}
		if (name.equals("E")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.E);
		}
		if (name.equals("F")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.F);
		}
		if (name.equals("G")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.G);
		}
		
		if (name.equals("Q")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.Q);
		}
	}
}
