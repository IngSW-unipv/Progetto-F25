package it.unipv.poisw.f25.gympal.utility;
import java.util.Scanner;

import factories.StaffFactory;
import it.unipv.poisw.f25.gympal.staff.Staff;

//###########################################################

public class LoginManager implements IRegexExpression{
	
	static public LoginManager logininstance;

	//----------------------------------------------------------------	
	
	private LoginManager() {}
	
	//----------------------------------------------------------------	
	
	public static synchronized LoginManager getLoginInstance() {
		
		if (logininstance == null) {
			
			logininstance = new LoginManager();
			
		}
		
		return logininstance;
		
	}
	
	//----------------------------------------------------------------
	
	
	//Login restituirà un'istanza di dipendente, receptionist oppure manager
	public Staff login (String nome, String cognome, String staffID) {
		
		//boolean exitCondition = true;
		RegexCheck reg = new RegexCheck();
		StaffFactory factory = new StaffFactory();
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			
			
			//Più avanti nello sviluppo l'input da tastiera sarà rimosso, in favore del passaggio
			//di parametri, ricevuti da GUI
			System.out.println("Inserire nome utente:");
			nome = scanner.nextLine();
			
			System.out.println("Inserire cognome utente:");
			cognome = scanner.nextLine();
			
			System.out.println("Inserire ID:");
			staffID = scanner.nextLine();
		
			if(reg.check(nome, NAME_REGEXEXPRESSION) && reg.check(cognome, SURNAME_REGEXEXPRESSION)
				&& reg.check(staffID, STAFF_ID_REGEXEXPRESSION)) {
				
				System.out.println("Credenziali valide!");
				scanner.close(); //Da rimuovere in seguito
				CredentialsPOJO credentials = new CredentialsPOJO();
				credentials.setAllProperties(nome, cognome, staffID);
				
				//Va qui inserita la logica che mette a confronto le stringhe inserite con
				//le credenziali presenti in archivio.
				if(archiveCheck(credentials) == true) {
					
					//E' qui richiamata la factory che istanzia DIP, REC o MAN a seconda di cosa
					//contenga lo staffID
					
					//Farò in modo che login restituisca la stringa identificativa del membro
					//dello staff, con cui in seguito invocare la factory, laddove serva creare
					//lo specifico membro dello staff.
					
					return factory.generateStaffMember(reg.retrieveSubString(staffID));
					
				}
				else {System.out.println("Nessun riscontro in archivio! Ritenta");}
				
				
						
				
			}
			else {
				
				System.out.println("Credenziali -NON- valide! Ritenta");
				//Va qui inserita la logica che notifichi la GUI affinchè ridisigeni la schermata
				//di login, con un messaggio appropriato che indichi un'insuccesso
				
			}
	
		}
	
	}
	
	//----------------------------------------------------------------
	
	private boolean archiveCheck(CredentialsPOJO credentials) {
		
		//Logica di connessione e scambio informazioni con il DB
		return true;
		
	}
	
	
}
