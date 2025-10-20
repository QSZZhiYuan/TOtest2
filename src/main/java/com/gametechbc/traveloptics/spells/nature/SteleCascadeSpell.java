/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Ancient_Desert_Stele_Entity
 *  com.github.L_Ender.cataclysm.init.ModSounds
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.spells.nature;

import com.github.L_Ender.cataclysm.entity.projectile.Ancient_Desert_Stele_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class SteleCascadeSpell
extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "stele_cascade");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.COMMON).setSchoolResource(SchoolRegistry.NATURE_RESOURCE).setMaxLevel(6).setCooldownSeconds(22.0).build();

    public SteleCascadeSpell() {
        this.manaCostPerLevel = 25;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 20;
        this.baseManaCost = 35;
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
        return Optional.of((SoundEvent)ModSounds.REMNANT_TAIL_SWING.get());
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.empty();
    }

    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (entity.m_6047_()) {
            this.spawnDesertSteleRing(entity, world, spellLevel);
        } else {
            this.spawnDesertSteleLine(entity, world, spellLevel);
        }
        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnDesertSteleRing(LivingEntity caster, Level world, int spellLevel) {
        int numRings = this.getRingsAndRows(spellLevel);
        int initialNumTargetsPerRing = 10;
        int ringGrowth = 8;
        int circleRadius = 3;
        int gapBetweenRings = 1;
        int damage = (int)this.getDamage(spellLevel, caster);
        double centerX = caster.m_20185_();
        double centerZ = caster.m_20189_();
        double initialYOffset = 8.0;
        double yOffsetIncrement = 2.0;
        for (int ring = 0; ring < numRings; ++ring) {
            int numTargetsPerRing = initialNumTargetsPerRing + ring * ringGrowth;
            int currentRadius = circleRadius + ring * (gapBetweenRings + 1);
            double yOffset = initialYOffset + (double)ring * yOffsetIncrement;
            double angleOffset = ring % 2 == 0 ? 0.0 : 360.0 / (double)(2 * numTargetsPerRing);
            for (int i = 0; i < numTargetsPerRing; ++i) {
                double angle = Math.toRadians((double)i * 360.0 / (double)numTargetsPerRing + angleOffset);
                double offsetX = (double)currentRadius * Math.cos(angle);
                double offsetZ = (double)currentRadius * Math.sin(angle);
                double spawnX = centerX + offsetX;
                double spawnZ = centerZ + offsetZ;
                double spawnY = caster.m_20186_() + yOffset;
                Vec3 spawnPos = new Vec3(spawnX, spawnY, spawnZ);
                this.spawnDesertSteleAtPosition(spawnPos, world, damage, caster);
            }
        }
    }

    private void spawnDesertSteleLine(LivingEntity caster, Level world, int spellLevel) {
        int numRows = this.getRingsAndRows(spellLevel) * 2;
        int initialNumColumns = 6;
        int rowGrowth = 2;
        int columnSpacing = 2;
        int rowSpacing = 2;
        double yOffsetIncrement = 2.0;
        double initialRowOffset = 3.0;
        int damage = (int)this.getDamage(spellLevel, caster);
        Vec3 lookDirection = new Vec3(caster.m_20154_().f_82479_, 0.0, caster.m_20154_().f_82481_).m_82541_();
        Vec3 rightDirection = lookDirection.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
        double baseX = caster.m_20185_();
        double baseY = caster.m_20186_() + 8.0;
        double baseZ = caster.m_20189_();
        for (int row = 0; row < numRows; ++row) {
            int numColumns = initialNumColumns + row * rowGrowth;
            double halfRowWidth = (double)((numColumns - 1) * columnSpacing) / 2.0;
            double rowOffsetX = (initialRowOffset + (double)(row * rowSpacing)) * lookDirection.f_82479_;
            double rowOffsetZ = (initialRowOffset + (double)(row * rowSpacing)) * lookDirection.f_82481_;
            double rowYOffset = baseY + (double)row * yOffsetIncrement;
            for (int column = 0; column < numColumns; ++column) {
                double columnOffsetX = ((double)(column * columnSpacing) - halfRowWidth) * rightDirection.f_82479_;
                double columnOffsetZ = ((double)(column * columnSpacing) - halfRowWidth) * rightDirection.f_82481_;
                double spawnX = baseX + rowOffsetX + columnOffsetX;
                double spawnY = rowYOffset;
                double spawnZ = baseZ + rowOffsetZ + columnOffsetZ;
                Vec3 spawnPos = new Vec3(spawnX, spawnY, spawnZ);
                this.spawnDesertSteleAtPosition(spawnPos, world, damage, caster);
            }
        }
    }

    private void spawnDesertSteleAtPosition(Vec3 pos, Level world, float damage, LivingEntity caster) {
        Ancient_Desert_Stele_Entity desertStele = new Ancient_Desert_Stele_Entity(world, pos.f_82479_, pos.f_82480_, pos.f_82481_, caster.m_146908_(), 10, damage, caster);
        desertStele.setDamage(damage);
        desertStele.setCaster(caster);
        world.m_7967_((Entity)desertStele);
    }

    private int getRingsAndRows(int spellLevel) {
        return spellLevel;
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return 4.0f + this.getSpellPower(spellLevel, (Entity)caster) * 4.0f;
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.m_237110_((String)"ui.traveloptics.damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), (int)2)}), Component.m_237110_((String)"ui.traveloptics.rings", (Object[])new Object[]{this.getRingsAndRows(spellLevel)}), Component.m_237110_((String)"ui.traveloptics.row_count", (Object[])new Object[]{this.getRingsAndRows(spellLevel) * 2}), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }
}

