package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import korisnici.Korisnici;
import korisnici.Korisnik;
import korisnici.Kozmeticar;
import korisnici.Zaposleni;
import tretmani.TipTretmana;
import tretmani.Tretmani;
import tretmani.VrstaTretmana;
import tretmani.ZakazanTretman;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testTretmani {
	private static TipTretmana testT1;
	private static VrstaTretmana testV1;
	private static ZakazanTretman testZ1;
	private static Kozmeticar testK1;
	private static Korisnik testK2;
	private static HashMap<Integer,TipTretmana> sviTestTipovi;
	private static HashMap<Integer,VrstaTretmana> sveTestVrste;
	private static HashMap<Integer,ZakazanTretman> sviTestZakazani;

	@AfterClass
	public static void cleanup() throws Exception{
		Korisnici.sviKorisnici.clear();
		Tretmani.sveVrsteTretmana.clear();
		Tretmani.sviTipoviTretmana.clear();
		Tretmani.sviZakazaniDatum.clear();
		Tretmani.sviZakazaniTretmani.clear();
	}
	
	@BeforeClass
	public static void setup() throws Exception{
		sviTestTipovi = new HashMap<>();
		sveTestVrste = new HashMap<>();
		sviTestZakazani = new HashMap<>();
		testK1 = new Kozmeticar("Marko","Markovic", "musko", "0320320", "BlaBla 22", "mare", "mare123",Zaposleni.nivoSpreme.FAKULTET, 10);
		Korisnici.dodajKorisnika(testK1);
		testK2 = new Korisnik("Zika","Zikic", "musko", "0320320", "BlaBla 22", "zika", "zika123");
		Korisnici.dodajKorisnika(testK2);
		testT1 = new TipTretmana("masaza");
		testV1 = new VrstaTretmana("test masaza",testT1,1000,Duration.ofMinutes(60));
		testZ1 = new ZakazanTretman(testV1,LocalDateTime.of(2023, 1, 1, 16, 0),testK1,testK2);
		sviTestTipovi.put(testT1.idGetter(), testT1);
		sveTestVrste.put(testV1.idGetter(), testV1);
		sviTestZakazani.put(testZ1.idGetter(), testZ1);
	}
	@Test
	public void testDodajTip() {
		Tretmani.dodajTipTretmana(testT1);
		Assert.assertTrue(Tretmani.sviTipoviTretmana.equals(sviTestTipovi));
	}
	@Test
	public void testCitajTip() {
		Tretmani.dodajTipTretmana(testT1);
		Assert.assertTrue(Tretmani.sviTipoviTretmana.get(testT1.idGetter()).equals(testT1));
	}
	@Test
	public void testIzmeniTip() {
		Tretmani.dodajTipTretmana(testT1);
		Tretmani.sviTipoviTretmana.get(testT1.idGetter()).editTipTretmana("sisanje");;
		Assert.assertTrue(Tretmani.sviTipoviTretmana.get(testT1.idGetter()).equals(new TipTretmana("sisanje")));
	}
	@Test
	public void testObrisiTip() {
		Tretmani.dodajTipTretmana(testT1);
		Tretmani.obrisiTip(testT1);
		Assert.assertTrue(Tretmani.sviTipoviTretmana.isEmpty());
	}
	
	@Test
	public void testDodajVrstu() {
		Tretmani.dodajVrstuTretmana(testV1);
		Assert.assertTrue(Tretmani.sveVrsteTretmana.equals(sveTestVrste));
	}
	@Test
	public void testCitajVrstu() {
		Tretmani.dodajVrstuTretmana(testV1);
		Assert.assertTrue(Tretmani.sveVrsteTretmana.get(testV1.idGetter()).equals(testV1));
	}
	@Test
	public void testIzmeniVrstu() {
		Tretmani.dodajVrstuTretmana(testV1);
		Tretmani.sveVrsteTretmana.get(testT1.idGetter()).editVrstaTretmana("test masazica",testT1,1000,Duration.ofMinutes(60));;
		Assert.assertTrue(Tretmani.sveVrsteTretmana.get(testT1.idGetter()).equals(new VrstaTretmana("test masazica",testT1,1000,Duration.ofMinutes(60))));
	}
	@Test
	public void testObrisiVrstu() {
		Tretmani.dodajVrstuTretmana(testV1);
		Tretmani.obrisiVrstu(testV1);
		Assert.assertTrue(Tretmani.sveVrsteTretmana.isEmpty());
	}
	
	@Test
	public void testDodajZakazan() {
		Tretmani.dodajZakazanTretman(testZ1);
		Assert.assertTrue(Tretmani.sviZakazaniTretmani.equals(sviTestZakazani));
	}
	@Test
	public void testCitajZakazan() {
		Tretmani.dodajZakazanTretman(testZ1);
		Assert.assertTrue(Tretmani.sviZakazaniTretmani.get(testZ1.idGetter()).equals(testZ1));
	}
	@Test
	public void testIzmeniZakazan() {
		Tretmani.dodajZakazanTretman(testZ1);
		Tretmani.sviZakazaniTretmani.get(testZ1.idGetter()).editZakazanTretman(testV1,LocalDateTime.of(2023, 1, 2, 16, 0),testK1,testK2);
		Assert.assertTrue(Tretmani.sviZakazaniTretmani.get(testZ1.idGetter()).equals(new ZakazanTretman(testV1,LocalDateTime.of(2023, 1, 2, 16, 0),testK1,testK2)));
	}
	@Test
	public void testObrisiZakazan() {
		Tretmani.dodajZakazanTretman(testZ1);
		Tretmani.obrisiZakazan(testZ1);
		Assert.assertTrue(Tretmani.sviZakazaniTretmani.isEmpty());
	}
	
	@Test
	public void testZFajlTretmani() throws IOException {
		File tempFile1 = File.createTempFile("test1", ".txt");
		File tempFile2 = File.createTempFile("test2", ".txt");
		File tempFile3 = File.createTempFile("test3", ".txt");

        String filePath1 = tempFile1.getAbsolutePath();
        String filePath2 = tempFile2.getAbsolutePath();
        String filePath3 = tempFile3.getAbsolutePath();

		Tretmani.sacuvajTipoveTretmana(filePath1);
		Tretmani.sacuvajVrsteTretmana(filePath2);
		Tretmani.sacuvajZakazaneTretmane(filePath3);
		sviTestTipovi = Tretmani.sviTipoviTretmana;
		sveTestVrste = Tretmani.sveVrsteTretmana;
		sviTestZakazani = Tretmani.sviZakazaniTretmani;
        
		Tretmani.sveVrsteTretmana.clear();
		Tretmani.sviTipoviTretmana.clear();
		Tretmani.sviZakazaniDatum.clear();
		Tretmani.sviZakazaniTretmani.clear();
		
		Tretmani.ucitajTipoveTretmana(filePath1);
		Tretmani.ucitajVrsteTretmana(filePath2);
		Tretmani.ucitajZakazaneTretmane(filePath3);
		
		
		Assert.assertTrue(Tretmani.sveVrsteTretmana.equals(sveTestVrste) && Tretmani.sviTipoviTretmana.equals(sviTestTipovi) && Tretmani.sviZakazaniTretmani.equals(sveTestVrste));
	}
	
}
