
package sct.culinarycraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFluidClassic extends BlockFluidRoot {

    protected boolean[] isOptimalFlowDirection = new boolean[4];
    protected int[] flowCost = new int[4];

    public BlockFluidClassic(int id, Material material) {

        super(id, material);
    }

    @Override
    public final int getQuantaValue(IBlockAccess world, int x, int y, int z) {

        if (world.getBlockId(x, y, z) == 0) {
            return 0;
        }
        if (world.getBlockId(x, y, z) != blockID) {
            return -1;
        }
        int quantaRemaining = quantaPerBlock - world.getBlockMetadata(x, y, z);
        return quantaRemaining;
    }

    @Override
    public boolean canCollideCheck(int meta, boolean fullHit) {

        return fullHit && meta == 0;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {

        int quantaRemaining = quantaPerBlock - world.getBlockMetadata(x, y, z);
        int expQuanta = -101;

        // check adjacent block levels if non-source
        if (quantaRemaining < quantaPerBlock) {
            int y2 = y - densityDir;

            if (world.getBlockId(x, y2, z) == blockID || world.getBlockId(x - 1, y2, z) == blockID || world.getBlockId(x + 1, y2, z) == blockID
                    || world.getBlockId(x, y2, z - 1) == blockID || world.getBlockId(x, y2, z + 1) == blockID) {
                expQuanta = quantaPerBlock - 1;

            } else {
                int maxQuanta = -100;
                maxQuanta = getLargerQuanta(world, x - 1, y, z, maxQuanta);
                maxQuanta = getLargerQuanta(world, x + 1, y, z, maxQuanta);
                maxQuanta = getLargerQuanta(world, x, y, z - 1, maxQuanta);
                maxQuanta = getLargerQuanta(world, x, y, z + 1, maxQuanta);

                expQuanta = maxQuanta - 1;
            }
            // decay calculation
            if (expQuanta != quantaRemaining) {
                quantaRemaining = expQuanta;
                if (expQuanta <= 0) {
                    world.setBlockToAir(x, y, z);
                } else {
                    world.setBlockMetadataWithNotify(x, y, z, quantaPerBlock - expQuanta, 3);
                    world.scheduleBlockUpdate(x, y, z, blockID, tickRate);
                    world.notifyBlocksOfNeighborChange(x, y, z, blockID);
                }
            }
        } else if (quantaRemaining > quantaPerBlock) {
            world.setBlockMetadataWithNotify(x, y, z, 0, 3);
        }
        // Flow vertically if possible
        if (canDisplace(world, x, y + densityDir, z)) {
            flowIntoBlock(world, x, y + densityDir, z, 1);
            return;
        }
        // Flow outward if possible
        int flowMeta = quantaPerBlock - quantaRemaining + 1;
        if (flowMeta >= quantaPerBlock) {
            return;
        }

        if (isSourceBlock(world, x, y, z) || !isFlowingVertically(world, x, y, z)) {

            if (world.getBlockId(x, y - densityDir, z) == blockID) {
                flowMeta = 1;
            }

            boolean flowTo[] = getOptimalFlowDirections(world, x, y, z);

            if (flowTo[0]) {
                flowIntoBlock(world, x - 1, y, z, flowMeta);
            }
            if (flowTo[1]) {
                flowIntoBlock(world, x + 1, y, z, flowMeta);
            }
            if (flowTo[2]) {
                flowIntoBlock(world, x, y, z - 1, flowMeta);
            }
            if (flowTo[3]) {
                flowIntoBlock(world, x, y, z + 1, flowMeta);
            }
        }
    }

    /* BLOCK FUNCTIONS */
    // @Override
    // public void randomDisplayTick(World world, int x, int y, int z, Random random) {
    //
    // int l;
    //
    // if (random.nextInt(10) == 0) {
    // l = world.getBlockMetadata(x, y, z);
    //
    // if (l <= 0 || l >= 8) {
    // world.spawnParticle("suspended", x + random.nextFloat(), y + random.nextFloat(), z +
    // random.nextFloat(), 0.0D, 0.0D, 0.0D);
    // }
    // }
    //
    // for (l = 0; l < 0; ++l) {
    // int i1 = random.nextInt(4);
    // int j1 = x;
    // int k1 = z;
    //
    // if (i1 == 0) {
    // j1 = x - 1;
    // }
    //
    // if (i1 == 1) {
    // ++j1;
    // }
    //
    // if (i1 == 2) {
    // k1 = z - 1;
    // }
    //
    // if (i1 == 3) {
    // ++k1;
    // }
    //
    // if (world.getBlockMaterial(j1, y, k1) == Material.air && (world.getBlockMaterial(j1, y - 1,
    // k1).blocksMovement() || world.getBlockMaterial(j1, y - 1, k1).isLiquid())) {
    // float f = 0.0625F;
    // double d0 = x + random.nextFloat();
    // double d1 = y + random.nextFloat();
    // double d2 = z + random.nextFloat();
    //
    // if (i1 == 0) {
    // d0 = x - f;
    // }
    //
    // if (i1 == 1) {
    // d0 = x + 1 + f;
    // }
    //
    // if (i1 == 2) {
    // d2 = z - f;
    // }
    //
    // if (i1 == 3) {
    // d2 = z + 1 + f;
    // }
    //
    // double d3 = 0.0D;
    // double d4 = 0.0D;
    //
    // if (i1 == 0) {
    // d3 = -f;
    // }
    //
    // if (i1 == 1) {
    // d3 = f;
    // }
    //
    // if (i1 == 2) {
    // d4 = -f;
    // }
    //
    // if (i1 == 3) {
    // d4 = f;
    // }
    //
    // world.spawnParticle("splash", d0, d1, d2, d3, 0.0D, d4);
    // }
    // }
    //
    // if (this.blockMaterial == Material.water && random.nextInt(64) == 0) {
    // l = world.getBlockMetadata(x, y, z);
    //
    // if (l > 0 && l < 8) {
    // world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "liquid.water", random.nextFloat() * 0.25F +
    // 0.75F, random.nextFloat() * 1.0F + 0.5F, false);
    // }
    // }
    //
    // double d5;
    // double d6;
    // double d7;
    //
    // if (random.nextInt(10) == 0 && world.doesBlockHaveSolidTopSurface(x, y - 1, z) &&
    // !world.getBlockMaterial(x, y - 2, z).blocksMovement()) {
    // d5 = x + random.nextFloat();
    // d7 = y - 1.05D;
    // d6 = z + random.nextFloat();
    //
    // world.spawnParticle("dripWater", d5, d7, d6, 0.0D, 0.0D, 0.0D);
    // }
    // }

    public boolean isFlowingVertically(IBlockAccess world, int x, int y, int z) {

        return world.getBlockId(x, y + densityDir, z) == blockID || world.getBlockId(x, y, z) == blockID && canFlowInto(world, x, y + densityDir, z);
    }

    public boolean isSourceBlock(World world, int x, int y, int z) {

        return world.getBlockId(x, y, z) == blockID && world.getBlockMetadata(x, y, z) == 0;
    }

    protected boolean[] getOptimalFlowDirections(World world, int x, int y, int z) {

        for (int side = 0; side < 4; side++) {
            flowCost[side] = 1000;

            int x2 = x;
            int y2 = y;
            int z2 = z;

            switch (side) {
            case 0:
                --x2;
                break;
            case 1:
                ++x2;
                break;
            case 2:
                --z2;
                break;
            case 3:
                ++z2;
                break;
            }

            if (!canFlowInto(world, x2, y2, z2) || isSourceBlock(world, x2, y2, z2)) {
                continue;
            }
            if (canFlowInto(world, x2, y2 + densityDir, z2)) {
                flowCost[side] = 0;
            } else {
                flowCost[side] = calculateFlowCost(world, x2, y2, z2, 1, side);
            }
        }

        int min = flowCost[0];
        for (int side = 1; side < 4; side++) {
            if (flowCost[side] < min) {
                min = flowCost[side];
            }
        }
        for (int side = 0; side < 4; side++) {
            isOptimalFlowDirection[side] = flowCost[side] == min;
        }
        return isOptimalFlowDirection;
    }

    protected int calculateFlowCost(World world, int x, int y, int z, int recurseDepth, int side) {

        int cost = 1000;

        for (int adjSide = 0; adjSide < 4; adjSide++) {
            if (adjSide == 0 && side == 1 || adjSide == 1 && side == 0 || adjSide == 2 && side == 3 || adjSide == 3 && side == 2) {
                continue;
            }

            int x2 = x;
            int y2 = y;
            int z2 = z;

            switch (adjSide) {
            case 0:
                --x2;
                break;
            case 1:
                ++x2;
                break;
            case 2:
                --z2;
                break;
            case 3:
                ++z2;
                break;
            }

            if (!canFlowInto(world, x2, y2, z2) || isSourceBlock(world, x2, y2, z2)) {
                continue;
            }
            if (canFlowInto(world, x2, y2 + densityDir, z2)) {
                return recurseDepth;
            }
            if (recurseDepth >= 4) {
                continue;
            }
            int min = calculateFlowCost(world, x2, y2, z2, recurseDepth + 1, adjSide);
            if (min < cost) {
                cost = min;
            }
        }
        return cost;
    }

    protected void flowIntoBlock(World world, int x, int y, int z, int meta) {

        if (meta < 0) {
            return;
        }
        if (displaceIfPossible(world, x, y, z)) {
            world.setBlock(x, y, z, this.blockID, meta, 3);
        }
    }

    protected boolean canFlowInto(IBlockAccess world, int x, int y, int z) {

        int bId = world.getBlockId(x, y, z);
        if (bId == 0) {
            return true;
        }
        if (bId == blockID) {
            return true;
        }
        if (displacementIds.containsKey(bId)) {
            return displacementIds.get(bId);
        }
        Material material = Block.blocksList[bId].blockMaterial;
        if (material.blocksMovement() || material == Material.water || material == Material.lava || material == Material.portal) {
            return false;
        }
        return true;
    }

    protected int getLargerQuanta(IBlockAccess world, int x, int y, int z, int compare) {

        int quantaRemaining = getQuantaValue(world, x, y, z);

        if (quantaRemaining <= 0) {
            return compare;
        }
        return quantaRemaining >= compare ? quantaRemaining : compare;
    }

}
