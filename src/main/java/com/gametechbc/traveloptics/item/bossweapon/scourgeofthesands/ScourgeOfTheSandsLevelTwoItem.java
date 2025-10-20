/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.entity.projectile.Ancient_Desert_Stele_Entity
 *  com.github.L_Ender.cataclysm.init.ModItems
 *  com.github.L_Ender.cataclysm.init.ModParticle
 *  com.github.L_Ender.cataclysm.init.ModSounds
 *  io.redspace.ironsspellbooks.api.registry.AttributeRegistry
 *  io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder
 *  io.redspace.ironsspellbooks.util.ItemPropertiesHelper
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
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
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package com.gametechbc.traveloptics.item.bossweapon.scourgeofthesands;

import com.gametechbc.traveloptics.config.WeaponConfig;
import com.gametechbc.traveloptics.data_manager.WeaponFormManager;
import com.gametechbc.traveloptics.entity.extended_projectiles.ExtendedSandstormEntity;
import com.gametechbc.traveloptics.entity.item.scourge_of_the_sands.evo_two.ScourgeOfTheSandsLevelTwoRenderer;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.item.UnbreakableGeoMagicSword;
import com.gametechbc.traveloptics.item.bossweapon.scourgeofthesands.ScourgeOfTheSandsAbility;
import com.github.L_Ender.cataclysm.entity.projectile.Ancient_Desert_Stele_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ScourgeOfTheSandsLevelTwoItem
extends UnbreakableGeoMagicSword {
    private static final int FORM_SANDSTORM = 0;
    private static final int FORM_BASE = 1;
    private static ItemDisplayContext transformType;
    private static final ScourgeOfTheSandsAbility ABILITY;

    public ScourgeOfTheSandsLevelTwoItem(SpellDataRegistryHolder[] imbuedSpells) {
        super(new Tier(){

            public int m_6609_() {
                return (Integer)WeaponConfig.scourgeSandsDurability.get();
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
                return Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)ModItems.ANCIENT_METAL_INGOT.get())});
            }
        }, (Double)WeaponConfig.scourgeSandsDamage.get(), (Double)WeaponConfig.scourgeSandsAttackSpeed.get(), imbuedSpells, Map.of((Attribute)AttributeRegistry.HOLY_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("8ff095c4-a26d-4d28-bc06-1a908b3577aa"), "Weapon Modifier", ((Double)WeaponConfig.scourgeSandsEvocationSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE), (Attribute)AttributeRegistry.NATURE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("8ff095c4-a26d-4d28-bc06-1a908b3577aa"), "Weapon Modifier", ((Double)WeaponConfig.scourgeSandsNatureSpellPower.get()).doubleValue(), AttributeModifier.Operation.MULTIPLY_BASE)), ItemPropertiesHelper.hidden((int)1).m_41497_(TravelopticsItems.RARITY_ANCIENT));
    }

    public void m_5929_(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        int i = this.m_8105_(stack) - count;
        if (i == 5) {
            this.massEffectParticle(worldIn, livingEntityIn, 3.0f);
        }
        if (i == 10) {
            this.massEffectParticle(worldIn, livingEntityIn, 4.5f);
        }
        if (i == 20) {
            this.massEffectParticle(worldIn, livingEntityIn, 6.0f);
            livingEntityIn.m_5496_((SoundEvent)ModSounds.REMNANT_ROAR.get(), 1.0f, 1.0f);
        }
    }

    private void massEffectParticle(Level world, LivingEntity caster, float radius) {
        if (world.f_46443_) {
            for (int j = 0; j < 70; ++j) {
                float angle = (float)(Math.random() * 2.0 * Math.PI);
                double distance = Math.sqrt(Math.random()) * (double)radius;
                double extraX = caster.m_20185_() + distance * (double)Mth.m_14089_((float)angle);
                double extraY = caster.m_20186_() + (double)0.3f;
                double extraZ = caster.m_20189_() + distance * (double)Mth.m_14031_((float)angle);
                world.m_7106_((ParticleOptions)ModParticle.SANDSTORM.get(), extraX, extraY, extraZ, 0.0, world.f_46441_.m_188583_() * 0.04, 0.0);
            }
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (player.m_6047_()) {
            WeaponFormManager.cycleForm(stack, 2);
            int newForm = WeaponFormManager.getForm(stack);
            if (newForm == 0) {
                player.m_5661_((Component)Component.m_237115_((String)"item.traveloptics.scourge_of_the_sands.tooltip.manifestation0").m_130940_(ChatFormatting.LIGHT_PURPLE), true);
            } else {
                player.m_5661_((Component)Component.m_237115_((String)"item.traveloptics.scourge_of_the_sands.tooltip.manifestation1").m_130940_(ChatFormatting.LIGHT_PURPLE), true);
            }
            if (!world.m_5776_()) {
                world.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), (SoundEvent)TravelopticsSounds.MANIFESTATION_CHANGE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            }
            return InteractionResultHolder.m_19092_((Object)stack, (boolean)world.m_5776_());
        }
        player.m_6672_(hand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)stack);
    }

    public void m_5551_(ItemStack stack, Level world, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player) {
            Player player = (Player)entity;
            int chargeDuration = this.m_8105_(stack) - timeLeft;
            float power = ScourgeOfTheSandsLevelTwoItem.getPowerForTime(chargeDuration);
            if (power >= 1.0f) {
                int currentForm = WeaponFormManager.getForm(stack);
                if (currentForm == 1) {
                    player.m_5496_((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0f, 1.0f);
                    this.spawnDesertStele(player, world, 25.0f);
                    player.m_36335_().m_41524_((Item)this, 100);
                } else {
                    player.m_5496_((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0f, 1.0f);
                    this.spawnSandstormEntities(player, world);
                    player.m_36335_().m_41524_((Item)this, 260);
                }
                List<Item> itemsToCooldown = List.of((Item)TravelopticsItems.SCOURGE_OF_THE_SANDS_LEVEL_THREE.get());
                for (int i = 0; i < player.m_150109_().m_6643_(); ++i) {
                    ItemStack invStack = player.m_150109_().m_8020_(i);
                    if (invStack.m_41619_() || !itemsToCooldown.contains(invStack.m_41720_())) continue;
                    player.m_36335_().m_41524_(invStack.m_41720_(), 200);
                }
            }
        }
    }

    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int m_8105_(ItemStack stack) {
        return 72000;
    }

    private static float getPowerForTime(int charge) {
        float f = (float)charge / 20.0f;
        if ((f = (f * f + f * 2.0f) / 3.0f) > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    private void spawnSandstormEntities(Player player, Level world) {
        if (!world.m_5776_()) {
            for (int i = 0; i < 2; ++i) {
                float angle = (float)i * (float)Math.PI;
                double sx = player.m_20185_() + (double)(Mth.m_14089_((float)angle) * 6.0f);
                double sy = player.m_20186_();
                double sz = player.m_20189_() + (double)(Mth.m_14031_((float)angle) * 6.0f);
                ExtendedSandstormEntity sandstorm = new ExtendedSandstormEntity(world, sx, sy, sz, 200, angle, (LivingEntity)player);
                sandstorm.setCustomDamage(7.0f);
                world.m_7967_((Entity)sandstorm);
            }
        }
    }

    private void spawnDesertSteleAtPosition(Vec3 position, Level world, float damage, LivingEntity caster) {
        Ancient_Desert_Stele_Entity desertStele = new Ancient_Desert_Stele_Entity(world, position.f_82479_, position.f_82480_, position.f_82481_, caster.m_146908_(), 10, damage, caster);
        desertStele.setDamage(damage);
        desertStele.setCaster(caster);
        world.m_7967_((Entity)desertStele);
    }

    private void spawnDesertStele(Player player, Level world, float damage) {
        double range = 18.0;
        List entities = world.m_45976_(LivingEntity.class, player.m_20191_().m_82400_(range));
        entities.sort(Comparator.comparingDouble(e -> e.m_20270_((Entity)player)));
        int targetLimit = 10;
        int targetsProcessed = 0;
        for (LivingEntity entity : entities) {
            if (entity == player) continue;
            this.spawnDesertSteleAbove(entity, world, damage, (LivingEntity)player);
            if (++targetsProcessed < targetLimit) continue;
            break;
        }
    }

    private void spawnDesertSteleAbove(LivingEntity target, Level world, float damage, LivingEntity caster) {
        Vec3 targetPos = target.m_20182_().m_82520_(0.0, (double)target.m_20192_() + 3.0, 0.0);
        this.spawnDesertSteleAtPosition(targetPos, world, damage, caster);
    }

    public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        ABILITY.appendHoverText(stack, world, tooltip, flag, 2);
        super.m_7373_(stack, world, tooltip, flag);
    }

    public void getTransformType(ItemDisplayContext type) {
        transformType = type;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    protected BlockEntityWithoutLevelRenderer getRenderer() {
        return new ScourgeOfTheSandsLevelTwoRenderer();
    }

    static {
        ABILITY = new ScourgeOfTheSandsAbility();
    }
}

