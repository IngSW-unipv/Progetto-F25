//PRIMARY KEY = nomeSconto

package it.unipv.poisw.f25.gympal.persistence.beans.Sconto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Sconto {
	
	private String nomeSconto;
	private LocalDate dataSconto;
	private BigDecimal amountSconto;
	
	// Costruttore di default
	public Sconto() {
	}

	// Costruttore parametrizzato
	public Sconto(String nomeSconto, LocalDate dataSconto, BigDecimal amountSconto) {
		this.nomeSconto = nomeSconto;
		this.dataSconto = dataSconto;
		this.amountSconto = amountSconto;
	}

	// Getters e setters
	public String getNomeSconto() {
		return nomeSconto;
	}


	public void setNomeSconto(String nomeSconto) {
		this.nomeSconto = nomeSconto;
	}


	public LocalDate getDataSconto() {
		return dataSconto;
	}


	public void setDataSconto(LocalDate dataSconto) {
		this.dataSconto = dataSconto;
	}


	public BigDecimal getAmountSconto() {
		return amountSconto;
	}


	public void setAmountSconto(BigDecimal amountSconto) {
		this.amountSconto = amountSconto;
	}


	@Override
	public String toString() {
		return "Sconto [nomeSconto=" + nomeSconto + ", dataSconto=" + dataSconto + ", amountSconto=" + amountSconto
				+ "]";
	}
	
	

}
