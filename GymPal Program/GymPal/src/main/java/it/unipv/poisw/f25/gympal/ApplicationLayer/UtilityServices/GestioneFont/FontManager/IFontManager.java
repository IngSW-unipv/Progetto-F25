package it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.FontManager;

import java.awt.Font;

public interface IFontManager {
	
	public void updateFont(Font newFont);
	
	//----------------------------------------------------------------
	
	public void addFontChangeListener(IFontChangeListener listener);
	
	//----------------------------------------------------------------
	
	public void removeFontChangeListener(IFontChangeListener listener);
	
	//----------------------------------------------------------------

}
