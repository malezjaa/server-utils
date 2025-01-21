package pl.malezjaa.server_utils.teleports;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import pl.malezjaa.server_utils.ChatUtils;
import pl.malezjaa.server_utils.ServerUtils;

public class Position {
    public double x;
    public double y;
    public double z;
    public ResourceKey<Level> world;

    public Position(double x, double y, double z, ResourceKey<Level> world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public Position() {
    }

    public int teleport(ServerPlayer player) {
        ServerLevel level = player.server.getLevel(world);

        if (level == null) {
            return ChatUtils.client_error(player, ServerUtils.config().homes.invalid_dimension);
        }

        int xpLevel = player.experienceLevel;
        player.teleportTo(level, x + 0.5D, y + 0.1D, z + 0.5D, player.getYRot(), player.getXRot());
        player.setExperienceLevels(xpLevel);
        return 1;
    }
}
