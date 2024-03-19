package salon;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import korisnici.Korisnici;
import korisnici.Korisnik;
import korisnici.Korisnik.tipKorisnika;
import korisnici.Kozmeticar;
import korisnici.Zaposleni;
import korisnici.Zaposleni.nivoSpreme;
import tretmani.TipTretmana;
import tretmani.Tretmani;
import tretmani.ZakazanTretman;
import vreme.Vreme;

public class Salon {
	protected String naziv;
	protected int id;
	public static int idPoslednjeg;
	protected String imeFajlaKorisnika;
	protected String imeFajlaVrstaTretmana;
	protected String imeFajlaTipovaTretmana;
	protected String imeFajlaZakazanihTretmana;
	protected LocalTime otvaranje;
	protected LocalTime zatvaranje;
	protected int loyaltyUslov;
	
	public Salon(int id, String naziv, String imeFajlaKorisnika, 
			String imeFajlaTipovaTretmana, String imeFajlaVrstaTretmana, String imeFajlaZakazanihTretmana,String otvaranje,
	String zatvaranje, int loyaltyUslov) {
		this.naziv=naziv;
		this.id=id;
		this.imeFajlaKorisnika=imeFajlaKorisnika;
		this.imeFajlaTipovaTretmana=imeFajlaTipovaTretmana;
		this.imeFajlaVrstaTretmana=imeFajlaVrstaTretmana;
		this.imeFajlaZakazanihTretmana=imeFajlaZakazanihTretmana;
		this.otvaranje = LocalTime.parse(otvaranje);
		this.zatvaranje = LocalTime.parse(zatvaranje);
		this.loyaltyUslov = loyaltyUslov;
	}
	public Salon(String naziv, String imeFajlaKorisnika, 
			String imeFajlaTipovaTretmana, String imeFajlaVrstaTretmana, String imeFajlaZakazanihTretmana,LocalTime otvaranje,
			LocalTime zatvaranje) {
		this.naziv=naziv;
		this.id= ++idPoslednjeg;
		this.imeFajlaKorisnika=imeFajlaKorisnika;
		this.imeFajlaTipovaTretmana=imeFajlaTipovaTretmana;
		this.imeFajlaVrstaTretmana=imeFajlaVrstaTretmana;
		this.imeFajlaZakazanihTretmana=imeFajlaZakazanihTretmana;
		this.otvaranje=otvaranje;
		this.zatvaranje=zatvaranje;
		this.loyaltyUslov = -1;
	}
	
	public Salon() {
		
	}
	
	public int idGetter() {
		return this.id;
	}
	public static void idPoslednjegSetter(int id) {
		idPoslednjeg = id;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(this.id);
		str.append("|");
		str.append(this.naziv);
		str.append("|");
		str.append(this.imeFajlaKorisnika);
		str.append("|");
		str.append(this.imeFajlaTipovaTretmana);
		str.append("|");
		str.append(this.imeFajlaVrstaTretmana);
		str.append("|");
		str.append(this.imeFajlaZakazanihTretmana);
		str.append("|");
		str.append(this.otvaranje);
		str.append("|");
		str.append(this.zatvaranje);
		str.append("|");
		str.append(this.loyaltyUslov);

		
		return str.toString();
	}
	
	public void setNaziv(String naziv) {
	    this.naziv = naziv;
	}
	
	public void setImeFajlaKorisnika(String imeFajlaKorisnika) {
	    this.imeFajlaKorisnika = imeFajlaKorisnika;
	}

	public void setImeFajlaVrstaTretmana(String imeFajlaVrstaTretmana) {
	    this.imeFajlaVrstaTretmana = imeFajlaVrstaTretmana;
	}

	public void setImeFajlaTipovaTretmana(String imeFajlaTipovaTretmana) {
	    this.imeFajlaTipovaTretmana = imeFajlaTipovaTretmana;
	}

	public void setImeFajlaZakazanihTretmana(String imeFajlaZakazanihTretmana) {
	    this.imeFajlaZakazanihTretmana = imeFajlaZakazanihTretmana;
	}
	
	
	public static String odrediPutanjuFajla(String imeFajla) {
		String separator = System.getProperty("file.separator");
		
	    return "podaci" + separator + imeFajla;
	}

	
	public void editSalon(String naziv, String imeFajlaKorisnika, 
	String imeFajlaVrstaTretmana, String imeFajlaTipovaTretmana, String imeFajlaZakazanihTretmana,LocalTime otvaranje,
	LocalTime zatvaranje){
		this.setNaziv(naziv);
		this.setImeFajlaKorisnika(imeFajlaKorisnika);
		this.setImeFajlaVrstaTretmana(imeFajlaVrstaTretmana);
		this.setImeFajlaTipovaTretmana(imeFajlaTipovaTretmana);
		this.setImeFajlaZakazanihTretmana(imeFajlaZakazanihTretmana);
	    this.otvaranje = otvaranje;
	    this.zatvaranje = zatvaranje;
	}
	
	public void setOtvaranje(LocalTime otvaranje) {
	    this.otvaranje = otvaranje;
	}
	public LocalTime getOtvaranje() {
	    return this.otvaranje;
	}
	public void setZatvaranje(LocalTime zatvaranje) {
	    this.zatvaranje = zatvaranje;
	}
	public LocalTime getZatvaranje() {
	    return this.zatvaranje;
	}
	
	public void setLoyaltyUslov(int uslov) {
	    this.loyaltyUslov = uslov;
	    
	    for(Korisnik k: Korisnici.sviKorisnici.values()) {
	    	k.checkLoyalty();
	    }
	}
	public int getLoyaltyUslov() {
	    return this.loyaltyUslov;
	}
	
	public static void izracunajPrihode(LocalDate pocetak, LocalDate kraj) {
		int prihodiUkupno = 0;
		LocalDate temp=pocetak;
		for(TipTretmana tip: Tretmani.sviTipoviTretmana.values()) {
			int prihodi=0;
			pocetak=temp;;
			while(pocetak.isBefore(kraj) || pocetak.isEqual(kraj)) {
				ArrayList<ZakazanTretman> zakazani = Tretmani.sviZakazaniDatum.get(pocetak);
				if(zakazani!=null) {
					for(ZakazanTretman zakazan: zakazani) {
						if(zakazan.getVrsta().getTipTretmana().equals(tip)) {
							prihodi+=zakazan.getCena();
						}
					}
				}
				pocetak = pocetak.plusDays(1);
			}
			System.out.println("Prihodi od + " + tip.getNazivTipaTretmana() + ": " + prihodi);
			prihodiUkupno+=prihodi;
		}
		System.out.println("Prihodi od zakazanih tretmana za ovaj period su: " + prihodiUkupno);
	}
	public static void izracunajRashode(LocalDate pocetak, LocalDate kraj) {
		int rashodiPovrat =0;
		int rashodiPlate =0;

		while(pocetak.isBefore(kraj) || pocetak.isEqual(kraj)) {
			ArrayList<ZakazanTretman> zakazani = Tretmani.sviZakazaniDatum.get(pocetak);
			if(zakazani!=null) {
				for(ZakazanTretman zakazan: zakazani) {
				switch(zakazan.getStatus()) {
					case IZVRSEN:{
							break;
						}
					case OTKAZAO_KLIJENT:{
						rashodiPovrat+=9*zakazan.getCena()/10;
							break;
						}
					case NIJE_SE_POJAVIO:{
							break;
						}
					case OTKAZAO_SALON:{
						rashodiPovrat+=zakazan.getCena();
							break;
						}
					case ZAKAZAN:{
							break;
						}
					}
				}
			}
			if(pocetak.getDayOfMonth()==1) {
				for(Korisnik k: Korisnici.sviKorisnici.values()) {
					if(k.getTip().equals(tipKorisnika.KLIJENT)==false) {
						rashodiPlate += ((Zaposleni) k).odrediPlatu();
					}
				}
			}
			pocetak = pocetak.plusDays(1);
		}
		System.out.println("Rashodi od plata za ovaj period su: " + rashodiPlate);
		System.out.println("Rashodi od otkazivanja za ovaj period su: " + rashodiPovrat);
		int ukupno = rashodiPlate+rashodiPovrat;
		System.out.println("Ukupni rashodi: " + ukupno);

	}
	public static void prikaziBilans(LocalDate pocetak, LocalDate kraj) {
		izracunajPrihode(pocetak, kraj);
		izracunajRashode(pocetak, kraj);
	}
	
	public static void resetBonus() {
		for(Kozmeticar k: Korisnici.sviKozmeticari.values()) {
			k.setBonus(0);
		}
	}
	
	public static void dodajBonusPrihod(LocalDate pocetak) {
		LocalDate kraj = pocetak.withDayOfMonth(pocetak.lengthOfMonth());
		
		Tretmani.najvisePrihoda(pocetak, kraj).setBonus(5000);;
	}
	public static void dodajBonusPrihod(LocalDate pocetak, int bonus) {
		LocalDate kraj = pocetak.withDayOfMonth(pocetak.lengthOfMonth());
		
		Tretmani.najvisePrihoda(pocetak, kraj).setBonus(bonus);;
	}
	public static void dodajBonusBroj(LocalDate pocetak) {
		LocalDate kraj = pocetak.withDayOfMonth(pocetak.lengthOfMonth());
		
		Tretmani.najviseTretmana(pocetak, kraj).setBonus(5000);;
	}
	public static void dodajBonusBroj(LocalDate pocetak, int bonus) {
		LocalDate kraj = pocetak.withDayOfMonth(pocetak.lengthOfMonth());
		
		Tretmani.najviseTretmana(pocetak, kraj).setBonus(bonus);;
	}
	
	public static void dodajBonusPrihod(LocalDate pocetak, LocalDate kraj) {
		Tretmani.najvisePrihoda(pocetak, kraj).setBonus(5000);;
	}
	public static void dodajBonusPrihod(LocalDate pocetak, LocalDate kraj, int bonus) {
		Tretmani.najvisePrihoda(pocetak, kraj).setBonus(bonus);;
	}
	public static void dodajBonusBroj(LocalDate pocetak, LocalDate kraj) {
		Tretmani.najviseTretmana(pocetak, kraj).setBonus(5000);;
	}
	public static void dodajBonusBroj(LocalDate pocetak, LocalDate kraj, int bonus) {
		Tretmani.najviseTretmana(pocetak, kraj).setBonus(bonus);;
	}
}
