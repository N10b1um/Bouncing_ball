package com.niobium.bouncingball.entity;

import com.niobium.bouncingball.Main;
import com.niobium.bouncingball.entity.custom.projectile.BouncingBallEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Main.MODID);

    public static final Supplier<EntityType<BouncingBallEntity>> BOUNCING_BALL =
            ENTITY_TYPES.register("bouncing_ball", () -> EntityType.Builder.<BouncingBallEntity>of(BouncingBallEntity::new, MobCategory.MISC)
                    .sized(0.5F,0.5F).build("bouncing_ball"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
