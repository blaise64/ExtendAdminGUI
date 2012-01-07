package me.blaise.ExtendAdminGUI.xmlbasedgui;

import me.blaise.ExtendAdminGUI.gui.ExtendAdminGUI;
import me.blaise.lib.gui.GridContainer;

import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.GenericTextField;
import org.w3c.dom.Element;

public class EntryBox extends GenericTextField
{

    public ExtendAdminGUI app_root;
    public int col;
    public int id;
    public String info;
    public Element node;
    public GridContainer parent;
    public int row;
    public boolean start_new_row;
    public String var;
	private int rowspan=1;
	private int colspan=1;
	
    public EntryBox(Element node, int rowheight, int colwidth, GridContainer parent, ExtendAdminGUI app_root)
    {
    	this.setAutoDirty(false);
    	//reference to the parent screen
        this.parent = parent;
        this.app_root=app_root;
        this.node=node;        

        //get the xml parameters
    	if(node.hasAttribute("default"))
    	{
    		setText(node.getAttribute("default"));
    	}
    	
    	if(node.hasAttribute("var"))
    	{
    		this.var=node.getAttribute("var");
    	}
    	else
    	{
    		this.var = "no_name_param";
    	}
    	
    	if(node.hasAttribute("row"))
		{
			this.row = (new Integer(node.getAttribute("row"))).intValue();
		}
		else
		{
			this.row = 1;
		}

		if(node.hasAttribute("col"))
		{
			this.col=(new Integer(node.getAttribute("col"))).intValue();
		}
		else
		{
			this.col = 1;
		}
		if(node.hasAttribute("rowspan"))
		{
			this.rowspan = (new Integer(node.getAttribute("rowspan"))).intValue();
		}
		
		if(node.hasAttribute("colspan"))
		{
			this.colspan = (new Integer(node.getAttribute("colspan"))).intValue();
		}

		if(node.hasAttribute("width"))
		{
			this.setWidth((new Integer(node.getAttribute("width"))).intValue());
		}
		else
		{
			this.setWidth(colwidth*colspan);
		}

		if(node.hasAttribute("height"))
		{ 
			this.setHeight((new Integer(node.getAttribute("height"))).intValue());
		}
		else
		{
			if(rowheight>15)
			{
				this.setHeight(15*rowspan);
			}
			else
			{
				this.setHeight(rowheight*rowspan);
			}

		}
    	
    	if(node.hasAttribute("info"))
    	{
    		this.info=node.getAttribute("info");
    	}
    	else
    	{
    		if(node.hasAttribute("default"))
        	{
        		this.info=node.getAttribute("default");
        	}
    		else
    		{
    			this.info=this.var;
    		}
    	}
    	
    	this.setMaximumCharacters(64);
    	this.setMaximumLines(3);
    	System.out.println("max char is "+this.getMaximumCharacters()+" max lines is "+this.getMaximumLines()+" tab index is "+this.getTabIndex());

        //request to be placed on the container grid
		this.parent.addElement(this, this.row, this.col, this.colspan, this.rowspan);
		//standard skin parameters
		this.setMargin(1,5);
		this.setTooltip(this.info);
		this.setFixed(true);
		
        //register the parameter
        this.app_root.registerParameter(this.var, this);
        
    }
    
    public void onTextFieldChange(TextFieldChangeEvent event)
    {
    	this.setFocus(true);
    	this.text=event.getNewText();
        System.out.println("TextFieldChangeEvent - new text is: "+event.getNewText()+" - focus: "+isFocused());
        //this.app_root.registerParameter(var, text);
    }
}
