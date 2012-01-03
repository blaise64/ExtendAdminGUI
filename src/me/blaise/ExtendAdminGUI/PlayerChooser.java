package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericTextField;
import org.w3c.dom.Element;

public class PlayerChooser extends GenericContainer
{

    public ExtendAdminGUI app_root;
    public int col;
    public int id;
    public String info;
    public Element node;
    public GridContainer parent;
    public int row;
    public String var;
	private GenericTextField entry;
	private GenericButton button;
	private String text;
	private int rowspan=1;
	private int colspan=1;
    
    public PlayerChooser(Element node, int rowheight, int colwidth, GridContainer parent, ExtendAdminGUI app_root)
    {
    	//reference to the parent screen
        this.parent = parent;
        this.app_root=app_root;
        this.node=node;
    	this.setFixed(true);
    	this.setLayout(ContainerType.HORIZONTAL);
    	this.setMargin(1,10,1,5);

    	//skin parameters
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
			this.setMaxWidth((new Integer(node.getAttribute("width"))).intValue());
			this.setWidth((new Integer(node.getAttribute("width"))).intValue());
		}
		else
		{
			this.setMaxWidth(colwidth*colspan);
			this.setWidth(colwidth*colspan);
		}

		if(node.hasAttribute("height"))
		{ 
			this.setMaxHeight((new Integer(node.getAttribute("height"))).intValue());
			this.setHeight((new Integer(node.getAttribute("height"))).intValue());
		}
		else
		{
			if(rowheight>15)
			{
				this.setHeight(15);
				this.setMaxHeight(15);
			}
			else
			{
				this.setHeight(rowheight);
				this.setMaxHeight(rowheight);
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
    	
    	if(node.hasAttribute("var"))
    	{
    		this.var=node.getAttribute("var");
    	}
    	else
    	{
    		this.var = "no_name_param";
    	}
    	
    	//creation of the main elements
    	
    	this.entry=new PlayerBox(this);
    	this.entry.setWidth(this.getWidth()-33);
    	this.entry.setMaxWidth(this.getMaxWidth()-33);
    	this.entry.setHeight(this.getHeight());
    	this.entry.setMaxHeight(this.getMaxHeight());
    	this.entry.setFixed(true);
    	this.entry.setMargin(0,5,0,0);
    	
    	this.button=new SelectBoxButton(this);
    	this.button.setHeight(this.getHeight());
    	this.button.setMaxHeight(this.getMaxHeight());
    	this.button.setMaxWidth(15);
    	this.button.setMargin(0,5,0,0);
    	this.button.setFixed(true);
    	
    	if(node.hasAttribute("default"))
    	{
    		setText(node.getAttribute("default"));
    	}
    	
    	this.entry.setTooltip(this.info);
    	this.button.setTooltip("Select "+this.var+" on the player panel");
        this.addChild(this.entry);
        this.addChild(this.button);
        //request to be placed on the container grid
        this.parent.addElement(this, this.row, this.col, this.colspan, this.rowspan);
        //register the parameter
        this.app_root.registerParameter(this.var, this.entry);
        //debug
        if(this.app_root.plugin.config.getBoolean("debug_mode"))
    		this.app_root.log((new StringBuilder("[ExtendAdminGUI - debug] new playerbox :")).append(" param "+this.var).append(" col "+col).append(" row "+row).toString());
    }
    
    public void setText(String text) {
    	this.text=text;
		this.entry.setText(this.text);
	}

	public void updateText() {
		this.text=this.entry.getText();
	}

	public void select() {
		this.app_root.registerLastEntry(this);
	}
	
    public String getText() {
		this.text=this.entry.getText();
		return this.text;
	}

}
