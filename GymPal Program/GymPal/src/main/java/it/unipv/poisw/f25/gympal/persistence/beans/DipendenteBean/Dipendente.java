package it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean;

public class Dipendente {
	
	private String staffId;
    private String nome;
    private String cognome;
    private String contatto;
    
    //Costruttore di default
    public Dipendente() {
    }

	public Dipendente(String staffId, String nome, String cognome, String contatto) {
		this.staffId = staffId;
		this.nome = nome;
		this.cognome = cognome;
		this.contatto = contatto;
	}
	
	//Getters and setters

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

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

	@Override
	public String toString() {
		return "Dipendente [staffId=" + staffId + ", nome=" + nome + ", cognome=" + cognome + ", contatto=" + contatto
				+ "]";
	}
}
