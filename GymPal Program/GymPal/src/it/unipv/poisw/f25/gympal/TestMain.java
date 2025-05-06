package it.unipv.poisw.f25.gympal;

import it.unipv.poisw.f25.gympal.staff.*;
import it.unipv.poisw.f25.gympal.utility.*;

public class TestMain {

	public static void main(String[] args) {
		
		LoginManager log = LoginManager.getLoginInstance();
		Staff member = log.login("Dud", "Dud", "Dud");
		member.presence();
		
	}

}
