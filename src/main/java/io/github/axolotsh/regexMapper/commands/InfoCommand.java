package io.github.axolotsh.regexMapper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.axolotsh.regexMapper.Plugin;
import io.github.axolotsh.regexMapper.RegexMapper;
import io.github.axolotsh.regexMapper.entities.RegexCase;
import io.github.axolotsh.regexMapper.utils.RegexCaseUtils;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InfoCommand implements ICommand {
    @Override
    public String getPermission() {
        return "regexmapper.command.regmap.info";
    }

    @Override
    @NotNull
    public LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("info")
                .requires(x -> x.getSender().hasPermission(getPermission()))
                .then(Commands.argument("item", StringArgumentType.greedyString())
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
        var util = new RegexCaseUtils(item);

        Plugin.LOGGER.info(sender.isOp() ? "true" : "false");
        Plugin.LOGGER.info(sender.hasPermission(new GiveCommand().getPermission()) ? "true" : "false");
        if (sender.isOp() || sender.hasPermission(new GiveCommand().getPermission()))
            sender.sendMessage(util.getOpComponent());
        else
            sender.sendMessage(util.getComponent());
        return Command.SINGLE_SUCCESS;
    }
}
