package io.github.axolotsh.regexMapper.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.jetbrains.annotations.NotNull;

public class RegexMapperCommand implements ICommand {
    @Override
    @NotNull
    public LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("regmap")
                .then(new ListCommand().getCommand())
                .then(new InfoCommand().getCommand());
    }
}
