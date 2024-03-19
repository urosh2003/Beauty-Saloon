package tretmani;

public class TipTretmana {
	String nazivTipaTretmana;
	int id;
	public static int idPoslednjeg;
	
	public TipTretmana(String naziv) {
		this.nazivTipaTretmana = naziv;
		this.id = ++idPoslednjeg;
	}
	public TipTretmana(String naziv, int id) {
		this.nazivTipaTretmana = naziv;
		this.id=id;
	}
	public TipTretmana() {
		
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
		str.append(this.nazivTipaTretmana);
		str.append("|");
		str.append(this.id);
		
		return str.toString();
	}
	
	public boolean equals(TipTretmana tip){
		if ((tip.nazivTipaTretmana).equals(this.nazivTipaTretmana)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setNazivTipaTretmana(String naziv) {
		this.nazivTipaTretmana = naziv;
	}
	
	public void editTipTretmana(String naziv) {
		this.setNazivTipaTretmana(naziv);
	}
	
	public String getNazivTipaTretmana(){
		return this.nazivTipaTretmana;
	}
	
	public void ispisTipaTretmana() {
		System.out.println(this.nazivTipaTretmana);
	}
}
