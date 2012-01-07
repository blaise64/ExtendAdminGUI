package me.blaise.ExtendAdminGUI;


public class ChoosableListWidget extends CorrectedListWidget{
	
	private Chooser parent;
	private String var;
	public ChoosableListWidget(Chooser parent, String var)
	{
		this.parent=parent;
		this.var=var;
	}
	  public void onSelected(int item, boolean doubleClick)
	  {
		  super.onSelected(item, doubleClick);
		  this.parent.choose(this.var, this.getSelectedItem().getText());
	  }
}