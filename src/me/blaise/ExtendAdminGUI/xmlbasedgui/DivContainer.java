package me.blaise.ExtendAdminGUI.xmlbasedgui;

import java.util.ArrayList;

import me.blaise.ExtendAdminGUI.gui.ExtendAdminGUI;
import me.blaise.lib.gui.GridContainer;
import me.blaise.lib.xml.XmlUtils;

import org.w3c.dom.Element;

public class DivContainer extends GridContainer
{
	private Element node;
	private ExtendAdminGUI app_root;
	private DivContainer parent;
	private int rowspan;
	private int colspan;
	private int row;
	private int col;
	
	public DivContainer(Element node, int nbrows, int nbcols, int colwidth, int rowheight, ExtendAdminGUI app_root)
	{
		super(nbrows, nbcols, colwidth, rowheight);
		this.node=node;
		this.app_root=app_root;
		this.parent=null;
		
		if(this.parent!=null){
			this.nbrows=(new Integer(node.getAttribute("nbrows"))).intValue();
			this.nbcols=(new Integer(node.getAttribute("nbcols"))).intValue();
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
				this.setWidth(this.parent.colwidth*colspan);
			}

			if(node.hasAttribute("height"))
			{ 
				this.setHeight((new Integer(node.getAttribute("height"))).intValue());
			}
			else
			{
				if(rowheight>15)
				{
					this.setHeight(15);
				}
				else
				{
					this.setHeight(this.parent.rowheight*rowspan);
				}
			}
			this.parent.addElement(this, this.row, this.col, this.colspan, this.rowspan);
		}
		
		ArrayList<Element> commandbuttons=XmlUtils.getChildrenByTagName(this.node,"commandbutton");
		System.out.println("commandbutton size is"+commandbuttons.size());
		for(Element el:commandbuttons)
        {
            new CommandButton(el, this.rowheight, this.colwidth, this, this.app_root);
        }
		
		ArrayList<Element> paramboxes=XmlUtils.getChildrenByTagName(this.node,"parambox");
		System.out.println("paramboxes size is"+paramboxes.size());
		for(Element el:paramboxes)
        {
            new EntryBox(el, this.rowheight, this.colwidth, this, this.app_root);
        }
		
		ArrayList<Element> linkbuttons=XmlUtils.getChildrenByTagName(this.node,"linkbutton");
		System.out.println("linkbuttons size is"+linkbuttons.size());
		for(Element el:linkbuttons)
        {
            new LinkButton(el, this.rowheight, this.colwidth, this, this.app_root);
        }
		
		ArrayList<Element> playerboxes=XmlUtils.getChildrenByTagName(this.node,"playerbox");
		for(Element el:playerboxes)
        {
            new PlayerChooser(el, this.rowheight, this.colwidth, this, this.app_root);
        }
		
		ArrayList<Element> div=XmlUtils.getChildrenByTagName(this.node,"div");
		System.out.println("div size is"+div.size());
		for(Element el:div)
        {
            new DivContainer(el, this, this.app_root);
        }
		
	}
	
	public DivContainer(Element node, DivContainer parent, ExtendAdminGUI app_root)
	{
		super(0, 0, 0, 0);
		this.node=node;
		this.app_root=app_root;
		this.parent=parent;
		this.nbrows=(new Integer(node.getAttribute("nbrows"))).intValue();
		this.nbcols=(new Integer(node.getAttribute("nbcols"))).intValue();
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
			this.setWidth(this.parent.colwidth*colspan);
		}
	
		if(node.hasAttribute("height"))
		{ 
			this.setHeight((new Integer(node.getAttribute("height"))).intValue());
		}
		else
		{
			if(rowheight>15)
			{
				this.setHeight(15);
			}
			else
			{
				this.setHeight(this.parent.rowheight*rowspan);
			}
		}
		this.parent.addElement(this, this.row, this.col, this.colspan, this.rowspan);
		
		this.rowheight=(this.height)/this.nbrows;
    	this.colwidth=(this.width)/this.nbcols;
    	this.setX(0).setY(0);
		this.setFixed(true);
		
		ArrayList<Element> commandbuttons=XmlUtils.getChildrenByTagName((Element)this.node,"commandbutton");
		for(Element el:commandbuttons)
        {
            new CommandButton(el, this.rowheight, this.colwidth, this, this.app_root);
        }
		
		ArrayList<Element> paramboxes=XmlUtils.getChildrenByTagName((Element)this.node,"parambox");
		for(Element el:paramboxes)
        {
            new EntryBox(el, this.rowheight, this.colwidth, this, this.app_root);
        }
		
		ArrayList<Element> linkbuttons=XmlUtils.getChildrenByTagName((Element)this.node,"linkbutton");
		for(Element el:linkbuttons)
        {
            new LinkButton(el, this.rowheight, this.colwidth, this, this.app_root);
        }
		
		ArrayList<Element> div=XmlUtils.getChildrenByTagName((Element)this.node,"div");
		for(Element el:div)
        {
            new DivContainer(el, this, this.app_root);
        }
	}
	
}