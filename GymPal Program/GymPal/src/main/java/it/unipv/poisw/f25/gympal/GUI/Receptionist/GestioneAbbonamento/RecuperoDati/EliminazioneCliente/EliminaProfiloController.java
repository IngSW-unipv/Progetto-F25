package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.IGestioneAbbCoordinator;

public class EliminaProfiloController {
	
	/*Vista*/
	IEliminaProfiloView eliminaView;
	
	/*Coordinatore*/
	IGestioneAbbCoordinator coordinator;
	
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
		
		this.eliminaView = eliminaView;
		this.coordinator = coordinator;
		
		this.onAnnulla = onAnnulla;
		this.onIndietro = onIndietro;
		this.onConferma = onConferma;
		
		impostaEventoAnulla();
		impostaEventoIndietro();
		impostaEventoConferma();
		
		impostaLabel();
		
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoAnulla() {
		
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
                        	null,
                            "Errore, tentativo eliminazione FALLITO:\n" + ex.getMessage(),
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                 }
            	
	        }
            
        });
		
	}
	
	
	//----------------------------------------------------------------
	
    
    private void impostaLabel() {
    	
    	eliminaView.getCfLabel().setText("Codice fiscale cliente: " + coordinator.getDTO().getCodiceFiscale());
    	
    };
    
    
	//----------------------------------------------------------------

}
