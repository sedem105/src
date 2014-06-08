package de.hhu.propra.teamA2.View;
import de.hhu.propra.teamA2.Model.*;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


class TextFieldEvent extends JPanel implements TextListener {
    public TextField text;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private ArrayList<Mannschaft> team;
    private Spielstand spielstand ;
    private Mannschaft mannschaft;
    private JButton wantBlue;
    private JButton wantGreen;
    private JButton wantYellow;
    private JButton wantLilac;
    private JButton wantRed;
    private JTable table;
    private MyTableModel model;
    private JScrollPane scrollPane;
    private JTable table_1;
    private SpielMain spielmain;
    private int teamsize=2;

    public TextFieldEvent(SpielMain spiel){
        spiel.setSize(800, 450);
        spiel.getContentPane().setLayout(new GridLayout(5,1));
        spielmain = spiel;
        headerLabel = new JLabel();
        headerLabel.setAlignmentX(Label.CENTER);
        statusLabel = new JLabel();
        statusLabel.setAlignmentX(JLabel.CENTER);
        statusLabel.setSize(350,100);

        controlPanel = new JPanel();


        spiel.getContentPane().add(headerLabel);
        spiel.getContentPane().add(controlPanel);
        spiel.getContentPane().add(statusLabel);
        spiel.getContentPane().add(statusLabel);


        model = new MyTableModel();
        model.addRow(Arrays.asList("Team:","Farbe:"));
        table = new JTable(model);
        table.setAlignmentX(JLabel.CENTER);
        table.setSize(100, 100);
        spiel.getContentPane().add(table);
        team = new ArrayList<Mannschaft>();
        keyAdapter(spiel);
        spiel.setVisible(true);
    }

    class FarbeChose implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            mannschaft = new Mannschaft();
            mannschaft.setFarbe(e.getActionCommand());
            System.out.println(e.getActionCommand());

        }

    }
    public void keyAdapter(SpielMain spiel1) {
        headerLabel.setText("Team Name:");

        final TextField textField = new TextField(20);
        textField.setBounds(16, 5, 170, 31);
        final SpielMain spiel = spiel1;
        JButton okButton = new JButton("Ok");
        okButton.setBounds(191, 8, 53, 25);
        JButton startButton = new JButton("Start");
        startButton.setBounds(249, 8, 70, 25);
        wantBlue = new JButton("blue");
        wantBlue.setBounds(324, 8, 110, 25);
        wantGreen = new JButton("green");
        wantGreen.setBounds(44, 41, 122, 25);
        wantYellow = new JButton("yellow");
        wantYellow.setBounds(171, 41, 125, 25);
        wantLilac = new JButton("lilac");
        wantLilac.setBounds(301, 41, 104, 25);
        wantRed = new JButton("red");
        wantRed.setBounds(421, 41, 104, 25);
        controlPanel.setLayout(null);

        controlPanel.add(textField);
        controlPanel.add(okButton);
        controlPanel.add(startButton);
        controlPanel.add(wantBlue);
        controlPanel.add(wantGreen);
        controlPanel.add(wantYellow);
        controlPanel.add(wantLilac);
        controlPanel.add(wantRed);

        wantBlue.addActionListener(new FarbeChose());
        wantGreen.addActionListener(new FarbeChose());
        wantYellow.addActionListener(new FarbeChose());
        wantLilac.addActionListener(new FarbeChose());
        wantRed.addActionListener(new FarbeChose());

        spiel.setVisible(true);



        // Event für textField JPanel
        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    /*
                    Wurmstand eins = new Wurmstand();
                    eins.setFarbe(mannschaft.getFarbe());
                    System.out.println(mannschaft.getFarbe());
                    Wurmstand zwei = new Wurmstand();
                    zwei.setFarbe(mannschaft.getFarbe());

                    System.out.println(mannschaft.getFarbe());
                    ArrayList<Wurmstand> arrayWurm = new ArrayList<Wurmstand>();
                    arrayWurm.add(eins);
                    arrayWurm.add(zwei);*/

                    ArrayList<Wurmstand> arrayWurm = new ArrayList<Wurmstand>();
                    for (int i=1;i<=teamsize;i++) {
                        arrayWurm.add(new Wurmstand());
                        arrayWurm.get(1).setFarbe(mannschaft.getFarbe());
                    }

                    mannschaft.setName(textField.getText());
                    mannschaft.setArraywurm(arrayWurm);
                    System.out.println(textField.getText());

                    if(mannschaft.getFarbe()=="blue")
                        wantBlue.setVisible(false);
                    if(mannschaft.getFarbe()=="yellow")
                        wantYellow.setVisible(false);
                    if(mannschaft.getFarbe()=="lilac")
                        wantLilac.setVisible(false);
                    if(mannschaft.getFarbe()=="green")
                        wantGreen.setVisible(false);
                    if(mannschaft.getFarbe()=="red")
                        wantRed.setVisible(false);
                    if(mannschaft.getName() != null){}

                    team.add(mannschaft);
                    spielstand = new Spielstand();
                    spielstand.setAktuelle_Level(1);// kann man manuell einstellen hier nur beispiel
                    spielstand.setAktuelle_Runde(1);// hier auch
                    spielstand.setTeam(team);

                    model.addRow(Arrays.asList(textField.getText(),mannschaft.getFarbe()));
                    table.setVisible(true);
                    table = new JTable(model);
                    table.setAlignmentX(JLabel.CENTER);
                    table.setSize(100, 100);

                    if(team.size()==1)
                        statusLabel.setText("Team 1 wurde erfolgreich erstellt");
                    if(team.size()==2){
                        statusLabel.setText("Sie können das Spiel beginnen.");
                        SaveGame.saveGame(spielstand);
                    }
                }
            }
        });

        // Event für okButton
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Wurmstand eins = new Wurmstand();
                eins.setFarbe(mannschaft.getFarbe());
                System.out.println(mannschaft.getFarbe());

                Wurmstand zwei = new Wurmstand();
                zwei.setFarbe(mannschaft.getFarbe());

                System.out.println(mannschaft.getFarbe());
                ArrayList<Wurmstand>arrayWurm = new ArrayList<Wurmstand>();
                arrayWurm.add(eins);
                arrayWurm.add(zwei);
                mannschaft.setName(textField.getText());
                mannschaft.setArraywurm(arrayWurm);
                System.out.println(textField.getText());

                if(mannschaft.getFarbe()=="blue")
                    wantBlue.setVisible(false);
                if(mannschaft.getFarbe()=="yellow")
                    wantYellow.setVisible(false);
                if(mannschaft.getFarbe()=="lilac")
                    wantLilac.setVisible(false);
                if(mannschaft.getFarbe()=="green")
                    wantGreen.setVisible(false);
                if(mannschaft.getFarbe()=="red")
                    wantRed.setVisible(false);
                if(mannschaft.getName() != null){}

                team.add(mannschaft);
                spielstand = new Spielstand();
                spielstand.setAktuelle_Level(1);// kann man manuell einstellen hier nur als beispiel
                spielstand.setAktuelle_Runde(1);// hier auch
                spielstand.setTeam(team);

                model.addRow(Arrays.asList(textField.getText(),mannschaft.getFarbe()));
                table.setVisible(true);
                table = new JTable(model);
                table.setAlignmentX(JLabel.CENTER);
                table.setSize(100, 100);

                SaveGame.saveGame(spielstand);
                if(team.size()==1)
                    statusLabel.setText("Team 1 wurde erfolgreich erstellt");
                if(team.size()==2){
                    statusLabel.setText("Sie können das Spiel beginnen.");
                    SaveGame.saveGame(spielstand);
                }

            }
        });

        // Event für startButton
        startButton.addActionListener(new jumptoGame());

    }

    public void textValueChanged(TextEvent te) {

    }
}

class jumptoGame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            SpielMain spiel = new SpielMain();
            spiel.setBounds(100, 100, 800, 450);
            spiel.setTitle("Worms");
            spiel.setVisible(true);
            Board board = new Board();
            spiel.add(board);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}