package me.blaise.ExtendAdminGUI;


import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.GenericTextField;

public class PlayerBox extends GenericTextField
{

    public PlayerChooser parent;
    public int id;
    public String var;
    
    public PlayerBox(PlayerChooser parent)
    {
    	this.parent=parent;
    }

    public void onTextFieldChange(TextFieldChangeEvent event)
    {
    	this.setFocus(true);
    	this.text=event.getNewText();
        System.out.println("TextFieldChangeEvent - new text is: "+event.getNewText()+" - focus: "+isFocused());
        this.parent.updateText();
    }
}
