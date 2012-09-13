package ss.linearlogic.playersearch;


import ss.linearlogic.playersearch.PSLogger;
import ss.linearlogic.playersearch.PSEvents;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class PlayerSearch extends JavaPlugin
{
	File configFile;
	public static FileConfiguration config;
	
	private PSEvents listener = new PSEvents(this);
	
	private PSCommands cmdExecutor = new PSCommands(this);
	
	public List<String> totalPlayers;
	
	int number;
	
	public void onEnable()
	{
		PSLogger.logInfo("Loading config.yml...");
		configFile = new File(getDataFolder(), "config.yml");
		try {firstRunConfiguration();
		} catch (Exception e) {
			e.printStackTrace();
		}
		config = new YamlConfiguration();
		loadConfig();
		totalPlayers = getConfig().getStringList("players");
		
		PSLogger.logInfo("Registering listener...");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(listener, this);
		
		PSLogger.logInfo("Activating command handler");
		getCommand("lookup").setExecutor(cmdExecutor);
		
		PSLogger.logInfo("Plugin successfully enabsled!");
	}
	
	public void onDisable()
	{
		PSLogger.logInfo("Saving players...");
		saveConfig();
		
		PSLogger.logInfo("Plugin successfully disabled!");
	}

//===========================Config file handling===========================//
	private void firstRunConfiguration() throws Exception
	{
		if(!configFile.exists())
		{
			PSLogger.logInfo("Config not found, Creating one for you.");
			configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), configFile);
		}
	}
	
	private void copy(InputStream in, File file)
	{
		try
		{
			OutputStream out = new FileOutputStream(file);
			byte[] bBuffer = new byte[1024];
			int len;
			while ((len = in.read(bBuffer)) > 0)
			{
				out.write(bBuffer, 0, len);
			}
			out.close();
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadConfig()
	{
		try
		{
			config.load(configFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
//	public void saveConfig()
//	{
//		try
//		{
//			config.save(configFile);
//		}
//		catch (IOException ioe)
//		{
//			ioe.printStackTrace();
//		}
//	}
}