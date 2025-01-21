package pl.malezjaa.server_utils.toml;

import pl.malezjaa.server_utils.teleports.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SavedPlayer {
    private UUID uuid;
    private String name;
    private long lastSeen;
    private HashMap<String, Position> homes;

    public SavedPlayer() {
    }

    public SavedPlayer(UUID uuid, String name, long lastSeen, HashMap<String, Position> homes) {
        this.uuid = uuid;
        this.name = name;
        this.lastSeen = lastSeen;
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

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public HashMap<String, Position> getHomes() {
        return homes;
    }

    public void setHomes(HashMap<String, Position> homes) {
        this.homes = homes;
    }
}