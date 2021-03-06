package org.dubhe.talisman.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import org.dubhe.talisman.registry.TEntityTypes;
import org.dubhe.talisman.registry.event.TServerTickEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("NullableProblems")
public class TalismanEntity extends Entity {
    private static final DataParameter<Integer> MANA = EntityDataManager.createKey(TalismanEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> STOP = EntityDataManager.createKey(TalismanEntity.class, DataSerializers.BOOLEAN);
    private UUID owner;
    private LivingEntity entity = null;
    private ListNBT executes = new ListNBT();

    public TalismanEntity(EntityType<? extends TalismanEntity> type, World world) {
        super(type, world);
        this.owner = null;
    }

    public TalismanEntity(World world, Vector3d pos, PlayerEntity owner, ListNBT execute) {
        this(world, pos.x, pos.y, pos.z, owner, execute);
    }

    public TalismanEntity(World world, double x, double y, double z, PlayerEntity owner, ListNBT execute) {
        super(TEntityTypes.TALISMAN.get(), world);
        this.setLocationAndAngles(x, y, z, owner.rotationYaw, owner.rotationPitch);
        this.owner = owner.getUniqueID();
        this.executes.addAll(execute);
    }

    @Nullable
    public LivingEntity getOwner() {
        if (!this.world.isRemote) {
            Entity entity = ((ServerWorld)this.world).getEntityByUuid(this.owner);
            if (entity instanceof LivingEntity) return (LivingEntity) entity;
        }
        return null;
    }

    @Override
    public CompoundNBT writeWithoutTypeId(CompoundNBT compound) {
        compound.put("executes", this.executes);
        if (this.owner != null) compound.putUniqueId("owner", this.owner);
        return super.writeWithoutTypeId(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("owner")) this.owner = compound.getUniqueId("owner");
        if (compound.contains("executes")) this.executes = compound.getList("executes", 8);
        super.read(compound);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(MANA, 200);
        this.dataManager.register(STOP, false);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.dataManager.set(MANA, compound.getInt("mana"));
        this.dataManager.set(STOP, compound.getBoolean("stop"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("mana", this.dataManager.get(MANA));
        compound.putBoolean("stop", this.dataManager.get(STOP));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.dataManager.get(STOP)) {
            if (this.dataManager.get(MANA) == 0) TServerTickEvent.addExecute(this.world, this, this.executes, null, this.entity);
            this.decreaseMana(1);
            return;
        }

        double speed = 0.525D;   // speed (speed * 20) blocks/s
        double yaw = this.getYaw(1.0F);
        double pitch = this.getPitch(1.0F);
        double y = -Math.sin(pitch * Math.PI / 180D) * speed;
        double x = -Math.sin(yaw * Math.PI / 180D);
        double z = Math.cos(yaw * Math.PI / 180D);
        double proportion = Math.sqrt((((speed * speed) - (y * y)) / ((x * x) + (z * z))));
        x *= proportion;
        z *= proportion;

        Vector3d pos = this.getPositionVec();
        this.setPosition(pos.x + x, pos.y + y, pos.z + z);
        pos = pos.add(0, 0.3, 0);
        if (this.owner != null) {
            AxisAlignedBB box = new AxisAlignedBB(pos.x - 0.3, pos.y - 0.3, pos.z - 0.3, pos.x + 0.3, pos.y + 0.3, pos.z + 0.3);
            List<LivingEntity> entities = this.world.getEntitiesWithinAABB(LivingEntity.class, box, (entity) -> entity != null && entity.isAlive() && !entity.equals(this.world.getPlayerByUuid(this.owner)));
            if (!entities.isEmpty()) {
                this.dataManager.set(STOP, true);
                this.dataManager.set(MANA, 0);
                this.entity = entities.get(0);
                return;
            }
        }

        for (double detectX = -0.3; detectX != 0.3; detectX +=0.3) {
            for (double detectY = -0.3; detectY != 0.3; detectY +=0.3) {
                for (double detectZ = -0.3; detectZ != 0.3; detectZ +=0.3) {
                    BlockPos blockPos = new BlockPos(pos.add(detectX, detectY, detectZ));
                    BlockState state = this.world.getBlockState(blockPos);
                    if (state.getBlock() != Blocks.AIR) {
                        this.dataManager.set(STOP, true);
                        this.dataManager.set(MANA, 0);
                        return;
                    }
                }
            }
        }

        this.decreaseMana(1);

    }

    public void decreaseMana(int value) {
        this.dataManager.set(MANA, this.dataManager.get(MANA) - value);
        if (this.dataManager.get(MANA) <= 0) this.dataManager.set(STOP, true);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
