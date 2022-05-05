import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelNawigacji extends JPanel{
	public static JButton Start;
	public static JButton ZapiszStanGry;
	public static JButton ZaladujPlansze;
	public static JButton Pauza;
	public static JButton Wznowienie;
	public static JButton ZaladujZapis;
	public static JButton ZapiszDoBazy;
	public static JButton ZaladujZBazy;
	public static JButton ZapiszXML;
	public static JButton ZaladujXML;
	public static JComboBox<String> cbPlansze;
	public static JComboBox<String> cbZapisyZBazy;
	public ArrayList<String> nazwyPlansz;
	public ArrayList<String> nazwyZapisowZBazy;
	public JProgressBar pbEnergiiBohatera;
	public PanelGry panelGry;
	public Runnable objektAktualizujStan;
	Thread watekAktStGry;
	PolaczenieZBaza bazka;
	public PanelNawigacji(PanelGry panelGry1, PolaczenieZBaza baza) {
		super();
		bazka = baza;
		panelGry = panelGry1;
		this.setBackground(new Color(100, 100, 200));
		pbEnergiiBohatera = new JProgressBar(0, 100);
		pbEnergiiBohatera.setForeground(Color.GREEN);
		pbEnergiiBohatera.setBackground(Color.RED);
		pbEnergiiBohatera.setValue(100);
		pbEnergiiBohatera.setPreferredSize(new Dimension(240, 20));
		this.add(pbEnergiiBohatera);
		
		cbZapisyZBazy = new JComboBox<String>();
		cbZapisyZBazy.setBackground(Color.orange);
		nazwyZapisowZBazy = bazka.PobranieZapisowZBazy();
		for(int i=0; i<nazwyZapisowZBazy.size(); i++) {
			cbZapisyZBazy.addItem(nazwyZapisowZBazy.get(i));
		}
		cbZapisyZBazy.setPreferredSize(new Dimension(300,  30));
		
		cbPlansze = new JComboBox<String>();
		cbPlansze.setBackground(Color.orange);
		nazwyPlansz = pobierzPlansze();
		for(int i=0; i<nazwyPlansz.size(); i++) {
			cbPlansze.addItem(nazwyPlansz.get(i));
		}
		cbPlansze.setPreferredSize(new Dimension(300, 30));
		
		Start = new JButton("start");
		ZaladujPlansze = new JButton("Za³aduj planszê");
		ZapiszStanGry = new JButton("Zapisz do pliku");
		Pauza = new JButton("Pauza");
		Wznowienie = new JButton("Wznowienie");
		ZaladujZapis = new JButton("Wczytaj zapis");
		ZapiszDoBazy = new JButton("Zapisz do bazy");
		ZaladujZBazy = new JButton("Za³aduj z bazy");
		ZapiszXML = new JButton("Zapisz XML");
		ZaladujXML = new JButton("Za³aduj XML");
		this.add(Start);
		this.add(Pauza);
		this.add(Wznowienie);
		this.add(ZaladujZapis);
		this.add(ZapiszStanGry);
		this.add(cbPlansze);
		this.add(ZaladujPlansze);
		this.add(cbZapisyZBazy);
		this.add(ZaladujZBazy);
		this.add(ZapiszDoBazy);
		this.add(ZapiszXML);
		this.add(ZaladujXML);
		Start.setPreferredSize(new Dimension(300, 25));
		ZaladujPlansze.setPreferredSize(new Dimension(150, 25));
		ZapiszStanGry.setPreferredSize(new Dimension(150, 25));
		Pauza.setPreferredSize(new Dimension(150, 25));
		Wznowienie.setPreferredSize(new Dimension(150, 25));
		ZaladujZapis.setPreferredSize(new Dimension(150, 25));
		ZapiszDoBazy.setPreferredSize(new Dimension(150, 25));
		ZaladujZBazy.setPreferredSize(new Dimension(150, 25));
		ZapiszXML.setPreferredSize(new Dimension(150, 25));
		ZaladujXML.setPreferredSize(new Dimension(150, 25));
		Start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zmianaStanuFormatki(1);
				objektAktualizujStan = new aktualizujStanGry();
				watekAktStGry = new Thread(objektAktualizujStan, "aktualizacja gry");
				panelGry.Graj();
				panelGry.watekGry.start();
				watekAktStGry.start();
			}
		});
		ZaladujPlansze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelGry.removeAll();
				panelGry.generujPlansze(nazwyPlansz.get(cbPlansze.getSelectedIndex()));
				pbEnergiiBohatera.setValue(panelGry.ludek.poziom_energii);
				zmianaStanuFormatki(0);
				panelGry.grabFocus();
			}
		});
		ZaladujZapis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelGry.removeAll();
				panelGry.wczytajZapis();
				pbEnergiiBohatera.setValue(panelGry.ludek.poziom_energii);
				zmianaStanuFormatki(0);
				panelGry.grabFocus();
			}
		});
		ZaladujZBazy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelGry.removeAll();
				bazka.ZaladowanieZapisuZBazy(panelGry, cbZapisyZBazy.getSelectedItem().toString());
				pbEnergiiBohatera.setValue(panelGry.ludek.poziom_energii);
				zmianaStanuFormatki(0);
				panelGry.grabFocus();
			}
		});
		ZapiszStanGry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zapisanieStanuGry();
				panelGry.grabFocus();
			}
		});
		ZapiszDoBazy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bazka.zapisStanuDoBazy("test "+ nazwyZapisowZBazy.size() +"", panelGry.listaPrzeszkod, panelGry.listaPostaci, panelGry.ludek, panelGry.drzwi);
				panelGry.grabFocus();
			}
		});
		Pauza.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelGry.pauza=true;
				zmianaStanuFormatki(3);
				panelGry.grabFocus();
			}
		});
		Wznowienie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelGry.pauza=false;
				zmianaStanuFormatki(1);
				panelGry.grabFocus();
			}
		});
		ZapiszXML.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zapisywanieXML();
			}
		});
		ZaladujXML.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("zaladowywanie XML");
			}
		});
		zmianaStanuFormatki(2);
	}
	
	public class aktualizujStanGry implements Runnable {

		@Override
		public void run() {
			while(pbEnergiiBohatera.getValue()>0) {
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				pbEnergiiBohatera.setValue(panelGry.ludek.poziom_energii);
			}
			if(panelGry.ludek.poziom_energii<1) {
				System.out.println("przegrana");
				zmianaStanuFormatki(0);
			}
		}
		
	}
	
	public ArrayList<String> pobierzPlansze() {
		ArrayList<String> lista_do_zwrotu = new ArrayList<String>();
		File sciezka = new File("Plansze");
		if(!sciezka.isDirectory()) {
			System.out.println("Folder '" + sciezka.getPath() + "' nie istnieje!");
		}
		else{
			File[] pliki = sciezka.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".txt");
				}
			});
			
			for(File plik: pliki){
				lista_do_zwrotu.add(plik.getName().substring(0, plik.getName().toString().length()-4));
			}
		}
		return lista_do_zwrotu;
	}
	
	public void zapisanieStanuGry() {
		JFileChooser wyborPlikow = new JFileChooser();
		FileOutputStream zapisywacz = null;
		wyborPlikow.setFileFilter(new FileNameExtensionFilter("bin", "bin"));
		wyborPlikow.setCurrentDirectory(new File("Zapisane stany gry"));
		if(wyborPlikow.showSaveDialog(this.getRootPane())==JFileChooser.APPROVE_OPTION) {
			
				try {
					zapisywacz = new FileOutputStream(wyborPlikow.getSelectedFile()+".bin");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				ObjectOutputStream wynik;
				try {
					wynik = new ObjectOutputStream(zapisywacz);
					wynik.writeObject(panelGry.listaPrzeszkod);
					wynik.writeObject(panelGry.listaPostaci);
					wynik.writeObject(panelGry.ludek);
					wynik.writeObject(panelGry.drzwi);
					wynik.close();
					System.out.println("Zapisano stan gry");
				} catch (IOException e) {
					System.out.println("Pojawi³ siê problem z zapisaniem");
					e.printStackTrace();
				}
		}
	}
	
	public void zapisywanieXML() {
		JFileChooser wyborPlikow = new JFileChooser();
		FileOutputStream zapisywacz = null;
		wyborPlikow.setFileFilter(new FileNameExtensionFilter("xml", "xml"));
		wyborPlikow.setCurrentDirectory(new File("Zapisane stany gry"));
		if(wyborPlikow.showSaveDialog(this.getRootPane())==JFileChooser.APPROVE_OPTION) {
			
				try {
					zapisywacz = new FileOutputStream(wyborPlikow.getSelectedFile()+".xml");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				XMLEncoder wynik;
				try {
					wynik = new XMLEncoder(zapisywacz);
					//wynik.writeObject(panelGry.listaPrzeszkod);
					//wynik.writeObject(panelGry.listaPostaci);
					wynik.writeObject(panelGry.ludek);
					//wynik.writeObject(panelGry.drzwi);
					wynik.close();
					System.out.println("Zapisano stan gry");
				} catch (Exception e) {
					System.out.println("Problem z zapisem xml");
					e.printStackTrace();
					
				}
		}
	}
	
	public static void zmianaStanuFormatki(int stan) {
		if(stan==0) {   //	plansza zaÅ‚adowana ale gra nie rozpoczÄ™ta
			Start.setEnabled(true);
			cbPlansze.setEnabled(true);
			ZaladujPlansze.setEnabled(true);
			ZapiszStanGry.setEnabled(false);
			Pauza.setEnabled(false);
			Wznowienie.setEnabled(false);
			ZaladujZapis.setEnabled(true);
			ZapiszDoBazy.setEnabled(false);
			ZaladujZBazy.setEnabled(true);
		}
		else if(stan==1) {	//	w trakcie gry
			Start.setEnabled(false);
			cbPlansze.setEnabled(false);
			ZaladujPlansze.setEnabled(false);
			ZapiszStanGry.setEnabled(false);
			Pauza.setEnabled(true);
			Wznowienie.setEnabled(false);
			ZaladujZapis.setEnabled(false);
			ZapiszDoBazy.setEnabled(false);
			ZaladujZBazy.setEnabled(false);
		}
		else if(stan==2) {	//	po uruchomieniu programu
			Start.setEnabled(false);
			cbPlansze.setEnabled(true);
			ZaladujPlansze.setEnabled(true);
			ZapiszStanGry.setEnabled(false);
			Pauza.setEnabled(false);
			Wznowienie.setEnabled(false);
			ZaladujZapis.setEnabled(true);
			ZapiszDoBazy.setEnabled(false);
			ZaladujZBazy.setEnabled(true);
		}
		else if(stan==3) {	//	zapaÅ‚zowana
			Start.setEnabled(false);
			cbPlansze.setEnabled(false);
			ZaladujPlansze.setEnabled(false);
			ZapiszStanGry.setEnabled(true);
			Pauza.setEnabled(false);
			Wznowienie.setEnabled(true);
			ZaladujZapis.setEnabled(false);
			ZapiszDoBazy.setEnabled(true);
			ZaladujZBazy.setEnabled(false);
		}
	}
}