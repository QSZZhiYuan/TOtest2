/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 *  software.bernie.geckolib.renderer.GeoRenderer
 */
package com.gametechbc.traveloptics.entity.projectiles.dragon_spirit;

import com.gametechbc.traveloptics.entity.projectiles.dragon_spirit.DragonSpiritEntity;
import com.gametechbc.traveloptics.entity.projectiles.dragon_spirit.DragonSpiritLayer;
import com.gametechbc.traveloptics.entity.projectiles.dragon_spirit.DragonSpiritModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class DragonSpiritRenderer
extends GeoEntityRenderer<DragonSpiritEntity> {
    public DragonSpiritRenderer(EntityRendererProvider.Context context) {
        super(context, (GeoModel)new DragonSpiritModel());
        this.addRenderLayer(new DragonSpiritLayer((GeoRenderer<DragonSpiritEntity>)this));
        this.f_114477_ = 0.0f;
    }

    public void render(DragonSpiritEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.m_85836_();
        float scale = 3.5f;
        poseStack.m_85841_(scale, scale, scale);
        Vec3 motion = entity.m_20184_();
        double horizontalDistance = Math.sqrt(motion.f_82479_ * motion.f_82479_ + motion.f_82481_ * motion.f_82481_);
        float yaw = (float)(Mth.m_14136_((double)motion.f_82479_, (double)motion.f_82481_) * 57.29577951308232);
        float pitch = (float)(Mth.m_14136_((double)motion.f_82480_, (double)horizontalDistance) * 57.29577951308232);
        poseStack.m_252781_(Axis.f_252436_.m_252977_(yaw));
        poseStack.m_252781_(Axis.f_252529_.m_252977_(-pitch));
        super.m_7392_((Entity)entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.m_85849_();
    }
}

