//PRIMARY KEY = cf, staffId, data, fasciaOraria

package it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean;

import java.util.List;

public interface IAppuntamentoPTDAO {

    // Inserisce un nuovo appuntamento nel database
    boolean insertAppuntamento(AppuntamentoPT appuntamento);

    // Cancella un appuntamento dal database
    boolean deleteAppuntamento(AppuntamentoPT appuntamento);

    // Recupera un singolo appuntamento
    AppuntamentoPT findAppuntamento(AppuntamentoPT appuntamento);

    // Recupera tutti gli appuntamenti dal database
    List<AppuntamentoPT> selectAll();

    // Recupera tutti gli appuntamenti di un cliente
    List<AppuntamentoPT> selectAllAppuntamentiByCf(AppuntamentoPT appuntamento);
    
    // Recupera tutti gli appuntamenti di un personal trainer
    List<AppuntamentoPT> selectAllAppuntamentiByStaffId(AppuntamentoPT appuntamento);

    // Recupera tutti gli appuntamenti in una data specifica
    List<AppuntamentoPT> selectAllAppuntamentiByDate(AppuntamentoPT appuntamento);
    
    // Cancella tutti gli appuntamenti antecedenti alla data attuale
 	boolean deleteOldAppuntamenti();
}
