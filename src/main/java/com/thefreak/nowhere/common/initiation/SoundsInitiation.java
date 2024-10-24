package com.thefreak.nowhere.common.initiation;

import com.thefreak.nowhere.Nowhere;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundsInitiation {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Nowhere.MODID);

    public static final RegistryObject<SoundEvent> LOCUST_HELLO = registerSoundEvent("locust_hello");
    public static final RegistryObject<SoundEvent> LOCUST_HELP = registerSoundEvent("locust_help");
    public static final RegistryObject<SoundEvent> LOCUST_PAIN = registerSoundEvent("locust_pain");
    public static final RegistryObject<SoundEvent> LOCUST_GROWL = registerSoundEvent("locust_growl");

    public static final RegistryObject<SoundEvent> LOCUST_GET_OUT = registerSoundEvent("locust_getout");



    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUNDS.register(name,() -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Nowhere.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
