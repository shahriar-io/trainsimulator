package jts.io;

import java.util.HashMap;

public enum ToucheClavier {

	ZERO,
	UN,
	DEUX,
	TROIS,
	QUATRE,
	CINQ,
	SIX,
	SEPT,
	HUIT,
	NEUF,
	GAUCHE,
	HAUT,
	DROIT,
	BAS,
	SHIFT,
	CTRL,
	ESPACE,
	ENTREE,
	PLUS,
	MOINS,
	A,
	B,
	C,
	D,
	E,
	F,
	G,
	H,
	I,
	J,
	K,
	L,
	M,
	N,
	O,
	P,
	Q,
	R,
	S,
	T,
	U,
	V,
	W,
	X,
	Y,
	Z;
	
	public static HashMap<Integer,ToucheClavier> reverseTouche = new HashMap<Integer,ToucheClavier> ();
	
	static {
        for (ToucheClavier m : ToucheClavier.values()) {
        	reverseTouche.put(m.ordinal(), m);
        }
    }
	
}
