package test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import korisnici.Korisnici;
import korisnici.Korisnik;
import korisnici.Korisnik.tipKorisnika;
import korisnici.Zaposleni.nivoSpreme;
import tretmani.Tretmani;
import tretmani.ZakazanTretman;

import java.io.IOException; 
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.io.PrintWriter;


public class testKorisnici {
	private static Korisnik testK1;
	private static Korisnik testK2;
	private static HashMap<Integer,Korisnik> sviTestKorisnici;
	
	@BeforeClass
	public static void setup() throws Exception{
		sviTestKorisnici = new HashMap<>();
		testK1 = new Korisnik("Marko","Markovic", "musko", "0320320", "BlaBla 22", "mare", "mare123");
		sviTestKorisnici.put(testK1.idGetter(), testK1);
		testK2 = new Korisnik("Zika","Zikic", "musko", "0320320", "BlaBla 22", "zika", "zika123");
		sviTestKorisnici.put(testK2.idGetter(), testK2);	
	}
	@Test
	public void testCitajKorisnike() {
		Korisnici.dodajKorisnika(testK1);
		Assert.assertTrue(Korisnici.sviKorisnici.get(testK1.idGetter()).equals(testK1));
	}
	
	@Test
	public void testDodajKorisnike() {
		Korisnici.dodajKorisnika(testK1);
		Korisnici.dodajKorisnika(testK2);
		Assert.assertTrue(Korisnici.sviKorisnici.equals(sviTestKorisnici));
	}
	
	@Test
	public void testUkloniKorisnike() {
		sviTestKorisnici.remove(testK1.idGetter());
		Korisnici.dodajKorisnika(testK1);
		Korisnici.dodajKorisnika(testK2);
		Korisnici.obrisiKorisnika(testK1);
		Assert.assertTrue(Korisnici.sviKorisnici.equals(sviTestKorisnici));
	}
	
	@Test
	public void testEditujKorisnike() {
		Korisnici.dodajKorisnika(testK1);
		Korisnici.sviKorisnici.get(testK1.idGetter()).editKorisnik("Marko","Markovic", "musko", "0320320", "BlaBla 22", "maree", "mare123");
		Assert.assertTrue(Korisnici.sviKorisnici.get(testK1.idGetter()).equals(new Korisnik("Marko","Markovic", "musko", "0320320", "BlaBla 22", "maree", "mare123")));
	}
	
	@Test
	public void testLoginUspesan() {
		Korisnici.dodajKorisnika(testK2);
		Assert.assertTrue(Korisnici.login("zika", "zika123"));
	}
	@Test
	public void testLoginNeuspesan() {
		Korisnici.dodajKorisnika(testK2);
		Assert.assertFalse(Korisnici.login("zika", "mare124"));
	}
	
	@Test
	public void testFajlKorisnici() throws IOException {
		Korisnici.dodajKorisnika(testK1);
		Korisnici.dodajKorisnika(testK2);
		File tempFile = File.createTempFile("test", ".txt");
        String filePath = tempFile.getAbsolutePath();
		Korisnici.sacuvajKorisnike(filePath);
		sviTestKorisnici = Korisnici.sviKorisnici;
		Korisnici.sviKorisnici.clear();
		Korisnici.ucitajKorisnikeIzFajla(filePath);
		Assert.assertTrue((Korisnici.sviKorisnici).equals(sviTestKorisnici));
	}

}
