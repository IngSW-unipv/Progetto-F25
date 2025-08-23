package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.IStrategieCalcoloPrezzoFactory;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.AutEmployee.IAutenticaDipendente;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveClient.IRetrieveClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveOccasionsDiscounts.IRetrieveDiscountsFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.AddClient.ICommitNewClientToDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.RemoveClient.IDeleteClientFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.TowardsDB.UpdateClient.IUpdateClientInsideDB;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.StaffFactory;

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

}
