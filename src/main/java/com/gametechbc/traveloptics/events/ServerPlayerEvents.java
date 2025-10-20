/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.init.ModEffect
 *  io.redspace.ironsspellbooks.api.magic.SpellSelectionManager$SpellSelectionEvent
 *  io.redspace.ironsspellbooks.api.spells.ISpellContainer
 *  io.redspace.ironsspellbooks.api.spells.SpellData
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickEmpty
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickItem
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  top.theillusivec4.curios.api.CuriosApi
 *  top.theillusivec4.curios.api.SlotResult
 */
package com.gametechbc.traveloptics.events;

import com.gametechbc.traveloptics.api.utils.ILeftClick;
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.gametechbc.traveloptics.init.TravelopticsMessages;
import com.gametechbc.traveloptics.init.TravelopticsSounds;
import com.gametechbc.traveloptics.network.MessageSwingArm;
import com.gametechbc.traveloptics.util.TravelopticsTags;
import com.github.L_Ender.cataclysm.init.ModEffect;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import java.util.Arrays;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@Mod.EventBusSubscriber(modid="traveloptics", bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ServerPlayerEvents {
    @SubscribeEvent
    public static void onRightClickFirework(PlayerInteractEvent.RightClickItem event) {
        ItemStack chestplate;
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        if (itemStack.m_150930_(Items.f_42688_) && player.m_21255_() && !(chestplate = player.m_150109_().m_36052_(2)).m_41619_() && chestplate.m_41720_() == TravelopticsItems.MECHANIZED_EXOSKELETON_ARMOR_CHESTPLATE.get()) {
            event.setCanceled(true);
            player.m_5661_((Component)Component.m_237115_((String)"item.traveloptics.mechanized_exoskeleton.jetpack.firework_warning").m_130940_(ChatFormatting.RED), true);
            player.m_9236_().m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), (SoundEvent)TravelopticsSounds.JETPACK_WARNING.get(), SoundSource.PLAYERS, 0.6f, 0.7f);
        }
        if (itemStack.m_150930_(Items.f_42688_) && player.m_21255_() && !(chestplate = player.m_150109_().m_36052_(2)).m_41619_() && chestplate.m_41720_() == TravelopticsItems.FORLORN_HARBINGER_ARMOR_ROBE.get()) {
            event.setCanceled(true);
            player.m_5661_((Component)Component.m_237115_((String)"item.traveloptics.wings.firework_warning").m_130940_(ChatFormatting.RED), true);
            player.m_9236_().m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), (SoundEvent)ACSoundRegistry.VESPER_IDLE.get(), SoundSource.PLAYERS, 0.6f, 0.8f);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.LeftClickEmpty event) {
        boolean flag = false;
        ItemStack leftItem = event.getEntity().m_21206_();
        ItemStack rightItem = event.getEntity().m_21205_();
        if (!event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
            if (leftItem.m_41720_() instanceof ILeftClick) {
                ((ILeftClick)leftItem.m_41720_()).onLeftClick(leftItem, (LivingEntity)event.getEntity());
                flag = true;
            }
            if (rightItem.m_41720_() instanceof ILeftClick) {
                ((ILeftClick)rightItem.m_41720_()).onLeftClick(rightItem, (LivingEntity)event.getEntity());
                flag = true;
            }
            if (event.getLevel().f_46443_ && flag) {
                TravelopticsMessages.sendMSGToServer(MessageSwingArm.INSTANCE);
            }
        }
    }

    @SubscribeEvent
    public static void applyCurioBasedSpells(SpellSelectionManager.SpellSelectionEvent event) {
        Player player = event.getEntity();
        CuriosApi.getCuriosInventory((LivingEntity)player).ifPresent(a -> {
            List list = a.findCurios(item -> item != null && ISpellContainer.isSpellContainer((ItemStack)item) && item.m_204117_(TravelopticsTags.SPELL_IMBUED_CURIO));
            for (SlotResult i : list) {
                int initialIndex;
                SpellData[] spells;
                ISpellContainer spellContainer = i.stack() != null ? ISpellContainer.get((ItemStack)i.stack()) : null;
                if (spellContainer == null || (spells = spellContainer.getAllSpells()) == null || Arrays.stream(spells).toList().isEmpty()) continue;
                for (int spellIndex = initialIndex = event.getManager().getSpellCount(); spellIndex < initialIndex + spells.length; ++spellIndex) {
                    SpellData spell = spells[spellIndex - initialIndex];
                    if (spell == null || spell.getSpell() == null) {
                        return;
                    }
                    event.addSelectionOption(new SpellData(spell.getSpell(), spell.getLevel(), true), i.stack().m_41720_().m_5524_(), spellIndex);
                }
            }
        });
    }
}

