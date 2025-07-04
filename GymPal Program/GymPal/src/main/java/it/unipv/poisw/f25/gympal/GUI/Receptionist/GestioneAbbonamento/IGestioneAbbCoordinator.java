package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.IUtenteAbbDTO;

public interface IGestioneAbbCoordinator {
	
	public void acquisisciCfCliente(String Cf);
	
    //----------------------------------------------------------------
	
	public ICampoValidabileFactory getCampoValidabileFactory();
	
    //----------------------------------------------------------------
	
	public IValidatoreCampi getValidatoreCampi();
	
    //----------------------------------------------------------------
	
	public IUtenteAbbDTO getUtenteAbbDTO();
	
    //----------------------------------------------------------------

}
