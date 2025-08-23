package it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VistaEControllore;

import java.util.List;

import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.Supporto.TurnoIndividuale;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.IVisualizzaTurniCoordinator;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VistaEControllore.CustomTableModelPerView.TurniPersonaliTableModel;

public class VisualizzaTurniController {
	
	/*Coordinatore*/
	private IVisualizzaTurniCoordinator coordinator;
	
	/*Viste*/
	private IVisualizzaTurniView turniView;
	
    //------------------------------------------------------------
	
	public VisualizzaTurniController(IVisualizzaTurniView turniView,
									 IVisualizzaTurniCoordinator coordinator) {
		
		/*Vista*/
		this.turniView = turniView;
		
		/*Coordinatore*/
		this.coordinator = coordinator;
		
		/*Setup Tabella Turni*/
		refreshTabellaTurni();
		
	}
	
    //------------------------------------------------------------
	
	public void refreshTabellaTurni() {
		
		List<TurnoIndividuale> turni = coordinator.passListaTurni();

		TurniPersonaliTableModel model = new TurniPersonaliTableModel(turni);
	    turniView.getTurniTable().setModel(model);
	    
	}
	
    //------------------------------------------------------------
	
    //------------------------------------------------------------

}
