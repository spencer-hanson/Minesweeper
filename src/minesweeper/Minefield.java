package minesweeper;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import java.util.*;

public class Minefield {
	private int height;
	private int width;
	ArrayList<ArrayList<MineCell>> cells; //cells arraylist
	
	public Image mine;
	public Image flag;
	public Font font;
	private Point selected;
	public Minefield(int posx, int posy, int height, int width) {
		this.height = height;
		this.width = width;
	
	 	font = new Font("Arial", Font.BOLD, 15);
		mine = Toolkit.getDefaultToolkit().getImage(getURL("images/mine.png"));
		flag = Toolkit.getDefaultToolkit().getImage(getURL("images/flag.png"));
		selected = new Point(-1, -1);
		
		initGame(posx, posy);
	}

	public void initGame(int posx, int posy) { //create all cells
		int offsetx = 8; //offset from 0,0
		int offsety = 30;
		int cellsize = 24;
		
		cells = new ArrayList<ArrayList<MineCell>>(height);
		for(int x = 0; x < width; x++) {
			ArrayList<MineCell> row = new ArrayList<MineCell>(width);
			for(int y = 0; y < height; y++) {
				MineCell cell = new MineCell(posx + offsetx + x*cellsize, posy + offsety + y*cellsize, cellsize, 0, x, y);
				row.add(cell);
			}
			cells.add(row);
		}
		genField();
	}
	
	public void paint(Graphics2D g2d, Point mouse) {
		g2d.setFont(font);
		for(ArrayList<MineCell> rows : cells) {
			for(MineCell cell : rows) {
				cell.paint(g2d, flag, mine, cellSelected(cell, mouse)); //draw all cells
			}
		}
	}
		
	public boolean cellSelected(MineCell cell, Point mouse) {
		if(cell.getBounds().contains(mouse)) {
			selected = new Point(cell.getIndexX(), cell.getIndexY());
			return true;
		} else { 
			return false;
		}
	}

	public MineCell getSelectedCell() {
		return cells.get(selected.x).get(selected.y);
	}
	
	public URL getURL(String filename) {
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		} catch(Exception e) {
			System.out.println("Error getting url for images!");
			e.printStackTrace();
		}
		return url;
	}
	
	public int rclick(Point point) {
		MineCell cell;
		if((cell = getSelectedCell()) != null) {
			return cell.flag();
		}
		return 0;
	}
	
	public void click(Point point) {
		 MineCell cell = getSelectedCell();
		 cell.detonate(cells, width, height);
	}
	
	
	public void genField() { //generator to create a random minefield
		int mines = (int)(height * width * .1);
		Random random = new Random();
		int x;
		int y;
		int i = 0;
		while(i < mines) {
			x = random.nextInt(width);
			y = random.nextInt(height);
			if(cells.get(x).get(y).getValue() != -1) {
				cells.get(x).get(y).setValue(-1);
				i++;
			}
		}
		
		for(x = 0; x < width; x++) {
			for(y = 0; y < height; y++) {
				if(cells.get(x).get(y).getValue() != -1) {
					processCell(x, y); //generate cell number
				}
			}
		}
	}
	
	public void processCell(int x, int y) {
		int count = 0;
		
		//left top
		try { if(cells.get(x-1).get(y+1).getValue() == -1) { count++; } } catch (Exception e) { }
		//left middle
		try { if(cells.get(x-1).get(y).getValue() == -1) { count++; } } catch (Exception e) { }
		//left bottom
		try { if(cells.get(x-1).get(y-1).getValue() == -1) { count++; } } catch (Exception e) { }
		//middle bottom
		try { if(cells.get(x).get(y-1).getValue() == -1) { count++; } } catch (Exception e) { }
		//middle top
		try { if(cells.get(x).get(y+1).getValue() == -1) { count++; } } catch (Exception e) { }
		//right top
		try { if(cells.get(x+1).get(y+1).getValue() == -1) { count++; } } catch (Exception e) { }
		//right middle
		try { if(cells.get(x+1).get(y).getValue() == -1) { count++; } } catch (Exception e) { }
		//right bottom
		try { if(cells.get(x+1).get(y-1).getValue() == -1) { count++; } } catch (Exception e) { }
		cells.get(x).get(y).setValue(count); //set number of mines around cell
	}
}