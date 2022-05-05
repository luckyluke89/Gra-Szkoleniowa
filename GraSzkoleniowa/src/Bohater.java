import java.awt.*;
import javax.swing.ImageIcon;

public class Bohater extends KlasaPostaci {
	private static Image fotkaBohatera = new ImageIcon("Bohater.jpeg").getImage();
	
	public Bohater() {
	}
	
	public Bohater(int x, int y) {
		super();
		this.setLocation(x, y);
		this.setPreferredSize(new Dimension(dx, dy));
		this.sila=30;
		this.rodzaj=0;
	}

	@Override
	public int Akcja(KlasaPostaci postac) {
		return postac.zmniejszEnergie(sila);		 
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(fotkaBohatera, 0, 0, 20, 20, this); 
	}
	
}
