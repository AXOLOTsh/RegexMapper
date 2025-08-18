package io.github.axolotsh.regexMapper.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

public interface ICommand {
    public String getPermission();

    @NotNull
    public LiteralArgumentBuilder<CommandSourceStack> getCommand();
}
