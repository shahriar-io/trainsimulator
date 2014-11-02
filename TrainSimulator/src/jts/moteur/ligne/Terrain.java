package jts.moteur.ligne;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.Controleur;

public class Terrain {

	private static int PROFONDEUR_MAX = 9;
	/**Tableau des puissances de 2*/
	private static int[] PUISSANCE2;
	
	//public static int DIMENSION;

	private float[] hauteurs;
	/**Profondeur de cette instance de terrain*/
	private int profondeurMax;
	/**Dimension du terrain (nb de points sur un axe)*/
	private int nbPts;
	/**Dimension du terrain (nb d'intervalles sur un axe)*/
	private int nbMailles;
	private List<Boolean> details;

	public Terrain(){
		this.details = new ArrayList<Boolean>();
	}
	
	public void init(int profondeur){
		this.profondeurMax = profondeur;
		nbMailles = PUISSANCE2[profondeur];
		nbPts = nbMailles+1;
		this.hauteurs = new float[nbPts*nbPts];
	}

	public static void init(){
		PUISSANCE2 = new int[PROFONDEUR_MAX+1];
		for(int i=0; i<=PROFONDEUR_MAX; i++){
			PUISSANCE2[i] = (int)Math.pow(2, i);
		}
	}
	
	public int getNbPoints(){
		return this.nbPts;
	}
	
	public int getNbMailles(){
		return this.nbMailles;
	}
	
	public float[] getHauteurs(){
		return this.hauteurs;
	}

	public float getHauteur(int i, int j){
		return hauteurs[nbPts*i + j];
	}

	public void setHauteur(int i, int j, float h){
		hauteurs[nbPts*i + j] = h;
	}

	public void getOrigine(int profondeur, int indice, int[] origine){
		if(profondeur == 0){
			origine[0] = 0;
			origine[1] = 0;
		} else {
			getOrigine(profondeur-1, indice/4, origine);
			switch (indice%4) {
			case 1:
				origine[0] += PUISSANCE2[profondeurMax - profondeur];
				break;

			case 2:
				origine[1] += PUISSANCE2[profondeurMax - profondeur];
				break;

			case 3:
				origine[0] += PUISSANCE2[profondeurMax - profondeur];
				origine[1] += PUISSANCE2[profondeurMax - profondeur];
				break;

			default:
				break;
			}
		}
	}

	public List<List<Boolean>> getDetails(){
		List<List<Boolean>> liste2liste = new ArrayList<List<Boolean>>();

		List<Boolean> liste = new ArrayList<Boolean>();
		liste.add(details.get(0));
		liste2liste.add(liste);
		int indiceBool = 1;

		for(int profondeur = 1; profondeur<profondeurMax; profondeur++){
			liste = new ArrayList<Boolean>();
			for(boolean b : liste2liste.get(profondeur-1)){
				if(b){
					for(int i=0; i<4; i++){
						liste.add(details.get(indiceBool));
						indiceBool ++;
					}
				} else {
					liste.add(false);
					liste.add(false);
					liste.add(false);
					liste.add(false);
				}
			}
			liste2liste.add(liste);
		}

		return liste2liste;
	}

	public void setDetails(List<List<Boolean>> liste2liste, boolean strict){
		//Préparation en cas de listes "permissives"
		if(!strict){
			for(int i=liste2liste.size()-1; i>=1; i--){
				List<Boolean> listeParent = liste2liste.get(i-1);
				List<Boolean> liste = liste2liste.get(i);
				for(int j=0; j<liste.size(); j++){
					boolean b = liste.get(j);
					if(b){
						listeParent.set(j/4, true);
					}
				}
			}
		}

		//Remplissage de la liste "condensée"
		details.clear();
		details.add(liste2liste.get(0).get(0));
		for(int i=1; i<liste2liste.size(); i++){
			List<Boolean> listeParent = liste2liste.get(i-1);
			List<Boolean> liste = liste2liste.get(i);
			for(int j=0; j<liste.size(); j++){
				boolean bParent = listeParent.get(j/4);
				boolean b = liste.get(j);
				if(bParent){
					details.add(b);
				} else if(strict){
					liste.set(j, false);
				}
			}
		}
	}

	public void load(File file) throws IOException{
		DataInputStream dis = new DataInputStream(new FileInputStream(file));

		int profondeur = dis.readShort();
		if(profondeur <= PROFONDEUR_MAX){
			this.init(profondeur);
			boolean brut = dis.readBoolean();
			if(brut){
				loadBrut(dis);
			} else {
				loadPartiel(dis);
			}
		} else {
			this.init(0);
			Controleur.LOG.error("Profondeur trop grande : " + profondeur + " / Profondeur MAX : " + PROFONDEUR_MAX);
		}
		
		dis.close();
	}
	
	public void save(File file, boolean brut) throws IOException{
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));

		dos.writeShort(profondeurMax);
		dos.writeBoolean(brut);
		if(brut){
			saveBrut(dos);
		} else {
			savePartiel(dos);
		}

		dos.close();
	}

	private void loadBrut(DataInputStream dis) throws IOException{
		for(int i=0; i<nbPts; i++){
			for(int j=0; j<nbPts; j++){
				setHauteur(i, j, dis.readFloat());
			}
		}
	}
	
	private void saveBrut(DataOutputStream dos) throws IOException{
		for(int i=0; i<nbPts; i++){
			for(int j=0; j<nbPts; j++){
				dos.writeFloat(getHauteur(i, j));
			}
		}
	}

	private void loadPartiel(DataInputStream dis) throws IOException{
		boolean[][] dejaChargees = new boolean[nbPts][nbPts];
		
		//Lecture du tableau de booléens
		int indiceBit = 0;
		byte boolConcat = 0;
		int nbBool = 1;
		for(int profondeur = 0; profondeur<profondeurMax; profondeur++){
			int nbTrue = 0;
			for(int i=0; i<nbBool; i++){
				//Décalage des indices pour lire le bit booléen suivant
				indiceBit--;
				if(indiceBit<0){
					indiceBit = 7;
					boolConcat = dis.readByte();
				}

				int bool = (boolConcat >> indiceBit)&0x01;
				if(bool == 1){
					details.add(true);
					nbTrue++;
				} else {
					details.add(false);
				}
			}
			//On a 4 fois plus de parcelles au niveau suivant
			nbBool = nbTrue*4;
		}

		loadHauteur(0, 0, dis, dejaChargees);
		loadHauteur(PUISSANCE2[profondeurMax], 0, dis, dejaChargees);
		loadHauteur(0, PUISSANCE2[profondeurMax], dis, dejaChargees);
		loadHauteur(PUISSANCE2[profondeurMax], PUISSANCE2[profondeurMax], dis, dejaChargees);
		List<List<Boolean>> liste2liste = getDetails();
		int[] origine = new int[2];
		for(int i=0; i<profondeurMax; i++){
			List<Boolean> liste = liste2liste.get(i);
			int delta = PUISSANCE2[profondeurMax-i-1];
			for(int j=0; j<liste.size(); j++){
				if(liste.get(j)){
					this.getOrigine(i, j, origine);
					loadHauteur(origine[0]+delta, 	origine[1], dis, dejaChargees);
					loadHauteur(origine[0], 		origine[1]+delta, dis, dejaChargees);
					loadHauteur(origine[0]+delta, 	origine[1]+delta, dis, dejaChargees);
					loadHauteur(origine[0]+2*delta, origine[1]+delta, dis, dejaChargees);
					loadHauteur(origine[0]+delta, 	origine[1]+2*delta, dis, dejaChargees);
				}
			}
		}
		
		//On extrapole les données manquantes
		for(int i=profondeurMax-1; i>0; i--){
			List<Boolean> liste = liste2liste.get(i);
			int delta = PUISSANCE2[profondeurMax-i];
			for(int j=0; j<liste.size(); j++){
				if(!liste.get(j) && liste2liste.get(i-1).get(j/4)){
					//On traite les bords
					this.getOrigine(i, j, origine);
					for(int k=1; k<delta; k++){
						double h11 = getHauteur(origine[0], origine[1]);
						double h1n = getHauteur(origine[0], origine[1]+delta);
						double hn1 = getHauteur(origine[0]+delta, origine[1]);
						double hnn = getHauteur(origine[0]+delta, origine[1]+delta);
						double hauteur;
						double dk = (double)k;
						hauteur = h11*(1-dk/delta) + hn1*dk/delta;
						loadHauteur(origine[0]+k, origine[1], (float)hauteur, dejaChargees);
						hauteur = h1n*(1-dk/delta) + hnn*dk/delta;
						loadHauteur(origine[0]+k, origine[1]+delta, (float)hauteur, dejaChargees);
						hauteur = h11*(1-dk/delta) + h1n*dk/delta;
						loadHauteur(origine[0], origine[1]+k, (float)hauteur, dejaChargees);
						hauteur = hn1*(1-dk/delta) + hnn*dk/delta;
						loadHauteur(origine[0]+delta, origine[1]+k, (float)hauteur, dejaChargees);
					}
					
					//On remplit au milieu
					for(int k=1; k<delta; k++){
						for(int l=1; l<delta; l++){
							double dk = (double)k;
							double dl = (double)l;
							float h1l = getHauteur(origine[0], 		origine[1]+l);
							float hnl = getHauteur(origine[0]+delta,origine[1]+l);
							float hk1 = getHauteur(origine[0]+k, 	origine[1]);
							float hkn = getHauteur(origine[0]+k, 	origine[1]+delta);
							double hauteur = (h1l*(1-dk/delta) + hnl*dk/delta + hk1*(1-dl/delta) + hkn*dl/delta)/2;
							loadHauteur(origine[0]+k, origine[1]+l, (float)hauteur, dejaChargees);
						}
					}
				}
			}
		}
	}

	private void savePartiel(DataOutputStream dos) throws IOException{
		boolean[][] dejaSauvees = new boolean[nbPts][nbPts];
		
		//Ecriture du tableau de booléens, concaténés en entier
		int iDetails = 0;
		int boolConcat;
		while((iDetails+7)<details.size()){
			boolConcat = 0;
			for(int i=0; i<8; i++){
				byte bool = details.get(iDetails+i)?(byte)1:0;
				boolConcat = boolConcat | bool << (7-i);
			}
			dos.writeByte(boolConcat);
			iDetails += 8;
		}
		boolConcat = 0;
		for(int i=0; i<details.size()-iDetails; i++){
			byte bool = details.get(iDetails+i)?(byte)1:0;
			boolConcat = boolConcat | bool << (7-i);
		}
		dos.writeByte(boolConcat);

		//Ecriture des données altimétriques
		saveHauteur(0, 0, dos, dejaSauvees);
		saveHauteur(PUISSANCE2[profondeurMax], 0, dos, dejaSauvees);
		saveHauteur(0, PUISSANCE2[profondeurMax], dos, dejaSauvees);
		saveHauteur(PUISSANCE2[profondeurMax], PUISSANCE2[profondeurMax], dos, dejaSauvees);
		List<List<Boolean>> liste2liste = getDetails();
		int[] origine = new int[2];
		for(int i=0; i<profondeurMax; i++){
			List<Boolean> liste = liste2liste.get(i);
			int delta = PUISSANCE2[profondeurMax-i-1];
			for(int j=0; j<liste.size(); j++){
				if(liste.get(j)){
					this.getOrigine(i, j, origine);
					saveHauteur(origine[0]+delta, 	origine[1], dos, dejaSauvees);
					saveHauteur(origine[0], 		origine[1]+delta, dos, dejaSauvees);
					saveHauteur(origine[0]+delta, 	origine[1]+delta, dos, dejaSauvees);
					saveHauteur(origine[0]+2*delta, origine[1]+delta, dos, dejaSauvees);
					saveHauteur(origine[0]+delta, 	origine[1]+2*delta, dos, dejaSauvees);
				}
			}
		}
	}
	
	/**Permet de charger les informations de hauteur de manière non-redondante.
	 * 
	 * @param i
	 * @param j
	 * @param dis
	 * @param dejaChargees la carte de booléens des hauteurs déjà chargées
	 * @throws IOException
	 */
	private void loadHauteur(int i, int j, DataInputStream dis, boolean[][] dejaChargees) throws IOException{
		if(!dejaChargees[i][j]){
			setHauteur(i, j, dis.readFloat());
			dejaChargees[i][j] = true;
		}
	}
	
	private void loadHauteur(int i, int j, float hauteur, boolean[][] dejaChargees) {
		if(!dejaChargees[i][j]){
			setHauteur(i, j, hauteur);
			dejaChargees[i][j] = true;
		}
	}
	
	/**Permet de sauvegarder les informations de hauteur de manière non-redondante.
	 * 
	 * @param i
	 * @param j
	 * @param dos
	 * @param dejaSauvees la carte de booléens des hauteurs déjà sauvées
	 * @throws IOException
	 */
	private void saveHauteur(int i, int j, DataOutputStream dos, boolean[][] dejaSauvees) throws IOException{
		if(!dejaSauvees[i][j]){
			dos.writeFloat(getHauteur(i, j));
			dejaSauvees[i][j] = true;
		}
	}
}
