package de.hhu.propra.teamA2.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Karsten und Susanna on 25.05.14.
 */
public class Schwert extends Waffe {
    private String name = "Schwert";
    private int damage = 15;
    Image img;                  // das Bild des Wurms, der auf das Board geladen werden soll
    ImageIcon img_waffe;

    public Schwert() {
        img_waffe = new ImageIcon("src/res/schwert.png");
        img = img_waffe.getImage();
        this.id=4;
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
