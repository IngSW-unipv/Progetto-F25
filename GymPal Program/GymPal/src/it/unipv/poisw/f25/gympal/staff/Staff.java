package it.unipv.poisw.f25.gympal.staff;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import it.unipv.poisw.f25.gympal.utility.IDateFormat;

public abstract class Staff implements IDateFormat{
	
	//generalità del dipendente
	private String nome, cognome, contatto, password;
	private LocalDate dataDiNascita;
	
	
	private String staffID;
	
	public Staff() {}; //Da rimuovere, in caso

	//costruttore
	public Staff(String nome, String cognome, String contatto, LocalDate dataDiNascita) {
		this.nome = nome;
		this.cognome = cognome;
		this.contatto = contatto;
		this.dataDiNascita = dataDiNascita; 
		generateStaffId();
	}
	
	//getters e setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getContatto() {
		return contatto;
	}

	public void setContatto(String contatto) {
		this.contatto = contatto;
	}


	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	
	public String getStaffID() {
		return staffID;
	}
	
	//metodo per formattare la data
    public String getDataNascitaFormattata() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATEFORMAT);
        return dataDiNascita.format(formatter);
    }
	
	//metodo per generare lo staff Id
	private void generateStaffId() {
		staffID = nome + "_" + cognome;		
	}
	
	
	public void presence() {System.out.println("STAFF: ci sono!");}
	
		
	
}
