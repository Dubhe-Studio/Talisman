package org.dubhe.talisman.registry.event;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.dubhe.talisman.talisman.Talismans;

import javax.annotation.Nullable;
import java.util.List;

public class TServerTickEvent {
    private static final List<ExecuteInstance> EXECUTES = Lists.newArrayList();

    public static void addExecute(World world, Entity entity, ListNBT executes, @Nullable LivingEntity target) {
        EXECUTES.add(new ExecuteInstance(world, entity, executes, target));

    }

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public void onClientSetUpEvent(TickEvent.ServerTickEvent event) {
        if (!EXECUTES.isEmpty()) {
            ExecuteInstance instance = EXECUTES.remove(0);
            if (!instance.getExecutes().isEmpty()) {
                Entity entity = instance.getEntity();
                Vector3d position = new Vector3d(entity.getPosX(), entity.getPosY(), entity.getPosZ());
                MinecraftServer server = instance.getWorld().getServer();
                try {
                    CommandSource source = server.getFunctionManager().getCommandSource().withPos(position).withRotation(entity, EntityAnchorArgument.Type.EYES).withEntity(entity);
                    for (INBT execute : instance.getExecutes()) {
                        String str = execute.getString();
                        if (str.startsWith("function:")) server.getCommandManager().handleCommand(source, String.format("function %s", str.split(":", 2)[1]));
                        else Talismans.get(str).execute(entity, position, instance.getTarget());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static class ExecuteInstance {
        private final World world;
        private final Entity entity;
        private final ListNBT executes;
        @Nullable
        private final LivingEntity target;

        public ExecuteInstance(World world, Entity entity, ListNBT executes, @Nullable LivingEntity target) {
            this.world = world;
            this.entity = entity;
            this.executes = executes;
            this.target = target;
        }

        public World getWorld() {
            return world;
        }

        public Entity getEntity() {
            return entity;
        }

        public ListNBT getExecutes() {
            return executes;
        }

        @Nullable
        public LivingEntity getTarget() {
            return target;
        }
    }

}
