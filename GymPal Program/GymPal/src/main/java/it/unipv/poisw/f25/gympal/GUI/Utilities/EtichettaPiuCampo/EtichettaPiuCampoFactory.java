package it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo;


import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;


public class EtichettaPiuCampoFactory implements IEtichettaPiuCampoFactory{

	private static final int LABEL_WIDTH = 150;	
	
    //----------------------------------------------------------------
	
    public Box creaCampoEtichettato(String testoEtichetta, JComponent campo ) {
    	
        JLabel etichetta = new JLabel(testoEtichetta);


        etichetta.setPreferredSize(new Dimension(LABEL_WIDTH, etichetta.getPreferredSize().height));
        etichetta.setMaximumSize(etichetta.getPreferredSize());
        /*Costringe il BoxLayout a rispettare la dimensione dei Field*/
        campo.setMaximumSize(campo.getPreferredSize());
        
        Box scatolaOrizzontale = Box.createHorizontalBox();
        scatolaOrizzontale.add(etichetta);
        scatolaOrizzontale.add(Box.createHorizontalStrut(10));
        scatolaOrizzontale.add(campo);
        scatolaOrizzontale.setAlignmentX(Component.CENTER_ALIGNMENT);

        return scatolaOrizzontale;
    }
    
    //----------------------------------------------------------------
	
}
