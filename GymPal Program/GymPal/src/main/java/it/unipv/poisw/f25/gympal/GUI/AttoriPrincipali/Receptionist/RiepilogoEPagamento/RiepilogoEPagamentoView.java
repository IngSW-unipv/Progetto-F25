package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.RiepilogoEPagamento;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.MasterDTOEBuilder.IDatiClienteReadOnly;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class RiepilogoEPagamentoView extends JPanel implements IRiepilogoEPagamentoView{

	private static final long serialVersionUID = 1L;
	
	/*Split Panes*/
	private JSplitPane mainSplitPanel;
	private JSplitPane mainBottomSplitPanel;
	private JSplitPane secondSplitPanel; //Il pannello superiore coincide con "mainSplitPanel"
	
	/*Pannelli*/
	private JPanel mainUpperPanel;
	private JPanel mainBottomLeftPanel; //Qui finisce il riepilogo
	private JPanel mainBottomRightPanel; //Opzioni per la factory che calcola i prezzi
	private JPanel navigationPanel; //Qui finisce il pannello con il bottone "Intrietro"
	
	/*Opzioni di sconto*/
	private JCheckBox scontoEtaCheckBox;
	private JCheckBox scontoOccasioniCheckBox;
	private JButton scontoOccasioniBtn;
	
	/*Opzioni di pagamento*/
	private JRadioButton carta;
	private JRadioButton contanti;
	private JRadioButton noPagamento;
	
	private JButton scontoSuBaseMesi;
	private JButton indietro;
	private JButton conferma;
	private JButton annulla;
	private JButton avvioPagamento;
	
	/*Durata Abbonamento*/
	private JRadioButtonMenuItem trimestrale;
	private JRadioButtonMenuItem semestrale;
	private JRadioButtonMenuItem annuale;
	private JRadioButtonMenuItem mensile;
	
	private JPopupMenu popupMenu;
	private ButtonGroup sceltaMesi;
	private ButtonGroup modalitaPagamento;
	
	private JLabel prezzoTotaleLabel;
	
	/*Servizi*/
	private final IDynamicButtonSizeSetter buttonSizeSetter;
	
    //----------------------------------------------------------------
	
	public RiepilogoEPagamentoView(IDynamicButtonSizeSetter setter,
								   IFontChangeRegister fontChangeRegister) {
		

		/*Servizi*/
        this.buttonSizeSetter = setter;
        
        /*Layout Pannello Principale*/
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        /*Costruzione contenuto pannello*/
        initComponents();
        buildMainUpperPanel();
        buildMainBottomPanels();
        buildNavigationPanel();
        buildSecondSplitPanel();

        add(secondSplitPanel);

        /*Sottoscrizione a meccanismo cambio-font*/
        fontChangeRegister.register(this, buttonSizeSetter);
    }

    //----------------------------------------------------------------

	// Inizializzazione componenti vista
    private void initComponents() {
        
        mainUpperPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        mainUpperPanel.setBorder(BorderFactory.createCompoundBorder(
        	    		
        	            BorderFactory.createTitledBorder("Riepilogo Anagrafica:"),
        	            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        	            
        	        )
        	    );

        mainSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        modalitaPagamento = new ButtonGroup();
        sceltaMesi = new ButtonGroup();

        // Bottom left panel (pagamento)
        mainBottomLeftPanel = new JPanel(new BorderLayout(10, 10));

        
        mainBottomLeftPanel.setBorder(BorderFactory.createCompoundBorder(
	  			 					  BorderFactory.createTitledBorder("Sezione "
	  			 					  + "pagamento: selezionare una opzione per"
	  			 					  + " sbloccare 'Conferma'"),
	  			 					  BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        prezzoTotaleLabel = new JLabel("Totale: € 0.00", JLabel.CENTER);
        prezzoTotaleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        avvioPagamento = new JButton("Avvio Pagamento");

        carta = new JRadioButton(" - Carta di credito / Bancomat");
        contanti = new JRadioButton(" - Contanti");
        noPagamento = new JRadioButton(" - No pagamento (abbonamento provvisorio - 30gg)");

        modalitaPagamento.add(carta);
        modalitaPagamento.add(contanti);
        modalitaPagamento.add(noPagamento);

        // Bottom right panel (sconti)
        mainBottomRightPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        mainBottomRightPanel.setBorder(BorderFactory.createTitledBorder("Sezione sconti:"));

        scontoEtaCheckBox = new JCheckBox("Applica sconto età");
        scontoOccasioniCheckBox = new JCheckBox("Applica sconto occasioni");
        scontoOccasioniBtn = new JButton("Seleziona sconto occasioni");
        scontoOccasioniBtn.setEnabled(false);

        trimestrale = new JRadioButtonMenuItem("Trimestrale");
        semestrale = new JRadioButtonMenuItem("Semestrale");
        annuale = new JRadioButtonMenuItem("Annuale");
        mensile = new JRadioButtonMenuItem("Mensile");

        sceltaMesi.add(mensile);
        sceltaMesi.add(trimestrale);
        sceltaMesi.add(semestrale);
        sceltaMesi.add(annuale);
        mensile.setSelected(true);

        popupMenu = new JPopupMenu();
        popupMenu.add(mensile);
        popupMenu.add(trimestrale);
        popupMenu.add(semestrale);
        popupMenu.add(annuale);

        scontoSuBaseMesi = new JButton("Numero mesi");

        // Navigation panel buttons
        indietro = new JButton("Indietro");
        conferma = new JButton("Conferma");
        annulla = new JButton("Annulla");

        buttonSizeSetter.uniformButtonSize(scontoOccasioniBtn, 
        								   indietro, 
        								   conferma, 
        								   annulla);
        
    }
    
    //----------------------------------------------------------------

    private void buildMainUpperPanel() {
    	
        JPanel upperPanelContainer = new JPanel(new BorderLayout());
        upperPanelContainer.add(mainUpperPanel, BorderLayout.CENTER);
        mainSplitPanel.setTopComponent(upperPanelContainer);
        
    }
    
    //----------------------------------------------------------------

    // Costruzione mainBottomLeftPanel
    private void buildMainBottomPanels() {
    	
        JPanel sceltaModalitaPagamento = new JPanel();
        sceltaModalitaPagamento.setLayout(new BoxLayout(sceltaModalitaPagamento, BoxLayout.Y_AXIS));
        sceltaModalitaPagamento.add(carta);
        sceltaModalitaPagamento.add(Box.createVerticalStrut(5));
        sceltaModalitaPagamento.add(contanti);
        sceltaModalitaPagamento.add(Box.createVerticalStrut(5));
        sceltaModalitaPagamento.add(noPagamento);
        sceltaModalitaPagamento.add(Box.createVerticalStrut(5));

        mainBottomLeftPanel.add(prezzoTotaleLabel, BorderLayout.NORTH);
        mainBottomLeftPanel.add(sceltaModalitaPagamento, BorderLayout.WEST);
        mainBottomLeftPanel.add(avvioPagamento, BorderLayout.SOUTH);

        // Costruzione mainBottomRightPanel
        mainBottomRightPanel.add(scontoEtaCheckBox);

        JPanel scontoOccasioniPanel = new JPanel();
        scontoOccasioniPanel.setLayout(new BoxLayout(scontoOccasioniPanel, BoxLayout.Y_AXIS));
        scontoOccasioniPanel.add(scontoOccasioniCheckBox);
        scontoOccasioniPanel.add(Box.createVerticalStrut(10));
        scontoOccasioniPanel.add(scontoOccasioniBtn);
        mainBottomRightPanel.add(scontoOccasioniPanel);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        mainBottomRightPanel.add(separator);

        JLabel durataLabel = new JLabel("Sceglie durata abbonamento (applica sconto base): ");
        durataLabel.setAlignmentX(CENTER_ALIGNMENT);
        mainBottomRightPanel.add(durataLabel);

        mainBottomRightPanel.add(scontoSuBaseMesi);

        // Split panel per la parte inferiore
        mainBottomSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainBottomSplitPanel.setLeftComponent(mainBottomRightPanel);
        mainBottomSplitPanel.setRightComponent(mainBottomLeftPanel);

        SwingUtilities.invokeLater(() -> mainBottomSplitPanel.setDividerLocation(0.5));
        mainBottomSplitPanel.setEnabled(false);

        mainSplitPanel.setBottomComponent(mainBottomSplitPanel);
        
    }
    
    //----------------------------------------------------------------

    private void buildNavigationPanel() {
    	
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setBorder(BorderFactory.createTitledBorder(""));

        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(annulla);
        navigationPanel.add(Box.createHorizontalStrut(100));
        navigationPanel.add(indietro);
        navigationPanel.add(Box.createHorizontalStrut(100));
        navigationPanel.add(conferma);
        navigationPanel.add(Box.createHorizontalGlue());
        
    }
    
    //----------------------------------------------------------------

    private void buildSecondSplitPanel() {
    	
        secondSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        secondSplitPanel.setTopComponent(mainSplitPanel);
        secondSplitPanel.setBottomComponent(navigationPanel);

        SwingUtilities.invokeLater(() -> secondSplitPanel.setDividerLocation(0.8));
        secondSplitPanel.setEnabled(false);
        
    }

	
    //----------------------------------------------------------------
	
	private void labeledEntry (JPanel panel, String label, String campoDTO ) { 
		
		panel.add(new JLabel (label));
		panel.add(new JLabel (campoDTO));
		
	}
	
	//----------------------------------------------------------------
	
	private JScrollPane listaAScorrimento(List<String> selezione) {
			
			/*La parte "selezione.toArray(new String[0])" converte la "List<String> selezione"
			 *in un array di tipo "String[]", il formato richiesto dal costruttore di "JList"*/
			
			/*"new String[0]" è un'array di appoggio che consente al metodo "toArray" di sapere
			 *il tipo. Alla fine è restituito un array avente tanti elementi quanti sono gli
			 *elementi della lista convertita.*/
			
		JList<String> lista = new JList<>(selezione != null ? selezione.toArray(new String[0]) : new String[0]);
		lista.setEnabled(false); //Listan NON modificabile
			
		return new JScrollPane(lista);
			
	}
	
	//----------------------------------------------------------------
	
	private void labeledList (JPanel panel, String label, List<String> campoDTO ) {
		
		panel.add(new JLabel (label));
		panel.add(listaAScorrimento(campoDTO));
		
	}
	
	//----------------------------------------------------------------
	
	  @Override
	  public void setDatiAbbonamento(IDatiClienteReadOnly abbDTO) {

	        mainUpperPanel.removeAll();

	        labeledEntry(mainUpperPanel, "Nome: ", abbDTO.getNome());
	        labeledEntry(mainUpperPanel, "Cognome: ", abbDTO.getCognome());
	        labeledEntry(mainUpperPanel, "Codice Fiscale: ", abbDTO.getCodiceFiscale());
	        labeledEntry(mainUpperPanel, "Sesso: ", abbDTO.getSesso());
	        labeledEntry(mainUpperPanel, "Contatto: ", abbDTO.getContatto());

	        if (abbDTO.getDataNascita() != null) {
	            Date dataNascita = Date.from(abbDTO.getDataNascita().atStartOfDay(ZoneId.systemDefault()).toInstant());
	            String dataNascitaCliente = new java.text.SimpleDateFormat("dd/MM/yyyy").format(dataNascita);
	            labeledEntry(mainUpperPanel, "Data di nascita: ", dataNascitaCliente);
	        } else {
	            labeledEntry(mainUpperPanel, "Data di nascita: ", "Dato mancante");
	        }

	        labeledEntry(mainUpperPanel, "Certificato di idoneità: ", abbDTO.getCertificatoIdoneita() ? "Si" : "No");
	        labeledEntry(mainUpperPanel, "Permesso genitori: ", abbDTO.getPermessoGenitori() ? "Si" : "No");

	        labeledList(mainUpperPanel, "Componenti abbonamento selezionate: ", abbDTO.getSezioniAbbonamento());
	        labeledList(mainUpperPanel, "Corsi selezionati: ", abbDTO.getCorsiSelezionati());

	        revalidate();
	        repaint();
	    }
	  
	//----------------------------------------------------------------

	    @Override
	    public void setPrezzoTotale(double prezzo) {
	    	
	        prezzoTotaleLabel.setText(String.format("Totale: " + "%.2f €", prezzo));
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void addIndietroListener(ActionListener listener) {
	    	
	        indietro.addActionListener(listener);
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void addAnnullaListener(ActionListener listener) {
	    	
	        annulla.addActionListener(listener);
	        
	    }

	//----------------------------------------------------------------
	    
	    @Override
	    public void addConfermaListener(ActionListener listener) {
	    	
	        conferma.addActionListener(listener);
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void addScontoSuBaseMesiListener(ActionListener listener) {
	    	
	        scontoSuBaseMesi.addActionListener(listener);
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void addMensilitaListener(ActionListener listener) {
	    	
	        trimestrale.addActionListener(listener);
	        semestrale.addActionListener(listener);
	        annuale.addActionListener(listener);
	        mensile.addActionListener(listener);
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void addScontoEtaListener(ActionListener listener) {
	    	
	        scontoEtaCheckBox.addActionListener(listener);
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void addScontoOccasioniListener(ActionListener listener) {
	    	
	        scontoOccasioniBtn.addActionListener(listener);
	        
	    }
	    
	//----------------------------------------------------------------
	    
	    @Override
	    public void addScontoOccasioniCheckboxListener(ActionListener listener) {
	    	
	    	scontoOccasioniCheckBox.addActionListener(listener);
	    	
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void addMetodoPagamentoListener(ActionListener listener) {
	    	
	        contanti.addActionListener(listener);
	        carta.addActionListener(listener);
	        noPagamento.addActionListener(listener);
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void addAvvioPagamentoListener(ActionListener listener) {
	    	
	        avvioPagamento.addActionListener(listener);
	        
	    }
	    
	//----------------------------------------------------------------
	    

	    @Override
	    public boolean isContantiSelected() {
	    	
	        return contanti.isSelected();
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public boolean isCartaSelected() {
	    	
	        return carta.isSelected();
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public boolean isNoPagamentoSelected() {
	    	
	        return noPagamento.isSelected();
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public boolean isScontoEtaSelected() {
	    	
	        return scontoEtaCheckBox.isSelected();
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public boolean isScontoOccasioniSelected() {
	    	
	    	return scontoOccasioniCheckBox.isSelected();
	        
	    }
	    
	//----------------------------------------------------------------
	    
	    @Override
	    public void setScontoOccasioniButtonEnabled(boolean enabled) {
	    	
	        scontoOccasioniBtn.setEnabled(enabled);
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public String getDurataSelezionata() {
	    	
	        if (trimestrale.isSelected()) return trimestrale.getText();
	        if (semestrale.isSelected()) return semestrale.getText();
	        if (annuale.isSelected()) return annuale.getText();
	        if (mensile.isSelected()) return mensile.getText();
	        return null;
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public String getScontoSuBaseMesiText() {
	    	
	        return scontoSuBaseMesi.getText();
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void setScontoSuBaseMesiText(String text) {
	    	
	        scontoSuBaseMesi.setText(text);
	        
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void showPopupMenu() {
	        popupMenu.show(scontoSuBaseMesi, 0, scontoSuBaseMesi.getHeight());
	    }
	    
	//----------------------------------------------------------------
	    
	    @Override
	    public JPanel getMainPanel() {
	    	
	    	return this;
	    	
	    }
	    
	//----------------------------------------------------------------
	    
	    @Override
	    public MetodoPagamento getMetodoPagamentoSelezionato() {
	    	
	    	if(isContantiSelected()) {return MetodoPagamento.CONTANTI;}
	    	else if(isCartaSelected()) {return MetodoPagamento.CARTA;}
	    	else if(isNoPagamentoSelected()) {return MetodoPagamento.NESSUNO;}
	    	else {return null;}
	    	
	    }

	//----------------------------------------------------------------
	    
	    @Override
	    public void setConfermaEnabled(boolean enabled) {
	    	
	    	conferma.setEnabled(enabled);
	    	
	    }
	 
	//----------------------------------------------------------------

	    
	
}
