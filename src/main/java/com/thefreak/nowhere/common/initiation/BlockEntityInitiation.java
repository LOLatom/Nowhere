package com.thefreak.nowhere.common.initiation;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.common.blockentity.TVBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInitiation {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Nowhere.MODID);

    public static final RegistryObject<BlockEntityType<TVBlockEntity>> TV_BE =
            BLOCK_ENTITY.register("tv_block_entity",() ->
                    BlockEntityType.Builder.of(TVBlockEntity::new , BlockInitiation.TV_BLOCK.get()).build(null));
}
