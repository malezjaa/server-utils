package pl.malezjaa.server_utils.toml;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import pl.malezjaa.server_utils.teleports.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

public class SavedPlayer {
    private UUID uuid;
    private String name;
    private HashMap<String, Position> homes;

    public SavedPlayer() {
    }

    public SavedPlayer(UUID uuid, String name, HashMap<String, Position> homes) {
        this.uuid = uuid;
        this.name = name;
        this.homes = homes;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Position> getHomes() {
        return homes;
    }

    public void setHomes(HashMap<String, Position> homes) {
        this.homes = homes;
    }
}
