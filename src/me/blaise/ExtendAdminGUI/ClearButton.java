package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class ClearButton extends GenericButton {
	Clearable parent;
	
	public ClearButton(Clearable parent)
	{
		//reference to the parent screen
		this.parent = parent;
		this.setText("clear");
		this.setHeight(15);
		this.setWidth(30);
		
		setColor(ExtendAdminGUI.COMMAND_BUTTON_COLOUR);
		setHoverColor(ExtendAdminGUI.BUTTON_HOVER_COLOUR);
		setDisabledColor(ExtendAdminGUI.DISABLED_BUTTON_COLOUR);
		
		this.setMargin(1,3);
		this.setFixed(true);
	}
	
	public void onButtonClick(ButtonClickEvent event)
	{
		//SpoutPlayer user = event.getPlayer();
		this.parent.clear();
	}
}
