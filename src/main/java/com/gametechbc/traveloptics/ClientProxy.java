/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.resources.sounds.TickableSoundInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package com.gametechbc.traveloptics;

import com.gametechbc.traveloptics.CommonProxy;
import com.gametechbc.traveloptics.sound.MechanizedExoskeletonJetpackSound;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(value=Dist.CLIENT)
@Mod.EventBusSubscriber(modid="traveloptics", value={Dist.CLIENT})
public class ClientProxy
extends CommonProxy {
    public static final Int2ObjectMap<TrackedSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap();
    public static final Map<BlockEntity, AbstractTickableSoundInstance> BLOCK_ENTITY_SOUND_INSTANCE_MAP = new HashMap<BlockEntity, AbstractTickableSoundInstance>();
    private static final ClientProxy INSTANCE = new ClientProxy();
    private static int tickCounter = 0;
    public static Map<UUID, Integer> bossBarRenderTypes = new HashMap<UUID, Integer>();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && ++tickCounter % 2 == 0) {
            INSTANCE.checkAndClearInactiveSounds();
        }
    }

    @Override
    public void clearSoundCacheFor(Entity entity) {
        ENTITY_SOUND_INSTANCE_MAP.remove(entity.m_19879_());
    }

    @Override
    public void clearSoundCacheFor(BlockEntity entity) {
        BLOCK_ENTITY_SOUND_INSTANCE_MAP.remove(entity);
    }

    @Override
    public void playWorldSound(@Nullable Object soundEmitter, byte type) {
        if (soundEmitter instanceof Entity) {
            Entity entity = (Entity)soundEmitter;
            if (!entity.m_9236_().f_46443_) {
                return;
            }
        }
        switch (type) {
            case 1: {
                TrackedSoundInstance tracked;
                MechanizedExoskeletonJetpackSound jetpackSound;
                AbstractTickableSoundInstance old;
                if (!(soundEmitter instanceof LivingEntity)) break;
                LivingEntity livingEntity = (LivingEntity)soundEmitter;
                AbstractTickableSoundInstance abstractTickableSoundInstance = old = ENTITY_SOUND_INSTANCE_MAP.get(livingEntity.m_19879_()) != null ? ((TrackedSoundInstance)ClientProxy.ENTITY_SOUND_INSTANCE_MAP.get((int)livingEntity.m_19879_())).sound : null;
                if (old == null || !(old instanceof MechanizedExoskeletonJetpackSound) || !(jetpackSound = (MechanizedExoskeletonJetpackSound)old).isSameEntity(livingEntity)) {
                    MechanizedExoskeletonJetpackSound sound = new MechanizedExoskeletonJetpackSound(livingEntity);
                    tracked = new TrackedSoundInstance(sound);
                    ENTITY_SOUND_INSTANCE_MAP.put(livingEntity.m_19879_(), (Object)tracked);
                } else {
                    tracked = (TrackedSoundInstance)ENTITY_SOUND_INSTANCE_MAP.get(livingEntity.m_19879_());
                }
                tracked.lastUpdateTick = System.currentTimeMillis();
                if (Minecraft.m_91087_().m_91106_().m_120403_((SoundInstance)tracked.sound) || !tracked.sound.m_7767_()) break;
                Minecraft.m_91087_().m_91106_().m_120372_((TickableSoundInstance)tracked.sound);
            }
        }
    }

    public void checkAndClearInactiveSounds() {
        long currentTime = System.currentTimeMillis();
        ENTITY_SOUND_INSTANCE_MAP.int2ObjectEntrySet().removeIf(entry -> {
            TrackedSoundInstance tracked = (TrackedSoundInstance)entry.getValue();
            if (currentTime - tracked.lastUpdateTick > 100L) {
                AbstractTickableSoundInstance patt4289$temp = tracked.sound;
                if (patt4289$temp instanceof MechanizedExoskeletonJetpackSound) {
                    MechanizedExoskeletonJetpackSound jetpackSound = (MechanizedExoskeletonJetpackSound)patt4289$temp;
                    jetpackSound.stopSound();
                }
                return true;
            }
            return false;
        });
    }

    @Override
    public Player getClientSidePlayer() {
        return Minecraft.m_91087_().f_91074_;
    }

    @Override
    public void removeBossBarRender(UUID bossBar) {
        bossBarRenderTypes.remove(bossBar);
    }

    @Override
    public void setBossBarRender(UUID bossBar, int renderType) {
        bossBarRenderTypes.put(bossBar, renderType);
    }

    public static class TrackedSoundInstance {
        public final AbstractTickableSoundInstance sound;
        public long lastUpdateTick;

        public TrackedSoundInstance(AbstractTickableSoundInstance sound) {
            this.sound = sound;
            this.lastUpdateTick = System.currentTimeMillis();
        }
    }
}

