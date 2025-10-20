/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.AnimationHolder
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.registries.SoundRegistry
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.gametechbc.traveloptics.spells.ender;

import com.gametechbc.traveloptics.spells.TravelopticsSpellAnimations;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

@AutoSpellConfig
public class RunicRadarSpell
extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "rune_swirl");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.EPIC).setSchoolResource(SchoolRegistry.ENDER_RESOURCE).setMaxLevel(3).setCooldownSeconds(25.0).build();

    public RunicRadarSpell() {
        this.manaCostPerLevel = 55;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 8;
        this.baseManaCost = 70;
    }

    public CastType getCastType() {
        return CastType.LONG;
    }

    public DefaultConfig getDefaultConfig() {
        return this.defaultConfig;
    }

    public ResourceLocation getSpellResource() {
        return this.spellId;
    }

    public Optional<SoundEvent> getCastStartSound() {
        return Optional.empty();
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of((SoundEvent)SoundRegistry.ELDRITCH_BLAST.get());
    }

    public AnimationHolder getCastStartAnimation() {
        return TravelopticsSpellAnimations.SHOOT_WINDUP;
    }

    public AnimationHolder getCastFinishAnimation() {
        return TravelopticsSpellAnimations.SHOOT;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.m_237110_((String)"ui.traveloptics.aoe_damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), (int)2)}), Component.m_237110_((String)"ui.traveloptics.falloff_distance", (Object[])new Object[]{Utils.stringTruncation((double)RunicRadarSpell.getRange(spellLevel, caster), (int)1)}), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }

    public void onCast(Level world, int spellLevel, LivingEntity caster, CastSource castSource, MagicData playerMagicData) {
        int standingOnY = Mth.m_14107_((double)caster.m_20186_()) - 1;
        double headY = caster.m_20186_() + 1.0;
        int numRings = 8;
        double baseRadius = 3.0;
        int baseEntitiesPerRing = 8;
        int baseWarmup = 0;
        float yawOffset = (float)Math.toRadians(caster.m_146908_());
        for (int ring = 0; ring < numRings; ++ring) {
            double radius = baseRadius + (double)ring * 1.5;
            int entitiesInRing = baseEntitiesPerRing + ring * 4;
            for (int i = 0; i < entitiesInRing; ++i) {
                double angle = Math.PI * 2 * (double)i / (double)entitiesInRing + (double)yawOffset;
                double x = caster.m_20185_() + (double)Mth.m_14089_((float)((float)angle)) * radius;
                double z = caster.m_20189_() + (double)Mth.m_14031_((float)((float)angle)) * radius;
                int warmup = baseWarmup + i * 2;
                this.spawnVoidRune(x, headY, z, standingOnY, (float)angle, warmup, world, caster);
            }
        }
        super.onCast(world, spellLevel, caster, castSource, playerMagicData);
    }

    private void spawnVoidRune(double x, double y, double z, int lowestYCheck, float yRot, int warmupDelayTicks, Level world, LivingEntity caster) {
        BlockPos blockPos = BlockPos.m_274561_((double)x, (double)y, (double)z);
        boolean positionFound = false;
        double yOffset = 0.0;
        do {
            VoxelShape shape;
            BlockPos below;
            BlockState blockState;
            if (!(blockState = world.m_8055_(below = blockPos.m_7495_())).m_60783_((BlockGetter)world, below, Direction.UP)) continue;
            if (!world.m_46859_(blockPos) && !(shape = world.m_8055_(blockPos).m_60812_((BlockGetter)world, blockPos)).m_83281_()) {
                yOffset = shape.m_83297_(Direction.Axis.Y);
            }
            positionFound = true;
            break;
        } while ((blockPos = blockPos.m_7495_()).m_123342_() >= lowestYCheck);
        if (positionFound) {
            world.m_7967_((Entity)new Void_Rune_Entity(world, x, (double)blockPos.m_123342_() + yOffset, z, yRot, warmupDelayTicks, 10.0f, caster));
        }
    }

    public static float getRange(int level, LivingEntity caster) {
        return 20.0f;
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return 13.0f + this.getSpellPower(spellLevel, (Entity)caster) * 3.0f;
    }
}

