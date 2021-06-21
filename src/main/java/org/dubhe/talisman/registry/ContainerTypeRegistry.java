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
import org.dubhe.talisman.block.tileentity.TalismanCraftingTableLeftTileEntity;
import org.dubhe.talisman.container.TalismanCraftingTableContainer;

@SuppressWarnings("ConstantConditions")
public class ContainerTypeRegistry {
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ModInitializer.MODID);
    public static RegistryObject<ContainerType<TalismanCraftingTableContainer>> TALISMAN_CRAFTING_TABLE = CONTAINERS.register("talisman_crafting_table", () -> IForgeContainerType.create((int window, PlayerInventory inv, PacketBuffer data) -> new TalismanCraftingTableContainer(window, inv, (TalismanCraftingTableLeftTileEntity) Minecraft.getInstance().world.getTileEntity(data.readBlockPos()))));

    public static void completeRegistry(IEventBus bus) {
        CONTAINERS.register(bus);
    }

}
