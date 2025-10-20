/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.particle.LightningParticle$OrbData
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package com.gametechbc.traveloptics.data_manager;

import com.gametechbc.traveloptics.api.particle.ParticleDirection;
import com.gametechbc.traveloptics.api.particle.SphereParticleManager;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PlasmaFuelManager {
    public static final int MAX_PLASMA_POINTS = 300;

    public static int getPlasmaFuel(ItemStack stack) {
        if (stack.m_41782_() && stack.m_41783_().m_128441_("PlasmaFuel")) {
            return stack.m_41783_().m_128451_("PlasmaFuel");
        }
        return 0;
    }

    public static void setPlasmaFuel(ItemStack stack, int points) {
        int cappedPoints = Math.min(points, 300);
        stack.m_41784_().m_128405_("PlasmaFuel", cappedPoints);
    }

    public static void addPlasmaFuel(ItemStack stack, Player player, int amount) {
        int currentPoints = PlasmaFuelManager.getPlasmaFuel(stack);
        int newPoints = Math.min(currentPoints + amount, 300);
        PlasmaFuelManager.setPlasmaFuel(stack, newPoints);
        PlasmaFuelManager.playSoundOnThreshold(player, currentPoints, newPoints);
        PlasmaFuelManager.spawnParticlesOnThreshold(player, currentPoints, newPoints);
    }

    public static void displayActionBar(Player player, int points) {
        if (player instanceof ServerPlayer) {
            int percentage = (int)((double)points / 300.0 * 100.0);
            MutableComponent message = points >= 300 ? Component.m_237113_((String)("\u26a1 Plasma Fuel: " + percentage + "%")).m_130938_(style -> style.m_178520_(15219515)).m_130940_(ChatFormatting.BOLD) : Component.m_237113_((String)("\u26a1 Plasma Fuel: " + percentage + "%")).m_130938_(style -> style.m_178520_(16476957));
            player.m_5661_((Component)message, true);
        }
    }

    private static void playSoundOnThreshold(Player player, int oldPoints, int newPoints) {
        if (player.m_9236_().m_5776_()) {
            return;
        }
        if (newPoints >= 300 && oldPoints < 300) {
            player.m_9236_().m_5594_(null, player.m_20183_(), (SoundEvent)TravelopticsSounds.JETPACK_FUEL_FULL.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    private static void spawnParticlesOnThreshold(Player player, int oldPoints, int newPoints) {
        if (newPoints >= 300 && oldPoints < 300) {
            SphereParticleManager.spawnParticles(player.m_9236_(), (Entity)player, 25, (ParticleOptions)new LightningParticle.OrbData(255, 26, 0), ParticleDirection.OUTWARD, 4.0);
        }
    }
}

