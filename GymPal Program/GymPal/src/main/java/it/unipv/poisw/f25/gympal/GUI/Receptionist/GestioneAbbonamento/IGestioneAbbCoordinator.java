package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento;

import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;

public interface IGestioneAbbCoordinator {
	
	public void acquisisciCfCliente(String Cf);
	
    //----------------------------------------------------------------
	
	public ICampoValidabileFactory getCampoValidabileFactory();
	
    //----------------------------------------------------------------
	
	public IValidatoreCampi getValidatoreCampi();
	
    //----------------------------------------------------------------
	
	public IDatiCliente getDTO();
	
    //----------------------------------------------------------------
	
    public void acquisisciComponentiAbbonamento(List<String> sezioniSelezionate,
											    List<String> corsiSelezionati);
    
    //----------------------------------------------------------------

}
