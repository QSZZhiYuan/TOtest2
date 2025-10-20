/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.L_Ender.cataclysm.client.event.EventPosePlayerHand
 *  io.redspace.ironsspellbooks.item.SpellBook
 *  io.redspace.ironsspellbooks.render.SpellBookCurioRenderer
 *  net.minecraft.client.renderer.FogRenderer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 *  top.theillusivec4.curios.api.client.CuriosRendererRegistry
 */
package com.gametechbc.traveloptics.events;

// import com.gametechbc.traveloptics.effects.AstralSenseEffect; // REMOVED (FogRenderer access issue)
// import com.gametechbc.traveloptics.effects.AstralSenseTreasureEffect; // REMOVED (FogRenderer access issue)
import com.gametechbc.traveloptics.init.TravelopticsItems;
import com.github.L_Ender.cataclysm.client.event.EventPosePlayerHand;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(modid="traveloptics", bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class ModClientEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            TravelopticsItems.getTravelopticsItems().stream().filter(item -> item.get() instanceof SpellBook).forEach(item -> CuriosRendererRegistry.register((Item)((Item)item.get()), SpellBookCurioRenderer::new));
            // FogRenderer.f_234164_.add(new AstralSenseEffect.AstralSenseFogFunction()); // REMOVED (FogRenderer access issue)
            // FogRenderer.f_234164_.add(new AstralSenseTreasureEffect.AstralSenseFogFunction()); // REMOVED (FogRenderer access issue)
        });
    }

    @SubscribeEvent
    public void onPoseHand(EventPosePlayerHand event) {
        LivingEntity player = (LivingEntity)event.getEntityIn();
        if (player.m_21120_(InteractionHand.OFF_HAND).m_150930_((Item)TravelopticsItems.THE_OBLITERATOR.get()) && player.m_21120_(InteractionHand.MAIN_HAND).m_150930_((Item)TravelopticsItems.THE_OBLITERATOR.get()) && player.m_6117_()) {
            if (player.m_5737_() == HumanoidArm.LEFT) {
                event.getModel().f_102811_.f_104203_ = event.getModel().f_102811_.f_104203_ * 0.5f - (float)Math.PI;
                event.getModel().f_102811_.f_104204_ = 0.0f;
            } else {
                event.getModel().f_102812_.f_104203_ = event.getModel().f_102812_.f_104203_ * 0.5f - (float)Math.PI;
                event.getModel().f_102812_.f_104204_ = 0.0f;
            }
        }
    }
}

