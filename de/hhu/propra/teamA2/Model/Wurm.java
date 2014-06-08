package de.hhu.propra.teamA2.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 * Created by Susanna und Karsten on 01.06.14.
 */

public class Wurm {
	private int hitpoints = 100;      // Lebenspunkte des Wurms. Wenn sie auf <= 0 sinken, ist der Wurm tot
	private int x,dx;             // Koordinaten, an denen sich der Wurm befindet
	private int y;
    private String farbe;
    Image img;                  // das Bild des Wurms, der auf das Board geladen werden soll
    ImageIcon img_left, img_right, img_dead;
    boolean alive = true;
    Waffe waffe_active;
    private int width, height;
    Bullet bullet;

    Bazooka bazooka = new Bazooka();
    Pistole pistole = new Pistole();
    Granate granate = new Granate();
    Schwert schwert = new Schwert();

    public Wurm(String farbe){  // Hauptmethode der Wurm-Klasse

        img_left = new ImageIcon("src/res/blubs_"+farbe+"_l.png"); // nach Eingabe der Farbe wird der Dateipfad für das Bild konstruiert
        img_right = new ImageIcon("src/res/blubs_"+farbe+"_r.png"); // nach Eingabe der Farbe wird der Dateipfad für das Bild konstruiert
        img_dead = new ImageIcon("src/res/blubs_"+farbe+"_dead.png"); // nach Eingabe der Farbe wird der Dateipfad für das Bild konstruiert
        img = img_left.getImage();         // und dann das Bild geladen

        //waffen = new LinkedList<Waffe>();
        waffe_active = bazooka;
        width = img.getWidth(null);
        height = img.getHeight(null);
        this.farbe=farbe;

    }

    public Waffe waffeActive(){
        return waffe_active;
    }
    public void setWaffe(int waffe){
        if(waffe==1)
            this.waffe_active = bazooka;
        if(waffe==2)
            this.waffe_active = pistole;
        if(waffe==3)
            this.waffe_active = granate;
        if(waffe==4)
            this.waffe_active = schwert;
    }
    public int getWaffe(){
        if(waffe_active==bazooka)return 1;
        if(waffe_active==pistole)return 2;
        if(waffe_active==granate)return 3;
        if(waffe_active==schwert)return 4;
        return 0;
    }

    public void takeDamage(int damage){
        if (alive) {
            hitpoints = Math.max(0,hitpoints-damage);
            if (hitpoints==0){
                this.alive = false;
                y += 15;
                img = img_dead.getImage();
            }
        }
    }
    public void ertrinken(){
        hitpoints = 0;
        this.alive = false;
        img = img_dead.getImage();

    }

    public Image getImage(){
        return img;
    }   // hier wird das geladene Bild an den Aufrufenden weitergegeben, im Moment das Board

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void move(){
        if (alive){
            if (dx == 1) {
                if ((x + dx) <= 750)
                    x = x + dx;
            }
            else if (dx == -1){
                if ((x + dx) >= 0)
                    x = x + dx;
            }
        }
    }

    public Bullet fire(){
        bullet = new Bullet(x+25,y+25);     // Offset, damit aus der Mitte des Wurms geschossen wird
        return bullet;
    }


    public void keyPressed(KeyEvent e){
        if (alive){
            int key = e.getKeyCode();
            char key_ = e.getKeyChar();              // der Eingabewert des Keys wird in einen charakter umgewandelt

            if (key == KeyEvent.VK_LEFT||key_=='q') {      // gehe und schaue nach links
                dx = -1;
                img = img_left.getImage();
            }
            if (key == KeyEvent.VK_RIGHT||key_=='e') {     // gehe und schaue nach rechts
                dx = 1;
                img = img_right.getImage();
            }
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        char key_ = e.getKeyChar();              // der Eingabewert des Keys wird in einen charakter umgewandelt

        if (key == KeyEvent.VK_LEFT||key_=='q')        // anhalten
            dx = 0;

        if (key == KeyEvent.VK_RIGHT||key_=='e')       // anhalten
            dx = 0;
    }

    public boolean getAlive(){
        return alive;
    }
    public int getHitpoints() {
        return hitpoints;
    }

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
    public int getXoffset() {
        return x+25;
    }   // damit die waffe von der Mitte des Wurms losschießt
    public int getYoffset() {
        return y+23;
    }   // damit die waffe von der Mitte des Wurms losschießt

    public void incX(){ this.x++;}
    public void decX(){ this.x--;}
    public void incY(){ this.y++;}
    public void decY(){ this.y--;}
    public int getDx(){ return dx;}

    // hier einige weitere getter- und setter-Methoden für die obigen Variablen:
    public String getFarbe() {
        return farbe;
    }
    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }
    public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

}