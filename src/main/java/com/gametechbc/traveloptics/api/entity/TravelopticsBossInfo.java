/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 */
package com.gametechbc.traveloptics.api.entity;

import com.gametechbc.traveloptics.init.TravelopticsMessages;
import com.gametechbc.traveloptics.network.MessageUpdateBossBar;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;

public class TravelopticsBossInfo
extends ServerBossEvent {
    private int renderType;

    public TravelopticsBossInfo(Component component, BossEvent.BossBarColor bossBarColor, boolean dark, int renderType) {
        super(component, bossBarColor, BossEvent.BossBarOverlay.PROGRESS);
        this.m_7003_(dark);
        this.renderType = renderType;
    }

    public void setRenderType(int renderType) {
        if (renderType != this.renderType) {
            this.renderType = renderType;
            TravelopticsMessages.sendMSGToAll(new MessageUpdateBossBar(this.m_18860_(), renderType));
        }
    }

    public int getRenderType() {
        return this.renderType;
    }

    public void m_6543_(ServerPlayer serverPlayer) {
        TravelopticsMessages.sendNonLocal(new MessageUpdateBossBar(this.m_18860_(), this.renderType), serverPlayer);
        super.m_6543_(serverPlayer);
    }

    public void m_6539_(ServerPlayer serverPlayer) {
        TravelopticsMessages.sendNonLocal(new MessageUpdateBossBar(this.m_18860_(), -1), serverPlayer);
        super.m_6539_(serverPlayer);
    }
}

