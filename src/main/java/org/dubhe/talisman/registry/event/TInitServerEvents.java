package org.dubhe.talisman.registry.event;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.dubhe.talisman.screen.ChangeClothingScreen;
import org.dubhe.talisman.screen.TCTScreen;
import org.dubhe.talisman.registry.TContainerTypes;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TInitServerEvents {
    @SubscribeEvent
    public static void onClientSetUpEvent(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(TContainerTypes.TALISMAN_CRAFTING_TABLE.get(), TCTScreen::new);
        ScreenManager.registerFactory(TContainerTypes.CHANGE_CLOTHING.get(), ChangeClothingScreen::new);
    }
}
