package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.RettificaInfoCliente.Rettifica;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.ZoneId;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.MasterDTOEBuilder.IDatiClienteReadOnly;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DataFerry.RawClientData;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo.IEtichettaPiuCampoFactory;

public class RettificaInfoView extends JPanel implements IRettificaInfoView{

	private static final long serialVersionUID = 1L;

	/*Dati Cliente estratti*/
	private JTextField codiceFiscale;
	
	private JTextField nome;
	private JTextField cognome;
	private JTextField cfAnagrafico;
	private JTextField contatto;
	
	private JSpinner dateSpinner;
	
	private JRadioButton maschio;
	private JRadioButton femmina;
	private ButtonGroup sesso;
	
	
	/*Operazioni*/
	private JButton estrai;
	private JButton elimina;
	private JButton saveMods;
	private JButton insData;
	
	/*Navigazione*/
	private JButton annulla;
	private JButton avanti;
	
	/*Labels*/
	private JLabel cfErrato;
	private JLabel postElimina;
	
	/*Pannelli & SplitPane*/
	private JSplitPane mainSplitPanel;
	
	/*Servizi*/
	private IDynamicButtonSizeSetter buttonSizeSetter;
	private IEtichettaPiuCampoFactory campoEtichettato;
	
	//----------------------------------------------------------------
	
	public RettificaInfoView(IDynamicButtonSizeSetter setter,
							 IEtichettaPiuCampoFactory campoEtichettato,
							 IFontChangeRegister fontChangeRegister) {
		
		/*Servizi*/
		this.buttonSizeSetter = setter;
        this.campoEtichettato = campoEtichettato;

        /*Layout Pannello principale*/
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 5, 10, 5));

        /*Costruzione contenuto pannello*/
        initFields();

        JPanel estraiPanel = buildEstraiPanel();
        JSplitPane eliminaAndEdit = buildEliminaAndEditSection();
        JSplitPane estraiAndEdit = buildEstraiAndEditPanel(estraiPanel, eliminaAndEdit);
        JPanel navigationPanel = buildNavigationPanel();

        mainSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, estraiAndEdit, navigationPanel);
        SwingUtilities.invokeLater(() -> mainSplitPanel.setDividerLocation(0.80));
        mainSplitPanel.setEnabled(false);

        add(mainSplitPanel);

        /*Sottoscrizione a meccanismo cambio-font*/
        fontChangeRegister.register(this, buttonSizeSetter);
    }

	//----------------------------------------------------------------

    private void initFields() {
    	
        codiceFiscale = new JTextField(80);
        nome = new JTextField(80);
        cognome = new JTextField(80);
        cfAnagrafico = new JTextField(80);
        contatto = new JTextField(80);

        cfAnagrafico.setEditable(false);

        SpinnerDateModel model = new SpinnerDateModel();
        dateSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(editor);

        maschio = new JRadioButton("Maschio");
        femmina = new JRadioButton("Femmina");
        sesso = new ButtonGroup();
        sesso.add(maschio);
        sesso.add(femmina);
        maschio.setSelected(true);

        estrai = new JButton("Estrai Dati");
        elimina = new JButton("Elimina");
        saveMods = new JButton("Salva Modifiche");
        insData = new JButton("Re-Ins Dati");
        annulla = new JButton("Annulla");
        avanti = new JButton("Avanti");

        saveMods.setEnabled(false);
        insData.setEnabled(false);
        elimina.setEnabled(false);

        buttonSizeSetter.uniformButtonSize(estrai, elimina, saveMods,
        								   insData, annulla, avanti);

        cfErrato = new JLabel("CF errato? Allora: ");
        postElimina = new JLabel("Usare -DOPO- Elimina");
        
    }

	//----------------------------------------------------------------
    
    private JPanel buildEstraiPanel() {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(""));

        JLabel titleLabel = new JLabel("-=Inserisci codice fiscale del cliente=-");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        estrai.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(campoEtichettato.creaCampoEtichettato("CF: ", codiceFiscale));
        panel.add(Box.createVerticalStrut(10));
        panel.add(estrai);
        panel.add(Box.createVerticalStrut(30));
        panel.add(Box.createVerticalGlue());

        return panel;
        
    }
    
	//----------------------------------------------------------------

    private JSplitPane buildEliminaAndEditSection() {
    	
        JSplitPane editAndInsertBtns = buildEditAndInsertSection();
        JPanel eliminaPanel = buildEliminaPanel();

        JSplitPane eliminaAndInfosView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, eliminaPanel, editAndInsertBtns);
        SwingUtilities.invokeLater(() -> eliminaAndInfosView.setDividerLocation(0.2));
        eliminaAndInfosView.setEnabled(false);

        return eliminaAndInfosView;
        
    }
    
	//----------------------------------------------------------------

    private JSplitPane buildEditAndInsertSection() {
    	
        JPanel anagraficaPanel = buildAnagraficaPanel();
        JPanel modReinsertPanel = buildSaveAndReinsertPanel();

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(anagraficaPanel), modReinsertPanel);
        SwingUtilities.invokeLater(() -> split.setDividerLocation(0.9));
        split.setEnabled(false);

        return split;
        
    }
    
	//----------------------------------------------------------------

    private JPanel buildAnagraficaPanel() {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("-=Anagrafica Cliente=-");
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel note = new JLabel("Modificare 'cf' -SE, E SOLO SE- dati utente sono stati eliminati");
        note.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createVerticalStrut(30));
        panel.add(campoEtichettato.creaCampoEtichettato("Nome: ", nome));
        panel.add(Box.createVerticalStrut(10));
        panel.add(campoEtichettato.creaCampoEtichettato("Cognome: ", cognome));
        panel.add(Box.createVerticalStrut(20));
        panel.add(campoEtichettato.creaCampoEtichettato("Data di nascita (dd/MM/aa): ", dateSpinner));
        panel.add(Box.createVerticalStrut(20));
        panel.add(note);
        panel.add(Box.createVerticalStrut(5));
        panel.add(campoEtichettato.creaCampoEtichettato("Codice Fiscale: ", cfAnagrafico));
        panel.add(Box.createVerticalStrut(10));
        panel.add(campoEtichettato.creaCampoEtichettato("Contatto cliente (email): ", contatto));
        panel.add(Box.createVerticalStrut(30));

        JLabel sessoLabel = new JLabel("Seleziona il sesso del cliente:");
        sessoLabel.setAlignmentX(CENTER_ALIGNMENT);
        maschio.setAlignmentX(CENTER_ALIGNMENT);
        femmina.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(sessoLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(maschio);
        panel.add(Box.createVerticalStrut(5));
        panel.add(femmina);
        panel.add(Box.createVerticalStrut(10));
        panel.add(Box.createVerticalGlue());

        return panel;
        
    }
    
	//----------------------------------------------------------------

    private JPanel buildSaveAndReinsertPanel() {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Modifica -O- Re-introduci dati Cliente"));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(Box.createVerticalStrut(35));
        left.add(saveMods);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(postElimina);
        right.add(Box.createVerticalStrut(10));
        right.add(insData);

        panel.add(Box.createHorizontalGlue());
        panel.add(left);
        panel.add(Box.createHorizontalStrut(40));
        panel.add(right);
        panel.add(Box.createHorizontalGlue());

        return panel;
        
    }
    
	//----------------------------------------------------------------

    private JPanel buildEliminaPanel() {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("ATTENZIONE - OPERAZIONE IRREVERSIBILE"));

        panel.add(Box.createVerticalGlue());
        panel.add(Box.createVerticalStrut(10));
        cfErrato.setAlignmentX(CENTER_ALIGNMENT);
        elimina.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(cfErrato);
        panel.add(Box.createVerticalStrut(10));
        panel.add(elimina);
        panel.add(Box.createVerticalGlue());

        return panel;
        
    }
    
	//----------------------------------------------------------------

    private JSplitPane buildEstraiAndEditPanel(JPanel estraiPanel, 
    										   JSplitPane eliminaAndInfosView) {
    	
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, estraiPanel, eliminaAndInfosView);
        SwingUtilities.invokeLater(() -> split.setDividerLocation(0.35));
        split.setEnabled(false);
        return split;
        
    }
    
	//----------------------------------------------------------------

    private JPanel buildNavigationPanel() {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(""));

        panel.add(Box.createHorizontalGlue());
        panel.add(annulla);
        panel.add(Box.createHorizontalStrut(100));
        panel.add(avanti);
        panel.add(Box.createHorizontalGlue());

        return panel;
        
    }

	//----------------------------------------------------------------

    @Override public void setTextFieldsContent(IDatiClienteReadOnly abbDTO) {
    	
        nome.setText(abbDTO.getNome());
        cognome.setText(abbDTO.getCognome());
        contatto.setText(abbDTO.getContatto());
        cfAnagrafico.setText(abbDTO.getCodiceFiscale());
        dateSpinner.setValue(Date.valueOf(abbDTO.getDataNascita()));
        
        if ("M".equals(abbDTO.getSesso())) {
        	
        	maschio.setSelected(true);
        	
        } 
        
        else {
        	
        	femmina.setSelected(true);
        	
        }
        
    }
    
	//----------------------------------------------------------------

    @Override public RawClientData getDatiClienteRaw() {
    	
        return new RawClientData(
        		
            nome.getText(), cognome.getText(), cfAnagrafico.getText(),
            contatto.getText(), maschio.isSelected() ? "M" : "F",
            ((java.util.Date) dateSpinner.getValue())
            							 .toInstant()
            							 .atZone(ZoneId.systemDefault())
            							 .toLocalDate()
            							 
        );
        
    }
    
	//----------------------------------------------------------------

    @Override public JTextField getCodiceFiscale() { return codiceFiscale; }
    
	//----------------------------------------------------------------
    
    @Override public JTextField getNome() { return nome; }
    
	//----------------------------------------------------------------
    
    @Override public JTextField getCognome() { return cognome; }
    
	//----------------------------------------------------------------
    
    @Override public JTextField getCfAnagrafico() { return cfAnagrafico; }
    
	//----------------------------------------------------------------
    
    @Override public JTextField getContatto() { return contatto; }
    
	//----------------------------------------------------------------
    
    @Override public JSpinner getDateSpinner() { return dateSpinner; }
    
	//----------------------------------------------------------------    
    
    @Override public JRadioButton getMaschio() { return maschio; }
    
	//----------------------------------------------------------------
    
    @Override public JRadioButton getFemmina() { return femmina; }
    
	//----------------------------------------------------------------
    
    @Override public void addDateSpinnerListener(ChangeListener listener) { 
    	
    	dateSpinner.addChangeListener(listener); 
    	
    }
    
	//----------------------------------------------------------------

    @Override public void addEstraiListenr(ActionListener listener) { 
    	
    	estrai.addActionListener(listener); 
    	
    }
    
	//----------------------------------------------------------------
    
    @Override public void addEliminaListener(ActionListener listener) {
    	
    	elimina.addActionListener(listener); 
    	
    }
    
	//----------------------------------------------------------------
    
    @Override public void addSaveModsListener(ActionListener listener) { 
    	
    	saveMods.addActionListener(listener); }
    
	//----------------------------------------------------------------
    
    @Override public void addInsDataListener(ActionListener listener) {
    	
    	insData.addActionListener(listener); }
    
	//----------------------------------------------------------------
    
    @Override public void addAnnullaListener(ActionListener listener) {
    	
    	annulla.addActionListener(listener); 
    	
    }
    
	//----------------------------------------------------------------
    
    @Override public void addAvantiListener(ActionListener listener) { 
    	
    	avanti.addActionListener(listener); 
    	
    }
    
	//----------------------------------------------------------------

    @Override public JPanel getMainPanel() { return this; }
    
	//----------------------------------------------------------------

    @Override public void setEliminaEnabled(boolean b) { elimina.setEnabled(b); }
    
	//----------------------------------------------------------------
    
    @Override public void setSaveModsEnabled(boolean b) { saveMods.setEnabled(b); }
    
	//----------------------------------------------------------------
    
    @Override public void setInsDataEnabled(boolean b) { insData.setEnabled(b); }
    
	//----------------------------------------------------------------
    
}
