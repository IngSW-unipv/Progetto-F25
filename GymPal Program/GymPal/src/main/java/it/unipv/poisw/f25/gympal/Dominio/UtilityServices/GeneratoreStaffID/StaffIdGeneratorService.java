package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GeneratoreStaffID;

import java.text.Normalizer;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexExpression;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class StaffIdGeneratorService implements IStaffIdGeneratorService{
	
	private final IPersistenceFacade persistence;
	
    //------------------------------------------------------------

    public StaffIdGeneratorService(IPersistenceFacade persistence) {
    	
        this.persistence = persistence;
        
    }
    
    //------------------------------------------------------------

    @Override
    public String generaStaffId(String nome, String cognome, 
    							String ruolo, String citta) {
    	
        if (nome == null || cognome == null || ruolo == null || citta == null) {
        	
            throw new IllegalArgumentException("Tutti i campi sono obbligatori per "
            								 + "generare uno staffId.");
            
        }

        String nomeNorm = normalizza(nome).toUpperCase();
        String cognomeNorm = normalizza(cognome).toUpperCase();
        String ruoloNorm = ruolo.toUpperCase();
        String cittaNorm = normalizza(citta).toUpperCase();

        int prossimoNumero = calcolaNumeroProgressivo(ruoloNorm, cittaNorm);
        String numeroFormatted = String.format("%04d", prossimoNumero);

        String staffId = String.format("%s_%s_%s_%s%s", nomeNorm, cognomeNorm, 
        							   ruoloNorm, numeroFormatted, cittaNorm);

        if (!staffId.matches(IRegexExpression.STAFF_ID_REGEXEXPRESSION)) {
        	
            throw new IllegalArgumentException("Staff ID generato non valido: " 
            								  + staffId);
            
        }

        return staffId;
    }
    
    //------------------------------------------------------------

    private String normalizza(String input) {
    	
        if (input == null) {return "";} /*Per evitare 'NullPointerException'*/
        
        /* 'Normalizer' fa parte del pacchetto 'java.text', e serve a normalizzare
         * i caratteri UNICODE, separando lettere da accenti ed altri segni dia-
         * critici*/
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        
        /* Nel particolare: "[\\p{InCombiningDiacriticalMarks}]", ""
         * Questa parte è responsabile della rimozione di segni come accenti, diere-
         * si, ecc. 
         * 
         * Amelié ---> Amelie
         * 
         * Di Bellò ---> Di Bello
         * 
         * Nel particolare: "[^\\p{L}0-9]", ""
         * Questa seconda parte rimuove tutto ciò che non è lettera e/o cifra.
         * Via spazi, apostrofi, trattini, punteggiatura e simboli vari.
         * 
         * Mc'Douglas ---> McDouglas
         * 
         * Gian-Battistia ---> GianBattista*/
        return normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                         .replaceAll("[^\\p{L}0-9]", ""); 
        
    }
    
    //------------------------------------------------------------

    private int calcolaNumeroProgressivo(String ruolo, String cittaNorm) {
    	
        List<Dipendente> dipendenti = persistence.selectAllDipendenti();
        int max = 0;

        for (Dipendente d : dipendenti) {
        	
            String staffId = d.getStaffId();

            if (staffId == null) continue;

            String suffisso = "_" + ruolo + "_";
            int idx = staffId.indexOf(suffisso);
            if (idx < 0) continue;

            String post = staffId.substring(idx + suffisso.length()); // es: 0002MILANO
            if (post.endsWith(cittaNorm)) {
            	
                String numeroStr = post.substring(0, post.length() - cittaNorm.length());
                try {
                	
                    int numero = Integer.parseInt(numeroStr);
                    if (numero > max) max = numero;
                    
                } catch (NumberFormatException ignored) {}
                
            }
            
        }

        return max + 1;
    }
    
    //------------------------------------------------------------
}
