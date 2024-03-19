package tretmani;

import java.io.BufferedReader;

import java.text.SimpleDateFormat;
import java.time.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.*;

import korisnici.Korisnici;
import korisnici.Korisnik;
import korisnici.Kozmeticar;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.util.*;
import org.jdatepicker.impl.*;
import org.jdatepicker.graphics.*;

import salon.Saloni;
import tretmani.ZakazanTretman.statusTretmana;

import javax.swing.*;
import javax.swing.text.DateFormatter;


public class Tretmani {
	public static HashMap <Integer, TipTretmana> sviTipoviTretmana = new HashMap<Integer, TipTretmana>(); 
	public static HashMap <Integer, VrstaTretmana> sveVrsteTretmana = new HashMap<Integer, VrstaTretmana>(); 
	public static HashMap <Integer, ZakazanTretman> sviZakazaniTretmani = new HashMap<Integer, ZakazanTretman>(); 
	public static HashMap <LocalDate, ArrayList<ZakazanTretman>> sviZakazaniDatum = new HashMap<LocalDate, ArrayList<ZakazanTretman>>();

	/////TIPOVI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static void ucitajTipoveTretmana(String putanja) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(putanja));
					String linija;
					TipTretmana tip = null;
					while((linija = in.readLine()) != null) {
					  String[] stavke = new String[20];
					  stavke = linija.split("\\|");
					  tip = new TipTretmana(stavke[0], Integer.parseInt(stavke[1]));
					  
					  sviTipoviTretmana.put(tip.idGetter(), tip);
					}
				if(tip == null) {
						TipTretmana.idPoslednjegSetter(0);
				}
				else {
						TipTretmana.idPoslednjegSetter(tip.idGetter()); 
				}
			in.close();
		} 
		catch (IOException e) {
			System.out.println("Doslo je do greske");
			e.printStackTrace();
		}
	}
	 public static void sacuvajTipoveTretmana(String putanja) {
		 try {
			PrintWriter pisi = new PrintWriter(new FileWriter(putanja));
			for (TipTretmana tip : sviTipoviTretmana.values()) {
				  pisi.println(tip.toString());
				}
			pisi.close();
		} catch (IOException e) {
			System.out.println("Doslo je do greske");
			e.printStackTrace();
		}
	 }
	 public static boolean dodajTipTretmana(TipTretmana tip) {
			boolean duplicate=false;
			for (TipTretmana t : sviTipoviTretmana.values()) {
				  if (tip.equals(t)) {
					  duplicate = true;
				  }
				}
			if (duplicate == false){
				sviTipoviTretmana.put(tip.idGetter(), tip);
				return true;
			}
			else {
				System.out.println("Tip tretmana nije dodat jer vec postoji isti");
				return false;
			}
		}
	public static void obrisiTip(TipTretmana tip) {
			for(VrstaTretmana v: sveVrsteTretmana.values()) {
				if(v.tip.equals(tip)) {
					obrisiVrstu(v);
				}
			}
			for(Kozmeticar k: Korisnici.sviKozmeticari.values()) {
				if(k.obucenZa(tip)) {
					k.skloniObuku(tip);
				}
			}
			sviTipoviTretmana.remove(tip.idGetter());
		}
	 
		/////VRSTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 
	 
	public static void ucitajVrsteTretmana(String putanja) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(putanja));
						String linija;
						VrstaTretmana vrsta = null;
						while((linija = in.readLine()) != null) {
						  String[] stavke = new String[20];
						  stavke = linija.split("\\|");
						  vrsta = new VrstaTretmana(Integer.parseInt(stavke[0]), stavke[1], Integer.parseInt(stavke[2]),Integer.parseInt(stavke[3]), Duration.parse(stavke[4]));
						  
						  sveVrsteTretmana.put(vrsta.idGetter(), vrsta);
						}
				if(vrsta == null) {
					VrstaTretmana.idPoslednjegSetter(0);
				}
				else {
						VrstaTretmana.idPoslednjegSetter(vrsta.idGetter()); 
				}
					in.close();
				} 
			catch (IOException e) {
				System.out.println("Doslo je do greske");
				e.printStackTrace();
			}
		}
	public static void sacuvajVrsteTretmana(String putanja) {
			 try {
				PrintWriter pisi = new PrintWriter(new FileWriter(putanja));
				for (VrstaTretmana vrsta : sveVrsteTretmana.values()) {
					  pisi.println(vrsta.toString());
					}
				pisi.close();
			} catch (IOException e) {
				System.out.println("Doslo je do greske");
				e.printStackTrace();
			}
		 }
	public static boolean dodajVrstuTretmana(VrstaTretmana vrsta) {
				boolean invalid=false;
				if(sviTipoviTretmana.get(vrsta.idGetter()) == null) {
					invalid=false;
				}
				for (VrstaTretmana v : sveVrsteTretmana.values()) {
					  if (vrsta.equals(v)) {
						  invalid = true;
					  }
					}
				if (invalid == false){
					sveVrsteTretmana.put(vrsta.idGetter(), vrsta);
					return true;
				}
				else {
					System.out.println("Vrsta tretmana nije dodata jer vec postoji ista ili ne postoji tip tretmana");
					return false;
				}
	}	
	public static void obrisiVrstu(VrstaTretmana vrsta) {
		for(ZakazanTretman z: sviZakazaniTretmani.values()) {
			if(z.vrsta.equals(vrsta)) {
				obrisiZakazan(z);
			}
		}
		sveVrsteTretmana.remove(vrsta.idGetter());
	}
	/////ZAKAZANI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 
	 
	public static void ucitajZakazaneTretmane(String putanja) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(putanja));
						String linija;
						ZakazanTretman zakazan = null;
						while((linija = in.readLine()) != null) {
						  String[] stavke = new String[20];
						  stavke = linija.split("\\|");
						  zakazan = new ZakazanTretman(Integer.parseInt(stavke[0]), Integer.parseInt(stavke[1]),
								  stavke[2], Integer.parseInt(stavke[3]), stavke[4], Integer.parseInt(stavke[5]), Integer.parseInt(stavke[6]));
						  sviZakazaniTretmani.put(zakazan.idGetter(), zakazan);
						  LocalDate datum = (zakazan.datum_i_vreme).toLocalDate();
							if (sviZakazaniDatum.get(datum)==null) {
								sviZakazaniDatum.put(datum, new ArrayList<ZakazanTretman>());
							}
							sviZakazaniDatum.get(datum).add(zakazan);
						}
				if(zakazan == null) {
						ZakazanTretman.idPoslednjegSetter(0);
				}
				else {
						ZakazanTretman.idPoslednjegSetter(zakazan.idGetter()); 
				}
				in.close();
			} 
			catch (IOException e) {
				System.out.println("Doslo je do greske");
				e.printStackTrace();
			}
		}
	public static void sacuvajZakazaneTretmane(String putanja) {
			 try {
				PrintWriter pisi = new PrintWriter(new FileWriter(putanja));
				for (ZakazanTretman zakazan : sviZakazaniTretmani.values()) {
					  pisi.println(zakazan.toString());
					}
				pisi.close();
			} catch (IOException e) {
				System.out.println("Doslo je do greske");
				e.printStackTrace();
			}
		 }
	public static void dodajZakazanTretman(ZakazanTretman zakazan) {
				boolean invalid=false;
				if(sveVrsteTretmana.get(zakazan.vrsta.idGetter()) == null) {
					invalid=false;
				}
				for (ZakazanTretman z : sviZakazaniTretmani.values()) {
					  if (zakazan.equals(z)) {
						  invalid = true;
					  }
					}
				if (invalid == false){
					sviZakazaniTretmani.put(zakazan.idGetter(), zakazan);
					LocalDate datum = (zakazan.datum_i_vreme).toLocalDate();
					if (sviZakazaniDatum.get(datum)==null) {
						sviZakazaniDatum.put(datum, new ArrayList<ZakazanTretman>());
					}
					sviZakazaniDatum.get(datum).add(zakazan);
				}
				else {
					System.out.println("Vrsta tretmana nije dodata jer vec postoji ista ili ne postoji tip tretmana");
				}
	}	
	public static void obrisiZakazan(ZakazanTretman zakazan) {
		sviZakazaniTretmani.remove(zakazan.idGetter());
		sviZakazaniDatum.remove(zakazan.getDatum_i_vreme());

	}
	
	public static void ispisiZakazaneTretmane() {
		for (ZakazanTretman z : sviZakazaniTretmani.values()) {
			z.ispisZakazanogTretmana();
		}
		 System.out.println("========================================================================================================================================");
	}
	public static void ispisiVrsteTretmana() {
		for (VrstaTretmana v : sveVrsteTretmana.values()) {
			v.ispisVrsteTretmana();
		}
		 System.out.println("========================================================================================================================================");
	}
	public static void ispisiTipoveTretmana() {
		for (TipTretmana t : sviTipoviTretmana.values()) {
			t.ispisTipaTretmana();
		}
		 System.out.println("========================================================================================================================================");
	}
	
	public static ArrayList<Kozmeticar> obuceniKozmeticari(int idVrste) {
		ArrayList<Kozmeticar> obuceni = new ArrayList<Kozmeticar>();
		TipTretmana trazeniTip = (Tretmani.sveVrsteTretmana.get(idVrste)).tip;
		
		
		for(Kozmeticar k : Korisnici.sviKozmeticari.values()) {
			if (k.obucenZa(trazeniTip)){
				obuceni.add(k);
			}
		}
		
		return obuceni;
	}
	public static ArrayList<Kozmeticar> obuceniKozmeticari(VrstaTretmana vrsta) {
		ArrayList<Kozmeticar> obuceni = new ArrayList<Kozmeticar>();
		TipTretmana trazeniTip = vrsta.tip;
		
		
		for(Kozmeticar k : Korisnici.sviKozmeticari.values()) {
			if (k.obucenZa(trazeniTip)){
				obuceni.add(k);
			}
		}
		
		return obuceni;
	}
	
	public static ArrayList<ZakazanTretman> obavezeKozmeticara(Kozmeticar kozmeticar, LocalDate datum){
		ArrayList<ZakazanTretman> zakazani = new ArrayList<ZakazanTretman>();
		ArrayList<ZakazanTretman> svi = sviZakazaniDatum.get(datum);
		if (svi==null) {
			return zakazani;
		}
		
		for(ZakazanTretman z: svi) {
			if (z.kozmeticar.equals(kozmeticar) && z.status.equals(statusTretmana.ZAKAZAN) ) {
				zakazani.add(z);
			}
		}
		
		return zakazani;
	}
	
	public static ArrayList<LocalTime> slobodniTermini(LocalDate datum, Kozmeticar kozmeticar, VrstaTretmana tretman){
		ArrayList<LocalTime> termini = new ArrayList<LocalTime>();
		ArrayList<ZakazanTretman> zakazani = obavezeKozmeticara(kozmeticar,datum);
		LocalTime vreme = Saloni.trenutniSalon.getOtvaranje();
		LocalTime kraj = Saloni.trenutniSalon.getZatvaranje();
		
		while (vreme.plus(tretman.getTrajanje()).isBefore(kraj) || vreme.plus(tretman.getTrajanje()).equals(kraj)) {
			boolean slobodno = true;
			for(ZakazanTretman z: zakazani) {
				if( (z.datum_i_vreme.toLocalTime()).equals(vreme)) {
					slobodno = false;
					vreme = vreme.plus(z.vrsta.getTrajanje());
					vreme = vreme.withMinute(0);
				}
				if(vreme.isBefore(z.datum_i_vreme.toLocalTime()) && vreme.plus(tretman.getTrajanje()).isAfter(z.datum_i_vreme.toLocalTime())) {
					slobodno = false;
					vreme = z.datum_i_vreme.toLocalTime();
					vreme = vreme.plus(z.vrsta.getTrajanje());
					vreme = vreme.withMinute(0);
				}
			}
			if (slobodno){
				termini.add(vreme);
			}
			vreme = vreme.plusHours(1);
		}
		
		return termini;
	}
	
	public static ArrayList<ZakazanTretman> prikaziRaspored(Kozmeticar kozmeticar, LocalDate datum) {
		ArrayList<ZakazanTretman> zakazani = obavezeKozmeticara(kozmeticar,datum);
		ArrayList<ZakazanTretman> zakazanisort = new ArrayList<>();
		LocalTime vreme = Saloni.trenutniSalon.getOtvaranje();
		LocalTime kraj = Saloni.trenutniSalon.getZatvaranje();
		while (vreme.isBefore(kraj)) {
			for(ZakazanTretman z: zakazani) {
				if( z.datum_i_vreme.toLocalTime().equals(vreme)) {
					zakazanisort.add(z);
				}
			}
			vreme = vreme.plusHours(1);
		}
		return zakazanisort;
	}
	
	public static void prikaziRaspored(Kozmeticar kozmeticar) {
		LocalDate datum = LocalDate.now();
		LocalDate datumKraj = datum.plusDays(31);
		
		while(datum.isBefore(datumKraj)) {
			prikaziRaspored(kozmeticar, datum);
			datum=datum.plusDays(1);
		}
	}
	
	public static ArrayList<ZakazanTretman> odrediPrethodneTretmane(Korisnik klijent) {
		LocalDateTime sada = LocalDateTime.now();
		ArrayList<ZakazanTretman> lista = new ArrayList<ZakazanTretman>();

		for(ZakazanTretman zakazan: sviZakazaniTretmani.values()) {
			if(zakazan.klijent.equals(klijent) && zakazan.datum_i_vreme.isBefore(sada)) {
				lista.add(zakazan);
			}
		}
		return lista;
	}
	public static ArrayList<ZakazanTretman> odrediPrethodneTretmane() {
		//LocalDateTime sada = LocalDateTime.now();
		LocalDateTime sada = LocalDateTime.now();
		ArrayList<ZakazanTretman> lista = new ArrayList<ZakazanTretman>();

		for(ZakazanTretman zakazan: sviZakazaniTretmani.values()) {
			if(zakazan.datum_i_vreme.isBefore(sada)) {
				lista.add(zakazan);
			}
		}
		return lista;
	}
	public static ArrayList<ZakazanTretman> prikaziBuduceTretmane(Korisnik klijent) {
		LocalDateTime sada = LocalDateTime.now();
		ArrayList<ZakazanTretman> lista = new ArrayList<ZakazanTretman>();

		for(ZakazanTretman zakazan: sviZakazaniTretmani.values()) {
			if(zakazan.klijent.equals(klijent) && zakazan.datum_i_vreme.isAfter(sada)) {
				lista.add(zakazan);
			}
		}
		return lista;
	}
	public static ArrayList<ZakazanTretman> prikaziBuduceTretmane() {
		LocalDateTime sada = LocalDateTime.now();
		ArrayList<ZakazanTretman> lista = new ArrayList<ZakazanTretman>();

		for(ZakazanTretman zakazan: sviZakazaniTretmani.values()) {
			if(zakazan.datum_i_vreme.isAfter(sada)) {
				lista.add(zakazan);
			}
		}
		return lista;
	}
	public static ArrayList<ZakazanTretman> sviZaduzeni(Korisnik k) {
		ArrayList<ZakazanTretman> lista = new ArrayList<ZakazanTretman>();
		LocalDateTime sada = LocalDateTime.now();
		for(ZakazanTretman zakazan: sviZakazaniTretmani.values()) {
			if(zakazan.kozmeticar.equals(k) && zakazan.datum_i_vreme.isAfter(sada)) {
				lista.add(zakazan);
			}
		}
		return lista;
	}

	
	
	public static void zakazi(Korisnik korisnik) {
		ArrayList<String> opcije = new ArrayList<String>();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (VrstaTretmana vrsta: Tretmani.sveVrsteTretmana.values()) {
			opcije.add(vrsta.ispisVrsteTretmana());
			ids.add(vrsta.idGetter());
		}
		VrstaTretmana vrsta=null;
		String[] options = opcije.toArray(new String[0]);

		JComboBox<String> comboBox = new JComboBox<>(options);

		int choice = JOptionPane.showOptionDialog(
				null,
				comboBox,
				"Izaberite vrstu tretmana",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				options[0]
		);
		if (choice >= 0) {
			int id = ids.get(comboBox.getSelectedIndex());
			vrsta = sveVrsteTretmana.get(id);
			System.out.println("Selected option: " + vrsta);
		} else {
			System.out.println("User cancelled.");
			return;
		}

		ArrayList<Kozmeticar> obuceni = obuceniKozmeticari(vrsta);
		opcije = new ArrayList<String>();
		ids = new ArrayList<Integer>();
		opcije.add("Svejedno mi je");
		ids.add(0);
		for (Kozmeticar k: obuceni) {
			opcije.add(k.getIme() + " " + k.getPrezime());
			ids.add(k.idGetter());
		}

		Kozmeticar kozmeticar=null;
		options = opcije.toArray(new String[0]);

		comboBox = new JComboBox<>(options);

		int brojKozmeticara = JOptionPane.showOptionDialog(
				null,
				comboBox,
				"Izaberite vrstu tretmana",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				options[0]
		);
		if (brojKozmeticara >= 0) {
			int id = ids.get(comboBox.getSelectedIndex());

			if(id == 0) {
				Random random = new Random();
				id = random.nextInt(obuceni.size());
				kozmeticar = obuceni.get(id);
				System.out.println("Selected option: " + kozmeticar);
			}
			else {
				kozmeticar = Korisnici.sviKozmeticari.get(id);
				System.out.println("Selected option: " + kozmeticar);
			}
		} else {
			System.out.println("User cancelled.");
			return;
		}

		UtilDateModel dateModel = new UtilDateModel();
		Properties properties = new Properties();

		JDateComponentFactory fabrika = new JDateComponentFactory();
		JDatePicker datePicker = fabrika.createJDatePicker();

		Object[] components = { "Select Date:", datePicker };
		Object[] optionsDate = { "OK", "Cancel" };
		LocalDate datum = null;
		while (true) {
			// Show the option dialog with the components and options
			int option = JOptionPane.showOptionDialog(null, components, "Date Picker", JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, optionsDate, optionsDate[0]);
			if (option == JOptionPane.OK_OPTION) {
				GregorianCalendar selectedDate = (GregorianCalendar) datePicker.getModel().getValue();
				datum = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				System.out.println("Selected date: " + datum);
				if(datum.isEqual(LocalDate.now()) || datum.isAfter(LocalDate.now())){
					break;
				}
				else{
					JOptionPane.showMessageDialog(null, "Ne mozete odabrati datum u proslosti", "Zakazivanje neuspesno", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				System.out.println("User cancelled.");
				return;
			}
		}


		ArrayList<LocalTime> termini = slobodniTermini(datum, kozmeticar, vrsta);
		if(termini.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nema slobodnih termina za odabranog kozmeticara tog datuma", "Zakazivanje neuspesno", JOptionPane.ERROR_MESSAGE);
			return;
		}
		opcije = new ArrayList<String>();
		for (LocalTime vreme: termini) {
			opcije.add(vreme.toString());
		}
		options = opcije.toArray(new String[0]);

		comboBox = new JComboBox<>(options);

		choice = JOptionPane.showOptionDialog(
				null,
				comboBox,
				"Izaberite vrstu tretmana",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				options[0]
		);
		LocalTime vreme = LocalTime.now();
		if (choice >= 0) {
			int id = comboBox.getSelectedIndex();
			vreme = termini.get(id);
			System.out.println("Selected option: " + vreme);
		} else {
			System.out.println("User cancelled.");
			return;
		}

		LocalDateTime datum_i_vreme = datum.atTime(vreme);
		
		dodajZakazanTretman(new ZakazanTretman(vrsta, datum_i_vreme, kozmeticar, korisnik));
		JOptionPane.showMessageDialog(null, "Zakazivanje uspesno", "Uspeh", JOptionPane.INFORMATION_MESSAGE);




	}
	
	public static void zakaziOnline() {
		zakazi(Korisnici.trenutniKorisnik);
	}

	public static void izvrsi(ZakazanTretman tretman) {
		tretman.setStatus(statusTretmana.IZVRSEN);
		tretman.klijent.dodajPare(tretman.cena);
	}
	public static void otkazi(ZakazanTretman tretman) {
		if(Korisnici.trenutniKorisnik.getTip().equals(Korisnik.tipKorisnika.KLIJENT))
		{
			tretman.setStatus(statusTretmana.OTKAZAO_KLIJENT);
		}
		else {
			tretman.setStatus(statusTretmana.OTKAZAO_SALON);
		}
	}
	public static void nijePojavio(ZakazanTretman tretman) {
		tretman.setStatus(statusTretmana.NIJE_SE_POJAVIO);
		tretman.klijent.dodajPare(tretman.cena);

	}
	
	public static Kozmeticar najvisePrihoda(LocalDate pocetak, LocalDate kraj) {
		int maxprihod=0;
		Kozmeticar maxKozmeticar = new Kozmeticar();
		for(Kozmeticar k: Korisnici.sviKozmeticari.values()) {
			int prihod = prihodKozmeticara(k,pocetak,kraj);
			if(prihod>maxprihod) {
				maxprihod = prihod;
				maxKozmeticar = k;
			}
		}
		return maxKozmeticar;
	}
	
	public static int prihodKozmeticara(Kozmeticar kozmeticar, LocalDate pocetak, LocalDate kraj) {
		int prihod = 0;
		while(pocetak.isBefore(kraj) || pocetak.equals(kraj)) {
			if(sviZakazaniDatum.get(pocetak)!=null) {
				for(ZakazanTretman zakazan: sviZakazaniDatum.get(pocetak)) {
					if(zakazan.kozmeticar.equals(kozmeticar)) {
						prihod += zakazan.getCena();
					}
				}	
			}
			pocetak = pocetak.plusDays(1);
		}
		return prihod;
	}
	
	public static Kozmeticar najviseTretmana(LocalDate pocetak, LocalDate kraj) {
		int maxbroj=0;
		Kozmeticar maxKozmeticar = new Kozmeticar();
		for(Kozmeticar k: Korisnici.sviKozmeticari.values()) {
			int broj = brojTretmana(k,pocetak,kraj);
			if(broj>maxbroj) {
				maxbroj = broj;
				maxKozmeticar = k;
			}
		}
		return maxKozmeticar;
	}
	
	public static int brojTretmana(Kozmeticar kozmeticar, LocalDate pocetak, LocalDate kraj) {
		int broj = 0;
		while(pocetak.isBefore(kraj) || pocetak.equals(kraj)) {
			if(sviZakazaniDatum.get(pocetak)!=null) {
				for(ZakazanTretman zakazan: sviZakazaniDatum.get(pocetak)) {
					if(zakazan.kozmeticar.equals(kozmeticar) && zakazan.getStatus().equals(statusTretmana.IZVRSEN)) {
						broj += 1;
					}
				}	
			}
			pocetak = pocetak.plusDays(1);

		}
		return broj;
	}

	public static ArrayList<ZakazanTretman> samoZakazani(Korisnik k){
		ArrayList<ZakazanTretman> tretmani = new ArrayList<ZakazanTretman>();
		for (ZakazanTretman z: sviZakazaniTretmani.values()){
			if (z.getStatus()==statusTretmana.ZAKAZAN && z.getKlijent().equals(k)){
				tretmani.add(z);
			}
		}
		return tretmani;
	}

	public static VrstaTretmana nadjiVrstu(String naziv){
		for (VrstaTretmana v:sveVrsteTretmana.values()){
			if (v.getNazivVrsteTretmana().equals(naziv)){
				return v;
			}
		}
		return null;
	}
	public static TipTretmana nadjiTip(String naziv){
		for (TipTretmana t:sviTipoviTretmana.values()){
			if (t.getNazivTipaTretmana().equals(naziv)){
				return t;
			}
		}
		return null;
	}

	public static int brojStatus(statusTretmana status, LocalDate pocetak, LocalDate kraj) {
		int broj = 0;
		while(pocetak.isBefore(kraj) || pocetak.equals(kraj)) {
			if(sviZakazaniDatum.get(pocetak)!=null) {
				for(ZakazanTretman zakazan: sviZakazaniDatum.get(pocetak)) {
					if(zakazan.getStatus().equals(status)) {
						broj += 1;
					}
				}
			}
			pocetak = pocetak.plusDays(1);

		}
		return broj;
	}

	public static Object brojZakazanih(VrstaTretmana vrsta, LocalDate pocetak, LocalDate kraj) {
		int broj = 0;
		while(pocetak.isBefore(kraj) || pocetak.equals(kraj)) {
			if(sviZakazaniDatum.get(pocetak)!=null) {
				for(ZakazanTretman zakazan: sviZakazaniDatum.get(pocetak)) {
					if(zakazan.vrsta.equals(vrsta)) {
						broj += 1;
					}
				}
			}
			pocetak = pocetak.plusDays(1);

		}
		return broj;
	}

	public static Object prihodZakazanih(VrstaTretmana vrsta, LocalDate pocetak, LocalDate kraj) {
		int prihod = 0;
		while(pocetak.isBefore(kraj) || pocetak.equals(kraj)) {
			if(sviZakazaniDatum.get(pocetak)!=null) {
				for(ZakazanTretman zakazan: sviZakazaniDatum.get(pocetak)) {
					if(zakazan.vrsta.equals(vrsta)) {
						prihod += zakazan.getCena();
					}
				}
			}
			pocetak = pocetak.plusDays(1);
		}
		return prihod;
	}
}

