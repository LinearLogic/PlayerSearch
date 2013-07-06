package ss.linearlogic.playersearch;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PSCommands implements CommandExecutor {

	private PlayerSearch plugin;
	private final String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "PlayerSearch" + ChatColor.GRAY + "] ";

	public PSCommands(PlayerSearch instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player) && (!sender.hasPermission("playersearch.lookup")) &&
				(!sender.hasPermission("playersearch.find")) && (!sender.hasPermission("playersearch.search"))) {
			sender.sendMessage(prefix + ChatColor.RED + "Uh oh! " + ChatColor.GRAY + "You don't have permission to " +
					"search for players.");
			return true;
		}
		if (args.length != 1) {
			sender.sendMessage(prefix + ChatColor.RED + "Too " + (args.length == 0 ? "few" : "many") + "arguments - " + 
					ChatColor.GRAY + "Usage is /lookup <name>. The name doesn't need to be complete. Enter '*' as " +
					"the name to list all players that have ever logged on to the server.");
			return true;
		}

		ArrayList<String> onlinePlayers = new ArrayList<String>();
		ArrayList<String> offlinePlayers = new ArrayList<String>();
		String search = args[0];
		for (int i = 0; i < plugin.totalPlayers.size(); i++) {
			String name = plugin.totalPlayers.get(i);
			if (!search.equals("*") && !name.toLowerCase().contains(search.toLowerCase()))
				continue;
			if (plugin.getServer().getPlayer(name) != null)
				onlinePlayers.add(name);
			else
				offlinePlayers.add(name);
		}
		int resultCount = 0;
		resultCount += onlinePlayers.size();
		resultCount += offlinePlayers.size();
		sender.sendMessage(prefix + "There are " + ChatColor.DARK_PURPLE + resultCount + ChatColor.GRAY + " players " +
				"whose names contain \"" + ChatColor.AQUA + args[0] + ChatColor.GRAY + "\":\n" + ChatColor.GREEN +
				"Online: " + ChatColor.GRAY + onlinePlayers.toString() + "\n" + ChatColor.RED + "Offline: " +
				ChatColor.GRAY + offlinePlayers.toString());
		return true;
	}
}
