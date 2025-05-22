package it.unipv.poisw.f25.gympal.factories;


import java.awt.Component;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;


public class EtichettaPiuCampoFactory {

	/*Avendo definito statico il metodo non c'è bisogno di istanziare la classe per usarlo.*/
	
    public static Box creaCampoEtichettato(String testoEtichetta, JComponent campo ) {
    	
        JLabel etichetta = new JLabel(testoEtichetta);
        
        
        /*Costringe il BoxLayout a rispettare la dimensione dei Field*/
        campo.setMaximumSize(campo.getPreferredSize());
        
        Box scatolaOrizzontale = Box.createHorizontalBox();
        scatolaOrizzontale.add(etichetta);
        scatolaOrizzontale.add(Box.createHorizontalStrut(10));
        scatolaOrizzontale.add(campo);
        scatolaOrizzontale.setAlignmentX(Component.CENTER_ALIGNMENT);

        return scatolaOrizzontale;
    }
	
}
