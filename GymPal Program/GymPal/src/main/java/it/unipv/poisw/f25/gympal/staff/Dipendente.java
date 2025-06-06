package it.unipv.poisw.f25.gympal.staff;

import java.time.LocalDate;

public class Dipendente extends Staff {

	public Dipendente() {super();};
	
	public Dipendente(String nome, String cognome, String contatto, LocalDate dataDiNascita) {
		super(nome, cognome, contatto, dataDiNascita);
		
	}
	
	@Override
	public void presence() {System.out.println("DIP: ci sono!");}
	
	@Override
	public void initGUI() {System.out.println("GUI inizializzata");}

}
