package me.blaise.ExtendAdminGUI.commands;

import java.util.logging.Level;

import me.blaise.ExtendAdminGUI.ExtendAdminGUIMain;
import me.blaise.ExtendAdminGUI.gui.ExtendAdminGUI;
import me.blaise.lib.command.AbstractCommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;



public class LoadScreenCommandHandler extends AbstractCommandHandler {
	
	private ExtendAdminGUIMain plugin;

	public LoadScreenCommandHandler(){
		this.plugin=((ExtendAdminGUIMain)ExtendAdminGUIMain.getInstance());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, Player player) {
		if(player != null) {
			if(args.length!=1)
        	{
				printHelp(sender);
				return false;
        	}
			else
        	{
	            if(plugin.getScreens().containsKey(args[0].toLowerCase()))
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
	                    		new ExtendAdminGUI(((ExtendAdminGUIMain)ExtendAdminGUIMain.getInstance()).getScreens().get(args[0].toLowerCase()), user, this.plugin );
	                    }
	            	}
        	        ExtendAdminGUIMain.doLog("Screen "+args[0]+" loaded by "+sender.getName(),Level.FINEST);
                	return true;
	            }
	            if(sender instanceof Player)
	                sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Load Failed - Check your layout files!").toString());
	            return false;
        	}
        }
		this.printHelp(sender);
		return false;
	}

	@Override
	public String getUsage() {
		return "loadscreen screenname";
	}

	@Override
	public String getCommandLabel() {
		return "loadscreen";
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
