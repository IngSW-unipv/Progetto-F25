package it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.FontManager;

import java.awt.Font;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FontManager implements IFontManager{
	
	private List<IFontChangeListener> listeners = new ArrayList<>();
	//private Font currentFont;
	 
	//----------------------------------------------------------------
	
    @Override
    public void updateFont(Font newFont) {

        // Aggiorna font nell'UIManager
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                UIManager.put(key, newFont);
            }
        }

        // Aggiorna tutte le finestre
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
            window.invalidate();
            window.validate();
            window.repaint();
        }

        // Notifica tutti i listener (sempre, anche se il font non cambia)
        for (IFontChangeListener listener : listeners) {
            listener.onFontChange(newFont);
        }
    }
	
	//----------------------------------------------------------------
	
	@Override
    public void addFontChangeListener(IFontChangeListener listener) {
		
        listeners.add(listener);
        
    }

	@Override
    public void removeFontChangeListener(IFontChangeListener listener) {
		
        listeners.remove(listener);
        
    }
    
	//----------------------------------------------------------------

}
