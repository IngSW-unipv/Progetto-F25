package it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.FontManager.IFontManager;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class FontChangeRegister implements IFontChangeRegister{
	
    private IFontManager fontManager;
    
    //------------------------------------------------------------

    public FontChangeRegister(IFontManager fontManager) {
    	
        this.fontManager = fontManager;
        
    }
    
    //------------------------------------------------------------

    @Override
    public void register(Component comp, IDynamicButtonSizeSetter buttonSizeSetter) {
        fontManager.addFontChangeListener(newFont -> {
             	
            aggiornaFontRecursivo(comp, newFont);

            // Posticipa la computazione due volte nel ciclo eventi
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

    private void aggiornaFontRecursivo(Component comp, Font font) {
        comp.setFont(font);
        if (comp instanceof Container) {
        	
            for (Component child : ((Container) comp).getComponents()) {
            	
                aggiornaFontRecursivo(child, font);
                
            }
            
        }
        
    }
    
    //------------------------------------------------------------

}
