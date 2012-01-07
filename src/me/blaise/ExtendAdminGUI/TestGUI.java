package me.blaise.ExtendAdminGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.*;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;





public class TestGUI implements Chooser
{
    public GenericPopup GUI;
    public Main plugin;
    public SpoutPlayer user;
	private WorldGuardPlugin worldGuard;
	private ArrayList<String> worldsNames;
	private ArrayList<String> regionsNames;
	private GenericContainer container;
	private Finder worldchooser;
	private Finder regionchooser;
	private GenericContainer container1;
	private GenericContainer container2;
	private GenericContainer container3;
	private GenericContainer container5;
	private GenericContainer container4;
	private GridContainer container6;
	private GenericContainer container7;
	private GenericTextField regionbox;
	private GenericTextField parentregionbox;
	private GenericTextField playerbox;
	private GenericTextField prioritybox;
	private HashMap<String, FlagChooser> flags;
    
    public TestGUI(SpoutPlayer user, Main instance)
    {
        this.user = user;
        this.plugin = instance;       
        
        this.worldsNames = new ArrayList<String>();
        this.regionsNames = new ArrayList<String>();
        
        //get the list of the world in the server
        List<World> worlds=Bukkit.getWorlds();
        //extract the list of the world's names
        for(World w:worlds){
        	this.worldsNames.add(w.getName());
        }
        //get the worldguard plugin
        this.worldGuard = (WorldGuardPlugin) this.plugin.getServer().getPluginManager().getPlugin("WorldGuard");        
        this.GUI = new GenericPopup();
        //selectWorld();
        
        this.container = new GenericContainer();
        this.container.setLayout(ContainerType.HORIZONTAL);
        this.container.setMarginTop(10);
        this.container.setX(0).setY(10);
        this.container.setHeight(60).setWidth(120).setFixed(true);
        
        //this.worldchooser = new Finder(this, "world", this.worldsNames);
        //this.container.addChildren(this.worldchooser);
        CorrectedTextField test=new CorrectedTextField();
        test.setHeight(15).setWidth((222*this.user.getMainScreen().getWidth())/1280).setFixed(true);
        this.container.addChildren(test);
        
        GenericButton b=new GenericButton("Reload Menu2");
        b.setHeight(15).setWidth(100).setFixed(true);
        this.container.addChildren(b);

        this.GUI.attachWidget(instance, this.container);
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


	@Override
	public void choose(String var, String value) {
		System.out.println(var+" value is "+value);
		
	}


}