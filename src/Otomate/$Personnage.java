package Otomate;

import java.awt.Color;
import java.util.List;

import Actions.$Action;
import ImageEditor.ImageColor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public abstract class $Personnage {

	// Attributs
	protected int viemax;
	protected int etat;
	protected Coordonnees position;
	protected Automate a;
	protected int inventaire;
	protected String nom;
	protected int dmg;
	protected Color couleur;
        protected BufferedImage sprite = null;
        protected String spriteURL = "../Graphics/Chara/1.png";

	// Constructeur
	protected $Personnage(String file, Color couleur) {
            a = new Automate(file);
            etat = a.etat_initial();
            position = new Coordonnees(0, 0);
            viemax = 100;
            inventaire = 0;
            nom = "Bob";
            dmg = 10;
            this.couleur = couleur;
	}
	protected $Personnage($Personnage cpy) {
		a = cpy.getAutomate();
		etat = a.etat_initial();
		position = new Coordonnees(0, 0);
		viemax = 100;
		inventaire = 0;
		nom = "Bob";
		dmg = 10;
		couleur = cpy.getCouleur();
                sprite = cpy.getSprite();
	}

	// M�thodes

	public int nbEtat() {
		return a.nbetats();
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int symbole) {
		if (a.transitions[symbole][etat] != 0)
			etat = a.transitions[symbole][etat];
	}

	public Automate getAutomate() {
		return a;
	}

	public int getViemax() {
		return viemax;
	}

	public void setViemax(int Vie) {
		this.viemax = Vie;
	}

	public int getDmg() {
		return dmg;
	}

	public Coordonnees getPosition() {
		return this.position;
	}

	public void setPosition(Coordonnees c) {
		position = c;
	}

	public int getInventaire() {
		return inventaire;
	}

	public void setInventaire(int Inventaire) {
		this.inventaire = Inventaire;
	}

	public String getNomHtml() {
		return "<font color=\"rgb(" + couleur.getRed() + "," + couleur.getGreen() + "," + couleur.getBlue() + ")\">"
				+ nom + "</font>";
	}

	public String getNom() {
		return nom;
	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color c) {
		this.couleur = c;
	}
        
        public BufferedImage getSprite() {
            return this.sprite;
        }
        
        public void setSpriteURL(String url) {
            this.spriteURL = url;
        }
    
    //Override
    public String toString(){
    	String s = "PV : " + viemax + "\n";
    	s += "Inventaire : " + /*Contenus.fromint(inventaire).toString() +*/ "\n";
    	s += "Position : " + position.toString() + "\n";
    	s += "Automate : " + a.toString() +"\n";
    	s += "Etat courant : " + etat;
    	return s;
    }
    
    /**
     * 
     * @param listCond, la liste des conditions
     * @param G, la grille
     * @param listCont, la liste des contenus de cases
     * @param listJoueur, la liste des joueurs
     * @return La description textuelle de l'action effectu�e
     */
	public String jouer(List<Conditions2> listCond, Grille G,List<Objet> listCont,List<Joueur> listJoueur) {
		List<Boolean> lb = G.recupcond(this, listCond, listCont, listJoueur);
		List<Integer> lc = G.conditions(this, lb);
		List<Integer> la = G.actionsPossibles(this, lc);
		$Action actionAFaire = G.takeOne(la);
		G.Maj(this, actionAFaire, listJoueur, lc);
		return actionAFaire.toString();
	}
}