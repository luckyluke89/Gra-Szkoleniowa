import java.awt.Dimension;
import java.io.Serializable;

import javax.swing.JPanel;

public class KlasaPrzeszkody extends JPanel implements Serializable{
	int dx = 20, dy = 20;
	int x, y;
	
	public KlasaPrzeszkody(int x, int y) {
		this.x = x;
		this.y = y;
		this.setSize(dx, dy);
		this.setLocation(x, y);
		this.setPreferredSize(new Dimension(dx, dy));
	}
	
	public int get_dx() {
		return this.dx;
	}
	public int get_dy() {
		return this.dy;
	}
	public int get_x() {
		return this.x;
	}
	public int get_y() {
		return this.y;
	}
	
	public void set_dx(int d_x) {
		this.dx=d_x;
	}
	public void set_dy(int d_y) {
		this.dy=d_y;
	}
	public void set_x(int zmienna_x) {
		this.x=zmienna_x;
	}
	public void set_y(int zmienna_y) {
		this.y=zmienna_y;
	}
}
