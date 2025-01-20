package pl.malezjaa.server_utils.teleports;

import net.minecraft.server.level.ServerPlayer;
import pl.malezjaa.server_utils.SUPlayer;

import java.util.HashMap;
import java.util.Map;

public interface TeleportManager {
    public static final int COOLDOWN = 10;
    public static final Map<String, Position> destinations = new HashMap<>();

    int teleport(ServerPlayer player, String destination);

    default boolean has(String destination) {
        return destinations.containsKey(destination);
    }
}
