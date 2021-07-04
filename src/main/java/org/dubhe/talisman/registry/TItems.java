package org.dubhe.talisman.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.item.CinnabarItem;
import org.dubhe.talisman.item.DivineStoneItem;
import org.dubhe.talisman.item.GuideDevilBottleItem;
import org.dubhe.talisman.item.InkItem;
import org.dubhe.talisman.item.PenItem;
import org.dubhe.talisman.item.TalismanItem;
import org.dubhe.talisman.item.TalismanPaperItem;

public final class TItems {
    private static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, ModInitializer.MODID);

    public static final RegistryObject<Item> TALISMAN = ITEM.register("talisman", () -> new TalismanItem(itemProperties().maxStackSize(16)));
    public static final RegistryObject<Item> TALISMAN_PAPER = ITEM.register("talisman_paper", () -> new TalismanPaperItem(itemProperties()));
    public static final RegistryObject<Item> GUIDE_DEVIL_BOTTLE = ITEM.register("guide_devil_bottle", () -> new GuideDevilBottleItem(itemProperties().maxStackSize(16)));
    public static final RegistryObject<Item> DIVINE_STONE = ITEM.register("divine_stone", () -> new DivineStoneItem(itemProperties()));
    public static final RegistryObject<Item> CINNABAR = ITEM.register("cinnabar", () -> new CinnabarItem(itemProperties()));
    public static final RegistryObject<Item> PEN = ITEM.register("pen", () -> new PenItem(itemProperties()));
    public static final RegistryObject<Item> INK = ITEM.register("ink", () -> new InkItem(itemProperties()));


    // block item
    public static RegistryObject<Item> DIVINE_STONE_ORE = ITEM.register("divine_stone_ore", () -> new BlockItem(TBlocks.DIVINE_STONE_ORE.get(), itemProperties()));
    public static RegistryObject<Item> CINNABAR_ORE = ITEM.register("cinnabar_ore", () -> new BlockItem(TBlocks.CINNABAR_ORE.get(), itemProperties()));
    public static RegistryObject<Item> TALISMAN_CRAFTING_TABLE = ITEM.register("talisman_crafting_table", () -> new BlockItem(TBlocks.TALISMAN_CRAFTING_TABLE.get(), itemProperties().maxStackSize(1)));


    private static Item.Properties itemProperties() {
        return new Item.Properties().group(ModInitializer.TalismanItemGroup);
    }

    public static void register(IEventBus bus) {
        ITEM.register(bus);
    }
}
