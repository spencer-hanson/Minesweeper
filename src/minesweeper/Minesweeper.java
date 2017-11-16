package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.Timer;

public class Minesweeper extends JFrame implements ActionListener, Runnable, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = -5997925957844201071L;
	
	final int WIDTH = 520;
	final int HEIGHT = 580;
	
	private Point mouse;
	private BufferedImage backbuffer; //back buffer 
	private Graphics2D g2d; //backbuffer graphics object
	
	private Minefield minefield;
	private Menu menu;
	
	private final Timer timer;
	private int time;
	
	private int flags;
	
	public void initComponents() {
		
	}
	
	public void init() {//set up JFrame
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Minesweeper");
		setLocationRelativeTo(null);
		addMouseMotionListener(this);
		addMouseListener(this);
		initComponents();
		setVisible(true);
	}
	
	public Minesweeper() {
		menu = new Menu(18, 30, 480, 40); //create a new menu with certian dimensions
		minefield = new Minefield(10, 50, 20, 20);
		backbuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g2d = backbuffer.createGraphics();
		mouse = new Point(0, 0);
		flags = 0;
		time = 0;
		timer = new Timer(1000, this); //new timer
		timer.start();
		init();
		run();
	}
	
	public static void main(String args[]) {
		new Minesweeper();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(backbuffer, 0, 0, this);
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		minefield.paint(g2d, mouse);
		menu.paint(g2d);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
			time += 1;
			menu.setTime(time);
	}

	@Override
	public void run() {
	while(true) {
		try {
			Thread.sleep(20);//main thread loop
			repaint();
		} catch(Exception e) {
		}
	}
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3) {//right clicked
			int add = 0;
			if((add = minefield.rclick(e.getPoint())) != 0) {
				flags += add;
				menu.setFlags(flags); //update menu
			}
		} else if(e.getButton() == MouseEvent.BUTTON1) {
			minefield.click(e.getPoint());
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouse = e.getPoint(); //update mouse
		
	}

}
