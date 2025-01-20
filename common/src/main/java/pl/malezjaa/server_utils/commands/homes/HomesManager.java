package pl.malezjaa.server_utils.commands.homes;

import net.minecraft.server.level.ServerPlayer;
import pl.malezjaa.server_utils.ChatUtils;
import pl.malezjaa.server_utils.SUPlayer;
import pl.malezjaa.server_utils.ServerUtils;
import pl.malezjaa.server_utils.teleports.TeleportManager;

public class HomesManager implements TeleportManager {
    @Override
    public int teleport(ServerPlayer player, String destination) {
        if (!this.has(destination)) {
            return ChatUtils.client_error(player, String.format(ServerUtils.config().homes.nonexistent_home, destination));
        }

        return 1;
    }
}
