package me.blaise.lib.gui;

import java.util.HashMap;

import me.blaise.ExtendAdminGUI.gui.CanExecuteFonction;
import me.blaise.ExtendAdminGUI.gui.ExtendAdminGUI;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.Widget;

public class Notepad extends GenericContainer implements CanExecuteFonction {
	
	private GenericContainer pagebox;
	private GenericContainer ongletsbox;
	private HashMap<String, GenericButton> onglets;
	private HashMap<String, Widget> pages;
	
	public class FonctionButton extends GenericButton {
		CanExecuteFonction parent;
		
		public FonctionButton(CanExecuteFonction parent, String name)
		{
			this.parent=parent;
			this.setText(name);
			this.setHeight(15);
			this.setWidth(80);
			
			setColor(ExtendAdminGUI.COMMAND_BUTTON_COLOUR);
			setHoverColor(ExtendAdminGUI.BUTTON_HOVER_COLOUR);
			setDisabledColor(ExtendAdminGUI.DISABLED_BUTTON_COLOUR);
			
			this.setMargin(1,3);
			this.setFixed(true);
		}
		
		public void onButtonClick(ButtonClickEvent event)
		{
			this.parent.onButtonClick(event);
		}
	}

	public Notepad()
	{
		this.setLayout(ContainerType.VERTICAL);
		this.ongletsbox=new GenericContainer();
		this.ongletsbox.setMarginTop(10);
		this.ongletsbox.setHeight(20).setWidth(this.getWidth());
		this.ongletsbox.setFixed(true);
		this.addChild(ongletsbox);
		this.onglets=new HashMap<String, GenericButton>();
		
		this.pagebox=new GenericContainer();
		this.addChild(pagebox);
		this.pages= new HashMap<String, Widget>();
	}
	
	public Notepad addPage(String name, Widget content)
	{
		FonctionButton b=new FonctionButton(this, name);
		this.onglets.put(name, b);
		this.ongletsbox.addChild(b);
		this.pages.put(name, content);
		content.setVisible(false);
		this.pagebox.addChild(content);
		
		return this;
	}
	
	public Notepad setPage(String name)
	{
		if(this.onglets.containsKey(name)){
			for(Widget w:this.pages.values()){
				w.setVisible(false);
			}
			for(GenericButton b:this.onglets.values()){
				b.setColor(ExtendAdminGUI.COMMAND_BUTTON_COLOUR);
			}
			this.onglets.get(name).setColor(ExtendAdminGUI.DISABLED_BUTTON_COLOUR);
			this.pages.get(name).setVisible(true);
		}
		return this;
	}
	
	public Notepad removePage(String name)
	{
		if(this.onglets.containsKey(name)){
			this.ongletsbox.removeChild(this.onglets.get(name));
			this.onglets.remove(name);
			this.pagebox.removeChild(this.pages.get(name));
			this.pages.remove(name);
		}
		return this;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		setPage(event.getButton().getText());
	}
}
