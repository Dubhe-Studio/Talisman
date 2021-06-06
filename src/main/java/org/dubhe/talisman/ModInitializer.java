package org.dubhe.talisman;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dubhe.talisman.registry.EntityTypeRegistry;
import org.dubhe.talisman.registry.ItemRegistry;

import static org.dubhe.talisman.ModInitializer.MODID;

@Mod(MODID)
public class ModInitializer {

    public static final String MODID = "talisman";

    private static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup TalismanItemGroup = new TalismanItemGroup();

    public ModInitializer() {

        ItemRegistry.completeRegistry(FMLJavaModLoadingContext.get().getModEventBus());
        EntityTypeRegistry.completeRegistry(FMLJavaModLoadingContext.get().getModEventBus());


        MinecraftForge.EVENT_BUS.register(this);
    }
}
