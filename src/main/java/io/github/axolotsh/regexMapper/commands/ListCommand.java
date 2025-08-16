package io.github.axolotsh.regexMapper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.axolotsh.regexMapper.RegexMapper;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ListCommand implements ICommand {
    @Override
    public @NotNull LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("list")
                .executes(this::execution);
    }

    private int execution(CommandContext<CommandSourceStack> ctx) {
        var sender = ctx.getSource().getSender();

        var cases = RegexMapper.getInstance().getCases();
        for (var item : cases) {
            sender.sendMessage(Component.text(item.getName())
                    .clickEvent(ClickEvent.runCommand(String.format("regmap info %s", item.getName())))
                    .hoverEvent(HoverEvent.showText(item.getComponent()))
                    .color(NamedTextColor.AQUA)
                    .decorate(TextDecoration.UNDERLINED));
        }

        return Command.SINGLE_SUCCESS;
    }
}
