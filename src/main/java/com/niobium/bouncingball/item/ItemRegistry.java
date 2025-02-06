package com.niobium.bouncingball.item;

import com.niobium.bouncingball.Main;
import com.niobium.bouncingball.item.custom.BouncingBallItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Main.MODID);

    public static final DeferredItem<Item> BOUNCING_BALL = ITEMS.register("bouncing_ball",
            () -> new BouncingBallItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
