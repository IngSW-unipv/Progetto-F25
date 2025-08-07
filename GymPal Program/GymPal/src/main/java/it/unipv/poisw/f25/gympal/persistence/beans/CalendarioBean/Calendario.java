//PRIMARY KEY = nomeEvento, dataEvento, oraInizio, oraFine

package it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean;

import java.time.LocalDate;
import java.time.LocalTime;

public class Calendario {
	
	private String nomeEvento;
    private LocalDate dataEvento;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private String messaggio;
    private String destinatarioMessaggio;
    
    //Costruttore di default
    public Calendario() {
    }
    
    //Costruttore parametrizzato
    public Calendario(String nomeEvento, LocalDate dataEvento, LocalTime oraInizio, LocalTime oraFine, String messaggio,
			String destinatarioMessaggio) {
		super();
		this.nomeEvento = nomeEvento;
		this.dataEvento = dataEvento;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.messaggio = messaggio;
		this.destinatarioMessaggio = destinatarioMessaggio;
	}
    
    
    //Getters e setters
	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public LocalDate getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(LocalDate dataEvento) {
		this.dataEvento = dataEvento;
	}

	public LocalTime getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public LocalTime getOraFine() {
		return oraFine;
	}

	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public String getDestinatarioMessaggio() {
		return destinatarioMessaggio;
	}

	public void setDestinatarioMessaggio(String destinatarioMessaggio) {
		this.destinatarioMessaggio = destinatarioMessaggio;
	}

	@Override
	public String toString() {
		return "Calendario [nomeEvento=" + nomeEvento + ", dataEvento=" + dataEvento + ", oraInizio=" + oraInizio
				+ ", oraFine=" + oraFine + ", messaggio=" + messaggio + ", destinatarioMessaggio="
				+ destinatarioMessaggio + "]";
	}
}
