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
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
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
import com.gametechbc.traveloptics.data_manager.WeaponFormManager;
import com.gametechbc.traveloptics.entity.item.thorns_of_oblivion.evo_two.ThornsOfOblivionLevelTwoRenderer;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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

public class ThornsOfOblivionLevelTwoItem
extends UnbreakableGeoMagicSword {
    private static ItemDisplayContext transformType;
    private static final int FORM_BASE = 0;
    private static final int FORM_TARGETED = 1;
    private int swingCounter = 0;

    public ThornsOfOblivionLevelTwoItem(SpellDataRegistryHolder[] imbuedSpells) {
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

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (player.m_6047_()) {
            WeaponFormManager.cycleForm(stack, 2);
            int newForm = WeaponFormManager.getForm(stack);
            if (newForm == 1) {
                player.m_5661_((Component)Component.m_237113_((String)"\u00a7dManifestation: Conical"), true);
            } else {
                player.m_5661_((Component)Component.m_237113_((String)"\u00a7dManifestation: Circular"), true);
            }
            if (!world.m_5776_()) {
                world.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), (SoundEvent)TravelopticsSounds.MANIFESTATION_CHANGE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            }
            List<Item> itemsToCooldown = List.of((Item)TravelopticsItems.THORNS_OF_OBLIVION_LEVEL_THREE.get());
            for (int i = 0; i < player.m_150109_().m_6643_(); ++i) {
                ItemStack invStack = player.m_150109_().m_8020_(i);
                if (invStack.m_41619_() || !itemsToCooldown.contains(invStack.m_41720_())) continue;
                player.m_36335_().m_41524_(invStack.m_41720_(), 160);
            }
            player.m_36335_().m_41524_((Item)this, 160);
            return InteractionResultHolder.m_19092_((Object)stack, (boolean)world.m_5776_());
        }
        return InteractionResultHolder.m_19098_((Object)stack);
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ++this.swingCounter;
        if (this.swingCounter >= 5) {
            Level level = attacker.m_9236_();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                int currentForm = WeaponFormManager.getForm(stack);
                if (currentForm == 0) {
                    this.shootClusterProjectiles(serverLevel, attacker, 360, 12);
                } else {
                    this.shootClusterProjectiles(serverLevel, attacker, 120, 8);
                }
            }
            this.swingCounter = 0;
        }
        return super.m_7579_(stack, target, attacker);
    }

    public void shootClusterProjectiles(ServerLevel serverLevel, LivingEntity entity, int angleRange, int clusterCount) {
        double angleBetween = Math.toRadians(angleRange) / (double)clusterCount;
        Vec3 lookDirection = entity.m_20154_();
        for (int i = 0; i < clusterCount; ++i) {
            double angle = angleBetween * (double)i - Math.toRadians((double)angleRange / 2.0);
            double xRotation = Math.sin(angle) * lookDirection.m_7094_() + Math.cos(angle) * lookDirection.m_7096_();
            double zRotation = Math.cos(angle) * lookDirection.m_7094_() - Math.sin(angle) * lookDirection.m_7096_();
            this.shootProjectile(serverLevel, entity, xRotation, zRotation);
        }
    }

    private void shootProjectile(ServerLevel serverLevel, LivingEntity entity, double offsetX, double offsetZ) {
        double motionScale = 1.5;
        Vec3 motion = new Vec3(offsetX, 0.0, offsetZ).m_82541_().m_82490_(motionScale);
        Amethyst_Cluster_Projectile_Entity amethystCluster = new Amethyst_Cluster_Projectile_Entity((EntityType)ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(), (Level)serverLevel, entity, 10.0f);
        amethystCluster.m_7678_(entity.m_20185_(), entity.m_20186_() + 1.5, entity.m_20189_(), 0.0f, 0.0f);
        amethystCluster.m_20256_(motion);
        serverLevel.m_7967_((Entity)amethystCluster);
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237113_((String)"\u00a7ePassive Ability: Thorn Cascade"));
        tooltip.add((Component)Component.m_237115_((String)"ui.traveloptics.weapon.evolution_two").m_130940_(ChatFormatting.YELLOW));
        int currentForm = WeaponFormManager.getForm(stack);
        if (currentForm == 1) {
            tooltip.add((Component)Component.m_237113_((String)"\u00a7dManifestation: Conical [CD: 8s]"));
        } else if (currentForm == 0) {
            tooltip.add((Component)Component.m_237113_((String)"\u00a7dManifestation: Circular [CD: 8s]"));
        }
        if (Screen.m_96638_()) {
            if (currentForm == 1) {
                tooltip.add((Component)Component.m_237113_((String)"\u00a7fEvery \u00a7b5th \u00a7fhit guarantees the launch of \u00a7b8 \u00a7fAmethyst Cluster Projectiles in a 120-degree arc in front of the player."));
            } else if (currentForm == 0) {
                tooltip.add((Component)Component.m_237113_((String)"\u00a7fEvery \u00a7b5th \u00a7fhit guarantees \u00a7b12 \u00a7fAmethyst Cluster Projectiles, launching in all direction."));
            }
            tooltip.add((Component)Component.m_237113_((String)""));
            tooltip.add((Component)Component.m_237113_((String)"\u00a7eEvolution Benefits:"));
            tooltip.add((Component)Component.m_237113_((String)"\u00a7e\u2605 \u00a7f[Evo 1] Decreases swing counter to activate the ability \u00a7b-2."));
            tooltip.add((Component)Component.m_237113_((String)"\u00a7e\u2605 \u00a7f[Evo 2] Now has \u00a7b2 Manifestations, \u00a7fCircular & Conical."));
            tooltip.add((Component)Component.m_237113_((String)"\u00a77\u2606 [Evo 3] Increases projectile count by \u00a73+50% \u00a77to both Manifestations."));
            tooltip.add((Component)Component.m_237113_((String)"\u00a78[Crouch right-click to change Manifestations]"));
        } else {
            tooltip.add((Component)Component.m_237113_((String)"\u00a7e\u2605\u2605\u00a77\u2606"));
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
        return new ThornsOfOblivionLevelTwoRenderer();
    }
}

