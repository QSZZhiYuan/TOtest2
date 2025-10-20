/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Eye_Of_Dungeon_Entity
 *  net.minecraft.ChatFormatting
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.stats.Stats
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.level.levelgen.structure.Structure
 */
package com.gametechbc.traveloptics.item.items;

import com.gametechbc.traveloptics.api.utils.TOGeneralUtils;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.util.TravelopticsTags;
import com.github.L_Ender.cataclysm.entity.projectile.Eye_Of_Dungeon_Entity;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;

public class CradleOfWillItem
extends Item {
    private final TagKey<Structure> structureTag = TravelopticsTags.CRADLE_OF_WILL_DESTINATION;
    private final int red = TOGeneralUtils.hexToRed("#8219ff");
    private final int green = TOGeneralUtils.hexToGreen("#8219ff");
    private final int blue = TOGeneralUtils.hexToBlue("#8219ff");

    public CradleOfWillItem() {
        super(new Item.Properties().m_41487_(64).m_41486_().m_41497_(TravelopticsItems.RARITY_VOID));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ServerLevel serverLevel;
        BlockPos structurePos;
        ItemStack itemStack = player.m_21120_(hand);
        player.m_6672_(hand);
        if (level instanceof ServerLevel && (structurePos = (serverLevel = (ServerLevel)level).m_215011_(this.structureTag, player.m_20183_(), 100, false)) != null) {
            Eye_Of_Dungeon_Entity eyeEntity = new Eye_Of_Dungeon_Entity(level, player.m_20185_(), player.m_20227_(0.5), player.m_20189_());
            eyeEntity.setItem(itemStack);
            eyeEntity.setR(this.red);
            eyeEntity.setG(this.green);
            eyeEntity.setB(this.blue);
            eyeEntity.signalTo(structurePos);
            level.m_214171_(GameEvent.f_157778_, eyeEntity.m_20182_(), GameEvent.Context.m_223717_((Entity)player));
            level.m_7967_((Entity)eyeEntity);
            if (player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer)player;
                CriteriaTriggers.f_10579_.m_73935_(serverPlayer, structurePos);
            }
            level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), (SoundEvent)TravelopticsSounds.NIGHTWARDEN_SUMMON_CLONE_1.get(), SoundSource.NEUTRAL, 0.5f, 1.0f);
            level.m_5898_(null, 1003, player.m_20183_(), 0);
            player.m_36335_().m_41524_((Item)this, 60);
            player.m_36246_(Stats.f_12982_.m_12902_((Object)this));
            player.m_21011_(hand, true);
            return InteractionResultHolder.m_19090_((Object)itemStack);
        }
        return InteractionResultHolder.m_19096_((Object)itemStack);
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.cradle_of_will.desc").m_130940_(ChatFormatting.DARK_GREEN));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.weapon_material.desc").m_130940_(ChatFormatting.DARK_GRAY));
        super.m_7373_(stack, world, tooltip, flag);
    }
}

