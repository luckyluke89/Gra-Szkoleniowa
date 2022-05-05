import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Mocny extends KlasaPostaci{
	private static Image fotkaMocnego = new ImageIcon("Mocny.jpeg").getImage();
	
	public Mocny(int x, int y) {
		super();
		this.setLocation(x, y);	
		this.setPreferredSize(new Dimension(dx, dy));
		this.sila=20;
		this.rodzaj=1;
		this.add(pasekEnergii);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(fotkaMocnego, 0, 0, 20, 20, this);
	}
	
}
