package org.dubhe.talisman.registry;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.item.*;
import org.dubhe.talisman.talisman.Talismans;

@SuppressWarnings("unused")
public final class TItems {
    private static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, ModInitializer.MODID);

    public static final RegistryObject<Item> TALISMAN = ITEM.register("talisman", () -> new TalismanItem(properties().maxStackSize(16)));
    public static final RegistryObject<Item> EXPLODE_TALISMAN = ITEM.register("explode_talisman", () -> new TalismanItem(true, Talismans.EXPLODE, properties().maxStackSize(16)));
    public static final RegistryObject<Item> TRANSFER_TALISMAN = ITEM.register("transfer_talisman", () -> new TalismanItem(true, Talismans.TRANSFER, properties().maxStackSize(16)));
    public static final RegistryObject<Item> FIREBALL_TALISMAN = ITEM.register("fireball_talisman", () -> new TalismanItem(true, Talismans.FIREBALL, properties().maxStackSize(16)));
//    public static final RegistryObject<Item> IMMOBILITY_TALISMAN = ITEM.register("immobility_talisman", () -> new TalismanItem(true, Talismans.IMMOBILITY, properties().maxStackSize(16)));
    public static final RegistryObject<Item> TREATMENT_TALISMAN = ITEM.register("treatment_talisman", () -> new TalismanItem(true, Talismans.TREATMENT, properties().maxStackSize(16)));
//    public static final RegistryObject<Item> PUPPET_TALISMAN = ITEM.register("puppet_talisman", () -> new TalismanItem(true, Talismans.PUPPET, properties().maxStackSize(16)));
//    public static final RegistryObject<Item> SEPARATION_TALISMAN = ITEM.register("separation_talisman", () -> new TalismanItem(true, Talismans.SEPARATION, properties().maxStackSize(16)));
    public static final RegistryObject<Item> THUNDER_TALISMAN = ITEM.register("thunder_talisman", () -> new TalismanItem(true, Talismans.THUNDER, properties().maxStackSize(16)));
//    public static final RegistryObject<Item> MUTE_TALISMAN = ITEM.register("mute_talisman", () -> new TalismanItem(true, Talismans.MUTE, properties().maxStackSize(16)));
    public static final RegistryObject<Item> CARRY_TALISMAN = ITEM.register("carry_talisman", () -> new TalismanItem(true, Talismans.CARRY, properties().maxStackSize(16)));
//    public static final RegistryObject<Item> WATER_BALL_TALISMAN = ITEM.register("water_ball_talisman", () -> new TalismanItem(true, Talismans.WATER_BALL, properties().maxStackSize(16)));
    public static final RegistryObject<Item> DOOM_TALISMAN = ITEM.register("doom_talisman", () -> new TalismanItem(true, Talismans.DOOM, properties().maxStackSize(16)));
    public static final RegistryObject<Item> HUGE_EXPLOSION_TALISMAN = ITEM.register("huge_explosion_talisman", () -> new TalismanItem(true, Talismans.HUGE_EXPLOSION, properties().maxStackSize(16)));
    public static final RegistryObject<Item> CHANGE_CLOTHING_TALISMAN = ITEM.register("change_clothing_talisman", () -> new ChangeClothingTalismanItem(Talismans.CHANGE_CLOTHING, properties().maxStackSize(16).maxDamage(5)));

    public static final RegistryObject<Item> TALISMAN_PAPER = ITEM.register("talisman_paper", () -> new Item(properties()));
    public static final RegistryObject<Item> GUIDE_DEVIL_BOTTLE = ITEM.register("guide_devil_bottle", () -> new GuideDevilBottleItem(properties().maxStackSize(16)));
    public static final RegistryObject<Item> DIVINE_STONE = ITEM.register("divine_stone", () -> new Item(properties()));
    public static final RegistryObject<Item> CINNABAR = ITEM.register("cinnabar", () -> new Item(properties()));
    public static final RegistryObject<Item> PEN_PRIMARY = ITEM.register("pen_primary", () -> new Item(properties().maxDamage(10)));
    public static final RegistryObject<Item> INK_PRIMARY = ITEM.register("ink_primary", () -> new Item(properties().maxDamage(10)));
    public static final RegistryObject<Item> RAOIST_ROBE_HELMET = ITEM.register("raoist_robe_helmet", () -> new ArmorItem(TArmorMaterial.RAOIST_ROBE, EquipmentSlotType.HEAD, properties()));
    public static final RegistryObject<Item> RAOIST_ROBE_CHESTPLATE = ITEM.register("raoist_robe_chestplate", () -> new ArmorItem(TArmorMaterial.RAOIST_ROBE, EquipmentSlotType.CHEST, properties()));
    public static final RegistryObject<Item> RAOIST_ROBE_LEGGINGS = ITEM.register("raoist_robe_leggings", () -> new ArmorItem(TArmorMaterial.RAOIST_ROBE, EquipmentSlotType.LEGS, properties()));
    public static final RegistryObject<Item> RAOIST_ROBE_BOOTS = ITEM.register("raoist_robe_boots", () -> new ArmorItem(TArmorMaterial.RAOIST_ROBE, EquipmentSlotType.FEET, properties()));
    public static final RegistryObject<Item> MUSIC_DISC_ALIOTH = ITEM.register("music_disc_alioth", () -> new TMusicDiscItem(0, TSoundEvents.ALIOTH, properties().maxStackSize(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MUSIC_DISC_ALKID = ITEM.register("music_disc_alkid", () -> new TMusicDiscItem(0, TSoundEvents.ALKID, properties().maxStackSize(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MUSIC_DISC_DUBHE = ITEM.register("music_disc_dubhe", () -> new TMusicDiscItem(0, TSoundEvents.DUBHE, properties().maxStackSize(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MUSIC_DISC_MEGREZ = ITEM.register("music_disc_megrez", () -> new TMusicDiscItem(0, TSoundEvents.MEGREZ, properties().maxStackSize(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MUSIC_DISC_MERAK = ITEM.register("music_disc_merak", () -> new TMusicDiscItem(0, TSoundEvents.MERAK, properties().maxStackSize(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MUSIC_DISC_MIZAR = ITEM.register("music_disc_mizar", () -> new TMusicDiscItem(0, TSoundEvents.MIZAR, properties().maxStackSize(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MUSIC_DISC_PHECDA = ITEM.register("music_disc_phecda", () -> new TMusicDiscItem(0, TSoundEvents.PHECDA, properties().maxStackSize(1).rarity(Rarity.RARE)));

    // block item
    public static RegistryObject<Item> DIVINE_STONE_ORE = ITEM.register("divine_stone_ore", () -> new BlockItem(TBlocks.DIVINE_STONE_ORE.get(), properties()));
    public static RegistryObject<Item> CINNABAR_ORE = ITEM.register("cinnabar_ore", () -> new BlockItem(TBlocks.CINNABAR_ORE.get(), properties()));
    public static RegistryObject<Item> DIVINE_STONE_BLOCK = ITEM.register("divine_stone_block", () -> new BlockItem(TBlocks.DIVINE_STONE_BLOCK.get(), properties()));
    public static RegistryObject<Item> TALISMAN_CRAFTING_TABLE = ITEM.register("talisman_crafting_table", () -> new BlockItem(TBlocks.TALISMAN_CRAFTING_TABLE.get(), properties().maxStackSize(1)));

    private static Item.Properties properties() {
        return new Item.Properties().group(ModInitializer.TalismanItemGroup);
    }

    public static void register(IEventBus bus) {
        ITEM.register(bus);
    }
}
