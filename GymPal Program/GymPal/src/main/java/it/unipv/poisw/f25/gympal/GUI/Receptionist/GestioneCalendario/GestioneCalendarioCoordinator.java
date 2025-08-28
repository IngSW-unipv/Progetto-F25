package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioSettimanaleController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioSettimanaleView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ICalendarioChangeListener;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ICalendarioSettimanaleView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.DatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Caching.CalendarioSettimanaleCachesBuilder;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.DTOHandlerCalendarioSettimanale;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers.TurniHandler;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.Utilities.SimulateOps.SimulazioneOperazione;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class GestioneCalendarioCoordinator implements ICoordinatoreCalendario {

	/*Controllore*/
	private IRegistraEMostraSchermate viewHandler;
	
	/*PER CACHING INFO DA DATABASE*/
	private List<AppuntamentoPT> tuttiAppuntamenti;
	private Map<LocalDate, List<SessioneCorso>> corsiCache;
	private Map<LocalDate, List<Turno>> turniCache;
	private Map<LocalDate, List<Calendario>> eventiCache;
	
	/*Per implementare meccanismo Observable con vista calendario*/
	private List<ICalendarioChangeListener> listeners = new ArrayList<>();
	
	/*Servizi*/
    private ICalendarioFacadeService calendarioFacade;
    private IFasciaOrariaValidator fasciaValidator;
    private IFontChangeRegister changeRegister;
    private IDateUtils dateUtils;
    
    
    /*DTO*/
    private List<DatiCellaCalendarioDTO> settimanaDTOs;
  
    //----------------------------------------------------------------
    
    public GestioneCalendarioCoordinator(IRegistraEMostraSchermate viewHandler,
            							 ICalendarioFacadeService calendarioFacade,
            							 IFasciaOrariaValidator fasciaValidator,
            							 IDateUtils dateUtils,
            							 ICommonServicesBundle serviziComuni) {
    	
    		/*Controller*/
    		this.viewHandler = viewHandler;
    		
    		/*Servizi*/
    		this.calendarioFacade = calendarioFacade;
    		this.dateUtils = dateUtils;
    		this.changeRegister = serviziComuni.getFontChangeRegister();
    		this.fasciaValidator = fasciaValidator;
    		
    }

    //----------------------------------------------------------------
    
    private void setupSchermataCalendario() {
    	
    	ICalendarioSettimanaleView calendarioView = new CalendarioSettimanaleView(new DynamicButtonSizeSetter(),
    												changeRegister);
    	viewHandler.registraSchermata("CALENDARIO_VIEW", calendarioView.getMainPanel());
    	
    	new CalendarioSettimanaleController(calendarioView, 
    										calendarioFacade, 
    										dateUtils,
    										changeRegister,
    										fasciaValidator,
    										this);
    	
    }    
    
    //----------------------------------------------------------------
    
    /*Per modifiche al calendario su singola data*/
    @Override
    public void notificaCambio(LocalDate data) {
    	
        notificaCambio(Collections.singletonList(data));
        
    }
    
    /*Per modifiche al calendario su più date*/
    @Override
    public void notificaCambio(List<LocalDate> dateCambiate) {
    	
        for (ICalendarioChangeListener listener : listeners) {
        	
            listener.onCambioCalendario(dateCambiate);
            
        }
        
    }
    
    
    //----------------------------------------------------------------
    
    @Override
    public void inizializzaConBarra(JFrame framePadre, Runnable callbackFine) {
    	
        SimulazioneOperazione caricamentoDialog = 
        		new SimulazioneOperazione(framePadre, "Operazione in corso...");

        SwingWorker<List<DatiCellaCalendarioDTO>, Integer> worker =
        		new SwingWorker<List<DatiCellaCalendarioDTO>, Integer>() {

        	@Override
        	protected List<DatiCellaCalendarioDTO> doInBackground() {
        		
        		CalendarioSettimanaleCachesBuilder builder = 
        		new CalendarioSettimanaleCachesBuilder(calendarioFacade);
        	    List<DatiCellaCalendarioDTO> lista = builder.build();

        	    corsiCache = builder.getCorsiCache();
        	    turniCache = builder.getTurniCache();
        	    eventiCache = builder.getEventiCache();
        	    tuttiAppuntamenti = builder.getAppuntamenti();

        	    return lista;
        	    
        	}


            @Override
            protected void process(List<Integer> chunks) {
            	
                int ultimo = chunks.get(chunks.size() - 1);
                caricamentoDialog.aggiornaProgresso(ultimo);
                
            }

            @Override
            protected void done() {
            	
                try {
                	
                    settimanaDTOs = get();
                    
                } catch (Exception e) {
                	
                    e.printStackTrace(); 
                    
                }

                caricamentoDialog.dispose();
                setupSchermataCalendario();

                if (callbackFine != null) {
                	
                    callbackFine.run();
                    
                }
                
            }
            
        };

        worker.execute();
        caricamentoDialog.setVisible(true);
        
    }  
    
    //----------------------------------------------------------------

    @Override
    public IDatiCellaCalendarioDTO getContenutoCella(LocalDate data, 
    												 int ora, 
    												 int minuti) {    	

        DatiCellaCalendarioDTO dto = settimanaDTOs.stream()
            .filter(d -> d.getData().equals(data) 
            		  && d.getOra() == ora 
            		  && d.getMinuti() == minuti)
            .findFirst()
            .orElse(new DatiCellaCalendarioDTO(data, ora, minuti,
                                               new ArrayList<>(),
                                               new ArrayList<>(),
                                               new ArrayList<>(),
                                               new ArrayList<>()));

        aggiornaDatiCella(dto); 
        return dto;
        
    }

    //----------------------------------------------------------------

    @Override
    public boolean verificaDisponibilita(LocalDate giorno, int ora) {

        TurniHandler turniHandler = new TurniHandler(calendarioFacade);
        return turniHandler.esistePersonaleDisponibile(giorno, ora);
        
    }

    //----------------------------------------------------------------

    /* Metodo pubblico per aggiornare i dati di una cella, caricare info turni, 
     * corsi, eventi e appuntamenti*/
    @Override
    public void aggiornaDatiCella(IDatiCellaCalendarioDTO dto) {
       
    	/*Forzatura aggiornamento da DB*/
    	corsiCache.remove(dto.getData());
    	
        new DTOHandlerCalendarioSettimanale(dto, calendarioFacade, 
        									tuttiAppuntamenti, corsiCache,
        									turniCache, eventiCache)
        								    .popolaDatiCella(dto.getMinuti());
        				                    
        
    }
    

    //----------------------------------------------------------------
    
    @Override
    public void addCalendarioChangeListener(ICalendarioChangeListener listener) {
    	
    	listeners.add(listener);
    	
    }
    
    @Override
    public void removeCalendarioChangeListener(ICalendarioChangeListener listener) {
    	
    	listeners.remove(listener);
    	
    }
    
    //----------------------------------------------------------------
   

}
