package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class SelectBoxButton extends GenericButton {

	private PlayerChooser parent;

	public SelectBoxButton(PlayerChooser parent)
	{
		this.parent=parent;
		setText("X");
		setColor(ExtendAdminGUI.COMMAND_BUTTON_COLOUR);
		setHoverColor(ExtendAdminGUI.BUTTON_HOVER_COLOUR);
		setDisabledColor(ExtendAdminGUI.DISABLED_BUTTON_COLOUR);
	}

	public void onButtonClick(ButtonClickEvent event)
	{
			this.parent.select();
	}
}
