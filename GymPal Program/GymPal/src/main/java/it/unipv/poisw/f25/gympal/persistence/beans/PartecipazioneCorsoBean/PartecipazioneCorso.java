//PRIMARY KEY = cf, sessioneId

package it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean;

public class PartecipazioneCorso {
	
	private  String cf;
	private  String sessioneId;
	
	// Costruttore di default
	public PartecipazioneCorso() {
		
	}

	// Costruttore parametrizzato
	public PartecipazioneCorso(String cf, String sessioneId) {
		this.cf = cf;
		this.sessioneId = sessioneId;
	}

	// Getters e setters
	
	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getSessioneId() {
		return sessioneId;
	}

	public void setSessioneId(String sessioneId) {
		this.sessioneId = sessioneId;
	}

	@Override
	public String toString() {
		return "PartecipazioneCorso [cf=" + cf + ", sessioneId=" + sessioneId + "]";
	}
}
