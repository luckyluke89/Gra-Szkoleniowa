import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class PolaczenieZBaza {
	Connection polaczenie;
	String adres = "jdbc:sqlite:Baza/bazaGraSzkoleniowa.db";
	public PolaczenieZBaza() {
		polaczenie = null;
		try {
			polaczenie = DriverManager.getConnection(adres);
			Statement stat = polaczenie.createStatement();
			String generatorTabeli = "CREATE TABLE IF NOT EXISTS Zapisy (id INTEGER PRIMARY KEY AUTOINCREMENT, NazwaZapisu varchar(255), Dane BLOB)";
			try {
				stat.execute(generatorTabeli);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(polaczenie != null) {
					polaczenie.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		polaczenie = null;
	}
	
	public void zapisStanuDoBazy(String nazwa, ArrayList<KlasaPrzeszkody> przeszkody, ArrayList<KlasaPostaci> postacie, Bohater glowny_bohater, Drzwi wyjscie) {
		ObjectOutputStream strumien;
		ByteArrayOutputStream strumienBytow = new ByteArrayOutputStream();
		
		try {
			strumien = new ObjectOutputStream(strumienBytow);
			try {
				strumien.writeObject(przeszkody);
				strumien.writeObject(postacie);
				strumien.writeObject(glowny_bohater);
				strumien.writeObject(wyjscie);
				strumien.close();
				strumienBytow.close();
			} catch (IOException e) {
				System.out.println("coœ nie tak z zapisem");
				e.printStackTrace();
			}
		} catch (FileNotFoundException e2) {
			System.out.println("niema pliku");
			e2.printStackTrace();
		} catch (IOException e2) {
			System.out.println("coœ innego");
			e2.printStackTrace();
		}
		
		try {
			polaczenie = DriverManager.getConnection(adres);

				try {
					PreparedStatement przygotowane_zapytanie = polaczenie.prepareStatement("insert into Zapisy values (NULL, ?, ?)");
					przygotowane_zapytanie.setString(1, nazwa);
					przygotowane_zapytanie.setBytes(2, strumienBytow.toByteArray());
					przygotowane_zapytanie.execute();
				} catch (SQLException e) {
					System.out.println("problem z zapisaniem");
					e.printStackTrace();
				}
			
		} catch (SQLException e) {
			System.out.println("coœ z tweorzeniem po³aczenia");
			System.out.println(e.getMessage());
		} finally {
			try {
				if(polaczenie != null) {
					polaczenie.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		polaczenie = null;
	}
	
	@SuppressWarnings("unchecked")
	public void ZaladowanieZapisuZBazy(PanelGry panelGry2, String NazwaZapisu) {
		ObjectInputStream zrodlo;
		String q_select = "SELECT Dane FROM Zapisy WHERE NazwaZapisu LIKE '"+ NazwaZapisu +"'";
		ResultSet wynik;
		PreparedStatement stat;
		try {
			polaczenie = DriverManager.getConnection(adres);
			stat = polaczenie.prepareStatement(q_select);
			wynik = stat.executeQuery();
			
			try {
				zrodlo = new ObjectInputStream(wynik.getBinaryStream("Dane"));
				try {
					panelGry2.listaPrzeszkod = (ArrayList<KlasaPrzeszkody>) zrodlo.readObject();
					panelGry2.listaPostaci = (ArrayList<KlasaPostaci>) zrodlo.readObject();
					panelGry2.ludek = (Bohater) zrodlo.readObject();
					panelGry2.drzwi = (Drzwi) zrodlo.readObject();
					
					for(int i = 0; i<panelGry2.listaPostaci.size(); i+=1) {
						panelGry2.add(panelGry2.listaPostaci.get(i));
					}
					for(int i = 0; i<panelGry2.listaPrzeszkod.size(); i+=1) {
						panelGry2.add(panelGry2.listaPrzeszkod.get(i));	
					}
					panelGry2.add(panelGry2.ludek);
					panelGry2.add(panelGry2.drzwi);
				} catch (ClassNotFoundException e) {
					System.out.println("coœ nie tak z odczytem objektów");
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.out.println("coœ nie tak z zapisem do zrodla objektow");
				e.printStackTrace();
			}
			
			panelGry2.RestartujWatekGry();
			panelGry2.repaint();
		} catch (SQLException e) {
			System.out.println("coœ nie tak z utworzeniem po³¹czenia");
			e.printStackTrace();
		} finally {
			if(polaczenie != null) {
				try {
					polaczenie.close();
				} catch (SQLException e) {
					System.out.println("coœ nie tak z zamkniêciem");
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public ArrayList<String> PobranieZapisowZBazy() {
		ArrayList<String> listaDoZwrotu = new ArrayList<String>();
		String q_select1 = "SELECT id, NazwaZapisu FROM Zapisy";
		ResultSet wynik;
		Statement stat1;
		
		try {
			polaczenie = DriverManager.getConnection(adres);
			stat1 = polaczenie.createStatement();
			wynik = stat1.executeQuery(q_select1);
			while(wynik.next()) {
				listaDoZwrotu.add(wynik.getString("NazwaZapisu"));
			}
		} catch (SQLException e) {
			System.out.println("coœ nie tak z utworzeniem po³¹czenia");
			e.printStackTrace();
		} finally {
			if(polaczenie != null) {
				try {
					polaczenie.close();
				} catch (SQLException e) {
					System.out.println("coœ nie tak z zamkniêciem");
					e.printStackTrace();
				}
			}
		}
		polaczenie = null;
		return listaDoZwrotu;
	}
}
