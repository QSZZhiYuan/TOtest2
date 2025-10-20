/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Flame_Jet_Entity
 *  com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.gametechbc.traveloptics.entity.extended_projectiles;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import com.github.L_Ender.cataclysm.entity.projectile.Flame_Jet_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ExtendedFlareBombEntity
extends Flare_Bomb_Entity {
    private float flameJetDamage = 7.0f;

    public ExtendedFlareBombEntity(EntityType<? extends Flare_Bomb_Entity> type, Level world) {
        super(type, world);
    }

    public ExtendedFlareBombEntity(Level level, LivingEntity owner) {
        this((EntityType<? extends Flare_Bomb_Entity>)((EntityType)TravelopticsEntities.EXTENDED_FLARE_BOMB.get()), level);
        this.m_5602_((Entity)owner);
    }

    public float getFlameJetDamage() {
        return this.flameJetDamage;
    }

    public void setFlameJetDamage(float flameJetDamage) {
        this.flameJetDamage = flameJetDamage;
    }

    protected void m_5790_(EntityHitResult result) {
        super.m_5790_(result);
    }

    protected void m_6532_(HitResult p_37628_) {
        if (!this.m_9236_().f_46443_) {
            this.m_5496_(SoundEvents.f_11909_, 1.5f, 0.75f);
            this.m_9236_().m_7605_((Entity)this, (byte)4);
            if (this.f_19796_.m_188499_()) {
                this.extendedXStrikeRune(10, 2.0);
            } else {
                this.extendedPlusStrikeRune(10, 2.0);
            }
            this.m_146870_();
        }
    }

    public void m_8119_() {
        super.m_8119_();
    }

    private void extendedPlusStrikeRune(int rune, double time) {
        for (int i = 0; i < 4; ++i) {
            float yawRadians = (float)Math.toRadians(90.0f + this.m_146908_());
            float throwAngle = yawRadians + (float)i * (float)Math.PI / 2.0f;
            for (int k = 0; k < rune; ++k) {
                double distance = 0.8 * (double)(k + 1);
                int delay = (int)(time * (double)(k + 1));
                this.extendedSpawnFangs(this.m_20185_() + (double)Mth.m_14089_((float)throwAngle) * 1.25 * distance, this.m_20189_() + (double)Mth.m_14031_((float)throwAngle) * 1.25 * distance, this.m_20186_() - 2.0, this.m_20186_() + 2.0, throwAngle, delay);
            }
        }
    }

    private void extendedXStrikeRune(int rune, double time) {
        for (int i = 0; i < 4; ++i) {
            float yawRadians = (float)Math.toRadians(45.0f + this.m_146908_());
            float throwAngle = yawRadians + (float)i * (float)Math.PI / 2.0f;
            for (int k = 0; k < rune; ++k) {
                double distance = 0.8 * (double)(k + 1);
                int delay = (int)(time * (double)(k + 1));
                this.extendedSpawnFangs(this.m_20185_() + (double)Mth.m_14089_((float)throwAngle) * 1.25 * distance, this.m_20189_() + (double)Mth.m_14031_((float)throwAngle) * 1.25 * distance, this.m_20186_() - 2.0, this.m_20186_() + 2.0, throwAngle, delay);
            }
        }
    }

    private void extendedSpawnFangs(double x, double z, double minY, double maxY, float rotation, int delay) {
        float jetDamage = this.getFlameJetDamage();
        BlockPos blockpos = BlockPos.m_274561_((double)x, (double)maxY, (double)z);
        boolean flag = false;
        double d0 = 0.0;
        do {
            BlockState blockstate1;
            VoxelShape voxelShape;
            BlockPos blockposBelow = blockpos.m_7495_();
            BlockState blockstate = this.m_9236_().m_8055_(blockposBelow);
            if (!blockstate.m_60783_((BlockGetter)this.m_9236_(), blockposBelow, Direction.UP)) continue;
            if (!this.m_9236_().m_46859_(blockpos) && !(voxelShape = (blockstate1 = this.m_9236_().m_8055_(blockpos)).m_60812_((BlockGetter)this.m_9236_(), blockpos)).m_83281_()) {
                d0 = voxelShape.m_83297_(Direction.Axis.Y);
            }
            flag = true;
            break;
        } while ((blockpos = blockpos.m_7495_()).m_123342_() >= Mth.m_14107_((double)minY) - 1);
        if (flag) {
            LivingEntity ownerEntity = this.m_19749_() instanceof LivingEntity ? (LivingEntity)this.m_19749_() : null;
            this.m_9236_().m_7967_((Entity)new Flame_Jet_Entity(this.m_9236_(), x, (double)blockpos.m_123342_() + d0, z, rotation, delay, jetDamage, ownerEntity));
        }
    }
}

