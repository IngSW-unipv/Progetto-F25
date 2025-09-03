package it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni;

import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.ClasseDiSupporto.TurnoIndividuale;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VistaEControllore.IVisualizzaTurniView;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VistaEControllore.VisualizzaTurniController;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VistaEControllore.VisualizzaTurniView;

public class VisualizzaTurniCoordinator implements IVisualizzaTurniCoordinator{
	
	/*Controllore*/
	private IRegistraEMostraSchermate viewHandler;

    /*Lista Turni del Dipendente*/
    private List<TurnoIndividuale> listaTurni;
    
    /*Servizi*/
    private IFontChangeRegister changeRegister;
	
	//----------------------------------------------------------------
	
	public VisualizzaTurniCoordinator(IRegistraEMostraSchermate viewHandler,
									  List<TurnoIndividuale> listaTurni,
									  IFontChangeRegister changeRegister) {
		
		/*Controllore*/
		this.viewHandler = viewHandler;
		
		/*Lista Turni*/
		this.listaTurni = listaTurni;	
		
		/*Servizi*/
		this.changeRegister = changeRegister;
		
		/*Inizializzazione Schermate GUI*/
		setupSchermataTurni();
		
	}
	
	//----------------------------------------------------------------
	
	private void setupSchermataTurni() {
		
		IVisualizzaTurniView turniView = new VisualizzaTurniView(changeRegister);
		viewHandler.registraSchermata("TURNI", turniView.getMainPanel());
		
		new VisualizzaTurniController(turniView, this);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public List<TurnoIndividuale> passListaTurni(){
		
		return listaTurni;
		
	}
	
	//----------------------------------------------------------------

}
