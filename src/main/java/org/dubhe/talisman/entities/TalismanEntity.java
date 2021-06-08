package org.dubhe.talisman.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.dubhe.talisman.registry.EntityTypeRegistry;

import java.util.UUID;

@SuppressWarnings("NullableProblems")
public class TalismanEntity extends Entity {
    private static final DataParameter<Integer> MANA = EntityDataManager.createKey(TalismanEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> STOP = EntityDataManager.createKey(TalismanEntity.class, DataSerializers.BOOLEAN);
    private UUID owner;
    private BlockPos executePos;
    private ListNBT executes = new ListNBT();

    public TalismanEntity(EntityType<? extends TalismanEntity> type, World world) {
        super(type, world);
        this.owner = null;
    }

    public TalismanEntity(World world, Vector3d pos, PlayerEntity owner, ListNBT execute, boolean throwable) {
        this(world, pos.x, pos.y, pos.z, owner, execute, throwable);
    }

    public TalismanEntity(World world, double x, double y, double z, PlayerEntity owner, ListNBT execute, boolean throwable) {
        super(EntityTypeRegistry.TALISMAN.get(), world);
        this.setLocationAndAngles(x, y, z, owner.rotationYaw, owner.rotationPitch);
        this.owner = owner.getUniqueID();
        this.executes.addAll(execute);
        this.dataManager.set(STOP, !throwable);
    }

    @Override
    public CompoundNBT writeWithoutTypeId(CompoundNBT compound) {
        compound.put("executes", this.executes);
        if (this.owner != null) compound.putUniqueId("owner", this.owner);
        return super.writeWithoutTypeId(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("execute")) this.owner = compound.getUniqueId("owner");
        this.executes = compound.getList("executes", 8);
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
            this.execute();
            this.setDead();
            return;
        }

        double speed = 0.175D;   // 位移速度 (speed * 20) blocks/s
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

        outer: for (double detectX = -0.3; detectX != 0.3; detectX +=0.3) {
            for (double detectY = -0.3; detectY != 0.3; detectY +=0.3) {
                for (double detectZ = -0.3; detectZ != 0.3; detectZ +=0.3) {
                    BlockPos blockPos = new BlockPos(pos.add(detectX, detectY, detectZ));
                    BlockState state = this.world.getBlockState(blockPos);
                    if (state.getBlock() != Blocks.AIR) {
                        this.dataManager.set(STOP, true);
                        this.executePos = blockPos;
                        break outer;
                    }
                }
            }
        }

        this.decreaseMana(1);

    }

    public void decreaseMana(int value) {
        if (this.dataManager.get(MANA) > 0) this.dataManager.set(MANA, this.dataManager.get(MANA) - value);
        else this.dataManager.set(STOP, true);
    }

    private void execute() {
        if (!this.world.isRemote) {
            System.out.println("Run at " + this.world.getBlockState(executePos) + this.getPositionVec());
        }
//        for (INBT execute : this.executes) {
//
//        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
