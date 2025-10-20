/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package com.gametechbc.traveloptics.item.items;

import com.gametechbc.traveloptics.init.TravelopticsItems;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ExcrucisItem
extends Item {
    public ExcrucisItem() {
        super(new Item.Properties().m_41487_(64).m_41486_().m_41497_(TravelopticsItems.RARITY_VOID));
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        if (!Screen.m_96638_()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.immense_pain_spike.desc").m_130940_(ChatFormatting.DARK_GREEN));
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.immense_pain_spike.shift_desc").m_130940_(ChatFormatting.DARK_GRAY));
            return;
        }
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.immense_pain_spike.headline_desc.damage_windows").m_130940_(ChatFormatting.GOLD));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.immense_pain_spike.tooltip_desc.damage_windows").m_130940_(ChatFormatting.GRAY));
        tooltip.add((Component)Component.m_237113_((String)""));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.immense_pain_spike.headline_desc.elemental_adaptation").m_130940_(ChatFormatting.GOLD));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.immense_pain_spike.tooltip_desc.elemental_adaptation").m_130940_(ChatFormatting.GRAY));
        tooltip.add((Component)Component.m_237113_((String)""));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.immense_pain_spike.headline_desc.resurgence_counter").m_130940_(ChatFormatting.GOLD));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.immense_pain_spike.tooltip_desc.resurgence_counter").m_130940_(ChatFormatting.GRAY));
        tooltip.add((Component)Component.m_237113_((String)""));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.immense_pain_spike.headline_desc.dodge").m_130940_(ChatFormatting.GOLD));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.immense_pain_spike.tooltip_desc.dodge").m_130940_(ChatFormatting.GRAY));
    }
}

