package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;

public class EventiHandler {
	
    private IDatiCellaCalendarioDTO dto;
    private ICalendarioService calendarioService;
    
    //----------------------------------------------------------------

    public EventiHandler(IDatiCellaCalendarioDTO dto, 
    					 ICalendarioService calendarioService) {
    	
        this.dto = dto;
        this.calendarioService = calendarioService;
        
    }
    
    //----------------------------------------------------------------

    public void caricaEventiPerDataEOra() {
    	
        // Recupera tutti gli eventi del giorno
        /*LocalDate data = dto.getData();
        List<Calendario> eventiDelGiorno = calendarioService.findEventiByData(data);

        List<String> eventi = new ArrayList<>();
        for (Calendario evento : eventiDelGiorno) {
        	
            int oraEvento = evento.getOraInizio().getHour();

            if (oraEvento == dto.getOra()) 
            {
                eventi.add(evento.getNomeEvento());
                
            }
            
        }

        dto.getEventiGenerici().clear();
        dto.getEventiGenerici().addAll(eventi);*/
        
    }
    
    //----------------------------------------------------------------

}
