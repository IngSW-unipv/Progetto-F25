package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica;

import java.time.LocalDate;

public interface ICtrlReqAnagraficiService {
	
	public boolean richiediCertificato();
	
    //----------------------------------------------------------------
	
	public boolean richiediPermessoGenitori(LocalDate dataNascita);
	
    //----------------------------------------------------------------

}
