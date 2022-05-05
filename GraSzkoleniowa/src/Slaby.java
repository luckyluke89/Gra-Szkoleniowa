import java.awt.*;
import javax.swing.ImageIcon;

public class Slaby extends KlasaPostaci {
	private static Image fotkaSlabego = new ImageIcon("S³aby.jpeg").getImage();
	public Slaby(int x, int y) {
		super();
		this.setLocation(x, y);
		this.setPreferredSize(new Dimension(dx, dy));
		this.sila=10;
		this.rodzaj=1;
		this.add(pasekEnergii);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(fotkaSlabego, 0, 0, 20, 20, this);
	}
	
}