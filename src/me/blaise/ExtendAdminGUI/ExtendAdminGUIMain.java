package me.blaise.ExtendAdminGUI;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.*;
import org.w3c.dom.*;

import me.blaise.ExtendAdminGUI.commands.*;
import me.blaise.lib.command.AbstractCommandHandler;
import me.blaise.lib.main.CustomPlugin;

//TODO: verifier les permissions

//TODO: GŽrer les clear
public class ExtendAdminGUIMain extends CustomPlugin
{
	public ExtendAdminGUIMain()
    {
        screens=new HashMap<String, Document>();
    }
	
	protected void registerCommands() {
		AbstractCommandHandler.registerHandler(new LoadScreenCommandHandler());
		AbstractCommandHandler.registerHandler(new RegionScreenCommandHandler());
		AbstractCommandHandler.registerHandler(new TestScreenCommandHandler());
		AbstractCommandHandler.registerHandler(new ReloadScreenCommandHandler());
	}

    public void load()
    {
//    	setupConfig();

        //loading of the screen
        Boolean succes = this.loadScreens();
        
        if(succes == false)
        {
        	PluginManager pm = getServer().getPluginManager();
           pm.disablePlugin(this);
            return;
        }
    }

//    private void checkDefaults()
//    {
//        //autoselect user
//    	if(config.getBoolean("select_user"))
//            logger.info("[ExtendAdminGUI - info] select_user changed - from false to true");
//    	//debug_mode
//        if(config.getBoolean("debug_mode"))
//            logger.info("[ExtendAdminGUI - info] debug_mode changed - from false to true");
//        //coloes
//        if(!config.getString("selected_button_color").replaceAll(" ", "").equalsIgnoreCase("120,50,120"))
//            logger.info((new StringBuilder("[ExtendAdminGUI - info] selected_button_color changed - from 120,50,120 to ")).append(config.getString("selected_button_color")).toString());
//        if(!config.getString("screen_button_color").replaceAll(" ", "").equalsIgnoreCase("150,150,150"))
//            logger.info((new StringBuilder("[ExtendAdminGUI - info] screen_button_color changed - from 150,150,150 to ")).append(config.getString("screen_button_color")).toString());
//        if(!config.getString("command_button_color").replaceAll(" ", "").equalsIgnoreCase("50,120,120"))
//            logger.info((new StringBuilder("[ExtendAdminGUI - info] command_button_color changed - from 50,120,120 to ")).append(config.getString("command_button_color")).toString());
//        if(!config.getString("link_button_color").replaceAll(" ", "").equalsIgnoreCase("150,150,150"))
//            logger.info((new StringBuilder("[ExtendAdminGUI - info] link_button_color changed - from 150,150,150 to ")).append(config.getString("link_button_color")).toString());
//        if(!config.getString("player_button_color").replaceAll(" ", "").equalsIgnoreCase("150,150,150"))
//            logger.info((new StringBuilder("[ExtendAdminGUI - info] player_button_color changed - from 150,150,150 to ")).append(config.getString("player_button_color")).toString());
//        if(!config.getString("navigation_button_color").replaceAll(" ", "").equalsIgnoreCase("50,150,150"))
//            logger.info((new StringBuilder("[ExtendAdminGUI - info] navigation_button_color changed - from 50,150,150 to ")).append(config.getString("navigation_button_color")).toString());
//        if(!config.getString("button_hover_color").replaceAll(" ", "").equalsIgnoreCase("150,120,50"))
//            logger.info((new StringBuilder("[ExtendAdminGUI - info] button_hover_color changed - from 150,120,50 to ")).append(config.getString("button_hover_color")).toString());
//        if(!config.getString("disabled_button_color").replaceAll(" ", "").equalsIgnoreCase("2,0,0"))
//            logger.info((new StringBuilder("[ExtendAdminGUI - info] disabled_button_color changed - from 2,0,0 to ")).append(config.getString("disabled_button_color")).toString());
//    }

//    private void setupConfig()
//    {
//        config = getConfig();
//        config.addDefault("select_user", Boolean.valueOf(false));
//        config.addDefault("selected_button_color", "120,50,120");
//        config.addDefault("screen_button_color", "150,150,150");
//        config.addDefault("command_button_color", "50,120,120");
//        config.addDefault("link_button_color", "150,150,150");
//        config.addDefault("player_button_color", "150,150,150");
//        config.addDefault("navigation_button_color", "50,150,150");
//        config.addDefault("button_hover_color", "150,120,50");
//        config.addDefault("disabled_button_color", "2,0,0");
//        config.addDefault("debug_mode", Boolean.valueOf(false));
//        config.addDefault("show_info", Boolean.valueOf(true));
//        saveConfig();
//    }
    
    //
    public Boolean loadScreens()
    {
    	if(this.screens.size()!=0)
    		this.screens.clear();
    	try
        {
	        String loc;
	        String children[];
	        loc = (new StringBuilder(String.valueOf(getDataFolder().getPath()))).append(File.separator).append("Screens").append(File.separator).toString();
	        File dir = new File(loc);
	        
	        //if(config.getBoolean("show_info"))
	            doLog("Screens folder is "+dir.getAbsolutePath(),Level.FINE);
	        
	        children = dir.list();
	        if(children.length == 0)
	        {
	        	doLog("No screen files found!  Check the BukkitDev page for help.",Level.WARNING);
	        	return false;
	        }
	        //if(config.getBoolean("show_info"))
	            doLog("Found "+Integer.toString(children.length)+" screen files.", Level.FINE);
	        for(int i = 0; i < children.length; i++)
        	{
	        	Document doc=this.loadScreen(loc, children[i]);
	        	if(doc != null)
	            {
					String s = children[i];
					int pos = s.lastIndexOf(".");
					if(pos != -1)
					    s = s.substring(0, pos);
					s=s.toLowerCase();
					this.screens.put(s,doc);
					//if(config.getBoolean("show_info"))
						doLog("Screen "+children[i]+" loaded as "+s, Level.FINE);
	             }
	        	 else
	        	 {
	        		 doLog("Error loading screen "+children[i], Level.SEVERE);
	        		 return false;
	        	 }
        	}
	        return true;
        }
        catch(Exception ne)
        {
            doLog("Error Loading a screen file!", Level.SEVERE);
            ne.printStackTrace();
            return false;
        }
    }
    
    //loading a file with his full name and directory name
    private Document loadScreen(String location, String screen_name)
    {
    	try
    	{
        	//if(config.getBoolean("show_info"))
	            doLog("Screen "+screen_name+" found. trying to load it.",Level.FINE);
        	
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 	        Document screen = factory.newDocumentBuilder().parse(new File((new StringBuilder(String.valueOf(location))).append(screen_name).toString()));
 	        screen.getDocumentElement().normalize();
 	       // if(config.getBoolean("show_info"))
 	        	doLog("Screen "+screen_name+" parsed", Level.FINE);
 	        return screen;
        }
        catch(Exception ne)
        {
            doLog("Bad XML formatting somewhere in a screen file!",Level.SEVERE);
            ne.printStackTrace();
            return null;
        }
    }

    public void unload()
    {
        config = null;
        screens = null;
    }

    public FileConfiguration config;
    public int permissionsPlugin;
    public HashMap<String, Document> screens;
	public HashMap<String, Document> getScreens() {
		return this.screens;
	}

	@Override
	protected void registerEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub
		
	}
}
