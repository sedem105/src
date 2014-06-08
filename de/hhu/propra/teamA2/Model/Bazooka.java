package de.hhu.propra.teamA2.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Karsten und Susanna on 25.05.14.
 */
public class Bazooka extends Waffe {
    private String name = "Bazooka";
    private int damage = 20;
    Image img;                  // das Bild des Wurms, der auf das Board geladen werden soll
    ImageIcon img_waffe;


    public Bazooka() {
        img_waffe = new ImageIcon("src/res/bazooka.png");
        img = img_waffe.getImage();
        this.id=1;
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
