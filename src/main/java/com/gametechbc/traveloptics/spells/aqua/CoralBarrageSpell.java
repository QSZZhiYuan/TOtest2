/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.config.DefaultConfig
 *  io.redspace.ironsspellbooks.api.magic.MagicData
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.AutoSpellConfig
 *  io.redspace.ironsspellbooks.api.spells.CastSource
 *  io.redspace.ironsspellbooks.api.spells.CastType
 *  io.redspace.ironsspellbooks.api.spells.ICastData
 *  io.redspace.ironsspellbooks.api.spells.ICastDataSerializable
 *  io.redspace.ironsspellbooks.api.spells.SpellRarity
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  io.redspace.ironsspellbooks.capabilities.magic.MultiTargetEntityCastData
 *  io.redspace.ironsspellbooks.capabilities.magic.PlayerRecasts
 *  io.redspace.ironsspellbooks.capabilities.magic.RecastInstance
 *  io.redspace.ironsspellbooks.capabilities.magic.RecastResult
 *  io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.gametechbc.traveloptics.spells.aqua;

import com.gametechbc.traveloptics.api.init.TravelopticsSchools;
import com.gametechbc.traveloptics.entity.projectiles.coral_bolt.BlueCoralBoltProjectile;
import com.gametechbc.traveloptics.entity.projectiles.coral_bolt.PinkCoralBoltProjectile;
import com.gametechbc.traveloptics.entity.projectiles.coral_bolt.RedCoralBoltProjectile;
import com.gametechbc.traveloptics.entity.projectiles.coral_bolt.YellowCoralBoltProjectile;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.ICastData;
import io.redspace.ironsspellbooks.api.spells.ICastDataSerializable;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MultiTargetEntityCastData;
import io.redspace.ironsspellbooks.capabilities.magic.PlayerRecasts;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@AutoSpellConfig
public class CoralBarrageSpell
extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation("traveloptics", "coral_barrage");
    private final DefaultConfig defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.RARE).setSchoolResource(TravelopticsSchools.AQUA_RESOURCE).setMaxLevel(5).setCooldownSeconds(16.0).build();

    public CoralBarrageSpell() {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 3;
        this.spellPowerPerLevel = 2;
        this.castTime = 0;
        this.baseManaCost = 80;
    }

    public CastType getCastType() {
        return CastType.INSTANT;
    }

    public DefaultConfig getDefaultConfig() {
        return this.defaultConfig;
    }

    public ResourceLocation getSpellResource() {
        return this.spellId;
    }

    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return 5;
    }

    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper((Level)level, (LivingEntity)entity, (MagicData)playerMagicData, (AbstractSpell)this, (int)64, (float)0.15f);
    }

    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        ICastData iCastData = playerMagicData.getAdditionalCastData();
        if (iCastData instanceof TargetEntityCastData) {
            TargetEntityCastData targetEntityCastData = (TargetEntityCastData)iCastData;
            PlayerRecasts recasts = playerMagicData.getPlayerRecasts();
            if (!recasts.hasRecastForSpell(this.getSpellId())) {
                recasts.addRecast(new RecastInstance(this.getSpellId(), spellLevel, this.getRecastCount(spellLevel, entity), 80, castSource, (ICastDataSerializable)new MultiTargetEntityCastData(new LivingEntity[]{targetEntityCastData.getTarget((ServerLevel)level)})), playerMagicData);
            } else {
                ICastDataSerializable iCastDataSerializable;
                RecastInstance instance = recasts.getRecastInstance(this.getSpellId());
                if (instance != null && (iCastDataSerializable = instance.getCastData()) instanceof MultiTargetEntityCastData) {
                    MultiTargetEntityCastData targetingData = (MultiTargetEntityCastData)iCastDataSerializable;
                    targetingData.addTarget(targetEntityCastData.getTargetUUID());
                }
            }
        }
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
        Level level = serverPlayer.m_9236_();
        Vec3 origin = serverPlayer.m_146892_().m_82549_(serverPlayer.m_20156_().m_82541_().m_82490_((double)0.2f));
        level.m_6263_(null, origin.f_82479_, origin.f_82480_, origin.f_82481_, (SoundEvent)TravelopticsSounds.AQUA_MISSILES_CAST.get(), SoundSource.PLAYERS, 2.0f, 1.4f);
        if (castDataSerializable instanceof MultiTargetEntityCastData) {
            MultiTargetEntityCastData targetingData = (MultiTargetEntityCastData)castDataSerializable;
            targetingData.getTargets().forEach(uuid -> {
                LivingEntity target = (LivingEntity)((ServerLevel)serverPlayer.m_9236_()).m_8791_(uuid);
                if (target != null) {
                    switch (level.f_46441_.m_188503_(4)) {
                        case 0: {
                            BlueCoralBoltProjectile coralBolt = new BlueCoralBoltProjectile(level, (LivingEntity)serverPlayer);
                            coralBolt.m_146884_(origin.m_82492_(0.0, (double)coralBolt.m_20206_(), 0.0));
                            Vec3 vec = target.m_20191_().m_82399_().m_82546_(serverPlayer.m_146892_()).m_82541_();
                            float inaccuracy = (float)Mth.m_14085_((double)0.2f, (double)1.4f, (double)(target.m_20182_().m_82557_(serverPlayer.m_20182_()) / 1024.0));
                            coralBolt.shoot(vec.m_82490_(0.75), inaccuracy);
                            coralBolt.setDamage(this.getDamage(recastInstance.getSpellLevel(), (LivingEntity)serverPlayer));
                            coralBolt.setHomingTarget(target);
                            level.m_7967_((Entity)coralBolt);
                            break;
                        }
                        case 1: {
                            PinkCoralBoltProjectile coralBolt = new PinkCoralBoltProjectile(level, (LivingEntity)serverPlayer);
                            coralBolt.m_146884_(origin.m_82492_(0.0, (double)coralBolt.m_20206_(), 0.0));
                            Vec3 vec = target.m_20191_().m_82399_().m_82546_(serverPlayer.m_146892_()).m_82541_();
                            float inaccuracy = (float)Mth.m_14085_((double)0.2f, (double)1.4f, (double)(target.m_20182_().m_82557_(serverPlayer.m_20182_()) / 1024.0));
                            coralBolt.shoot(vec.m_82490_(0.75), inaccuracy);
                            coralBolt.setDamage(this.getDamage(recastInstance.getSpellLevel(), (LivingEntity)serverPlayer));
                            coralBolt.setHomingTarget(target);
                            level.m_7967_((Entity)coralBolt);
                            break;
                        }
                        case 2: {
                            YellowCoralBoltProjectile coralBolt = new YellowCoralBoltProjectile(level, (LivingEntity)serverPlayer);
                            coralBolt.m_146884_(origin.m_82492_(0.0, (double)coralBolt.m_20206_(), 0.0));
                            Vec3 vec = target.m_20191_().m_82399_().m_82546_(serverPlayer.m_146892_()).m_82541_();
                            float inaccuracy = (float)Mth.m_14085_((double)0.2f, (double)1.4f, (double)(target.m_20182_().m_82557_(serverPlayer.m_20182_()) / 1024.0));
                            coralBolt.shoot(vec.m_82490_(0.75), inaccuracy);
                            coralBolt.setDamage(this.getDamage(recastInstance.getSpellLevel(), (LivingEntity)serverPlayer));
                            coralBolt.setHomingTarget(target);
                            level.m_7967_((Entity)coralBolt);
                            break;
                        }
                        case 3: {
                            RedCoralBoltProjectile coralBolt = new RedCoralBoltProjectile(level, (LivingEntity)serverPlayer);
                            coralBolt.m_146884_(origin.m_82492_(0.0, (double)coralBolt.m_20206_(), 0.0));
                            Vec3 vec = target.m_20191_().m_82399_().m_82546_(serverPlayer.m_146892_()).m_82541_();
                            float inaccuracy = (float)Mth.m_14085_((double)0.2f, (double)1.4f, (double)(target.m_20182_().m_82557_(serverPlayer.m_20182_()) / 1024.0));
                            coralBolt.shoot(vec.m_82490_(0.75), inaccuracy);
                            coralBolt.setDamage(this.getDamage(recastInstance.getSpellLevel(), (LivingEntity)serverPlayer));
                            coralBolt.setHomingTarget(target);
                            level.m_7967_((Entity)coralBolt);
                        }
                    }
                }
            });
        }
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return 1.0f + this.getSpellPower(spellLevel, (Entity)caster);
    }

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.m_237110_((String)"ui.irons_spellbooks.damage", (Object[])new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), (int)2)}), Component.m_237110_((String)"ui.irons_spellbooks.projectile_count", (Object[])new Object[]{this.getRecastCount(spellLevel, caster)}), Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
    }

    public ICastDataSerializable getEmptyCastData() {
        return new MultiTargetEntityCastData(new LivingEntity[0]);
    }
}

