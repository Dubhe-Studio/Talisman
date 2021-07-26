package org.dubhe.talisman;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dubhe.talisman.registry.TBlocks;
import org.dubhe.talisman.registry.TCommands;
import org.dubhe.talisman.registry.TContainerTypes;
import org.dubhe.talisman.registry.TEffects;
import org.dubhe.talisman.registry.TEntityTypes;
import org.dubhe.talisman.registry.TItems;
import org.dubhe.talisman.registry.TOreGenerates;
import org.dubhe.talisman.registry.TRecipes;
import org.dubhe.talisman.registry.TStats;
import org.dubhe.talisman.registry.TTileEntityTypes;
import org.dubhe.talisman.registry.event.TServerTickEvent;
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
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

//        TSoundEvents.register(FMLJavaModLoadingContext.get().getModEventBus());
        TBlocks.register(eventBus);
        TItems.register(eventBus);
        TTileEntityTypes.register(eventBus);
        TContainerTypes.register(eventBus);
        TEntityTypes.register(eventBus);
        TRecipes.register(eventBus);
        TEffects.register(eventBus);
        TStats.register();
        Talismans.init();

        MinecraftForge.EVENT_BUS.addListener(TOreGenerates::register);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new TServerTickEvent());
        MinecraftForge.EVENT_BUS.register(new TCommands());

    }

    public static ResourceLocation getIdentifier(String id) {
        return new ResourceLocation(MODID, id);
    }

}
