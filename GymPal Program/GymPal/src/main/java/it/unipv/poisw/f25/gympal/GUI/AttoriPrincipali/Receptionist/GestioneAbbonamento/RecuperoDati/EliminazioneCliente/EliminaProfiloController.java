package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.IGestioneAbbCoordinator;

public class EliminaProfiloController {
	
	/*Vista*/
	private IEliminaProfiloView eliminaView;
	
	/*Coordinatore*/
	private IGestioneAbbCoordinator coordinator;
	
	/*CallBacks*/
	private Runnable onAnnulla;
	private Runnable onIndietro;
	private Runnable onConferma;
	
	//----------------------------------------------------------------
	
	public EliminaProfiloController(IEliminaProfiloView eliminaView,
									 Runnable onAnnulla,
									 Runnable onIndietro,
									 Runnable onConferma,
									 IGestioneAbbCoordinator coordinator) {
		
		/*Vista*/
		this.eliminaView = eliminaView;
		
		/*Coordinatore*/
		this.coordinator = coordinator;
		
		/*Callbacks*/
		this.onAnnulla = onAnnulla;
		this.onIndietro = onIndietro;
		this.onConferma = onConferma;
		
		/*Inizializzazione Vista*/
		impostaEventoAnnulla();
		impostaEventoIndietro();
		impostaEventoConferma();
		
		impostaLabel();
		
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoAnnulla() {
		
		eliminaView.addAnnullaListener(e -> {onAnnulla.run();});
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoIndietro() {
		
		eliminaView.addIndietroListener(e -> {onIndietro.run();});
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoConferma() {
		
		eliminaView.addConfermaListener(e -> {
			
	        int result = JOptionPane.showConfirmDialog(
                    null, 
                    "Vuoi davvero confermare?", 
                    "Conferma eliminazione", 
                    JOptionPane.YES_NO_OPTION); 
	        
	        if (result == JOptionPane.YES_OPTION) {
            
                try {
    	        	
        	        onConferma.run();
                        
                 } catch (Exception ex) {
            	 
                        JOptionPane.showMessageDialog(
                        	eliminaView.getMainPanel(),
                            "Errore, tentativo eliminazione FALLITO:\n" + ex.getMessage(),
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                 }
            	
	        }
            
        });
		
	}
	
	
	//----------------------------------------------------------------
	
    
    private void impostaLabel() {
    	
        if (coordinator.getDTO() != null && coordinator.getDTO().getCodiceFiscale() != null) {
        	
            eliminaView.getCfLabel().setText("Codice fiscale cliente: " 
            								+ coordinator.getDTO().getCodiceFiscale());
            
        } else {
        	
            eliminaView.getCfLabel().setText("Codice fiscale cliente: "
            							   + "[dato non disponibile]");
            
        }
    	
    }
    
    
	//----------------------------------------------------------------

}
