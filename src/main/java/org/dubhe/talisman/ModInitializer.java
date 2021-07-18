package org.dubhe.talisman;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dubhe.talisman.registry.TBaseValue;
import org.dubhe.talisman.registry.TBlocks;
import org.dubhe.talisman.registry.TContainerTypes;
import org.dubhe.talisman.registry.TEntityTypes;
import org.dubhe.talisman.registry.TItems;
import org.dubhe.talisman.registry.TRecipes;
import org.dubhe.talisman.registry.TSoundEvents;
import org.dubhe.talisman.registry.TStats;
import org.dubhe.talisman.registry.TTileEntityTypes;
import org.dubhe.talisman.talisman.Talismans;

import static org.dubhe.talisman.ModInitializer.MODID;

@Mod(MODID)
public class ModInitializer {

    public static final String MODID = "talisman";

    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup TalismanItemGroup = new ItemGroup("talisman_group") {
        @Override
        @SuppressWarnings("NullableProblems")
        public ItemStack createIcon() {
            return new ItemStack(TItems.TALISMAN.get());
        }
    };

    public ModInitializer() {

//        TSoundEvents.register(FMLJavaModLoadingContext.get().getModEventBus());
        TBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        TItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        TTileEntityTypes.register(FMLJavaModLoadingContext.get().getModEventBus());
        TContainerTypes.register(FMLJavaModLoadingContext.get().getModEventBus());
        TEntityTypes.register(FMLJavaModLoadingContext.get().getModEventBus());
        TRecipes.register(FMLJavaModLoadingContext.get().getModEventBus());
        TStats.register();
        Talismans.init();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
