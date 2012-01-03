package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class ChoiceButton extends GenericButton {
	Chooser parent;
	String name;
	String var;
	
	public ChoiceButton(String var, String name, Chooser parent)
	{
		//reference to the parent screen
		this.parent = parent;
		this.var=var;
		this.setText(name);
		this.name=name;
		this.setHeight(15);
		this.setMaxHeight(15);
		
		setColor(ExtendAdminGUI.COMMAND_BUTTON_COLOUR);
		setHoverColor(ExtendAdminGUI.BUTTON_HOVER_COLOUR);
		setDisabledColor(ExtendAdminGUI.DISABLED_BUTTON_COLOUR);
		
		this.setMargin(1,5);
		this.setFixed(true);
	}
	
	public void onButtonClick(ButtonClickEvent event)
	{
		//SpoutPlayer user = event.getPlayer();
		this.parent.choose(this.var, this.name);
	}
}
