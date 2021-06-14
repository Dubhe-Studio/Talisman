package org.dubhe.talisman.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.items.CinnabarItem;
import org.dubhe.talisman.items.DivineStoneItem;
import org.dubhe.talisman.items.GuideDevilBottleItem;
import org.dubhe.talisman.items.InkItem;
import org.dubhe.talisman.items.PenItem;
import org.dubhe.talisman.items.TalismanItem;
import org.dubhe.talisman.items.TalismanPaperItem;

public final class ItemRegistry {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModInitializer.MODID);

    public static final RegistryObject<Item> TALISMAN = ITEMS.register("talisman", () -> new TalismanItem(initProperties()));
    public static final RegistryObject<Item> TALISMAN_PAPER = ITEMS.register("talisman_paper", () -> new TalismanPaperItem(initProperties()));
    public static final RegistryObject<Item> GUIDE_DEVIL_BOTTLE = ITEMS.register("guide_devil_bottle", () -> new GuideDevilBottleItem(initProperties()));
    public static final RegistryObject<Item> DIVINE_STONE = ITEMS.register("divine_stone", () -> new DivineStoneItem(initProperties()));
    public static final RegistryObject<Item> CINNABAR = ITEMS.register("cinnabar", () -> new CinnabarItem(initProperties()));
    public static final RegistryObject<Item> PEN = ITEMS.register("pen", () -> new PenItem(initProperties()));
    public static final RegistryObject<Item> INK = ITEMS.register("ink", () -> new InkItem(initProperties()));


    // block item
    public static RegistryObject<Item> DIVINE_STONE_ORE = ITEMS.register("divine_stone_ore", () -> new BlockItem(BlockRegistry.DIVINE_STONE_ORE.get(), new Item.Properties().group(ModInitializer.TalismanItemGroup)));
    public static RegistryObject<Item> CINNABAR_ORE = ITEMS.register("cinnabar_ore", () -> new BlockItem(BlockRegistry.CINNABAR_ORE.get(), new Item.Properties().group(ModInitializer.TalismanItemGroup)));
    public static RegistryObject<Item> TALISMAN_CRAFTING_TABLE = ITEMS.register("talisman_crafting_table", () -> new BlockItem(BlockRegistry.TALISMAN_CRAFTING_TABLE.get(), new Item.Properties().group(ModInitializer.TalismanItemGroup)));


    private static Item.Properties initProperties() {
        return new Item.Properties().group(ModInitializer.TalismanItemGroup);
    }

    public static void completeRegistry(IEventBus bus) {
        ITEMS.register(bus);
    }
}
