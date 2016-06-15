package Otomate;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import Actions.*;
import Parser.ParserConditions;
import Parser.ParserObjet;

public class Grille {

	//Attributs
    private Case[][] g;
    private List<Coordonnees> coinsAutomates;
    private  List<Integer> nbetats;
    public  int tailleX;
    public  int tailleY;
    
    
    // Getteurs
    
    public int tailleX(){
    	return tailleX;
    }
    
    public int tailleY(){
    	return tailleY;
    }
    
    public List<Coordonnees> getCoinsAutomates(){
    	return coinsAutomates;
    }
    
    public Case get(int x,int y){
    	return g[x][y];
    }
    
    //Retourne la case de coordonnees c
    public Case Pos(Coordonnees c){
        return g[c.getX()][c.getY()];
    }
    
	public  List<Integer> getNbetats() {
		return nbetats;
	}
    
    // Setteurs
	
    public void set(int val, int x, int y){
    	g[x][y].setValeur(val);
    }

	public void setNbetats(List<Integer> nbetats) {
		this.nbetats = nbetats;
	}

	public void setTailleX(int integer) {
		tailleX = integer;
	}
	
	public void setTailleY(int integer) {
		tailleY = integer;
	}

	public void setP(boolean b, int i, int j) {
		g[i][j].setPiegee(b);
	}

    // Constructeur
    /**
     * Crée une grille de taille totale x*y
     * @param x
     * @param y
     */
    public Grille(int x, int y){
    	g = new Case[x][y];
    	tailleX=x;
    	tailleY=y;
    	int i,j;
    	for(i=0;i<tailleX;i++){
    		for(j=0;j<tailleY;j++){
    			g[i][j] = new Case();
    		}
    	}
    }
    
    /**
     * POUR DEBUG
     * Crée une grille carrée de taille 50*50
     */
    public Grille(){
    	g = new Case[50][50];
    	tailleX=50;
    	tailleY=50;
    	int i,j;
    	for(i=0;i<tailleX;i++){
    		for(j=0;j<tailleY;j++){
    			g[i][j] = new Case();
    		}
    	}
    }
    
    public Grille(List<Joueur> l){
    	int i,j;
        List<$Personnage> list = new LinkedList<>();
        for(i=0; i<l.size(); i++){
        	for(j=0;j<l.get(i).getSizePersonnages();j++){
        		list.add(l.get(i).getPersonnagesI(j));    	
        	}
        }
        List<Integer> taille = new LinkedList<Integer>();
        for(i=0; i<l.size(); i++){
            for(j=0; j<l.get(i).getSizePersonnages(); j++){
            	taille.add(l.get(i).getPersonnagesI(j).nbEtat());
            }
        }
        setNbetats(taille);
        int maxi = max(taille);
        int dimh = maxi * list.size();
        if(dimh<50)
            dimh = 50;
        maxi = l.get(0).getPersonnagesI(0).a.nbconditions();
        int dimv = maxi * list.size();
        if(dimv<50)
            dimv = 50;
            
      //cration die la map dimh/dimv avec minimum 150/150
        
        new Grille(dimh, dimv);
    }
    
    //Méthodes
    
    /**
	 * Fonction qui renvoie un enier aléatoire entre deux bornes
	 * @param min
	 * @param max
	 * @return un entier aléatoire entre min et max
	 */
	public static int random(int min, int max){
    	Random r = new Random();
    	int k = min + r.nextInt(max - min);

    	return k;
    }

    /**
     * Renvoie l'entier maximal d'une liste d'entiers
     * @param l, une liste d'entier
     * @return le maximum de la liste
     */
    public int max(List<Integer> l){
        int fin = l.size();
        int i, m=0;
        for(i=0; i<fin; i++){
            if(m<l.get(i))
                m=l.get(i);
        }
        return m;
    }
	
    //Place les automates au bon endroit sur la map
    public void Placements(List<Joueur> J) {
        int l = coinsAutomates.size();
        List<$Personnage> list = new LinkedList<>();
        int i,j,k,nbCond = J.get(0).getPersonnagesI(0).getAutomate().nbconditions();   //nbCond contient le nombre de condition (soit la "hauteur" de nos automates)
        for(i=0; i<l; i++){
        	for(j=0;j<J.get(i).getSizePersonnages();j++){
        	    list.add(J.get(i).getPersonnagesI(j));    	
        	}
	        }
	        
        for(i=0;i<l;i++){
        	int ix=coinsAutomates.get(i).getX();
        	int iy=coinsAutomates.get(i).getY();
        	int tailleix=list.get(i).nbEtat();
        	int tailleiy=list.get(i).getAutomate().nbconditions;
            for(j=i+1; j<l; j++){
            	int jx=coinsAutomates.get(j).getX();
            	int jy=coinsAutomates.get(j).getY();
            	int taillejx=list.get(j).nbEtat();
            	int taillejy=list.get(j).getAutomate().nbconditions;
                if(!(ix+tailleix>=jx && ix<=jx+taillejx && iy+tailleiy>=jy && iy<=jy+taillejy)){System.out.println("ok");}
            	else
                    System.out.println("Erreur les automates se superposent on a un probleme pour la generation de leur coordonnees : erreur dans goAutomates !");
            }
        }
        int jdeb, kdeb;
        int x=0, s, y;
        i=0;
        while(x<J.size())
        {
            y=0;
            s = J.get(x).getSizePersonnages();
            System.out.println("tamer");
            while(y<s && i<l)
            {            	
                    jdeb = coinsAutomates.get(i).getX();
                    for(j=jdeb; j<J.get(x).getPersonnagesI(y).nbEtat()+jdeb; j++){      //parcours de lignes
                        kdeb = coinsAutomates.get(i).getY();
                        for(k=kdeb;k<nbCond+kdeb;k++){          //parcours des colonnes
                            g[j][k] = J.get(x).getPersonnagesI(y).a.getActions(k-kdeb, j-jdeb);
                        }
                    }
                y++;
                i++;
            }
            
            x++;
        }
    }
    
    public  List<Coordonnees> goAutomates(List<$Personnage> list, int dimh, int dimv){
        List<Coordonnees> res = new LinkedList<Coordonnees>();
        Random rnd = new Random();
        int i, j, k;
        Coordonnees[] newc = new Coordonnees[list.size()];
        for(i=0;i<list.size();i++){
        	newc[i] = new Coordonnees();
        }
        int nb = list.size();
        
        for(k=0; k<list.size(); k++){
            i = rnd.nextInt(nb);       //donne le numro de la case "h" abscisse correspondant
            newc[k].setX(i*dimh/nb);
            j = rnd.nextInt(nb);
            newc[k].setY(j*dimv/nb);
            j=k;
            for(i=0; i<k; i++){
                if(newc[k].getX() == res.get(i).getX() && newc[k].getY() == res.get(i).getY()){
                    i=k;
                    k--;           // la c'est pour refaire le meme tour puisque la case est deja occupee
                }
            }
            if(j == k){             //c'est pour verifier qu'on est pas tomba dans le if et que c'est bon la case est dispo
                res.add(newc[k]);
            }
        }
        return res;
    }
    
    public void initialisergrille(List<Joueur> l) {
    	int i,j,k;
        for(i=0; i<tailleX; i++){
            for(j=0; j<tailleY; j++){
                k = random(0, 16);        //car 16 actions possibles numerotees de 0 a 15 
                g[i][j].element = k;
            }
        }

        List<$Personnage> list = new LinkedList<>();
        for(i=0; i<l.size(); i++){
        	for(j=0;j<l.get(i).getSizePersonnages();j++){
        		list.add(l.get(i).getPersonnagesI(j));    	
        	}
        }
        
        coinsAutomates = goAutomates(list, tailleX, tailleY);
        Placements(l);
    }
    
/**
 * Retourne une liste de condtions a partir du parser
 * @param filename
 * @return
 */
    public List<Conditions> condparser(String filename){
    	ParserConditions P = new ParserConditions(filename);
    	return P.list;
    }
    
/**
 * Retourne une liste d'objet a partir du parser
 * @param filename
 * @return
 */
    public List<Objet> objparser(String filename){
    	ParserObjet P = new ParserObjet(filename);
    	return P.list;
    }
    
/**
 * Retourne la liste de booleen indiquant les conditions possibles
 * @param p
 * @param lc
 * @param lo
 * @param lj
 * @return
 */
    public List<Boolean> recupcond($Personnage p, List<Conditions> lc, List<Objet> lo, List<Joueur> lj){
    	List<Boolean> res = new LinkedList<Boolean>();
    	int s = lc.size();
    	int i;
    	for(i=0; i<s; i++){
    		res.add(lc.get(i).estVrai(this, p.getPosition(), lo, p, lj));
    	}
    	return res;
    }
 // TODO CHANGER LES TRUCS PARKE 5 ICI WESH
/**
 * Retourne une liste de 6 entiers repr�sentant les differentes conditions
 * @param p
 * @param l
 * @return
 */
    public List<Integer> conditions($Personnage p, List<Boolean> l){
    	List<Integer> listcond = new LinkedList<>();
    								//**********CONDITION SUR CASE***************
    	if(l.get(9) == true)		//
    		listcond.add(9);		//
    	else if(l.get(10) == true)	//
    		listcond.add(10);		//
    	else if(l.get(15) == true)	//
    		listcond.add(15);		//
    	else if(l.get(16) == true)	//
    		listcond.add(16);		//
    	else if(l.get(17) == true)	//
    		listcond.add(17);
    	else listcond.add(0);
    	if(l.get(1) == true)		//**********CONDITION AU NORD****************
    		listcond.add(1);		//
    	else if(l.get(5) == true)	//
    		listcond.add(5);		//
    	else if(l.get(11) == true)	//
    		listcond.add(11);		//
    	if(l.get(3) == true)		//*********CONDITION EST*********************
    		listcond.add(3);		//
    	else if(l.get(7) == true)	//
    		listcond.add(7);		//
    	else if(l.get(13) == true)	//
    		listcond.add(13);		//
    	if(l.get(2) == true)		//************CONDITION SUD*******************
    		listcond.add(2);		//
    	else if(l.get(6) == true)	//
    		listcond.add(6);		//
    	else if(l.get(12) == true)	//
    		listcond.add(12);		//
    	if(l.get(4) == true)		//*************CONDITION OUEST***************
    		listcond.add(4);		//
    	else if(l.get(8) == true)	//
    		listcond.add(8);		//
    	else if(l.get(14) == true)	//
    		listcond.add(14);		//
    	return listcond;
    }
    
    /**
     * 
     * @param p
     * @param l
     * @return
     */
    public List<Integer> actionsPossibles($Personnage p, List<Integer> l){
    	List<Integer> la = new LinkedList<>();
    	int i, s = l.size();
    	for(i=0; i<s; i++){
    		if(p.getAutomate().transition(l.get(i), p.getEtat()-1) != 0){
    			la.add(p.getAutomate().getActions(l.get(i), p.getEtat()-1).getValeur()-1);
    			p.setEtat(p.getAutomate().transition(l.get(i), p.getEtat()-1));
    		}
    	}
    	return la;
    }
    
    /**
     * 
     * @param l
     * @return
     */
    public $Action takeOne(List<Integer> l){
        $Action a = null;
        if(l.isEmpty() == true){
            a = new RaF();
            return a;
        }
        else{
            int i = random(0, l.size());
            if(l.get(i) == 0)
            	a = new RaF();
            else if(l.get(i) == 1)
            	a = new DeplNord();
            else if(l.get(i) == 2)
            	a = new DeplSud();
            else if(l.get(i) == 3)
            	a = new DeplEst();
            else if(l.get(i) == 4)
            	a = new DeplOuest();
            else if(l.get(i) == 5)
            	a = new AttNord();
            else if(l.get(i) == 6)
            	a = new AttSud();
            else if(l.get(i) == 7)
            	a = new AttEst();
            else if(l.get(i) == 8)
            	a = new AttOuest();
            else if(l.get(i) == 9)
            	a = new Ramasser();
            else if(l.get(i) == 10)
            	a = new Pieger();
            else if(l.get(i) == 11)
            	a = new Manger();
            else if(l.get(i) == 12)
            	a = new Soigner();
            else if(l.get(i) == 13)
            	a = new Fuir();
            else if(l.get(i) == 14)
            	a = new Detruire();
            else if(l.get(i) == 15)
            	a = new Fouiller();
            return a;
        }
    }
    
//On rappelle que l'"origine" du repere de la grille est en haut  gauche donc un deplacement au nord = -1 en ord et +1 pour aller vers le sud cependant
//on garde +1 pour l'est en abs et -1 pour l'ouest
  
//Met a jour la map = change le numero si besoin est
    public void Maj($Personnage P, $Action A, List<Joueur> J, List<Integer> l){
    	int i,j;
    	List<$Personnage> list = new LinkedList<>();
    	int longperso = J.size();
    	for(i=0; i<longperso; i++){
        	for(j=0;j<J.get(i).getSizePersonnages();j++){
        	    list.add(J.get(i).getPersonnagesI(j));    	
        	}
        }
    	A.todo(l,P,list, this);
    }
}