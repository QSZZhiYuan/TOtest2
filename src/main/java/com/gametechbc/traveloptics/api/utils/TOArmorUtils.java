/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package com.gametechbc.traveloptics.api.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TOArmorUtils {
    public static boolean isWearingFullSet(Player player, Class<? extends ArmorItem> armorClass) {
        return TOArmorUtils.isWearingArmorPiece(player, ArmorItem.Type.HELMET, armorClass) && TOArmorUtils.isWearingArmorPiece(player, ArmorItem.Type.CHESTPLATE, armorClass) && TOArmorUtils.isWearingArmorPiece(player, ArmorItem.Type.LEGGINGS, armorClass) && TOArmorUtils.isWearingArmorPiece(player, ArmorItem.Type.BOOTS, armorClass);
    }

    private static boolean isWearingArmorPiece(Player player, ArmorItem.Type armorType, Class<? extends ArmorItem> armorClass) {
        Item itemInSlot = player.m_6844_(armorType.m_266308_()).m_41720_();
        return armorClass.isInstance(itemInSlot);
    }

    public static boolean isWearingHalfSet(Player player, Class<? extends ArmorItem> armorClass) {
        int count = 0;
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            if (!TOArmorUtils.isWearingArmorPiece(player, type, armorClass)) continue;
            ++count;
        }
        return count >= 2;
    }

    public static int getHighestProtectionLevel(Player player, Class<? extends ArmorItem> armorClass) {
        int highest = 0;
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            Item item = player.m_6844_(type.m_266308_()).m_41720_();
            if (!armorClass.isInstance(item) || !(item instanceof ArmorItem)) continue;
            ArmorItem armor = (ArmorItem)item;
            highest = Math.max(highest, armor.m_40404_());
        }
        return highest;
    }

    public static int getArmorPieceCount(Player player, Class<? extends ArmorItem> armorClass) {
        int count = 0;
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            if (!TOArmorUtils.isWearingArmorPiece(player, type, armorClass)) continue;
            ++count;
        }
        return count;
    }

    public static boolean isMissingArmorPiece(Player player, Class<? extends ArmorItem> armorClass) {
        return TOArmorUtils.getArmorPieceCount(player, armorClass) < 4;
    }

    public static int getTotalArmorDurability(Player player, Class<? extends ArmorItem> armorClass) {
        int totalDurability = 0;
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            ItemStack stack = player.m_6844_(type.m_266308_());
            if (!armorClass.isInstance(stack.m_41720_()) || !stack.m_41763_()) continue;
            totalDurability += stack.m_41776_() - stack.m_41773_();
        }
        return totalDurability;
    }

    public static float getWeakestArmorDurability(Player player, Class<? extends ArmorItem> armorClass) {
        float weakest = 1.0f;
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            ItemStack stack = player.m_6844_(type.m_266308_());
            if (!armorClass.isInstance(stack.m_41720_()) || !stack.m_41763_()) continue;
            float durability = (float)(stack.m_41776_() - stack.m_41773_()) / (float)stack.m_41776_();
            weakest = Math.min(weakest, durability);
        }
        return weakest;
    }

    public static int getTotalArmorToughness(Player player, Class<? extends ArmorItem> armorClass) {
        int totalToughness = 0;
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            Item item = player.m_6844_(type.m_266308_()).m_41720_();
            if (!armorClass.isInstance(item) || !(item instanceof ArmorItem)) continue;
            ArmorItem armor = (ArmorItem)item;
            totalToughness += (int)armor.m_40405_();
        }
        return totalToughness;
    }

    public static boolean hasAnyArmor(Player player) {
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            if (player.m_6844_(type.m_266308_()).m_41619_()) continue;
            return true;
        }
        return false;
    }

    public static void repairAllArmor(Player player) {
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            ItemStack stack = player.m_6844_(type.m_266308_());
            if (stack.m_41619_() || !stack.m_41763_()) continue;
            stack.m_41721_(0);
        }
    }

    public static void damageAllArmor(Player player, int damage) {
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            ItemStack stack = player.m_6844_(type.m_266308_());
            if (stack.m_41619_() || !stack.m_41763_()) continue;
            stack.m_41622_(damage, (LivingEntity)player, p -> p.m_21166_(type.m_266308_()));
        }
    }

    public static int getTotalArmorWeight(Player player) {
        int totalWeight = 0;
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            Item item = player.m_6844_(type.m_266308_()).m_41720_();
            if (!(item instanceof ArmorItem)) continue;
            ArmorItem armor = (ArmorItem)item;
            totalWeight += armor.m_40404_() + (int)armor.m_40405_();
        }
        return totalWeight;
    }

    public static boolean isArmorFullyIntact(Player player) {
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            ItemStack stack = player.m_6844_(type.m_266308_());
            if (stack.m_41619_() || !stack.m_41763_() || stack.m_41773_() <= 0) continue;
            return false;
        }
        return true;
    }
}

