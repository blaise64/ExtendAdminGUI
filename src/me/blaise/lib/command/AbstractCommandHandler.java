package me.blaise.lib.command;

import java.util.HashMap;
import java.util.logging.Level;

import me.blaise.lib.main.CustomPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public abstract class AbstractCommandHandler {
	private static HashMap<String, AbstractCommandHandler> handlers = new HashMap<String, AbstractCommandHandler>();
	
	public static boolean handleCommand(CommandSender sender, Command cmd, String commandLabel, String args[]) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player)sender;
		}
		AbstractCommandHandler handler = handlers.get(cmd.getName());
		if(handler != null) {
			if(player == null && handler.isIngameOnly()) {
				sender.sendMessage("This can be used ingame only");
				return true;
			}
			return handler.onCommand(sender, cmd, commandLabel, args, player);
		} else {
			sender.sendMessage("Command '"+cmd.getName()+"' not found.");
			printAllHelp(sender);
			return true;
		}
	}
	
	public static void registerHandler(AbstractCommandHandler handler) {
		handlers.put(handler.getCommandLabel(), handler);
		CustomPlugin.doLog("Registered Command "+handler.getCommandLabel(), Level.FINEST);
	}
	
	public static void printAllHelp(CommandSender sender) {
		sender.sendMessage("CustomPlugin command reference");
		for(AbstractCommandHandler handler:handlers.values()) {
			handler.printHelp(sender);
		}
	}
	
	public abstract void printHelp(CommandSender sender);
	public abstract boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[], Player player);
	public abstract String getUsage();
	public abstract String getCommandLabel();
	public abstract boolean isIngameOnly();
	public abstract String getPermission();
}
