package salon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import korisnici.Korisnici;
import tretmani.TipTretmana;
import tretmani.Tretmani;
import tretmani.ZakazanTretman;

public class Saloni {
	public static HashMap <Integer, Salon> sviSaloni = new HashMap<Integer, Salon>(); 
	public static Salon trenutniSalon;

	/////TIPOVI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static void ucitajSalone(String putanja) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(putanja));
					String linija;
					Salon salon = null;
					while((linija = in.readLine()) != null) {
					  String[] stavke = new String[20];
					  stavke = linija.split("\\|");
					  salon = new Salon(Integer.parseInt(stavke[0]), stavke[1], stavke[2], stavke[3], stavke[4], stavke[5],stavke[6],stavke[7],Integer.parseInt(stavke[8]));
					  
					  sviSaloni.put(salon.idGetter(), salon);
					}
				if(salon == null) {
						Salon.idPoslednjegSetter(0);
				}
				else {
						Salon.idPoslednjegSetter(salon.idGetter()); 
				}
			in.close();
		} 
		catch (IOException e) {
			System.out.println("Doslo je do greske");
			e.printStackTrace();
		}
	}
	 public static void sacuvajSalone(String putanja) {
		 try {
			PrintWriter pisi = new PrintWriter(new FileWriter(putanja));
			for (Salon salon : sviSaloni.values()) {
				  pisi.println(salon.toString());
				}
			pisi.close();
		} catch (IOException e) {
			System.out.println("Doslo je do greske");
			e.printStackTrace();
		}
	 }
	 public static void dodajSalon(Salon salon) {
			boolean duplicate=false;
			for (Salon s : sviSaloni.values()) {
				  if (salon.equals(s)) {
					  duplicate = true;
				  }
				}
			if (duplicate == false){
				sviSaloni.put(salon.idGetter(), salon);
			}
			else {
				System.out.println("Tip tretmana nije dodat jer vec postoji isti");
			}
		}
	public static void obrisiSalon(Salon salon) {
		sviSaloni.remove(salon.idGetter());
		}
	
	public static void inicijalizujSalon(Salon salon) {
		Korisnici.sviKorisnici.clear();
		Tretmani.sveVrsteTretmana.clear();
		Tretmani.sviTipoviTretmana.clear();
		Tretmani.sviZakazaniTretmani.clear();
		Tretmani.ucitajTipoveTretmana(Salon.odrediPutanjuFajla(salon.imeFajlaTipovaTretmana));
		Korisnici.ucitajKorisnikeIzFajla(Salon.odrediPutanjuFajla(salon.imeFajlaKorisnika));
		Tretmani.ucitajVrsteTretmana(Salon.odrediPutanjuFajla(salon.imeFajlaVrstaTretmana));
		Tretmani.ucitajZakazaneTretmane(Salon.odrediPutanjuFajla(salon.imeFajlaZakazanihTretmana));
		trenutniSalon = salon;
	}
	public static void sacuvajSalonPodatke(Salon salon) {
		Tretmani.sacuvajTipoveTretmana(Salon.odrediPutanjuFajla(salon.imeFajlaTipovaTretmana));
		Korisnici.sacuvajKorisnike(Salon.odrediPutanjuFajla(salon.imeFajlaKorisnika));
		Tretmani.sacuvajVrsteTretmana(Salon.odrediPutanjuFajla(salon.imeFajlaVrstaTretmana));
		Tretmani.sacuvajZakazaneTretmane(Salon.odrediPutanjuFajla(salon.imeFajlaZakazanihTretmana));
		sacuvajSalone(Salon.odrediPutanjuFajla("svi_saloni.csv"));
	}
}
