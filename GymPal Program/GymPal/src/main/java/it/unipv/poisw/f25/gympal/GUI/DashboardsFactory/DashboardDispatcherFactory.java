package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.ReceptionistDashboardAvviabile;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.staff.Receptionist;
import it.unipv.poisw.f25.gympal.staff.Staff;


/*DashboardDispatcherFactory è una factory con dispatching che:

- riceve un oggetto di tipo Staff

- individua dinamicamente, in base al tipo dell'oggetto, quale dashboard avviare

- restituisce un oggetto che implementa l’interfaccia IDashboardAvviabile,
  che potrà poi essere eseguito per aprire la GUI*/

public class DashboardDispatcherFactory {
	
	/*Questa istruzione dichiara e inizializza una mappa statica che associa:

	  - un tipo (classe) che estende Staff

	  - ad una funzione che, preso un oggetto di tipo Staff, restituisce un
	    oggetto di tipo "IDashboardAvviabile"*/
	
	/*"Class<T>" è la classe Java che rappresenta i metatipi (tipi a runtime).
	 * 
	 *"Class<? extends Staff>" vuol dire "Un oggetto Class che rappresenta 
	 *una classe qualsiasi che estende (o implementa) Staff".
	 * 
	 *"? extends Staff" è una wildcard con upper bound: accetta qualsiasi
	 *tipo che sia una sottoclasse di Staff*/
	
	/*"Function<Staff, IDashboardAvviabile>" è il tipo del valore associato nella mappa.
	 * 
	 * "Function<T, R>" è un'interfaccia funzionale di Java 8, dove:
	 * 
	 * - "T" tipo di input (Staff)
	 * 
	 * - "R" tipo di output
	 * 
	 * In pratica, nel caso specifico, una funzione che prende un oggetto "Staff" e
	 *  restituisce un oggetto "IDashboardAvviabile"*/
	
	/*Questa mappa è definita statica poiché serve come registro condiviso a
	 *livello di classe, indipendente dalle istanze di "DashboardDispatcherFactory"
	 *altrimenti si avrebbe inefficienza e ridondanza*/
	
	/*Tale soluzione permette di creare dinamicamente l’istanza corretta della dashboard
	 *senza if/else o switch.*/
	
    private static final Map<Class<? extends Staff>, 
    						 Function<Staff, IDashboardAvviabile>> mappa = new HashMap<>();

    //----------------------------------------------------------------
    
    /*Usando un blocco static, è possibile popolare la mappa appena la factory viene caricata,
	  senza bisogno di chiamare metodi esplicitamente o creare istanze della factory*/
    
    /*Il blocco static permette di:

	  - Centralizzare tutte le regole di dispatching (registrazione dei tipi supportati)

	  - Separare la logica di inizializzazione dalla logica operativa (getDashboardPer)

      - Rendere il codice leggibile e ordinato
      
      - Elimina il rischio di eseguire più volte l'inizializzazione della mappa*/
    
    /*Inoltre, dal momento che il metodo getDashboardPer è static, non c'è bisogno
     *di creare un oggetto DashboardDispatcherFactory. 
     *Quindi, anche la mappa e le regole devono essere statiche, così da poter essere 
     *accessibili senza istanziare la classe*/
    
    static {
    	
    	/* Il metodo "put" serve ad inserire una coppia chiave-valore nella mappa.
    	 * 
    	 * La funzione lambda "staff -> new ReceptionistDashboardAvviabile((Receptionist) staff)"
    	 * corrisponde a "Function<Staff, IDashboardAvviabile>", e funziona perché 
    	 * "ReceptionistDashboardAvviabile" implementa "IDashboardAvviabile", dunque è
    	 * del tipo corretto*/
    	
        mappa.put(Receptionist.class, staff -> new ReceptionistDashboardAvviabile(
        		 (Receptionist) staff));
        // mappa.put(Admin.class, staff -> new AdminDashboardAvviabile((Admin) staff));
        
    }
    
    //----------------------------------------------------------------
    
    /*Il metodo "getDashboardPer(Staff staff)" funziona così:
     * 
     *- Prende la classe esatta dell’oggetto staff con staff.getClass()

	  - Cerca nella mappa se c’è una funzione associata a quella classe

	  - Se la trova, la invoca "(apply(staff))" per creare l’oggetto IDashboardAvviabile

	  - Se non la trova, lancia un’eccezione*/

    public static IDashboardAvviabile getDashboardPer(Staff staff) {
    	
        Function<Staff, IDashboardAvviabile> factory = mappa.get(staff.getClass());
        
        if (factory == null) {
        	
            throw new IllegalArgumentException("Nessuna dashboard definita per: "
            									+ staff.getClass());
        }
        
        return factory.apply(staff);
        
    }
    
    //----------------------------------------------------------------
    
}

