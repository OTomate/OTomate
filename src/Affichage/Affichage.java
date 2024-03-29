package Affichage;

import Otomate.Gentil;
import Otomate.Grille;
import Otomate.Joueur;
import Otomate.Mechant;
import Otomate.historique.Evenement;
import Otomate.historique.Historique;
import Otomate.historique.Tour;
import Otomate.$Personnage;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

public class Affichage {

    public static int random(int min, int max) {
        return (int) (min + (Math.random() * (max - min)));
    }
    //Attibuts
    private static FenetreJeu jeu;
    private static List<$Personnage> perso;

    private static Historique h;

    //Methodes    
    public static void main(String[] args) throws IOException {
        jeu = new FenetreJeu();

        Grille g = new Grille(20, 20);
        int i, j;
        for (i = 0; i < g.tailleX(); i++) {
            for (j = 0; j < g.tailleY(); j++) {
                g.set(random(1, 3), i, j);
            }
        }

        perso = new ArrayList<>();
        perso.add(new Gentil("/Parser/AutomateenXML.xml", Color.PINK,"bob"));
        perso.add(new Mechant("/Parser/AutomateenXML.xml", Color.BLACK,"john"));

        perso.get(0).getPosition().setX(1);
        perso.get(0).getPosition().setY(1);
        perso.get(1).getPosition().setX(5);
        perso.get(1).getPosition().setY(10);

        h = new Historique();
        h.addTour();
        h.ceTour().addEvenement(new Evenement(perso.get(0), "ne fait rien"));
        h.ceTour().addEvenement(new Evenement(perso.get(1), "ne fait rien"));
        h.addTour();
        h.ceTour().addEvenement(new Evenement(perso.get(0), "ne fait toujours rien"));
        h.ceTour().addEvenement(new Evenement(perso.get(1), "ne sait rien faire"));
        h.ceTour().addEvenement(new Evenement(perso.get(0), "s'ennuie"));
        h.ceTour().addEvenement(new Evenement(perso.get(1), "compte les nuages"));

        jeu.charger();
    }

    public static void recharger(Grille g, List<Joueur> l, Historique h) throws IOException {
        List<$Personnage> lp = new LinkedList<>();
        int i, j, max = l.size(), max2;

        for (i = 0; i < max; i++) {
            max2 = l.get(i).getSizePersonnages();
            for (j = 0; j < max2; j++) {
                lp.add(l.get(i).getPersonnagesI(j));
            }
        }
        jeu = new FenetreJeu();
        jeu.charger();

    }

    public static void again() {
        jeu.pan_plateau.repaint();
        jeu.majTabPersos();
    }

    public static void charger() throws IOException {
        jeu = new FenetreJeu();
        jeu.charger();

    }

    public static void ajouterTour(Tour t) {
        jeu.ajouterTourHistorique(t);
    }

	public static void fin() {
		JOptionPane.showMessageDialog(null,
                "La partie est finie !",
                "Victoire",
                JOptionPane.INFORMATION_MESSAGE,
                null);		
	}

}
