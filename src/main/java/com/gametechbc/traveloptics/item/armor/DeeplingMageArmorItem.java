/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  software.bernie.geckolib.renderer.GeoArmorRenderer
 */
package com.gametechbc.traveloptics.item.armor;

import com.gametechbc.traveloptics.api.item.AbstractImbuableArmorItem;
import com.gametechbc.traveloptics.entity.armor.deepling_mage_armor.DeeplingMageArmorModel;
import com.gametechbc.traveloptics.entity.armor.deepling_mage_armor.DeeplingMageArmorRenderer;
import com.gametechbc.traveloptics.item.TravelopticsArmorMaterials;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DeeplingMageArmorItem
extends AbstractImbuableArmorItem {
    public DeeplingMageArmorItem(ArmorItem.Type slot, Item.Properties settings) {
        super(TravelopticsArmorMaterials.DEEPLING_MAGE, slot, settings);
    }

    @Override
    protected Set<ArmorItem.Type> getImbuableArmorTypes() {
        return Set.of(ArmorItem.Type.CHESTPLATE);
    }

    @Override
    protected Map<ArmorItem.Type, Integer> getMaxSpellSlots() {
        return Map.of(ArmorItem.Type.CHESTPLATE, 1);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        super.onArmorTick(stack, world, player);
        if (!world.f_46443_ && this.isWearingFullSet(player) && player.m_5842_()) {
            this.grantWaterBreathing(player);
        }
    }

    private void grantWaterBreathing(Player player) {
        if (!player.m_21023_(MobEffects.f_19608_)) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19608_, 200, 0, false, false, true));
        }
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        super.m_7373_(stack, world, tooltip, flag);
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.deepling_mage_armor.tooltip").m_130940_(ChatFormatting.GREEN));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.deepling_mage_armor.tooltip1"));
        tooltip.add((Component)Component.m_237113_((String)""));
    }

    private boolean isWearingFullSet(Player player) {
        return player.m_6844_(ArmorItem.Type.HELMET.m_266308_()).m_41720_() instanceof DeeplingMageArmorItem && player.m_6844_(ArmorItem.Type.CHESTPLATE.m_266308_()).m_41720_() instanceof DeeplingMageArmorItem && player.m_6844_(ArmorItem.Type.LEGGINGS.m_266308_()).m_41720_() instanceof DeeplingMageArmorItem && player.m_6844_(ArmorItem.Type.BOOTS.m_266308_()).m_41720_() instanceof DeeplingMageArmorItem;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new DeeplingMageArmorRenderer(new DeeplingMageArmorModel());
    }
}

