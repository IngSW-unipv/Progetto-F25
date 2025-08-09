package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers.CorsiHandler;

public class DTOHandlerCalendarioSettimanale {
	
	private IDatiCellaCalendarioDTO dto;
    private ICalendarioService calendarioService;

    //----------------------------------------------------------------

    public DTOHandlerCalendarioSettimanale(IDatiCellaCalendarioDTO dto, 
    									   ICalendarioService calendarioService) {
    	
        this.dto = dto;
        this.calendarioService = calendarioService;
        
    }

    //----------------------------------------------------------------

    public void popolaDatiCella(int minuti) {
        // Esecuzione in cascata degli handler specializzati
    	
    	////DA RIMUOVERE QUANDO APPUNTAMENTI ED EVENTI SARANNO FUNZIONANTI
        dto.getAppuntamentiPT().clear();
        dto.getEventiGenerici().clear();
        //////////////////////////////////////////////////////////////////

        // Turni staff
        /*TurniHandler turniHandler = new TurniHandler(dto, calendarioService);
        turniHandler.caricaTurniPerDataEOraEMinuti(minuti);*/

        // Corsi di gruppo
        CorsiHandler corsiHandler = new CorsiHandler(dto, calendarioService);
        corsiHandler.caricaCorsiPerDataEOraEMinuti(minuti);

        // Appuntamenti PT
        /*AppuntamentiHandler appuntamentiHandler = new AppuntamentiHandler(dto, calendarioService);
        appuntamentiHandler.caricaAppuntamentiPerDataEOraEMinuti(minuti);*/

        // Eventi generici (se necessario)
        /*EventiHandler eventiHandler = new EventiHandler(dto, calendarioService);
        eventiHandler.caricaEventiPerDataEOraEMinuti(minuti);*/
    }
 
    //----------------------------------------------------------------
    
}
