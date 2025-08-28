package it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory;

import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabile.ICampoValidabile;

public interface ICampoValidabileFactory {
	
	public ICampoValidabile creaCampoValidabile(JTextField field, String regex);

}
