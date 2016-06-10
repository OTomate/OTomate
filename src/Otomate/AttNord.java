package Otomate;

import java.util.List;
import java.util.Random;

public class AttNord extends $Action{
	
	private boolean effect = false;
	int valeur = 5;
    
	public void todo(List<Integer> l, $Personnage p, List<$Personnage> lp){
		if(l.get(1) == 5){		// 5 = ennemi au nord et 1 regard au nord
			$Personnage e = null;
			Coordonnees card = p.position;
			card.setY(card.getY()-1);
			int s = lp.size();
			int i=0;
			while(i<s){
				if(lp.get(i).position == card){
					e = lp.get(i);
					i=s;
				}
			}
			if(p instanceof Gentil){
				if(e != null && e instanceof Mechant)
					((Mechant) e).vie -= p.dmg + ((Gentil) p).arme;
				
			}
			else
				if(e != null && e instanceof Gentil){
					((Gentil) e).vie -= p.dmg;
					if(Grille.random(0, 101) > 24)
						((Gentil)e).saignement = true;
					if(Grille.random(0,101) > 4)
						((Gentil)e).infecte = true;					
				}
			effect = true;
		}
	}
	
	public String toString(){
		if(effect == true)
			return("Le personnage attaque l'ennemi au nord.");
		else
			return ("Echec, pas d'ennemi au nord:D.");
	}
}