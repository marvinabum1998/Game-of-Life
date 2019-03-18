//Import Java utils
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Timer;
import java.util.Random;
import java.util.Scanner;



public class main_GOL extends Canvas {
//Initialise Variables
public byte[][] cells, newCells;
private long tick;
private int size = 25;
private Timer t;
private BufferStrategy bf;
private GUI g;

	main_GOL(final GUI g) {
		this.g = g;
		//BufferStrategy is used to render the program faster
		bf = getBufferStrategy();
		//Setting the size of frame and grids
		setSize(1000+size, 1000+size);
		//setting background colour to black
		setBackground(Color.BLACK);
		//Adding a mouse listener by retrieving the position of the users data index pointer when clicked
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				//Get position of x which is the width of the program
				int x = e.getX()/(getWidth()/size);
				//Get position of y which is the height of the program
				int y = e.getY()/(getHeight()/size);
				//Setting the position clicked inside the array					 
				cells[x][y] = (byte) (~cells[x][y] & 1);
				//Printing the position inside the console window
				System.out.println("Marking Cells at: x= "+x+ " y= " +y + " as " + cells[x][y]);
				//repaint the grid based on the users click
				repaint();
			}
			
		});
		//Creating a timer
		t = new Timer(500,new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tick();
				g.btns.setTick();
			}
		});
	}
	
//Buffer method to render the GUI faster and smoother
	 public void paint(Graphics g) {
		 createBufferStrategy(1);
		 bf = getBufferStrategy();
		 g = null;
		 try {
			 g = bf.getDrawGraphics();
			 render(g);
			 
		 } finally {
			 g.dispose();
		 }
		 bf.show();
		 Toolkit.getDefaultToolkit().sync();
	 }
	 //Creating the GUI
	 private void render(Graphics g) {
		 for (int x = 0; x < cells.length; x++) {
			 for (int y = 0; y < cells[x].length; y++) {	
			//Check if cells are 1 (alive)
				 if(cells[x][y] == 1){
				 g.setColor(Color.GREEN);
				g.fillRect(x*(getWidth()/size), y*(getHeight()/size),getWidth()/size ,getWidth()/size);
			 }
			 g.setColor(Color.WHITE);
			 g.drawRect(x*(getWidth()/size), y*(getHeight()/size),getWidth()/size ,getWidth()/size);
			}
		 }
	 }
	 //Initiate Program method
	 public void init() {
			cells = new byte[size][size];
			// Set all cells to 0 (dead)
			for (int i = 0; i < cells.length; i++) {
				for (int j = 0; j < cells[i].length; j++) {
					cells[i][j] = 0;
				}
			}
			tick = 0;
			g.btns.setTick();
			repaint();
		}
		public long getTicks() {
			return tick;
		}
		//Start timer
		public void start() {
			t.start();
			
		}
		//Stop timer
		public void stop() {
			t.stop();
			
		}
		//Import board
	public void importBoard() throws FileNotFoundException {
			init();
			//Retrieving the board.data
			Scanner in = new Scanner(new File("board.dat"));
			//Reading every line and checking which positions has been saved
			while(in.hasNext()) {
				cells[in.nextByte()][in.nextByte()] = 1;
			}
			in.close();
		}
	public void exportBoard() throws IOException {
		// Save the board to a file as x y
		BufferedWriter out = new BufferedWriter(new FileWriter("board.dat"));
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if(cells[i][j] == 1) {
					out.write(i+" "+j+" ");
				}
			}
		}
		out.close();
	}
	//Not working
	public void randomise_board() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
			cells[x][y] = (byte) (Math.random());
			}
		}
	}
	//Main code to check if neighbour is either alive or dead
		private void tick() {
			//Adding 1 to tick everyone time it runs
			tick++;
			newCells = new byte[size][size];
			int totalSum = 0;
			// The array should wrap around so that cells[1000][1001] refers to the top right cell
			for (int i = 0; i < cells.length; i++) {
				for (int j = 0; j < cells[i].length; j++) {
					//Creating a wrapper syntax
					int ip = (((i+1)%cells.length)+cells.length)%cells.length;
					int im = (((i-1)%cells.length)+cells.length)%cells.length;
					int jp = (((j+1)%cells[i].length)+cells[i].length)%cells[i].length;
					int jm = (((j-1)%cells[i].length)+cells[i].length)%cells[i].length;
					//Creating an array for the wrapper syntax
					byte[] neighbours = {
						cells[im][j],
						cells[ip][j],
						cells[i][jp],
						cells[i][jm],
						cells[ip][jp],
						cells[ip][jm],
						cells[im][jp],
						cells[im][jm]
					};
				
					byte sum = 0;
					for(byte b: neighbours) {
						sum += b;
					}
					totalSum += sum;
					if(cells[i][j] == 1){
						// Dealing with live cells
						if(sum < 2) {
							newCells[i][j] = 0;
						} else if(sum > 3) {
							newCells[i][j] = 0;
						} else {
							newCells[i][j] = 1;
						}
					} else if(sum == 3) {
						// Dealing with dead cell
						newCells[i][j] = 1;
					} else {
						newCells[i][j] = 0;
					}
				}
			}
			if(totalSum == 0) {
				// Stop the program if all cells are dead
				System.out.println("All cells are dead, stopping");
				stop();
			}
			//Changing the cells to the new cells based on dead or alive
			cells = newCells;
			repaint();
		}
		
}

