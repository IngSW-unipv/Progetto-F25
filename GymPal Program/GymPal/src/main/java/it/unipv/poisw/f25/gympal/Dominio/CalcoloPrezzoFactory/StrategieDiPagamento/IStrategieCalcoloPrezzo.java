package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento;


import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IRiepilogoDTO;

public interface IStrategieCalcoloPrezzo {
	
	double calcolaPrezzo(IRiepilogoDTO abbonamento);

}
