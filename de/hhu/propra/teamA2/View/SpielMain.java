package de.hhu.propra.teamA2.View;

import de.hhu.propra.teamA2.Model.MyTableModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SpielMain extends JFrame {

//----------hier musste einiges auskommentiert werden, weil die Buttons und der KeyListener sich gegenseitig stören
//----------man sollte einfach nie Buttons/ActionListener und KeyListener eim gleichen Fenster implementieren
//----------wir müssen uns also wegen dem Optionsmenü was überlegen

	private JTextField textField;
	private JScrollPane scroll;
	private List<String[]>data;
	private JTable table;
	private MyTableModel model;
	private JRadioButton soundRadio;
	private boolean isHidden;

    SpielMain() throws IOException{
		
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // ohne das würde das Fenster beim Klicken des [x] oben in der Ecke nicht schließen
		setBounds(100, 100, 800, 450);                      // hier wird die Position des Fensters auf dem Bildschirm und die Breite und Höhe festgelegt
        //setLocationRelativeTo(null);
        //setSize(800,450);

/* -------herausgenommen, weil der Button den ActionListener des Boards stört-------
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(0, 6, 80, 25);
		getContentPane().add(btnBack);
        btnBack.addActionListener(new back());
*/
/* -------chat brauchen wir noch nicht-------
		textField = new JTextField();
		textField.setBounds(988, 5, 134, 28);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSenden = new JButton("chat");
		btnSenden.setBounds(859, 6, 117, 29);
		getContentPane().add(btnSenden);
		btnSenden.addActionListener(new senden());
		getContentPane().add(btnSenden);
		
		table = new JTable();
		data = new ArrayList<String[]>();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(869, 32, 242, 104);
		getContentPane().add(scrollPane);
*/
/*  -------herausgenommen, weil der Button den ActionListener des Boards stört-------
		JButton btnEinstellung = new JButton("Einstellung");
		btnEinstellung.setBounds(100, 6, 117, 25);
		getContentPane().add(btnEinstellung);
		btnEinstellung.addActionListener(new hiddeneinstellung());
		soundRadio = new JRadioButton("Sound");
		soundRadio.setBounds(25, 58, 141, 23);
		getContentPane().add(soundRadio);
		model = new MyTableModel();
		isHidden = false;
		soundRadio.setVisible(isHidden);*/
	}

	class hiddeneinstellung implements ActionListener { // das wird grade wegen den Auskommentierungen nicht verwendet, kann man im Grunde auch auskommentieren

		@Override
		public void actionPerformed(ActionEvent arg0) {
				soundRadio.setVisible(isHidden);
				isHidden = !isHidden;
		}
	}
/* -------chat brauchen wir noch nicht-------
	class senden implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			model.addRow(Arrays.asList("Thai",textField.getText()));
			table = new JTable(model);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(869, 32, 242, 104);
			getContentPane().add(scrollPane);
			System.out.println(textField.getText());
			textField.setText("");
		}
	}
*/
	class back implements ActionListener {          // das wird grade wegen den Auskommentierungen nicht verwendet, kann man im Grunde auch auskommentieren

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				System.out.println("thai");
				StartView start = new  StartView ();
				setVisible(false);
				start.setVisible(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
/* was ist das?
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}*/

}
