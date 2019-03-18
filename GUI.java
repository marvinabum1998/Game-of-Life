import java.awt.BorderLayout;
import javax.swing.*;
public class GUI {
	
public main_GOL life;
public Buttons btns;


GUI() {
	//Start GUI
	//Creating a new instance of JFrame
	JFrame frame = new JFrame("Game Of Life");
	//Settings the default close operation (x sign) to close the program
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//Retrieving the buttons and board from main_GOL and buttons.java
	life = new main_GOL(this);
	btns = new Buttons(this);
	//Create a new layout for the board. 
	frame.setLayout(new BorderLayout());
	//Settings position of button and grids
	frame.add(btns,BorderLayout.PAGE_START);
	frame.add(life, BorderLayout.CENTER);
	//Initiate board by running the inin(); method inside main_gol.java
	life.init();
	frame.pack();
	frame.setBounds(100,100,660,720);
	//Set frame visible and not re-sizable
	frame.setResizable(false);
	frame.setVisible(true);
	
	
	}
}
