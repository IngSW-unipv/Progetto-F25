package it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.CampoValidabileFactory;

import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.CampoValidabile.ICampoValidabile;

public interface ICampoValidabileFactory {
	
	public ICampoValidabile creaCampoValidabile(JTextField field, String regex);

}
