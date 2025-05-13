package it.unipv.poisw.f25.gympal.staff;

import java.time.LocalDate;

public class Receptionist extends Staff {

	public Receptionist() {super();};

	//----------------------------------------------------------------
	
	public Receptionist(String nome, String cognome, String contatto, LocalDate dataDiNascita) {
		
		super(nome, cognome, contatto, dataDiNascita);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void presence() {System.out.println("REC: ci sono!");} //Metodo di test, da eliminare

	//----------------------------------------------------------------
	
	@Override
	public void initGUI() {System.out.println("GUI inizializzata");}
	/*Questo metodo notifica lo strato GUI, affinché questo visualizzi a schermo la finestra
	  entro cui sono raccolte le operazioni disponibili al Receptionist*/

	//----------------------------------------------------------------
	
	public void creaProfiloUtente() {};
	
	//----------------------------------------------------------------
	
	public void modificaAbbonamento() {};
	
	//----------------------------------------------------------------
	
	public void appointmentManager() {};

}
