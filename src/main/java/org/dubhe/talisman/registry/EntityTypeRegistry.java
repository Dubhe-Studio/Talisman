package org.dubhe.talisman.registry;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.entities.TalismanEntity;

public class EntityTypeRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ModInitializer.MODID);
    public static RegistryObject<EntityType<TalismanEntity>> TALISMAN = ENTITY_TYPES.register("talisman", () -> EntityType.Builder.create(TalismanEntity::new, EntityClassification.MISC).size(3, 0.5F).build("talisman"));

    public static void completeRegistry(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }

}
