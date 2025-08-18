package io.github.axolotsh.regexMapper.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RegexMapperCommand implements ICommand {
    @Override
    public String getPermission() {
        return "regexmapper.command.regmap";
    }

    @Override
    @NotNull
    public LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("regmap").requires(x -> x.getSender().hasPermission(getPermission()))
                .then(new ListCommand().getCommand())
                .then(new InfoCommand().getCommand())
                .then(new GiveCommand().getCommand());
    }
}
