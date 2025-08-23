package it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VistaEControllore;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class VisualizzaTurniView extends JPanel implements IVisualizzaTurniView{

	private static final long serialVersionUID = 1L;
	
	private JTable turniTable;
	
    //------------------------------------------------------------
	
	public VisualizzaTurniView() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                  BorderFactory.createLineBorder(Color.GRAY),
                  new EmptyBorder(10, 10, 10, 10)));
        initTable();
		
	}
	
    //------------------------------------------------------------
	
    private void initTable() {
    	
        turniTable = new JTable();
        turniTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(turniTable);
        add(scrollPane, BorderLayout.CENTER);
        
    }
	
    //------------------------------------------------------------
    
    @Override
    public JTable getTurniTable() {
    	
        return turniTable;
        
    }
    
    //------------------------------------------------------------
	
	@Override
	public JPanel getMainPanel() {return this;}
	
    //------------------------------------------------------------

}
