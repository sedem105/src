package de.hhu.propra.teamA2.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Karsten und Susanna on 25.05.14.
 */
public class Pistole extends Waffe {
    private String name = "Pistole";
    private int damage = 10;
    Image img;                  // das Bild des Wurms, der auf das Board geladen werden soll
    ImageIcon img_waffe;

    public Pistole() {
        img_waffe = new ImageIcon("src/res/pistole.png");
        img = img_waffe.getImage();
        this.id=3;
    }

    public Image getImage(){
        return img;
    }

    public String getName(){
        return this.name;
    }

    public int angreifen(){
        return damage;
    }

}
