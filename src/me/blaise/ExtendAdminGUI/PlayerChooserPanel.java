package me.blaise.ExtendAdminGUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.*;

public class PlayerChooserPanel extends GenericContainer implements Chooser
{
    private ExtendAdminGUI app_root;
	private ArrayList<String> players;
	private Finder playerfinder;
	private ChoiceButton self_button;
	
	
    
	public PlayerChooserPanel(int width, int height, ExtendAdminGUI instance)//TODO: use a parent to fill with the choosen name
    {
    	this.players = new ArrayList<String>();
    	this.app_root = instance;
    	//Finding all the players
        ArrayList<Player> temp = new ArrayList<Player>();
        Collections.addAll(temp, Bukkit.getServer().getOnlinePlayers());
        temp.remove(this.app_root.user);
        //Storing all the players names
        Player p;
        for(Iterator<Player> iterator = temp.iterator(); iterator.hasNext(); players.add(p.getName()))
            p = iterator.next();
        //Sorting the list of players
        Collections.sort(players);
        
        //drawing the stuff
        this.setWidth(width).setHeight(height);
        this.setFixed(true);
        this.setMargin(10, 0);
        if(this.app_root.plugin.config.getBoolean("debug_mode"))
        {
			this.app_root.log((new StringBuilder("[ExtendAdminGUI - debug] PlayerChooserPanel created")).toString());
        }
        
        this.playerfinder=new Finder(this, "player", players);
        
        //--------------- self button -----------------//
        self_button = new ChoiceButton("player", this.app_root.user.getName(), this);
        self_button.setHeight(15);
        self_button.setWidth(110);
        self_button.setFixed(true);
        self_button.setMargin(1,5);
      
        //fitting all
        this.setLayout(ContainerType.VERTICAL);
        this.addChildren(playerfinder, self_button);
        this.setAnchor(WidgetAnchor.CENTER_CENTER);
    }

    public void clear()
    {
        for(Widget w : getChildren())
        {
            removeChild(w);
        }
    }
    
	public void choose(String var, String name)
	{
		this.app_root.fillLastEntry(name);
	}
}
