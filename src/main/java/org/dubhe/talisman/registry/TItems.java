package org.dubhe.talisman.registry;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.item.*;

@SuppressWarnings("unused")
public final class TItems {
    private static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, ModInitializer.MODID);

    public static final RegistryObject<Item> TALISMAN = ITEM.register("talisman", () -> new TalismanItem(properties().maxStackSize(16)));
    public static final RegistryObject<Item> TALISMAN_PAPER = ITEM.register("talisman_paper", () -> new TalismanPaperItem(properties()));
    public static final RegistryObject<Item> GUIDE_DEVIL_BOTTLE = ITEM.register("guide_devil_bottle", () -> new GuideDevilBottleItem(properties().maxStackSize(16)));
    public static final RegistryObject<Item> DIVINE_STONE = ITEM.register("divine_stone", () -> new DivineStoneItem(properties()));
    public static final RegistryObject<Item> CINNABAR = ITEM.register("cinnabar", () -> new CinnabarItem(properties()));
    public static final RegistryObject<Item> PEN_PRIMARY = ITEM.register("pen_primary", () -> new PenItem(properties().maxDamage(10)));
    public static final RegistryObject<Item> INK_PRIMARY = ITEM.register("ink_primary", () -> new InkItem(properties().maxDamage(10)));
    public static final RegistryObject<Item> RAOIST_ROBE_HELMET = ITEM.register("raoist_robe_helmet", () -> new ArmorItem(TArmorMaterial.RAOIST_ROBE, EquipmentSlotType.HEAD, properties()));
    public static final RegistryObject<Item> RAOIST_ROBE_CHESTPLATE = ITEM.register("raoist_robe_chestplate", () -> new ArmorItem(TArmorMaterial.RAOIST_ROBE, EquipmentSlotType.CHEST, properties()));
    public static final RegistryObject<Item> RAOIST_ROBE_LEGGINGS = ITEM.register("raoist_robe_leggings", () -> new ArmorItem(TArmorMaterial.RAOIST_ROBE, EquipmentSlotType.LEGS, properties()));
    public static final RegistryObject<Item> RAOIST_ROBE_BOOTS = ITEM.register("raoist_robe_boots", () -> new ArmorItem(TArmorMaterial.RAOIST_ROBE, EquipmentSlotType.FEET, properties()));
    //    public static final RegistryObject<Item> MUSIC_DISC_ = ITEM.register("music_disc_", () -> new MusicDiscItem(0, TSoundEvents.MUSIC_DISC_,itemProperties().maxStackSize(1).rarity(Rarity.RARE)));

    // block item
    public static RegistryObject<Item> DIVINE_STONE_ORE = ITEM.register("divine_stone_ore", () -> new BlockItem(TBlocks.DIVINE_STONE_ORE.get(), properties()));
    public static RegistryObject<Item> CINNABAR_ORE = ITEM.register("cinnabar_ore", () -> new BlockItem(TBlocks.CINNABAR_ORE.get(), properties()));
    public static RegistryObject<Item> TALISMAN_CRAFTING_TABLE = ITEM.register("talisman_crafting_table", () -> new BlockItem(TBlocks.TALISMAN_CRAFTING_TABLE.get(), properties().maxStackSize(1)));

    private static Item.Properties properties() {
        return new Item.Properties().group(ModInitializer.TalismanItemGroup);
    }

    public static void register(IEventBus bus) {
        ITEM.register(bus);
    }
}
