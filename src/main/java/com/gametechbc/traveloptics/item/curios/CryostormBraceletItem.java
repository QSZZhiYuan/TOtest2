/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package com.gametechbc.traveloptics.item.curios;

import com.gametechbc.traveloptics.api.compat.Curios;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CryostormBraceletItem
extends SimpleDescriptiveCurio {
    public CryostormBraceletItem() {
        super(new Item.Properties().m_41487_(1).m_41497_(TravelopticsItems.RARITY_FROSTED), Curios.BRACELET_SLOT);
    }

    public List<Component> getDescriptionLines(ItemStack stack) {
        return List.of(Component.m_237115_((String)"item.traveloptics.cryostorm_bracelet.desc1").m_130940_(ChatFormatting.GREEN), Component.m_237115_((String)"item.traveloptics.cryostorm_bracelet.desc").m_130940_(ChatFormatting.BLUE));
    }
}

