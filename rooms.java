import java.util.*;

/**
 * Clasa rooms reprezinta o camera si are 2 ArrayList-uri, unul cu umiditati si 
 * unul cu temperaturi, Aceasta clasa are si un constructor care genereaza
 * un element de tip camera.
 */
public class rooms {
	String room_name;
	String device_id;
	Double surface;
	int poz_max;
	ArrayList<tempreture> interval = new ArrayList<tempreture>(24);
	ArrayList<humidity> interval_hum = new ArrayList<humidity>(24);
	
	rooms(String room_name, String device_id, Double surface, int poz_max){
		this.room_name = room_name;
		this.device_id = device_id;
		this.surface = surface;
		this.poz_max = poz_max;
		
		for(int i = 0; i < 24; i++) {
			interval.add(new tempreture());
			interval_hum.add(new humidity());
		}
	}
}
