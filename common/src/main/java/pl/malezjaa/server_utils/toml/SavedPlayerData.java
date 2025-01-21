package pl.malezjaa.server_utils.toml;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SavedPlayerData {
    private List<SavedPlayer> players;

    public SavedPlayerData() {
        this.players = new ArrayList<>();
    }

    public List<SavedPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<SavedPlayer> players) {
        this.players = players;
    }

    public void addPlayer(SavedPlayer player) {
        this.players.add(player);
    }

    public void removePlayer(UUID uuid) {
        this.players.removeIf(player -> player.getUuid().equals(uuid));
    }

    public SavedPlayer getPlayer(UUID uuid) {
        return this.players.stream()
                .filter(player -> player.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }
}

