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
		if (name.equals("H")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.H);
		}
		if (name.equals("I")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.I);
		}
		if (name.equals("J")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.J);
		}
		if (name.equals("K")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.K);
		}
		if (name.equals("L")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.L);
		}
		if (name.equals("M")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.M);
		}
		if (name.equals("N")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.N);
		}
		if (name.equals("O")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.O);
		}
		if (name.equals("P")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.P);
		}
		if (name.equals("Q")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.Q);
		}
		if (name.equals("R")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.R);
		}
		if (name.equals("S")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.S);
		}
		if (name.equals("T")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.T);
		}
		if (name.equals("U")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.U);
		}
		if (name.equals("V")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.V);
		}
		if (name.equals("W")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.W);
		}
		if (name.equals("X")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.X);
		}
		if (name.equals("Y")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.Y);
		}
		if (name.equals("Z")) {
			clavier.setTouchePressee(keyPressed, ToucheClavier.Z);
		}
	}
}
