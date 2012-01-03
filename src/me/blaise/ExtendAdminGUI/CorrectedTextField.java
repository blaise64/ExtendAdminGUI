package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.GenericTextField;

public class CorrectedTextField extends GenericTextField {
	 public void onTextFieldChange(TextFieldChangeEvent event)
	    {
	    	this.setFocus(true);
	    	//this.text=event.getNewText();
	    }
}
