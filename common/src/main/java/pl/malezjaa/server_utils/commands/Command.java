package pl.malezjaa.server_utils.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

import java.util.List;

public interface Command {
    List<LiteralArgumentBuilder<CommandSourceStack>> register();
}
