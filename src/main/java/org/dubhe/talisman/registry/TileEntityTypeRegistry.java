package org.dubhe.talisman.registry;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.block.tileentity.TalismanCraftingTableLeftTileEntity;

@SuppressWarnings("ConstantConditions")
public class TileEntityTypeRegistry {
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModInitializer.MODID);
    public static RegistryObject<TileEntityType<TalismanCraftingTableLeftTileEntity>> TALISMAN_CRAFTING_TABLE = TILE_ENTITY_TYPE.register("talisman_crafting_table", () -> TileEntityType.Builder.create(TalismanCraftingTableLeftTileEntity::new, BlockRegistry.TALISMAN_CRAFTING_TABLE.get()).build(null));


    public static void completeRegistry(IEventBus bus) {
        TILE_ENTITY_TYPE.register(bus);
    }

}
