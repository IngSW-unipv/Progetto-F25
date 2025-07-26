package it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;

public interface IDatiCliente {
	
	/*Contratto per i DTO che devono essere passati alla schermata di riepilogo e paga-
	 *mento.*/
	
	public String getNome();
	
    //----------------------------------------------------------------
	
	public String getCognome();
	
    //----------------------------------------------------------------
	
	public String getCodiceFiscale();
	
    //----------------------------------------------------------------
	
	public String getSesso();
	
    //----------------------------------------------------------------
	
	public String getContatto();
	
    //----------------------------------------------------------------
	
	public LocalDate getDataNascita();
	
    //----------------------------------------------------------------
	
	public boolean getCertificatoIdoneita();
	
    //----------------------------------------------------------------
	
	public boolean getPermessoGenitori();
	
    //----------------------------------------------------------------
	
	public List<String> getSezioniAbbonamento();
	
    //----------------------------------------------------------------
	
	public List<String> getCorsiSelezionati();
	
    //----------------------------------------------------------------
	
	public DurataAbbonamento getDurataAbbonamento();
	
    //----------------------------------------------------------------
	
	public boolean getStatoPagamento();
	
    //----------------------------------------------------------------

}
