package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class AppuntamentiPTPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable appuntamentiTable;

    private JButton fissaBtn;
    private JButton annullaBtn;

    private JTextField cfClienteField;
    private JTextField staffIdField;
    private JButton filtraBtn;

    //----------------------------------------------------------------

    public AppuntamentiPTPanel() {

        setLayout(new BorderLayout());

        /*############################################################*/
        
        // Pannello filtri
        JPanel filtroPanel = new JPanel();

        cfClienteField = new JTextField(16);
        staffIdField = new JTextField(35);

        filtroPanel.add(new JLabel("CF Cliente:"));
        filtroPanel.add(cfClienteField);
        
        filtroPanel.add(new JLabel("StaffId:"));
        filtroPanel.add(staffIdField);
        
        filtraBtn = new JButton("Filtra");
        filtroPanel.add(filtraBtn);

        add(filtroPanel, BorderLayout.NORTH);
        
        /*############################################################*/

        appuntamentiTable = new JTable();
        add(new JScrollPane(appuntamentiTable), BorderLayout.CENTER);
        
        /*############################################################*/

        JPanel btnPanel = new JPanel();
        fissaBtn = new JButton("Iscrivi Cliente");
        annullaBtn = new JButton("Annulla Iscrizione");

        btnPanel.add(fissaBtn);
        btnPanel.add(annullaBtn);

        add(btnPanel, BorderLayout.SOUTH);

    }

    //----------------------------------------------------------------

    public JTable getAppuntamentiTable() { return appuntamentiTable; }

    //----------------------------------------------------------------

    public void addFissaBtnListener(ActionListener listener) {
    	
        fissaBtn.addActionListener(listener);
        
    }

    //----------------------------------------------------------------

    public void addAnnullaBtnListener(ActionListener listener) {
    	
        annullaBtn.addActionListener(listener);
        
    }

    //----------------------------------------------------------------

    public void addFiltroListener(ActionListener listener) {
    	
        filtraBtn.addActionListener(listener);
        
    }

    //----------------------------------------------------------------

    public String getCfClienteFiltro() {
    	
        return cfClienteField.getText().trim();
        
    }

    //----------------------------------------------------------------

    public String getStaffIdFiltro() {
    	
        return staffIdField.getText().trim();
        
    }
    
    //----------------------------------------------------------------

}
