package tretmani;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import korisnici.Korisnici;
import korisnici.Korisnik;
import korisnici.Kozmeticar;
import vreme.Vreme;


public class ZakazanTretman{
	public enum statusTretmana{
		ZAKAZAN,
		IZVRSEN,
		OTKAZAO_KLIJENT,
		OTKAZAO_SALON,
		NIJE_SE_POJAVIO
	}
	
	int id;
	public static int idPoslednjeg;
	VrstaTretmana vrsta;
	LocalDateTime datum_i_vreme;
	Kozmeticar kozmeticar;
	statusTretmana status;
	int cena;
	Korisnik klijent;
	boolean obrisan=false;

	
	public ZakazanTretman(VrstaTretmana vrsta, LocalDateTime vreme,
			Kozmeticar kozmeticar, statusTretmana status, Korisnik klijent){
		this.id = ++idPoslednjeg;
		this.vrsta=vrsta;
		this.datum_i_vreme=vreme;
		this.kozmeticar=kozmeticar;
		this.status=status;
		this.klijent=klijent;
		this.cena=vrsta.cena;
	}
	
	public ZakazanTretman(VrstaTretmana vrsta, LocalDateTime vreme,
			Kozmeticar kozmeticar, Korisnik klijent){
		this.id = ++idPoslednjeg;
		this.vrsta=vrsta;
		this.datum_i_vreme=vreme;
		this.kozmeticar=kozmeticar;
		this.status= statusTretmana.ZAKAZAN;
		this.klijent=klijent;
		this.cena=vrsta.cena;
		if(klijent.getLoyalty()) {
			this.cena -= this.cena/10;
		}
	}

	public ZakazanTretman(int id, VrstaTretmana vrsta, LocalDateTime vreme,
			Kozmeticar kozmeticar, statusTretmana status, int cena, Korisnik klijent){
		this.id = id;
		this.vrsta=vrsta;
		this.datum_i_vreme=vreme;
		this.kozmeticar=kozmeticar;
		this.status=status;
		this.klijent=klijent;
		this.cena=cena;
	}
	public ZakazanTretman(int id, int idVrste, String sVreme,
			int idKozmeticara, String status, int cena, int idKlijenta){
		this.id = id;
		this.vrsta= Tretmani.sveVrsteTretmana.get(idVrste);
		this.datum_i_vreme=Vreme.string_u_vreme(sVreme);
		this.kozmeticar= Korisnici.sviKozmeticari.get(idKozmeticara);
		this.status=statusTretmana.valueOf(status);
		this.cena=cena;
		this.klijent= Korisnici.sviKorisnici.get(idKlijenta);

	}

	public ZakazanTretman() {

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
		str.append(this.vrsta.idGetter());
		str.append("|");
		str.append(Vreme.vreme_u_string(datum_i_vreme));
		str.append("|");
		str.append(this.kozmeticar.idGetter());
		str.append("|");
		str.append(this.status);
		str.append("|");
		str.append(this.cena);
		str.append("|");
		str.append(this.klijent.idGetter());

		
		return str.toString();
	}
	public boolean equals(ZakazanTretman zakazan){
		if ((zakazan.vrsta).equals(this.vrsta) && (zakazan.datum_i_vreme).isEqual(this.datum_i_vreme) 
				&& (zakazan.kozmeticar).equals(this.kozmeticar) ){
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setVrsta(VrstaTretmana vrsta) {
	    this.vrsta = vrsta;
	}

	public String getNaziv(){
		return this.vrsta.getNazivVrsteTretmana();
	}
	public LocalDateTime getDatum_i_vreme(){
		return datum_i_vreme;
	}
	public VrstaTretmana getVrsta() {
	    return this.vrsta;
	}
	
	public int getCena() {
	    return this.cena;
	}

	public void setDatum_i_vreme(LocalDateTime datum_i_vreme) {
	    this.datum_i_vreme = datum_i_vreme;
	}

	public void setKozmeticar(Kozmeticar kozmeticar) {
	    this.kozmeticar = kozmeticar;
	}
	public Kozmeticar getKozmeticar() {
	    return this.kozmeticar;
	}
	public void setStatus(statusTretmana status) {
	    this.status = status;
	}
	public statusTretmana getStatus(){
	    return this.status;
	}
	private void setKlijent(Korisnik klijent) {
		this.klijent=klijent;
	}
	public Korisnik getKlijent() {
		return this.klijent;
	}
	
	
	public void editZakazanTretman(VrstaTretmana vrsta, LocalDateTime vreme,
			Kozmeticar kozmeticar, statusTretmana status, Korisnik klijent){
		this.setVrsta(vrsta);
		this.setDatum_i_vreme(vreme);
		this.setKozmeticar(kozmeticar);
		this.setStatus(status);
		this.setKlijent(klijent);
		this.cena=vrsta.cena;
	}
	public void editZakazanTretman(VrstaTretmana vrsta, LocalDateTime vreme,
			Kozmeticar kozmeticar, Korisnik klijent){
		this.setVrsta(vrsta);
		this.setDatum_i_vreme(vreme);
		this.setKozmeticar(kozmeticar);
		this.setKlijent(klijent);
		this.cena=vrsta.cena;
	}
	
	public void ispisZakazanogTretmana() {
		System.out.println("Klijent " + this.klijent.getIme() + " " + this.klijent.getPrezime() + ", " +
	this.status + " " + this.vrsta.nazivVrsteTretmana + " kod " + this.kozmeticar.getIme() + " "+ this.kozmeticar.getPrezime() 
	+ ", datum i vreme: " + this.datum_i_vreme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:00")) + ", cena: " + this.cena);
	}
	
}
