package org.dubhe.talisman.registry;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.entity.TalismanEntity;

public final class TEntityTypes {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, ModInitializer.MODID);
    public static RegistryObject<EntityType<TalismanEntity>> TALISMAN = ENTITY_TYPE.register("talisman", () -> EntityType.Builder.create((EntityType.IFactory<TalismanEntity>) TalismanEntity::new, EntityClassification.MISC).size(0.6F, 0.6F).build("talisman"));

    public static void register(IEventBus bus) {
        ENTITY_TYPE.register(bus);
    }

}
