package de.hhu.propra.teamA2.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 * Created by Susanna und Karsten on 01.06.14.
 */

public class WurmLeiche {
    private int x;             // Koordinaten, an denen sich der Wurm befindet
    private int y;
    private String farbe;
    Image img;                  // das Bild des Wurms, der auf das Board geladen werden soll
    ImageIcon img_dead;

    public WurmLeiche(String farbe){  // Hauptmethode der Wurm-Klasse

        img_dead = new ImageIcon("src/res/blubs_"+farbe+"_dead.png"); // nach Eingabe der Farbe wird der Dateipfad für das Bild konstruiert
        img = img_dead.getImage();         // und dann das Bild geladen

    }

    public Image getImage(){
        return img;
    }   // hier wird das geladene Bild an den Aufrufenden weitergegeben, im Moment das Board

    public int getX() {
        return x;
    }       // fragt die aktuelle Position des Wurms ab (hier: x-Wert, das können wir noch mit dem y-Wert unten zusammenfassen)
    public void setX(int x) {
        this.x = x;
    }   // bzw verändert die aktuelle Position des Wurms (hier: x-Wert, das können wir noch mit dem y-Wert unten zusammenfassen)
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    // hier einige weitere getter- und setter-Methoden für die obigen Variablen:
    public String getFarbe() {
        return farbe;
    }
    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

}