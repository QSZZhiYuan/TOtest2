/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Orb_Entity
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  software.bernie.geckolib.renderer.GeoArmorRenderer
 */
package com.gametechbc.traveloptics.item.armor;

import com.gametechbc.traveloptics.data_manager.CooldownManager;
import com.gametechbc.traveloptics.entity.armor.abyssal_hide_armor.AbyssalHideArmorModel;
import com.gametechbc.traveloptics.entity.armor.abyssal_hide_armor.AbyssalHideArmorRenderer;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.item.TravelopticsArmorMaterials;
import com.gametechbc.traveloptics.item.UnbreakableImbueableArmor;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Orb_Entity;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AbyssalHideArmorItem
extends UnbreakableImbueableArmor {
    private static final int COOLDOWN_TICKS = 400;
    private static final double TRACKING_RANGE = 15.0;

    public AbyssalHideArmorItem(ArmorItem.Type slot, Item.Properties settings) {
        super(TravelopticsArmorMaterials.ABYSSAL_HIDE, slot, settings);
    }

    @Override
    protected Set<ArmorItem.Type> getImbuableArmorTypes() {
        return Set.of(ArmorItem.Type.CHESTPLATE);
    }

    @Override
    protected Map<ArmorItem.Type, Integer> getMaxSpellSlots() {
        return Map.of(ArmorItem.Type.CHESTPLATE, 1);
    }

    public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.m_6883_(stack, world, entity, slot, selected);
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        if (!this.isWearingFullSet(player)) {
            return;
        }
        ItemStack chestplate = player.m_6844_(ArmorItem.Type.CHESTPLATE.m_266308_());
        if (stack != chestplate || chestplate.m_41720_() != this) {
            return;
        }
        if (!world.f_46443_) {
            if (CooldownManager.isCooldownActive(chestplate)) {
                CooldownManager.tickCooldown(chestplate);
                return;
            }
            if ((double)player.m_21223_() <= (double)player.m_21233_() * 0.75) {
                this.shootAbyssOrbs(world, (LivingEntity)player);
                world.m_5594_(null, player.m_20183_(), (SoundEvent)TravelopticsSounds.ORBITAL_VOID_PULSE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                CooldownManager.setCooldown(chestplate, 400, 400);
            }
        }
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        super.m_7373_(stack, world, tooltip, flag);
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.abyssal_hide_armor.tooltip").m_130940_(ChatFormatting.GREEN));
        int cooldown = CooldownManager.getCooldown(stack);
        if (cooldown > 0) {
            tooltip.add((Component)Component.m_237110_((String)"item.tooltip.traveloptics.armor_cooldown", (Object[])new Object[]{cooldown / 20}).m_130940_(ChatFormatting.GRAY));
        }
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.abyssal_hide_armor.tooltip1"));
        tooltip.add((Component)Component.m_237113_((String)""));
    }

    private float calculateAbyssOrbDamage(LivingEntity entity) {
        float enderSpellPower = (float)entity.m_21133_((Attribute)AttributeRegistry.ENDER_SPELL_POWER.get());
        float eldritchSpellPower = (float)entity.m_21133_((Attribute)AttributeRegistry.ELDRITCH_SPELL_POWER.get());
        return (enderSpellPower + eldritchSpellPower) * 3.0f;
    }

    private void shootAbyssOrbs(Level level, LivingEntity entity) {
        int orbCount = 6;
        float getDamage = this.calculateAbyssOrbDamage(entity);
        double angleBetween = Math.PI * 2 / (double)orbCount;
        for (int i = 0; i < orbCount; ++i) {
            Projectile projectile;
            double angle = angleBetween * (double)i;
            double offsetX = Math.sin(angle) * 6.0;
            double offsetZ = Math.cos(angle) * 6.0;
            double motionScale = 3.0;
            Vec3 motion = new Vec3(offsetX, 0.0, offsetZ).m_82541_().m_82490_(motionScale);
            EntityType entityType = EntityType.m_20632_((String)"cataclysm:abyss_orb").orElse(null);
            if (entityType == null || (projectile = (Projectile)entityType.m_20615_(level)) == null) continue;
            projectile.m_7678_(entity.m_20185_(), entity.m_20186_() + 1.5, entity.m_20189_(), 0.0f, 0.0f);
            projectile.m_20334_(motion.m_7096_(), motion.m_7098_(), motion.m_7094_());
            LivingEntity target = this.findNearestTarget(level, entity);
            if (target != null && projectile instanceof Abyss_Orb_Entity) {
                Abyss_Orb_Entity abyssOrb = (Abyss_Orb_Entity)projectile;
                abyssOrb.setTracking(true);
                abyssOrb.m_5602_((Entity)entity);
                abyssOrb.setDamage(getDamage);
                try {
                    Field finalTargetField = Abyss_Orb_Entity.class.getDeclaredField("finalTarget");
                    finalTargetField.setAccessible(true);
                    finalTargetField.set(abyssOrb, target);
                }
                catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            level.m_7967_((Entity)projectile);
        }
    }

    private LivingEntity findNearestTarget(Level level, LivingEntity caster) {
        AABB boundingBox = new AABB(caster.m_20185_() - 15.0, caster.m_20186_() - 15.0, caster.m_20189_() - 15.0, caster.m_20185_() + 15.0, caster.m_20186_() + 15.0, caster.m_20189_() + 15.0);
        List possibleTargets = level.m_6443_(LivingEntity.class, boundingBox, entity -> {
            TamableAnimal tamable;
            if (entity == caster) {
                return false;
            }
            if (caster.m_7307_((Entity)entity)) {
                return false;
            }
            return !(entity instanceof TamableAnimal) || !(tamable = (TamableAnimal)entity).m_21824_() || tamable.m_269323_() != caster;
        });
        if (possibleTargets.isEmpty()) {
            return null;
        }
        return possibleTargets.stream().min(Comparator.comparingDouble(arg_0 -> ((LivingEntity)caster).m_20280_(arg_0))).orElse(null);
    }

    private boolean isWearingFullSet(Player player) {
        return player.m_6844_(ArmorItem.Type.HELMET.m_266308_()).m_41720_() instanceof AbyssalHideArmorItem && player.m_6844_(ArmorItem.Type.CHESTPLATE.m_266308_()).m_41720_() instanceof AbyssalHideArmorItem && player.m_6844_(ArmorItem.Type.LEGGINGS.m_266308_()).m_41720_() instanceof AbyssalHideArmorItem && player.m_6844_(ArmorItem.Type.BOOTS.m_266308_()).m_41720_() instanceof AbyssalHideArmorItem;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new AbyssalHideArmorRenderer(new AbyssalHideArmorModel());
    }
}

