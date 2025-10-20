/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.npc.VillagerTrades$ItemListing
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.trading.MerchantOffer
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.storage.loot.LootParams
 *  net.minecraft.world.level.storage.loot.LootParams$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 */
package com.gametechbc.traveloptics.api.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class AdvancedTrades {

    public static class LootOutputTrade
    implements VillagerTrades.ItemListing {
        private final ItemStack currency;
        private final ResourceLocation lootTable;
        private final int minCurrency;
        private final int maxCurrency;
        private final int tradeUses;
        private final float priceMultiplier;

        public LootOutputTrade(ItemStack currency, int minCurrency, int maxCurrency, ResourceLocation lootTable, int tradeUses, float priceMultiplier) {
            this.currency = currency;
            this.minCurrency = minCurrency;
            this.maxCurrency = maxCurrency;
            this.lootTable = lootTable;
            this.tradeUses = tradeUses;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer m_213663_(Entity trader, RandomSource random) {
            Level level = trader.m_9236_();
            if (!(level instanceof ServerLevel)) {
                return null;
            }
            ServerLevel serverLevel = (ServerLevel)level;
            LootTable lootTableData = serverLevel.m_7654_().m_278653_().m_278676_(this.lootTable);
            LootParams context = new LootParams.Builder(serverLevel).m_287235_(LootContextParamSets.f_81410_);
            ObjectArrayList items = lootTableData.m_287195_(context);
            if (!items.isEmpty()) {
                int currencyAmount = random.m_216332_(this.minCurrency, this.maxCurrency);
                ItemStack output = (ItemStack)items.get(0);
                return new MerchantOffer(new ItemStack((ItemLike)this.currency.m_41720_(), currencyAmount), output, this.tradeUses, 0, this.priceMultiplier);
            }
            return null;
        }
    }

    public static class LootCurrencyTrade
    implements VillagerTrades.ItemListing {
        private final ResourceLocation lootTable;
        private final ItemStack output;
        private final int minOutput;
        private final int maxOutput;
        private final int tradeUses;
        private final float priceMultiplier;

        public LootCurrencyTrade(ResourceLocation lootTable, ItemStack output, int minOutput, int maxOutput, int tradeUses, float priceMultiplier) {
            this.lootTable = lootTable;
            this.output = output;
            this.minOutput = minOutput;
            this.maxOutput = maxOutput;
            this.tradeUses = tradeUses;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer m_213663_(Entity trader, RandomSource random) {
            Level level = trader.m_9236_();
            if (!(level instanceof ServerLevel)) {
                return null;
            }
            ServerLevel serverLevel = (ServerLevel)level;
            LootTable lootTableData = serverLevel.m_7654_().m_278653_().m_278676_(this.lootTable);
            LootParams context = new LootParams.Builder(serverLevel).m_287235_(LootContextParamSets.f_81410_);
            ObjectArrayList items = lootTableData.m_287195_(context);
            if (!items.isEmpty()) {
                ItemStack currency = (ItemStack)items.get(0);
                int outputAmount = random.m_216332_(this.minOutput, this.maxOutput);
                return new MerchantOffer(currency, new ItemStack((ItemLike)this.output.m_41720_(), outputAmount), this.tradeUses, 0, this.priceMultiplier);
            }
            return null;
        }
    }

    public static class DualItemTrade
    implements VillagerTrades.ItemListing {
        private final ItemStack currency;
        private final ItemStack middleItem;
        private final ItemStack output;
        private final int minCurrency;
        private final int maxCurrency;
        private final int minMiddle;
        private final int maxMiddle;
        private final int minOutput;
        private final int maxOutput;
        private final int tradeUses;
        private final float priceMultiplier;

        public DualItemTrade(int tradeUses, ItemStack currency, int minCurrency, int maxCurrency, ItemStack middleItem, int minMiddle, int maxMiddle, ItemStack output, int minOutput, int maxOutput, float priceMultiplier) {
            this.currency = currency;
            this.middleItem = middleItem;
            this.output = output;
            this.minCurrency = minCurrency;
            this.maxCurrency = maxCurrency;
            this.minMiddle = minMiddle;
            this.maxMiddle = maxMiddle;
            this.minOutput = minOutput;
            this.maxOutput = maxOutput;
            this.tradeUses = tradeUses;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer m_213663_(Entity trader, RandomSource random) {
            int currencyAmount = random.m_216332_(this.minCurrency, this.maxCurrency);
            int middleAmount = random.m_216332_(this.minMiddle, this.maxMiddle);
            int outputAmount = random.m_216332_(this.minOutput, this.maxOutput);
            return new MerchantOffer(new ItemStack((ItemLike)this.currency.m_41720_(), currencyAmount), new ItemStack((ItemLike)this.middleItem.m_41720_(), middleAmount), new ItemStack((ItemLike)this.output.m_41720_(), outputAmount), this.tradeUses, 0, this.priceMultiplier);
        }
    }

    public static class FlexibleTrade
    implements VillagerTrades.ItemListing {
        private final ItemStack currency;
        private final ItemStack output;
        private final int minCurrency;
        private final int maxCurrency;
        private final int minOutput;
        private final int maxOutput;
        private final int tradeUses;
        private final float priceMultiplier;

        public FlexibleTrade(int tradeUses, ItemStack currency, int minCurrency, int maxCurrency, ItemStack output, int minOutput, int maxOutput, float priceMultiplier) {
            this.currency = currency;
            this.output = output;
            this.minCurrency = minCurrency;
            this.maxCurrency = maxCurrency;
            this.minOutput = minOutput;
            this.maxOutput = maxOutput;
            this.tradeUses = tradeUses;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer m_213663_(Entity trader, RandomSource random) {
            int currencyAmount = random.m_216332_(this.minCurrency, this.maxCurrency);
            int outputAmount = random.m_216332_(this.minOutput, this.maxOutput);
            return new MerchantOffer(new ItemStack((ItemLike)this.currency.m_41720_(), currencyAmount), new ItemStack((ItemLike)this.output.m_41720_(), outputAmount), this.tradeUses, 0, this.priceMultiplier);
        }
    }
}

