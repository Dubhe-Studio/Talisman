package org.dubhe.talisman.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.block.*;

public class TBlocks {
    private static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, ModInitializer.MODID);

    public static RegistryObject<Block> DIVINE_STONE_ORE = BLOCK.register("divine_stone_ore", TOreBlock::new);
    public static RegistryObject<Block> CINNABAR_ORE = BLOCK.register("cinnabar_ore", TOreBlock::new);
    public static RegistryObject<Block> DIVINE_STONE_BLOCK = BLOCK.register("divine_stone_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.DIAMOND).setRequiresTool().hardnessAndResistance(3.0F, 6.0F).sound(SoundType.METAL)));
    public static RegistryObject<Block> TALISMAN_CRAFTING_TABLE = BLOCK.register("talisman_crafting_table", TalismanCraftingTableBlock::new);

    public static void register(IEventBus event) {
        BLOCK.register(event);
    }
}
