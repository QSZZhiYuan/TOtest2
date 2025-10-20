/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.effect.SummonTimer
 *  io.redspace.ironsspellbooks.entity.mobs.MagicSummon
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.LivingEntity
 */
package com.gametechbc.traveloptics.api.entity.mobs;

import io.redspace.ironsspellbooks.effect.SummonTimer;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public interface MagicDinosaurSummon
extends MagicSummon {
    default public void onRemovedHelper(Entity entity, SummonTimer timer) {
        LivingEntity livingEntity;
        Entity.RemovalReason reason = entity.m_146911_();
        if (reason != null && (livingEntity = this.getSummoner()) instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)livingEntity;
            if (reason.m_146965_()) {
                MobEffectInstance effect = player.m_21124_((MobEffect)timer);
                if (effect != null) {
                    MobEffectInstance decrement = new MobEffectInstance((MobEffect)timer, effect.m_19557_(), effect.m_19564_() - 1, false, false, true);
                    if (decrement.m_19564_() >= 0) {
                        player.m_21221_().put(timer, decrement);
                        player.f_8906_.m_9829_((Packet)new ClientboundUpdateMobEffectPacket(player.m_19879_(), decrement));
                    } else {
                        player.m_21195_((MobEffect)timer);
                    }
                }
                if (reason.equals((Object)Entity.RemovalReason.DISCARDED)) {
                    player.m_213846_((Component)Component.m_237110_((String)"ui.traveloptics.dinosaur_summon_despawn_message", (Object[])new Object[]{((Entity)this).m_5446_()}));
                }
            }
        }
    }
}

