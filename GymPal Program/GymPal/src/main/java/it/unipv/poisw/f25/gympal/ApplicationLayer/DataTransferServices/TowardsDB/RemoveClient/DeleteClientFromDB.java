package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.RemoveClient;

import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;
import it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean.PartecipazioneCorso;

public class DeleteClientFromDB implements IDeleteClientFromDB{
	
	private IPersistenceFacade persistenceFacade;
    private ICalendarioFacadeService calendarioFacade;
    
	//----------------------------------------------------------------

    public DeleteClientFromDB(IPersistenceFacade facade, ICalendarioFacadeService calendarioFacade) {
    	
        this.persistenceFacade = facade;
        this.calendarioFacade = calendarioFacade;
        
    }
    
	//----------------------------------------------------------------

    @Override
    public boolean huntAndKill(IDatiCliente abbDTO) {
    	
        Cliente cliente = new Cliente();
        cliente.setCf(abbDTO.getCodiceFiscale());
        
        return persistenceFacade.deleteCliente(cliente);
        
    }
    
	//----------------------------------------------------------------

    @Override
    public boolean eliminaClienteCompletamente(IDatiCliente abbDTO) {
    	
        String cfCliente = abbDTO.getCodiceFiscale();
        
        if (cfCliente == null || cfCliente.isEmpty()) {
        	
            return false;
            
        }

        try {
        	
            annullaPartecipazioniCorsi(cfCliente);
            annullaAppuntamentiPT(cfCliente);
            return huntAndKill(abbDTO);
            
        } catch (Exception e) {
        	
            e.printStackTrace();
            return false;
            
        }
        
    }
    
	//----------------------------------------------------------------

    private void annullaPartecipazioniCorsi(String cfCliente) {
    	
        List<PartecipazioneCorso> partecipazioni = calendarioFacade.getPartecipazioniCliente(cfCliente);

        if (partecipazioni != null && !partecipazioni.isEmpty()) {
        	
            for (PartecipazioneCorso p : partecipazioni) {
            	
                calendarioFacade.annullaPartecipazione(p.getCf(), p.getSessioneId());
                
            }
            
        }
        
    }
    
	//----------------------------------------------------------------

    private void annullaAppuntamentiPT(String cfCliente) {
    	
        List<AppuntamentoPT> appuntamenti = 
        		calendarioFacade.getAppuntamentiPT(cfCliente);

        if (appuntamenti != null && !appuntamenti.isEmpty()) {
        	
            for (AppuntamentoPT app : appuntamenti) {
            	
                calendarioFacade.annullaLezionePT(app.getCf(), 
                								  app.getStaffId(), 
                								  app.getData(), 
                								  app.getFasciaOraria());
                
            }
            
        }
        
    }
    
	//----------------------------------------------------------------

}
