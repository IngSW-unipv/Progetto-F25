package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.IReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioSettimanaleController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioSettimanaleView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ICalendarioSettimanaleView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.DatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.DTOHandlerCalendarioSettimanale;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.Handlers.SecondaryHandlers.TurniHandler;
import it.unipv.poisw.f25.gympal.GUI.Utilities.SimulazioneOperazione;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;

public class GestioneCalendarioCoordinator implements ICoordinatoreCalendario {

	/**/
	private IReceptionistController viewHandler;
	
	/*Viste*/
	private ICalendarioSettimanaleView calendarioView;
	
	/*Servizi*/
    private final ICalendarioService calendarioService;
    
    /*DTO*/
    private List<DatiCellaCalendarioDTO> settimanaDTOs;

    //----------------------------------------------------------------
    
    public GestioneCalendarioCoordinator(IReceptionistController viewHandler, 
            							 ICalendarioService calendarioService) {
    	
    		this.viewHandler = viewHandler;
    		this.calendarioService = calendarioService;
    		
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
            	
                List<DatiCellaCalendarioDTO> lista = new ArrayList<>();
                
                LocalDate lunedi = LocalDate.now().with(DayOfWeek.MONDAY);
                int totaleCelle = 7 * 13 * 2;
                int count = 0;

                for (int giorno = 0; giorno < 7; giorno++) {
                	
                    LocalDate data = lunedi.plusDays(giorno);

                    for (int ora = 8; ora <= 20; ora++) {
                    	
                        for (int minuti : new int[]{0, 30}) {
                        	
                            DatiCellaCalendarioDTO dto = new DatiCellaCalendarioDTO(
                                data, ora, minuti,
                                new ArrayList<>(), 
                                new ArrayList<>(), 
                                new ArrayList<>()
                            );

                            new DTOHandlerCalendarioSettimanale(dto,calendarioService)
                                 							.popolaDatiCella(minuti);

                            lista.add(dto);

                            count++;
                            publish((int) ((count / (double) totaleCelle) * 100));
                            
                        }
                        
                    }
                    
                }
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
    
    private void setupSchermataCalendario() {
    	
    	calendarioView = new CalendarioSettimanaleView(new DynamicButtonSizeSetter());
    	viewHandler.registraSchermata("CALENDARIO_VIEW", calendarioView.getMainPanel());
    	
    	new CalendarioSettimanaleController(calendarioView, this);
    	
    }    
    
    //----------------------------------------------------------------    

    @Override
    public IDatiCellaCalendarioDTO getContenutoCella(LocalDate data, int ora, int minuti) {
    	
    	

        DatiCellaCalendarioDTO dto = settimanaDTOs.stream()
            .filter(d -> d.getData().equals(data) && d.getOra() == ora && d.getMinuti() == minuti)
            .findFirst()
            .orElse(new DatiCellaCalendarioDTO(data, ora, minuti,
                                               new ArrayList<>(),
                                               new ArrayList<>(),
                                               new ArrayList<>()));

        aggiornaDatiCella(dto); 
        return dto;
        
    }




    //----------------------------------------------------------------

    @Override
    public boolean verificaDisponibilita(LocalDate giorno, int ora) {
    	
        /*Creazione handler "al volo" con DTO fittizio, solo per verifica
         *disponibilità uso metodo statico*/
        TurniHandler turniHandler = new TurniHandler(calendarioService);
        return turniHandler.esistePersonaleDisponibile(giorno, ora);
        
    }

    //----------------------------------------------------------------

    /* Metodo pubblico per aggiornare i dati di una cella, caricare info turni, 
     * corsi, eventi e appuntamenti*/
    @Override
    public void aggiornaDatiCella(IDatiCellaCalendarioDTO dto) {
    	
        // Istanziazione handler "al volo" e passaggio DTO + servizio
        /*new TurniHandler(dto, calendarioService).caricaTurniPerDataEOra();
        new CorsiHandler(dto, calendarioService).caricaCorsiPerDataEOraEMinuti(dto.getMinuti());
        new EventiHandler(dto, calendarioService).caricaEventiPerDataEOra();
        new AppuntamentiHandler(dto, calendarioService).caricaAppuntamentiPerDataEOraEMinuti(dto.getMinuti());*/
       
        new DTOHandlerCalendarioSettimanale(dto, calendarioService).popolaDatiCella(dto.getMinuti());
        
    }
    

    //----------------------------------------------------------------
}
