package it.unipv.poisw.f25.gympal.Dominio.ControlloRequisitiAnagrafica;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;

public class CtrlReqAnagraficiService implements ICtrlReqAnagraficiService {
	
	private ICalcoloEtaService etaService;
	
	//----------------------------------------------------------------
	
	public CtrlReqAnagraficiService(ICalcoloEtaService etaService) {
		
		this.etaService = etaService;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public boolean richiediCertificato() {
		
		return true;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public boolean richiediPermessoGenitori(LocalDate dataNascita) {
		
		return etaService.isMinorenne(dataNascita);
		
	}
	
	//----------------------------------------------------------------

}
