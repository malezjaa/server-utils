package pl.malezjaa.server_utils.commands.homes;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import pl.malezjaa.server_utils.SUPlayer;
import pl.malezjaa.server_utils.commands.Command;

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
                        )
        );
    }

    public int tpHome(ServerPlayer player, String homeName) {
        SUPlayer pl = SUPlayer.fromMcPlayer(player);
        pl.homes().teleport(player, homeName);

        return 1;
    }
}
