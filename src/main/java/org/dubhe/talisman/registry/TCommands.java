package org.dubhe.talisman.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TCommands {

    @SubscribeEvent
    public void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("math")
                .then(Commands.literal("cos").then(Commands.argument("value", DoubleArgumentType.doubleArg()).executes(context -> {
                    // value就是输入的第三个参数
                    double value = DoubleArgumentType.getDouble(context, "value");
                    return Command.SINGLE_SUCCESS;
                }))).then(Commands.literal("sin").then(Commands.argument("value", DoubleArgumentType.doubleArg()).executes(context -> {
                    // value就是输入的第三个参数
                    double value = DoubleArgumentType.getDouble(context, "value");
                    return Command.SINGLE_SUCCESS;
                }))).then(Commands.literal("tan").then(Commands.argument("value", DoubleArgumentType.doubleArg()).executes(context -> {
                    // value就是输入的第三个参数
                    double value = DoubleArgumentType.getDouble(context, "value");
                    return Command.SINGLE_SUCCESS;
                })))
        );
    }
}
