package it.unipv.poisw.f25.gympal.GUI.MasterDTOEBuilder;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;

public interface IDatiClienteReadOnly {
	
	String getNome();
	
	//----------------------------------------------------------------

    String getCognome();
    
    //----------------------------------------------------------------

    String getCodiceFiscale();
    
    //----------------------------------------------------------------

    String getSesso();
    
    //----------------------------------------------------------------

    String getContatto();
    
    //----------------------------------------------------------------

    LocalDate getDataNascita();
    
    //----------------------------------------------------------------

    boolean getCertificatoIdoneita();
    
    //----------------------------------------------------------------

    boolean getPermessoGenitori();
    
    //----------------------------------------------------------------

    List<String> getSezioniAbbonamento();
    
    //----------------------------------------------------------------

    List<String> getCorsiSelezionati();
    
    //----------------------------------------------------------------

    DurataAbbonamento getDurataAbbonamento();
    
    //----------------------------------------------------------------

}
