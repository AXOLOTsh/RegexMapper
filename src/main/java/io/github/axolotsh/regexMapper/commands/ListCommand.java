package io.github.axolotsh.regexMapper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.axolotsh.regexMapper.RegexMapper;
import io.github.axolotsh.regexMapper.utils.RegexCaseUtils;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ListCommand implements ICommand {
    @Override
    public String getPermission() {
        return "regexmapper.command.regmap.list";
    }

    @Override
    public @NotNull LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("list")
                .requires(x -> x.getSender().hasPermission(getPermission()))
                .executes(this::execution);
    }

    private int execution(CommandContext<CommandSourceStack> ctx) {
        var sender = ctx.getSource().getSender();

        var cases = RegexMapper.getInstance().getCases();
        for (var item : cases) {
            var util = new RegexCaseUtils(item);
            sender.sendMessage(Component.text(item.getName())
                    .clickEvent(ClickEvent.runCommand(String.format("regmap info %s", item.getName())))
                    .hoverEvent(HoverEvent.showText(util.getOpComponent()))
                    .color(NamedTextColor.AQUA)
                    .decorate(TextDecoration.UNDERLINED));
        }

        return Command.SINGLE_SUCCESS;
    }
}
