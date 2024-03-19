package vreme;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vreme {
	public static DateTimeFormatter sema = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH");

	public static String vreme_u_string(LocalDateTime vreme) {

		return vreme.format(sema);
	}
	
	public static LocalDateTime string_u_vreme(String sVreme) {

		return LocalDateTime.parse(sVreme, sema);
	}
}
