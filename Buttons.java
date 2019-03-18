import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Buttons extends JPanel {
	//Setting Variables
	private GUI g;
	public JToggleButton stopStart;
	private JLabel tickCount;
	
	Buttons(final GUI g) {
		//settings the GUI settings to "this"
		this.g= g;
		//Set size of the GUI
		this.setSize(300,50);
		//Creating stop/start button
		stopStart = new JToggleButton("Start", false);
		stopStart.addItemListener(new ItemListener() {
			//Once button has been clicked it will change to states such as running or stopped.
		public void itemStateChanged(ItemEvent ev) {
			if(ev.getStateChange()== ItemEvent.SELECTED){
				stopStart.setText("Running");
				g.life.start();
			} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
				stopStart.setText("Stopped");
				g.life.stop();
			}
		}
		 });
		//Creating reset button
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Repaints the form and remove everything, it will then re-initiate the board from the start
				g.life.init();
				repaint();
			}
		});
		//Creating exit button
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}			
		});
		//Creating import button
		JButton imp = new JButton("Import");
		imp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//Using in-built function to import the board
					g.life.importBoard();
				} catch (FileNotFoundException e1) {
				//If no file is found error will appear in the console window and program will still run
					e1.printStackTrace();
				}
			}
		});
		//Creating save button
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//Using in-built function to export the board to a data file
					g.life.exportBoard();
				} catch (IOException e1) {
				
					e1.printStackTrace();
				}
			}
		}); 
		//Creating the label to count how many generations has gone by
		tickCount = new JLabel("Generation: "+g.life.getTicks());
	//Adding all buttons to the Frame
		add(stopStart);
		add(imp);
		add(reset);
		add(save);
		add(exit);
		add(imp);
		add(tickCount);
		
		}
	public void setTick() {
		tickCount.setText("Generation: "+g.life.getTicks());
	}
		
	}

