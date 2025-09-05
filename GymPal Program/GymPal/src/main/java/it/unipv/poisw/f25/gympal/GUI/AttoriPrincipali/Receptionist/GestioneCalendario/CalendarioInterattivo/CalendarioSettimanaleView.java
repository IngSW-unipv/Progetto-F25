package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities.CalendarioCellPanel;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class CalendarioSettimanaleView extends JPanel implements ICalendarioSettimanaleView {

    private static final long serialVersionUID = 1L;
    
    /*Fasce orarie & giorni*/
    private final int ORE_INIZIO = 8;
    private final int ORE_FINE = 20;
    private final int NUM_GIORNI = 7;

    /*Navigazione*/
    private JButton btnPrecedente;
    private JButton btnSuccessiva;
    private JButton btnGestioneAvanzata;
    private JButton btnLegenda;
    
    /*Pannelli*/
    private JPanel griglia;
    private JPanel pannelloBottoni;
    
    /**/
    private LocalDate[] dateSettimana;
    private LocalDate lunediCorrente;
    
    private List<CalendarioCellPanel> celle;
    
    /*Servizi*/
    private IDynamicButtonSizeSetter buttonSizeSetter;
    private IFontChangeRegister fontChangeRegister;
    
	//----------------------------------------------------------------

    public CalendarioSettimanaleView(IDynamicButtonSizeSetter setter,
    								 IFontChangeRegister fontChangeRegister) {
    	
    	buttonSizeSetter = setter;
    	this.fontChangeRegister = fontChangeRegister;
    	
        setLayout(new BorderLayout());

        griglia = new JPanel();
        celle = new ArrayList<>();
        lunediCorrente = LocalDate.now().with(DayOfWeek.MONDAY);

        // ScrollPane su griglia
        JScrollPane scrollPane = new JScrollPane(griglia);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        creaPannelloBottoni();
        add(pannelloBottoni, BorderLayout.SOUTH);
        
        fontChangeRegister.register(this, buttonSizeSetter);
        
    }
    
	//----------------------------------------------------------------

    @Override
    public void popolaGriglia(LocalDate lunedi, 
    						  ActionListener cellClickListener) {
    	
    	this.lunediCorrente = lunedi;
    	
        griglia.removeAll();
        griglia.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        celle.clear();
        dateSettimana = new LocalDate[NUM_GIORNI];

        for (int i = 0; i < NUM_GIORNI; i++) {
        	
            dateSettimana[i] = lunedi.plusDays(i);
            
        }

        // ---------- Intestazione Giorni (riga 0) ----------
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.weightx = 0;
        
        JLabel vuoto = new JLabel("");
        vuoto.setPreferredSize(new Dimension(70, 30)); // spazio per le ore
        griglia.add(vuoto, gbc);

        for (int i = 0; i < NUM_GIORNI; i++) {
        	
            gbc.gridx = i + 1;
            gbc.weightx = 1;

            LocalDate giorno = dateSettimana[i];
            String labelText = giorno.getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " 
            						+ giorno.toString();

            JLabel label = new JLabel(labelText, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            label.setOpaque(true);
            label.setBackground(new Color(230, 230, 250));
            label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            label.setPreferredSize(new Dimension(120, 30));

            griglia.add(label, gbc);
            
        }

        // ---------- Celle Orarie + Contenuto ----------
        int r = 1;
        
        for (int ora = ORE_INIZIO; ora <= ORE_FINE; ora++) {
        	
            for (int minuti = 0; minuti < 60; minuti += 30) {
                gbc.gridy = r++;

                // Colonna ore
                gbc.gridx = 0;
                gbc.weightx = 0;
                gbc.weighty = 0;
                gbc.fill = GridBagConstraints.BOTH;
                String labelTime = String.format("%02d:%02d", ora, minuti);
                JLabel oraLabel = new JLabel(labelTime, SwingConstants.CENTER);
                oraLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
                oraLabel.setOpaque(true);
                oraLabel.setBackground(new Color(245, 245, 245));
                oraLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                oraLabel.setPreferredSize(new Dimension(70, 60)); // Spazio verticale visibile
                griglia.add(oraLabel, gbc);

                // Celle per i giorni
                gbc.weightx = 1;
                gbc.weighty = 1;
                gbc.fill = GridBagConstraints.BOTH;

                for (int giorno = 0; giorno < NUM_GIORNI; giorno++) {
                	
                    gbc.gridx = giorno + 1;

                    LocalDate giornoData = dateSettimana[giorno];
                    CalendarioCellPanel cella = new CalendarioCellPanel(giornoData,
                    												    ora, minuti,
                    												    fontChangeRegister,
                    												    buttonSizeSetter);
                    
                    cella.addButtonListener(cellClickListener);
                    griglia.add(cella, gbc);
                    celle.add(cella);
                    
                }

            }
            
        }

        revalidate();
        repaint();
    }
    
	//----------------------------------------------------------------

    @Override
    public LocalDate[] getDateSettimana() {
    	
        return dateSettimana;
        
    }
    
	//----------------------------------------------------------------

    @Override
    public List<CalendarioCellPanel> getTutteLeCelle() {
    	
        return celle;
        
    }

	//----------------------------------------------------------------
    
    @Override
    public void addBtnPrecedenteListener(ActionListener listener) {
    	
        btnPrecedente.addActionListener(listener);
        
    }
    
	//----------------------------------------------------------------

    @Override
    public void addBtnSuccessivaListener(ActionListener listener) {
    	
        btnSuccessiva.addActionListener(listener);
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public void addBtnGestioneAvanzataListener(ActionListener listener) {
    	
        btnGestioneAvanzata.addActionListener(listener);
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public void addBtnLegendaListener(ActionListener listener) {
    	
    	btnLegenda.addActionListener(listener);
    	
    }
    
	//----------------------------------------------------------------

    @Override
    public LocalDate getLunediCorrente() {
    	
        return lunediCorrente;
        
    }
    
	//----------------------------------------------------------------

    @Override
    public JPanel getMainPanel() {
    	
        return this;
        
    }
    
	//----------------------------------------------------------------
    
    
    private void creaPannelloBottoni() {
    	
        pannelloBottoni = new JPanel();
        
        btnPrecedente = new JButton("<< Settimana Precedente");
        btnSuccessiva = new JButton("Settimana Successiva >>");
        btnGestioneAvanzata = new JButton("Gestione avanzata...");
        btnLegenda = new JButton("Legenda");
        
        buttonSizeSetter.uniformButtonSize(btnLegenda);
        buttonSizeSetter.uniformButtonSize(btnPrecedente, btnSuccessiva,
        								   btnGestioneAvanzata);

        pannelloBottoni.add(btnPrecedente);
        pannelloBottoni.add(btnSuccessiva);
        pannelloBottoni.add(btnGestioneAvanzata);
        pannelloBottoni.add(btnLegenda);
        
    }

	//----------------------------------------------------------------


}
