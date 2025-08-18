package io.github.axolotsh.regexMapper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.axolotsh.regexMapper.RegexMapper;
import io.github.axolotsh.regexMapper.entities.RegexCase;
import io.github.axolotsh.regexMapper.utils.RegexCaseUtils;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class GiveCommand implements ICommand {
    @Override
    @NotNull
    public LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("give")
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
        var executor = ctx.getSource().getExecutor();


        if (executor instanceof Player player) {
            var qitem = RegexMapper.getInstance().getCases().stream()
                    .filter(x -> Objects.equals(x.getName(), itemName))
                    .findFirst();
            if (qitem.isEmpty()) {
                sender.sendMessage(Component.text("Can't find this item!").color(NamedTextColor.RED));
                return Command.SINGLE_SUCCESS;
            }

            var item = qitem.get();
            var util = new RegexCaseUtils(item);

            ItemStack itemStack;
            var material = item.getItem();
            if (material == null)
                itemStack = new ItemStack(Material.PAPER);
            else
                itemStack = new ItemStack(material);

            var meta = itemStack.getItemMeta();
            meta = util.modifyMeta(meta);
            meta.displayName(MiniMessage.miniMessage().deserialize(item.getName()));
            itemStack.setItemMeta(meta);
            player.give(itemStack);

            return Command.SINGLE_SUCCESS;
        }

        sender.sendMessage(Component.text("This command can be executed only by player!").color(NamedTextColor.RED));
        return Command.SINGLE_SUCCESS;
    }
}
