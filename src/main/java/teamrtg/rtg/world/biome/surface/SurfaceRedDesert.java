package teamrtg.rtg.world.biome.surface;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;
import teamrtg.rtg.util.math.CliffCalculator;
import teamrtg.rtg.util.noise.CellNoise;
import teamrtg.rtg.util.noise.OpenSimplexNoise;
import teamrtg.rtg.api.biome.RealisticBiomeBase;

import java.util.Random;

public class SurfaceRedDesert extends SurfaceBase {


    public SurfaceRedDesert(RealisticBiomeBase biome) {
        super(biome);
    }

    @Override
    public void paintSurface(ChunkPrimer primer, int i, int j, int x, int y, int depth, World world, Random rand, OpenSimplexNoise simplex, CellNoise cell, float[] noise, float river, BiomeGenBase[] base) {
        float c = CliffCalculator.calc(x, y, noise);
        boolean cliff = c > 1.4f;

        for (int k = 255; k > -1; k--) {
            Block b = primer.getBlockState(x, k, y).getBlock();
            if (b == Blocks.air) {
                depth = -1;
            } else if (b == Blocks.stone) {
                depth++;

                if (cliff) {
                    if (depth < 6) {
                        primer.setBlockState(x, k, y, biome.config.CLIFF_BLOCK_1.get().getBlock().getStateFromMeta((byte) 14));
                    }
                } else if (depth < 6) {
                    if (depth == 0 && k > 61) {
                        primer.setBlockState(x, k, y, biome.config.TOP_BLOCK.get());
                    } else if (depth < 4) {
                        primer.setBlockState(x, k, y, biome.config.FILL_BLOCK.get());
                    } else {
                        primer.setBlockState(x, k, y, biome.config.BOTTOM_BLOCK.get().getBlock().getDefaultState());
                    }
                }
            }
        }
    }
}
