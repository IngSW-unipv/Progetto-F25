package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment;

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

import it.unipv.poisw.f25.gympal.Dominio.Enums.MetodoPagamento;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IRiepilogoDTO;

public class RiepilogoEPagamentoView extends JPanel implements IRiepilogoEPagamentoView{

	private static final long serialVersionUID = 1L;
	
	private JSplitPane mainSplitPanel;
	private JPanel mainUpperPanel;
	private JPanel mainBottomLeftPanel; //Qui finisce il riepilogo
	private JPanel mainBottomRightPanel; //Opzioni per la factory che calcola i prezzi
	
	private JSplitPane mainBottomSplitPanel;
	
	private JSplitPane secondSplitPanel; //Il pannello superiore coincide con "mainSplitPanel"
	private JPanel indietroConfermaPanel; //Qui finisce il pannello con il bottone "Intrietro"
	
	private JCheckBox scontoEtaCheckBox;
	private JCheckBox scontoOccasioniCheckBox;
	
	private JRadioButton carta;
	private JRadioButton contanti;
	private JRadioButton noPagamento;
	
	private JButton scontoSuBaseMesi;
	private JButton indietro;
	private JButton conferma;
	private JButton annulla;
	private JButton avvioPagamento;
	
	private JRadioButtonMenuItem trimestrale;
	private JRadioButtonMenuItem semestrale;
	private JRadioButtonMenuItem annuale;
	private JRadioButtonMenuItem nessuno;
	
	private JPopupMenu popupMenu;
	private ButtonGroup sceltaMesi;
	private ButtonGroup modalitaPagamento;
	
	private JLabel prezzoTotaleLabel;
	
    //----------------------------------------------------------------
	
	public RiepilogoEPagamentoView() {
		
	    setLayout(new BorderLayout());
	    
	    /*############################################################*/
	    
	    mainUpperPanel = new JPanel(new GridLayout(0, 2, 10, 10));
	    
	    mainUpperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    
	    mainUpperPanel.setBorder(BorderFactory.createTitledBorder("Riepilogo Anagrafica:"));

	    mainSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	    
	    JPanel upperPanelContainer = new JPanel();
	    upperPanelContainer.setLayout(new BorderLayout());


	    upperPanelContainer.add(mainUpperPanel, BorderLayout.CENTER);

	    mainSplitPanel.setTopComponent(upperPanelContainer);
	    
	    /*############################################################*/
	    
        mainBottomLeftPanel = new JPanel(new BorderLayout(10, 10));
        
        mainBottomLeftPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        mainBottomLeftPanel.setBorder(BorderFactory.createTitledBorder("Sezione pagamento:"));
        
        prezzoTotaleLabel = new JLabel("Totale: € 0.00", JLabel.CENTER);
        
        avvioPagamento = new JButton("Avvio Pagamento");
        
        carta = new JRadioButton(" - Carta di credito / Bancomat");
        contanti = new JRadioButton(" - Contanti");
        noPagamento = new JRadioButton (" - No pagamento (abbonamento provvisorio - 30gg)");
        
        modalitaPagamento = new ButtonGroup();
        
        modalitaPagamento.add(carta);
        modalitaPagamento.add(contanti);
        modalitaPagamento.add(noPagamento);
        
        //Sottopannello locale a "mainBottomLeftPanel"
        JPanel sceltaModalitaPagamento = new JPanel ();
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

                
        /*############################################################*/
        
        mainBottomRightPanel = new JPanel();
        
        mainBottomRightPanel.setLayout(new GridLayout(0, 1, 5, 5));
        
        mainBottomRightPanel.setBorder(BorderFactory.createTitledBorder("Sezione sconti:"));
        
        scontoEtaCheckBox = new JCheckBox("Applica sconto età");
        mainBottomRightPanel.add(scontoEtaCheckBox);
        
        scontoOccasioniCheckBox = new JCheckBox("Applica sconto occasioni");
        mainBottomRightPanel.add(scontoOccasioniCheckBox);
        
        trimestrale = new JRadioButtonMenuItem("Trimestrale");
        semestrale = new JRadioButtonMenuItem("Semestrale");
        annuale = new JRadioButtonMenuItem("Annuale");
        nessuno = new JRadioButtonMenuItem("Nessuno");
        
        sceltaMesi = new ButtonGroup();
        sceltaMesi.add(trimestrale);
        sceltaMesi.add(semestrale);
        sceltaMesi.add(annuale);
        sceltaMesi.add(nessuno);
        
        nessuno.setSelected(true);
        
        /*Il JPopupMenu non è un componente visivo fisso, come un JPanel o un JButton.
         *Esso è visualizzato in modo dinamico, su base temporanea, tramite invocazione del
         *metodo "show", che per input ha:
         *
         *- il componente che invoca il PopupMenu
         *
         *- la posizione "x" rispetto al componente che invoca il menu
         *
         *- la posizione "y" rispetto al componente che invoca il menu
         *
         *ES: popupMenu.show(scontoSuBaseMesi, 0, 0); */
        
        popupMenu = new JPopupMenu();
        popupMenu.add(trimestrale);
        popupMenu.add(semestrale);
        popupMenu.add(annuale);
        popupMenu.add(nessuno);
        
        /*Aggiunto separatore e label*/
        JSeparator separator = new JSeparator();
        //In orizzontale, sottile.
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); 
        mainBottomRightPanel.add(separator);
        
        JLabel durataLabel = new JLabel("Sceglie durata abbonamento (applica sconto base): ");
        durataLabel.setAlignmentX(CENTER_ALIGNMENT); 
        mainBottomRightPanel.add(durataLabel);
        ////////////////////////////////////////////
        
        scontoSuBaseMesi = new JButton("Numero mesi");
                
        mainBottomRightPanel.add(scontoSuBaseMesi);

        sceltaMesi = new ButtonGroup();
        
        
	    /*############################################################*/
	    
	    mainBottomSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    
	    mainBottomSplitPanel.setRightComponent(mainBottomLeftPanel);
	    
	    mainBottomSplitPanel.setLeftComponent(mainBottomRightPanel);
	    
		SwingUtilities.invokeLater(() -> {
			
			mainBottomSplitPanel.setDividerLocation(0.5); 
		    
		});
		
		mainBottomSplitPanel.setEnabled(false);
	    
	    mainSplitPanel.setBottomComponent(mainBottomSplitPanel);
        
	    /*############################################################*/
	    
	    indietroConfermaPanel = new JPanel();
	    
	    indietroConfermaPanel.setLayout(new BoxLayout(indietroConfermaPanel, BoxLayout.X_AXIS));
	    
	    indietro = new JButton ("Indietro");
		indietro.setMaximumSize(new Dimension(80, 40));
		
		conferma = new JButton ("Conferma");
		conferma.setMaximumSize(new Dimension(80, 40));
		
		annulla = new JButton ("Annulla");
		annulla.setMaximumSize(new Dimension(80, 40));
		
		indietroConfermaPanel.add(Box.createHorizontalGlue());
		indietroConfermaPanel.add(annulla);
		indietroConfermaPanel.add(Box.createHorizontalStrut(100));
		indietroConfermaPanel.add(indietro);
		indietroConfermaPanel.add(Box.createHorizontalStrut(100));
		indietroConfermaPanel.add(conferma);
		indietroConfermaPanel.add(Box.createHorizontalGlue());
	    
	    /*############################################################*/
	    
	    secondSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	    
	    secondSplitPanel.setTopComponent(mainSplitPanel);
	    
	    secondSplitPanel.setBottomComponent(indietroConfermaPanel);
	    
		SwingUtilities.invokeLater(() -> {
			
			secondSplitPanel.setDividerLocation(0.8); 
		    
		});
		
		secondSplitPanel.setEnabled(false);
	    
	    /*############################################################*/

	    add(secondSplitPanel);
	    
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
	  public void setDatiAbbonamento(IRiepilogoDTO abbDTO) {

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
	        prezzoTotaleLabel.setText(String.format("%.2f €", prezzo));
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
	        nessuno.addActionListener(listener);
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void addScontoEtaListener(ActionListener listener) {
	        scontoEtaCheckBox.addActionListener(listener);
	    }
	    
	//----------------------------------------------------------------

	    @Override
	    public void addScontoOccasioniListener(ActionListener listener) {
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
	    public String getDurataSelezionata() {
	        if (trimestrale.isSelected()) return trimestrale.getText();
	        if (semestrale.isSelected()) return semestrale.getText();
	        if (annuale.isSelected()) return annuale.getText();
	        if (nessuno.isSelected()) return nessuno.getText();
	        return null;
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
	    
	    /*@Override
	    public boolean isScontoMensilitaSelected() {
	        return scontoMensilitaCheckBox.isSelected();
	    }*/
	    
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
	    	else {return MetodoPagamento.NESSUNO;}
	    	
	    }
	    
	    
	
}
