package de.hhu.propra.teamA2.View;

import com.thai.client.ChangeClient;
import com.thai.client.Client;
import com.thai.client.Clientdelegate;
import com.thai.client.changeName;
import com.thai.server.Server;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public  class CheckConnect extends JFrame implements Clientdelegate{
	private JTable table;
	JLabel ipadress;
    private JButton readyButton;
    private JButton startButton;
    private JComboBox<String> configurationCB;
    private boolean ready;
	private Model model;
	Client client;
	Thread thread1;
    private Set<String> readyList = new HashSet<String>();
	CheckConnect() throws IOException{
		super();
		changeName.createList(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1123, 617);
        getContentPane().setLayout(null);
        model = new Model();
        table = new JTable(model);
        model.addColumn("Name");
		model.addColumn("Spieler");
		model.addRow(new Object[]{"Name" ,"Spieler", "Konfiguration"});
		table.setBounds(104, 229, 315, 199);

		getContentPane().add(table);
		
		JLabel lblNewLabel = new JLabel("Check Connection");
		lblNewLabel.setBounds(106, 173, 200, 50);
		getContentPane().add(lblNewLabel);
		  
		InetAddress ip = InetAddress.getLocalHost();
		 ipadress = new JLabel("Current IP address : " + ip.getHostAddress());

		ipadress.setBounds(800, 6, 282, 16);
		getContentPane().add(ipadress);
		
		JButton Host = new JButton("Host");
		Host.setBounds(767, 49, 117, 29);
		getContentPane().add(Host);
		
		JButton btnClient = new JButton("Client");
		btnClient.addActionListener(new CreateClient());
		btnClient.setBounds(919, 49, 117, 29);
		getContentPane().add(btnClient);
		Host.addActionListener(new CreateHost());

        readyButton = new JButton("bereit");
        readyButton.addActionListener(new ReadyButtonAction());
        readyButton.setBounds(350, 173, 100, 30);
        getContentPane().add(readyButton);

        startButton = new JButton("Start");
        startButton.addActionListener(new StartAction());
        startButton.setBounds(500, 173, 100, 30);
        startButton.setEnabled(false);
        getContentPane().add(startButton);

        JLabel configurationLabel = new JLabel("Konfiguration");
        configurationLabel.setBounds(450, 225, 100, 30);
        getContentPane().add(configurationLabel);

        configurationCB = new JComboBox<String>();
        configurationCB.setBounds(450, 270, 100, 30);
        getContentPane().add(configurationCB);

        JButton createConfig = new JButton("Neu");
        createConfig.setBounds(450, 310, 100, 30);
        createConfig.addActionListener(new CreateConfigAction());
        getContentPane().add(createConfig);
    }

	class CreateHost implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			   Thread thread2 = new Thread(){
		        	 public void run(){
		        		 Server server = new Server();
		        		 try {
							server.runServer();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		 
		        	 }
		        	   
		          };
		          thread2.start();
			makeClient(true);

			
		}
		
	}
	class CreateClient implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			
		          makeClient(false);
		}
		
	}

    class CreateConfigAction implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent event) {
            createConfig();
        }
    }

    private void createConfig() {
        // alle möglichen Farben als Array
        String[] colors = new String[] {"Blau", "Grün", "Gelb", "Lila", "Rot" };
        // Erzeugt neues Popup mit den Farben
        String input = (String) JOptionPane.showInputDialog(this,
                "Wähle deine Konfiguration aus", "Konfiugrator", JOptionPane.INFORMATION_MESSAGE,
                null, colors,"Grün");
        if (input != null) { // nur wenn Farbe ausgewählt wurde
            client.sendInfo("CREATE_CONFIG" + input + "\n"); // informiere den Server über die neue Konfiguration
        }
    }

    class StartAction implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent event) {
            System.out.println("Start!");
        }
    }

    class ReadyButtonAction implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent event) {
            if(!ready) {
                client.sendInfo("READY" + client.getName()+"\n");
                ready = true;
            } else {
                client.sendInfo("UNREADY" + client.getName()+"\n");
                ready = false;
            }

        }
    }

    class Model extends DefaultTableModel {
        Color readyColor = Color.GREEN;
        Color normalColor = Color.RED;

        Color getColor(int row) {
            if(row == 0) return Color.WHITE;
            final String name = (String) table.getValueAt(row, 0);
            return readyList.contains(name) && row > 0? readyColor : normalColor;
        }
    }

	private void makeClient(final boolean isHost){
		final Clientdelegate  test = this; 
		   thread1 = new Thread(){
	          	public void run(){
	          		
	          		
	  				try {
	  					client = new Client(test);
	  					
	  					 client.Clientrun(isHost);
	  					 
	  				} catch (IOException e) {
	  					// TODO Auto-generated catch block
	  					e.printStackTrace();
	  				}
	                 
	          	}
	          };
	          thread1.start();
	}
	
	@Override
	public void update(String info) {
		// TODO Auto-generated method stub
		
	
	
		
	}
	
	class changeclient implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			
			System.out.println(table.getSelectedColumnCount());
		}
		
		
	}
	@Override
	public void updateTable(final List<String> list) {
		// TODO Auto-generated method stub
		table.setVisible(false);
		model = new Model();
		table = new JTable(model);
		model.addColumn("Name"); 
		model.addColumn("Spieler");
		model.addRow(new Object[]{"Name" ,"Spieler"});
		table.setBounds(104, 229, 315, 199);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                Model model = (Model) table.getModel();
                c.setBackground(model.getColor(row));
                return c;
            }
        });
		
		getContentPane().add(table);
		for(int i  =0; i <list.size();i++)
		{
			System.out.println(list.get(i));
			String name = list.get(i);
			String spieler ="Zuschauer";
			if(i ==0)
				spieler = "Host";
			if(i==1)
				spieler = "Spieler";
			
			
			String neuname = changeName.neueName(name);
			if(neuname!=null&&neuname.length()>1)
				name = neuname;
			model.addRow(new Object[]{name, spieler});
		}
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent event) {
		    	String nameclient = list.get(table.getSelectedRow()-1);
		    	ChangeClient change = new ChangeClient(nameclient, client);
		    	
		        if (table.getSelectedRow() > -1) {
		            // print first column value from selected row
		            System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
		           
		           
		            
		        }
		    }
		});
	}

    @Override
    public void setReady(final String name) {
        readyList.add(name);
        model.fireTableStructureChanged();
        enableStartButton();
    }

    @Override
    public void setUnReady(final String name) {
        if (readyList.contains(name)) {
            readyList.remove(name);
            model.fireTableStructureChanged();
            enableStartButton();
        }
    }

    @Override
    /**
     * Fügt eine neue Konfiguration hinzu, wenn diese noch nicht vorhanden ist.
     */
    public void createConfig(final String config) {
        for (int i = 0; i <= configurationCB.getModel().getSize(); i++) {
            if (configurationCB.getModel().getElementAt(i).equals(config)) {
                return;
            }
        }
        configurationCB.addItem(config);
    }

    private void enableStartButton() {
        if (readyList.size() == model.getRowCount() - 1) {
            startButton.setEnabled(true);
        } else {
            startButton.setEnabled(false);
        }
    }

    @Override
	public void kick() {
		thread1.stop();
	}
}
