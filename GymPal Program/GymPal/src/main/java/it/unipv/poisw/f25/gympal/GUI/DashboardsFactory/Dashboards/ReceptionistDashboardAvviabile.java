package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.ApplicationLayer.CalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveDipendenti.RetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.RegistrationServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.CommonServicesBundle;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistDashboardView;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;

public class ReceptionistDashboardAvviabile implements IDashboardAvviabile{
	
	
    public ReceptionistDashboardAvviabile() {}
    
    //----------------------------------------------------------------

    @Override
    public void avvia() {
    	
    	/*Vista*/
        ReceptionistDashboardView recDashView = new ReceptionistDashboardView();
        
        /*Servizi*/
        IPersistenceFacade persistence = PersistenceFacade.getInstance();
        
        RegistrationServicesBundle serviziReg = new RegistrationServicesBundle();
        CommonServicesBundle serviziComuni = new CommonServicesBundle();
        
        ICalendarioService calendarioService = new CalendarioService();
        IRetrieveDipendentiFromDB listaDip = new RetrieveDipendentiFromDB(persistence);
        ICalendarioFacadeService calendarioFacade = new CalendarioFacadeService(
        											   calendarioService,
        											   listaDip);
        
        new ReceptionistController(recDashView, 
        						   serviziReg, 
        						   serviziComuni, 
        						   calendarioFacade);
        
        recDashView.setVisible(true);
        
    }
    
    //----------------------------------------------------------------

}
