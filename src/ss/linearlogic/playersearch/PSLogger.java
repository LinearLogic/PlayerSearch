package ss.linearlogic.playersearch;

import java.util.logging.Logger;

public class PSLogger
{
  private static final Logger log = Logger.getLogger("PlayerSearch");

  public static void logInfo(String message)
  {
    log.info("[PlayerSearch] " + message);
  }
  
  public static void logWarning(String message) {
    log.warning("[PlayerSearch] " + message);
  }

  public static void logSevere(String message) {
    log.severe("[PlayerSearch] " + message);
  }
}