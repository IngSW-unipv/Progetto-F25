package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;

public class DettaglioCellaFrame extends JFrame {

	private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------

    public DettaglioCellaFrame() {
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // ----------------------------------------------------------------

    public DettaglioCellaFrame(IDatiCellaCalendarioDTO dati, LocalDate data,
                               int ora, int minuti) {

        super("Dettagli cella - " + data 
        						  + " " + String.format("%02d:%02d", ora, minuti));
        setLayout(new BorderLayout());

        // HEADER
        JLabel headerLabel = new JLabel("Data: " + data 
        										 + "   Ora: " 
        										 + String.format("%02d:%02d", ora, minuti));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // CONTENUTO
        JPanel contenutoPanel = new JPanel(new GridBagLayout());
        contenutoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        contenutoPanel.add(creaSezioneConLista("Corsi", dati.getCorsi()), gbc);

        gbc.gridx = 1;
        contenutoPanel.add(creaSezioneConLista("Appuntamenti PT", dati.getAppuntamentiPT()), gbc);

        gbc.gridx = 2;
        contenutoPanel.add(creaSezioneConLista("Eventi", dati.getEventiGenerici()), gbc);

        gbc.gridx = 3;
        contenutoPanel.add(creaSezioneConLista("Turni", dati.getTurni()), gbc);

        add(contenutoPanel, BorderLayout.CENTER);

        // FOOTER
        JPanel footer = new JPanel();
        JButton chiudiBtn = new JButton("Chiudi");
        chiudiBtn.addActionListener(e -> dispose());
        footer.add(chiudiBtn);
        add(footer, BorderLayout.SOUTH);

        setSize(1600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        
    }

    // ----------------------------------------------------------------

    private JPanel creaSezioneConLista(String titolo, List<String> dati) {
    	
        JPanel sezionePanel = new JPanel(new BorderLayout());
        sezionePanel.setBorder(BorderFactory.createTitledBorder(titolo));

        DefaultListModel<String> model = new DefaultListModel<>();
        
        if (dati == null || dati.isEmpty()) {
        	
            model.addElement("Nessuno");
            
        } else {
        	
        	/*Al modello sono aggiunte tutte le stringhe nella lista 'dati'*/
            dati.forEach(model::addElement);
            
        }

        JList<String> jList = new JList<>(model);

        /* Renderer HTML:  questo blocco controlla se una stringa comincia con il
         * tag '<html>' e, in tal caso, consente a 'JLabel' di renderizzare conte-
         * nuto HTML.
         * 
         * Nel caso in cui una stringa non sia posta in formato HTML, essa è ren-
         * derizzata normalmente.*/
        jList.setCellRenderer(new DefaultListCellRenderer() {
        	
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
            	
                JLabel label = (JLabel) super.getListCellRendererComponent(list, 
                						  value, index, isSelected, cellHasFocus);
                
                String text = (String) value;
                
                if (text != null && text.trim().startsWith("<html>")) {
                	
                    label.setText(text);  
                    
                } else {
                	
                    label.setText(text);
                    
                }
                
                return label;
                
            }
            
        });

        JScrollPane scrollPane = creaListaScrollPane(model, jList);
        sezionePanel.add(scrollPane, BorderLayout.CENTER);

        sezionePanel.setMinimumSize(new Dimension(200, 100));
        return sezionePanel;
        
    }

    // ----------------------------------------------------------------

    /* La classe 'DefaultListModel' è pensata per essere usata direttamente con
     * i componenti Swing come 'JList'.
     * Essa è mutabile, e supporta eventi di modifica, quindi l'interfaccia utente
     * risulta aggiornata in modo automatico quando i dati cambiano.
     * 
     * Una 'List' classica andrebbe convertita in un 'ListModel' prima di essere
     * passata ad una 'JList'. Peraltro, 'List' non notifica nessun componente
     * Swing a fronte di cambiamenti nei dati.*/
    private JScrollPane creaListaScrollPane(DefaultListModel<String> model,
                                            JList<String> jList) {

        FontMetrics metrics = jList.getFontMetrics(jList.getFont());
        int visibleRows = Math.max(3, Math.min(model.getSize(), 10));
        jList.setVisibleRowCount(visibleRows);

        int maxTextWidth = 0;
        
        for (int i = 0; i < model.size(); i++) {
        	
            int width = metrics.stringWidth(model.get(i));
            if (width > maxTextWidth) {
            	
                maxTextWidth = width;
                
            }
            
        }

        int preferredWidth = Math.max(maxTextWidth + 30, 200);
        int rowHeight = jList.getFixedCellHeight();
        if (rowHeight <= 0) rowHeight = metrics.getHeight();
        int preferredHeight = visibleRows * rowHeight + 10;

        JScrollPane scrollPane = new JScrollPane(jList);
        scrollPane.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        scrollPane.setMinimumSize(new Dimension(200, 100));

        return scrollPane;
        
    }

    // ----------------------------------------------------------------
    
}
