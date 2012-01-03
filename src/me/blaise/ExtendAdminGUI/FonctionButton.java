package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class FonctionButton extends GenericButton {
	CanExecuteFonction parent;
	
	public FonctionButton(CanExecuteFonction parent, String name)
	{
		this.parent=parent;
		this.setText(name);
		this.setHeight(15);
		this.setWidth(80);
		
		setColor(ExtendAdminGUI.COMMAND_BUTTON_COLOUR);
		setHoverColor(ExtendAdminGUI.BUTTON_HOVER_COLOUR);
		setDisabledColor(ExtendAdminGUI.DISABLED_BUTTON_COLOUR);
		
		this.setMargin(1,3);
		this.setFixed(true);
	}
	
	public void onButtonClick(ButtonClickEvent event)
	{
		this.parent.onButtonClick(event);
	}
}
