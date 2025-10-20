/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.food.FoodData
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.api.utils;

import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class TOPlayerUtils {
    public static boolean isSurvivalOrAdventure(Player player) {
        return !player.m_7500_() && !player.m_5833_();
    }

    public static void giveItemToPlayer(Player player, ItemStack itemStack) {
        if (!player.m_150109_().m_36054_(itemStack)) {
            player.m_36176_(itemStack, false);
        }
    }

    public static void syncPlayerInventory(ServerPlayer serverPlayer) {
        serverPlayer.f_36095_.m_38946_();
    }

    public static void removeItemFromPlayer(Player player, Item item, int amount) {
        for (int i = 0; i < player.m_150109_().m_6643_(); ++i) {
            ItemStack stack = player.m_150109_().m_8020_(i);
            if (stack.m_41720_() != item) continue;
            int toRemove = Math.min(stack.m_41613_(), amount);
            stack.m_41774_(toRemove);
            if ((amount -= toRemove) <= 0) break;
        }
    }

    public static boolean hasItem(Player player, Item item) {
        return player.m_150109_().m_18949_(Set.of(item));
    }

    public static void addExhaustion(Player player, float exhaustion) {
        player.m_36324_().m_38703_(exhaustion);
    }

    public static ItemStack findItem(Player player, Predicate<ItemStack> condition) {
        for (ItemStack stack : player.m_150109_().f_35974_) {
            if (!condition.test(stack)) continue;
            return stack;
        }
        return ItemStack.f_41583_;
    }

    public static void restoreHunger(Player player, int foodAmount, float saturation) {
        FoodData foodData = player.m_36324_();
        int newFoodLevel = Math.min(foodData.m_38702_() + foodAmount, 20);
        float newSaturation = Math.min(foodData.m_38722_() + saturation, (float)newFoodLevel);
        foodData.m_38705_(newFoodLevel);
        foodData.m_38717_(newSaturation);
        foodData.m_150378_(0.0f);
    }

    public static void launchPlayer(Player player, Vec3 direction, double force) {
        Vec3 motion = direction.m_82541_().m_82490_(force);
        player.m_20256_(motion);
        player.f_19864_ = true;
    }

    public static void teleportPlayer(Player player, Vec3 targetPos, boolean negateFallDamage) {
        player.m_6021_(targetPos.f_82479_, targetPos.f_82480_, targetPos.f_82481_);
        if (negateFallDamage) {
            player.m_183634_();
        }
    }

    public static void restorePlayerAirSupply(Player player, int amount) {
        player.m_20301_(Math.min(player.m_20146_() + amount, player.m_6062_()));
    }
}

