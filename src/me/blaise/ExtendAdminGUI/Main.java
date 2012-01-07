// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Main.java

package me.blaise.ExtendAdminGUI;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.w3c.dom.*;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.EconomyResponse;

//TODO: verifier les eprmissions

//TODO: GŽrer les clear
public class Main extends JavaPlugin
{

    
	private Vault vault=null;
	public Main()
    {
        config = null;
        //opened_gui = new HashMap();
        permissionsPlugin = -1;
        screens=new HashMap<String, Document>();
    }

    void log(Object message)
    {
        String s;
        if(message instanceof Integer)
            s = Integer.toString(((Integer)message).intValue());
        else
            s = (String)message;
        getServer().getLogger().info(s);
    }

    public void onEnable()
    {
        
    	setupConfig();
        
    	
        
        PluginManager pm = getServer().getPluginManager();
        ArrayList<String> plugins = new ArrayList<String>();
        Plugin aplugin[];
        
        int j = (aplugin = pm.getPlugins()).length;
        for(int i = 0; i < j; i++)
        {
            Plugin p = aplugin[i];
            plugins.add(p.getDescription().getName());
        }
        logger = getServer().getLogger();
        
      //Loading Vault
    	Plugin x = this.getServer().getPluginManager().getPlugin("Vault");
        if(x != null & x instanceof Vault) {
            vault = (Vault) x;
            logger.info(String.format("[%s] Enabled Version %s", getDescription().getName(), getDescription().getVersion()));
        } else {
            /**
             * Throw error & disable because we have Vault set as a dependency, you could give a download link
             * or even download it for the user.  This is all up to you as a developer to decide the best option
             * for your users!  For our example, we assume that our audience (developers) can find the Vault
             * plugin and properly install it.  It's usually a bad idea however.
             */
            logger.warning(String.format("[%s] Vault was _NOT_ found! Disabling plugin.", getDescription().getName()));
            getPluginLoader().disablePlugin(this);
        }
        
        //loading of the screen
        Boolean succes = this.loadScreens();
        
        if(succes == false)
        {
           pm.disablePlugin(this);
            return;
        }
        
        //Checking Version
        PluginDescriptionFile pdf = getDescription();
        String version = pdf.getVersion();
        logger.info((new StringBuilder("[ExtendAdminGUI] Version ")).append(version).append(" Running!").toString());
        
        //DebugMode
        if(config.getBoolean("debug_mode"))
           logger.info("[ExtendAdminGUI] Debug mode enabled!");
    }

    private void checkDefaults()
    {
        //autoselect user
    	if(config.getBoolean("select_user"))
            logger.info("[ExtendAdminGUI - info] select_user changed - from false to true");
    	//debug_mode
        if(config.getBoolean("debug_mode"))
            logger.info("[ExtendAdminGUI - info] debug_mode changed - from false to true");
        //coloes
        if(!config.getString("selected_button_color").replaceAll(" ", "").equalsIgnoreCase("120,50,120"))
            logger.info((new StringBuilder("[ExtendAdminGUI - info] selected_button_color changed - from 120,50,120 to ")).append(config.getString("selected_button_color")).toString());
        if(!config.getString("screen_button_color").replaceAll(" ", "").equalsIgnoreCase("150,150,150"))
            logger.info((new StringBuilder("[ExtendAdminGUI - info] screen_button_color changed - from 150,150,150 to ")).append(config.getString("screen_button_color")).toString());
        if(!config.getString("command_button_color").replaceAll(" ", "").equalsIgnoreCase("50,120,120"))
            logger.info((new StringBuilder("[ExtendAdminGUI - info] command_button_color changed - from 50,120,120 to ")).append(config.getString("command_button_color")).toString());
        if(!config.getString("link_button_color").replaceAll(" ", "").equalsIgnoreCase("150,150,150"))
            logger.info((new StringBuilder("[ExtendAdminGUI - info] link_button_color changed - from 150,150,150 to ")).append(config.getString("link_button_color")).toString());
        if(!config.getString("player_button_color").replaceAll(" ", "").equalsIgnoreCase("150,150,150"))
            logger.info((new StringBuilder("[ExtendAdminGUI - info] player_button_color changed - from 150,150,150 to ")).append(config.getString("player_button_color")).toString());
        if(!config.getString("navigation_button_color").replaceAll(" ", "").equalsIgnoreCase("50,150,150"))
            logger.info((new StringBuilder("[ExtendAdminGUI - info] navigation_button_color changed - from 50,150,150 to ")).append(config.getString("navigation_button_color")).toString());
        if(!config.getString("button_hover_color").replaceAll(" ", "").equalsIgnoreCase("150,120,50"))
            logger.info((new StringBuilder("[ExtendAdminGUI - info] button_hover_color changed - from 150,120,50 to ")).append(config.getString("button_hover_color")).toString());
        if(!config.getString("disabled_button_color").replaceAll(" ", "").equalsIgnoreCase("2,0,0"))
            logger.info((new StringBuilder("[ExtendAdminGUI - info] disabled_button_color changed - from 2,0,0 to ")).append(config.getString("disabled_button_color")).toString());
    }

    private void setupConfig()
    {
        config = getConfig();
        config.addDefault("select_user", Boolean.valueOf(false));
        config.addDefault("selected_button_color", "120,50,120");
        config.addDefault("screen_button_color", "150,150,150");
        config.addDefault("command_button_color", "50,120,120");
        config.addDefault("link_button_color", "150,150,150");
        config.addDefault("player_button_color", "150,150,150");
        config.addDefault("navigation_button_color", "50,150,150");
        config.addDefault("button_hover_color", "150,120,50");
        config.addDefault("disabled_button_color", "2,0,0");
        config.addDefault("debug_mode", Boolean.valueOf(false));
        config.addDefault("show_info", Boolean.valueOf(true));
        saveConfig();
    }
    
    //
    private Boolean loadScreens()
    {
    	try
        {
	        String loc;
	        String children[];
	        loc = (new StringBuilder(String.valueOf(getDataFolder().getPath()))).append(File.separator).append("Screens").append(File.separator).toString();
	        File dir = new File(loc);
	        
	        if(config.getBoolean("show_info"))
	            logger.info((new StringBuilder("[ExtendAdminGUI - info] Screens folder is ")).append(dir.getAbsolutePath()).toString());
	        
	        children = dir.list();
	        if(children.length == 0)
	        {
	        	logger.warning("[ExtendAdminGUI] No screen files found!  Check the BukkitDev page for help.");
	        	return false;
	        }
	        if(config.getBoolean("show_info"))
	            logger.info((new StringBuilder("[ExtendAdminGUI - info] Found ")).append(Integer.toString(children.length)).append(" screen files.").toString());
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
					if(config.getBoolean("show_info"))
						logger.info((new StringBuilder("[ExtendAdminGUI - info] Screen ")).append(children[i]).append(" loaded as ").append(s).toString());

	             }
	        	 else
	        	 {
	        		 logger.severe((new StringBuilder("[ExtendAdminGUI] Error loading screen ")).append(children[i]).toString());
	        		 return false;
	        	 }
        	}
	        return true;
        }
        catch(Exception ne)
        {
            logger.severe("[ExtendAdminGUI] Error Loading a screen file!");
            ne.printStackTrace();
            return false;
        }
    }
    
    //loading a file with his full name and directory name
    private Document loadScreen(String location, String screen_name)
    {
    	try
    	{
        	if(config.getBoolean("show_info"))
	            logger.info((new StringBuilder("[ExtendAdminGUI - info] Screen ")).append(screen_name).append(" found. trying to load it.").toString());
        	
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 	        Document screen = factory.newDocumentBuilder().parse(new File((new StringBuilder(String.valueOf(location))).append(screen_name).toString()));
 	        screen.getDocumentElement().normalize();
 	        if(config.getBoolean("show_info"))
 	        	logger.info((new StringBuilder("[ExtendAdminGUI - info] Screen ")).append(screen_name).append(" parsed").toString());
 	        return screen;
        }
        catch(Exception ne)
        {
            logger.severe("[ExtendAdminGUI] Bad XML formatting somewhere in a screen file!");
            ne.printStackTrace();
            return null;
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
    	//if(vault.perms.hasPermission(sender, "example.plugin.awesome", false))
        if(cmd.getName().equalsIgnoreCase("loadscreen"))
        {
        	if(args.length!=1)
        	{
        		sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Bad Use : /loadscreen screen_name_without_extension").toString());
        		return false;
        	}
        	else
        	{
        		if(config.getBoolean("show_info"))
     	        	logger.info((new StringBuilder("[ExtendAdminGUI - info] Searching screen ")).append(args[0].toLowerCase()).toString());
	            if(this.screens.containsKey(args[0].toLowerCase()))
	            {
	            	if(sender instanceof SpoutPlayer)
	            	{
	            		SpoutPlayer user = (SpoutPlayer)sender;
	            		//TODO: deal with permissions
	            		if(user.getMainScreen().getActivePopup() != null)
	            		{
	            			user.getMainScreen().closePopup();
	            		}
	                    else
	                    {
	                    	if(user.getActiveScreen() == ScreenType.GAME_SCREEN || user.getActiveScreen() == ScreenType.CUSTOM_SCREEN || user.getActiveScreen() == ScreenType.CHAT_SCREEN)
	                    		new ExtendAdminGUI(this.screens.get(args[0].toLowerCase()), user, this);
	                    }
	            	}
        	        logger.info((new StringBuilder("[ExtendAdminGUI] Screen ")).append(args[0]).append(" loaded by ").append(sender.getName()).toString());
                	return true;
	            }
	            if(sender instanceof Player)
	                sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Load Failed - Check your layout files!").toString());
	            return false;
        	}
        }
        else if(cmd.getName().equalsIgnoreCase("reloadscreens"))
        {
        	if(args.length!=0)
        	{
        		sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Bad Use : /reloadscreens").toString());
        		return false;
        	}
        	else
        	{
        		if(config.getBoolean("show_info"))
        		{
     	        	logger.info((new StringBuilder("[ExtendAdminGUI - info] Reloading screens")).toString());
        		this.loadScreens();
	            return true;
	            }
	            if(sender instanceof Player)
	                sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Load Failed - Check your layout files!").toString());
	            return false;
        	}
        }
        else if(cmd.getName().equalsIgnoreCase("regionscreen"))
        {
        	if(args.length!=0)
        	{
        		sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Bad Use : /regionscreen").toString());
        		return false;
        	}
        	else
        	{
        		if(config.getBoolean("show_info"))
        		{
     	        	logger.info((new StringBuilder("[ExtendAdminGUI - info] Loading region Screen")).toString());
     	        	if(sender instanceof SpoutPlayer)
	            	{
	            		SpoutPlayer user = (SpoutPlayer)sender;
	            		//TODO: deal with permissions
	            		if(user.getMainScreen().getActivePopup() != null)
	            		{
	            			user.getMainScreen().closePopup();
	            		}
	                    else
	                    {
	                    	if(user.getActiveScreen() == ScreenType.GAME_SCREEN || user.getActiveScreen() == ScreenType.CUSTOM_SCREEN || user.getActiveScreen() == ScreenType.CHAT_SCREEN)
	                    		new RegionGUI(user, this);
	                    }
	            	}
	            return true;
	            }
	            if(sender instanceof Player)
	                sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Load Failed - Check your layout files!").toString());
	            return false;
        	}
        }
		else if(cmd.getName().equalsIgnoreCase("testscreen"))
                {
                	if(args.length!=0)
                	{
                		sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Bad Use : /tetscreen").toString());
                		return false;
                	}
                	else
                	{
                		if(config.getBoolean("show_info"))
                		{
             	        	logger.info((new StringBuilder("[ExtendAdminGUI - info] Loading test Screen")).toString());
             	        	if(sender instanceof SpoutPlayer)
        	            	{
        	            		SpoutPlayer user = (SpoutPlayer)sender;
        	            		if(user.getMainScreen().getActivePopup() != null)
        	            		{
        	            			user.getMainScreen().closePopup();
        	            		}
        	                    else
        	                    {
        	                    	if(user.getActiveScreen() == ScreenType.GAME_SCREEN || user.getActiveScreen() == ScreenType.CUSTOM_SCREEN || user.getActiveScreen() == ScreenType.CHAT_SCREEN)
        	                    		new TestGUI(user, this);
        	                    }
        	            	}
        	       return true;
        	    }
	            if(sender instanceof Player)
	                sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Load Failed - Check your layout files!").toString());
	            return false;
        	}
        }
        else
        {
            return false;
        }
    }

    public void onDisable()
    {
        config = null;
        //opened_gui = null;
        logger = null;
        screens = null;
        getServer().getLogger().info("[ExtendAdminGUI] Stopped!");
    }

    public FileConfiguration config;
    //public HashMap<String, ExtendAdminGUI> opened_gui;
    public int permissionsPlugin;
    private Logger logger;
    public HashMap<String, Document> screens;
}
