package rtg.world.biome.realistic.biomesoplenty;

import java.util.Random;

import biomesoplenty.api.biome.BOPBiomes;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import rtg.api.config.BiomeConfig;
import rtg.api.util.WorldUtil.Terrain;
import rtg.api.world.RTGWorld;
import rtg.api.world.surface.SurfaceBase;
import rtg.api.world.terrain.TerrainBase;


public class RealisticBiomeBOPQuagmire extends RealisticBiomeBOPBase {

    public static Biome biome = BOPBiomes.quagmire.orNull();
    public static Biome river = Biomes.RIVER;

    public RealisticBiomeBOPQuagmire() {

        super(biome);
    }

    @Override
    public void initConfig() {
    }

    @Override
    public TerrainBase initTerrain() {

        return new TerrainBOPQuagmire();
    }

    @Override
    public SurfaceBase initSurface() {

        return new SurfaceBOPQuagmire(getConfig(), biome.topBlock, biome.fillerBlock);
    }

    public class TerrainBOPQuagmire extends TerrainBase {

        public TerrainBOPQuagmire() {

        }

        @Override
        public float generateNoise(RTGWorld rtgWorld, int x, int y, float border, float river) {

            return terrainMarsh(x, y, rtgWorld, 61.5f, river);
        }
    }

    public class SurfaceBOPQuagmire extends SurfaceBase {

        public SurfaceBOPQuagmire(BiomeConfig config, IBlockState top, IBlockState filler) {

            super(config, top, filler);
        }

        @Override
        public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, RTGWorld rtgWorld, float[] noise, float river, Biome[] base) {

            Random rand = rtgWorld.rand();
            float c = Terrain.calcCliff(x, z, noise);
            boolean cliff = c > 1.4f ? true : false;

            for (int k = 255; k > -1; k--) {
                Block b = primer.getBlockState(x, k, z).getBlock();
                if (b == Blocks.AIR) {
                    depth = -1;
                }
                else if (b == Blocks.STONE) {
                    depth++;

                    if (cliff && k > 64) {
                        if (depth > -1 && depth < 2) {
                            if (rand.nextInt(3) == 0) {

                                primer.setBlockState(x, k, z, hcCobble(rtgWorld, i, j, x, z, k));
                            }
                            else {

                                primer.setBlockState(x, k, z, hcStone(rtgWorld, i, j, x, z, k));
                            }
                        }
                        else if (depth < 10) {
                            primer.setBlockState(x, k, z, hcStone(rtgWorld, i, j, x, z, k));
                        }
                    }
                    else {
                        if (depth == 0 && k > 61) {
                            primer.setBlockState(x, k, z, topBlock);
                        }
                        else if (depth < 4) {
                            primer.setBlockState(x, k, z, fillerBlock);
                        }
                    }
                }
            }
        }
    }
}
