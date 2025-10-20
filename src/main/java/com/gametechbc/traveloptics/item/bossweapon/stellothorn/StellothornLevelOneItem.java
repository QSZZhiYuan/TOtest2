/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder
 *  io.redspace.ironsspellbooks.item.UniqueItem
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow$Pickup
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.gametechbc.traveloptics.item.bossweapon.stellothorn;

import com.gametechbc.traveloptics.config.WeaponConfig;
import com.gametechbc.traveloptics.entity.item.stellothorn.StellothornRenderer;
import com.gametechbc.traveloptics.entity.projectiles.stellothorn_projectile.StellothornProjectileEntity;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.item.bossweapon.stellothorn.StellothornItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.UniqueItem;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StellothornLevelOneItem
extends StellothornItem
implements UniqueItem {
    private static ItemDisplayContext transformType;

    public StellothornLevelOneItem(SpellDataRegistryHolder[] imbuedSpells) {
        super(imbuedSpells);
    }

    @Override
    protected void spawnStellothornProjectile(Level level, Player player, ItemStack stack, float chargePower) {
        if (!level.f_46443_) {
            StellothornProjectileEntity spearEntity = new StellothornProjectileEntity(level, (LivingEntity)player, stack);
            spearEntity.setOriginalSlot(player.m_150109_().f_35977_);
            spearEntity.m_37251_((Entity)player, player.m_146909_(), player.m_146908_(), 0.0f, chargePower * 1.8f, 1.0f);
            if (player.m_150110_().f_35937_) {
                spearEntity.f_36705_ = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            spearEntity.setTriggerAoEOnReturn(true);
            spearEntity.setTridentDamage(((Double)WeaponConfig.stellothornDamage.get()).floatValue());
            level.m_7967_((Entity)spearEntity);
        }
        level.m_6269_(null, (Entity)player, (SoundEvent)TravelopticsSounds.NIGHTWARDEN_SWING_HEAVY.get(), SoundSource.PLAYERS, 0.8f, 1.0f);
    }

    @Override
    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237113_((String)""));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.stellothorn.tooltip").m_130940_(ChatFormatting.GREEN));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.stellothorn.tooltip1"));
        if (Screen.m_96638_()) {
            tooltip.add((Component)Component.m_237113_((String)""));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapons.evolution_benefits.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.stellothorn.evo_one.active.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.stellothorn.evo_two.inactive.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.stellothorn.evo_three.inactive.tooltip"));
        } else {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapons.evolution.stars_one.tooltip"));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.shift_hold.advanced_tooltips"));
        }
        tooltip.add((Component)Component.m_237113_((String)""));
    }

    @Override
    public void getTransformType(ItemDisplayContext type) {
        transformType = type;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    protected BlockEntityWithoutLevelRenderer getRenderer() {
        return new StellothornRenderer();
    }
}

