/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  software.bernie.geckolib.renderer.GeoArmorRenderer
 */
package com.gametechbc.traveloptics.item.armor;

import com.gametechbc.traveloptics.data_manager.CooldownManager;
import com.gametechbc.traveloptics.data_manager.PhantomRageManager;
import com.gametechbc.traveloptics.entity.armor.cursed_wraithguard_armor.CursedWraithguardArmorModel;
import com.gametechbc.traveloptics.entity.armor.cursed_wraithguard_armor.CursedWraithguardArmorRenderer;
import com.gametechbc.traveloptics.init.TravelopticsEffects;
import com.gametechbc.traveloptics.item.TravelopticsArmorMaterials;
import com.gametechbc.traveloptics.item.UnbreakableImbueableArmor;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CursedWraithguardArmorItem
extends UnbreakableImbueableArmor {
    private static final int COOLDOWN_TICKS = 500;

    public CursedWraithguardArmorItem(ArmorItem.Type slot, Item.Properties settings) {
        super(TravelopticsArmorMaterials.CURSED_WRAITHGUARD, slot, settings);
    }

    @Override
    protected Set<ArmorItem.Type> getImbuableArmorTypes() {
        return Set.of(ArmorItem.Type.CHESTPLATE);
    }

    @Override
    protected Map<ArmorItem.Type, Integer> getMaxSpellSlots() {
        return Map.of(ArmorItem.Type.CHESTPLATE, 1);
    }

    public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.m_6883_(stack, world, entity, slot, selected);
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        ItemStack chestplate = player.m_6844_(ArmorItem.Type.CHESTPLATE.m_266308_());
        if (stack != chestplate || chestplate.m_41720_() != this || !this.isWearingFullSet(player)) {
            return;
        }
        if (!world.f_46443_) {
            if (CooldownManager.isCooldownActive(chestplate)) {
                CooldownManager.tickCooldown(chestplate);
                return;
            }
            if (player.f_20916_ > 0) {
                int currentPoints = PhantomRageManager.getPhantomRage(chestplate);
                if (currentPoints < 350) {
                    int rageAmount = world.f_46441_.m_188501_() < 0.6f ? 3 : 1;
                    PhantomRageManager.addPhantomRage(chestplate, player, rageAmount);
                }
                if (PhantomRageManager.getPhantomRage(chestplate) >= 350) {
                    PhantomRageManager.setPhantomRage(chestplate, 0);
                    this.applyRageEffect(player);
                    CooldownManager.setCooldown(chestplate, 500, 500);
                }
            }
        }
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        CursedWraithguardArmorItem chestplate;
        super.m_7373_(stack, world, tooltip, flag);
        tooltip.add((Component)Component.m_237113_((String)""));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.cursed_wraithguard_armor.tooltip").m_130940_(ChatFormatting.GREEN));
        Item item = stack.m_41720_();
        if (item instanceof CursedWraithguardArmorItem && (chestplate = (CursedWraithguardArmorItem)item).m_266204_() == ArmorItem.Type.CHESTPLATE) {
            int currentPhantomRage = PhantomRageManager.getPhantomRage(stack);
            tooltip.add((Component)Component.m_237113_((String)"\ud83d\udca2 ").m_7220_((Component)Component.m_237110_((String)"item.tooltip.traveloptics.phantom_rage", (Object[])new Object[]{currentPhantomRage, 350}).m_130940_(ChatFormatting.WHITE)));
        }
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.cursed_wraithguard_armor.tooltip1"));
        int cooldown = CooldownManager.getCooldown(stack);
        if (cooldown > 0) {
            tooltip.add((Component)Component.m_237110_((String)"item.tooltip.traveloptics.armor_cooldown", (Object[])new Object[]{cooldown / 20}).m_130940_(ChatFormatting.GRAY));
        }
        tooltip.add((Component)Component.m_237113_((String)""));
    }

    private void applyRageEffect(Player player) {
        int amplifier = (double)(player.m_21223_() / player.m_21233_()) <= 0.5 ? 3 : 1;
        player.m_7292_(new MobEffectInstance((MobEffect)TravelopticsEffects.PHANTOM_RAGE.get(), 300, amplifier, false, false, false));
    }

    private boolean isWearingFullSet(Player player) {
        return player.m_6844_(ArmorItem.Type.HELMET.m_266308_()).m_41720_() instanceof CursedWraithguardArmorItem && player.m_6844_(ArmorItem.Type.CHESTPLATE.m_266308_()).m_41720_() instanceof CursedWraithguardArmorItem && player.m_6844_(ArmorItem.Type.LEGGINGS.m_266308_()).m_41720_() instanceof CursedWraithguardArmorItem && player.m_6844_(ArmorItem.Type.BOOTS.m_266308_()).m_41720_() instanceof CursedWraithguardArmorItem;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new CursedWraithguardArmorRenderer(new CursedWraithguardArmorModel());
    }
}

