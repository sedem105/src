package de.hhu.propra.teamA2.View;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class LastScreen {

	private SpielMain spielfeld;
	private JPanel controlPanel;
	private JButton restartButton;
	private JButton endButton;
	
	public LastScreen(SpielMain spiel){
		
		spiel.setSize(450, 800);
		spiel.getContentPane().setLayout(new GridLayout(5,1));
		spielfeld = spiel;
		controlPanel = new JPanel();
		spielfeld.getContentPane().add(controlPanel);
		keyAdapter();
		
		spielfeld.setVisible(true);
	}
	
	public void keyAdapter(){
		
		
		restartButton = new JButton("New Game");
		endButton = new JButton("Exit");
		
		
		controlPanel.add(restartButton);
		controlPanel.add(endButton);
		
		spielfeld.getContentPane().add(controlPanel);
		
		restartButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                try{
                    StartView start = new StartView();
                    start.setVisible(true);
                }catch(IOException ioe){

                }

			}
		});
		
		endButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				spielfeld.dispose();
			}
		});
	}
	
}
