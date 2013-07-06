package ss.linearlogic.playersearch;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PSEvents implements Listener {
	private PlayerSearch plugin;
	public PSEvents(PlayerSearch instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String name = event.getPlayer().getName();
		if (!plugin.totalPlayers.contains(name)) {			
			plugin.totalPlayers.add(name);
			plugin.getConfig().set("players", plugin.totalPlayers);
			plugin.saveConfig();
		}
	}
}
