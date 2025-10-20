/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package com.gametechbc.traveloptics.data_manager;

import net.minecraft.world.item.ItemStack;

public class CooldownManager {
    public static int getCooldown(ItemStack stack) {
        if (stack.m_41782_() && stack.m_41783_().m_128441_("Cooldown")) {
            return stack.m_41783_().m_128451_("Cooldown");
        }
        return 0;
    }

    public static void setCooldown(ItemStack stack, int cooldown, int maxCooldown) {
        int cappedCooldown = Math.min(cooldown, maxCooldown);
        stack.m_41784_().m_128405_("Cooldown", cappedCooldown);
    }

    public static void addCooldown(ItemStack stack, int amount, int maxCooldown) {
        int currentCooldown = CooldownManager.getCooldown(stack);
        int newCooldown = Math.min(currentCooldown + amount, maxCooldown);
        CooldownManager.setCooldown(stack, newCooldown, maxCooldown);
    }

    public static void reduceCooldown(ItemStack stack, int amount) {
        int currentCooldown = CooldownManager.getCooldown(stack);
        int newCooldown = Math.max(currentCooldown - amount, 0);
        CooldownManager.setCooldown(stack, newCooldown, Integer.MAX_VALUE);
    }

    public static boolean isCooldownActive(ItemStack stack) {
        return CooldownManager.getCooldown(stack) > 0;
    }

    public static void tickCooldown(ItemStack stack) {
        if (CooldownManager.isCooldownActive(stack)) {
            CooldownManager.reduceCooldown(stack, 1);
        }
    }
}

