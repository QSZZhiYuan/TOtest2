/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.registries.EntityRegistry
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.anti_cheese;

import com.gametechbc.traveloptics.entity.mobs.nightwarden_boss.NightwardenBossEntity;
import com.gametechbc.traveloptics.entity.projectiles.supernova.supermassive_black_hole.SupermassiveBlackHoleEntity;
import com.gametechbc.traveloptics.init.TravelopticsEntities;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class NightwardenAntiCCHandler {
    private static final Map<EntityType<?>, Vec3> CHECK_ENTITIES = Map.of((EntityType)EntityRegistry.ROOT.get(), new Vec3(5.0, 2.5, 2.5), (EntityType)EntityRegistry.BLACK_HOLE.get(), new Vec3(25.0, 12.5, 12.5), (EntityType)TravelopticsEntities.SUPERMASSIVE_BLACK_HOLE.get(), new Vec3(25.0, 12.5, 12.5), (EntityType)TravelopticsEntities.AQUA_VORTEX_ENTITY.get(), new Vec3(20.0, 10.0, 10.0), (EntityType)EntityRegistry.SCULK_TENTACLE.get(), new Vec3(15.0, 7.5, 7.5));

    public static void tick(NightwardenBossEntity boss) {
        if (boss.m_9236_().f_46443_ || boss.f_19797_ % 60 != 0) {
            return;
        }
        Level level = boss.m_9236_();
        BlockPos pos = boss.m_20183_();
        for (Map.Entry<EntityType<?>, Vec3> entry : CHECK_ENTITIES.entrySet()) {
            EntityType<?> type = entry.getKey();
            Vec3 range = entry.getValue();
            AABB box = new AABB((double)pos.m_123341_() - range.f_82479_, (double)pos.m_123342_() - range.f_82481_, (double)pos.m_123343_() - range.f_82479_, (double)pos.m_123341_() + range.f_82479_, (double)pos.m_123342_() + range.f_82480_, (double)pos.m_123343_() + range.f_82479_);
            List entities = level.m_6443_(Entity.class, box, e -> e.m_6095_() == type);
            for (Entity e2 : entities) {
                if (type == TravelopticsEntities.SUPERMASSIVE_BLACK_HOLE.get()) {
                    SupermassiveBlackHoleEntity bh;
                    if (!(e2 instanceof SupermassiveBlackHoleEntity) || (bh = (SupermassiveBlackHoleEntity)e2).m_19749_() == boss) continue;
                    e2.m_146870_();
                    continue;
                }
                e2.m_146870_();
            }
        }
    }
}

