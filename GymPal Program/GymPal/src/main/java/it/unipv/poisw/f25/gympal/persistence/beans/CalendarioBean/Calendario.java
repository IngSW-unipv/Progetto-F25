package it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean;

import java.time.LocalDate;

public class Calendario {
	
	private String nomeEvento;
    private LocalDate dataEvento;
    private String messaggio;
    private String destinatarioMessaggio;
    
    //Costruttore di default
    public Calendario() {
    }
    
    //Costruttore parametrizzato
	public Calendario(String nomeEvento, LocalDate dataEvento, String messaggio, String destinatarioMessaggio) {
		this.nomeEvento = nomeEvento;
		this.dataEvento = dataEvento;
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
		return "Calendario [nomeEvento=" + nomeEvento + ", dataEvento=" + dataEvento + ", messaggio=" + messaggio
				+ ", destinatarioMessaggio=" + destinatarioMessaggio + "]";
	}
}
