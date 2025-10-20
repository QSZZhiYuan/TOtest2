/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.phys.Vec3
 */
package com.gametechbc.traveloptics.entity.projectiles.asteroid;

import com.gametechbc.traveloptics.TravelopticsMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

public class AsteroidRenderer
extends EntityRenderer<Projectile> {
    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("traveloptics", "asteroid_model"), "main");
    private static final ResourceLocation BASE_TEXTURE = TravelopticsMod.id("textures/entity/asteroid/asteroid.png");
    private static final ResourceLocation[] FIRE_TEXTURES = new ResourceLocation[]{TravelopticsMod.id("textures/entity/asteroid/fire_0.png"), TravelopticsMod.id("textures/entity/asteroid/fire_1.png"), TravelopticsMod.id("textures/entity/asteroid/fire_2.png"), TravelopticsMod.id("textures/entity/asteroid/fire_3.png"), TravelopticsMod.id("textures/entity/asteroid/fire_4.png"), TravelopticsMod.id("textures/entity/asteroid/fire_5.png"), TravelopticsMod.id("textures/entity/asteroid/fire_6.png"), TravelopticsMod.id("textures/entity/asteroid/fire_7.png")};
    protected final ModelPart body;
    protected final ModelPart outline;
    protected final float scale;

    public AsteroidRenderer(EntityRendererProvider.Context context, float scale) {
        super(context);
        ModelPart modelpart = context.m_174023_(MODEL_LAYER_LOCATION);
        this.body = modelpart.m_171324_("body");
        this.outline = modelpart.m_171324_("outline");
        this.scale = scale;
    }

    public static LayerDefinition createModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.m_171576_();
        partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.f_171404_);
        partdefinition.m_171599_("outline", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 16.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_((MeshDefinition)meshdefinition, (int)48, (int)24);
    }

    public void render(Projectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.m_85836_();
        poseStack.m_85837_(0.0, entity.m_20191_().m_82376_() * 0.5, 0.0);
        poseStack.m_85841_(this.scale, this.scale, this.scale);
        Vec3 motion = entity.m_20184_();
        float xRot = -((float)(Mth.m_14136_((double)motion.m_165924_(), (double)motion.f_82480_) * 57.2957763671875) - 90.0f);
        float yRot = -((float)(Mth.m_14136_((double)motion.f_82481_, (double)motion.f_82479_) * 57.2957763671875) + 90.0f);
        poseStack.m_252781_(Axis.f_252436_.m_252977_(yRot));
        poseStack.m_252781_(Axis.f_252529_.m_252977_(xRot));
        VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110458_((ResourceLocation)this.getTextureLocation(entity)));
        this.body.m_104301_(poseStack, consumer, 0xF000F0, OverlayTexture.f_118083_);
        float f = (float)entity.f_19797_ + partialTicks;
        consumer = bufferSource.m_6299_(RenderType.m_110458_((ResourceLocation)this.getFireTextureLocation(entity)));
        poseStack.m_85841_(1.15f, 1.15f, 1.15f);
        this.outline.m_104301_(poseStack, consumer, 0xF000F0, OverlayTexture.f_118083_);
        poseStack.m_85849_();
        super.m_7392_((Entity)entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    public ResourceLocation getTextureLocation(Projectile entity) {
        return BASE_TEXTURE;
    }

    public ResourceLocation getFireTextureLocation(Projectile entity) {
        int frame = entity.f_19797_ % FIRE_TEXTURES.length;
        return FIRE_TEXTURES[frame];
    }
}

