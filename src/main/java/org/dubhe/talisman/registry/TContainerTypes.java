package org.dubhe.talisman.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.block.tileentity.TCTLeftTileEntity;
import org.dubhe.talisman.container.ChangeClothingContainer;
import org.dubhe.talisman.container.TCTContainer;

@SuppressWarnings("ConstantConditions")
public class TContainerTypes {
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ModInitializer.MODID);

    public static RegistryObject<ContainerType<TCTContainer>> TALISMAN_CRAFTING_TABLE = CONTAINERS.register("talisman_crafting_table", () -> IForgeContainerType.create((int window, PlayerInventory inv, PacketBuffer data) -> new TCTContainer(window, inv, (TCTLeftTileEntity) Minecraft.getInstance().world.getTileEntity(data.readBlockPos()))));
    public static RegistryObject<ContainerType<ChangeClothingContainer>> CHANGE_CLOTHING = CONTAINERS.register("change_clothing", () -> IForgeContainerType.create((int window, PlayerInventory inv, PacketBuffer buffer) -> new ChangeClothingContainer(window, inv, buffer.readBoolean())));

    public static void register(IEventBus bus) {
        CONTAINERS.register(bus);
    }

}
