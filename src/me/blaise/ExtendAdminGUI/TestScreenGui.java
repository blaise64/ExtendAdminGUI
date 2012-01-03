package me.blaise.ExtendAdminGUI;

import java.util.ArrayList;
import org.getspout.spoutapi.gui.GenericContainer;
import org.w3c.dom.Element;

public class TestScreenGui implements Chooser
{
	ExtendAdminGUI app_root;
	int colwidth;
	int nbcols;
	int nbrows;
	Element node;
	int rowheight;
	//public ArrayList<ContainerRow> rows;
	private GenericContainer test;
	

	public TestScreenGui(ExtendAdminGUI app_root)
	{
		this.app_root=app_root;
		ArrayList<String> t= new ArrayList<String>();
		for(int i=0; i<23; i++)
			t.add(""+i);
		this.test= new Finder(this, "test",t);
		
	}
	
	public void clear()
    {
    }
	
	public GenericContainer getMainContainer() {
		    return this.test;
	}

	@Override
	public void choose(String var, String name) {
		
	}
	
}
