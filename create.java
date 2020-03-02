import java.io.*;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
/**
 * In aceasta clasa implementez doar metoda main. Citesc fisierele de input
 * si output. Pentru fisierele de input verific daca al 3-lea parametru este
 * mai mic ca 1000 ca sa imi dau seama daca am umiditate si daca trebuie sa
 * mai citesc ceva dupa.
 * 
 * Pentru fiecare camera citesc proprietetiile acesteia. In while citesc tot
 * fisierul si iau pe rand cazurile. Pentru OBSERVE calculez pozitia pe care
 * trebuie sa adaug si adaug elementul respectiv doar daca nu exista duplicate,
 * daca care sortez ArrayList-ul. Fac acelasi lucru si pentru OBSERVEH.
 * 
 * In TEMPERATURE resetez temperatura globala.
 * 
 * In functia TRIGGER calculez media ponderata pentru temperatura si umiditate
 * si pornesc centrala dupa regulile descrise.
 * 
 * In functia LIST afisez pentru fiecare camera temperaturile din intervalul
 * dorit.
 */
public class create {
	public static void main(String[] args) throws IOException {
		house h = new house();
		File in = new File("therm.in");
	    FileWriter wr = new FileWriter("therm.out");
			
		if(in.exists() == false) {
			System.out.println("not exists");
			return;
		}
				
		Scanner input = new Scanner(in);
				
		int number_rooms = input.nextInt();
		String a = input.next();
		double global_temp = Double.parseDouble(a);
		String c = input.next();	
		String d = c;
		double b = Double.parseDouble(c);
		int timestamp_ref;
		double global_hum = 0;
		
		if(b < 1000) {
			global_hum = b;
			timestamp_ref = input.nextInt();
		}
		else {
			int e = Integer.parseInt(d);
			timestamp_ref = e;
		}
		
		for(int i = 0; i < number_rooms; i++) {
			String room_name = input.next();
			String device_id = input.next();
			double surface = input.nextDouble();
			h.add_room(room_name, device_id, surface);
		}
		
		while(input.hasNext()) {
			String character = input.next();
			int time = 0;
			int ref = timestamp_ref;
			int poz = 0;
			int t = 0;
			double sum = 0;
			double med = 0;
			double count = 0;
			double sum1 = 0;
			double med1 = 0;
			double count1 = 0;
			int poz_start = 0;
			int poz_stop = 0;
		
			if(character.equals("OBSERVE")) {
				character = input.next();
				
				for(int i = 0; i < number_rooms; i++)
					if(h.room.get(i).device_id.equals(character)) {
						time = input.nextInt();
						t = i;
						break;
					}
					
				poz = 24 - ((ref - time) / 3600)-1;
			
				character = input.next();
				double temp1 = Double.parseDouble(character);
				int k = 0;
				for(int j = 0; j < h.room.get(t).interval.get(poz).temps.size(); j++)
					if(h.room.get(t).interval.get(poz).temps.get(j).doubleValue() == temp1)
						k++;
				
				if(k != 0)
					continue;
				if(poz >= 0 && poz < 24 && ref>=time) {
					h.room.get(t).interval.get(poz).temps.add(temp1);
					if(h.room.get(t).poz_max < poz)
						h.room.get(t).poz_max = poz;
				}
				Collections.sort(h.room.get(t).interval.get(poz).temps);
			}
			
			if(character.equals("OBSERVEH")) {
				character = input.next();
				
				for(int i = 0; i < number_rooms; i++)
					if(h.room.get(i).device_id.equals(character)) {
						time = input.nextInt();
						t = i;
						break;
					}
					
				poz = 24 - ((ref - time) / 3600)-1;
			
				character = input.next();
				double hum1 = Double.parseDouble(character);
				int k = 0;
				for(int j = 0; j < h.room.get(t).interval_hum.get(poz).humidity.size(); j++)
					if(h.room.get(t).interval_hum.get(poz).humidity.get(j).doubleValue() == hum1)
						k++;
				
				if(k != 0)
					continue;
				if(poz >= 0 && poz < 24 && ref>=time) {
					h.room.get(t).interval_hum.get(poz).humidity.add(hum1);
					if(h.room.get(t).poz_max < poz)
						h.room.get(t).poz_max = poz;
				}
				Collections.sort(h.room.get(t).interval_hum.get(poz).humidity);
			}

			if(character.equals("TRIGGER")) {
				for(int i = 0; i < number_rooms; i++) {
					if(h.room.get(i).interval.get(h.room.get(i).poz_max).temps.isEmpty()) 
						continue;
					sum += (h.room.get(i).interval.get(h.room.get(i).poz_max).temps.get(0)*h.room.get(i).surface);
					count += h.room.get(i).surface;
				}	
				
				for(int i = 0; i < number_rooms; i++) {
					if(h.room.get(i).interval_hum.get(h.room.get(i).poz_max).humidity.isEmpty()) 
						continue;
					sum1 += (h.room.get(i).interval_hum.get(h.room.get(i).poz_max).humidity.get(h.room.get(i).interval_hum.get(h.room.get(i).poz_max).humidity.size()-1)*h.room.get(i).surface);
					count1 += h.room.get(i).surface;
				}
				med1 = sum1/count1;
				if(med1 > global_hum && global_hum != 0) {
					wr.write("NO");
					wr.write("\n");

					continue;
				}
				med = sum/count;
				if(med < global_temp)
					wr.write("YES");
				else 
					wr.write("NO");
				wr.write("\n");
			}
			
			if(character.equals("TEMPERATURE")) {
				double character1;
				String v = input.next();
				
				character1 = Double.parseDouble(v);
				global_temp = character1;
			}
			
			if(character.equals("LIST")) {
				character = input.next();
				int time_start = input.nextInt();
				int time_stop = input.nextInt();
								
				for(int i = 0; i < number_rooms; i++) {
					if(h.room.get(i).room_name.equals(character)) {
						for(int j = 0; j < 24; j++)
							Collections.sort(h.room.get(i).interval.get(j).temps);
						t = i;
						break;
					}
				}
				wr.write(h.room.get(t).room_name +  " ");
				poz_start = 24 - ((timestamp_ref - time_start) / 3600)-1;
				poz_stop = 24 - ((timestamp_ref - time_stop) / 3600)-1;
				int k = 0;
				for(int i = poz_stop; i >= poz_start+1; i--) {
						for(int j = 0; j < h.room.get(t).interval.get(i).temps.size(); j++)
							wr.write(String.format(Locale.US, "%.2f", h.room.get(t).interval.get(i).temps.get(j))+ " ");
				}
				wr.write("\n");
			}
		}
		
		input.close();
		wr.close();
	}
}
