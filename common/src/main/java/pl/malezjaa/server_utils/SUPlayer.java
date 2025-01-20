package pl.malezjaa.server_utils;

import net.minecraft.world.entity.player.Player;
import pl.malezjaa.server_utils.commands.homes.HomesManager;
import pl.malezjaa.server_utils.teleports.TeleportManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SUPlayer {
    private static final Map<UUID, SUPlayer> players = new HashMap<>();
    private static final HomesManager homes = new HomesManager();

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

    public HomesManager homes() {
        return homes;
    }
}