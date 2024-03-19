package main;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import gui.LoginFrame;
import korisnici.*;
import korisnici.Korisnik.tipKorisnika;
import korisnici.Zaposleni.nivoSpreme;
import tretmani.*;
import salon.*;
import vreme.Vreme;


public class Main {
    private static void cleanup(){
        Saloni.sacuvajSalonPodatke(Saloni.trenutniSalon);
    }
    public static void main(String[] args) {
        Saloni.ucitajSalone(Salon.odrediPutanjuFajla("svi_saloni.csv"));
        Saloni.inicijalizujSalon(Saloni.sviSaloni.get(1));

        Runtime.getRuntime().addShutdownHook(new Thread(Main::cleanup));

        LoginFrame glavni = new LoginFrame();

    }
}













