package com.thefreak.nowhere.common.initiation;

import com.thefreak.nowhere.Nowhere;
import com.thefreak.nowhere.common.entities.TheLocust;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInitiation {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Nowhere.MODID);

    public static final RegistryObject<EntityType<TheLocust>> THE_LOCUST = ENTITIES.register("the_locust", () ->
            EntityType.Builder.<TheLocust>of(TheLocust::new, MobCategory.MONSTER).sized(1.2F,5F).build(
                    new ResourceLocation(Nowhere.MODID, "the_locust").toString()));
}
