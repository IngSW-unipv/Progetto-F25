package it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities;
import java.io.Serializable;

public class CredentialsPOJO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String nome, cognome, staffID;
	
	
	
	public CredentialsPOJO() {}
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	//-----------------------------------------------------

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	//-----------------------------------------------------

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	
	//-----------------------------------------------------
	
	public void setAllProperties(String nome, String cognome, String staffID) {
		
		setNome(nome);
		setCognome(cognome);
		setStaffID(staffID);
		
	}
	
}
