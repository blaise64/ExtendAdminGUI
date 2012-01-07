package me.blaise.lib.gui;

import org.getspout.spoutapi.gui.GenericListWidget;
public class CorrectedListWidget extends GenericListWidget{
	  public void onSelected(int item, boolean doubleClick)
	  {
		  if(this.getHeight()<(20*(this.getItems().length-1)))
			  this.setScrollPosition(20*this.getSelectedRow());
		  else
			  this.setScrollPosition(0);
	}
}