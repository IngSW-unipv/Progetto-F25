package it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;

public interface ICoordinator {
	
	/*Contratto per coordinatori che fanno uso della schermata di riepilogo e pagamento*/
	
	public IDatiCliente getDTO();
	
    //----------------------------------------------------------------
	
	public void acquisisciMetodoPagamento (MetodoPagamento metodoPagamento);
	
    //----------------------------------------------------------------
	
    public void acquisisciScontiEDurata(boolean scontoEta, boolean scontoOccasioni,
										DurataAbbonamento durataAbbonamento);
    
    //----------------------------------------------------------------
    
    public double getDiscountedPrice(ICalcolaPrezzo abbonamentoDTO);
    
    //----------------------------------------------------------------

}
