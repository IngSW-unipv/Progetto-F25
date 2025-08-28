package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GeneratoreStaffID;

public interface IStaffIdGeneratorService {
	
	public String generaStaffId(String nome, String cognome, 
								String ruolo, String citta);

}
