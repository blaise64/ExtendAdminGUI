package me.blaise.lib.gui;


import org.getspout.spoutapi.gui.*;

public abstract class CustomContainer extends GenericContainer
{
	public abstract void handleTextFieldChange(TextField field, String newValue);

	public abstract void handleClick(Button button);
	
	public abstract void handleSelected(int item, boolean doubleClick);
	
	public abstract boolean hasWidget(Widget w);
	
}