package me.blaise.ExtendAdminGUI;

import java.util.HashMap;
import java.util.LinkedList;

import org.getspout.spoutapi.gui.*;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.w3c.dom.*;

//TODO: actualize the widgets with the parameters known
//TODO: make constants for the tags names

public class ExtendAdminGUI
{
    static Color BUTTON_HOVER_COLOUR;
    static Color COMMAND_BUTTON_COLOUR;
    static Color DISABLED_BUTTON_COLOUR;
    static Color LINK_BUTTON_COLOUR;
    static Color NAVIGATION_BUTTON_COLOUR;
    static Color PLAYER_BUTTON_COLOUR;
    static Color SCREEN_BUTTON_COLOUR;
    static Color SELECTED_BUTTON_COLOUR;
    public Document doc;
    
    // Container parameter_panel;
    public GenericPopup GUI;
    public Main plugin;
    public SpoutPlayer user;
	private HashMap<String, GenericTextField> parameters;
	
	// navigation
	private LinkedList<Element> listNode;
	private int navIndex;
	private ScreenGUI current;
	private HashMap<String, Element> screens;
	private boolean test=false;
	
    
    public ExtendAdminGUI(Document doc, SpoutPlayer user, Main instance)
    {
        this.user = user;
        this.plugin = instance;
        this.doc=doc;
        this.GUI = new GenericPopup();
        //test
        if(this.test){
        	this.GUI.attachWidget(this.plugin, (new TestScreenGui(this)).getMainContainer());
        	this.user.getMainScreen().attachPopupScreen(GUI);
        	return;
        }
        
        //normal
        
        this.screens= new HashMap<String, Element>();
		this.parameters= new HashMap<String, GenericTextField>();
//        SELECTED_BUTTON_COLOUR = get_colour("selected_button_color");
//        SCREEN_BUTTON_COLOUR = get_colour("screen_button_color");
//        COMMAND_BUTTON_COLOUR = get_colour("command_button_color");
//        LINK_BUTTON_COLOUR = get_colour("link_button_color");
//        PLAYER_BUTTON_COLOUR = get_colour("player_button_color");
//        NAVIGATION_BUTTON_COLOUR = get_colour("navigation_button_color");
//        BUTTON_HOVER_COLOUR = get_colour("button_hover_color");
//        DISABLED_BUTTON_COLOUR = get_colour("disabled_button_color");

		listNode = new LinkedList<Element>(); 
		navIndex = -1;

		NodeList list = this.doc.getElementsByTagName("screen");
		for(int i=0; i<list.getLength();i++)
		{
			this.screens.put(((Element)list.item(i)).getAttribute("name"),((Element)list.item(i)));
		}
        
        if(this.plugin.config.getBoolean("debug_mode"))
        	log((new StringBuilder("[ExtendAdminGUI - debug] Opened a GUI for ")).append(user.getName()).toString());
        
		//TODO: a method getMainScreen
		NodeList listmain = this.doc.getElementsByTagName("main_screen");
		if(listmain.getLength()!=0)
		{
			this.screens.put(((Element)listmain.item(0)).getAttribute("name"),((Element)listmain.item(0)));
			this.addPanel((Element)(this.doc.getElementsByTagName("main_screen").item(0)));
			//++navIndex;
		}
		else
		{
			 if(this.plugin.config.getBoolean("debug_mode"))
		        	log((new StringBuilder("[ExtendAdminGUI - debug] No main screen ")).toString());
			 return;
		}
		this.user.getMainScreen().attachPopupScreen(GUI);
    }
    
    void log(Object message)
    {
        String s;
        if(message instanceof Integer)
            s = Integer.toString(((Integer)message).intValue());
        else
            s = (String)message;
        plugin.getServer().getLogger().info(s);
    }
    
    public void addPanel(Element xml)
    {
    	if(this.navIndex<(this.listNode.size()-1))
    	{
    		this.listNode.add(++navIndex, xml);
    	}
    	else
    	{
        	listNode.add(xml);
        	++navIndex;//goto next element just happened
    	}
    	if(this.current!=null)
    		this.current.clear();
    	this.current=new ScreenGUI(this, xml);
    	setPanel(current.getMainContainer());
    }
    
    private void setPanel(GenericContainer panel)
    {
    	if(this.plugin.config.getBoolean("debug_mode"))
               log((new StringBuilder("[ExtendAdminGUI - debug] Changing Screen")).toString());
    	//this.user.getMainScreen().closePopup();
        this.GUI.attachWidget(this.plugin, panel);
    }
    
    //TODO: back in multiple level : Is this working ?
    public void goBack()
    {
    	if(navIndex>0)
    	{
    		Element xml=this.listNode.get(--navIndex);
    		this.current.clear();
        	this.current=new ScreenGUI(this, xml);
        	setPanel(current.getMainContainer());
    	}
    }
    
    public void goNext()
    {
    	if(this.navIndex<(this.listNode.size()-1))
    	{
    		Element xml=this.listNode.get(++navIndex);
    		this.current.clear();
        	this.current=new ScreenGUI(this, xml);
        	setPanel(current.getMainContainer());
    	}
    }
    
    public void goTo(String name)
    {
    	if(this.screens.containsKey(name))
    	{
    		addPanel(this.screens.get(name));
    	}
    }
    
	public String getParamValue(String param) {
		if(this.plugin.config.getBoolean("debug_mode"))
    		this.log((new StringBuilder("[ExtendAdminGUI - debug] request for parameter")).append(param).toString());
		if(this.parameters.containsKey(param))
			return this.parameters.get(param).getText();
		else 
			return "%no_param%";
	}
	
	public void registerParameter(String param, GenericTextField e)
	{
		this.parameters.put(param, e);
	}

	public void registerLastEntry(PlayerChooser e)
	{
		this.current.registerLastEntry(e);
	}
	
	public void fillLastEntry(String name)
	{
		this.current.fillLastEntry(name);
	}
}
