package com.thefreak.nowhere.common.initiation;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.common.blocks.TVBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInitiation {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Nowhere.MODID);

    public static final RegistryObject<Block> TV_BLOCK = BLOCKS.register("tv", () -> new TVBlock(BlockBehaviour.Properties.of().noOcclusion()));

}
