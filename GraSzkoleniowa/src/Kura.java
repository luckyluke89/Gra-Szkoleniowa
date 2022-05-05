import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Kura extends KlasaPostaci {
	private static Image fotkakury = new ImageIcon("Kura.jpeg").getImage();
	
	public Kura(int x, int y) {
		super();
		this.setLocation(x, y);	
		this.setPreferredSize(new Dimension(dx, dy));
		this.sila=30;
		this.poziom_energii=30;
		this.rodzaj=2;
	}
	
	@Override
	public int Akcja(KlasaPostaci postac) {
		if((postac.poziom_energii+sila)<=100) {
			postac.poziom_energii+=sila;
		}
		return postac.poziom_energii;
	}

	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(fotkakury, 0, 0, 20, 20, this);
	}
}
