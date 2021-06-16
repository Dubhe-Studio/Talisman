package org.dubhe.talisman;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dubhe.talisman.registry.BlockRegistry;
import org.dubhe.talisman.registry.ContainerTypeRegistry;
import org.dubhe.talisman.registry.EntityTypeRegistry;
import org.dubhe.talisman.registry.ItemRegistry;
import org.dubhe.talisman.registry.RecipeRegistry;
import org.dubhe.talisman.registry.TileEntityTypeRegistry;
import org.dubhe.talisman.talisman.Talismans;

import static org.dubhe.talisman.ModInitializer.MODID;

@Mod(MODID)
public class ModInitializer {

    public static final String MODID = "talisman";

    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup TalismanItemGroup = new TalismanItemGroup();

    public ModInitializer() {

        BlockRegistry.completeRegistry(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegistry.completeRegistry(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityTypeRegistry.completeRegistry(FMLJavaModLoadingContext.get().getModEventBus());
        ContainerTypeRegistry.completeRegistry(FMLJavaModLoadingContext.get().getModEventBus());
        EntityTypeRegistry.completeRegistry(FMLJavaModLoadingContext.get().getModEventBus());
        RecipeRegistry.completeRegistry(FMLJavaModLoadingContext.get().getModEventBus());
//        RecipeRegistry.completeRegistry(FMLJavaModLoadingContext.get().getModEventBus());
        Talismans.init();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
