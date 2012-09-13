package ss.linearlogic.playersearch;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PSCommands implements CommandExecutor
{
	private PlayerSearch plugin;
	public PSCommands(PlayerSearch instance)
	{
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if ((sender instanceof Player) && (!sender.hasPermission("playersearch.lookup")) && (!sender.hasPermission("playersearch.find")) && (!sender.hasPermission("playersearch.search"))) {
			sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "PlayerSearch" + ChatColor.GRAY + "] " + ChatColor.RED + "Uh oh! " + ChatColor.GRAY + "You don't have permission to search for players.");
			return true;
		}
		if (args.length != 1) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "PlayerSearch" + ChatColor.GRAY + "] " + ChatColor.RED + "Too few arguments. " + ChatColor.GRAY + "Usage is /lookup <name>. The name doesn't need to be complete.");
				return true;
			}
			sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "PlayerSearch" + ChatColor.GRAY + "] " + ChatColor.RED + "Too many arguments. " + ChatColor.GRAY + "Usage is /lookup <name>. The name doesn't need to be complete.");
			return true;
		}
		ArrayList<String> onlinePlayers = new ArrayList<String>();
		ArrayList<String> offlinePlayers = new ArrayList<String>();
		String search = args[0];
		for (Player player : plugin.getServer().getOnlinePlayers()) // search online players first, to make sure the offline player results aren't redundant
		{
			if (player.getName().toLowerCase().contains(search.toLowerCase())) {
				onlinePlayers.add(player.getName());
			}
		}
		for (String playerName : plugin.totalPlayers) // search all players, and only record those that are offline
		{
			if ((playerName.toLowerCase().contains(search.toLowerCase())) && (!onlinePlayers.contains(playerName))) {
				offlinePlayers.add(playerName);
			}
		}
		int resultCount = 0;
		if (!onlinePlayers.isEmpty()) {
			resultCount += onlinePlayers.size();
		}
		if (!offlinePlayers.isEmpty()) {
			resultCount += offlinePlayers.size();
		}
		sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "PlayerSearch" + ChatColor.GRAY + "] There are " + ChatColor.AQUA + resultCount + ChatColor.GRAY + " players whose names contain \"" + ChatColor.AQUA + args[0] + ChatColor.GRAY + "\":\n" + ChatColor.GREEN + "Online: " + ChatColor.GRAY + onlinePlayers.toString() + "\n" + ChatColor.RED + "Offline: " + ChatColor.GRAY + offlinePlayers.toString());
		return true;
	}
}
