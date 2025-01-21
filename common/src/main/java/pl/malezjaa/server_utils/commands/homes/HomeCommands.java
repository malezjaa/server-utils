package pl.malezjaa.server_utils.commands.homes;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import pl.malezjaa.server_utils.ChatUtils;
import pl.malezjaa.server_utils.SUPlayer;
import pl.malezjaa.server_utils.ServerUtils;
import pl.malezjaa.server_utils.commands.Command;
import pl.malezjaa.server_utils.teleports.Position;

import java.util.List;

public class HomeCommands implements Command {
    @Override
    public List<LiteralArgumentBuilder<CommandSourceStack>> register() {
        return List.of(
                // TODO: add suggestions
                Commands.literal("home")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .executes(context -> tpHome(context.getSource().getPlayerOrException(), StringArgumentType.getString(context, "name")))
                        ),
                Commands.literal("sethome")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .executes(context -> setHome(context.getSource().getPlayerOrException(), StringArgumentType.getString(context, "name")))
                        )
        );
    }

    public int tpHome(ServerPlayer player, String homeName) {
        SUPlayer pl = SUPlayer.fromMcPlayer(player);
        pl.homes().teleport(player, homeName);

        return 1;
    }

    public int setHome(ServerPlayer player, String homeName) {
        SUPlayer pl = SUPlayer.fromMcPlayer(player);
        Position pos = new Position(player.getX(), player.getY(), player.getZ(), player.level().dimension());

        if (pl.homes().numHomes() >= ServerUtils.config().homes.limit) {
            return ChatUtils.client_error(player, String.format(ServerUtils.config().homes.limit_reached, ServerUtils.config().homes.limit));
        }
        pl.homes().addHome(homeName, pos);

        return 1;
    }
}
