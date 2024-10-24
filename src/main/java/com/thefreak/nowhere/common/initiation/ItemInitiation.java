package com.thefreak.nowhere.common.initiation;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.common.items.CassetteItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInitiation {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Nowhere.MODID);

    public static final RegistryObject<Item> CASSETTE = ITEMS.register("cassette", () -> new CassetteItem(0,new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> TOE_CASSETTE = ITEMS.register("toe_cassette", () -> new CassetteItem(1,new Item.Properties().stacksTo(1)));

    public static final RegistryObject<BlockItem> TV = ITEMS.register("tv", () -> new BlockItem(BlockInitiation.TV_BLOCK.get(),new Item.Properties().stacksTo(1)));

    public static final RegistryObject<BlockItem> STRANGE_WALL = ITEMS.register("strange_wall", () -> new BlockItem(BlockInitiation.STRANGE_WALL.get(),new Item.Properties()));

}
