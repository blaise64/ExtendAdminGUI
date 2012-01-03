package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;


/**
 * @author blaise
 *
 */
public class NavButton extends GenericButton
{
	public int id;
	public static enum NavButtonType {PREV, NEXT, FIND}
	Navigable parent;
	public NavButtonType type;

	
	public NavButton( String name, NavButtonType type, Navigable parent)
	{
		this.parent=parent;
		this.type=type;
		setText(name);
		setColor(ExtendAdminGUI.COMMAND_BUTTON_COLOUR);
		setHoverColor(ExtendAdminGUI.BUTTON_HOVER_COLOUR);
		setDisabledColor(ExtendAdminGUI.DISABLED_BUTTON_COLOUR);
		this.setMargin(1,5);
		this.setFixed(true);
	}
	
	public void onButtonClick(ButtonClickEvent event)
	{
		if(this.type==NavButtonType.NEXT)
			this.parent.navigateNext();
		else if(this.type==NavButtonType.PREV)
			this.parent.navigatePrevious();
		else if(this.type==NavButtonType.FIND)
			this.parent.search();
	}
}
