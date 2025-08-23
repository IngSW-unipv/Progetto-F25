package it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni;

import java.util.List;

import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.Supporto.TurnoIndividuale;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VistaEControllore.IVisualizzaTurniView;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VistaEControllore.VisualizzaTurniController;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VistaEControllore.VisualizzaTurniView;

public class VisualizzaTurniCoordinator implements IVisualizzaTurniCoordinator{
	
	/*Controllore*/
	private IRegistraEMostraSchermate viewHandler;
	
	/*Viste*/
	private IVisualizzaTurniView turniView;
	
	/*Servizi*/
	
	
    /*Lista Turni del Dipendente*/
    private List<TurnoIndividuale> listaTurni;
	
	//----------------------------------------------------------------
	
	public VisualizzaTurniCoordinator(IRegistraEMostraSchermate viewHandler,
									  List<TurnoIndividuale> listaTurni) {
		
		/*Controllore*/
		this.viewHandler = viewHandler;
		
		/*Lista Turni*/
		this.listaTurni = listaTurni;		
		
		/*Inizializzazione Schermate GUI*/
		setupSchermataTurni();
		
	}
	
	//----------------------------------------------------------------
	
	private void setupSchermataTurni() {
		
		turniView = new VisualizzaTurniView();
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
