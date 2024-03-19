package korisnici;

import korisnici.Korisnik.tipKorisnika;
import korisnici.Zaposleni.nivoSpreme;
import tretmani.Tretmani;
import tretmani.ZakazanTretman;

import java.io.IOException; 
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.io.PrintWriter;


public class Korisnici {
	public static HashMap <Integer, Korisnik> sviKorisnici = new HashMap<Integer, Korisnik>();
	public static HashMap <Integer, Kozmeticar> sviKozmeticari = new HashMap<Integer, Kozmeticar>();

	public static Korisnik trenutniKorisnik;
	
	public static void ucitajKorisnikeIzFajla(String putanja) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(putanja));
					String linija;
					Korisnik korisnik = null;
					while((linija = in.readLine()) != null) {
					  String[] stavke = new String[20];
					  stavke = linija.split("\\|");
						  switch(stavke[0]) {
							  case "KLIJENT":{
								  korisnik = new Korisnik(Integer.parseInt(stavke[1]),stavke[2], stavke[3], stavke[4], stavke[5], stavke[6], stavke[7], stavke[8],
										  Boolean.parseBoolean(stavke[9]), Integer.parseInt(stavke[10]));
								  break;
							  }
							  case "MENADZER":{
								  korisnik = new Zaposleni(Integer.parseInt(stavke[1]),stavke[2], stavke[3], stavke[4], stavke[5], stavke[6], stavke[7], stavke[8],
										   nivoSpreme.sprema(stavke[9]), Integer.parseInt(stavke[10]), Integer.parseInt(stavke[11]), tipKorisnika.MENADZER);
								  break;
							  }
							  case "RECEPCIONER":{
								  korisnik = new Zaposleni(Integer.parseInt(stavke[1]),stavke[2], stavke[3], stavke[4], stavke[5], stavke[6], stavke[7], stavke[8],
										   nivoSpreme.sprema(stavke[9]), Integer.parseInt(stavke[10]), Integer.parseInt(stavke[11]), tipKorisnika.RECEPCIONER);
								  break;
							  }
							  case "KOZMETICAR":{
								  if(stavke.length==13) {
									  korisnik = new Kozmeticar(Integer.parseInt(stavke[1]), stavke[2], stavke[3], stavke[4], stavke[5], stavke[6], stavke[7], stavke[8],
											  nivoSpreme.sprema(stavke[9]), Integer.parseInt(stavke[10]), Integer.parseInt(stavke[11]), stavke[12]);
								  }
								  else {
									  korisnik = new Kozmeticar(Integer.parseInt(stavke[1]), stavke[2], stavke[3], stavke[4], stavke[5], stavke[6], stavke[7], stavke[8],
											  nivoSpreme.sprema(stavke[9]), Integer.parseInt(stavke[10]), Integer.parseInt(stavke[11]));
								  }

								  sviKozmeticari.put(korisnik.idGetter(), (Kozmeticar) korisnik);
								  break;
							  }
						  }
						  sviKorisnici.put(korisnik.idGetter(), korisnik);
					}
			if(korisnik == null) {
					Korisnik.idPoslednjegSetter(0);
				}
			else {
					Korisnik.idPoslednjegSetter(korisnik.idGetter()); 
				}
			in.close();
		} 
		catch (IOException e) {
			System.out.println("Doslo je do greske");
			e.printStackTrace();
		}
	}
	public static boolean dodajKorisnika(Korisnik korisnik) {
		boolean duplicate=false;
		for (Korisnik k : sviKorisnici.values()) {
			  if (korisnik.equals(k)) {
				  duplicate = true;
			  }
			}
		if (duplicate == false){
			sviKorisnici.put(korisnik.idGetter(), korisnik);
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean dodajKorisnika(Kozmeticar korisnik) {
		boolean duplicate=false;
		for (Korisnik k : sviKorisnici.values()) {
			if (korisnik.equals(k)) {
				duplicate = true;
			}
		}
		if (duplicate == false){
			sviKorisnici.put(korisnik.idGetter(), korisnik);
			sviKozmeticari.put((korisnik.idGetter()),korisnik);
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void obrisiKorisnika(Korisnik korisnik) {
		sviKorisnici.remove(korisnik.idGetter());
		for(ZakazanTretman z: Tretmani.sviZakazaniTretmani.values())
		{
			if(korisnik.equals(z.getKlijent()) || korisnik.equals(z.getKozmeticar())) {
				Tretmani.obrisiZakazan(z);
			}
		}
	}
	 public static void sacuvajKorisnike(String putanja) {
		 try {
			PrintWriter pisi = new PrintWriter(new FileWriter(putanja));
			for (Korisnik k : sviKorisnici.values()) {
				  pisi.println(k.toString());
				}
			pisi.close();
		} catch (IOException e) {
			System.out.println("Doslo je do greske");
			e.printStackTrace();
		}
	 }
	
	 public static void ispisiKorisnike() {
		 for (Korisnik k : sviKorisnici.values()) {
			  k.ispisiKorisnika();
			}
		 System.out.println("========================================================================================================================================");
	 }
	 
	 public static boolean login(String username, String password) {
		 for (Korisnik k : sviKorisnici.values()) {
			  if(k.korisnickoIme.equals(username) && k.lozinka.equals(password)) {
				  trenutniKorisnik = k;
				  System.out.println("Dobrodosli, " + k.ime);
				  return true;
			  }
			}
		 return false;
	 }
	 public static void logout() {
		 trenutniKorisnik = null;
		 System.out.println("Dovidjenja");

	 }

	 public static Korisnik nadjiKorisnika(String ime, String prezime){
		for(Korisnik k:sviKorisnici.values()){
			if (k.getIme().equals(ime) && k.getPrezime().equals(prezime)){
				return k;
			}
		}
		return null;
	 }

	public static ArrayList<Korisnik> sviLoyalty() {
		ArrayList<Korisnik> korisnici = new ArrayList<>();
		for(Korisnik k: sviKorisnici.values()){
			if(k.tip==tipKorisnika.KLIJENT && k.getLoyalty()){
				korisnici.add(k);
			}
		}
		return korisnici;
	}
}
