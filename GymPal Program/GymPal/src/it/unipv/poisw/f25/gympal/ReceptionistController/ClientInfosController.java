package it.unipv.poisw.f25.gympal.ReceptionistController;

import java.awt.Color;
import java.util.Date;

import javax.swing.JOptionPane;

import it.unipv.poisw.f25.gympal.GUI.ClientInfosView;
import it.unipv.poisw.f25.gympal.utility.EtaCliente;
import it.unipv.poisw.f25.gympal.utility.IRegexExpression;
import it.unipv.poisw.f25.gympal.utility.ValidazioneCampo;

public class ClientInfosController implements IRegexExpression {

	private ClientInfosView clientInfos;
	
	private Runnable onAvanti;
	private Runnable onIndietro;
	
	//----------------------------------------------------------------
	
	public ClientInfosController(ClientInfosView infos, Runnable onAvantiCallback,
														Runnable onIndietroCallback) {
		
		clientInfos = infos;
		
		onAvanti = onAvantiCallback;
		onIndietro = onIndietroCallback;
		
		impostaEventiTextFields();
		impostaControlloEta ();
		impostaEventoAvanti();
		impostaEventoIndietro();
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventiTextFields() {

		ValidazioneCampo.aggiungiValidatore(clientInfos.getNome(), NAME_REGEXEXPRESSION);
		
		ValidazioneCampo.aggiungiValidatore(clientInfos.getCognome(), SURNAME_REGEXEXPRESSION);
		
		ValidazioneCampo.aggiungiValidatore(clientInfos.getCodiceFiscale(), CODICE_FISCALE);

	}
	
	//----------------------------------------------------------------
	
	private void impostaControlloEta () {
		
		clientInfos.getDateSpinner().addChangeListener(e -> {
			
		    Date dataDiNascita = (Date) clientInfos.getDateSpinner().getValue();
		    
		    int eta = EtaCliente.calcolaEta(dataDiNascita);
		    
		    if (eta < 18) {
		    	
		        clientInfos.getPermessoGenitoriSi().setVisible(true);
		        clientInfos.getPermessoGenitoriNo().setVisible(true);
		        clientInfos.getPermessoGenitoriLabel().setVisible(true);
		        
		    } else {
		    	
		        clientInfos.getPermessoGenitoriSi().setVisible(false);
		        clientInfos.getPermessoGenitoriNo().setVisible(false);
		        clientInfos.getPermessoGenitoriLabel().setVisible(false);
		    }
		    
		    clientInfos.revalidate();
		    
		    clientInfos.repaint();
		    
		});

		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoAvanti() {
		
	    clientInfos.getAvantiButton().addActionListener(e -> {
	        
	        // Controllo validità globale
	    	
	        boolean validitaCampi = true;
	        
	        
	        // Syso per verificare lo stato dei campi
	        
	        System.out.println("Nome background: " + clientInfos.getNome().getBackground());
	        System.out.println("Cognome background: " + clientInfos.getCognome().getBackground());
	        System.out.println("Codice Fiscale background: " + clientInfos.getCodiceFiscale().getBackground());

	        
	        // Estrazione valori RGB per ogni campo
	        
	        Color nomeColor = clientInfos.getNome().getBackground();
	        Color cognomeColor = clientInfos.getCognome().getBackground();
	        Color codiceFiscaleColor = clientInfos.getCodiceFiscale().getBackground();

	        
	        // E' controllato che i colori siano uguali a #b2fab4 (r=178, g=250, b=180).
	        /*Bisogna confrontare i singoli valori RGB perché quando sono confrontati i colori
	         *con il metodo getBackground(), è possibile che ci siano piccole discrepanze nel
	         *modo in cui il colore viene gestito (come la trasparenza o altre proprietà 
	         *interne)*/
	        
	        /*Ho scelto di percorrere questa strada per non creare ridondanza con il controllo
	         *eseguito nel metodo "impostaEventiTextFields()"*/
	        
	        if (nomeColor.getRed() != 178 || nomeColor.getGreen() != 250 || nomeColor.getBlue() != 180 ||
	            cognomeColor.getRed() != 178 || cognomeColor.getGreen() != 250 || cognomeColor.getBlue() != 180 ||
	            codiceFiscaleColor.getRed() != 178 || codiceFiscaleColor.getGreen() != 250 || codiceFiscaleColor.getBlue() != 180) {
	        	
	            validitaCampi = false;
	            
	        }
	        

	        /* Controllo RadioButtons: è verificato che i radio per Certificato di Idoneità e Permesso Genitori
	         * siano su "Si"*/
	        
	        System.out.println("Certificato di Idoneità: " + clientInfos.getCertIdoneitàSi().isSelected());
	        System.out.println("Permesso Genitori: " + clientInfos.getPermessoGenitoriSi().isSelected());

	        if (!clientInfos.getCertIdoneitàSi().isSelected() || 
	        	!clientInfos.getPermessoGenitoriSi().isSelected()) {
	        	
	            validitaCampi = false;
	            
	        }

	        
	        // Se tutto è valido, procedi con l'azione "Avanti"
	        
	        if (validitaCampi) {
	        	
	            onAvanti.run();
	            
	        } else {
	        	
	            // Mostra un messaggio di errore se uno dei campi è invalido
	        	
	        	/*"clientInfos" è il componente genitore della finestra di dialogo, dunque il
	        	 * dialogo sarà disegnato centrato rispetto a "clientInfos" (se invece vi fosse
	        	 * "null", allora il dialogo sarebbe centrato rispetto allo schermo).
	        	 * 
	        	 * "Errore" è il titolo della finestra di dialogo.
	        	 * 
	        	 * "JOptionPane.ERROR_MESSAGE" èil tipo di messaggio, che determina l’icona
	        	 * da mostrare accanto al testo
	        	 * */
	        	
	            JOptionPane.showMessageDialog(clientInfos, "Per favore, compila correttamente "
	            					  + "tutti i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
	            
	        }
	        
	    });
	    
	}


	
	//----------------------------------------------------------------
	
	private void impostaEventoIndietro() {
		
	    clientInfos.getIndietroButton().addActionListener(e -> {
	        
	    	onIndietro.run();
	        
	    });
	    
	}		
		
	
	
}
