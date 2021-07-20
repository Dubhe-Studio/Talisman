package org.dubhe.talisman.registry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import org.dubhe.talisman.ModInitializer;

public class TOreGenerates {
    private static final ConfiguredFeature<?, ?> DIVINE_STONE_ORE = Features.register(
            ModInitializer.getIdentifier("ore_divine_stone").toString(),
            Feature.ORE.withConfiguration(new OreFeatureConfig(
                    OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                    TBlocks.DIVINE_STONE_ORE.get().getDefaultState(),
                    9
            )).range(32).square().count(2)
    );

    private static final ConfiguredFeature<?, ?> CINNABAR_ORE = Features.register(
            ModInitializer.getIdentifier("ore_cinnabar").toString(),
            Feature.ORE.withConfiguration(new OreFeatureConfig(
                    OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                    TBlocks.CINNABAR_ORE.get().getDefaultState(),
                    8
            )).range(16).square().count(8)
    );

    public static void register(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        if (event.getName() != null && category != Biome.Category.THEEND && category != Biome.Category.NETHER && category != Biome.Category.NONE) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DIVINE_STONE_ORE);
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CINNABAR_ORE);
        }
    }
}