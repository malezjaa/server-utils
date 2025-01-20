package pl.malezjaa.server_utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ServerUtils {
    public static final String MOD_ID = "server_utils";
    public static final Logger LOGGER = LogManager.getLogger("Server Utils");

    private static ConfigManager configManager;

    public static void init() {
        configManager = new ConfigManager();
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}