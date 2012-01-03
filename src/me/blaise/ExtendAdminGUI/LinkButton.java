package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.w3c.dom.Element;

public class LinkButton extends GenericButton{
	
	public ExtendAdminGUI app_root;
	public int col;
	public String target;
	public int id;
	public String info;
	public Element node;
	public GridContainer parent;
	public int row;
	public boolean stay_open;
	private int rowspan=1;
	private int colspan=1;

	public LinkButton(Element node, int rowheight, int colwidth, GridContainer parent, ExtendAdminGUI app_root) {
		this.parent = parent;
		this.app_root=app_root;
		this.node=node;
	
		if(node.hasAttribute("stay_open"))
		{
			this.stay_open=(new Boolean(node.getAttribute("stay_open"))).booleanValue();
		}
		else
		{
			this.stay_open = false;
		}
	
		if(node.hasAttribute("label"))
		{
			setText(node.getAttribute("label"));
		}
		else
		{
			setText("NoNameButton");
		}
	
		if(node.hasAttribute("target"))
		{
			this.target=node.getAttribute("target");
		}
		else
		{
			this.target = "";
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
			this.info=this.target;
		}
	
		setColor(ExtendAdminGUI.COMMAND_BUTTON_COLOUR);
		setHoverColor(ExtendAdminGUI.BUTTON_HOVER_COLOUR);
		setDisabledColor(ExtendAdminGUI.DISABLED_BUTTON_COLOUR);
		
		//request to be placed on the container grid
		this.parent.addElement(this, this.row, this.col, this.colspan, this.rowspan);
		//standard skin parameters
		this.setMargin(1,5);
		this.setTooltip(this.info);
		this.setFixed(true);
		
		//debug
		if(this.app_root.plugin.config.getBoolean("debug_mode"))
			this.app_root.log((new StringBuilder("[ExtendAdminGUI - debug] new linkbutton :")).append(" command "+target).append(" col "+col).append(" row "+row).append("size is: h"+this.getHeight()+" w "+this.getWidth()).toString());
		
		//TODO: better coloration
	}
	
	public void onButtonClick(ButtonClickEvent event)
	{
		this.app_root.goTo(target);
		//debug
		if(this.app_root.plugin.config.getBoolean("debug_mode"))
			this.app_root.log((new StringBuilder("[ExtendAdminGUI - debug] going to screen ")).append(target).toString());
	}
}