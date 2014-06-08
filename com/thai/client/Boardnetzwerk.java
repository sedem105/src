package com.thai.client;

import de.hhu.propra.teamA2.Model.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Boardnetzwerk extends  JPanel implements ActionListener,Clientdelegate{      //Erstellen der Spielfläche, erstmal ein paar Variablen festlegen:

    int round = 1;      // Rundennummer, startet bei Runde 1
    int levelnummer = 1;  // Levelnummer, startet bei Level 1

    Level level = new Level();  // erstelle ein Objekt der Klasse Level

    Wurm wurm1;     // für den Moment: erstelle nur 3 Würmer (soll noch variabel werden)
    Wurm wurm2;
    Wurm wurm3;
    Wurm wurm_active;
    Image img1, img2;   // das werden Hintergrund- (img1) und Vordergrundbild (img2), kommt unten noch vor

    String farbe1 = "green";    // Die Farben in denen die Würmer/Blubs angezeigt werden.
    String farbe2 = "blue";     // Diese Strings übergebe ich später an die Klasse Level, damit diese
    String farbe3 = "red";      // der Klasse Wurm die richtigen Namen der entsprechenden Bildadateien
    String farbe4 = "lilac";      // (deren korrekte Dateipfade) konstruieren kann.
    String farbe5 = "yellow";

    Timer time;

    Point mausPos=new Point();              // Position der Maus beim Klicken zum Zielen mit Waffe
    Point zielPos=new Point();              // Endpunkt der Geraden beim Zielen
    Point startPos=new Point();             // Startpunkt der Gerade beim Zielen, wichtig bei Abprallern!

    Rectangle zielpunkt=new Rectangle();

    //Terrain-Objekte (noch in Datei auslagern)
    //Level1
    Rectangle2D.Double block1_1_ = new Rectangle2D.Double(249,214,289,221);     // mittlerer Block, (Input sind: x-Wert, y-Wert, Breite, Höhe)
    Rectangle2D.Double block1_2_ = new Rectangle2D.Double(538,321,186,120);     // rechts, Mauerwürfel fehlt noch
    Rectangle2D.Double block1_3_ = new Rectangle2D.Double(73, 321, 176, 120);   // links, muss noch schräg werden, also statt Rectangle2D muss Polygon
    //Level2
    Rectangle2D.Double block2_1_ = new Rectangle2D.Double(82,274,213,72);   //seife links
    Rectangle2D.Double block2_2_ = new Rectangle2D.Double(463,264,248,113); //seife rechts
    Rectangle2D.Double block2_3_ = new Rectangle2D.Double(68,242,23,48);    //wannenrand links
    Rectangle2D.Double block2_4_ = new Rectangle2D.Double(705,242,33,48);   //wannenrand rechts
    //Level3
    int[] xpoints_a = {27,196,196};         // die x-Werte für die Polygone werden in einem,
    int[] ypoints_a = {379,207,379};        // und die y-Werte in einem anderen Array gespeichert
    int[] xpoints_b = {596,596,765};        // nochmal für das zweite Polygon
    int[] ypoints_b = {379,207,379};
    Rectangle2D.Double block3_1_ = new Rectangle2D.Double(196,207,400,30);  // Brücke Mittelteil
    Polygon block3_2_ = new Polygon(xpoints_a,ypoints_a,3);      // Schräge links als Dreieck (Input sind: Array mit x-werten, Array mit y-Werten, Anzahl der Eckpunkte)
    Polygon block3_3_ = new Polygon(xpoints_b,ypoints_b,3);      // Schräge rechts als Dreieck

    public Boardnetzwerk(){
        Spielstand stand = SaveGame.loadGame(); //load spielstand
        Mannschaft team1 = stand.getTeam().get(0);
        Mannschaft team2= stand.getTeam().get(1);
        addKeyListener(new AL());   // hier wird ein neuer KeyListener erstellt und dem Board hinzugefügt
        addMouseListener(new ML()); // Mauslistener für Zielen mit Waffe
        setFocusable(true);         // damit das Board aufnahmebereit ist zB für die KeyEvent, wird es hier auf focusable gestellt

        level.level(levelnummer);   // die aktuelle levelnummer (initial 1) wird hier an die Klasse Level übergeben
        img1 = level.getBg();       // Hintergrund- und Vordergrund-Bild werden hier von der Klasse Level abgerufen
        img2 = level.getFg();       // durch deren getter-Methoden

        wurm1 = new Wurm(team1.getFarbe());   // ein neues Objekt Wurm wird erzeugt und der String Farbe übergeben, mit dem innerhalb dieser Klasse
        wurm2 = new Wurm(team2.getFarbe());   // die Pfade der Bilder konstruiert werden können
        wurm3 = new Wurm(team2.getFarbe()); //  stell jeweiles Farbe von den team ein 
        wurm_active=wurm1;

        wurm1.setX(level.getWurm1posx());       // Startpositionen der Würmer werden von der Klasse Level abgefragt.
        wurm1.setY(level.getWurm1posy());       // Je nach am anfang übergebenem Level sind die Positionen unterschiedlich
        wurm2.setX(level.getWurm2posx());       // das soll noch verbessert werden, in einem Array oder so
        wurm2.setY(level.getWurm2posy());
        wurm3.setX(level.getWurm3posx());
        wurm3.setY(level.getWurm3posy());

        mausPos.setLocation(wurm_active.getXoffset(),wurm_active.getYoffset());     //Zielstrahl am Anfang zum Punkt machen, zielt nirgendwohin  bzw. auf sich selbst
        zielPos.setLocation(wurm_active.getXoffset(),wurm_active.getYoffset());
        startPos.setLocation(wurm_active.getXoffset(),wurm_active.getYoffset());

        time = new Timer(5,this);
        time.start();
    }

    public void actionPerformed(ActionEvent e){
        wurm_active.move();
        falling();
        repaint();
    }

    protected void paintComponent(Graphics block){          // ab hier wird auf dem Board (=JPanel) gezeichnet
        super.paintComponent(block);
        if(levelnummer==1){                                 // je nach aktellem Level werden unterschiedliche Hintergrundobjekte gezeichnet:
            Graphics2D block1_1 = (Graphics2D) block;
            Graphics2D block1_2 = (Graphics2D) block;
            Graphics2D block1_3 = (Graphics2D) block;
            //block2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            // Beispiel für Kantenglättung, brauchen wir hier aber nicht. Die Objekte verschwinden ja hinter den Bildern
            block1_1.draw(block1_1_);     // mittlerer Block, (Input sind: x-Wert, y-Wert, Breite, Höhe)
            block1_2.draw(block1_2_);     // rechts, Mauerwürfel fehlt noch
            block1_3.draw(block1_3_);   // links, muss noch schräg werden, also statt Rectangle2D muss Polygon
        }else
        if(levelnummer==2){
            Graphics2D block2_1 = (Graphics2D) block;
            Graphics2D block2_2 = (Graphics2D) block;
            Graphics2D block2_3 = (Graphics2D) block;
            Graphics2D block2_4 = (Graphics2D) block;
            block2_1.draw(block2_1_);   //seife links
            block2_2.draw(block2_2_); //seife rechts
            block2_3.draw(block2_3_);    //wannenrand links
            block2_4.draw(block2_4_);   //wannenrand rechts
        }else
        if(levelnummer==3){
            Graphics2D block3_1 = (Graphics2D) block;
            Graphics2D block3_2 = (Graphics2D) block;
            Graphics2D block3_3 = (Graphics2D) block;
            block3_1.draw(block3_1_);  // Brücke Mittelteil
            block3_2.draw(block3_2_);      // Schräge links als Dreieck (Input sind: Array mit x-werten, Array mit y-Werten, Anzahl der Eckpunkte)
            block3_3.draw(block3_3_);      // Schräge rechts als Dreieck
        }
        //Graphics2D zielpunkt= (Graphics2D) block;
        //zielpunkt.draw(new Line2D.Double(wurm_active.getXoffset(), wurm_active.getYoffset(), zielPos.getX(), zielPos.getY()));      // auf aktiver Wurm umstellen und Position anpassen
    }

    public void paint(Graphics statics){          // ab hier wird wieder auf dem Board (=JPanel) gezeichnet, ähnlich wie oben, aber diesmal keine Shapes, sondern Images

        super.paint(statics);
        Graphics2D g2d = (Graphics2D) statics;

        //g2d.drawImage(level.getBg(), 0, 0, null);        // Hintergrund je nach levelnummer wird von Klasse Level erfragt und an die Koordinaten (0,0) relativ zu null positioniert
        //g2d.drawImage(level.getFg(), 0, 0, null);        // Vordergrund ebenso
        g2d.drawImage(wurm1.getImage(), wurm1.getX(), wurm1.getY(), null);      // zeichnet Würmer an Startpositionen bzw die aktuelle Position.
        g2d.drawImage(wurm2.getImage(), wurm2.getX(), wurm2.getY(), null);      // Input sind: Image, x-Wert, y-Wert, relativ zu Positon
        g2d.drawImage(wurm3.getImage(), wurm3.getX(), wurm3.getY(), null);
        g2d.drawString("Round: "+round, 250, 15);           // Schreibt oben an Position (250,15) den Text Round: und fügt dann die aktuelle Rundennummer ein
        g2d.drawString("Level: "+levelnummer, 320, 15);     // Schreibt oben an Position (320,15) den Text Level: und fügt dann die aktuelle Levelnummer ein

        g2d.drawString("X", wurm_active.getX()+25, wurm_active.getY()-10);     // markiert aktuellen Wurm mit einem Buchstaben X

        g2d.drawString(""+wurm1.getHitpoints(), wurm1.getX()+25, wurm1.getY()); // Hitpoints über jeden Wurm schreiben
        g2d.drawString(""+wurm2.getHitpoints(), wurm2.getX()+25, wurm2.getY());
        g2d.drawString(""+wurm3.getHitpoints(), wurm3.getX()+25, wurm3.getY());
    }

    public class AL extends KeyAdapter{             // der oben erstellte KeyListener ("AL") wird hier definiert
        public void keyReleased(KeyEvent e) {
            wurm_active.keyReleased(e);
        }

        public void keyPressed(KeyEvent e){         // bei gedrücktem Key wird abgefragt, welcher Key es war
            wurm_active.keyPressed(e);

            char key = e.getKeyChar();              // der Eingabewert des Keys wird in einen charakter umgewandelt
            if(levelnummer<3) {                     // damit nach Level 3 nicht wieder Level 1 kommt oder sonst etwas komisches, stoppen wir hier die Levelerhöhung
                if (key == 'l') {                   // Wenn "l" wie Level gedrückt wurde:
                    levelnummer++;                  // dann erhöhe die aktuelle Levelnummer um 1
                    level.level(levelnummer);       // aktualisiere den Wert auch in der Klasse Level
                    round=1;                        // mit jedem neuen Level starten wir wieder bei Runde 1
                    wurm_active=wurm1;
                    wurm1.setX(level.getWurm1posx());       // Startpositionen der Würmer, die sind hier anders als in Level 1, darum werden sie neu geladen
                    wurm1.setY(level.getWurm1posy());
                    wurm2.setX(level.getWurm2posx());
                    wurm2.setY(level.getWurm2posy());
                    wurm3.setX(level.getWurm3posx());
                    wurm3.setY(level.getWurm3posy());
                }
            }
            if (key == 'r') {                       // Wenn "r" wie Round gedrückt wurde:
                round++;                            // dann erhöhe die aktuelle Rundennummer um 1
                if(round%3==1){                     // damit man rotiert, wir die aktuelle Rundennummer modulo Anzahl der Würmer/Spieler gerechnet
                    wurm_active=wurm1;
                }else if(round%3==2){
                    wurm_active=wurm2;
                }else if(round%3==0){
                    wurm_active=wurm3;
                }
            }
            if(key=='1'){
                wurm_active.setWaffe(1);
                System.out.println("Waffe 1 aktiv! (Bazooka)");   // Dummy für neue Waffe laden, Bild reinmachen usw.
            }
            if(key=='2'){
                wurm_active.setWaffe(2);
                System.out.println("Waffe 2 aktiv! (Pistole)");
            }
            if(key=='3'){
                wurm_active.setWaffe(3);
                System.out.println("Waffe 3 aktiv! (Granate)");
            }
            if(key=='4'){
                wurm_active.setWaffe(4);
                System.out.println("Waffe 4 aktiv! (Schwert)");
            }
        }
    }
    /***** Zielen ohne abprallen
    public class ML extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {

            if(e.getButton()==MouseEvent.BUTTON1) {         // bei linksklick Mausposition bestimmen für Zielstrahl
                mausPos = e.getPoint();
                zielPos.setLocation(wurm_active.getXoffset(),wurm_active.getYoffset());  // reset von zielPos für neue while-schleife
                int i=1;
                if(mausPos.getX()>wurm_active.getXoffset()) {           // Verlängerung der Linie bis Frame-Ende links oder rechts, 2 Punkte -> 1 Gerade
                    while(zielPos.getX()<800 && checkCollisionsWurm()!=2) {             // Schritt für Schritt verlängern bis Ende Frame oder Kollision (if-abfrage fehlt noch)
                        zielPos.setLocation(wurm_active.getXoffset() + i, ((mausPos.getY() - wurm_active.getYoffset()) / (mausPos.getX() - wurm_active.getXoffset())) * i + wurm_active.getYoffset());
                        i++;
                        repaint();
                    }
                }else{
                    while(zielPos.getX()>0 && checkCollisionsWurm()!=2) {
                        zielPos.setLocation(wurm_active.getXoffset()-i, (mausPos.getY() - wurm_active.getYoffset()) / (mausPos.getX() - wurm_active.getXoffset()) * (-i) + wurm_active.getYoffset());
                        i++;
                        repaint();
                    }
                }
            }
        }
    }*/


    public class ML extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {

            boolean treffer=false;
            if(e.getButton()==MouseEvent.BUTTON1) {         // bei linksklick Mausposition bestimmen für Zielstrahl
                mausPos = e.getPoint();

                zielPos.setLocation(wurm_active.getX(),wurm_active.getY());  // reset von zielPos für neue while-schleife, vielleicht lieber beim rundenwechsel
                startPos.setLocation(wurm_active.getX(),wurm_active.getY());
                treffer=flugbahn();
                System.out.println(zielPos);
                while((zielPos.getX()==800||zielPos.getX()==0||zielPos.getY()==0||zielPos.getY()==450)&&!treffer){

                    if(zielPos.getX()==800||zielPos.getX()==0){
                        mausPos.setLocation(mausPos.getX(),mausPos.getY()+(zielPos.getY()-mausPos.getY())*2);
                    }else if(zielPos.getY()==450||zielPos.getY()==0){
                        mausPos.setLocation(mausPos.getX()+(zielPos.getX()-mausPos.getX())*2,mausPos.getY());
                    }
                    startPos.setLocation(zielPos);

                    treffer=flugbahn();
                    System.out.println("neu "+zielPos);
                }
            }
        }
    }

    public boolean flugbahn(){            // vermutlich schlecht gelöst, wer will kanns besser machen. Hauptsache es klappt.
        // Aufteilung in: Schuss nach rechts, Schuss nach links und bei beiden jeweils Alternative falls Grenze oben/unten wird eher erreicht.
        // ohne Aufteilung kam Schrott raus
        int i=1;
        boolean treffer=false;

        if(mausPos.getX()>startPos.getX()) {           // Verlängerung der Linie bis Frame-Ende links oder rechts, 2 Punkte -> 1 Gerade

            if(((mausPos.getY() - startPos.getY()) / (mausPos.getX() - startPos.getX())) * (800-startPos.getX()) + startPos.getY()<450 && ((mausPos.getY() - startPos.getY()) / (mausPos.getX() - startPos.getX())) * (800-startPos.getX()) + startPos.getY()>0) {

                while (zielPos.getX() < 800&&!treffer) {             // Schritt für Schritt verlängern bis Ende Frame oder Kollision (if-abfrage fehlt noch)

                    zielPos.setLocation(startPos.getX() + i, ((mausPos.getY() - startPos.getY()) / (mausPos.getX() - startPos.getX())) * i + startPos.getY());      //Formel zu Waffe auslagern, solang linear noch hier

                    i++;
                    repaint();
                    zielpunkt.setLocation(zielPos);
                    zielpunkt.setSize(1,1);
                    if(kollisionsabfrageRectangle(zielpunkt)!=null)treffer=true;
                    else if(kollisionsabfrageWurm(zielpunkt)!=null)treffer=true;
                }
            }else{
                if(mausPos.getY()>startPos.getY()){
                    while(zielPos.getY()<450&&!treffer){
                        zielPos.setLocation(i*(mausPos.getX()-startPos.getX())/(mausPos.getY()-startPos.getY())+startPos.getX(),startPos.getY()+i);

                        i++;
                        repaint();
                        zielpunkt.setLocation(zielPos);
                        zielpunkt.setSize(1,1);
                        if(kollisionsabfrageRectangle(zielpunkt)!=null)treffer=true;
                        else if(kollisionsabfrageWurm(zielpunkt)!=null)treffer=true;
                    }
                }else{
                    while(zielPos.getY()>0&&!treffer){
                        zielPos.setLocation((-i)*(mausPos.getX()-startPos.getX())/(mausPos.getY()-startPos.getY())+startPos.getX(),startPos.getY()-i);

                        i++;
                        repaint();
                        zielpunkt.setLocation(zielPos);
                        zielpunkt.setSize(1,1);
                        if(kollisionsabfrageRectangle(zielpunkt)!=null)treffer=true;
                        else if(kollisionsabfrageWurm(zielpunkt)!=null)treffer=true;
                    }
                }
            }
        }else{
            if(((mausPos.getY() - startPos.getY()) / (mausPos.getX() - startPos.getX())) * (0-startPos.getX()) + startPos.getY()<450 && ((mausPos.getY() - startPos.getY()) / (mausPos.getX() - startPos.getX())) * (0-startPos.getX()) + startPos.getY()>0) {

                while (zielPos.getX() > 0&&!treffer) {               // falls Schuss nach links

                    zielPos.setLocation(startPos.getX() - i, ((mausPos.getY() - startPos.getY()) / (mausPos.getX() - startPos.getX())) * (-i) + startPos.getY());

                    i++;
                    repaint();
                    zielpunkt.setLocation(zielPos);
                    zielpunkt.setSize(1,1);
                    if(kollisionsabfrageRectangle(zielpunkt)!=null)treffer=true;
                    else if(kollisionsabfrageWurm(zielpunkt)!=null)treffer=true;
                }
            }else{
                if(mausPos.getY()>startPos.getY()){
                    while(zielPos.getY()<450&&!treffer){
                        zielPos.setLocation(i*(mausPos.getX()-startPos.getX())/(mausPos.getY()-startPos.getY())+startPos.getX(),startPos.getY()+i);

                        i++;
                        repaint();
                        zielpunkt.setLocation(zielPos);
                        zielpunkt.setSize(1,1);
                        if(kollisionsabfrageRectangle(zielpunkt)!=null)treffer=true;
                        else if(kollisionsabfrageWurm(zielpunkt)!=null)treffer=true;
                    }
                }else{
                    while(zielPos.getY()>0&&!treffer){
                        zielPos.setLocation((-i)*(mausPos.getX()-startPos.getX())/(mausPos.getY()-startPos.getY())+startPos.getX(),startPos.getY()-i);
                        i++;

                        repaint();
                        zielpunkt.setLocation(zielPos);
                        zielpunkt.setSize(1,1);
                        if(kollisionsabfrageRectangle(zielpunkt)!=null)treffer=true;
                        else if(kollisionsabfrageWurm(zielpunkt)!=null)treffer=true;
                    }
                }
            }
        }
        return treffer;
    }

    public Rectangle2D kollisionsabfrageRectangle(Rectangle movingObject){
        if(levelnummer==1) {
            if(block1_1_.intersects(movingObject))return block1_1_;
            if(block1_2_.intersects(movingObject))return block1_2_;
            if(block1_3_.intersects(movingObject))return block1_3_;
            return null;
        }else if(levelnummer==2){
            if(block2_1_.intersects(movingObject))return block2_1_;
            if(block2_2_.intersects(movingObject))return block2_2_;
            if(block2_3_.intersects(movingObject))return block2_3_;
            if(block2_4_.intersects(movingObject))return block2_4_;
            return null;
        }else if(levelnummer==3){
            if(block3_1_.intersects(movingObject))return block3_1_;     //Polygone fehlen!!! umbauen?
            return null;
        }
        return null;
    }

    public Wurm kollisionsabfrageWurm(Rectangle movingObject){
        Rectangle wurm1_= wurm1.getBounds();
        Rectangle wurm2_= wurm2.getBounds();
        Rectangle wurm3_= wurm3.getBounds();

        if(wurm1_.intersects(movingObject) && wurm1!=wurm_active && wurm1.getAlive()){
            wurm1.takeDamage(wurm_active.waffeActive().angreifen());
            return wurm1;}
        if(wurm2_.intersects(movingObject) && wurm2!=wurm_active && wurm2.getAlive()){
            wurm2.takeDamage(wurm_active.waffeActive().angreifen());
            return wurm2;}
        if(wurm3_.intersects(movingObject) && wurm3!=wurm_active && wurm3.getAlive()){
            wurm3.takeDamage(wurm_active.waffeActive().angreifen());
            return wurm3;}

        return null; //dummy, hier eigentlich getroffener Wurm
    }

    /* gehört zum schießen ohne abprallen
    public int checkCollisionsWurm() {
        Rectangle wurm_1 = wurm1.getBounds();
        Rectangle wurm_2 = wurm2.getBounds();
        Rectangle wurm_3 = wurm3.getBounds();
        Rectangle2D.Double punkt = new Rectangle2D.Double(zielPos.getX(), zielPos.getY(), 1, 1);
        if (wurm_1.intersects(punkt) && wurm1!=wurm_active && wurm1.getAlive()) {
            wurm1.takeDamage(wurm_active.waffeActive().angreifen());
            return 2;
        }
        if (wurm_2.intersects(punkt) && wurm2!=wurm_active && wurm2.getAlive()) {
            wurm2.takeDamage(wurm_active.waffeActive().angreifen());
            return 2;
        }
        if (wurm_3.intersects(punkt) && wurm3!=wurm_active && wurm3.getAlive()) {
            wurm3.takeDamage(wurm_active.waffeActive().angreifen());
            return 2;
        }
        else return 0;
    }*/

    public int checkCollisions(){       //für falling()
        Rectangle wurm = wurm_active.getBounds();
        if (levelnummer==1 && (wurm.intersects(block1_1_)||wurm.intersects(block1_2_)||wurm.intersects(block1_3_)||wurm_active.getY()==404)){
            return 1; //boden
        }
        if (levelnummer==2 &&(wurm.intersects(block2_1_)||wurm.intersects(block2_2_)||wurm.intersects(block2_3_)||wurm.intersects(block2_4_)||wurm_active.getY()==404)){
            return 1; //boden
        }
        if (levelnummer==3 &&(wurm.intersects(block3_1_)||block3_2_.intersects(wurm_active.getBounds())||block3_3_.intersects(wurm_active.getBounds())||wurm_active.getY()==404)){
            return 1; //boden
        }
        return 0;
    }

    public void falling(){
        if (checkCollisions()==0)   //  collisiondetection
            wurm_active.incY();   //y++
    }

	@Override
	public void update(String info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTable(List<String> list) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void setReady(final String name) {
        throw new NotImplementedException();
    }

    @Override
    public void setUnReady(final String name) {
        throw new NotImplementedException();
    }

    @Override
	public void kick() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void createConfig(final String config) {
        throw new NotImplementedException();
    }
}
