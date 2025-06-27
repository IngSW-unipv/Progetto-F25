package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabile;

import javax.swing.JTextField;

public interface ICampoValidabile {
	
	public boolean isValido();
	
	//----------------------------------------------------------------
	
	public JTextField getField();
	
	//----------------------------------------------------------------

}
