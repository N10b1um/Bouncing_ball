package com.niobium.bouncingball;

import com.mojang.logging.LogUtils;
import com.niobium.bouncingball.entity.EntityRegistry;
import com.niobium.bouncingball.item.ItemRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "bouncingball";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Main(IEventBus modEventBus, ModContainer modContainer) {
        ItemRegistry.register(modEventBus);
        EntityRegistry.register(modEventBus);

        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::addCreative);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("BouncingBall Mod: Клиентская настройка запущена.");
        EntityRenderers.register(
                EntityRegistry.BOUNCING_BALL.get(),
                ThrownItemRenderer::new
        );
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ItemRegistry.BOUNCING_BALL);
        }
    }
}