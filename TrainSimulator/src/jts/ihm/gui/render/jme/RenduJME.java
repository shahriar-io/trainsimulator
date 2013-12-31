package jts.ihm.gui.render.jme;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import jts.ihm.clavier.InterfaceClavier;
import jts.ihm.gui.render.InterfaceMoteur3D;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.ObjetScene;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
	private JtsActionListener actionListener;

	private List<ObjetScene> objetsAjout;
	private float x;
	private float y;
	private float z;
	private float theta;
	private float phi;

	public RenduJME(int width, int height, InterfaceClavier clavier){
		super();
		this.width = width;
		this.height = height;
		this.actionListener = new JtsActionListener(clavier);
		this.objetsAjout = new ArrayList<ObjetScene>();

		AppSettings settings = new AppSettings(true);
		settings.setWidth(width);
		settings.setHeight(height);
		settings.setSamples(8);
		settings.setFrameRate(60);
		//settings.setRenderer(AppSettings.LWJGL_OPENGL1);

		this.setSettings(settings);
		this.createCanvas();

		JmeCanvasContext ctx = (JmeCanvasContext)this.getContext();
		Dimension dim = new Dimension(width, height);
		ctx.getCanvas().setPreferredSize(dim);

		this.setPauseOnLostFocus(false);
		this.setDisplayStatView(false);
		this.setDisplayFps(false);
	}

	public void simpleInitApp() {
		System.out.println("Caps : " + renderer.getCaps());
		
		assetManager.registerLocator(".", FileLocator.class);

		AmbientLight sun = new AmbientLight();
		sun.setColor(ColorRGBA.Gray);
		rootNode.addLight(sun);

		DirectionalLight sun2 = new DirectionalLight();
		sun2.setColor(ColorRGBA.Gray);
		sun2.setDirection((new Vector3f(-1, -4, -1).normalizeLocal()));
		rootNode.addLight(sun2);

		cam.setFrustumPerspective(45, (float)height/(float)width, 1, 2000);

		creerTerrain();
		creerCiel();
		initClavier();
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
		terrain.setLocalScale(9f, 1f, 9f);
		rootNode.attachChild(terrain);

		TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
		terrain.addControl(control);

		System.out.println(inputManager);
	}

	public void creerCiel() {
		TextureKey tk = new TextureKey("data/objets/textures/ciel2.jpg", true);
        tk.setGenerateMips(true);
        tk.setAsCube(true);
        Texture tex = assetManager.loadTexture(tk);
        rootNode.attachChild(SkyFactory.createSky(assetManager, tex, tex, tex, tex, tex, tex));
	}
	
	public void initClavier() {
		inputManager.addMapping("A", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("B", new KeyTrigger(KeyInput.KEY_B));
		inputManager.addMapping("C", new KeyTrigger(KeyInput.KEY_C));
		inputManager.addMapping("D", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping("E", new KeyTrigger(KeyInput.KEY_E));
		inputManager.addMapping("F", new KeyTrigger(KeyInput.KEY_F));
		inputManager.addMapping("G", new KeyTrigger(KeyInput.KEY_G));
		inputManager.addMapping("H", new KeyTrigger(KeyInput.KEY_H));
		inputManager.addMapping("I", new KeyTrigger(KeyInput.KEY_I));
		inputManager.addMapping("J", new KeyTrigger(KeyInput.KEY_J));
		inputManager.addMapping("K", new KeyTrigger(KeyInput.KEY_K));
		inputManager.addMapping("L", new KeyTrigger(KeyInput.KEY_L));
		inputManager.addMapping("M", new KeyTrigger(KeyInput.KEY_M));
		inputManager.addMapping("N", new KeyTrigger(KeyInput.KEY_N));
		inputManager.addMapping("O", new KeyTrigger(KeyInput.KEY_O));
		inputManager.addMapping("P", new KeyTrigger(KeyInput.KEY_P));
		inputManager.addMapping("Q", new KeyTrigger(KeyInput.KEY_Q));
		inputManager.addMapping("R", new KeyTrigger(KeyInput.KEY_R));
		inputManager.addMapping("S", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("T", new KeyTrigger(KeyInput.KEY_T));
		inputManager.addMapping("U", new KeyTrigger(KeyInput.KEY_U));
		inputManager.addMapping("V", new KeyTrigger(KeyInput.KEY_V));
		inputManager.addMapping("W", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("X", new KeyTrigger(KeyInput.KEY_X));
		inputManager.addMapping("Y", new KeyTrigger(KeyInput.KEY_Y));
		inputManager.addMapping("Z", new KeyTrigger(KeyInput.KEY_Z));
		inputManager.addListener(actionListener,"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
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
