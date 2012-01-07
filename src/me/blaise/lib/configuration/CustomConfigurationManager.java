package me.blaise.lib.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.zip.ZipEntry;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class CustomConfigurationManager {
	@SuppressWarnings("unused")
	private File file;
	private Plugin plugin;
	private int minimumLevel = Level.WARNING.intValue();
	private boolean basicTakesItem = false;
	
	public CustomConfigurationManager(Plugin plugin) {
		this.plugin = plugin;
	}
	
    public void load() {

        //file= new File(plugin.getDataFolder(), "config.yml");
    }

    private void createDefaultConfiguration() {
	}

	/**
     * Unload the configuration.
     */
    public void unload() {
    }
	

    /**
     * Create a default configuration file from the .jar.
     * 
     * @param actual 
     * @param defaultName 
     */
    public void createDefaultConfiguration(File actual, String defaultName) {
        
//        // Make parent directories
//        File parent = actual.getParentFile();
//        if (!parent.exists()) {
//            parent.mkdirs();
//        }
//
//        if (actual.exists()) {
//            return;
//        }
//
//        InputStream input =
//                    null;
//            try {
//                JarFile file = new JarFile(getFile());
//                ZipEntry copy = file.getEntry("defaults/" + defaultName);
//                if (copy == null) throw new FileNotFoundException();
//                input = file.getInputStream(copy);
//            } catch (IOException e) {
//                logger.severe(getDescription().getName() + ": Unable to read default configuration: " + defaultName);
//            }
//        
//        if (input != null) {
//            FileOutputStream output = null;
//
//            try {
//                output = new FileOutputStream(actual);
//                byte[] buf = new byte[8192];
//                int length = 0;
//                while ((length = input.read(buf)) > 0) {
//                    output.write(buf, 0, length);
//                }
//
//                logger.info("WorldGuard: Default configuration file written: "
//                        + actual.getAbsolutePath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (input != null) {
//                        input.close();
//                    }
//                } catch (IOException e) {
//                }
//
//                try {
//                    if (output != null) {
//                        output.close();
//                    }
//                } catch (IOException e) {
//                }
//            }
//        }
    }
}
