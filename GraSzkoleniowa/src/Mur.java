import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Mur extends KlasaPrzeszkody {
	private static Image fotkaMuru = new ImageIcon("Mur.jpeg").getImage();
	
	public Mur(int x, int y) {
		super(x, y);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(fotkaMuru, 0, 0, dx, dy, this);
	}
}