package pl.malezjaa.server_utils;

import com.mojang.brigadier.CommandDispatcher;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import pl.malezjaa.server_utils.commands.Command;
import pl.malezjaa.server_utils.commands.homes.HomeCommands;

import java.util.ArrayList;
import java.util.List;

public class Events {
    public static void init() {
        CommandRegistrationEvent.EVENT.register(Events::registerCommands);
    }

    private static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        ServerUtils.LOGGER.info("Registering Server Utils commands");

        List<Command> commands = new ArrayList<>(
                List.of(new HomeCommands())
        );

        for (Command command : commands) {
            for (var builder : command.register()) {
                dispatcher.register(builder);
            }
        }
    }
}
