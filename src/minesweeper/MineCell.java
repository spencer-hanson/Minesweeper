package minesweeper;

import java.awt.*;
import java.util.ArrayList;

public class MineCell {

	int value;
	boolean flaged; //flagged
	boolean revealed; //shown
	
	private int x;
	private int y;
	private int size;
	
	private int indexx;
	private int indexy;
	
	Rectangle bounds;
	private boolean checked;
	private boolean die;
	
	public void detonate(ArrayList<ArrayList<MineCell>> cells, int width, int height) { 
		checked = true;
		if(flaged) { 
			return; 
		} else if(value == -1) {
			die = true;
			return; 
		} else if(value != 0) {
			revealed = true;
			return;
		} else {
			revealed = true;
			flaged = false;
			
			//left up right down
			
			//left
			if(indexx > 0) {
				MineCell cell = cells.get(indexx - 1).get(indexy);
				if(!cell.isChecked()) {
					cell.detonate(cells, width, height);
				}
				
			}
			//up
			if(indexy > 0) {
				MineCell cell = cells.get(indexx).get(indexy - 1);
				if(!cell.isChecked()) {
					cell.detonate(cells, width, height);
				}
			}
			//right
			if(indexx < width - 1) {
				MineCell cell = cells.get(indexx + 1).get(indexy);
				if(!cell.isChecked()) {
					cell.detonate(cells, width, height);
				}
			}
			//down
			if(indexy < height - 1) {
				MineCell cell = cells.get(indexx).get(indexy + 1);
				if(!cell.isChecked()) {
					cell.detonate(cells, width, height);
				}
			}
		}
	}
	
	public void reset() {
		checked = false;
	}
	
	public MineCell(int x, int y, int size, int value, int indexx, int indexy) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.value = value;
		this.revealed = false;
		this.die = false;
		this.checked = false;
		this.indexx = indexx;
		this.indexy = indexy;
		flaged = false;
		bounds = new Rectangle(x, y, size, size);
	}
	
	public void paint(Graphics2D g2d, Image flag, Image mine, boolean selected) {
		if(die) { //bombed cell
			g2d.setColor(new Color(255, 0, 0));
			g2d.fillRect(x, y, size, size);
			g2d.drawImage(mine, x+2, y+3, null);
		}
		
		if(!revealed && !die) {
			g2d.setColor(new Color(162, 162, 162));
			g2d.fillRect(x, y, size, size);
		} else if(revealed && !die) { 
			g2d.setColor(new Color(200, 200, 200));
			g2d.fillRect(x, y, size, size);
			switch(value) {
			case -1:
				g2d.drawImage(mine, x+2, y+3, null);
				break;
			case 0:
				//Nothing
				break;
			case 1:
				g2d.setColor(new Color(0, 153, 0));
				g2d.drawString("1", x + size /2 - 4, y + size /2 + 6);
				break;
			case 2:
				g2d.setColor(new Color(0, 0, 255));
				g2d.drawString("2", x + size /2 - 4, y + size /2 + 6);
				break;
			case 3:
				g2d.setColor(new Color(255, 0, 0));
				g2d.drawString("3", x + size /2 - 4, y + size /2 + 6);
				break;
			case 4:
				g2d.setColor(new Color(255, 128, 0));
				g2d.drawString("4", x + size /2 - 4, y + size /2 + 6);
				break;
			case 5:
				g2d.setColor(new Color(0, 204, 204));
				g2d.drawString("5", x + size /2 - 4, y + size /2 + 6);
				break;
			case 6:
				g2d.setColor(new Color(204, 0, 204));
				g2d.drawString("6", x + size /2 - 4, y + size /2 + 6);
				break;
			case 7:
				g2d.setColor(new Color(64, 64, 64));
				g2d.drawString("7", x + size /2 - 4, y + size /2 + 6);
				break;
			case 8:
				g2d.setColor(new Color(127, 0, 255));
				g2d.drawString("8", x + size /2 - 4, y + size /2 + 6);
				break;
			}
		}
		
		g2d.setColor(Color.black);
		g2d.drawRect(x, y, size, size); //black border
		
		if(selected && !die) {
			g2d.setColor(new Color(0, 0, 255, 90));
			g2d.fillRect(x, y, size, size);
		}
		
		if(flaged && !die) {
			g2d.drawImage(flag, x, y, null);
		}
		
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public int flag() {
		if(revealed) {
			return 0; //can't flag
		}else if(flaged) {
			flaged = false;
			return -1; //un flag
		} else if(!flaged) {
			flaged = true;
			return 1; //flag
		}
		return 0;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getIndexX() {
		return indexx;
	}
	
	public int getIndexY() {
		return indexy;
	}
	
	public boolean isChecked() {
		return checked;
	}
}
