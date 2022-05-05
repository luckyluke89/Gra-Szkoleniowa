import java.awt.Color;
import java.awt.Dimension;
import java.io.Serializable;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class KlasaPostaci extends JPanel implements Serializable{
	
	int dx = 20, dy = 20;
	int poziom_energii, sila, rodzaj, kierunekWstecz;
	public JProgressBar pasekEnergii = new JProgressBar(0, 100);
	public KlasaPostaci() {
		this.setSize(dx, dy);
		pasekEnergii.setSize(20, 5);
		pasekEnergii.setPreferredSize(new Dimension(20, 5));
		kierunekWstecz = 0;
		poziom_energii = 100;
		pasekEnergii.setValue(poziom_energii);
		pasekEnergii.setLocation(0, 0);
		pasekEnergii.setForeground(Color.RED);
	}
	
	public int get_dx() {
		return this.dx;
	}
	public int get_dy() {
		return this.dy;
	}
	public int get_poziom_energii() {
		return this.poziom_energii;
	}
	public int get_sila() {
		return this.sila;
	}
	public int get_rodzaj() {
		return this.rodzaj;
	}
	public int get_kierunekWstecz() {
		return this.kierunekWstecz;
	}
	
	
	public void set_dx(int x) {
		this.dx=x;
	}
	public void set_dy(int y) {
		this.dy=y;
	}
	public void set_poziom_energii(int poziom) {
		this.poziom_energii=poziom;
	}
	public void set_sila(int s) {
		this.sila=s;
	}
	public void set_rodzaj(int r) {
		this.rodzaj=r;
	}
	public void set_kierunekWstecz(int k) {
		this.kierunekWstecz=k;
	}
	
	public void Ruch(int kierunek) {	
		int xt = (int)this.getLocation().getX(); 
		int yt = (int)this.getLocation().getY();
		
		if (kierunek == 1){
			if (yt>=20) {
				yt=yt-dy;
				kierunekWstecz = 3;
			}
		}
		else if (kierunek == 2){
			if ((xt+dx+20)<=800) {
				xt=xt+dx;
				kierunekWstecz = 4;
			}
		}
		else if (kierunek == 3){
			if ((yt+dy+20)<=800) {
				yt=yt+dy;
				kierunekWstecz = 1;
			}
		}
		else if (kierunek == 4){
			if (xt>=20) {
				xt=xt-dx;
				kierunekWstecz = 2;
			}
		}
		this.setLocation(xt, yt);
	}
	
	public int Akcja(KlasaPostaci postac) {
		postac.poziom_energii-=sila;
		return postac.poziom_energii;
	}
	
	public int zmniejszEnergie(int oIle) {
		this.poziom_energii-=oIle;
		this.pasekEnergii.setValue(poziom_energii);
		return this.poziom_energii;
	}
}
