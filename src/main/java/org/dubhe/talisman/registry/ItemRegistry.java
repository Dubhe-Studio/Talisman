package org.dubhe.talisman.registry;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.items.TalismanItem;

public class ItemRegistry {
    private static final DeferredRegister<Item> ItemRegistry = DeferredRegister.create(ForgeRegistries.ITEMS, ModInitializer.MODID);

    public static final RegistryObject<Item> TALISMAN = ItemRegistry.register("talisman", TalismanItem::new);


    public static void completeRegistry(IEventBus bus) {
        ItemRegistry.register(bus);
    }
}
