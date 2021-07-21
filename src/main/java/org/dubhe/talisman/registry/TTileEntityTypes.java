package org.dubhe.talisman.registry;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.block.tileentity.TCTLeftTileEntity;
import org.dubhe.talisman.block.tileentity.TCTRightTileEntity;

@SuppressWarnings("ConstantConditions")
public class TTileEntityTypes {
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModInitializer.MODID);

    public static RegistryObject<TileEntityType<TCTLeftTileEntity>> TALISMAN_CRAFTING_TABLE = TILE_ENTITY_TYPE.register("talisman_crafting_table", () -> TileEntityType.Builder.create(TCTLeftTileEntity::new, TBlocks.TALISMAN_CRAFTING_TABLE.get()).build(null));
    public static RegistryObject<TileEntityType<TCTRightTileEntity>> TALISMAN_CRAFTING_TABLE_RIGHT = TILE_ENTITY_TYPE.register("talisman_crafting_table_right", () -> TileEntityType.Builder.create(TCTRightTileEntity::new, TBlocks.TALISMAN_CRAFTING_TABLE_RIGHT.get()).build(null));


    public static void register(IEventBus bus) {
        TILE_ENTITY_TYPE.register(bus);
    }

}
