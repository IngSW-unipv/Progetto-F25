package it.unipv.poisw.f25.gympal.GUI.Utilities.FontSetter;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class FontSetter implements IFontSetter {

	@Override
	public void setDefaultFont(Font newFont) {
		
		/*"UIManager.getLookAndFeelDefaults()" restituisce tutte le proprietà 
		 *predefinite del look-and-feel corrente.*/
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		
		/*"defaults.keys" restituisce un elenco in formato "Enumeration" di tutte le
		 *chiavi disponibili nel contesto del look-and-feel corrente ("Label.font", 
		 *"Button.font", "MenuItem.font")*/
		Enumeration<Object> keys = defaults.keys();
		
		
		/*Il "while" scorre tutte le chiavi disponibili nel look-and-feel, e per ciascuna
		 *ricava il valore associato.
		 *Tramite "if", è verificato se il valore individuato è un tipo di font, infatti,
		 *"Font.class.isAssignableFrom(value.getClass())" significa: il valore è un og-
		 *getto di tipo "Font", oppure una sottoclasse?
		 *Qualora la risposta sia affermativa, allora il font corrente è sostituito da
		 *"newFont" (passato al metodo come parametro).*/
		while(keys.hasMoreElements()) {
			
			Object key = keys.nextElement();
			Object value = defaults.get(key);
			
			if(value != null && Font.class.isAssignableFrom(value.getClass())) {
				
				
				UIManager.put(key, newFont);
				
			}
			
		}
		
	}
	
}
