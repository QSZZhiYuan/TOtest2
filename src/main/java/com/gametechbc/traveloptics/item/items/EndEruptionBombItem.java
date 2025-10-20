/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.item.items;

import com.gametechbc.traveloptics.entity.projectiles.end_eruption_bomb.EruptionBombProjectileEntity;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class EndEruptionBombItem
extends Item {
    public EndEruptionBombItem() {
        super(new Item.Properties().m_41487_(64).m_41486_().m_41497_(TravelopticsItems.RARITY_VOID));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        player.m_6672_(hand);
        if (!level.f_46443_) {
            Vec3 origin = player.m_146892_();
            Vec3 direction = player.m_20154_();
            EruptionBombProjectileEntity bomb = new EruptionBombProjectileEntity(level, (LivingEntity)player);
            bomb.m_146884_(origin.m_82549_(direction.m_82490_(0.5)).m_82492_(0.0, (double)(bomb.m_20206_() / 2.0f), 0.0));
            bomb.m_6686_(direction.f_82479_, direction.f_82480_, direction.f_82481_, 1.1f, 0.0f);
            double enderPower = player.m_21133_((Attribute)AttributeRegistry.ENDER_SPELL_POWER.get());
            float scaledDamage = (float)(10.0 * enderPower);
            bomb.setDamage(scaledDamage);
            level.m_7967_((Entity)bomb);
            level.m_214171_(GameEvent.f_157778_, bomb.m_20182_(), GameEvent.Context.m_223717_((Entity)player));
            level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12417_, SoundSource.PLAYERS, 0.6f, 0.4f / (player.m_217043_().m_188501_() * 0.4f + 0.8f));
            if (!player.m_150110_().f_35937_) {
                stack.m_41774_(1);
            }
            player.m_36335_().m_41524_((Item)this, 60);
            player.m_36246_(Stats.f_12982_.m_12902_((Object)this));
            player.m_21011_(hand, true);
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return InteractionResultHolder.m_19096_((Object)stack);
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.end_eruption_bomb.tooltip").m_130940_(ChatFormatting.GOLD));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.end_eruption_bomb.tooltip1").m_130940_(ChatFormatting.GRAY));
    }
}

