package tretmani;

import java.time.Duration;
import java.time.LocalTime;

public class VrstaTretmana{
	String nazivVrsteTretmana;
	int id;
	public static int idPoslednjeg;
	TipTretmana tip;
	int cena;
	Duration trajanje;

	
	public VrstaTretmana(String naziv, TipTretmana tip, int cena,Duration trajanje) {
		this.nazivVrsteTretmana = naziv;
		this.tip = tip;
		this.cena = cena;
		this.id = ++idPoslednjeg;
		this.trajanje=trajanje;
	}
	public VrstaTretmana(int id, String naziv, TipTretmana tip, int cena,Duration trajanje) {
		this.nazivVrsteTretmana = naziv;
		this.tip = tip;
		this.id=id;
		this.cena = cena;
		this.trajanje=trajanje;
	}
	public VrstaTretmana(int id, String naziv, int idTipa, int cena, Duration trajanje) {
		this.nazivVrsteTretmana = naziv;
		this.id=id;
		this.tip = Tretmani.sviTipoviTretmana.get(idTipa);
		this.cena = cena;
		this.trajanje=trajanje;
	}
	
	public VrstaTretmana() {
		
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
		str.append(this.nazivVrsteTretmana);
		str.append("|");
		str.append(tip.id);
		str.append("|");
		str.append(this.cena);
		str.append("|");
		str.append(this.trajanje);

		
		return str.toString();
	}
	
	public boolean equals(VrstaTretmana vrsta){
		if ((vrsta.nazivVrsteTretmana).equals(this.nazivVrsteTretmana)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setNazivVrsteTretmana(String naziv) {
		this.nazivVrsteTretmana = naziv;
	}
	public String getNazivVrsteTretmana(){
		return nazivVrsteTretmana;
	}
	public void setTipTretmana(TipTretmana tip) {
		this.tip = tip;
	}
	public TipTretmana getTipTretmana() {
		return this.tip;
	}
	public void setCena(int cena) {
		this.cena=cena;
	}
	public int getCena() {
		return this.cena;
	}

	public void setTrajanje(Duration trajanje) {
		this.trajanje=trajanje;
	}
	public Duration getTrajanje() {
		return this.trajanje;
	}
	
	public void editVrstaTretmana(String naziv, TipTretmana tip, int cena, Duration trajanje) {
		this.setNazivVrsteTretmana(naziv);
		this.setTipTretmana(tip);
		this.setCena(cena);
		this.setTrajanje(trajanje);
	}
	
	public String ispisVrsteTretmana() {
		return(this.nazivVrsteTretmana + ", tip tretmana: " + this.tip.getNazivTipaTretmana() + ", cena: " + this.cena + ", trajanje: " + this.trajanje.toMinutes() + " minuta");
	}
}
