/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity
 *  com.github.L_Ender.cataclysm.init.ModEntities
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder
 *  io.redspace.ironsspellbooks.util.ItemPropertiesHelper
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.gametechbc.traveloptics.item.bossweapon.thornsofoblivion;

import com.gametechbc.traveloptics.config.WeaponConfig;
import com.gametechbc.traveloptics.entity.item.thorns_of_oblivion.evo_one.ThornsOfOblivionLevelOneRenderer;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.item.UnbreakableGeoMagicSword;
import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ThornsOfOblivionLevelOneItem
extends UnbreakableGeoMagicSword {
    private static ItemDisplayContext transformType;
    private int swingCounter = 0;

    public ThornsOfOblivionLevelOneItem(SpellDataRegistryHolder[] imbuedSpells) {
        super(new Tier(){

            public int m_6609_() {
                return (Integer)WeaponConfig.thornsOblivionDurability.get();
            }

            public float m_6624_() {
                return 2.0f;
            }

            public float m_6631_() {
                return 0.0f;
            }

            public int m_6604_() {
                return 1;
            }

            public int m_6601_() {
                return 20;
            }

            public Ingredient m_6282_() {
                return Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)TravelopticsItems.VERDANT_SPELLWEAVE_INGOT.get())});
            }
        }, (Double)WeaponConfig.thornsOblivionDamage.get(), (Double)WeaponConfig.thornsOblivionAttackSpeed.get(), imbuedSpells, Map.of((Attribute)AttributeRegistry.NATURE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("3065131f-4dbc-45a3-adc7-7f093bd5da65"), "Weapon Modifier", ((Double)WeaponConfig.thornsOblivionNatureSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE), (Attribute)AttributeRegistry.ENDER_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("3065131f-4dbc-45a3-adc7-7f093bd5da65"), "Weapon Modifier", ((Double)WeaponConfig.thornsOblivionEnderSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE)), ItemPropertiesHelper.hidden((int)1).m_41497_(TravelopticsItems.RARITY_NATURAL));
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ++this.swingCounter;
        if (this.swingCounter >= 5) {
            Level level = attacker.m_9236_();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                this.shootClusterProjectiles(serverLevel, attacker);
            }
            this.swingCounter = 0;
        }
        return super.m_7579_(stack, target, attacker);
    }

    public void shootClusterProjectiles(ServerLevel serverLevel, LivingEntity entity) {
        int clusterCount = 12;
        double angleBetween = Math.PI * 2 / (double)clusterCount;
        for (int i = 0; i < clusterCount; ++i) {
            double angle = angleBetween * (double)i;
            double offsetX = Math.sin(angle) * 6.0;
            double offsetZ = Math.cos(angle) * 6.0;
            double motionScale = 1.5;
            Vec3 motion = new Vec3(offsetX, 0.0, offsetZ).m_82541_().m_82490_(motionScale);
            Amethyst_Cluster_Projectile_Entity amethystCluster = new Amethyst_Cluster_Projectile_Entity((EntityType)ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(), (Level)serverLevel, entity, 10.0f);
            amethystCluster.m_7678_(entity.m_20185_(), entity.m_20186_() + 1.5, entity.m_20189_(), 0.0f, 0.0f);
            amethystCluster.m_20256_(motion);
            serverLevel.m_7967_((Entity)amethystCluster);
        }
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237113_((String)"\u00a7ePassive Ability: Thorn Cascade"));
        tooltip.add((Component)Component.m_237115_((String)"ui.traveloptics.weapon.evolution_one").m_130940_(ChatFormatting.YELLOW));
        if (Screen.m_96638_()) {
            tooltip.add((Component)Component.m_237113_((String)"\u00a7fEvery \u00a7b5th \u00a7fhit guarantees \u00a7b12 \u00a7fAmethyst Cluster Projectiles, launching in every direction."));
            tooltip.add((Component)Component.m_237113_((String)""));
            tooltip.add((Component)Component.m_237113_((String)"\u00a7eEvolution Benefits:"));
            tooltip.add((Component)Component.m_237113_((String)"\u00a7e\u2605 \u00a7f[Evo 1] Decreases swing counter to activate the ability \u00a7b-2."));
            tooltip.add((Component)Component.m_237113_((String)"\u00a77\u2606 [Evo 2] Now has \u00a732 Manifestations, \u00a77Circular & Conical."));
            tooltip.add((Component)Component.m_237113_((String)"\u00a77\u2606 [Evo 3] Increases projectile count by \u00a73+50% \u00a77to both Manifestations."));
        } else {
            tooltip.add((Component)Component.m_237113_((String)"\u00a7e\u2605\u00a77\u2606\u2606"));
            tooltip.add((Component)Component.m_237113_((String)"\u00a78[Hold Shift for more info]"));
        }
        super.m_7373_(stack, world, tooltip, flag);
    }

    public void getTransformType(ItemDisplayContext type) {
        transformType = type;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    protected BlockEntityWithoutLevelRenderer getRenderer() {
        return new ThornsOfOblivionLevelOneRenderer();
    }
}

