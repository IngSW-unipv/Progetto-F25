package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica;

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
	
	@Override
	public boolean isCertificatoValido(boolean selezioneCertificato) {
		
	    return !richiediCertificato() || selezioneCertificato;
	    
	}

	
	//----------------------------------------------------------------
	
	@Override
	public boolean richiediPermessoGenitori(LocalDate dataNascita) {
		
		return etaService.isMinorenne(dataNascita);
		
	}
	
	@Override
	public boolean isPermessoGenitoriValido(LocalDate dataNascita, boolean selezionePermesso) {
		
	    return !richiediPermessoGenitori(dataNascita) || selezionePermesso;
	    
	}

	
	//----------------------------------------------------------------

}
