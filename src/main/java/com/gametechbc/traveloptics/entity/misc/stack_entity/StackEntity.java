/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.network.NetworkHooks
 */
package com.gametechbc.traveloptics.entity.misc.stack_entity;

import com.gametechbc.traveloptics.init.TravelopticsEntities;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

public class StackEntity
extends Entity {
    private static final EntityDataAccessor<Integer> STACK_COUNT = SynchedEntityData.m_135353_(StackEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<String> STACK_NAME = SynchedEntityData.m_135353_(StackEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135030_);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.m_135353_(StackEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Optional<UUID>> TARGET_UUID = SynchedEntityData.m_135353_(StackEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135041_);
    private static final EntityDataAccessor<Float> Y_OFFSET = SynchedEntityData.m_135353_(StackEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private Entity cachedTarget = null;
    private int lastUpdateTick = -1;

    public StackEntity(EntityType<?> type, Level world) {
        super(type, world);
        this.f_19811_ = true;
    }

    public StackEntity(Level world, Entity target, String stackName, int initialStacks, int color, float yOffset) {
        this((EntityType)TravelopticsEntities.STACK.get(), world);
        this.setTargetUUID(target.m_20148_());
        this.setStackName(stackName);
        this.setStackCount(initialStacks);
        this.setColor(color);
        this.setYOffset(yOffset);
        this.updatePosition(target);
        this.cachedTarget = target;
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(STACK_COUNT, (Object)1);
        this.f_19804_.m_135372_(STACK_NAME, (Object)"");
        this.f_19804_.m_135372_(COLOR, (Object)0xFFFFFF);
        this.f_19804_.m_135372_(TARGET_UUID, Optional.empty());
        this.f_19804_.m_135372_(Y_OFFSET, (Object)Float.valueOf(0.5f));
    }

    public void m_8119_() {
        super.m_8119_();
        boolean shouldUpdate = this.f_19797_ - this.lastUpdateTick >= 1;
        Entity target = this.getTargetEntity();
        if (target == null || target.m_213877_()) {
            this.m_146870_();
            return;
        }
        if (shouldUpdate) {
            this.updatePosition(target);
            this.lastUpdateTick = this.f_19797_;
        }
        if (this.getStackCount() <= 0) {
            this.m_146870_();
        }
    }

    private void updatePosition(Entity target) {
        double x = target.m_20185_();
        double y = target.m_20186_() + (double)target.m_20206_() + (double)this.getYOffset();
        double z = target.m_20189_();
        this.m_6034_(x, y, z);
        this.cachedTarget = target;
    }

    @Nullable
    private Entity getTargetEntity() {
        if (this.cachedTarget != null && !this.cachedTarget.m_213877_()) {
            return this.cachedTarget;
        }
        Optional<UUID> targetUUID = this.getTargetUUID();
        if (targetUUID.isPresent()) {
            Level level = this.m_9236_();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                Entity entity = serverLevel.m_8791_(targetUUID.get());
                if (entity != null) {
                    this.cachedTarget = entity;
                    return entity;
                }
            } else {
                for (Entity entity : this.m_9236_().m_45976_(Entity.class, this.m_20191_().m_82400_(32.0))) {
                    if (!entity.m_20148_().equals(targetUUID.get())) continue;
                    this.cachedTarget = entity;
                    return entity;
                }
            }
        }
        this.cachedTarget = null;
        return null;
    }

    public void addStacks(int amount) {
        this.setStackCount(Math.min(this.getStackCount() + amount, 999));
    }

    public void removeStacks(int amount) {
        this.setStackCount(Math.max(this.getStackCount() - amount, 0));
    }

    public void setMaxStacks() {
        this.setStackCount(1000);
    }

    public int getStackCount() {
        return (Integer)this.f_19804_.m_135370_(STACK_COUNT);
    }

    public void setStackCount(int count) {
        this.f_19804_.m_135381_(STACK_COUNT, (Object)Math.max(0, Math.min(count, 1000)));
    }

    public String getStackName() {
        return (String)this.f_19804_.m_135370_(STACK_NAME);
    }

    public void setStackName(String name) {
        this.f_19804_.m_135381_(STACK_NAME, (Object)name);
    }

    public int getColor() {
        return (Integer)this.f_19804_.m_135370_(COLOR);
    }

    public void setColor(int color) {
        this.f_19804_.m_135381_(COLOR, (Object)color);
    }

    public Optional<UUID> getTargetUUID() {
        return (Optional)this.f_19804_.m_135370_(TARGET_UUID);
    }

    public void setTargetUUID(UUID uuid) {
        this.f_19804_.m_135381_(TARGET_UUID, Optional.of(uuid));
    }

    public float getYOffset() {
        return ((Float)this.f_19804_.m_135370_(Y_OFFSET)).floatValue();
    }

    public void setYOffset(float offset) {
        this.f_19804_.m_135381_(Y_OFFSET, (Object)Float.valueOf(offset));
    }

    public boolean m_6783_(double distance) {
        return distance < 4096.0;
    }

    public boolean m_6094_() {
        return false;
    }

    public boolean m_5829_() {
        return false;
    }

    public boolean m_6087_() {
        return false;
    }

    protected void m_7378_(CompoundTag tag) {
        this.setStackCount(tag.m_128451_("StackCount"));
        this.setStackName(tag.m_128461_("StackName"));
        this.setColor(tag.m_128451_("Color"));
        this.setYOffset(tag.m_128457_("YOffset"));
        if (tag.m_128403_("TargetUUID")) {
            this.setTargetUUID(tag.m_128342_("TargetUUID"));
        }
        this.lastUpdateTick = tag.m_128451_("LastUpdateTick");
    }

    protected void m_7380_(CompoundTag tag) {
        tag.m_128405_("StackCount", this.getStackCount());
        tag.m_128359_("StackName", this.getStackName());
        tag.m_128405_("Color", this.getColor());
        tag.m_128350_("YOffset", this.getYOffset());
        tag.m_128405_("LastUpdateTick", this.lastUpdateTick);
        this.getTargetUUID().ifPresent(uuid -> tag.m_128362_("TargetUUID", uuid));
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

    public static void addStacksToEntity(Entity target, String stackName, int amount, int color, float yOffset) {
        if (target.m_9236_().f_46443_) {
            return;
        }
        StackEntity existing = StackEntity.findStackEntityByTarget(target, stackName);
        if (existing != null) {
            existing.addStacks(amount);
        } else {
            StackEntity newStack = new StackEntity(target.m_9236_(), target, stackName, amount, color, yOffset);
            target.m_9236_().m_7967_((Entity)newStack);
        }
    }

    public static boolean removeStacksFromEntity(Entity target, String stackName, int amount) {
        StackEntity existing = StackEntity.findStackEntityByTarget(target, stackName);
        if (existing != null) {
            existing.removeStacks(amount);
            return true;
        }
        return false;
    }

    public static int getStackCountFromEntity(Entity target, String stackName) {
        StackEntity existing = StackEntity.findStackEntityByTarget(target, stackName);
        return existing != null ? existing.getStackCount() : 0;
    }

    public static boolean clearStacksFromEntity(Entity target, String stackName) {
        StackEntity existing = StackEntity.findStackEntityByTarget(target, stackName);
        if (existing != null) {
            existing.m_146870_();
            return true;
        }
        return false;
    }

    public static List<Entity> scanForStackedEntities(Level world, double centerX, double centerY, double centerZ, double radius, String stackName) {
        if (world.f_46443_) {
            return new ArrayList<Entity>();
        }
        AABB scanArea = new AABB(centerX - radius, centerY - radius, centerZ - radius, centerX + radius, centerY + radius, centerZ + radius);
        ArrayList<Entity> stackedEntities = new ArrayList<Entity>();
        List stackEntities = world.m_45976_(StackEntity.class, scanArea);
        for (StackEntity stackEntity : stackEntities) {
            double distanceSquared;
            Entity targetEntity;
            if (!stackEntity.getStackName().equals(stackName) || stackEntity.getStackCount() <= 0 || (targetEntity = stackEntity.getTargetEntity()) == null || targetEntity.m_213877_() || !((distanceSquared = targetEntity.m_20275_(centerX, centerY, centerZ)) <= radius * radius)) continue;
            stackedEntities.add(targetEntity);
        }
        return stackedEntities;
    }

    public static List<StackScanResult> scanForStackedEntitiesDetailed(Level world, double centerX, double centerY, double centerZ, double radius, String stackName) {
        if (world.f_46443_) {
            return new ArrayList<StackScanResult>();
        }
        AABB scanArea = new AABB(centerX - radius, centerY - radius, centerZ - radius, centerX + radius, centerY + radius, centerZ + radius);
        ArrayList<StackScanResult> results = new ArrayList<StackScanResult>();
        List stackEntities = world.m_45976_(StackEntity.class, scanArea);
        for (StackEntity stackEntity : stackEntities) {
            double distanceSquared;
            Entity targetEntity;
            if (!stackEntity.getStackName().equals(stackName) || stackEntity.getStackCount() <= 0 || (targetEntity = stackEntity.getTargetEntity()) == null || targetEntity.m_213877_() || !((distanceSquared = targetEntity.m_20275_(centerX, centerY, centerZ)) <= radius * radius)) continue;
            results.add(new StackScanResult(targetEntity, stackEntity.getStackCount(), Math.sqrt(distanceSquared), stackEntity));
        }
        return results;
    }

    private static StackEntity findStackEntityByTarget(Entity target, String stackName) {
        AABB searchBox = new AABB(target.m_20185_() - 2.0, target.m_20186_() - 1.0, target.m_20189_() - 2.0, target.m_20185_() + 2.0, target.m_20186_() + 5.0, target.m_20189_() + 2.0);
        List stacks = target.m_9236_().m_45976_(StackEntity.class, searchBox);
        return stacks.stream().filter(stack -> stack.getTargetUUID().isPresent()).filter(stack -> stack.getTargetUUID().get().equals(target.m_20148_())).filter(stack -> stack.getStackName().equals(stackName)).findFirst().orElse(null);
    }

    public static class StackScanResult {
        private final Entity entity;
        private final int stackCount;
        private final double distance;
        private final StackEntity stackEntity;

        public StackScanResult(Entity entity, int stackCount, double distance, StackEntity stackEntity) {
            this.entity = entity;
            this.stackCount = stackCount;
            this.distance = distance;
            this.stackEntity = stackEntity;
        }

        public Entity getEntity() {
            return this.entity;
        }

        public int getStackCount() {
            return this.stackCount;
        }

        public double getDistance() {
            return this.distance;
        }

        public StackEntity getStackEntity() {
            return this.stackEntity;
        }
    }
}

