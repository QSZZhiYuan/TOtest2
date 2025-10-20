/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.events.SpellPreCastEvent
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.event.TickEvent$PlayerTickEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickItem
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package com.gametechbc.traveloptics.effects.FrozenSight;

import com.gametechbc.traveloptics.init.TravelopticsEffects;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FrozenSightHandler {
    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player entity = event.getEntity();
        if (entity instanceof ServerPlayer) {
            MobEffectInstance frozenSightEffect;
            ServerPlayer player = (ServerPlayer)entity;
            if (!player.m_9236_().f_46443_ && (frozenSightEffect = player.m_21124_((MobEffect)TravelopticsEffects.FROZEN_SIGHT.get())) != null) {
                event.setCanceled(true);
                player.m_5661_((Component)Component.m_237113_((String)"Wait till it ends!").m_130940_(ChatFormatting.RED), true);
                player.m_9236_().m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11683_, SoundSource.PLAYERS, 0.5f, 1.0f);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCast(SpellPreCastEvent event) {
        Player entity = event.getEntity();
        if (entity instanceof ServerPlayer) {
            MobEffectInstance frozenSightEffect;
            ServerPlayer player = (ServerPlayer)entity;
            if (!player.m_9236_().f_46443_ && (frozenSightEffect = player.m_21124_((MobEffect)TravelopticsEffects.FROZEN_SIGHT.get())) != null) {
                event.setCanceled(true);
                player.m_5661_((Component)Component.m_237113_((String)"Wait till it ends!").m_130940_(ChatFormatting.RED), true);
                player.m_9236_().m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11683_, SoundSource.PLAYERS, 0.5f, 1.0f);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        MobEffectInstance effectInstance = player.m_21124_((MobEffect)TravelopticsEffects.FROZEN_SIGHT.get());
        if (effectInstance != null) {
            player.m_6853_(true);
            player.m_6862_(false);
        }
    }
}

