package me.blaise.ExtendAdminGUI.commands;

import java.util.logging.Level;

import me.blaise.ExtendAdminGUI.ExtendAdminGUIMain;
import me.blaise.ExtendAdminGUI.gui.TestGUI;
import me.blaise.lib.command.AbstractCommandHandler;
import me.blaise.lib.main.CustomPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;



public class TestScreenCommandHandler extends AbstractCommandHandler {
	
	private ExtendAdminGUIMain plugin;

	public TestScreenCommandHandler(){
		this.plugin=((ExtendAdminGUIMain)ExtendAdminGUIMain.getInstance());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, Player player) {
		if(player != null) {
			if(args.length!=0)
        	{
        		
        	}
        	else
        	{
    			CustomPlugin.doLog("Loading test Screen", Level.FINEST);
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
                    		new TestGUI(user, this.plugin);
                    }
            	}
    			return true;
        	}
        }
		this.printHelp(sender);
		return false;
	}

	@Override
	public String getUsage() {
		return "testscreen";
	}

	@Override
	public String getCommandLabel() {
		return "testscreen";
	}

	@Override
	public boolean isIngameOnly() {
		return true;
	}

	@Override
	public String getPermission() {
		return "extendadmingui.loadscreen";
	}

	@Override
	public void printHelp(CommandSender sender) {
		String prefixe = "/";
		if(!(sender instanceof Player)) {
			if(!this.isIngameOnly()){
				prefixe = "";
			}
		}
		sender.sendMessage(ChatColor.YELLOW + prefixe + " " + this.getCommandLabel() + ChatColor.WHITE + " " + this.getUsage());
	}


}
