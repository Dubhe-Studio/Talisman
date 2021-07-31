package org.dubhe.talisman.registry.event;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.dubhe.talisman.entity.TalismanEntity;
import org.dubhe.talisman.talisman.Talismans;

import javax.annotation.Nullable;
import java.util.List;

public class TServerTickEvent {
    private static final List<ExecuteInstance> EXECUTES = Lists.newArrayList();

    public static void addExecute(World world, Entity entity, ListNBT executes, @Nullable Hand hand, @Nullable LivingEntity target) {
        EXECUTES.add(new ExecuteInstance(world, entity, executes, hand, target));
    }

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public void onServerTickEvent(TickEvent.ServerTickEvent event) {
        if (!EXECUTES.isEmpty()) {
            ExecuteInstance instance = EXECUTES.remove(0);
            Entity entity = instance.entity;
            if (!instance.executes.isEmpty()) {
                Vector3d position = new Vector3d(entity.getPosX(), entity.getPosY(), entity.getPosZ());
                MinecraftServer server = instance.world.getServer();
                try {
                    CommandSource source = server.getFunctionManager().getCommandSource().withPos(position).withRotation(entity, EntityAnchorArgument.Type.EYES).withEntity(entity);
                    for (INBT execute : instance.executes) {
                        String str = execute.getString();
                        if (str.startsWith("function:")) server.getCommandManager().handleCommand(source, String.format("function %s", str.split(":", 2)[1]));
                        else Talismans.get(str).execute(entity, position, instance.hand, instance.target);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (entity instanceof TalismanEntity) entity.remove();
        }
    }

    static class ExecuteInstance {
        private final World world;
        private final Entity entity;
        private final ListNBT executes;
        @Nullable
        private final Hand hand;
        @Nullable
        private final LivingEntity target;

        public ExecuteInstance(World world, Entity entity, ListNBT executes, @Nullable Hand hand, @Nullable LivingEntity target) {
            this.world = world;
            this.entity = entity;
            this.executes = executes;
            this.hand = hand;
            this.target = target;
        }
    }

}
