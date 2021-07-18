package org.dubhe.talisman.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;

public class TSoundEvents {
    private static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModInitializer.MODID);

    public static final RegistryObject<SoundEvent> MUSIC_DISC_ = SOUND_EVENT.register("music_disc.", () -> new SoundEvent(new ResourceLocation(ModInitializer.MODID,  "sounds/")));


    public static void register(IEventBus bus) {
        SOUND_EVENT.register(bus);
    }

}
