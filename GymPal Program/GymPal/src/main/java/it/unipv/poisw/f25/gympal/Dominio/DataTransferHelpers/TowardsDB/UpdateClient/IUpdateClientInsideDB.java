package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.UpdateClient;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.DTO.IUtenteAbbDTO;

public interface IUpdateClientInsideDB {
	
	public boolean update(IUtenteAbbDTO abbDTO);

}
