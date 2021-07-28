package org.dubhe.talisman.registry;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;

import static org.dubhe.talisman.ModInitializer.getIdentifier;

public class TSoundEvents {
    private static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModInitializer.MODID);

    public static final RegistryObject<SoundEvent> ALIOTH = SOUND_EVENT.register("music_disc.alioth", () -> new SoundEvent(getIdentifier("music_disc.alioth")));
    public static final RegistryObject<SoundEvent> ALKID = SOUND_EVENT.register("music_disc.alkid", () -> new SoundEvent(getIdentifier("music_disc.alkid")));
    public static final RegistryObject<SoundEvent> DUBHE = SOUND_EVENT.register("music_disc.dubhe", () -> new SoundEvent(getIdentifier("music_disc.dubhe")));
    public static final RegistryObject<SoundEvent> MEGREZ = SOUND_EVENT.register("music_disc.megrez", () -> new SoundEvent(getIdentifier("music_disc.megrez")));
    public static final RegistryObject<SoundEvent> MERAK = SOUND_EVENT.register("music_disc.merak", () -> new SoundEvent(getIdentifier("music_disc.merak")));
    public static final RegistryObject<SoundEvent> MIZAR = SOUND_EVENT.register("music_disc.mizar", () -> new SoundEvent(getIdentifier("music_disc.mizar")));
    public static final RegistryObject<SoundEvent> PHECDA = SOUND_EVENT.register("music_disc.phecda", () -> new SoundEvent(getIdentifier("music_disc.phecda")));
    public static final RegistryObject<SoundEvent> THE_BIG_DIPPER = SOUND_EVENT.register("music.the_big_dipper", () -> new SoundEvent(getIdentifier("music.the_big_dipper")));

    public static void register(IEventBus bus) {
        SOUND_EVENT.register(bus);
    }

}
