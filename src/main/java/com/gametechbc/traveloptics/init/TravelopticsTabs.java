/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.redspace.ironsspellbooks.api.registry.SpellRegistry
 *  io.redspace.ironsspellbooks.api.spells.AbstractSpell
 *  io.redspace.ironsspellbooks.api.spells.ISpellContainer
 *  io.redspace.ironsspellbooks.registries.ItemRegistry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.CreativeModeTabs
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.event.BuildCreativeModeTabContentsEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.RegistryObject
 */
package com.gametechbc.traveloptics.init;

import com.gametechbc.traveloptics.init.TravelopticsItems;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid="traveloptics", bus=Mod.EventBusSubscriber.Bus.MOD)
public class TravelopticsTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create((ResourceKey)Registries.f_279569_, (String)"traveloptics");
    public static final RegistryObject<CreativeModeTab> TRAVELOPTICS_ITEMS = REGISTRY.register("traveloptics_items", () -> CreativeModeTab.builder().m_257941_((Component)Component.m_237115_((String)"item_group.traveloptics.traveloptics")).m_257737_(() -> new ItemStack((ItemLike)TravelopticsItems.CRADLE_OF_WILL.get())).m_257501_((parameters, tabData) -> {
        tabData.m_246326_((ItemLike)TravelopticsItems.RUNED_PURPUR_BLOCK.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.DUSKWOOD_BOOKSHELF.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.OBSIDIAN_BOOKSHELF.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.AQUA_UPGRADE_ORB.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ELDRITCH_UPGRADE_ORB.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.AQUA_RUNE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.HULLBREAKER_STEEL.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TECTONIC_INGOT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ABYSSAL_SPELLWEAVE_INGOT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.PYRO_SPELLWEAVE_INGOT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.VERDANT_SPELLWEAVE_INGOT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.VOID_SPELLWEAVE_INGOT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TECTONIC_UPGRADE_SMITHING_TEMPLATE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.DARKNESS_UPGRADE_SMITHING_TEMPLATE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ABYSSAL_UPGRADE_SMITHING_TEMPLATE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.WITHERITE_UPGRADE_SMITHING_TEMPLATE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.VOID_MANUSCRIPT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.CRADLE_OF_WILL.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.SHATTERED_HYDROCHARGE_BRACELET.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.FROSTED_CRYOSTORM_BRACELET.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.RESONANT_SCRAP.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.HULLBREAKER_SCALES.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TREMOR_CORE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.DARKNESS_CLOTH.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.DARK_GEM_OF_THE_LIVING_VOID.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ABYSSAL_TENTACLE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ECHO_WINGLET.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.FLAME_TEMPERED_HANDGUARD.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.LAST_GLOW.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ELYTRA_JETPACK_COMPONENT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.PLASMA_POWER_CELL.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ANCIENT_METAL_WEAPON_PARTS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.DESERT_JEWEL_FRAGMENT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.KYREXI_CLAWS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.SPECTRAL_SPELL_SLOT_UPGRADE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.SANCTIFIED_SPELL_SLOT_UPGRADE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.EYE_OF_NOTHINGNESS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.WITHERED_EXCRUCIS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.EXCRUCIS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.MUSIC_DISC_169.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.MUSIC_DISC_NIGHTWARDEN.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.AQUAMANCER_SPAWN_EGG.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.AQUA_GRANDMASTER_SPAWN_EGG.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ENRAGED_DEAD_KING_SPAWN_EGG.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.VOID_TOME_SPAWN_EGG.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.VOIDSHELF_GOLEM_SPAWN_EGG.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.FADING_MAGE_SPAWN_EGG.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.THE_NIGHTWARDEN_SPAWN_EGG.get());
    }).m_257652_());
    public static final RegistryObject<CreativeModeTab> TRAVELOPTICS_EQUIPMENTS = REGISTRY.register("traveloptics_equipments", () -> CreativeModeTab.builder().m_257941_((Component)Component.m_237115_((String)"item_group.traveloptics.traveloptics_equipment")).m_257737_(() -> new ItemStack((ItemLike)TravelopticsItems.ARCHIVE_OF_ABYSSAL_SECRETS.get())).withTabsBefore(new ResourceKey[]{TRAVELOPTICS_ITEMS.getKey()}).m_257501_((parameters, tabData) -> {
        tabData.m_246326_((ItemLike)TravelopticsItems.END_ERUPTION_BOMB_ITEM.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.GUIDE_TO_WATERY_WHISPERS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.CODEX_OF_THE_CRUSHING_DEPTHS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ARCHIVE_OF_ABYSSAL_SECRETS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.THE_ACCUSED_CODEX.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.CHRONICLES_OF_THE_FIRELORD.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TOME_OF_ABYSSAL_FLORA.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.WAND_OF_FINAL_LIGHT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TITANLORD_SCEPTER.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TITANLORD_SCEPTER_RETRO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TITANLORD_SCEPTER_TECTONIC.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.STELLOTHORN.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ETERNAL_MAELSTROM_TRIDENT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.GAUNTLET_OF_EXTINCTION.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ABYSSAL_TIDECALLER.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.THE_OBLITERATOR.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.CURSED_WRAITHBLADE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.MECHANIZED_WRAITHBLADE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.FLAMES_OF_ELDRITCH.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.HARBINGERS_WRATH.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.SCOURGE_OF_THE_SANDS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.THORNS_OF_OBLIVION.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.VOIDSTRIKE_REAPER.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.INFERNAL_DEVASTATOR.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.POCKET_BLACK_HOLE_BELT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.AETHERIAL_DESPAIR_RING.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.FIRESTORM_RING.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.HYDROCHARGE_BRACELET.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.CRYOSTORM_BRACELET.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.AZURE_IGNITION_BRACELET.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.NIGHTSTALKERS_BAND.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ENERGY_UNBOUND_NECKLACE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.SIGIL_OF_THE_SPIDER_SORCERER.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.AMULET_OF_SPECTRAL_SHIFT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.BOTTLED_RAINCLOUD.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ELYTRA_JETPACK_COMPONENT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.AQUA_ECHO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.BLOOD_ECHO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ELDRITCH_ECHO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ENDER_ECHO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.EVOCATION_ECHO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.FIRE_ECHO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.HOLY_ECHO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ICE_ECHO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.LIGHTNING_ECHO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.NATURE_ECHO.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.DEEPLING_MAGE_ARMOR_HELMET.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.DEEPLING_MAGE_ARMOR_ROBE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.DEEPLING_MAGE_ARMOR_LEGGINGS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.DEEPLING_MAGE_ARMOR_BOOTS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.FORLORN_HARBINGER_ARMOR_HOOD.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.FORLORN_HARBINGER_ARMOR_ROBE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.FORLORN_HARBINGER_ARMOR_LEGGINGS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.FORLORN_HARBINGER_ARMOR_BOOTS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.PRIMORDIAL_CREST_ARMOR_HELMET.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.PRIMORDIAL_CREST_ARMOR_CHESTPLATE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.PRIMORDIAL_CREST_ARMOR_LEGGINGS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.PRIMORDIAL_CREST_ARMOR_BOOTS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TECTONIC_CREST_ARMOR_HELMET.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TECTONIC_CREST_ARMOR_CHESTPLATE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TECTONIC_CREST_ARMOR_LEGGINGS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.TECTONIC_CREST_ARMOR_BOOTS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ABYSSAL_HIDE_ARMOR_HAT.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ABYSSAL_HIDE_ARMOR_ROBE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ABYSSAL_HIDE_ARMOR_LEGGINGS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.ABYSSAL_HIDE_ARMOR_BOOTS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.CURSED_WRAITHGUARD_ARMOR_CROWN.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.CURSED_WRAITHGUARD_ARMOR_CHESTPLATE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.CURSED_WRAITHGUARD_ARMOR_SPECTRAL_WRAPPING.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.CURSED_WRAITHGUARD_ARMOR_BOOTS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.MECHANIZED_EXOSKELETON_ARMOR_HELMET.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.MECHANIZED_EXOSKELETON_ARMOR_CHESTPLATE.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.MECHANIZED_EXOSKELETON_ARMOR_LEGGINGS.get());
        tabData.m_246326_((ItemLike)TravelopticsItems.MECHANIZED_EXOSKELETON_ARMOR_BOOTS.get());
    }).m_257652_());
    public static final RegistryObject<CreativeModeTab> TRAVELOPTICS_SCROLLS = REGISTRY.register("traveloptics_scrolls", () -> CreativeModeTab.builder().m_257941_((Component)Component.m_237115_((String)"item_group.traveloptics.traveloptics_scrolls")).m_257737_(() -> new ItemStack((ItemLike)TravelopticsItems.AQUA_SCROLL_DUMMY.get())).withTabsAfter(new ResourceKey[]{TRAVELOPTICS_ITEMS.getKey()}).m_257652_());

    @SubscribeEvent
    public static void fillCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CreativeModeTabs.m_258007_() || event.getTab() == TRAVELOPTICS_SCROLLS.get()) {
            SpellRegistry.getEnabledSpells().stream().filter(spellType -> spellType != SpellRegistry.none()).filter(spell -> spell.getSpellResource().m_135827_().equals("traveloptics")).forEach(spell -> {
                for (int i = spell.getMinLevel(); i <= spell.getMaxLevel(); ++i) {
                    ItemStack itemstack = new ItemStack((ItemLike)ItemRegistry.SCROLL.get());
                    ISpellContainer spellList = ISpellContainer.createScrollContainer((AbstractSpell)spell, (int)i, (ItemStack)itemstack);
                    spellList.save(itemstack);
                    event.m_246342_(itemstack);
                }
            });
        }
    }
}

