package pl.malezjaa.server_utils;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ChatUtils {
    public static int client_error(ServerPlayer player, String msg) {
        player.displayClientMessage(Component.literal(msg), false);

        return 0;
    }

    public static int client_success(ServerPlayer player, String msg) {
        player.displayClientMessage(Component.literal(msg), false);

        return 1;
    }
}
