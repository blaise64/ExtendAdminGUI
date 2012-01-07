package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.gui.GenericListWidget;
import org.getspout.spoutapi.gui.Orientation;

public class CorrectedListWidget extends GenericListWidget{
	  public void onSelected(int item, boolean doubleClick)
	  {
		  if(this.getHeight()<(20*(this.getItems().length-1)))
			  this.setScrollPosition(20*this.getSelectedRow());
		  else
			  this.setScrollPosition(0);
		  if(this.needsScrollBar(Orientation.VERTICAL))
			  System.out.println("need scrollbar"+this.getHeight()+" "+this.getItems().length);
//			  System.out.println(""+this.getMaxScrollPosition()+"  "+this.getItems().length+" "+this.getSelectedRow()+"  "+this.getScrollPosition(Orientation.VERTICAL));
	}
}