/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.api.utils;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TOEntityUtils {
    public static boolean isValidTarget(LivingEntity entity) {
        return entity != null && entity.m_6084_() && !entity.m_213877_();
    }

    public static double getEntitySpeed(LivingEntity entity) {
        return entity.m_21133_(Attributes.f_22279_);
    }

    public static boolean isMobTargetingPlayer(Mob mob, Player player) {
        return mob.m_5448_() == player;
    }

    public static boolean isMoving(Entity entity) {
        Vec3 velocity = entity.m_20184_();
        return velocity.f_82479_ != 0.0 || velocity.f_82480_ != 0.0 || velocity.f_82481_ != 0.0;
    }

    public static void pullEntityTowards(Entity entity, Vec3 target, double speed) {
        Vec3 motion = target.m_82546_(entity.m_20182_()).m_82541_().m_82490_(speed);
        entity.m_20256_(motion);
    }

    public static LivingEntity findClosestHostile(LivingEntity entity, double range) {
        AABB boundingBox = entity.m_20191_().m_82400_(range);
        List entities = entity.m_9236_().m_6443_(LivingEntity.class, boundingBox, target -> target != entity && target.m_6084_() && !target.m_7307_((Entity)entity));
        return entities.stream().min(Comparator.comparingDouble(arg_0 -> ((LivingEntity)entity).m_20280_(arg_0))).orElse(null);
    }

    public static <T extends Entity> List<T> getEntitiesInRange(Entity entity, Class<T> entityType, double range) {
        AABB boundingBox = entity.m_20191_().m_82400_(range);
        return entity.m_9236_().m_45976_(entityType, boundingBox);
    }

    public static List<LivingEntity> getEntitiesMatching(Entity entity, double range, Predicate<LivingEntity> filter) {
        AABB boundingBox = entity.m_20191_().m_82400_(range);
        return entity.m_9236_().m_6443_(LivingEntity.class, boundingBox, filter);
    }

    public static LivingEntity findStrongestEntity(Entity entity, double range) {
        AABB boundingBox = entity.m_20191_().m_82400_(range);
        return entity.m_9236_().m_45976_(LivingEntity.class, boundingBox).stream().max(Comparator.comparingDouble(LivingEntity::m_21223_)).orElse(null);
    }

    public static LivingEntity findWeakestEntity(Entity entity, double range) {
        AABB boundingBox = entity.m_20191_().m_82400_(range);
        return entity.m_9236_().m_45976_(LivingEntity.class, boundingBox).stream().min(Comparator.comparingDouble(LivingEntity::m_21223_)).orElse(null);
    }
}

