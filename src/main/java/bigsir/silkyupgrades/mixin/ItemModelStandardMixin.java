package bigsir.silkyupgrades.mixin;

import bigsir.silkyupgrades.upgrades.ToolUpgrade;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.item.material.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bigsir.silkyupgrades.SilkyUpgrades;

import java.util.Random;

@Mixin(value = ItemModelStandard.class, remap = false)
public abstract class ItemModelStandardMixin {

	@Inject(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/model/ItemModelStandard;renderItemInWorld(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/item/ItemStack;FFZ)V", shift = At.Shift.AFTER))
	public void renderOverlay0(Tessellator tessellator, ItemRenderer renderer, ItemStack itemstack, Entity entity, float brightness, boolean handheldTransform, CallbackInfo ci){
		if(ToolUpgrade.isItemUpgraded(itemstack)){
			SilkyUpgrades.itemModelSilky.renderItemInWorld(tessellator, entity, itemstack, brightness, 1.0f, false);
		}
	}

	@Inject(method = "renderAsItemEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/model/ItemModelStandard;renderFlat(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/client/render/stitcher/IconCoordinate;)V", shift = At.Shift.AFTER))
	public void renderOverlay1(Tessellator tessellator, Entity entity, Random random, ItemStack itemstack, int renderCount, float yaw, float brightness, float partialTick, CallbackInfo ci){
		if(ToolUpgrade.isItemUpgraded(itemstack)){
			SilkyUpgrades.itemModelSilky.renderFlat(tessellator, SilkyUpgrades.itemModelSilky.getIcon(entity, itemstack));
		}
	}

	@Inject(method = "renderItemIntoGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/model/ItemModelStandard;renderTexturedQuad(Lnet/minecraft/client/render/tessellator/Tessellator;IILnet/minecraft/client/render/stitcher/IconCoordinate;)V", shift = At.Shift.AFTER))
	public void renderOverlay2(Tessellator tessellator, FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemStack, int x, int y, float brightness, float alpha, CallbackInfo ci){
		if(ToolUpgrade.isItemUpgraded(itemStack) && !(itemStack.getItem() instanceof ItemBlock)){
			SilkyUpgrades.itemModelSilky.renderTexturedQuad(tessellator, x, y, SilkyUpgrades.itemModelSilky.getIcon(null, itemStack));
		}
	}

	@Redirect(method = "renderItemOverlayIntoGUI", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;getMaxDamage()I"))
	public int upgradeMaxDamage(ItemStack instance){
		if(ToolUpgrade.isItemUpgraded(instance)) return ToolUpgrade.getItemUpgrade(instance).getDurability();
		return instance.getMaxDamage();
	}

	@Redirect(method = "renderItemOverlayIntoGUI", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;getItemDamageForDisplay()I"))
	public int upgradeDamageDisplay(ItemStack instance){
		if(ToolUpgrade.isItemUpgraded(instance)) return SilkyUpgrades.getItemUpgradeDamage(instance);
		return instance.getItemDamageForDisplay();
	}

}
