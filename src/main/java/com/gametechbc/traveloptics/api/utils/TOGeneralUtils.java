/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.util.Utils
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Vec3i
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.event.ForgeEventFactory
 *  org.joml.Vector3f
 */
package com.gametechbc.traveloptics.api.utils;

import io.redspace.ironsspellbooks.api.util.Utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import org.joml.Vector3f;

public class TOGeneralUtils {
    private static final Random RANDOM = new Random();

    public static List<MutableComponent> getWetEffectTooltip(int amplifier) {
        float lightningShred = (float)(amplifier + 1) * 5.0f;
        float iceShred = (float)(amplifier + 1) * 2.5f;
        return List.of(Component.m_237110_((String)"ui.traveloptics.applies_wet_amp", (Object[])new Object[]{amplifier}), Component.m_237110_((String)"ui.traveloptics.lightning_shred", (Object[])new Object[]{Utils.stringTruncation((double)lightningShred, (int)2)}), Component.m_237110_((String)"ui.traveloptics.ice_shred", (Object[])new Object[]{Utils.stringTruncation((double)iceShred, (int)2)}));
    }

    public static List<MutableComponent> buildAquaSpellInfo(@Nullable Integer wetAmp, boolean includeFooter, MutableComponent ... coreStats) {
        ArrayList<MutableComponent> tooltip = new ArrayList<MutableComponent>(List.of(coreStats));
        if (wetAmp != null) {
            tooltip.addAll(TOGeneralUtils.getWetEffectTooltip(wetAmp));
        }
        if (includeFooter) {
            tooltip.add(Component.m_237113_((String)"\u00a79T.O Magic 'n Extras"));
        }
        return tooltip;
    }

    public static LivingEntity findNearestTargetIn3D(Level level, LivingEntity caster, double range, boolean ignoreAllies, boolean ignoreTamed, boolean randomSelection) {
        AABB boundingBox = new AABB(caster.m_20185_() - range, caster.m_20186_() - range, caster.m_20189_() - range, caster.m_20185_() + range, caster.m_20186_() + range, caster.m_20189_() + range);
        List possibleTargets = level.m_6443_(LivingEntity.class, boundingBox, entity -> {
            TamableAnimal tamable;
            if (entity == caster) {
                return false;
            }
            if (ignoreAllies && caster.m_7307_((Entity)entity)) {
                return false;
            }
            return !ignoreTamed || !(entity instanceof TamableAnimal) || !(tamable = (TamableAnimal)entity).m_21824_() || tamable.m_269323_() != caster;
        });
        if (possibleTargets.isEmpty()) {
            return null;
        }
        return randomSelection ? (LivingEntity)possibleTargets.get(RANDOM.nextInt(possibleTargets.size())) : (LivingEntity)possibleTargets.stream().min(Comparator.comparingDouble(arg_0 -> ((LivingEntity)caster).m_20280_(arg_0))).orElse(null);
    }

    public static void applyFlightBoost(Player player, double boostScale, double targetSpeed, boolean normalizeSpeed, boolean hurtMarked) {
        Vec3 motion = player.m_20184_();
        Vec3 boost = player.m_20154_().m_82490_(boostScale);
        Vec3 newMotion = motion.m_82549_(boost);
        if (normalizeSpeed && newMotion.m_82553_() > targetSpeed) {
            newMotion = newMotion.m_82541_().m_82490_(targetSpeed);
        }
        player.m_20256_(newMotion);
        player.f_19864_ = hurtMarked;
    }

    public static void applyFlightSpeedLimit(Player player, double targetSpeed, boolean normalizeSpeed, boolean hurtMarked) {
        Vec3 motion = player.m_20184_();
        if (normalizeSpeed && motion.m_82553_() > targetSpeed) {
            motion = motion.m_82541_().m_82490_(targetSpeed);
        }
        player.m_20256_(motion);
        player.f_19864_ = hurtMarked;
    }

    public static void applySpin(Entity entity, float spinSpeed) {
        entity.m_146922_(entity.m_146908_() + spinSpeed);
    }

    public static void maintainHeight(Entity entity, double targetHeight, double hoverStrength) {
        double currentHeight = entity.m_20186_();
        double difference = targetHeight - currentHeight;
        if (Math.abs(difference) > 0.1) {
            entity.m_20256_(entity.m_20184_().m_82520_(0.0, difference * hoverStrength, 0.0));
        }
    }

    public static void applyKnockback(Entity entity, Vec3 direction, double strength, boolean hurtMarked) {
        Vec3 knockback = direction.m_82541_().m_82490_(strength);
        entity.m_20256_(knockback);
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            livingEntity.f_19864_ = hurtMarked;
        }
    }

    public static double getSquaredDistance(Entity entity1, Entity entity2) {
        return entity1.m_20182_().m_82557_(entity2.m_20182_());
    }

    public static void applyDirectionalBoost(Entity entity, double strength, boolean hurtMarked) {
        Vec3 lookDirection = entity.m_20154_().m_82541_().m_82490_(strength);
        entity.m_20256_(lookDirection);
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            livingEntity.f_19864_ = hurtMarked;
        }
    }

    public static void applyFriction(Entity entity, double friction) {
        Vec3 velocity = entity.m_20184_().m_82490_(friction);
        entity.m_20256_(velocity);
    }

    public static void instantKill(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            livingEntity.m_6469_(entity.m_269291_().m_269425_(), Float.MAX_VALUE);
        } else {
            entity.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    public static boolean isAirborne(Entity entity) {
        return !entity.m_20096_() && !entity.m_20069_();
    }

    public static void forceDropHeldItem(LivingEntity entity) {
        for (ItemStack stack : entity.m_6167_()) {
            entity.m_19983_(stack);
        }
        entity.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
        entity.m_21008_(InteractionHand.OFF_HAND, ItemStack.f_41583_);
    }

    public static void doHealingPercentage(LivingEntity livingEntity, float percent) {
        float healAmount = livingEntity.m_21233_() * (percent / 100.0f);
        livingEntity.m_5634_(healAmount);
    }

    public static void reduceFallDamage(LivingEntity livingEntity, float reductionFactor) {
        Vec3 motion = livingEntity.m_20184_();
        livingEntity.m_20334_(motion.f_82479_, motion.f_82480_ * (double)(1.0f - reductionFactor), motion.f_82481_);
    }

    public static void extinguishFire(LivingEntity livingEntity) {
        if (livingEntity.m_6060_()) {
            livingEntity.m_20095_();
        }
    }

    public static boolean isBelowHealthThreshold(LivingEntity livingEntity, float percent) {
        return livingEntity.m_21223_() / livingEntity.m_21233_() <= percent / 100.0f;
    }

    public static void notifyPlayersInRange(Entity source, Component message, double radius) {
        if (source.m_9236_().f_46443_) {
            return;
        }
        source.m_9236_().m_45976_(ServerPlayer.class, source.m_20191_().m_82400_(radius)).forEach(player -> player.f_8906_.m_9829_((Packet)new ClientboundSetActionBarTextPacket(message)));
    }

    public static boolean isPlayerActuallyBlocking(LivingEntity target, LivingEntity attacker) {
        Vec3 attackDirection;
        Player player;
        if (!(target instanceof Player) || !(player = (Player)target).m_21254_()) {
            return false;
        }
        Vec3 playerLookAngle = player.m_20154_();
        double dotProduct = playerLookAngle.m_82526_(attackDirection = attacker.m_20182_().m_82546_(player.m_20182_()).m_82541_());
        return dotProduct > -0.3;
    }

    public static boolean spawnEntityOnGround(Level level, double x, double z, double minY, double maxY, Function<Vec3, Entity> entityFactory) {
        Vec3 spawnPos;
        Entity entity;
        BlockPos pos = BlockPos.m_274561_((double)x, (double)maxY, (double)z);
        boolean foundGround = false;
        double yOffset = 0.0;
        do {
            VoxelShape shape;
            BlockPos below;
            if (!level.m_8055_(below = pos.m_7495_()).m_60783_((BlockGetter)level, below, Direction.UP)) continue;
            if (!level.m_46859_(pos) && !(shape = level.m_8055_(pos).m_60812_((BlockGetter)level, pos)).m_83281_()) {
                yOffset = shape.m_83297_(Direction.Axis.Y);
            }
            foundGround = true;
            break;
        } while ((pos = pos.m_7495_()).m_123342_() >= Mth.m_14107_((double)minY) - 1);
        if (foundGround && (entity = entityFactory.apply(spawnPos = new Vec3(x, (double)pos.m_123342_() + yOffset, z))) != null) {
            level.m_7967_(entity);
            return true;
        }
        return false;
    }

    public boolean isAlly(LivingEntity owner, LivingEntity target) {
        return owner.m_5647_() != null && owner.m_5647_().m_83536_(target.m_5647_());
    }

    public boolean isTamed(LivingEntity target) {
        if (target instanceof TamableAnimal) {
            TamableAnimal tamableAnimal = (TamableAnimal)target;
            return tamableAnimal.m_21824_();
        }
        return false;
    }

    public static Vector3f hexToVector3f(String hexColor) {
        String cleanHex = hexColor.startsWith("#") ? hexColor.substring(1) : hexColor;
        int rgb = Integer.parseInt(cleanHex, 16);
        float red = (float)(rgb >> 16 & 0xFF) / 255.0f;
        float green = (float)(rgb >> 8 & 0xFF) / 255.0f;
        float blue = (float)(rgb & 0xFF) / 255.0f;
        return new Vector3f(red, green, blue);
    }

    public static Vector3f hexToVector3f(int hexColor) {
        float red = (float)(hexColor >> 16 & 0xFF) / 255.0f;
        float green = (float)(hexColor >> 8 & 0xFF) / 255.0f;
        float blue = (float)(hexColor & 0xFF) / 255.0f;
        return new Vector3f(red, green, blue);
    }

    public static int[] hexToRGB255(String hexColor) {
        String cleanHex = hexColor.startsWith("#") ? hexColor.substring(1) : hexColor;
        int rgb = Integer.parseInt(cleanHex, 16);
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        return new int[]{red, green, blue};
    }

    public static int[] hexToRGB255(int hexColor) {
        int red = hexColor >> 16 & 0xFF;
        int green = hexColor >> 8 & 0xFF;
        int blue = hexColor & 0xFF;
        return new int[]{red, green, blue};
    }

    public static int hexToRed(String hexColor) {
        return TOGeneralUtils.hexToRGB255(hexColor)[0];
    }

    public static int hexToGreen(String hexColor) {
        return TOGeneralUtils.hexToRGB255(hexColor)[1];
    }

    public static int hexToBlue(String hexColor) {
        return TOGeneralUtils.hexToRGB255(hexColor)[2];
    }

    public static int rgbToARGB(int rgbColor, float alphaPercent) {
        int alpha = (int)(Math.max(0.0f, Math.min(1.0f, alphaPercent)) * 255.0f);
        return alpha << 24 | rgbColor & 0xFFFFFF;
    }

    public static void doMobBreakSuffocatingBlocks(LivingEntity entity) {
        TOGeneralUtils.doMobBreakSuffocatingBlocks(entity, Vec3.f_82478_);
    }

    public static void doMobBreakSuffocatingBlocks(LivingEntity entity, Vec3 offset) {
        if (ForgeEventFactory.getMobGriefingEvent((Level)entity.m_9236_(), (Entity)entity)) {
            int l = Mth.m_14143_((float)(entity.m_20205_() / 2.0f + 1.0f));
            int i1 = Mth.m_14167_((float)entity.m_20206_());
            Vec3i o = new Vec3i(Math.round((float)offset.f_82479_), Math.round((float)offset.f_82480_), Math.round((float)offset.f_82481_));
            for (BlockPos blockpos : BlockPos.m_121976_((int)(entity.m_146903_() - l + o.m_123341_()), (int)(entity.m_146904_() + o.m_123342_()), (int)(entity.m_146907_() - l + o.m_123343_()), (int)(entity.m_146903_() + l + o.m_123341_()), (int)(entity.m_146904_() + i1 + o.m_123342_()), (int)(entity.m_146907_() + l + o.m_123343_()))) {
                BlockState blockstate = entity.m_9236_().m_8055_(blockpos);
                if (!blockstate.canEntityDestroy((BlockGetter)entity.m_9236_(), blockpos, (Entity)entity) || !ForgeEventFactory.onEntityDestroyBlock((LivingEntity)entity, (BlockPos)blockpos, (BlockState)blockstate) || !entity.m_9236_().m_46953_(blockpos, true, (Entity)entity)) continue;
                entity.m_9236_().m_5898_(null, 1022, entity.m_20183_(), 0);
            }
        }
    }

    public static void applyHovering(Entity entity, double baseHoverHeight, double motionSpeed, double deadzone, boolean hurtMarked) {
        double currentY;
        BlockPos groundPos = entity.m_20183_().m_7495_();
        while (entity.m_9236_().m_46859_(groundPos) && groundPos.m_123342_() > entity.m_9236_().m_141937_()) {
            groundPos = groundPos.m_7495_();
        }
        double groundHeight = groundPos.m_123342_() + 1;
        double targetHoverHeight = groundHeight + baseHoverHeight;
        double deltaY = targetHoverHeight - (currentY = entity.m_20186_());
        if (Math.abs(deltaY) > deadzone) {
            Vec3 motion = entity.m_20184_();
            entity.m_20334_(motion.f_82479_, deltaY * motionSpeed, motion.f_82481_);
        }
        entity.f_19864_ = hurtMarked;
        entity.f_19789_ = 0.0f;
        entity.m_6853_(false);
    }
}

