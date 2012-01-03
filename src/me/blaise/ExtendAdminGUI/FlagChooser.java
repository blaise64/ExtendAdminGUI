package me.blaise.ExtendAdminGUI;

import org.getspout.spoutapi.gui.GenericCheckBox;

public class FlagChooser extends GenericCheckBox
{
    private String flag;
    private boolean init;
	private String iftrue;
	private String iffalse;
    
    public FlagChooser(String flag, boolean initvalue, String iftrue, String iffalse)
    {
    	this.flag=flag;
    	this.init=initvalue;
    	this.iftrue=iftrue;
    	this.iffalse=iffalse;
    	
    	//reference to the parent screen
    	this.setText(this.flag);
    	this.setChecked(this.init);
    	this.setWidth(80);
    	this.setHeight(15);
    	this.setFixed(true);
    	this.setMargin(1,5);
    }
    
    public boolean inDefaultState()
    {
    	return (this.init==this.isChecked());
    }
    
    public String getValue() {
    	if(this.isChecked())
			return this.iftrue;
    	else 
    		return this.iffalse;
	}

	public void clear() {
		this.setChecked(this.init);
	}

}
