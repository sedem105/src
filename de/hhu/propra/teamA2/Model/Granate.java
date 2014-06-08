package de.hhu.propra.teamA2.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Karsten und Susanna on 25.05.14.
 */
public class Granate extends Waffe {
    private String name = "Granate";
    private int damage = 20;
    Image img;                  // das Bild des Wurms, der auf das Board geladen werden soll
    ImageIcon img_waffe;

    public Granate() {
        img_waffe = new ImageIcon("src/res/granate.png");
        img = img_waffe.getImage();
        this.id=2;
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
