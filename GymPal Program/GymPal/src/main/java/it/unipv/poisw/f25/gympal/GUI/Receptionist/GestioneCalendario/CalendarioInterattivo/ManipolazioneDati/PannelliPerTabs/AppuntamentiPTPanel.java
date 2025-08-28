package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.IFontChangeRegister;

public class AppuntamentiPTPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable appuntamentiTable;

    private JButton fissaBtn;
    private JButton annullaBtn;

    private JTextField cfClienteField;
    private JComboBox<String> staffIdComboBox;
    private JButton filtraBtn;

    //----------------------------------------------------------------

    public AppuntamentiPTPanel(IDynamicButtonSizeSetter buttonSizeSetter,
    						   IFontChangeRegister fontChangeRegister) {

        setLayout(new BorderLayout());

        /*############################################################*/
        
        // Pannello filtri
        JPanel filtroPanel = new JPanel();

        cfClienteField = new JTextField(16);
        staffIdComboBox = new JComboBox<>();
        staffIdComboBox.setEditable(false);

        filtroPanel.add(new JLabel("CF Cliente:"));
        filtroPanel.add(cfClienteField);
        
        filtroPanel.add(new JLabel("StaffId:"));
        filtroPanel.add(staffIdComboBox);
        
        filtraBtn = new JButton("Filtra");
        filtroPanel.add(filtraBtn);

        add(filtroPanel, BorderLayout.NORTH);
        
        /*############################################################*/

        appuntamentiTable = new JTable();
        appuntamentiTable.setAutoCreateRowSorter(true);
        add(new JScrollPane(appuntamentiTable), BorderLayout.CENTER);
        
        /*############################################################*/

        JPanel btnPanel = new JPanel();
        fissaBtn = new JButton("Fissa Appuntamento");
        annullaBtn = new JButton("Annulla Appuntamento");
        
        buttonSizeSetter.uniformButtonSize(fissaBtn, annullaBtn);

        btnPanel.add(fissaBtn);
        btnPanel.add(annullaBtn);

        add(btnPanel, BorderLayout.SOUTH);
        
        fontChangeRegister.register(this, buttonSizeSetter);
        
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
    	
    	Object selected = staffIdComboBox.getSelectedItem();
        return selected != null ? selected.toString().trim() : "";
        
    }
    
    //----------------------------------------------------------------
    
    public void setStaffIdOptions(List<String> staffIds) {
    	
        staffIdComboBox.removeAllItems();
        staffIdComboBox.addItem(""); // Per selezionare "nessuno"
        
        for (String id : staffIds) {
        	
            staffIdComboBox.addItem(id);
            
        }
        
    }
    
    //----------------------------------------------------------------

}
