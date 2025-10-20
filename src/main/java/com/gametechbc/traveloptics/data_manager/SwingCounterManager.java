/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package com.gametechbc.traveloptics.data_manager;

import net.minecraft.world.item.ItemStack;

public class SwingCounterManager {
    private static final String SWING_COUNTER_TAG = "SwingCounter";

    public static int getSwingCount(ItemStack stack) {
        if (stack.m_41782_() && stack.m_41783_().m_128441_(SWING_COUNTER_TAG)) {
            return stack.m_41783_().m_128451_(SWING_COUNTER_TAG);
        }
        return 1;
    }

    public static void setSwingCount(ItemStack stack, int count) {
        stack.m_41784_().m_128405_(SWING_COUNTER_TAG, count);
    }

    public static void incrementSwingCount(ItemStack stack, int maxSwings) {
        int current = SwingCounterManager.getSwingCount(stack);
        SwingCounterManager.setSwingCount(stack, current >= maxSwings ? 1 : current + 1);
    }
}

