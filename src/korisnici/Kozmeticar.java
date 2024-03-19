package korisnici;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import tretmani.TipTretmana;
import tretmani.Tretmani;
import tretmani.ZakazanTretman;

public class Kozmeticar extends Zaposleni{
	private HashSet<TipTretmana> obucen = new HashSet<TipTretmana>(); 
	
	public Kozmeticar(String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme,
			String lozinka, nivoSpreme nivo, int godineStaza) {
		super(ime, prezime, pol, telefon, adresa, korisnickoIme, lozinka, nivo, godineStaza, tipKorisnika.KOZMETICAR);
		}
	public Kozmeticar(int id,String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme,
			String lozinka, nivoSpreme nivo, int godineStaza, int bonus) {
		super(id,ime, prezime, pol, telefon, adresa, korisnickoIme, lozinka, nivo, godineStaza, bonus, tipKorisnika.KOZMETICAR);
		}
	public Kozmeticar(int id,String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme,
			String lozinka, nivoSpreme nivo, int godineStaza, int bonus, String obucen) {
		super(id,ime, prezime, pol, telefon, adresa, korisnickoIme, lozinka, nivo, godineStaza, bonus, tipKorisnika.KOZMETICAR);
		String[] stavke = obucen.split(",");
		for (String staka: stavke) {
			this.dodajObuku(Tretmani.sviTipoviTretmana.get(Integer.parseInt(staka)));
		}
	}
	
	public Kozmeticar() {}
	
	public void dodajObuku(TipTretmana tip) {
		this.obucen.add(tip);
	}
	public void skloniObuku(TipTretmana tip) {
		this.obucen.remove(tip);
	}
	
	public boolean obucenZa(TipTretmana tip) {
		for (TipTretmana t: obucen) {
			if(tip.equals(t)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(this.tip);
		str.append("|");
		str.append(this.id);
		str.append("|");
		str.append(this.ime);
		str.append("|");
		str.append(this.prezime);
		str.append("|");
		str.append(this.pol);		
		str.append("|");
		str.append(this.telefon);	
		str.append("|");
		str.append(this.adresa);
		str.append("|");
		str.append(this.korisnickoIme);
		str.append("|");
		str.append(this.lozinka);
		str.append("|");
		str.append(this.sprema);
		str.append("|");
		str.append(this.godineStaza);
		str.append("|");
		str.append(this.bonus);
		str.append("|");
		for(TipTretmana tip: this.obucen) {
			str.append(tip.idGetter() +",");
		}
		
		
		return str.toString();
	}
	@Override
	public String ispisiKorisnika() {
		StringBuilder str = new StringBuilder();
		for(TipTretmana tip: obucen) {
			str.append(tip.getNazivTipaTretmana());
			str.append(", ");
		}
		String obucenIpsis = str.toString();
		return("Kozmeticar " + this.ime + " " + this.prezime + ", obucen za: " + obucenIpsis + "bonus: " + this.bonus);

	}
	public String getObucen() {
		StringBuilder str = new StringBuilder();
		for(TipTretmana tip: obucen) {
			str.append(tip.getNazivTipaTretmana());
			str.append(", ");
		}
		return str.toString();
	}
}
