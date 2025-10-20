/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.item.curios;

import com.gametechbc.traveloptics.api.compat.Curios;
import com.gametechbc.traveloptics.api.utils.TOCurioUtils;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PocketBlackHoleBeltItem
extends SimpleDescriptiveCurio {
    public PocketBlackHoleBeltItem() {
        super(new Item.Properties().m_41487_(1).m_41497_(TravelopticsItems.RARITY_VOID), Curios.BELT_SLOT);
    }

    public void m_6883_(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.m_6883_(stack, level, entity, slot, selected);
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        if (player.f_19797_ % 2 == 0 && TOCurioUtils.getWearingCurio((LivingEntity)player, (Item)this)) {
            double radius = 8.0;
            for (ExperienceOrb orb : level.m_45976_(ExperienceOrb.class, player.m_20191_().m_82400_(radius))) {
                if (orb.m_213877_()) continue;
                this.pullEntityToward((LivingEntity)player, (Entity)orb, 0.45);
            }
        }
    }

    private void pullEntityToward(LivingEntity target, Entity entity, double strength) {
        double dz;
        double dy;
        double dx = target.m_20185_() - entity.m_20185_();
        double distanceSq = dx * dx + (dy = target.m_20186_() + 0.75 - entity.m_20186_()) * dy + (dz = target.m_20189_() - entity.m_20189_()) * dz;
        if (distanceSq < 0.1) {
            return;
        }
        double factor = strength / (distanceSq + 0.1);
        entity.m_20256_(entity.m_20184_().m_82520_(dx * factor, dy * factor, dz * factor));
    }

    public List<Component> getDescriptionLines(ItemStack stack) {
        return List.of(Component.m_237115_((String)"item.traveloptics.pocket_black_hole.desc").m_130940_(ChatFormatting.BLUE));
    }
}

