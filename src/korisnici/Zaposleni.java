package korisnici;

public class Zaposleni extends Korisnik{
	public enum nivoSpreme{
		OSNOVNA_SKOLA,
		SREDNJA_SKOLA,
		FAKULTET,
		MASTER,
		DOKTOR;
		
		static nivoSpreme sprema(String a) {
			switch(a) {
				case("OSNOVNA_SKOLA"): {
					return nivoSpreme.OSNOVNA_SKOLA;
				}
				case("SREDNJA_SKOLA"): {
					return nivoSpreme.SREDNJA_SKOLA;
				}
				case("FAKULTET"): {
					return nivoSpreme.FAKULTET;
				}
				case("MASTER"): {
					return nivoSpreme.MASTER;
				}
				case("DOKTOR"): {
					return nivoSpreme.DOKTOR;
				}
			}
			return null;
		}
	}
	protected nivoSpreme sprema;
	protected int godineStaza;
	protected int bonus = 0;
	int plata;
	
	public Zaposleni(String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme,
			String lozinka, nivoSpreme nivo, int godineStaza, tipKorisnika tip) {
		super(ime, prezime, pol, telefon, adresa, korisnickoIme, lozinka);
		this.tip = tip;
		this.sprema= nivo;
		this.godineStaza=godineStaza;
	}
	public Zaposleni(int id,String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme,
			String lozinka, nivoSpreme nivo, int godineStaza, int bonus, tipKorisnika tip) {
		super(id, ime, prezime, pol, telefon, adresa, korisnickoIme, lozinka);
		this.bonus = bonus;
		this.tip = tip;
		this.sprema= nivo;
		this.godineStaza=godineStaza;
	}
	public Zaposleni() {}
	
	public int odrediPlatu() {
		int plata = 80000;
		plata += godineStaza*500;
		switch(this.sprema) {
		case DOKTOR:{
			plata+=5000;
		}
		case MASTER:{
			plata+=5000;

		}
		case FAKULTET:{
			plata+=5000;

		}
		case SREDNJA_SKOLA:{
			plata+=5000;

		}
		case OSNOVNA_SKOLA:{
			plata+=5000;
		}
		}
		return plata + this.bonus;
	}
	
	public void setBonus(int bonus) {
		this.bonus=bonus;
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

		
		return str.toString();
	}
	
	public void setGodineStaza(int godine) {
	    this.godineStaza = godine;
	}
	public void setSprema(nivoSpreme nivo) {
	    this.sprema = nivo;
	}

	public int getGodineStaza() {
		return this.godineStaza;
	}
	public nivoSpreme getSprema() {
		return this.sprema;
	}
	public int getBonus(){return this.bonus;}
	
	public void editZaposleni(String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme, String lozinka,int godine,nivoSpreme nivo ){
		this.setIme(ime);
		this.setPrezime(prezime);
		this.setPol(pol);
		this.setTelefon(telefon);
		this.setAdresa(adresa);
		this.setKorisnickoIme(korisnickoIme);
		this.setLozinka(lozinka);
		this.setGodineStaza(godine);
		this.setSprema(nivo);
	}
	
	@Override
	public String ispisiKorisnika() {
		return (this.tip + " " + this.ime + " " + this.prezime);
	}
}
