package it.unipv.poisw.f25.gympal.factories;

import it.unipv.poisw.f25.gympal.staff.*;

public class StaffFactory {

	public StaffFactory(){};
	
	public Staff generateStaffMember(String key) {
		
		switch(key) {
		
			case "DIP": return new Dipendente();
			case "REC": return new Receptionist();
			case "MAN": return new Manager();
		
		}
		return null;
		
	}
	
}
