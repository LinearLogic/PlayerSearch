package ss.linearlogic.playersearch;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

public class PlayerSearch extends JavaPlugin {

	private PSEvents listener = new PSEvents(this);
	private PSCommands cmdExecutor = new PSCommands(this);
	public List<String> totalPlayers;

	public void onEnable() {
		logInfo("Loading players...");
		saveDefaultConfig();
		totalPlayers = getConfig().getStringList("players");

		logInfo("Registering listener...");
		getServer().getPluginManager().registerEvents(listener, this);

		logInfo("Activating command handler...");
		getCommand("lookup").setExecutor(cmdExecutor);

		logInfo("Enabled!");
	}

	public void onDisable() {
		logInfo("Saving players...");
		saveConfig();
		logInfo("Disabled!");
	}

	public void logInfo(String msg) {
		getLogger().info(msg);
	}

	public void logWarning(String msg) {
		getLogger().warning(msg);
	}

	public void logSevere(String msg) {
		getLogger().severe(msg);
	}
}
