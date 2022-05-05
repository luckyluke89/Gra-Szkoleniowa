import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Drzwi extends KlasaPrzeszkody{
	private static Image fotkaDrzwi = new ImageIcon("Drzwi.jpeg").getImage();
	
	public Drzwi(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(fotkaDrzwi, 0, 0, dx, dy, this);
	}
}