package it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons;

import java.util.List;

import javax.swing.AbstractButton;

public interface IDynamicButtonSizeSetter {
	
	public void uniformButtonSize(List<? extends AbstractButton> buttons);
	
	//----------------------------------------------------------------
	
	public void uniformButtonSize(AbstractButton... buttons);
	
	//----------------------------------------------------------------

}
