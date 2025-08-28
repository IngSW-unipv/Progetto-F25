package it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractButton;

public class DynamicButtonSizeSetter implements IDynamicButtonSizeSetter {
	
	private final Set<Set<AbstractButton>> gruppiBottoni = new HashSet<>();
	
	//----------------------------------------------------------------

	@Override
	public void uniformButtonSize(List<? extends AbstractButton> buttons) {
		
		uniformButtonSize(buttons.toArray(new AbstractButton[0]));
		
	}
	
	//----------------------------------------------------------------
	
	/*La sintassi "AbstractButton... buttons" esemplifica una 'varargs' ("variable
	 *length arguments list"), una funzionalità di Java che consente di passare ad un
	 *metodo un numero arbitrario di argomenti, come fossero un array.*/
	
	@Override
	public void uniformButtonSize(AbstractButton... buttons) {
		
        Set<AbstractButton> gruppo = new HashSet<>(Arrays.asList(buttons));
        gruppiBottoni.add(gruppo);
        aggiornaDimensioniGruppo(gruppo);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
    public void recomputeAll() {
    	
        for (Set<AbstractButton> gruppo : gruppiBottoni) {
        	
            aggiornaDimensioniGruppo(gruppo);
            
        }
        
    }
    
	//----------------------------------------------------------------

    private void aggiornaDimensioniGruppo(Set<AbstractButton> gruppo) {
    	
        int maxWidth = 0;
        int maxHeight = 0;

        // Annullata dimensione precedente per rilevare nuova preferredSize
        for (AbstractButton b : gruppo) {
        	
            b.setPreferredSize(null);
            
        }

        for (AbstractButton b : gruppo) {
        	
            Dimension pref = b.getPreferredSize();
            
            ///////////////////////////////////////
            /*System.out.printf("Bottone: \"%s\" | Font: %s | Preferred: %s%n",
                    b.getText(), b.getFont(), pref);*/
            ///////////////////////////////////////
            
            maxWidth = Math.max(maxWidth, pref.width);
            maxHeight = Math.max(maxHeight, pref.height);
            
        }

        Dimension uniform = new Dimension(maxWidth, maxHeight);
        
        //////////////////////////////////////////////////
        //System.out.println(">> Recomputing buttons. New max size: " + uniform);
        //////////////////////////////////////////////////
        
        for (AbstractButton b : gruppo) {
        	
            b.setPreferredSize(uniform);
            
        }
        
        if (!gruppo.isEmpty()) {
        	
            Component parent = gruppo.iterator().next().getParent();
            if (parent != null) {
            	/* Posti in questo punto del programma, 'revalidate' e 'repaint'
            	 * assicurano la corretta visualizzazione dei JButton aggiornati
            	 * per contenere il testo al loro interno, a seguito di cambia-
            	 * menti nelle dimensioni del font.*/
                parent.revalidate();
                parent.repaint();
                
            }
            
        }        
        
    }
    
	//----------------------------------------------------------------
	
}
