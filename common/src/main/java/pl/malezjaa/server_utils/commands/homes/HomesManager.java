package pl.malezjaa.server_utils.commands.homes;

import net.minecraft.server.level.ServerPlayer;
import pl.malezjaa.server_utils.ChatUtils;
import pl.malezjaa.server_utils.SUPlayer;
import pl.malezjaa.server_utils.ServerUtils;
import pl.malezjaa.server_utils.teleports.Position;
import pl.malezjaa.server_utils.teleports.TeleportManager;
import pl.malezjaa.server_utils.toml.SavedPlayer;

import java.util.HashMap;

public class HomesManager implements TeleportManager {
    public final HashMap<String, Position> playerHomes = new HashMap<>();

    @Override
    public int teleport(ServerPlayer player, String destination) {
        if (!this.has(destination)) {
            return ChatUtils.client_error(player, String.format(ServerUtils.config().homes.nonexistent_home, destination));
        }

        Position position = playerHomes.get(destination);
        return position.teleport(player);
    }

    @Override
    public boolean has(String destination) {
        return playerHomes.containsKey(destination);
    }

    public static HomesManager fromSaved(SavedPlayer player) {
        HomesManager homesManager = new HomesManager();

        HashMap<String, Position> savedHomes = player.getHomes();

        if (savedHomes != null) {
            homesManager.playerHomes.putAll(savedHomes);
        }

        return homesManager;
    }

    public void addHome(String name, Position pos) {
        playerHomes.put(name, pos);
    }

    public int numHomes() {
        return playerHomes.size();
    }
}