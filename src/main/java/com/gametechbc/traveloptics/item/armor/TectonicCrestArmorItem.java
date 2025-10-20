/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SchoolRegistry
 *  io.redspace.ironsspellbooks.api.spells.SchoolType
 *  io.redspace.ironsspellbooks.capabilities.magic.MagicManager
 *  io.redspace.ironsspellbooks.damage.DamageSources
 *  io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.player.Player
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
import com.gametechbc.traveloptics.entity.armor.tectonic_crest_armor.TectonicCrestArmorModel;
import com.gametechbc.traveloptics.entity.armor.tectonic_crest_armor.TectonicCrestArmorRenderer;
import com.gametechbc.traveloptics.item.TravelopticsArmorMaterials;
import com.gametechbc.traveloptics.item.UnbreakableImbueableArmor;
import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
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

public class TectonicCrestArmorItem
extends UnbreakableImbueableArmor {
    private static final double KNOCKBACK_RADIUS = 5.0;
    private static final float KNOCKBACK_FORCE = 2.0f;
    private static final float DAMAGE_AMOUNT = 5.0f;
    private static final int COOLDOWN_TICKS = 400;
    private static final int FIRE_RESISTANCE_DURATION = 200;

    public TectonicCrestArmorItem(ArmorItem.Type slot, Item.Properties settings) {
        super(TravelopticsArmorMaterials.TECTONIC_CREST, slot, settings);
    }

    @Override
    protected Set<ArmorItem.Type> getImbuableArmorTypes() {
        return Set.of(ArmorItem.Type.CHESTPLATE);
    }

    @Override
    protected Map<ArmorItem.Type, Integer> getMaxSpellSlots() {
        return Map.of(ArmorItem.Type.CHESTPLATE, 1);
    }

    public void m_6883_(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.m_6883_(stack, level, entity, slot, selected);
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        ItemStack chestplate = player.m_6844_(EquipmentSlot.CHEST);
        if (stack != chestplate || chestplate.m_41720_() != this) {
            return;
        }
        if (!level.f_46443_ && this.isWearingFullSet(player)) {
            this.grantFireResistance(player);
            if (CooldownManager.isCooldownActive(chestplate)) {
                CooldownManager.tickCooldown(chestplate);
                return;
            }
            if (player.f_20916_ > 0) {
                this.triggerKnockbackAndDamage(player);
                this.setCooldownOnChestplate(player);
            }
        }
    }

    private void triggerKnockbackAndDamage(Player player) {
        Level level = player.m_9236_();
        AABB boundingBox = new AABB(player.m_20185_() - 5.0, player.m_20186_() - 5.0, player.m_20189_() - 5.0, player.m_20185_() + 5.0, player.m_20186_() + 5.0, player.m_20189_() + 5.0);
        List nearbyEntities = level.m_6443_(LivingEntity.class, boundingBox, entity -> {
            TamableAnimal tamable;
            if (entity == player) {
                return false;
            }
            if (player.m_7307_((Entity)entity)) {
                return false;
            }
            if (entity instanceof TamableAnimal && (tamable = (TamableAnimal)entity).m_21824_()) {
                return tamable.m_269323_() != player;
            }
            return true;
        });
        double fireSpellPower = player.m_21133_((Attribute)AttributeRegistry.FIRE_SPELL_POWER.get());
        float scaledDamage = (float)(5.0 + fireSpellPower * 3.0);
        for (LivingEntity entity2 : nearbyEntities) {
            DamageSource knockbackDamageSource = new DamageSource(DamageSources.getHolderFromResource((Entity)entity2, TravelopticsDamageTypes.TECTONIC_CREST), (Entity)player, null);
            DamageSources.ignoreNextKnockback((LivingEntity)entity2);
            Vec3 knockbackVector = entity2.m_20182_().m_82546_(player.m_20182_()).m_82541_().m_82490_(2.0);
            entity2.m_20256_(knockbackVector);
            entity2.m_6469_(knockbackDamageSource, scaledDamage);
            entity2.m_20254_(10);
        }
        level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), (SoundEvent)ACSoundRegistry.LUXTRUCTOSAURUS_STOMP.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        MagicManager.spawnParticles((Level)level, (ParticleOptions)new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.HOLY.get()).getTargetingColor(), 5.0f), (double)player.m_20185_(), (double)(player.m_20186_() + (double)0.165f), (double)player.m_20189_(), (int)1, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (boolean)true);
    }

    private void grantFireResistance(Player player) {
        if (!player.m_21023_(MobEffects.f_19607_)) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 200, 0, false, false));
        }
    }

    private void setCooldownOnChestplate(Player player) {
        ItemStack chestplate = player.m_6844_(ArmorItem.Type.CHESTPLATE.m_266308_());
        if (chestplate.m_41720_() instanceof TectonicCrestArmorItem) {
            CooldownManager.setCooldown(chestplate, 400, 400);
        }
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        super.m_7373_(stack, world, tooltip, flag);
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.tectonic_crest_armor.tooltip").m_130940_(ChatFormatting.GREEN));
        tooltip.add((Component)Component.m_237115_((String)"item.traveloptics.tectonic_crest_armor.tooltip1"));
        int cooldown = CooldownManager.getCooldown(stack);
        if (cooldown > 0) {
            tooltip.add((Component)Component.m_237110_((String)"item.tooltip.traveloptics.armor_cooldown", (Object[])new Object[]{cooldown / 20}).m_130940_(ChatFormatting.GRAY));
        }
        tooltip.add((Component)Component.m_237113_((String)""));
    }

    private boolean isWearingFullSet(Player player) {
        return player.m_6844_(ArmorItem.Type.HELMET.m_266308_()).m_41720_() instanceof TectonicCrestArmorItem && player.m_6844_(ArmorItem.Type.CHESTPLATE.m_266308_()).m_41720_() instanceof TectonicCrestArmorItem && player.m_6844_(ArmorItem.Type.LEGGINGS.m_266308_()).m_41720_() instanceof TectonicCrestArmorItem && player.m_6844_(ArmorItem.Type.BOOTS.m_266308_()).m_41720_() instanceof TectonicCrestArmorItem;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new TectonicCrestArmorRenderer(new TectonicCrestArmorModel());
    }
}

