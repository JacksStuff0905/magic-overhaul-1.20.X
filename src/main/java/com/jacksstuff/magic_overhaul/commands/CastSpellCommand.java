package com.jacksstuff.magic_overhaul.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class CastSpellCommand {
    public CastSpellCommand(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(Commands.literal("cast")
                .then(Commands.argument("rune1", ItemArgument.item(context)))
                .executes(commandContext -> execute(commandContext, ItemArgument.getItem(commandContext, "rune1").getItem())));
    }

    private int execute(CommandContext<CommandSourceStack> context, Item item) {
        ServerPlayer player = context.getSource().getPlayer();
        player.sendSystemMessage(Component.literal("test"));
        return 1;
    }
}