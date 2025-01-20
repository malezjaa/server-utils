package pl.malezjaa.server_utils.neoforge;

import pl.malezjaa.server_utils.ServerUtils;
import net.neoforged.fml.common.Mod;

@Mod(ServerUtils.MOD_ID)
public final class ServerUtilsNeoForge {
    public ServerUtilsNeoForge() {
        // Run our common setup.
        ServerUtils.init();
    }
}
