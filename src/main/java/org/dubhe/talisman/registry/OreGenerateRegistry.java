package org.dubhe.talisman.registry;

import com.google.common.collect.Lists;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class OreGenerateRegistry {
    private static final List<String> DIVINE_STONE_ORE = Lists.newArrayList(
            Biomes.JUNGLE.getRegistryName().toString(),
            Biomes.JUNGLE_EDGE.getRegistryName().toString(),
            Biomes.JUNGLE_HILLS.getRegistryName().toString(),
            Biomes.MODIFIED_JUNGLE.getRegistryName().toString(),
            Biomes.MODIFIED_JUNGLE_EDGE.getRegistryName().toString(),
            Biomes.BAMBOO_JUNGLE.getRegistryName().toString(),
            Biomes.BAMBOO_JUNGLE_HILLS.getRegistryName().toString()
    );
    @SubscribeEvent
    public static void onSetUpEvent(FMLCommonSetupEvent event) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (DIVINE_STONE_ORE.contains(biome.toString())) {
//                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
//                    Feature.ORE.withConfiguration(
//                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
//                                    BlockRegistry.obsidianBlock.get().getDefaultState(),
//                                    3)
//                    ).withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(new DepthAverageConfig(30, 30, 20)))
//            );

            }
        }
    }
}