package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces;

import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.IRetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public interface ICoordinator {
	
	/*Contratto per coordinatori che fanno uso della schermata di riepilogo e pagamento*/
	
	public IDatiCliente getDTO();
	
    //----------------------------------------------------------------
	
	public void acquisisciMetodoPagamento (MetodoPagamento metodoPagamento);
	
    //----------------------------------------------------------------
    
    public void acquisisciScontoEta(boolean scontoEta);
    
    /*Per flag attivazione sconti occasioni*/
    public void acquisisciScontoOccasioni(boolean scontoOccasioni);
    
    public void acquisisciDurataAbbonamento(DurataAbbonamento durataAbbonamento);
    
    //----------------------------------------------------------------
    
    /*Acquisisce la selezione di sconti da GUI*/
    public void acquisisciScontiOccasioneSelezionati(List<Sconto> scontiOccasioneSelezionati);
    
    //----------------------------------------------------------------
    
    public double getDiscountedPrice();
    
    //----------------------------------------------------------------
    
    public IRetrieveDiscountsFromDB getScontiOccasioni();
    
    //----------------------------------------------------------------
    
    public String esponiDurataAbbonamento();
    
    //----------------------------------------------------------------

}
