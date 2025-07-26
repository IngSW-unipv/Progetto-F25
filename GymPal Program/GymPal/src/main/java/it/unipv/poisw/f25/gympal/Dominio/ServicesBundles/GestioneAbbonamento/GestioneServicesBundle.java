package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneAbbonamento;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.RetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient.DeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.UpdateClient.UpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;

public class GestioneServicesBundle {
	
    private IPersistenceFacade facade;
    private IRetrieveClientFromDB veicoloDati;
    private IDeleteClientFromDB headHunter;
    private IUpdateClientInsideDB updateClient;
    
	//----------------------------------------------------------------
    
    public GestioneServicesBundle(){
        
        this.facade = PersistenceFacade.getInstance();
        this.veicoloDati = new RetrieveClientFromDB(facade);
        this.headHunter = new DeleteClientFromDB(facade);
        this.updateClient = new UpdateClientInsideDB(facade);
    	
    }
    
	//----------------------------------------------------------------
    
    public IRetrieveClientFromDB getVeicoloDati() {
    	
    	return veicoloDati;
    	
    }
    
   //----------------------------------------------------------------
    
    public IDeleteClientFromDB getHeadHunter() {
    	
    	return headHunter;
    	
    }
    
   //----------------------------------------------------------------
    
    public IUpdateClientInsideDB getUpdater() {
    	
    	return updateClient;
    	
    }
    
   //---------------------------------------------------------------- 

}
