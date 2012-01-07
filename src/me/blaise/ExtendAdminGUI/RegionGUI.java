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





public class RegionGUI implements Chooser, CanExecuteFonction
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
    
    public RegionGUI(SpoutPlayer user, Main instance)
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
        
        this.container1 = new GenericContainer();
        this.container1.setLayout(ContainerType.VERTICAL);
        
        this.container2 = new GenericContainer();
        this.container2.setLayout(ContainerType.VERTICAL);
        
        //adding the stuff for configurate the region
        this.container6 = new GridContainer(5,3,90,17);
        
        this.container3 = new GenericContainer();
        this.container3.setLayout(ContainerType.HORIZONTAL);
        
        this.container4 = new GenericContainer();
        this.container4.setLayout(ContainerType.VERTICAL);
        
        this.container5 = new GenericContainer();
        this.container5.setLayout(ContainerType.VERTICAL);
        
        this.container7 = new GenericContainer();
        this.container7.setLayout(ContainerType.VERTICAL);


        //adding all the flags
        this.flags=new HashMap<String, FlagChooser>();
        this.flags.put("passthrough", new FlagChooser("passthrough", false, "Allow", "Deny"));
        this.container4.addChild(this.flags.get("passthrough"));
        this.flags.put("build", new FlagChooser("build", true, "Allow", "Deny"));
        this.container4.addChild(this.flags.get("build"));
        this.flags.put("pvp", new FlagChooser("pvp", true, "Allow", "Deny"));
        this.container4.addChild(this.flags.get("pvp"));
        this.flags.put("mob-damage", new FlagChooser("mob-damage", true, "Allow", "Deny"));
        this.container4.addChild(this.flags.get("mob-damage"));
        this.flags.put("mob-spawning",new FlagChooser("mob-spawning", true, "Allow", "Deny"));
        this.container4.addChild(this.flags.get("mob-spawning"));
        this.flags.put("creeper-explosion",new FlagChooser("creeper-explosion", true, "Allow", "Deny"));
        this.container4.addChild(this.flags.get("creeper-explosion"));
        this.flags.put("ghast-fireball", new FlagChooser("ghast-fireball", true, "Allow", "Deny"));
        this.container4.addChild(this.flags.get("ghast-fireball"));
        this.flags.put("sleep",new FlagChooser("sleep", true, "Allow", "Deny"));
        this.container4.addChild(this.flags.get("sleep"));
		
		this.flags.put("tnt", new FlagChooser("tnt", true, "Allow", "Deny"));
        this.container5.addChild(this.flags.get("tnt"));
		this.flags.put("lighter", new FlagChooser("lighter", true, "Allow", "Deny"));
        this.container5.addChild(this.flags.get("lighter"));
		this.flags.put("fire-spread", new FlagChooser("fire-spread", true, "Allow", "Deny"));
        this.container5.addChild(this.flags.get("fire-spread"));
		this.flags.put("lava-fire", new FlagChooser("lava-fire", true, "Allow", "Deny"));
        this.container5.addChild(this.flags.get("lava-fire"));
		this.flags.put("lightning", new FlagChooser("lightning", true, "Allow", "Deny"));
        this.container5.addChild(this.flags.get("lightning"));
		this.flags.put("chest-access", new FlagChooser("chest-access", false, "Allow", "Deny"));
        this.container5.addChild(this.flags.get("chest-access"));
		this.flags.put("water-flow", new FlagChooser("water-flow", true, "Allow", "Deny"));
        this.container5.addChild(this.flags.get("water-flow"));
		this.flags.put("lava-flow", new FlagChooser("lava-flow", true, "Allow", "Deny"));
        this.container5.addChild(this.flags.get("lava-flow"));
		
		this.flags.put("use", new FlagChooser("use", true, "Allow", "Deny"));
        this.container7.addChild(this.flags.get("use"));
		this.flags.put("vehicle-place", new FlagChooser("vehicle-place", false, "Allow", "Deny"));
        this.container7.addChild(this.flags.get("vehicle-place"));
		this.flags.put("snow-fall", new FlagChooser("snow-fall", true, "Allow", "Deny"));
        this.container7.addChild(this.flags.get("snow-fall"));
		this.flags.put("leaf-decay", new FlagChooser("leaf-decay", true, "Allow", "Deny"));
        this.container7.addChild(this.flags.get("leaf-decay"));
		this.flags.put("notify-enter", new FlagChooser("notify-enter", false, "true", "false"));
        this.container7.addChild(this.flags.get("notify-enter"));
		this.flags.put("notify-leave", new FlagChooser("notify-leave", false, "true", "false"));
        this.container7.addChild(this.flags.get("notify-leave"));
		this.flags.put("entry", new FlagChooser("entry", true, "Allow", "Deny"));
        this.container7.addChild(this.flags.get("entry"));
		this.flags.put("exit", new FlagChooser("exit", true, "Allow", "Deny"));
        this.container7.addChild(this.flags.get("exit"));
		
        this.container6.addElement(new FonctionButton(this, "Set Flags"), 5, 2);
        
		this.container3.addChildren(this.container4, this.container5, this.container7);

        this.regionbox=new CorrectedTextField();
        this.regionbox.setHeight(15);
        this.regionbox.setMargin(1, 5);
        this.container6.addElement(regionbox, 1, 1);
        this.container6.addElement(new FonctionButton(this, "(Re)Define"), 2, 1);
        
        this.parentregionbox = new CorrectedTextField();
        this.parentregionbox.setHeight(15);
        this.parentregionbox.setMargin(1, 5);
        this.container6.addElement(parentregionbox, 1, 2);
        this.container6.addElement(new FonctionButton(this, "Set Parent"), 2, 2);
        
        this.playerbox = new CorrectedTextField();
        this.playerbox.setHeight(15);
        this.playerbox.setMargin(1, 5);
        this.container6.addElement(playerbox, 1, 3);
        this.container6.addElement(new FonctionButton(this, "Add Owner"), 2, 3);
        this.container6.addElement(new FonctionButton(this, "Rem Owner"), 3, 3);
        this.container6.addElement(new FonctionButton(this, "Add Member"), 4, 3);
        this.container6.addElement(new FonctionButton(this, "Rem Member"), 5, 3);
        
        this.prioritybox = new CorrectedTextField();
        this.prioritybox.setHeight(15);
        this.prioritybox.setMargin(1, 5);
        this.container6.addElement(prioritybox, 3, 2);
        this.container6.addElement(new FonctionButton(this, "Set Priority"), 4, 2);
        
        this.container6.addElement(new FonctionButton(this, "Select"), 3, 1);
        this.container6.addElement(new FonctionButton(this, "Remove"), 4, 1);
        this.container6.addElement(new FonctionButton(this, "Claim"), 5, 1);
        
        this.container6.setMarginBottom(5);
		
		this.container2.addChildren(this.container6, this.container3);
		
        this.worldchooser = new Finder(this, "world", this.worldsNames);
        this.worldchooser.setHeight(80).setWidth(120).setFixed(true);
        this.regionchooser = new Finder(this, "region", this.regionsNames);
        this.regionchooser.setHeight(80).setWidth(120).setFixed(true);
        
        this.container1.addChildren(this.worldchooser, this.regionchooser);
        this.container.addChildren(this.container1, this.container2);

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
		if(var.equalsIgnoreCase("world"))
		{
			World w = Bukkit.getWorld(value);
			this.regionsNames.clear();
			this.regionsNames.addAll(this.worldGuard.getRegionManager(w).getRegions().keySet());
			this.regionchooser.setChoices(this.regionsNames);
		}
		else if(var.equalsIgnoreCase("region"))
		{
			System.out.println("choosing region "+value);
			this.regionbox.setText(value);
		}
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		String s=event.getButton().getText();
		String command="/region ";
		
		String priority=this.prioritybox.getText();
		String regionname=this.regionbox.getText();
		String player=this.playerbox.getText();
		String parentregion=this.parentregionbox.getText();
		
		if(s.equalsIgnoreCase("(re)define"))
		{			
			if(this.regionsNames.contains(regionname))
				command+="re";
			command+="define "+regionname;
			user.chat(command); //execute command in the chat
		}
		else if(s.equalsIgnoreCase("Set Parent"))
		{
			command+="setparent "+regionname+" "+parentregion;
			user.chat(command); //execute command in the chat
		}
		else if(s.equalsIgnoreCase("Add Owner"))
		{
			command+="addowner "+regionname+" "+player;
			user.chat(command); //execute command in the chat
		}
		else if(s.equalsIgnoreCase("Rem Owner"))
		{
			command+="remowner "+regionname+" "+player;
			user.chat(command); //execute command in the chat
		}
		else if(s.equalsIgnoreCase("Add Member"))
		{
			command+="addmember "+regionname+" "+player;
			user.chat(command); //execute command in the chat
		}
		else if(s.equalsIgnoreCase("Rem Member"))
		{
			command+="remmember "+regionname+" "+player;
			user.chat(command); //execute command in the chat
		}
		else if(s.equalsIgnoreCase("Set Priority"))
		{
			command+="setpriority "+regionname+" "+priority;
			user.chat(command); //execute command in the chat
		}
		else if(s.equalsIgnoreCase("Select"))
		{
			command+="select "+regionname;
			user.chat(command); //execute command in the chat
		}
		else if(s.equalsIgnoreCase("Remove"))
		{
			command+="remove "+regionname;
			user.chat(command); //execute command in the chat
		}
		else if(s.equalsIgnoreCase("Claim"))
		{
			command+="claim "+regionname;
			user.chat(command); //execute command in the chat
		}
		else if(s.equalsIgnoreCase("Set Flags"))
		{
			for(String flag:this.flags.keySet())
			{
				FlagChooser flagchooser=this.flags.get(flag);
				if(!flagchooser.inDefaultState())
				{
					command="/region flag "+regionname+" "+flagchooser.getText()+" "+flagchooser.getValue();
					user.chat(command); //execute command in the chat
				}
			}
		}
	}
}
//TODO: add button to reset the flags
//TODO: add loading of the flags of a region
//TODO: add missing fields to configure a region - add the paramaters missing witch are not 2 state flags
