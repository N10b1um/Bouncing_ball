package com.niobium.bouncingball.item.custom;

import com.niobium.bouncingball.entity.custom.projectile.BouncingBallEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class BouncingBallItem extends BowItem {
    public BouncingBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack itemstack, Level pLevel, LivingEntity pPlayer, int timeLeft) {
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        int charge = this.getUseDuration(itemstack,pPlayer)-timeLeft;
        float power = getPowerForTime(charge);

        if (!pLevel.isClientSide && pPlayer instanceof Player) {
            BouncingBallEntity bouncingBallEntity = new BouncingBallEntity(pLevel, pPlayer);
            bouncingBallEntity.setItem(itemstack);
            bouncingBallEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F * power, 1.0F);
            pLevel.addFreshEntity(bouncingBallEntity);

            ((Player) pPlayer).awardStat(Stats.ITEM_USED.get(this));
        }

        itemstack.shrink(1);
    }
}
