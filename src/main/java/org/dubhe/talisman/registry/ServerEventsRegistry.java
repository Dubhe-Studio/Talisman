package org.dubhe.talisman.registry;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.dubhe.talisman.screen.TalismanCraftingTableScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerEventsRegistry {
    @SubscribeEvent
    public static void onClientSetUpEvent(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ContainerTypeRegistry.TALISMAN_CRAFTING_TABLE.get(), TalismanCraftingTableScreen::new);
    }
}
