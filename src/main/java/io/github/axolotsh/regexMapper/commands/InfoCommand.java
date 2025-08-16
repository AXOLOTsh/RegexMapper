package io.github.axolotsh.regexMapper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.axolotsh.regexMapper.RegexMapper;
import io.github.axolotsh.regexMapper.entities.RegexCase;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InfoCommand implements ICommand {
    @Override
    @NotNull
    public LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("info")
                .then(Commands.argument("item", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            RegexMapper.getInstance().getCases().stream()
                                    .map(RegexCase::getName)
                                    .forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .executes(this::execution));
    }

    private int execution(CommandContext<CommandSourceStack> ctx) {
        var itemName = ctx.getArgument("item", String.class);
        var sender = ctx.getSource().getSender();

        var qitem = RegexMapper.getInstance().getCases().stream()
                .filter(x -> Objects.equals(x.getName(), itemName))
                .findFirst();
        if (qitem.isEmpty()) {
            sender.sendMessage(Component.text("Can't find this item!").color(NamedTextColor.RED));
            return Command.SINGLE_SUCCESS;
        }

        var item = qitem.get();

        sender.sendMessage(item.getComponent());
        return Command.SINGLE_SUCCESS;
    }
}
