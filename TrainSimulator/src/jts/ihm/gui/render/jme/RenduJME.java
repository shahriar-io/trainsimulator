package jts.ihm.gui.render.jme;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import jts.ihm.gui.render.InterfaceMoteur3D;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.ObjetScene;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;

public class RenduJME extends SimpleApplication implements InterfaceMoteur3D {
	
	private int width;
	private int height;

	private List<ObjetScene> objetsAjout;
	private float x;
	private float y;
	private float z;
	private float theta;
	private float phi;

	public RenduJME(int width, int height){
		super();
		this.width = width;
		this.height = height;
		this.objetsAjout = new ArrayList<ObjetScene>();
		
		AppSettings settings = new AppSettings(true);
		settings.setWidth(width);
		settings.setHeight(height);
		settings.setSamples(16);
		//settings.setUseInput(false);
		
		this.setSettings(settings);
		this.createCanvas();
		
		JmeCanvasContext ctx = (JmeCanvasContext)this.getContext();
		//ctx.setSystemListener(this);
		Dimension dim = new Dimension(width, height);
		ctx.getCanvas().setPreferredSize(dim);
		
		this.setPauseOnLostFocus(false);
	}
	
	public void simpleInitApp() {
		assetManager.registerLocator(".", FileLocator.class);
		
		AmbientLight sun = new AmbientLight();
		//DirectionalLight sun = new DirectionalLight();
		//sun.setDirection((new Vector3f(-1, -4, -1).normalizeLocal()));
        rootNode.addLight(sun);
        
        cam.setFrustumPerspective(45, (float)height/(float)width, 1, 2000);
        
        creerTerrain();
        creerCiel();
	}

	public void creerTerrain() {
		Material mat_terrain = new Material(assetManager, 
                "Common/MatDefs/Terrain/Terrain.j3md");
     
        mat_terrain.setTexture("Alpha", assetManager.loadTexture(
                "data/objets/textures/alphamap.png"));
     
        Texture grass = assetManager.loadTexture(
                "data/objets/textures/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex1", grass);
        mat_terrain.setFloat("Tex1Scale", 64f);
        mat_terrain.setTexture("Tex3", grass);
        mat_terrain.setFloat("Tex3Scale", 128f);
     
        Texture dirt = assetManager.loadTexture(
                "data/objets/textures/dirt.jpg");
        dirt.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex2", dirt);
        mat_terrain.setFloat("Tex2Scale", 32f);
        
        int patchSize = 65;
        float[] heights = new float[512*512];
        TerrainQuad terrain = new TerrainQuad("my terrain", patchSize, 513, heights);
     
        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(-1500, 0, 0);
        terrain.setLocalScale(8f, 1f, 8f);
        rootNode.attachChild(terrain);
     
        TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
        terrain.addControl(control);
        
        System.out.println(inputManager);
	}

	public void creerCiel() {
		rootNode.attachChild(SkyFactory.createSky(
                assetManager, "data/objets/textures/ciel.jpg", true));
	}

	public Canvas getCanvas() {
		return ((JmeCanvasContext)this.getContext()).getCanvas();
	}
	
	public void deplacerCamera(float x, float y, float z, float theta, float phi) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.theta = theta;
		this.phi = phi;
	}
	
	public void simpleUpdate(float tpf){
		cam.setLocation(new Vector3f(x, y, z));
		Quaternion q = new Quaternion();
		q.fromAngles(0, (float)(theta), -phi);
		cam.setRotation(q);
		
		while(!objetsAjout.isEmpty()){
			ObjetScene objet = objetsAjout.get(0);
			Spatial rail = assetManager.loadModel("data/objets/" + objet.getNomObjet().substring(0, objet.getNomObjet().length()-4) + ".j3o");
			Node pivot = new Node("pivot");
	        rootNode.attachChild(pivot);
	        pivot.rotate(0,(float)(-objet.getAngle().getPsi()),0);
	        pivot.move((float)objet.getPoint().getY(), (float)objet.getPoint().getZ(), (float)objet.getPoint().getX());
	        pivot.attachChild(rail);
	        objetsAjout.remove(0);
		}
	}

	public void dessinerLigne(List<Point> ligne) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dessinerSurface(List<Point> frontiere) {
		// TODO Auto-generated method stub
		
	}

	public void chargerObjet(ObjetScene objet) {
		objetsAjout.add(objet);
	}

	@Override
	public void setHeure(float heure) {
		// TODO Auto-generated method stub
		
	}

}
