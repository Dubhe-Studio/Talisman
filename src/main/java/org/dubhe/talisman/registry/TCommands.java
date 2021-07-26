package org.dubhe.talisman.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TCommands {

    private static final Map<String, Function<CommandContext<CommandSource>, Double>> TYPES = new HashMap<>();

    @SubscribeEvent
    public void register(RegisterCommandsEvent event) {
        LiteralArgumentBuilder<CommandSource> literal = Commands.literal("math");
        for (Map.Entry<String, Function<CommandContext<CommandSource>, Double>> command : TYPES.entrySet()) {
            literal.then(Commands.literal(command.getKey()).then(Commands.argument("value", DoubleArgumentType.doubleArg()).then(Commands.argument("scale", DoubleArgumentType.doubleArg()).executes(context -> command.getValue().apply(context).intValue()))));
        }

        event.getDispatcher().register(literal);
    }

    static {
        TYPES.put("sin", context -> Math.sin(DoubleArgumentType.getDouble(context, "value")) * DoubleArgumentType.getDouble(context, "scale"));
        TYPES.put("cos", context -> Math.cos(DoubleArgumentType.getDouble(context, "value")) * DoubleArgumentType.getDouble(context, "scale"));
        TYPES.put("tan", context -> Math.tan(DoubleArgumentType.getDouble(context, "value")) * DoubleArgumentType.getDouble(context, "scale"));


    }

}
