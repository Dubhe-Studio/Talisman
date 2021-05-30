package net.minecraft.world.gen.placement;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class CountMultilayerPlacement extends Placement<FeatureSpreadConfig> {
   public CountMultilayerPlacement(Codec<FeatureSpreadConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, FeatureSpreadConfig config, BlockPos pos) {
      List<BlockPos> list = Lists.newArrayList();
      int i = 0;

      boolean flag;
      do {
         flag = false;

         for(int j = 0; j < config.getSpread().getSpread(rand); ++j) {
            int k = rand.nextInt(16) + pos.getX();
            int l = rand.nextInt(16) + pos.getZ();
            int i1 = helper.func_242893_a(Heightmap.Type.MOTION_BLOCKING, k, l);
            int j1 = func_242915_a(helper, k, i1, l, i);
            if (j1 != Integer.MAX_VALUE) {
               list.add(new BlockPos(k, j1, l));
               flag = true;
            }
         }

         ++i;
      } while(flag);

      return list.stream();
   }

   private static int func_242915_a(WorldDecoratingHelper p_242915_0_, int p_242915_1_, int p_242915_2_, int p_242915_3_, int p_242915_4_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(p_242915_1_, p_242915_2_, p_242915_3_);
      int i = 0;
      BlockState blockstate = p_242915_0_.func_242894_a(blockpos$mutable);

      for(int j = p_242915_2_; j >= 1; --j) {
         blockpos$mutable.setY(j - 1);
         BlockState blockstate1 = p_242915_0_.func_242894_a(blockpos$mutable);
         if (!func_242914_a(blockstate1) && func_242914_a(blockstate) && !blockstate1.matchesBlock(Blocks.BEDROCK)) {
            if (i == p_242915_4_) {
               return blockpos$mutable.getY() + 1;
            }

            ++i;
         }

         blockstate = blockstate1;
      }

      return Integer.MAX_VALUE;
   }

   private static boolean func_242914_a(BlockState p_242914_0_) {
      return p_242914_0_.isAir() || p_242914_0_.matchesBlock(Blocks.WATER) || p_242914_0_.matchesBlock(Blocks.LAVA);
   }
}