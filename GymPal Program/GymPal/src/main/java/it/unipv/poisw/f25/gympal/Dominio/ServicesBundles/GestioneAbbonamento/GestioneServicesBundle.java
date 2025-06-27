package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneAbbonamento;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.FromDB.RetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient.DeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;

public class GestioneServicesBundle {
	
    private IPersistenceFacade facade;
    private IRetrieveClientFromDB veicoloDati;
    private IDeleteClientFromDB headHunter;
    
	//----------------------------------------------------------------
    
    public GestioneServicesBundle(){
        
        this.facade = PersistenceFacade.getInstance();
        this.veicoloDati = new RetrieveClientFromDB(facade);
        this.headHunter = new DeleteClientFromDB(facade);
    	
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

}
