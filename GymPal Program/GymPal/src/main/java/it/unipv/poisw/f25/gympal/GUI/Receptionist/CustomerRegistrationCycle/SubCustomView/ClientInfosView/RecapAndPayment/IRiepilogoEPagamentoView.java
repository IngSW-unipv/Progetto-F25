package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment;

import java.awt.event.ActionListener;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.DTO.AbbonamentoDTO;

public interface IRiepilogoEPagamentoView {
	
	/* Metodi per inizializzare la view con i dati dell'abbonamento */
	public void setDatiAbbonamento(AbbonamentoDTO abbonamentoDTO);
	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	/* Metodo per aggiornare il prezzo totale nella view */
	public void setPrezzoTotale(double prezzo);
	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	/* Metodi per aggiungere listener agli eventi */
	public void addIndietroListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addAnnullaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addConfermaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addScontoSuBaseMesiListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addMensilitaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addScontoEtaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addScontoOccasioniListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addMetodoPagamentoListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addAvvioPagamentoListener(ActionListener listener);
	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	/* Metodi per interrogare lo stato della view (checkbox, radio, ecc) */
	public boolean isScontoEtaSelected();
	
	//----------------------------------------------------------------
	
	public boolean isScontoOccasioniSelected();
	
	//----------------------------------------------------------------
	
	public String getDurataSelezionata();
	
	//----------------------------------------------------------------
	
	public boolean isContantiSelected();
	
	//----------------------------------------------------------------
	
	public boolean isCartaSelected();
	
	//----------------------------------------------------------------
	
	public boolean isNoPagamentoSelected();
	
	//----------------------------------------------------------------
	
	public String getScontoSuBaseMesiText();
	
	//----------------------------------------------------------------
	
	public void setScontoSuBaseMesiText(String text);
	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	/* Metodo per mostrare il popup menu dello sconto su base mesi */
	public void showPopupMenu();
	
	//----------------------------------------------------------------
}
