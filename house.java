import java.util.*;

/**
 * Clasa house contine un ArrayList de camere si o functie add_room care adauga
 * camera in array.
 */
public class house {
	ArrayList<rooms> room = new ArrayList<rooms>();
	
	public house() {}
	
	void add_room(String room_name, String device_id, Double surface) {
		room.add(new rooms(room_name, device_id, surface, 0));
	}
}
