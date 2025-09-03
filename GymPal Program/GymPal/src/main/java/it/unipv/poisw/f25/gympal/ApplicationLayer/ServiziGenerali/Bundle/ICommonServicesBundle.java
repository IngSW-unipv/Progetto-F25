package it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle;

import it.unipv.poisw.f25.gympal.ApplicationLayer.Autenticazione.IAutenticaDipendente;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveClient.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.IRetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.FontManager.IFontManager;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.GUI.LoginELogout.LoginScreen.LoginUtilities.StaffFactory;

public interface ICommonServicesBundle {
	
	public IRegexCheck getRegexChecker();
	
	//----------------------------------------------------------------
	
	public ICampoValidabileFactory getCampoValidabileFactory();
	
	//----------------------------------------------------------------
	
	public IValidatoreCampi getValidatoreCampi();
	
	//----------------------------------------------------------------
	
	public IStrategieCalcoloPrezzoFactory getPrezzoFactory();
	
	//----------------------------------------------------------------
	
	public IRetrieveClientFromDB getRecuperaDati();
	
	//----------------------------------------------------------------
	
	public IDeleteClientFromDB getHeadHunter();
	
	//----------------------------------------------------------------
	
	public IUpdateClientInsideDB getUpdater();
	
	//----------------------------------------------------------------
	
	public ICommitNewClientToDB getImmettiDati();
	
	//----------------------------------------------------------------
	
	public IRetrieveDiscountsFromDB getDiscounts();
	
	//----------------------------------------------------------------
	
	public IAutenticaDipendente getAutDipendente();
	
	//----------------------------------------------------------------
	
	public StaffFactory getStaffFactory();
	
	//----------------------------------------------------------------
	
	public IFontManager getFontManager();
	
	//----------------------------------------------------------------
	
	public IFontChangeRegister getFontChangeRegister();
	
	//----------------------------------------------------------------

}
