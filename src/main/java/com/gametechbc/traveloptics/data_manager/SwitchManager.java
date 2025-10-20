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

public class SwitchManager {
    private static final String SWITCHES_TAG = "SwitchStates";

    public static boolean isEnabled(ItemStack stack, String switchKey) {
        if (stack.m_41782_() && stack.m_41783_().m_128441_(SWITCHES_TAG)) {
            CompoundTag switches = stack.m_41783_().m_128469_(SWITCHES_TAG);
            return switches.m_128471_(switchKey);
        }
        return false;
    }

    public static void setSwitch(ItemStack stack, String switchKey, boolean state) {
        CompoundTag switches = stack.m_41784_().m_128469_(SWITCHES_TAG);
        switches.m_128379_(switchKey, state);
        stack.m_41783_().m_128365_(SWITCHES_TAG, (Tag)switches);
    }

    public static void toggleSwitch(ItemStack stack, String switchKey) {
        SwitchManager.setSwitch(stack, switchKey, !SwitchManager.isEnabled(stack, switchKey));
    }
}

