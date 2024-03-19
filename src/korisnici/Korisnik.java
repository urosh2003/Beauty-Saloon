package korisnici;

import salon.Saloni;

public class Korisnik{
	public enum tipKorisnika{
		MENADZER,
		KOZMETICAR,
		RECEPCIONER,
		KLIJENT
	}
	
	protected String ime;
	protected String prezime;
	protected String pol;
	protected String telefon;
	protected String adresa;
	protected String korisnickoIme;
	protected String lozinka;
	protected boolean loyaltyCard;
	protected int potrosenePare;
	protected tipKorisnika tip;
	protected int id;
	public static int idPoslednjeg;
	
	public Korisnik(String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme, String lozinka){
		this.id = ++idPoslednjeg;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.telefon = telefon;
		this.adresa = adresa;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.loyaltyCard = false;
		this.potrosenePare = 0;
		this.tip = tipKorisnika.KLIJENT;
	}
	public Korisnik(int id, String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme, String lozinka, boolean loyaltyCard, int potrosenePare){
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.telefon = telefon;
		this.adresa = adresa;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.loyaltyCard = loyaltyCard;
		this.potrosenePare = potrosenePare;
		this.tip = tipKorisnika.KLIJENT;
	}
	public Korisnik(int id, String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme, String lozinka){
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.telefon = telefon;
		this.adresa = adresa;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.tip = tipKorisnika.KLIJENT;
	}
	public Korisnik(){}
	
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
		str.append(this.loyaltyCard);
		str.append("|");
		str.append(this.potrosenePare);
		
		return str.toString();
	}
	
	public int idGetter() {
		return this.id;
	}
	public String korisnickoImeGetter() {
		return this.korisnickoIme;
	}
	
	public void dodajPare(int suma) {
		this.potrosenePare += suma;
		this.checkLoyalty();
	}
	
	public static void idPoslednjegSetter(int id) {
		idPoslednjeg = id;
	}
	
	public boolean identifyer(String username) {
		if (username.equals(this.korisnickoIme)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean equals(Korisnik k){
		if (k.korisnickoImeGetter().equals(this.korisnickoIme)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setIme(String ime) {
	    this.ime = ime;
	}
	public String getIme() {
	    return this.ime;
	}

	public int getPare() {
		return this.potrosenePare;
	}


	public void setPrezime(String prezime) {
	    this.prezime = prezime;
	}
	public String getPrezime() {
	    return this.prezime;
	}

	public void setPol(String pol) {
	    this.pol = pol;
	}
	public String getPol() {
		return this.pol;
	}


	public void setTelefon(String telefon) {
	    this.telefon = telefon;
	}
	public String getTelefon() {
		return this.telefon;
	}


	public void setAdresa(String adresa) {
	    this.adresa = adresa;
	}
	public String getAdresa() {
		return this.adresa;
	}


	public void setKorisnickoIme(String korisnickoIme) {
	    this.korisnickoIme = korisnickoIme;
	}

	public void setLozinka(String lozinka) {
	    this.lozinka = lozinka;
	}
	public String getLozinka() {
		return this.lozinka;
	}


	public void setTip(tipKorisnika tip) {
		this.tip=tip;
	}
	public tipKorisnika getTip() {
		return this.tip;
	}
	
	public void setLoyalty(boolean valid) {
		this.loyaltyCard = valid;
	}
	public boolean getLoyalty(){
		return this.loyaltyCard;
	}
	
	public void checkLoyalty() {
		if (this.potrosenePare >= Saloni.trenutniSalon.getLoyaltyUslov() && Saloni.trenutniSalon.getLoyaltyUslov()>=0) {
			this.loyaltyCard=true;
		}
	}
	
	public void editKorisnik(String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme, String lozinka){
		this.setIme(ime);
		this.setPrezime(prezime);
		this.setPol(pol);
		this.setTelefon(telefon);
		this.setAdresa(adresa);
		this.setKorisnickoIme(korisnickoIme);
		this.setLozinka(lozinka);
	}
	
	public String ispisiKorisnika() {
		return (this.ime + " " + this.prezime + ", Prikupljeno bodova: " + this.potrosenePare + ", Loyalty card: " + this.loyaltyCard);
	}
}









