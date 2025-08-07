//PRIMARY KEY = sessioneId

package it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean;

import java.time.LocalDate;

public class SessioneCorso {
	
	private  String sessioneId;
	private String staffId;
	private LocalDate data;
	private String fasciaOraria;
	private int numIscritti;
	
	// Costruttore di default
	public SessioneCorso() {
		
	}

	// Costruttore parametrizzato
	public SessioneCorso(String sessioneId, String staffId, LocalDate data, String fasciaOraria, int numIscritti) {
		this.sessioneId = sessioneId;
		this.staffId = staffId;
		this.data = data;
		this.fasciaOraria = fasciaOraria;
		this.numIscritti = numIscritti;
	}
	
	// Getters e setters

	public String getSessioneId() {
		return sessioneId;
	}

	public void setSessioneId(String sessioneId) {
		this.sessioneId = sessioneId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getFasciaOraria() {
		return fasciaOraria;
	}

	public void setFasciaOraria(String fassciaOraria) {
		this.fasciaOraria = fassciaOraria;
	}

	public int getNumIscritti() {
		return numIscritti;
	}

	public void setNumIscritti(int numIscritti) {
		this.numIscritti = numIscritti;
	}

	@Override
	public String toString() {
		return "SessioneCorso [sessioneId=" + sessioneId + ", staffId=" + staffId + ", data=" + data
				+ ", fasciaOraria=" + fasciaOraria + ", numIscritti=" + numIscritti + "]";
	}

}
