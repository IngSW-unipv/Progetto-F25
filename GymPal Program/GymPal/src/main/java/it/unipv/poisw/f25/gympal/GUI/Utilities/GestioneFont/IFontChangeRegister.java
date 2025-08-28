package it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont;

import java.awt.Component;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public interface IFontChangeRegister {
	
	public void register(Component comp, IDynamicButtonSizeSetter buttonSizeSetter);
	
	public void register(Component comp);

}
