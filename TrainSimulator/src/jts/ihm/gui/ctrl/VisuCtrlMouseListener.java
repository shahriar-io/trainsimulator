package jts.ihm.gui.ctrl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class VisuCtrlMouseListener extends MouseAdapter implements MouseListener, MouseWheelListener, MouseMotionListener {

	private final static double RATIO_ZOOM = 1.2;
	
	private PanelVisuRoute visuRoute;
	
	private int xClic;
	private int yClic;
	
	private boolean isMolettePressed;
	
	public VisuCtrlMouseListener(PanelVisuRoute visuRoute){
		this.visuRoute = visuRoute;
		this.isMolettePressed = false;
	}
	
	public void mousePressed(MouseEvent event) {
		if(event.getButton() == MouseEvent.BUTTON2){
			isMolettePressed = true;
			xClic = event.getPoint().x;
			yClic = event.getPoint().y;
		}
	}

	public void mouseReleased(MouseEvent event) {
		if(event.getButton() == MouseEvent.BUTTON2){
			isMolettePressed = false;
			visuRoute.appliquerChangementPDV();
		}
	}

	public void mouseWheelMoved(MouseWheelEvent event) {
		this.visuRoute.appliquerFacteur(Math.pow(RATIO_ZOOM, event.getWheelRotation()));
	}

	public void mouseDragged(MouseEvent event) {
		if(isMolettePressed){
			visuRoute.appliquerDecalage(event.getX() - xClic, event.getY() - yClic);
		}
	}

	public void mouseMoved(MouseEvent event) {
		
	}
}
