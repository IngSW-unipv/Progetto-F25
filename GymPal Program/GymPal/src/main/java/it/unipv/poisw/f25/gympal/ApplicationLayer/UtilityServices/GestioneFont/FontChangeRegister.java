package it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.FontManager.IFontManager;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class FontChangeRegister implements IFontChangeRegister{
	
	
    private IFontManager fontManager;
    
    private Map<Class<? extends Component>, 
    		    BiConsumer<Component, Font>> fontAwareUpdaters;
    
    //------------------------------------------------------------

    public FontChangeRegister(IFontManager fontManager) {
    	
        this.fontManager = fontManager;
        fontAwareUpdaters = createFontAwareUpdaters();
        
    }
    
    //------------------------------------------------------------

    @Override
    public void register(Component comp, IDynamicButtonSizeSetter buttonSizeSetter) {
        fontManager.addFontChangeListener(newFont -> {
             	
            aggiornaFontRecursivo(comp, newFont);

            // Doppia posticipazione computazione in EDT
            SwingUtilities.invokeLater(() ->
                SwingUtilities.invokeLater(() -> {
                    buttonSizeSetter.recomputeAll();

                })
            );
        });
    }
    
    
    @Override
    public void register(Component comp) {
    	
    	fontManager.addFontChangeListener(newFont -> {
         	
            aggiornaFontRecursivo(comp, newFont);});
    	
    }
    
    //------------------------------------------------------------

    /* Aggiorna ricorsivamente un componente Swing ed i figli ivi contenuti*/
    private void aggiornaFontRecursivo(Component comp, Font font) {
    	
    	/*Applicazione nuovo font a componente attuale*/
        comp.setFont(font);

        /* Iterazione su componenti sensibili al font, registrati nella mappa
         * "fontAwareUpdaters".
         * 
         * Ad ogni componente nella mappa è associata una funzione "BiConsumer",
         * avente per scopo calcolo di dimensioni basate su nuovo
         * font, ed applicazione di suddette dimensioni al componente.*/
        for (Map.Entry<Class<? extends Component>, 
        	 BiConsumer<Component, Font>> entry : fontAwareUpdaters.entrySet()) {
        	
        	/* "comp.GetClass" è sottotipo della classe nella mappa?*/
            if (entry.getKey().isAssignableFrom(comp.getClass())) {
            	
            	/* True --> esecuzione funzione "BiConsumer"
            	 * NOTA: "BiConsumer" riceve il componente soggetto a cambiamento
            	 * 		  ed il font in base al quale tale cambiamento è operato.*/
                entry.getValue().accept(comp, font);
                
            }
            
        }

        /* Comp contenitore? Si: ricorsione su figli */
        if (Container.class.isAssignableFrom(comp.getClass())) {
        	
            Component[] children = ((Container) comp).getComponents();
            
            for (Component child : children) {
            	
                aggiornaFontRecursivo(child, font);
                
            }
            
        }

        comp.revalidate();
        comp.repaint();
        
    }
    
    //------------------------------------------------------------
    
    /* Metodo preposto a costruzione di mappa che lega funzioni "BiComponent" a
     * tipi di componenti Swing.
     * 
     * Le "BiConsumer" aggiornano le dimensioni dei componenti sulla base del nuovo
     * font applicato.*/
    private Map<Class<? extends Component>, 
    			BiConsumer<Component, Font>> createFontAwareUpdaters() {
    	
    	/* Chiave = tipo di componente Swing
    	 * Valore = funzione "BiConsumer" che accetta un componente Swing 
    	 * 			ed un font. Ricalcola ed applica la dimensione preferita.*/
        Map<Class<? extends Component>, 
            BiConsumer<Component, Font>> map = new HashMap<>();

        /* "BiComponent" per "JTextField"*/
        map.put(JTextField.class, (comp, font) -> {
        	
        	/* Cast del Component a JTextField*/
            JTextField tf = (JTextField) comp;
            
            /* "FontMetrics" misura le dimensioni del componente basate sul font*/
            FontMetrics metrics = tf.getFontMetrics(font);
            
            /* Numero colonne maggiore di '0'?
             * Si: imposta numero colonne preso da 'tf'
             * No: imposta numero colonne a '10' (Fallback)*/
            int columns = tf.getColumns() > 0 ? tf.getColumns() : 10;

            /* Costringe Swing ad eseguire un ricalcolo interno della larghezza
             * delle colonne.*/
            tf.setColumns(0);
            tf.setColumns(columns);

            /* Larghezza basata su numero di colonne, moltiplicata per larghezza
             * di un carattere tipico ('M', in questo caso).
             * Aggiunta una spaziatura minima (+10).*/
            int width = metrics.charWidth('M') * columns + 10;
            
            /* Altezza basata su altezza del font + padding (+6)*/
            int height = metrics.getHeight() + 6;

            Dimension newSize = new Dimension(width, height);
            tf.setPreferredSize(newSize);
            tf.setMinimumSize(newSize);
            tf.setMaximumSize(newSize);

            tf.revalidate();
            tf.repaint();

            // Anche sul contenitore per sicurezza
            Container parent = tf.getParent();
            if (parent != null) {
                parent.revalidate();
                parent.repaint();
            }
            
        });

        /* "BiComponent" per "JButton"*/
        map.put(JButton.class, (comp, font) -> {
        	
            JButton btn = (JButton) comp;
            FontMetrics metrics = btn.getFontMetrics(font);
            String text = btn.getText() != null ? btn.getText() : "";
            int width = metrics.stringWidth(text) + 20;
            int height = metrics.getHeight() + 6;
            applySize(btn, width, height);
            
        });

        /* "BiComponent" per "JComboBox"*/
        map.put(JComboBox.class, (comp, font) -> {
        	
            JComboBox<?> combo = (JComboBox<?>) comp;
            FontMetrics metrics = combo.getFontMetrics(font);
            Object selectedItem = combo.getSelectedItem();
            int width = selectedItem != null ? 
            			metrics.stringWidth(selectedItem.toString()) + 30 : 100;
            
            int height = metrics.getHeight() + 6;
            applySize(combo, width, height);
            
        });

        /* "BiComponent" per "JSpinner"*/
        map.put(JSpinner.class, (comp, font) -> {
        	
            JSpinner spinner = (JSpinner) comp;
            FontMetrics metrics = spinner.getFontMetrics(font);
            int width = 120;
            int height = metrics.getHeight() + 6;
            applySize(spinner, width, height);
            
        });

        return map;
    }
    
    //------------------------------------------------------------
    
    private void applySize(Component comp, int width, int height) {
    	
        Dimension newSize = new Dimension(width, height);

        comp.setPreferredSize(newSize);
        //comp.setMinimumSize(null);
        //comp.setMaximumSize(null);
        
    }
    
    //------------------------------------------------------------

}
