package com.thefreak.nowhere.common.initiation;

import com.thefreak.nowhere.Nowhere;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInitiation {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Nowhere.MODID);

    public static final RegistryObject<Item> CASSETTE = ITEMS.register("cassette", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<BlockItem> TV = ITEMS.register("tv", () -> new BlockItem(BlockInitiation.TV_BLOCK.get(),new Item.Properties().stacksTo(1)));

    public static final RegistryObject<BlockItem> STRANGE_WALL = ITEMS.register("strange_wall", () -> new BlockItem(BlockInitiation.STRANGE_WALL.get(),new Item.Properties()));

}
