package me.blaise.lib.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.zip.ZipEntry;

import me.blaise.lib.command.AbstractCommandHandler;
import me.blaise.lib.configuration.CustomConfigurationManager;
import net.milkbowl.vault.Vault;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
//import org.yaml.snakeyaml.Yaml;


public abstract class CustomPlugin extends JavaPlugin {
	private static CustomPlugin instance;
	private CustomConfigurationManager config;
	private Vault vault;
	
    /**
     * Construct objects. Actual loading occurs when the plugin is enabled, so
     * this merely instantiates the objects.
     */
    public CustomPlugin() {
        this.config = new CustomConfigurationManager(this);
        this.instance = this;
    }


	@Override
	public void onEnable() {
		instance = this;
		
		CustomPlugin.doLog("Enable started", Level.FINE);
		
		//Load the configuration from the config file
		config = new CustomConfigurationManager(this);
		
		//Handle dependencies
		loadDependencies();
		
		//Register event
        registerEvents();
		
		//Register commands
        registerCommands();
		
		//Load saved stuff
		load();
		
		printStatus(true);
	}
	
	private void loadDependencies() {
		//Loading Vault
    	Plugin x = this.getServer().getPluginManager().getPlugin("Vault");
        if(x != null & x instanceof Vault) {
            vault = (Vault) x;
            doLog(String.format(" Vault found and enable - Version %s", getDescription().getVersion()), Level.INFO);
        } else {
            doLog(String.format("Vault was _NOT_ found! Disabling plugin."), Level.WARNING);
            getPluginLoader().disablePlugin(this);
        }
	}


	protected abstract void registerCommands();
		//AbstractCommandHandler.registerHandler(new ExempleCommandHandler());


	protected abstract void registerEvents();
		/*pm.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Normal, instance);
		pm.registerEvent(Type.BLOCK_BREAK, blockListener, Priority.Normal, instance);
		pm.registerEvent(Type.BLOCK_PLACE, blockListener, Priority.Normal, instance);
		pm.registerEvent(Type.PLAYER_PICKUP_ITEM, playerListener, Priority.Normal, instance);
		pm.registerEvent(Type.CHUNK_LOAD, worldListener, Priority.Normal, instance);
		pm.registerEvent(Type.CHUNK_UNLOAD, worldListener, Priority.Normal, instance);
		*/


	@Override
	public void onDisable() {
		unload();
		try {
			//Save stuff
			save();
		} catch (Exception e) {
			doLog("Saving failed! Please report the bug including the log", Level.SEVERE);
			e.printStackTrace();
			doLog("End of log.", Level.SEVERE);
		}
		printStatus(false);
	}
	
	private void unload() {		
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		return AbstractCommandHandler.handleCommand(sender, command, commandLabel, args);
	}

	public static CustomPlugin getInstance() {
		return instance;
	}

	protected abstract void load();
		/*Yaml yaml = new Yaml();
		doLog("Loading started...", Level.FINE);
		try {
			ArrayList<Object> dump = (ArrayList<Object>) yaml.load(new FileReader(getSaveFile()));
			for(Object o:dump) {
				try {
					HashMap<String, Object> item = (HashMap<String, Object>)o;
					int x = (Integer)item.get("x");
					int y = (Integer)item.get("y");
					int z = (Integer)item.get("z");
					String worldName = (String)item.get("world");
					int environment = (Integer)item.get("environment");
					int material = (Integer)item.get("material");
					int data = (Integer)item.get("data");
					String scType = (String)item.get("type");
					
					ArrayList<String> owners = (ArrayList<String>) item.get("owners");
					
					
					World w = new WorldCreator(worldName).environment(Environment.getEnvironment(environment)).createWorld();
					if(w == null) {
						doLog("Couldn't find world "+worldName, Level.SEVERE);
						continue;
					}
					Block b = w.getBlockAt(x,y,z);
					if(b == null) {
						doLog("Couldn't find block", Level.SEVERE);
						continue;
					}
					ItemStack stack = new ItemStack(material, 1, (short) data);
					ShowcaseType type = ShowcaseType.getType(scType);
					if(type == null) {
						doLog("Couldn't find type: "+scType, Level.SEVERE);
						continue;
					}
					ShowcasePlayer player = ShowcasePlayer.getPlayer(owners.get(0));
					Showcase sc = type.createShowcase(b, stack, player, new String[0]);
					sc.setShowcaseType(type);
					for(String name:owners) {
						sc.addOwner(ShowcasePlayer.getPlayer(name));
					}
					try{
						HashMap<String, Object> extra = (HashMap<String, Object>) item.get("extra");
						if(extra != null) {
							sc.doLoad(extra);
						}
					} catch(Exception e) {
						doLog("Error on loading type "+scType, Level.SEVERE);
						e.printStackTrace();
						sc.remove();
						continue;
					}
					addShowcase(sc);
				} catch(Exception e) {
					doLog("Problem with loading: "+e.getMessage() + " One Showcase is lost!", Level.SEVERE);
				}
			}
		} catch (FileNotFoundException e) {
			doLog("Problem with loading: "+e.getMessage(), Level.SEVERE);
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			doLog("Invalid file.", Level.SEVERE);
			e.printStackTrace();
		}
		doLog("Loading done.", Level.FINER);*/

	protected abstract void save();/*
		Yaml yaml = new Yaml();
		ArrayList<Object> dump = new ArrayList<Object>();
		doLog("Saving...", Level.FINE);
		
		for(Showcase sc:showcases.values()) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("x", sc.getBlock().getX());
			item.put("y", sc.getBlock().getY());
			item.put("z", sc.getBlock().getZ());
			item.put("world", sc.getBlock().getWorld().getName());
			item.put("environment", sc.getBlock().getWorld().getEnvironment().getId());
			
			ArrayList<String> owners = new ArrayList<String>();
			for(ShowcasePlayer owner:sc.getOwners()) {
				owners.add(owner.getName());
			}
			item.put("owners", owners);
			
			item.put("material", sc.getType().getTypeId());
			item.put("data", sc.getType().getDurability());
			item.put("type", sc.getShowcaseType().getName());
			
			HashMap<String, Object> type = new HashMap<String, Object>();
			sc.doSave(type);
			item.put("extra", type);
			
			dump.add(item);
		}
		
		try {
			yaml.dump(dump, new FileWriter(getSaveFile()));
		} catch (IOException e) {
			doLog("Problem with saving: "+e.getMessage(), Level.SEVERE);
		}
		doLog("Saved.", Level.FINER);*/
	
	
	/**
	 * Prints on the console using ConsoleCommandSender (which can use ChatColor)
	 * The format will be [Plugin] msg
	 * @param msg
	 */
	public static void doLog(String msg, Level level) {
//		if(level.intValue() < getConfig().getMinimumLogLevel()){
//			return;
//		}
		String print = "[";
		ChatColor color = ChatColor.BLACK;
		if(level.equals(Level.SEVERE)) {
			color = ChatColor.RED;
		}
		if(level.equals(Level.WARNING)) {
			color = ChatColor.GOLD;
		}
		if(level.equals(Level.INFO)) {
			color = ChatColor.BLACK;
		}
		if(level.equals(Level.FINE)) {
			color = ChatColor.DARK_GREEN;
		}
		if(level.equals(Level.FINER)){
			color = ChatColor.GREEN;
		}
		if(level.equals(Level.FINEST)){
			color = ChatColor.GREEN;
		}
		print += color.toString();
		print += instance.getDescription().getName()+ChatColor.BLACK+"] ";
		if(level.equals(Level.INFO)) {
			print+="[Info] ";
		}
		print += msg;
		instance.getServer().getConsoleSender().sendMessage(print);
	}

	protected void printStatus(boolean enable) {
		String print = "";
		PluginDescriptionFile pdf = getDescription();
		print+= "["+ChatColor.GREEN+pdf.getName()+ChatColor.BLACK+"] by [";
		int i = 0;
		for(String author:pdf.getAuthors()) {
			print+=ChatColor.GREEN+author+ChatColor.BLACK;
			if(i+1<pdf.getAuthors().size()) {
				print +=", ";
			} else {
				print +="] ";
			}
			i++;
		}
		print+="version ["+ChatColor.GREEN+pdf.getVersion()+ChatColor.BLACK+"] ";
		if(enable) {
			print+=ChatColor.GREEN+"enabled";
		} else {
			print+=ChatColor.RED+"disabled";
		}
		print+=ChatColor.BLACK+".";
		
		getServer().getConsoleSender().sendMessage(print);
	}
	
	public static CustomConfigurationManager getCustomConfig(){
		return instance.config;
	}

    
}
