package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.JDialogFissaAppuntamento;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;

public class FissaAppuntamentoDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private IDateUtils dateUtils;
    private IFasciaOrariaValidator fasciaValidator;

    private JTextField cfField;
    private JTextField dataField;
    private JComboBox<String> staffIdComboBox;
    private JTextField fasciaField;

    private boolean confermato = false;

    //----------------------------------------------------------------

    public FissaAppuntamentoDialog(List<String> staffIdOptions,
                                   IDateUtils dateUtils,
                                   IFasciaOrariaValidator fasciaValidator) {
    	
        this.dateUtils = dateUtils;
        this.fasciaValidator = fasciaValidator;

        setTitle("Fissa Appuntamento PT");
        setModal(true);
        setSize(650, 450);
        setLocationRelativeTo(null);

        inizializzaDialog(staffIdOptions);
        
    }

    //----------------------------------------------------------------

    private void inizializzaDialog(List<String> staffIdOptions) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Codice Fiscale
        panel.add(new JLabel("Codice Fiscale Cliente:"));
        cfField = new JTextField();
        panel.add(cfField);

        // Data
        panel.add(new JLabel("Data (yyyy-MM-dd):"));
        dataField = new JTextField();
        panel.add(dataField);

        // Staff ID
        panel.add(new JLabel("Staff ID (DIP):"));
        staffIdComboBox = new JComboBox<>();
        for (String id : staffIdOptions) {
            staffIdComboBox.addItem(id);
        }
        panel.add(staffIdComboBox);

        // Fascia oraria (campo libero)
        panel.add(new JLabel("Fascia Oraria (es. 10:00-11:00):"));
        fasciaField = new JTextField();
        panel.add(fasciaField);

        // Pulsanti
        JButton confermaBtn = new JButton("Conferma");
        JButton annullaBtn = new JButton("Annulla");

        confermaBtn.addActionListener(this::handleConferma);
        annullaBtn.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.add(confermaBtn);
        btnPanel.add(annullaBtn);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        
    }

    //----------------------------------------------------------------

    private void handleConferma(ActionEvent e) {
        String cf = getCodiceFiscale();
        if (cf == null || cf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserire il codice fiscale del cliente.");
            return;
        }

        if (getData() == null) {
            JOptionPane.showMessageDialog(this, "Inserire una data valida (yyyy-MM-dd).");
            return;
        }

        String fascia = getFasciaOraria();
        if (!fasciaValidator.isValid(fascia)) {
            JOptionPane.showMessageDialog(this, fasciaValidator.getErrore(fascia));
            return;
        }

        if (getStaffIdSelezionato() == null || getStaffIdSelezionato().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selezionare uno staffId.");
            return;
        }

        confermato = true;
        dispose();
    }

    //----------------------------------------------------------------

    public boolean isConfermato() {
    	
        return confermato;
        
    }
    
    //----------------------------------------------------------------

    public String getCodiceFiscale() {
    	
        return cfField.getText().trim();
        
    }
    
    //----------------------------------------------------------------

    public LocalDate getData() {
    	
        return dateUtils.parseData(dataField.getText().trim());
        
    }
    
    //----------------------------------------------------------------

    public String getStaffIdSelezionato() {
    	
        Object selected = staffIdComboBox.getSelectedItem();
        return selected != null ? selected.toString().trim() : null;
        
    }
    
    //----------------------------------------------------------------

    public String getFasciaOraria() {
    	
        return fasciaField.getText().trim();
        
    }
    
    //----------------------------------------------------------------

    public AppuntamentoPT getAppuntamentoPT() {
    	
        String cf = getCodiceFiscale();
        LocalDate data = getData();
        String staffId = getStaffIdSelezionato();
        String fascia = getFasciaOraria();

        if (cf == null || cf.isEmpty() 
        			   || data == null 
        			   || staffId == null 
        			   || fascia == null) {
        	
            return null;
            
        }

        return new AppuntamentoPT(cf, staffId, data, fascia);
        
    }

    //----------------------------------------------------------------

}
