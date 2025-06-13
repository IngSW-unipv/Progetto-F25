package it.unipv.poisw.f25.gympal.StrategieDiPagamento;


import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IRiepilogoDTO;

public interface IStrategieCalcoloPrezzo {
	
	double calcolaPrezzo(IRiepilogoDTO abbonamento);

}
