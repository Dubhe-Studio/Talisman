package org.dubhe.talisman.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.dubhe.talisman.registry.EntityTypeRegistry;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("NullableProblems")
public class TalismanEntity extends Entity {
    private static final DataParameter<Byte> CRITICAL = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> COUNTER = EntityDataManager.createKey(TalismanEntity.class, DataSerializers.VARINT);
    private UUID owner;
    private boolean stop;
    private int ticks = 0;
    private ListNBT executes = new ListNBT();

    public TalismanEntity(EntityType<? extends TalismanEntity> type, World world) {
        super(type, world);
        this.owner = null;
        this.stop = false;
    }

    public TalismanEntity(World world, Vector3d pos, PlayerEntity owner, ListNBT execute, boolean throwable) {
        this(world, pos.x, pos.y, pos.z, owner, execute, throwable);
    }

    public TalismanEntity(World world, double x, double y, double z, PlayerEntity owner, ListNBT execute, boolean throwable) {
        super(EntityTypeRegistry.TALISMAN.get(), world);
        this.setLocationAndAngles(x, y, z, owner.rotationYaw, owner.rotationPitch);
        this.owner = owner.getUniqueID();
        this.executes.addAll(execute);
        this.stop = !throwable;
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
        this.dataManager.register(COUNTER, 0);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.dataManager.set(COUNTER, compound.getInt("counter"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("counter", this.dataManager.get(COUNTER));
    }

    @Override
    public void tick() {
        super.tick();
        if (stop) {
            this.execute();
            this.setDead();
        }

        double length = .05D;
        Vector3d playerPos = getPositionVec();
        double yaw = this.getYaw(1.0F);
        double pitch = this.getPitch(1.0F);
        double y = -Math.sin(pitch * Math.PI / 180D) * length;
        double x = -Math.sin(yaw * Math.PI / 180D);
        double z = Math.cos(yaw * Math.PI / 180D);
        double proportion = Math.sqrt((((length * length) - (y * y)) / ((x * x) + (z * z))));
        x *= proportion;
        z *= proportion;

        if (++ticks > 60) {
        }

        if (this.world.getBlockState(this.entityBlockPosition).getBlock() != Blocks.AIR) {

        }


        boolean flag = this.getNoClip();
        Vector3d vector3d = this.getMotion();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(horizontalMag(vector3d));
            this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, f) * (double)(180F / (float)Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

        BlockPos blockpos = this.getPosition();
        BlockState blockstate = this.world.getBlockState(blockpos);
        if (blockstate.getBlock() != Blocks.AIR && !flag) {
            VoxelShape voxelshape = blockstate.getCollisionShapeUncached(this.world, blockpos);
            if (!voxelshape.isEmpty()) {
                Vector3d vector3d1 = this.getPositionVec();

                for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
                    if (axisalignedbb.offset(blockpos).contains(vector3d1)) {
                        this.stop = true;
                        break;
                    }
                }
            }
        }

        if (this.isWet()) {
            this.extinguish();
        }


        // 未写完的实体逻辑
//        if (this.stop && !flag) {
//            if (this.inBlockState != blockstate && this.func_234593_u_()) {
//                this.func_234594_z_();
//            } else if (!this.world.isRemote) {
//                this.func_225516_i_();
//            }
//
//        } else {
//            vector3d = this.getMotion();
//            double d3 = vector3d.x;
//            double d4 = vector3d.y;
//            double d0 = vector3d.z;
//
//            double d5 = this.getPosX() + d3;
//            double d1 = this.getPosY() + d4;
//            double d2 = this.getPosZ() + d0;
//            float f1 = MathHelper.sqrt(horizontalMag(vector3d));
//            if (flag) {
//                this.rotationYaw = (float)(MathHelper.atan2(-d3, -d0) * (double)(180F / (float)Math.PI));
//            } else {
//                this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * (double)(180F / (float)Math.PI));
//            }
//
//            this.rotationPitch = (float)(MathHelper.atan2(d4, (double)f1) * (double)(180F / (float)Math.PI));
//            this.rotationPitch = addValue(this.prevRotationPitch, this.rotationPitch);
//            this.rotationYaw = addValue(this.prevRotationYaw, this.rotationYaw);
//            float f2 = 0.99F;
//            float f3 = 0.05F;
//            if (this.isInWater()) {
//                for(int j = 0; j < 4; ++j) {
//                    float f4 = 0.25F;
//                    this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
//                }
//
//                f2 = this.getWaterDrag();
//            }
//
//            this.setMotion(vector3d.scale(f2));
//            if (!this.hasNoGravity() && !flag) {
//                Vector3d vector3d4 = this.getMotion();
//                this.setMotion(vector3d4.x, vector3d4.y - (double)0.05F, vector3d4.z);
//            }
//
//            this.setPosition(d5, d1, d2);
//            this.doBlockCollisions();
//        }
//        if (!this.world.isRemote &&this.stop && this.timeInGround != 0 && !this.customPotionEffects.isEmpty() && this.timeInGround >= 600) {
//            this.world.setEntityState(this, (byte)0);
//        }


    }

    public boolean getNoClip() {
        if (!this.world.isRemote) {
            return this.noClip;
        } else {
            return (this.dataManager.get(CRITICAL) & 2) != 0;
        }
    }
    protected static float addValue(float prevRotation, float rotation) {
        while(rotation - prevRotation < -180.0F) {
            prevRotation -= 360.0F;
        }

        while(rotation - prevRotation >= 180.0F) {
            prevRotation += 360.0F;
        }

        return MathHelper.lerp(0.2F, prevRotation, rotation);
    }

    private void execute() {
        System.out.println("run!");
//        for (INBT execute : this.executes) {
//
//        }
    }


    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
