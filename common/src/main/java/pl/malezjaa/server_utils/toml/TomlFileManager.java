package pl.malezjaa.server_utils.toml;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import dev.architectury.platform.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.function.Supplier;

public class TomlFileManager<T> {
    private static final Logger LOGGER = LogManager.getLogger("File Manager");

    private final TomlMapper mapper;
    private final File file;
    private final Class<T> type;
    private final Supplier<String[]> defaultContent;
    private T content;

    public TomlFileManager(Path filePath, Class<T> type, Supplier<String[]> defaultContent) {
        this.mapper = new TomlMapper();
        this.file = filePath.toFile();
        this.type = type;
        this.defaultContent = defaultContent;
        initialize();
    }

    private void initialize() {
        if (!exists()) {
            createFile();
        } else {
            readContent();
        }
    }

    public boolean exists() {
        return file.exists();
    }

    public T getContent() {
        return content;
    }

    public void setContent(T newContent) {
        this.content = newContent;
    }

    public File getFile() {
        return file;
    }

    public void save() {
        try {
            mapper.writeValue(file, content);
            LOGGER.info("Successfully saved file: {}", file.getName());
        } catch (Exception e) {
            LOGGER.error("Failed to save file: {}", file.getName(), e);
        }
    }

    private void readContent() {
        try {
            content = mapper.readValue(file, type);
            LOGGER.info("Successfully loaded file: {}", file.getName());
        } catch (Exception e) {
            LOGGER.error("Failed to read file: {}", file.getName(), e);
            createFile();
        }
    }

    private void createFile() {
        try {
            createParentDirectories();

            if (file.createNewFile()) {
                writeDefaultContent();
                readContent();
                LOGGER.info("Successfully created new file: {}", file.getName());
            } else {
                LOGGER.error("Failed to create file: {}", file.getName());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to create file: {}", file.getName(), e);
        }
    }

    private void createParentDirectories() {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    private void writeDefaultContent() {
        if (defaultContent == null) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : defaultContent.get()) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            LOGGER.error("Failed to write default content to file: {}", file.getName(), e);
        }
    }
}