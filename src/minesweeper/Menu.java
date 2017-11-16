package minesweeper;

import java.awt.*;
import java.net.URL;

public class Menu {
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private int time;
	private int flags;
	
	public Image flag;
	public Image clock;
	
	public URL getURL(String filename) { //image loading utility
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		} catch(Exception e) {
			System.out.println("Error getting url for images!");
			e.printStackTrace();
		}
		return url;
	}
	
	public Menu(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.time = 0;
		this.flags = 0;
		flag = Toolkit.getDefaultToolkit().getImage(getURL("images/flag.png"));
		clock = Toolkit.getDefaultToolkit().getImage(getURL("images/clock.png"));
	}
	
	public void paint(Graphics2D g2d) {
		g2d.setColor(new Color(162, 162, 162));
		//anti-aliasing
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		//draw the menu
		g2d.fillRect(x, y, width, height);
		g2d.setColor(Color.black);
		g2d.drawRect(x, y, width, height);
		g2d.setFont(new Font("Arial", Font.BOLD, 24));
		g2d.drawString("Minesweeper", x + width/3 + 10, y + height/2 + 10);
		g2d.drawString(time + "", x + 40 , y + height/2 + 10);
		g2d.drawString(flags + "", x + width - 60 , y + height/2 + 10);
		g2d.drawImage(clock, x, y + height/2 - clock.getHeight(null)/2, null);
		g2d.drawImage(flag, x + width - (60 + flag.getHeight(null) + 5), y + height/2 - flag.getHeight(null)/2, null);
	}
	
	public void setFlags(int flags) {//number of flagged mines
		this.flags = flags;
	}
	
	public void setTime(int time) { //set board time
		this.time = time;
	}
}
