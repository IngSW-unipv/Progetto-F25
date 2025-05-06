package it.unipv.poisw.f25.gympal.staff;

import java.time.LocalDate;

public class Manager extends Staff{

	public Manager() {super();};
	
	public Manager(String nome, String cognome, String contatto, LocalDate dataDiNascita) {
		super(nome, cognome, contatto, dataDiNascita);
		
	}
	
	@Override
	public void presence() {System.out.println("MAN: ci sono!");}

}
