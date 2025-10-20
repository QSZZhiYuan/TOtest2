/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package com.gametechbc.traveloptics.setup;

import com.gametechbc.traveloptics.init.TravelopticsItems;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemTooltipsHandler {
    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemTooltipsHandler.execute((Event)event, event.getItemStack(), event.getToolTip());
    }

    public static void execute(ItemStack itemstack, List<Component> tooltip) {
        ItemTooltipsHandler.execute(null, itemstack, tooltip);
    }

    private static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
        if (tooltip == null) {
            return;
        }
        if (itemstack.m_41720_() == TravelopticsItems.ABYSSAL_SPELLWEAVE_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.CRIMSON_SPELLWEAVE_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.VERDANT_SPELLWEAVE_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.CELESTIAL_SPELLWEAVE_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.EVOKATED_SPELLWEAVE_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.ELDRITCH_SPELLWEAVE_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.PYRO_SPELLWEAVE_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.LIGHTNING_SPELLWEAVE_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.CRYO_SPELLWEAVE_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.TECTONIC_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.HULLBREAKER_STEEL.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.VOID_SPELLWEAVE_INGOT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.ELYTRA_JETPACK_COMPONENT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.DARK_GEM_OF_THE_LIVING_VOID.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.HULLBREAKER_SCALES.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.DARKNESS_CLOTH.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.creating_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.ABYSSAL_TENTACLE.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapon_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.DESERT_JEWEL_FRAGMENT.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapon_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.FLAME_TEMPERED_HANDGUARD.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapon_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.KYREXI_CLAWS.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapon_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.LAST_GLOW.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.last_glow.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.ECHO_WINGLET.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapon_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        }
        if (itemstack.m_41720_() == TravelopticsItems.TREMOR_CORE.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.tremor_core.desc").m_130940_(ChatFormatting.DARK_GREEN));
        }
        if (itemstack.m_41720_() == TravelopticsItems.RESONANT_SCRAP.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.resonant_scrap.desc").m_130940_(ChatFormatting.DARK_GREEN));
        }
        if (itemstack.m_41720_() == TravelopticsItems.OBSIDIAN_BOOKSHELF.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.obsidian_bookshelf.desc").m_130940_(ChatFormatting.DARK_GREEN));
        }
        if (itemstack.m_41720_() == TravelopticsItems.EYE_OF_NOTHINGNESS.get()) {
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.eye_of_nothingness.tooltip").m_130940_(ChatFormatting.DARK_GREEN));
        }
    }
}

