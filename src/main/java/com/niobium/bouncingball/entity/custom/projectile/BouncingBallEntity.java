package com.niobium.bouncingball.entity.custom.projectile;

import com.niobium.bouncingball.entity.EntityRegistry;
import com.niobium.bouncingball.item.ItemRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BouncingBallEntity extends ThrowableItemProjectile {
    private boolean hasCollided = false;

    public BouncingBallEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BouncingBallEntity(Level pLevel, LivingEntity livingEntity) {
        super(EntityRegistry.BOUNCING_BALL.get(),livingEntity, pLevel);
    }
    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.BOUNCING_BALL.get();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        if(hasCollided) {
            return;
        }

        Vec3 velocity = getDeltaMovement();

        if(velocity.length() <= 0.1){
            hasCollided = true;
            this.discard();
            ItemEntity bouncingBallItemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(),
                    new ItemStack(ItemRegistry.BOUNCING_BALL.get()));

            this.level().addFreshEntity(bouncingBallItemEntity);
            return;
        }

        Vec3 normal = Vec3.atLowerCornerOf(result.getDirection().getNormal());
        Vec3 reflected = velocity.subtract(normal.scale(2 * velocity.dot(normal)));
        double factor = Math.max(0, velocity.length() - 0.1 * 1.5) / velocity.length();

        setDeltaMovement(reflected.scale(factor));
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if(hasCollided) {
            return;
        }

        Entity hitEntity = result.getEntity();

        if (!(this.getOwner() instanceof Player shooter)) {
            return;
        }

        if (hitEntity == shooter) {
            hasCollided = true;
            this.discard();
            ItemStack bouncingBall = new ItemStack(ItemRegistry.BOUNCING_BALL.get());

            if (!shooter.getInventory().add(bouncingBall)) {
                shooter.drop(bouncingBall, false, false);
            }
            return;
        }

        if (hitEntity instanceof Player hitPlayer) {
            hasCollided = true;
            ItemStack bouncingBall = new ItemStack(ItemRegistry.BOUNCING_BALL.get());
            if (!hitPlayer.getInventory().add(bouncingBall)) {
                hitPlayer.drop(bouncingBall, false);
            }
        }
    }

    @Override
    public void tick() {
        Vec3 movement = getDeltaMovement();
        Vec3 position = position();

        BlockHitResult hitResult = this.level().clip(new ClipContext(
                position,
                position.add(movement),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this));

        if (hitResult.getType() != HitResult.Type.MISS) {
            onHitBlock(hitResult);
        }

        super.tick();
    }
}
