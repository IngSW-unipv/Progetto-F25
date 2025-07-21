package it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons;

import java.awt.Dimension;
import java.util.List;

import javax.swing.AbstractButton;

public class DynamicButtonSizeSetter implements IDynamicButtonSizeSetter {

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
		
		if(buttons == null || buttons.length == 0) {return;}
		
		int maxWidth = 0;
		int maxHeight = 0;
		
		for(AbstractButton b: buttons) {
			
			Dimension pref = b.getPreferredSize();
			maxWidth = Math.max(maxWidth, pref.width);
			maxHeight = Math.max(maxHeight, pref.height);
			
		}
		
		Dimension uniform = new Dimension(maxWidth, maxHeight);
		
		for(AbstractButton b: buttons) {
			
			b.setPreferredSize(uniform);
			
		}
		
	}
	
	//----------------------------------------------------------------
	
}
