package bigsir.silkyupgrades.mixin;

import bigsir.silkyupgrades.upgrades.ToolUpgrade;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolHoe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import bigsir.silkyupgrades.SilkyUpgrades;

@Mixin(value = ItemToolHoe.class, remap = false)
public abstract class ItemToolHoeMixin {
	@Redirect(method = "onBlockDestroyed", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/material/ToolMaterial;isSilkTouch()Z"))
	public boolean redirectSilkTouch(ToolMaterial instance, @Local(name = "itemstack")ItemStack stack){
		if(ToolUpgrade.gilded.hasThisUpgrade(stack)) return true;
		return instance.isSilkTouch();
	}
}
