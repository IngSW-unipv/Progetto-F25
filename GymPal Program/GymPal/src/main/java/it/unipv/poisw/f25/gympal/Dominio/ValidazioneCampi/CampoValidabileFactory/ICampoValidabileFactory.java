package it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.CampoValidabileFactory;

import javax.swing.JTextField;


import it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.CampoValidabile.ICampoValidabile;

public interface ICampoValidabileFactory {
	
	public ICampoValidabile creaCampoValidabile(JTextField field, String regex);

}
