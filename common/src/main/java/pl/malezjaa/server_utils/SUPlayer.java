package pl.malezjaa.server_utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.architectury.platform.Platform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import pl.malezjaa.server_utils.commands.homes.HomesManager;
import pl.malezjaa.server_utils.teleports.Position;
import pl.malezjaa.server_utils.toml.SavedPlayer;
import pl.malezjaa.server_utils.toml.TomlFileManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static pl.malezjaa.server_utils.ServerUtils.LOGGER;
import static pl.malezjaa.server_utils.ServerUtils.MOD_ID;

//! We currently have the serialization in here, because compiler returns some wierd errors.
class SavedPlayerTomlSerializer extends JsonSerializer<SavedPlayer> {
    @Override
    public void serialize(SavedPlayer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        // Write the player section
        gen.writeFieldName("player");
        gen.writeStartObject();
        gen.writeStringField("uuid", value.getUuid().toString());
        gen.writeStringField("name", value.getName());
        gen.writeEndObject();

        // Write homes section
        gen.writeFieldName("homes");
        gen.writeStartObject();
        for (Map.Entry<String, Position> home : value.getHomes().entrySet()) {
            gen.writeFieldName(home.getKey());
            gen.writeStartObject();
            Position pos = home.getValue();
            gen.writeNumberField("x", pos.x);
            gen.writeNumberField("y", pos.y);
            gen.writeNumberField("z", pos.z);
            gen.writeStringField("level", pos.world.location().toString());
            gen.writeEndObject();
        }
        gen.writeEndObject();

        gen.writeEndObject();
    }
}

class SavedPlayerTomlDeserializer extends JsonDeserializer<SavedPlayer> {
    @Override
    public SavedPlayer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        JsonNode playerNode = node.get("player");
        UUID uuid = UUID.fromString(playerNode.get("uuid").asText());
        String name = playerNode.get("name").asText();

        HashMap<String, Position> homes = new HashMap<>();
        JsonNode homesNode = node.get("homes");
        if (homesNode != null && homesNode.isObject()) {
            homesNode.fields().forEachRemaining(entry -> {
                JsonNode posNode = entry.getValue();
                Position pos = new Position(
                        posNode.get("x").asDouble(),
                        posNode.get("y").asDouble(),
                        posNode.get("z").asDouble(),
                        ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(posNode.get("level").toString()))
                );
                homes.put(entry.getKey(), pos);
            });
        }

        return new SavedPlayer(uuid, name, homes);
    }
}

class SavedPlayerTomlModule extends SimpleModule {
    public SavedPlayerTomlModule() {
        addSerializer(SavedPlayer.class, new SavedPlayerTomlSerializer());
        addDeserializer(SavedPlayer.class, new SavedPlayerTomlDeserializer());
    }
}

public class SUPlayer {
    private static final Map<UUID, SUPlayer> players = new HashMap<>();
    public static HomesManager homes = new HomesManager();

    String name;
    UUID uuid;

    public SUPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public static SUPlayer fromMcPlayer(Player player) {
        UUID uuid = player.getUUID();
        String name = player.getName().getString();

        return players.computeIfAbsent(uuid, key -> new SUPlayer(uuid, name));
    }

    public static void loadFromData(ServerPlayer pl) {
        Path filePath = Platform.getGameFolder().resolve(Path.of("world", MOD_ID, "playerdata", pl.getStringUUID() + ".toml"));
        LOGGER.info("Loading player data for " + pl.getStringUUID() + " from " + filePath);

        TomlFileManager<SavedPlayer> playerManager = new TomlFileManager<>(
                filePath,
                SavedPlayer.class,
                () -> new String[]{},
                tomlMapper -> {
                    tomlMapper.registerModule(new SavedPlayerTomlModule());
                }
        );

        SavedPlayer savedPlayer = playerManager.getContent();
        SUPlayer player = SUPlayer.fromMcPlayer(pl);

        if (savedPlayer != null) {
            player.homes = HomesManager.fromSaved(savedPlayer);
        }

        players.put(pl.getUUID(), player);
    }

    public HomesManager homes() {
        return homes;
    }

    public void save(ServerPlayer pl) {
        Path filePath = Platform.getGameFolder().resolve(Path.of("world", MOD_ID, "playerdata", pl.getStringUUID() + ".toml"));

        TomlFileManager<SavedPlayer> playerManager = new TomlFileManager<>(
                filePath,
                SavedPlayer.class,
                () -> new String[]{},
                tomlMapper -> {
                    tomlMapper.registerModule(new SavedPlayerTomlModule());
                }
        );

        playerManager.setContent(
                new SavedPlayer(this.uuid, this.name, this.homes().playerHomes)
        );
        playerManager.save();
    }
}