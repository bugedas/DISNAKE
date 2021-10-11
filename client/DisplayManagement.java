package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ArrayBlockingQueue;

// NORMALEMENT OK : interface graphique + gestion des demandes du clavier
@SuppressWarnings("serial")
public class DisplayManagement extends JComponent implements KeyListener {
	protected static final byte EMPTY = 0, FULL = 1, FOOD = 2, PERSO = 3,
			cellSize = 10;
	private byte[][] gate; // la gate est calculee par un autre thread
	private JFrame graph;
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
		graph = new JFrame(serverName.toUpperCase());
		addKeyListener(this);
		graph.setBounds(400, 100, (size+1) * cellSize, (size + 3) * cellSize);
		a.setBounds(size * cellSize/4,size * cellSize/2, size * cellSize, size * cellSize/4);
		graph.add(a);
		graph.add(this);
		setFocusable(true);
		requestFocusInWindow();
		graph.setVisible(true);
		setFocusable(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				fill(g, i, j, gate[i][j]);
		// this.remove(a);
	}

	private void fill(Graphics g, int i, int j, byte color) {

		g.setColor((color == EMPTY) ? Color.WHITE
				: (color == FULL) ? Color.BLACK : (color == FOOD) ? Color.RED
				: (color == PERSO) ? Color.blue  : Color.gray);

		g.fillRect(i * cellSize + cellSize / 16, j * cellSize + cellSize / 16,
				cellSize - cellSize / 8, cellSize - cellSize / 8);
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
