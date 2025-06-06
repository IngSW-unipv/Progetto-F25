package it.unipv.poisw.f25.gympal.DAOs;

import java.time.LocalDate;

public class ClientDataPOJO {
	
    private String nome;
    private String cognome;
    private String indirizzo;
    private LocalDate dataDiNascita;
    private String codiceFiscale;
    
	//-----------------------------------------------------
    
    private ClientDataPOJO(Builder builder) {
    	
        this.nome = builder.nome;
        this.cognome = builder.cognome;
        this.indirizzo = builder.indirizzo;
        this.dataDiNascita = builder.dataDiNascita;
        this.codiceFiscale = builder.codiceFiscale;
        
    }

    
	//-----------------------------------------------------

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

	//-----------------------------------------------------
    
    /*Il Builder è considerato un "helper" esterno o interno*/
    
    public static class Builder {
    	
        private String nome;
        private String cognome;
        private String indirizzo;
        private LocalDate dataDiNascita;
        private String codiceFiscale;
        
    	//-----------------------------------------------------

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder cognome(String cognome) {
            this.cognome = cognome;
            return this;
        }

        public Builder indirizzo(String indirizzo) {
            this.indirizzo = indirizzo;
            return this;
        }

        public Builder dataDiNascita(LocalDate dataDiNascita) {
            this.dataDiNascita = dataDiNascita;
            return this;
        }

        public Builder codiceFiscale(String codiceFiscale) {
            this.codiceFiscale = codiceFiscale;
            return this;
        }
        
    	//-----------------------------------------------------

        public ClientDataPOJO build() {
            return new ClientDataPOJO(this);
        }
        
        /*  ClientDataPOJO client = new ClientDataPOJO.Builder()
            .nome("Luca")
            .cognome("Bianchi")
            .indirizzo("Via Roma 123, Milano")
            .dataDiNascita(LocalDate.of(1990, 5, 10))
            .codiceFiscale("BNCGLC90E10F205X")
            .build(); */
        
    }
    
}
