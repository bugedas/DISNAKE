package client;

import utilities.TemplateMethod.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ArrayBlockingQueue;

// NORMALEMENT OK : interface graphique + gestion des demandes du clavier
@SuppressWarnings("serial")
public class DisplayManagement extends JComponent implements KeyListener {
	protected static final byte EMPTY = 0, FULL = 1, APPLE = 2, PERSO = 3, POISON = 4, WALL = 5,
			cellSize = 10;
	private byte[][] gate; // la gate est calculee par un autre thread
	private JFrame graph;
	private Grid grid;
	private ArrayBlockingQueue<Byte> requestDir;
	private JLabel a = new JLabel();
	private final int size = utilities.GameOptions.gridSize;

	protected void swap(byte[][] backgate) {
		try {
			EventQueue.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					gate = backgate;
					paintImmediately(0, 0, getWidth(), getHeight());
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected DisplayManagement(String serverName, int num, ArrayBlockingQueue<Byte> dir) {
		gate = new byte[size][size];
		setgraph(serverName, num);
		requestDir = dir;
	}

	private void setgraph(String serverName, int num) {
		grid = new Grid(serverName.toUpperCase());
		grid.setBounds(400, 100, (size+1) * cellSize, (size + 3) * cellSize);
		a.setBounds(size * cellSize/4,size * cellSize/2, size * cellSize, size * cellSize/4);
		grid.getFrames().add(a);
		grid.getFrames().add(this);
		setFocusable(true);
		requestFocusInWindow();
		grid.getFrames().setVisible(true);
		setFocusable(true);
		graph = grid.getFrames();
		addKeyListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				grid.fill(g, i, j, gate[i][j]);
			}
		// this.remove(a);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		byte a = (byte) (e.getKeyCode()-37);
		if(a>=0 && a<4)
			try{
				requestDir.add(a);
			}catch(IllegalStateException t){
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public void print(String string) {
		paintImmediately(0, 0, getWidth(), getHeight());
		a.setText(string);
	}
}
