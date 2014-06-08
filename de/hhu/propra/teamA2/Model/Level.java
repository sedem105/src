package de.hhu.propra.teamA2.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by Susanna und Karsten on 01.06.14.
 */
public class Level {

    int levelnummer;              // eingegebene Levelnummer
    Image img1, img2;   // für Hintergrund und Vordergrundbild
    int wurm1posx=0;    // initial auf 0 gesetzte Positionen der Würmer
    int wurm1posy=0;    // sollte man noch Arrays draus machen und auch unten die getter und setter anpassen
    int wurm2posx=0;    // und dann auch für eine beliebige anzahl Würmer (oder zumindest innerhalb der maximal wählbaren Spieler- und Würmerzahl
    int wurm2posy=0;
    int wurm3posx=0;
    int wurm3posy=0;
    int wurm4posx=0;
    int wurm4posy=0;

    public void level(int levelnummer){
        ImageIcon bg = new ImageIcon("src/res/lvl_"+levelnummer+"_bg.png");   // nach Eingabe der Levelnummer x wird der Dateipfad für das Hintergrundbilds konstruiert
        img1 = bg.getImage();                                       // und dann das Bild geladen
        ImageIcon fg = new ImageIcon("src/res/lvl_"+levelnummer+".png");      // nach Eingabe der Levelnummer x wird der Dateipfad für das Vordergrundbilds konstruiert
        img2 = fg.getImage();                                       // und dann das Bild geladen

        if(levelnummer==1) {
            wurm1posx = 300;        // Level-abhängige Startpositionen der Würmer
            wurm1posy = 142;        // sollten wir in eine Text-Datei auslagern
            wurm2posx = 400;        // und wie oben schon gesagt: für mehr als nur 3 Würmer schreiben
            wurm2posy = 142;
            wurm3posx = 500;
            wurm3posy = 142;
            wurm4posx = 540;
            wurm4posy = 248;
        }
        if(levelnummer==2) {
            wurm1posx = 250;
            wurm1posy = 228;
            wurm2posx = 490;
            wurm2posy = 218;
            wurm3posx = 550;
            wurm3posy = 218;
            wurm4posx = 590;
            wurm4posy = 218;
        }
        if(levelnummer==3) {
            wurm1posx = 250;
            wurm1posy = 181;
            wurm2posx = 400;
            wurm2posy = 181;
            wurm3posx = 550;
            wurm3posy = 181;
            wurm4posx = 590;
            wurm4posy = 181;
        }
    }
    //test:
    Rectangle2D.Double block1_1_ = new Rectangle2D.Double(249,187,289,221);     // mittlerer Block, (Input sind: x-Wert, y-Wert, Breite, Höhe)
    public Shape getShapes(){
        return block1_1_;
    }

    public Image getBg(){
        return img1;
    }   // übergibt dem Aufrufenden das Hintergrundbild
    public Image getFg(){
        return img2;
    }   // übergibt dem Aufrufenden das Vordergrundbild

    public int getWurm1posx(){
        return wurm1posx;
    }   // übergibt dem Aufrufenden die Postionen der (noch nur 3) Würmer
    public int getWurm2posx(){
        return wurm2posx;
    }
    public int getWurm3posx(){
        return wurm3posx;
    }
    public int getWurm4posx(){
        return wurm4posx;
    }
    public int getWurm1posy(){
        return wurm1posy;
    }
    public int getWurm2posy(){
        return wurm2posy;
    }
    public int getWurm3posy(){
        return wurm3posy;
    }
    public int getWurm4posy(){
        return wurm4posy;
    }
}
