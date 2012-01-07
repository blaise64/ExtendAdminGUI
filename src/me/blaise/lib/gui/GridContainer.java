package me.blaise.lib.gui;

import java.util.HashMap;


import org.getspout.spoutapi.gui.*;

public class GridContainer extends GenericContainer
{
	protected HashMap<WLocation, Widget> childrenMap;
	protected int colwidth;
	protected int rowheight;
	protected int nbcols;
	protected int nbrows;
	protected autoSizeType autosize=autoSizeType.HORIZONTAL;
	protected WidgetAnchor align=WidgetAnchor.CENTER_CENTER;
	public static enum autoSizeType {HORIZONTAL, VERTICAL, CENTER, NONE}
	
	public GridContainer(int nbrows, int nbcols, int colwidth, int rowheight)
	{
		this.childrenMap = new HashMap<WLocation, Widget>();
		this.rowheight=rowheight;
		this.colwidth=colwidth;
		this.nbcols=nbcols;
		this.nbrows=nbrows;
		this.setHeight(nbrows*rowheight).setWidth(nbcols*colwidth);
		this.setX(0).setY(0);
		this.setFixed(true);
		this.updateSize();
	}
	
	public Container addChild( Widget child, int row, int col)
	{
		WLocation l =new WLocation(row, col);
		this.childrenMap.put(l, child);
		return this.insertChild(-1, child);
	}
	
	public Container addChild( Widget child, int row, int col, int colspan, int rowspan)
	{
		WLocation l =new WLocation(row, col, colspan, rowspan);
		this.childrenMap.put(l, child);
		return this.insertChild(-1, child);
	}
	
	//TODO:make an auto size parameter (ex=-1) to permit a autosize in all directions
	public Container updateLayout()
	{
		if ((!this.recalculating) && (this.getWidth() > 0) && (this.getHeight() > 0) && (!this.children.isEmpty())) {
			this.recalculating = true;
			for(WLocation loc: this.childrenMap.keySet()){
				Widget child=this.childrenMap.get(loc);
					if((child!=null) && child.isVisible())
					{
						int x=Math.max(0,this.getX());
						int y=Math.max(0,this.getY());
						x+=Math.max(0,(loc.col-1)*colwidth);
						y+=Math.max(0,(loc.row-1)*rowheight);
						
						//widget have fixed size
						if(this.autosize==autoSizeType.VERTICAL)
						{
							child.setHeight(Math.max(0,rowheight*loc.rowspan-child.getMarginTop()-child.getMarginBottom()));
						}
						else if(this.autosize==autoSizeType.HORIZONTAL)
						{
							child.setWidth(Math.max(0,colwidth*loc.colspan-child.getMarginLeft()-child.getMarginRight()));
						}
						else if(this.autosize==autoSizeType.CENTER)
						{
							child.setWidth(Math.max(0,colwidth*loc.colspan-child.getMarginLeft()-child.getMarginRight()));
							child.setHeight(Math.max(0,rowheight*loc.rowspan-child.getMarginTop()-child.getMarginBottom()));
						}
						
						//align the widget
						//to the left
						if ((this.align == WidgetAnchor.TOP_CENTER) || (this.align == WidgetAnchor.CENTER_CENTER) || (this.align == WidgetAnchor.BOTTOM_CENTER))
							x += (Math.max(0,colwidth*loc.colspan - child.getWidth())) / 2;
						else if ((this.align == WidgetAnchor.TOP_RIGHT) || (this.align == WidgetAnchor.CENTER_RIGHT) || (this.align == WidgetAnchor.BOTTOM_RIGHT)) {
							x += Math.max(0,colwidth*loc.colspan - child.getWidth() - child.getMarginRight());
						}
						else if ((this.align == WidgetAnchor.TOP_LEFT) || (this.align == WidgetAnchor.CENTER_LEFT) || (this.align == WidgetAnchor.BOTTOM_LEFT)) {
							x += Math.max(0,child.getMarginLeft());
						}
						//to the top
						if ((this.align == WidgetAnchor.CENTER_LEFT) || (this.align == WidgetAnchor.CENTER_CENTER) || (this.align == WidgetAnchor.CENTER_RIGHT))
							y += (Math.max(0,rowheight*loc.rowspan - child.getHeight())) / 2;
						else if ((this.align == WidgetAnchor.BOTTOM_LEFT) || (this.align == WidgetAnchor.BOTTOM_CENTER) || (this.align == WidgetAnchor.BOTTOM_RIGHT)) {
							y += Math.max(0,rowheight*loc.rowspan - child.getHeight() - child.getMarginBottom());
						}
						else if ((this.align == WidgetAnchor.TOP_LEFT) || (this.align == WidgetAnchor.TOP_CENTER) || (this.align == WidgetAnchor.TOP_RIGHT)) {
							y += Math.max(0,child.getMarginTop());
						}
						
						//apply the new position
						child.setX(x).setY(y);
						child=null;
						x=0;
						y=0;
					}
				
			}
			this.recalculating = false;
		}
	    this.needsLayout = false;
	    return this;
	}
	
	public void addElement(GenericWidget w, int row, int col)
	{
		this.addChild(w, row, col);
	}
	
	public void addElement(GenericWidget w, int row, int col, int colspan, int rowspan)
	{
		this.addChild(w, row, col, colspan, rowspan);
	}
	
	public void clear()
	{
		this.childrenMap.clear();
		for(Widget w : getChildren())
		{
			removeChild(w);
		}
	}
	
	public Container updateSize()
	{
		//if (hasContainer())
		//{
	    //      getContainer().updateSize();
	    //      getContainer().deferLayout();
	    //}
		return this;
	}
	
	public void put(GenericWidget w){
		//fill from left to right and from top to bottom
		int i=(this.getChildren().length)%nbcols;
		int j=(this.getChildren().length)/nbcols;
		//System.out.println(" i "+i+" j "+j);
		if(j<nbrows){
			this.addElement(w, j+1, i+1);
		}
	}	
	
}