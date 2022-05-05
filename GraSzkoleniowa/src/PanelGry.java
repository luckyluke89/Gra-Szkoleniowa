import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelGry extends JPanel {
	
	public int rozmiarPola;
	public Bohater ludek;
	Drzwi drzwi;
	public ArrayList<KlasaPostaci> listaPostaci;
	public ArrayList<KlasaPrzeszkody> listaPrzeszkod;
	public Runnable objektRozgrywka;
	Thread watekGry;
	public boolean pauza;
	
	public PanelGry(){
		super();
		pauza = false;
		rozmiarPola = 20;
		this.setSize(800, 800);
		this.setPreferredSize(new Dimension(800, 800));
		this.setBackground(new Color(0, 150, 0));
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				NacisnietoPrzycisk(e);
			}
		});
		this.repaint();
	}
	
	public void Graj() {
		this.repaint();
		this.grabFocus();
	}
	
	public void RestartujWatekGry() {
		objektRozgrywka = null;
		watekGry = null;
		objektRozgrywka = new Rozgrywka();
		watekGry = new Thread(objektRozgrywka, "rozgrywka");
	}
	
	public void generujPlansze(String nazwaPlanszy) {
		listaPostaci = new ArrayList<KlasaPostaci>();
		listaPrzeszkod = new ArrayList<KlasaPrzeszkody>();
		BufferedReader readerPlanszy;
		String wiersz;
		char znak;
		int idZnaku;
		try {
			readerPlanszy = new BufferedReader(new FileReader("Plansze/" + nazwaPlanszy + ".txt"));
			
			for(int idWiersza=0; idWiersza<40; idWiersza++ ) {
				wiersz=readerPlanszy.readLine();
				idZnaku=0;
				for(int i=0; i<80; i+=2) {
					znak=wiersz.charAt(i);
					switch(znak) {
					case 'X':
						ludek = new Bohater(idZnaku*rozmiarPola, idWiersza*rozmiarPola);
						this.add(ludek);
						break;
					case '#':
						listaPrzeszkod.add(new Mur(idZnaku*rozmiarPola, idWiersza*rozmiarPola));
						this.add(listaPrzeszkod.get(listaPrzeszkod.size()-1));
						break;
					case 'D':
						listaPrzeszkod.add(new Drzewo(idZnaku*rozmiarPola, idWiersza*rozmiarPola));
						this.add(listaPrzeszkod.get(listaPrzeszkod.size()-1));
						break;
					case 'K':
						listaPostaci.add(new Kura(idZnaku*rozmiarPola, idWiersza*rozmiarPola));
						this.add(listaPostaci.get(listaPostaci.size()-1));
						break;
					case '1':
						listaPostaci.add(new Mocny(idZnaku*rozmiarPola, idWiersza*rozmiarPola));
						this.add(listaPostaci.get(listaPostaci.size()-1));
						break;
					case '2':
						listaPostaci.add(new Slaby(idZnaku*rozmiarPola, idWiersza*rozmiarPola));
						this.add(listaPostaci.get(listaPostaci.size()-1));
						break;
					case 'W':
						drzwi = new Drzwi(idZnaku*rozmiarPola, idWiersza*rozmiarPola);
						this.add(drzwi);
						break;
						
						default:
							break;
					}
					idZnaku++;
				}
			}
			readerPlanszy.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Pliku nie znaleziono.");
		}
		ludek.poziom_energii=100;
		
		RestartujWatekGry();
		this.repaint();
	}
	
	@SuppressWarnings("unchecked")
	public void wczytajZapis() {
		JFileChooser wyborPlikow = new JFileChooser();
		FileInputStream odtwarzacz = null;
		wyborPlikow.setFileFilter(new FileNameExtensionFilter("bin", "bin"));
		wyborPlikow.setCurrentDirectory(new File("Zapisane stany gry"));
		if(wyborPlikow.showOpenDialog(this.getRootPane())==JFileChooser.APPROVE_OPTION) {
			listaPostaci = new ArrayList<KlasaPostaci>();
			listaPrzeszkod = new ArrayList<KlasaPrzeszkody>();
			try {
				odtwarzacz = new FileInputStream(wyborPlikow.getSelectedFile());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ObjectInputStream wynik;
			try {
				wynik = new ObjectInputStream(odtwarzacz);
				try {
					listaPrzeszkod = (ArrayList<KlasaPrzeszkody>) wynik.readObject();
					listaPostaci = (ArrayList<KlasaPostaci>) wynik.readObject();
					ludek = (Bohater) wynik.readObject();
					drzwi = (Drzwi) wynik.readObject();
					
					for(int i = 0; i<listaPostaci.size(); i+=1) {
						this.add(listaPostaci.get(i));
					}
					for(int i = 0; i<listaPrzeszkod.size(); i+=1) {
						this.add(listaPrzeszkod.get(i));	
					}
					this.add(ludek);
					this.add(drzwi);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println("Za³adowano stan gry");
			} catch (IOException e) {
				System.out.println("Pojawi³ siê problem z za³adowaniem zapisu");
				e.printStackTrace();
			}
			RestartujWatekGry();
			this.repaint();
		}
	}
	
	public void NacisnietoPrzycisk(KeyEvent e) {
		if(watekGry.isAlive()) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if(czyMozliwyRuch(1)) this.ludek.Ruch(1);
			break;
			case KeyEvent.VK_RIGHT:
				if(czyMozliwyRuch(2)) this.ludek.Ruch(2);
			break;
			case KeyEvent.VK_DOWN:
				if(czyMozliwyRuch(3)) this.ludek.Ruch(3);
				break;
			case KeyEvent.VK_LEFT:
				if(czyMozliwyRuch(4)) this.ludek.Ruch(4);
				break;
			default:
				break;
			}
			if(ludek.getLocation().equals(drzwi.getLocation())) {
				System.out.println("wygrana");
				PanelNawigacji.zmianaStanuFormatki(0);
			}
		}
	}
	
	public boolean czyMozliwyRuch(int kierunek) {
		if(kierunek == 1) {
			for(int i = 0; i<listaPostaci.size(); i+=1) {
				if(ludek.getLocation().getX()==listaPostaci.get(i).getLocation().getX() && (ludek.getLocation().getY()-20)==listaPostaci.get(i).getLocation().getY()) return false;	
			}
			for(int i = 0; i<listaPrzeszkod.size(); i+=1) {
				if(ludek.getLocation().getX()==listaPrzeszkod.get(i).getLocation().getX() && (ludek.getLocation().getY()-20)==listaPrzeszkod.get(i).getLocation().getY()) return false;	
			}
		}
		else if(kierunek == 2) {
			for(int i = 0; i<listaPostaci.size(); i+=1) {
				if(ludek.getLocation().getY()==listaPostaci.get(i).getLocation().getY() && (ludek.getLocation().getX()+20)==listaPostaci.get(i).getLocation().getX()) return false;	
			}
			for(int i = 0; i<listaPrzeszkod.size(); i+=1) {
				if(ludek.getLocation().getY()==listaPrzeszkod.get(i).getLocation().getY() && (ludek.getLocation().getX()+20)==listaPrzeszkod.get(i).getLocation().getX()) return false;	
			}
		}
		else if(kierunek == 3) {
			for(int i = 0; i<listaPostaci.size(); i+=1) {
				if(ludek.getLocation().getX()==listaPostaci.get(i).getLocation().getX() && (ludek.getLocation().getY()+20)==listaPostaci.get(i).getLocation().getY()) return false;	
			}
			for(int i = 0; i<listaPrzeszkod.size(); i+=1) {
				if(ludek.getLocation().getX()==listaPrzeszkod.get(i).getLocation().getX() && (ludek.getLocation().getY()+20)==listaPrzeszkod.get(i).getLocation().getY()) return false;	
			}
		}
		else if(kierunek == 4) {
			for(int i = 0; i<listaPostaci.size(); i+=1) {
				if(ludek.getLocation().getY()==listaPostaci.get(i).getLocation().getY() && (ludek.getLocation().getX()-20)==listaPostaci.get(i).getLocation().getX()) return false;	
			}
			for(int i = 0; i<listaPrzeszkod.size(); i+=1) {
				if(ludek.getLocation().getY()==listaPrzeszkod.get(i).getLocation().getY() && (ludek.getLocation().getX()-20)==listaPrzeszkod.get(i).getLocation().getX()) return false;	
			}
		}
		return true;
	}
	
	public void kasujPostac(KlasaPostaci postac) {
		this.remove(postac);
	}
	
	public int wykonajRuchLosowyWszystkichPostaci(KlasaPostaci postac) {
		Random generatorRuchu = new Random();
		ArrayList<Integer> kierunkiSasiadow = new ArrayList<>();
		int kierunek, kierunekLudka;
		
		kierunkiSasiadow.clear();
		kierunek=0;
		kierunekLudka=0;
		
		for(int j = 0; j<listaPostaci.size(); j+=1) {
			kierunek=zKtorejStronySasiad(postac.getLocation(), listaPostaci.get(j).getLocation(), postac.getSize().width);
			if(kierunek>0) kierunkiSasiadow.add(kierunek);
		}
		for(int j = 0; j<listaPrzeszkod.size(); j+=1) {
			kierunek=zKtorejStronySasiad(postac.getLocation(), listaPrzeszkod.get(j).getLocation(), postac.getSize().width);
			if(kierunek>0) kierunkiSasiadow.add(kierunek);
		}
		
		kierunek=zKtorejStronySasiad(postac.getLocation(), drzwi.getLocation(), postac.getSize().width);
		if(kierunek>0) kierunkiSasiadow.add(kierunek);
		
		kierunek=0;
		kierunekLudka=zKtorejStronySasiad(postac.getLocation(), ludek.getLocation(), postac.getSize().width);
		if(kierunkiSasiadow.size()==3 && kierunekLudka==0) {
			kierunek=1;
			while(kierunkiSasiadow.contains(kierunek)) {
				kierunek++;
			}
			postac.Ruch(kierunek);
		}
		else {
			kierunek=0;
			if(!kierunkiSasiadow.contains(postac.kierunekWstecz)) kierunkiSasiadow.add(postac.kierunekWstecz);
			
			if(kierunkiSasiadow.size()<4 && kierunekLudka==0) {
				while(kierunkiSasiadow.contains(kierunek) || kierunek==0) {
					kierunek=(generatorRuchu.nextInt(4)+1);
				}
				postac.Ruch(kierunek);
			}
			else if(kierunekLudka==0) {
				postac.Ruch(postac.kierunekWstecz);
			}
		}
		
		return kierunekLudka;
	}
	
	public int zKtorejStronySasiad(Point2D obiektPorownywany, Point2D obiektCel, int przeskok) {
		if(obiektPorownywany.getY()==obiektCel.getY()) {
			if((obiektPorownywany.getX()-obiektCel.getX())==przeskok)
				return 4;
			if((obiektPorownywany.getX()-obiektCel.getX())==-przeskok)
				return 2;
		}
		if(obiektPorownywany.getX()==obiektCel.getX()) {
			if((obiektPorownywany.getY()-obiektCel.getY())==przeskok)
				return 1;
			if((obiektPorownywany.getY()-obiektCel.getY())==-przeskok)
				return 3;
		}
		return 0;
	}
	
	public class Rozgrywka implements Runnable {
		@Override
		public void run() {
			while(ludek.poziom_energii>0 && !ludek.getLocation().equals(drzwi.getLocation())) {
				
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				if(pauza==false) {
					for(int i = 0; i<listaPostaci.size() && !ludek.getLocation().equals(drzwi.getLocation()); i++) {
						
						if(wykonajRuchLosowyWszystkichPostaci(listaPostaci.get(i))>0) {
							
							if(ludek.Akcja(listaPostaci.get(i))>0 || listaPostaci.get(i).rodzaj==2) {
								listaPostaci.get(i).Akcja(ludek);
								if(listaPostaci.get(i).rodzaj==2) {
									if((ludek.poziom_energii+listaPostaci.get(i).poziom_energii)<=100) {
										kasujPostac(listaPostaci.get(i)); 
										listaPostaci.remove(i);
									}
								}
							}
							else {
								kasujPostac(listaPostaci.get(i));
								listaPostaci.remove(i);
							}
						}
					}
				}
			}
		}	
	}	
}