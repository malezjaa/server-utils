package pl.malezjaa.server_utils.toml;

import pl.malezjaa.server_utils.teleports.Position;

import java.util.HashMap;
import java.util.UUID;

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
