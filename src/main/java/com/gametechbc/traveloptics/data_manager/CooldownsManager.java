/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package com.gametechbc.traveloptics.data_manager;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class CooldownsManager {
    private static final String COOLDOWN_TAG = "Cooldowns";

    public static int getCooldown(ItemStack stack, String abilityKey) {
        if (stack.m_41782_() && stack.m_41783_().m_128441_(COOLDOWN_TAG)) {
            CompoundTag cooldowns = stack.m_41783_().m_128469_(COOLDOWN_TAG);
            return cooldowns.m_128451_(abilityKey);
        }
        return 0;
    }

    public static void setCooldown(ItemStack stack, String abilityKey, int cooldown, int maxCooldown) {
        CompoundTag cooldowns = stack.m_41784_().m_128469_(COOLDOWN_TAG);
        cooldowns.m_128405_(abilityKey, Math.min(cooldown, maxCooldown));
        stack.m_41783_().m_128365_(COOLDOWN_TAG, (Tag)cooldowns);
    }

    public static void addCooldown(ItemStack stack, String abilityKey, int amount, int maxCooldown) {
        int newCooldown = Math.min(CooldownsManager.getCooldown(stack, abilityKey) + amount, maxCooldown);
        CooldownsManager.setCooldown(stack, abilityKey, newCooldown, maxCooldown);
    }

    public static void reduceCooldown(ItemStack stack, String abilityKey, int amount) {
        int newCooldown = Math.max(CooldownsManager.getCooldown(stack, abilityKey) - amount, 0);
        CooldownsManager.setCooldown(stack, abilityKey, newCooldown, Integer.MAX_VALUE);
    }

    public static boolean isCooldownActive(ItemStack stack, String abilityKey) {
        return CooldownsManager.getCooldown(stack, abilityKey) > 0;
    }

    public static void tickCooldown(ItemStack stack) {
        if (stack.m_41782_() && stack.m_41783_().m_128441_(COOLDOWN_TAG)) {
            CompoundTag cooldowns = stack.m_41783_().m_128469_(COOLDOWN_TAG);
            for (String key : cooldowns.m_128431_()) {
                int newCooldown = Math.max(cooldowns.m_128451_(key) - 1, 0);
                cooldowns.m_128405_(key, newCooldown);
            }
            stack.m_41783_().m_128365_(COOLDOWN_TAG, (Tag)cooldowns);
        }
    }
}

