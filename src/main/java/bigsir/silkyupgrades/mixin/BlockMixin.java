package bigsir.silkyupgrades.mixin;

import bigsir.silkyupgrades.upgrades.ToolUpgrade;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Block.class, remap = false)
public abstract class BlockMixin {

	@Redirect(method = "harvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/Item;isSilkTouch()Z"))
	private boolean silkTouch(Item instance, @Local(name="heldItemStack") ItemStack heldItemSTack){
		return instance.isSilkTouch() || ToolUpgrade.gilded.hasThisUpgrade(heldItemSTack);
	}
}
