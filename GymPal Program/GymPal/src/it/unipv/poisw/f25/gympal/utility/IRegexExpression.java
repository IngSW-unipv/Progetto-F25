package it.unipv.poisw.f25.gympal.utility;

public interface IRegexExpression {

	public static final String STAFF_ID_REGEXEXPRESSION =
		    "^(?!.*[\";<>%=])[A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*(?:[ '-][A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*)*_" +
		    "[A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*(?:[ '-][A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*)*_" +
		    "(REC|MAN|DIP)$";

	public static final String NAME_REGEXEXPRESSION = "^(?!.*[\";<>%=]).*[A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*(?:[ '-][A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*){0,4}$";
	
	public static final String SURNAME_REGEXEXPRESSION = "^(?!.*[\";<>%=]).*[A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*(?:[ '-][A-ZÀ-Ý][a-zà-ÿA-ZÀ-Ý]*){0,4}$";
	
	public static final String CODICE_FISCALE = "^[A-Z]{6}[0-9]{2}[A-EHLMPRST]{1}[0-9]{2}[A-Z]{1}[0-9A-Z]{3}[A-Z]{1}$";
	
	/*1) Aggiornate le regex affinché impongano la presenza di una maiuscola in testa alla stringa,
	 *   per nomi, cognomi.
	 *
	 *NOTA: il "+" che precede "$" impone che nella stringa vi sia almeno un'altro carattere, oltre
	 *	    alla maiuscola in testa. Se il "$" fosse preceduto da "*", allora si potrebbe avere
	 * 	    anche solo la maiuscola iniziale, senza altro carattere a seguirla.*/
	
	/*2) Ulteriore aggiornamento regex per tener conto di lettere accentate, apostrofi, trattini
	 *   e spazi in nomi e cognomi.*/
	
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
	 * */
	
		
}
