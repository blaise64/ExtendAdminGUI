package me.blaise.lib.configuration;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;
import org.bukkit.configuration.file.FileConfiguration;


public class CustomConfigurationManager {
	private Plugin plugin;
	private FileConfiguration config;
	
	public CustomConfigurationManager(Plugin plugin) {
		this.plugin = plugin;
		config = this.plugin.getConfig();
	}

    public int getMinimumLogLevel(){
    	return this.config.getInt("min-log-level", Level.WARNING.intValue());
    }

}
