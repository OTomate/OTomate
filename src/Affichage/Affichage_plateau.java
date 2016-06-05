package Affichage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import Otomate.Personnage;
import Otomate.Grille;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Affichage_plateau extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int TAILLECASE = 16;
	
	private Grille gr;
	private List<Personnage> perso;
    private List<BufferedImage> tiles;
	
	void Affiche_perso(Graphics graph,int i,int x,int y){
            graph.setColor(Color_int(11));
            graph.fillOval(TAILLECASE*x, TAILLECASE*y, TAILLECASE, TAILLECASE);
	}
        
        void loadTiles() {
            tiles = new ArrayList<>();
            BufferedImage img = null;
            BufferedImage img2 = null;
            try {
                img = ImageIO.read(new File("..\\Graphics\\Tiles\\grass.jpg")); // Version Windows
             //	  img = ImageIO.read(new File("../Graphics/Tiles/grass.jpg")); //Version Linux
                tiles.add(img);
                  img2 = ImageIO.read(new File("../Graphics/Tiles/build.jpg"));
             //   img2 = ImageIO.read(new File("..\\Graphics\\Tiles\\build.jpg"));
                tiles.add(img2);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
	
	Color Color_int(int i){
            switch(i){
                case 1:
                return Color.BLACK;
                case 2:
                return Color.lightGray;
                case 3:
                return Color.blue;
                case 4:
                return Color.green;
                case 5:
                return Color.gray;
                case 6:
                return Color.yellow;
                case 7:
                return Color.cyan;
                case 8:
                return Color.DARK_GRAY;
                case 9:
                return Color.magenta;
                case 10:
                return Color.ORANGE;
                case 11:
                return Color.PINK;
                default:
                return Color.white;
            }
	}
	
	void Affiche_case(Graphics graph,int image,int x,int y){
            //System.out.println("case " + x + " "  + y + " avec image : " + image + " faites !");
            graph.setColor(Color_int(image));
            graph.fillRect(TAILLECASE*x,TAILLECASE*y, TAILLECASE, TAILLECASE);
            if(tiles.size() >= image)
                graph.drawImage(tiles.get(image), x, y, TAILLECASE, TAILLECASE, null);
	}
	
	Affichage_plateau(Grille g,List<Personnage> perso){
            gr =g ;
            this.perso=perso;
            loadTiles();
	}
	
        @Override
        public void paintComponent(Graphics g){
            super.paintComponents(g);

            int Nb = perso.size();
            int maxx=this.getWidth();
            int maxy=this.getHeight();
            int i,j;
            TAILLECASE=16;
            // System.out.println("Version 1 : Width : " + this.getWidth() + " Height : " + this.getHeight());

            this.setPreferredSize( new Dimension(gr.tailleX*17,gr.tailleY*17));
            maxx=maxx/TAILLECASE;
            maxy=maxy/TAILLECASE;

            if(gr.tailleX<maxx){
                maxx=gr.tailleX;
            }

            if(gr.tailleY<maxy){
                maxy=gr.tailleY;
            }


            if(maxx<(this.getWidth()/16) || maxy<(this.getHeight()/16)){
                TAILLECASE=((this.getWidth())/maxx);

                if(TAILLECASE > (this.getHeight()/maxy)){
                    //System.out.println("je rendre l� !");
                    TAILLECASE=((this.getHeight())/maxy);
                }
            }

            TAILLECASE--;
            System.out.println("Width : " + this.getWidth() + " Height : " + this.getHeight());



            // System.out.println("coucou " + maxx + " " + maxy);	
            for(j=0;j<maxy;j++){
                for(i=0;i<maxx;i++){
                    Affiche_case(g,gr.get(i,j).element,i,j);
                }
            }

            for(i=0;i<Nb;i++){
                Affiche_perso(g,i,perso.get(i).position.getX(),perso.get(i).position.getY());
            }

            }
	
        }
