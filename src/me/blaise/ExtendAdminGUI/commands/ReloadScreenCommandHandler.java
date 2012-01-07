package me.blaise.ExtendAdminGUI.commands;


import me.blaise.ExtendAdminGUI.ExtendAdminGUIMain;
import me.blaise.lib.command.AbstractCommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadScreenCommandHandler extends AbstractCommandHandler {
	
	private ExtendAdminGUIMain plugin;

	public ReloadScreenCommandHandler(){
		this.plugin=((ExtendAdminGUIMain)ExtendAdminGUIMain.getInstance());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, Player player) {
		if(player != null) {
			if(args.length!=0)
        	{
				printHelp(sender);
				return false;
        	}
			else
        	{
	           plugin.loadScreens();
	           return true;
        	}
        }
		this.printHelp(sender);
		return false;
	}

	@Override
	public String getUsage() {
		return "reloadscreens";
	}

	@Override
	public String getCommandLabel() {
		return "reloadscreens";
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
