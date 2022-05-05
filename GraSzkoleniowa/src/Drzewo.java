import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Drzewo  extends KlasaPrzeszkody{
	private static Image fotkaDrzewa = new ImageIcon("Drzewo.jpeg").getImage();

	public Drzewo(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(fotkaDrzewa, 0, 0, dx, dy, this);
	}
}