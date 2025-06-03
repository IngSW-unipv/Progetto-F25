package it.unipv.poisw.f25.gympal.utility;

public interface IRegexExpression {

	public static final String STAFF_ID_REGEXEXPRESSION =
		    "^(?!.*[\";<>%=])[A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*(?:[ '-][A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*)*_" +
		    "[A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*(?:[ '-][A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*)*_" +
		    "(REC|MAN|DIP)_[0-9]{4}[A-ZÀ-Ý]{2,34}$";

	public static final String NAME_REGEXEXPRESSION = "^(?!.*[\";<>%=]).*[A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*(?:[ '-][A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*){0,4}$";
	
	public static final String SURNAME_REGEXEXPRESSION = "^(?!.*[\";<>%=]).*[A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*(?:[ '-][A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*){0,4}$";
	
	public static final String CODICE_FISCALE = "^[A-Z]{6}[0-9]{2}[A-EHLMPRST]{1}[0-9]{2}[A-Z]{1}[0-9A-Z]{3}[A-Z]{1}$";
	
	public static final String EMAIL = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z0-9.-]{1,253}\\.[a-zA-Z]{2,}$"; 

	

	
	/*Struttura delle regex:
	 * 
	 * "^" ---> Indica che il controllo parte da inizio stringa
	 * 
	 * "(?!.*[\";<>%=])" ---> Questo è un "Negative LookAhead": "(?!...)" controlla che nella
	 * 						  stringa in esame non vi sia nulla di quanto specificato fra le
	 * 						  parentesi tonde, nello specifico "[\";<>%=]". Ovvero, se la stringa
	 * 						  contiene anche solo uno fra questi caratteri:
	 * 
	 * 							", ;, <, >, %, =
	 * 
	 * 						  allora il match fallisce.
	 * 
	 * "[A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*" ---> Impone che la prima lettera sia una maiuscola, accentata 
	 * 								  oppure no. Segue un numero qualunque di lettere accentate,
	 * 								  oppure no.
	 * 
	 * "(?:[ '-][A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*)*" ---> Questo è un gruppo 'non capture', che consente di
	 * 											raggruppare più espressioni, senza restituire 
	 * 											match separati.
	 * 										    La particella "[ '-]" accetta spazio, apostrofo o
	 * 											trattino come separatore fra parti di un nome 
	 * 											(es: De La Cruz, D'Amico, Jean-Pierre ecc.)
	 * 
	 * "_" ---> Gli underscore fungono da separatori fissi: ciascuna parte dell'ID è divisa dalle
	 * 			altre da un singolo underscore, in modo esatto.
	 * 
	 * "(REC|MAN|DIP)" ---> Costituisce una scelta esclusiva fra uno di tre possibili ruoli.
	 * 
	 * "$" ---> Demarca la fine della stringa, in modo che la regex si aspetti l'assenza di ulte-
	 * 			riori caratteri in coda a "(REC|MAN|DIP)".
	 * 
	 *
	 *Per la regex della mail:
	 *
	 * "\\." ---> impone la presenza del punto prima del dominio
	 * 
	 * "[a-zA-Z]{2,}" ---> il dominio deve essere composto da almeno due lettere, vincolo imposto
	 * 					   da "{2,}"
	 * 
	 * "{1, 253}" ---> non è scelto a caso. Il massimo numero di caratteri consentito per un 
	 * 				   intero nome di dominio è 255 byte secondo lo standard RFC 1035.
	 * 
	 * 				   Sottraendo la "@" e il punto "." finale, rimangono 253 caratteri
	 * 				   utilizzabili nel dominio vero e proprio.*/
	
		
}
