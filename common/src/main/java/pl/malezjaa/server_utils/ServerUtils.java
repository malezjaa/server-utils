package pl.malezjaa.server_utils;

import dev.architectury.platform.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.malezjaa.server_utils.toml.SavedPlayerData;
import pl.malezjaa.server_utils.toml.ServerConfig;
import pl.malezjaa.server_utils.toml.TomlFileManager;

import java.nio.file.Path;

public final class ServerUtils {
    public static final String MOD_ID = "server_utils";
    public static final Logger LOGGER = LogManager.getLogger("Server Utils");

    private static TomlFileManager<ServerConfig> configManager;
    private static TomlFileManager<SavedPlayerData> playerData;

    public static void init() {
        configManager = new TomlFileManager<ServerConfig>(
                Platform.getConfigFolder().resolve(MOD_ID + ".toml"),
                ServerConfig.class,
                () -> new String[]{
                        "# Default config for server utils mod",
                        "[homes]",
                        "limit = 5"
                }
        );
        playerData = new TomlFileManager<SavedPlayerData>(
                Platform.getGameFolder().resolve(Path.of("world", "server_utils", "playerdata.toml")),
                SavedPlayerData.class,
                () -> new String[]{}
        );

        Events.init();
    }

    public static ServerConfig config() {
        return configManager.getContent();
    }

    public static TomlFileManager<SavedPlayerData> playerData() {
        return playerData;
    }
}