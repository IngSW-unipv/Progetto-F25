package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.RemoveClient;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.IUtenteAbbDTO;

public interface IDeleteClientFromDB {
	
	public boolean huntAndKill(IUtenteAbbDTO abbDTO);

}
