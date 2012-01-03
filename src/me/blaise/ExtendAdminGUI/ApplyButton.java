package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class ApplyButton extends GenericButton {
	Applicable parent;
	
	public ApplyButton(Applicable parent)
	{
		this.parent=parent;
		this.setText("Apply");
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
		this.parent.apply();
	}
}
