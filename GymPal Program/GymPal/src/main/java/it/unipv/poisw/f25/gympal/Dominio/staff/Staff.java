package it.unipv.poisw.f25.gympal.Dominio.staff;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import it.unipv.poisw.f25.gympal.utility.IDateFormat;

public abstract class Staff implements IDateFormat{
	
	//generalità del dipendente
	private String nome, cognome, contatto;
	private LocalDate dataDiNascita;
	private String staffID;
	
	//----------------------------------------------------------------
	
	/*Costruttori*/
	
	public Staff() {}; 

	public Staff(String nome, String cognome, String contatto, LocalDate dataDiNascita) {
		this.nome = nome;
		this.cognome = cognome;
		this.contatto = contatto;
		this.dataDiNascita = dataDiNascita; 
		generateStaffId();
	}
	
	//----------------------------------------------------------------
	
	public void setBasicInfo(String nome, String cognome, String staffID) {
		
	    this.nome = nome;
	    this.cognome = cognome;
	    this.staffID = staffID;
	    
	}
	
	//----------------------------------------------------------------
	
	//getters e setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	//----------------------------------------------------------------
	
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	//----------------------------------------------------------------

	public String getContatto() {
		return contatto;
	}

	public void setContatto(String contatto) {
		this.contatto = contatto;
	}

	//----------------------------------------------------------------

	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	
	public String getStaffID() {
		return staffID;
	}
	
	//----------------------------------------------------------------
	
	//metodo per formattare la data
    public String getDataNascitaFormattata() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATEFORMAT);
        return dataDiNascita.format(formatter);
    }
    
	//----------------------------------------------------------------
	
	public void presence() {System.out.println("STAFF: ci sono!");}
	
	//----------------------------------------------------------------
	
	public void initGUI() {};
	
	//----------------------------------------------------------------	
	
	//metodo per generare lo staff Id
	private void generateStaffId() {
		staffID = nome + "_" + cognome;		
	}
	
	//----------------------------------------------------------------	
	
}
