package org.dubhe.talisman.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.dubhe.talisman.registry.EntityTypeRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@SuppressWarnings("NullableProblems")
public class TalismanEntity extends Entity {
    private static final DataParameter<Integer> COUNTER = EntityDataManager.createKey(TalismanEntity.class, DataSerializers.VARINT);
    private UUID owner;
    private Collection<String> execute = Collections.emptyList();

    public TalismanEntity(EntityType<? extends TalismanEntity> type, World world) {
        super(type, world);
    }

    public TalismanEntity(World world, Vector3d pos, PlayerEntity owner, Collection<String> execute) {
        this(world, pos.x, pos.y, pos.z, owner, execute);
    }

    public TalismanEntity(World world, double x, double y, double z, PlayerEntity owner, Collection<String> execute) {
        super(EntityTypeRegistry.TALISMAN.get(), world);
        this.setPosition(x, y, z);
        this.owner = owner.getUniqueID();
        this.execute.addAll(execute);
    }

    @Override
    public CompoundNBT writeWithoutTypeId(CompoundNBT compound) {
        compound.put("executes", this.toListNBT());
        if (this.owner != null) compound.putUniqueId("owner", this.owner);
        return super.writeWithoutTypeId(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("execute")) this.owner = compound.getUniqueId("owner");
        this.toCollection(compound.getList("executes", 8));
        super.read(compound);
    }

    private ListNBT toListNBT() {
        ListNBT list = new ListNBT();
        for (String s : this.execute) {
            list.add(StringNBT.valueOf(s));
        }
        return list;
    }

    private void toCollection(ListNBT list) {
        Collection<String> collection = new ArrayList<>(Collections.emptyList());
        for (int i = 0; i < list.size(); i++) {
            collection.add(list.getString(i));
        }
        this.execute = collection;
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
//        if (world.isRemote) {
//            logger.info(this.dataManager.get(COUNTER));
//        }
//        if (!world.isRemote) {
//            logger.info(this.dataManager.get(COUNTER));
//            this.dataManager.set(COUNTER, this.dataManager.get(COUNTER) + 1);
//        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
