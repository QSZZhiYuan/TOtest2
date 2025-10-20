/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.ImmutableMultimap$Builder
 *  com.google.common.collect.Multimap
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.ISpellContainer
 *  net.minecraft.ChatFormatting
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.Nullable
 *  top.theillusivec4.curios.api.SlotContext
 */
package com.gametechbc.traveloptics.api.item;

import com.gametechbc.traveloptics.api.item.curio.CurioBaseItem;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

public abstract class AdvancedEchoCurio
extends CurioBaseItem {
    private final Multimap<Attribute, AttributeModifier> attributeMap = HashMultimap.create();
    private static final int EXPERIENCE_COST = 180;

    public AdvancedEchoCurio(Item.Properties properties, Map<Attribute, AttributeModifier> attributes) {
        super(properties.m_41487_(1).m_41497_(Rarity.EPIC));
        for (Map.Entry<Attribute, AttributeModifier> entry : attributes.entrySet()) {
            this.attributeMap.put((Object)entry.getKey(), (Object)entry.getValue());
        }
    }

    protected abstract Map<AbstractSpell, SpellAttributes> getSpellAttributes();

    protected abstract Component getAssignedHoverText();

    protected abstract Component getUnassignedHoverText();

    protected SoundEvent getAssignSound() {
        return SoundEvents.f_11887_;
    }

    protected int selectLevel(AbstractSpell spell, float quality, boolean prioritizeLowerLevel) {
        int maxLevel = spell.getMaxLevel();
        if (!prioritizeLowerLevel) {
            return 1 + Math.round(quality * (float)(maxLevel - 1));
        }
        double adjustedQuality = Math.pow(quality, 0.5);
        int calculatedLevel = 1 + Math.round((float)(adjustedQuality * (double)(maxLevel - 1)));
        return Math.min(calculatedLevel, maxLevel + Math.round((quality - 1.0f) * (float)maxLevel));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        AbstractSpell selectedSpell;
        ItemStack stack = player.m_21120_(hand);
        CompoundTag tag = stack.m_41784_();
        if (tag.m_128441_("spellAssigned")) {
            player.m_5661_((Component)Component.m_237115_((String)"item.traveloptics.spell_imbuing_curio.already_assigned.message").m_130940_(ChatFormatting.RED), true);
            return InteractionResultHolder.m_19098_((Object)stack);
        }
        if (player.f_36079_ < 180) {
            player.m_5661_((Component)Component.m_237115_((String)"item.traveloptics.spell_imbuing_curio.not_enough_xp.message").m_130940_(ChatFormatting.RED), true);
            return InteractionResultHolder.m_19100_((Object)stack);
        }
        Map<AbstractSpell, SpellAttributes> possibleSpells = this.getSpellAttributes();
        if (!possibleSpells.isEmpty() && (selectedSpell = this.selectWeightedRandomSpell(possibleSpells)) != null) {
            SpellAttributes attributes = possibleSpells.get(selectedSpell);
            float quality = attributes.qualityMin + new Random().nextFloat() * (attributes.qualityMax - attributes.qualityMin);
            int levelOfSpell = this.selectLevel(selectedSpell, quality, attributes.prioritizeLowerLevel);
            ISpellContainer spellContainer = ISpellContainer.create((int)1, (boolean)true, (boolean)true);
            spellContainer.addSpell(selectedSpell, levelOfSpell, true, stack);
            spellContainer.save(stack);
            tag.m_128379_("spellAssigned", true);
            player.m_6756_(-180);
            level.m_5594_(null, player.m_20183_(), this.getAssignSound(), player.m_5720_(), 1.0f, 1.0f);
            if (!level.f_46443_) {
                player.m_5661_((Component)Component.m_237110_((String)"item.traveloptics.spell_imbuing_curio.success", (Object[])new Object[]{selectedSpell.getDisplayName(null)}).m_130940_(ChatFormatting.GREEN), true);
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return InteractionResultHolder.m_19100_((Object)stack);
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = stack.m_41784_();
        if (tag.m_128441_("spellAssigned")) {
            tooltip.add(this.getAssignedHoverText());
        } else {
            tooltip.add(this.getUnassignedHoverText());
            tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.random_imbue_curio.ex_required.tooltip").m_130940_(ChatFormatting.AQUA));
        }
    }

    private AbstractSpell selectWeightedRandomSpell(Map<AbstractSpell, SpellAttributes> possibleSpells) {
        List<Map.Entry> enabledSpells = possibleSpells.entrySet().stream().filter(entry -> ((AbstractSpell)entry.getKey()).isEnabled()).toList();
        int totalWeight = enabledSpells.stream().mapToInt(entry -> ((SpellAttributes)entry.getValue()).weight).sum();
        if (totalWeight <= 0) {
            return null;
        }
        int randomWeight = new Random().nextInt(totalWeight);
        for (Map.Entry entry2 : enabledSpells) {
            if ((randomWeight -= ((SpellAttributes)entry2.getValue()).weight) >= 0) continue;
            return (AbstractSpell)entry2.getKey();
        }
        return null;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        ImmutableMultimap.Builder attributeBuilder = new ImmutableMultimap.Builder();
        for (Attribute attribute : this.attributeMap.keySet()) {
            for (AttributeModifier modifier : this.attributeMap.get((Object)attribute)) {
                attributeBuilder.put((Object)attribute, (Object)new AttributeModifier(uuid, modifier.m_22214_(), modifier.m_22218_(), modifier.m_22217_()));
            }
        }
        return attributeBuilder.build();
    }

    public static class SpellAttributes {
        public final int weight;
        public final boolean prioritizeLowerLevel;
        public final float qualityMin;
        public final float qualityMax;

        public SpellAttributes(int weight, boolean prioritizeLowerLevel, float qualityMin, float qualityMax) {
            this.weight = weight;
            this.prioritizeLowerLevel = prioritizeLowerLevel;
            this.qualityMin = qualityMin;
            this.qualityMax = qualityMax;
        }
    }
}

