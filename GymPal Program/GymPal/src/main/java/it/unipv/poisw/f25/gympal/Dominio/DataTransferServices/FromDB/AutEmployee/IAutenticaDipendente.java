package it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.AutEmployee;

public interface IAutenticaDipendente {
	
	public boolean autenticazione(String nome, String cognome, String staffId);

}
