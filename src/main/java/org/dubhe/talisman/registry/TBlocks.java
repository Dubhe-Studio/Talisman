package org.dubhe.talisman.registry;

import net.minecraft.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.block.CinnabarOreBlock;
import org.dubhe.talisman.block.DivineStoneOreBlock;
import org.dubhe.talisman.block.TalismanCraftingTableBlock;

public class TBlocks {
    private static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, ModInitializer.MODID);

    public static RegistryObject<Block> DIVINE_STONE_ORE = BLOCK.register("divine_stone_ore", DivineStoneOreBlock::new);
    public static RegistryObject<Block> CINNABAR_ORE = BLOCK.register("cinnabar_ore", CinnabarOreBlock::new);
    public static RegistryObject<Block> TALISMAN_CRAFTING_TABLE = BLOCK.register("talisman_crafting_table", TalismanCraftingTableBlock::new);

    public static void register(IEventBus event) {
        BLOCK.register(event);
    }
}
