package me.blaise.ExtendAdminGUI;


import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.GenericTextField;

public class SearchBox extends GenericTextField
{

    public Navigable parent;
    public int id;
    public String var;
    
    public SearchBox(Navigable parent)
    {
    	this.parent=parent;
    }

    public void onTextFieldChange(TextFieldChangeEvent event)
    {
    	this.setFocus(true);
    	this.text=event.getNewText();
    	this.parent.search();
    }
}
