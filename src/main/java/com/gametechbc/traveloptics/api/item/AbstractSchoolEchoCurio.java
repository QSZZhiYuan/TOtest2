/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.SpellRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.ISpellContainer
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  net.minecraft.ChatFormatting
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.registries.RegistryObject
 *  org.jetbrains.annotations.Nullable
 */
package com.gametechbc.traveloptics.api.item;

import com.gametechbc.traveloptics.api.item.curio.CurioBaseItem;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSchoolEchoCurio
extends CurioBaseItem {
    private static final int EXPERIENCE_COST = 10;

    public AbstractSchoolEchoCurio(Item.Properties properties) {
        super(properties.m_41487_(1));
    }

    protected abstract RegistryObject<SchoolType> getSchool();

    protected abstract Component getAssignedHoverText();

    protected abstract Component getUnassignedHoverText();

    protected SoundEvent getAssignSound() {
        return SoundEvents.f_11887_;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        CompoundTag tag = stack.m_41784_();
        if (tag.m_128441_("spellAssigned")) {
            player.m_5661_((Component)Component.m_237115_((String)"item.traveloptics.spell_imbuing_curio.already_assigned").m_130940_(ChatFormatting.RED), true);
            return InteractionResultHolder.m_19098_((Object)stack);
        }
        if (player.f_36079_ < 10) {
            player.m_5661_((Component)Component.m_237115_((String)"item.traveloptics.spell_imbuing_curio.not_enough_xp").m_130940_(ChatFormatting.RED), true);
            return InteractionResultHolder.m_19100_((Object)stack);
        }
        List<AbstractSpell> schoolSpells = this.getSpellsFromSchool();
        if (!schoolSpells.isEmpty()) {
            AbstractSpell selectedSpell = this.selectRandomSpell(schoolSpells);
            int levelOfSpell = 1 + new Random().nextInt(3);
            ISpellContainer spellContainer = ISpellContainer.create((int)1, (boolean)true, (boolean)true);
            spellContainer.addSpell(selectedSpell, levelOfSpell, true, stack);
            spellContainer.save(stack);
            tag.m_128379_("spellAssigned", true);
            player.m_6756_(-10);
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
        }
    }

    private List<AbstractSpell> getSpellsFromSchool() {
        SchoolType school = (SchoolType)this.getSchool().get();
        return SpellRegistry.getEnabledSpells().stream().filter(spell -> spell.getSchoolType() == school).collect(Collectors.toList());
    }

    private AbstractSpell selectRandomSpell(List<AbstractSpell> spells) {
        if (spells.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return spells.get(random.nextInt(spells.size()));
    }
}

