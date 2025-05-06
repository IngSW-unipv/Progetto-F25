package it.unipv.poisw.f25.gympal.staff;

import java.time.LocalDate;

public class Receptionist extends Staff {

	public Receptionist() {super();};
	
	public Receptionist(String nome, String cognome, String contatto, LocalDate dataDiNascita) {
		super(nome, cognome, contatto, dataDiNascita);
		
	}
	
	@Override
	public void presence() {System.out.println("REC: ci sono!");}

}
