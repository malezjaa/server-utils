package pl.malezjaa.server_utils;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import dev.architectury.platform.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

public class ConfigManager {
    private static final Logger LOGGER = LogManager.getLogger("Server Utils Config");
    private static final String CONFIG_FILE = "server_utils.toml";
    private static final String[] CONFIG_DEFAULT = {
            "# Default config for server utils mod",
            "[homes]",
            "limit = 5"
    };

    private final TomlMapper tomlMapper;
    private ServerConfig config;
    private final File configFile;

    public ConfigManager() {
        this.tomlMapper = new TomlMapper();
        this.configFile = Platform.getConfigFolder().resolve(CONFIG_FILE).toFile();
        loadConfig();
    }

    private void loadConfig() {
        if (configFile.exists()) {
            LOGGER.info("Config file exists at {}", configFile);
            try {
                config = tomlMapper.readValue(configFile, ServerConfig.class);
                LOGGER.info("Successfully loaded config file");
            } catch (Exception e) {
                LOGGER.error("Failed to read config file", e);
                createDefaultConfig();
            }
        } else {
            LOGGER.info("Creating config file");
            createDefaultConfig();
        }
    }

    private void createDefaultConfig() {
        try {
            if (configFile.createNewFile()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
                    for (String line : CONFIG_DEFAULT) {
                        writer.write(line);
                        writer.newLine();
                    }
                }

                config = tomlMapper.readValue(configFile, ServerConfig.class);
                LOGGER.info("Successfully created default config file");
            } else {
                LOGGER.error("Failed to create config file");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to create or read config file", e);
        }
    }

    public void saveConfig() {
        try {
            tomlMapper.writeValue(configFile, config);
            LOGGER.info("Successfully saved config file");
        } catch (Exception e) {
            LOGGER.error("Failed to save config file", e);
        }
    }

    public ServerConfig getConfig() {
        return config;
    }
}
