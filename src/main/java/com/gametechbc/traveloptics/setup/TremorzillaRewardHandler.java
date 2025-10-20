/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$EntityInteractSpecific
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package com.gametechbc.traveloptics.setup;

import com.gametechbc.traveloptics.init.TravelopticsItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TremorzillaRewardHandler {
    private static final String REWARD_TAG = "TitanlordScepterGiven";

    @SubscribeEvent
    public static void onEntityInteracted(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity entity = event.getTarget();
        if (entity instanceof TamableAnimal) {
            Player player;
            TamableAnimal tameableAnimal = (TamableAnimal)entity;
            Player player2 = player = event.getEntity() instanceof Player ? event.getEntity() : null;
            // REMOVED AC dependency: Tremorzilla check disabled
            if (player == null) {
                return;
            }
            // AC creature check removed - this feature requires Alex's Caves
            return;
            ItemStack heldItem = player.m_21205_();
            if (heldItem.m_41720_() != TravelopticsItems.TREMOR_CORE.get()) {
                return;
            }
            CompoundTag entityData = tameableAnimal.getPersistentData();
            if (tameableAnimal.m_6162_() && !tameableAnimal.m_21824_()) {
                if (player.m_9236_().m_5776_()) {
                    player.m_5661_((Component)Component.m_237113_((String)"This Tremorzilla needs to be tamed first!"), true);
                }
                return;
            }
            if (tameableAnimal.m_6162_() && entityData.m_128471_(REWARD_TAG)) {
                if (player.m_9236_().m_5776_()) {
                    player.m_5661_((Component)Component.m_237113_((String)"The reward has already been given to this Tremorzilla!"), true);
                }
                return;
            }
            if (tameableAnimal.m_6162_() && tameableAnimal.m_21824_() && tameableAnimal.m_269323_() instanceof Player && !entityData.m_128471_(REWARD_TAG)) {
                if (!player.m_9236_().m_5776_()) {
                    entityData.m_128379_(REWARD_TAG, true);
                    int slotIndex = player.m_150109_().f_35977_;
                    heldItem.m_41774_(1);
                    ItemStack rewardItem = new ItemStack((ItemLike)TravelopticsItems.TITANLORD_SCEPTER.get(), 1);
                    if (player.m_150109_().m_8020_(slotIndex).m_41619_()) {
                        player.m_150109_().m_6836_(slotIndex, rewardItem);
                    } else {
                        boolean addedToInventory = player.m_36356_(rewardItem);
                        if (!addedToInventory) {
                            Level level = player.m_9236_();
                            level.m_7967_((Entity)new ItemEntity(level, player.m_20185_(), player.m_20186_(), player.m_20189_(), rewardItem));
                        }
                    }
                } else {
                    player.m_5661_((Component)Component.m_237113_((String)"You have been rewarded with the Titanlord Scepter!"), true);
                }
            }
        }
    }
}

