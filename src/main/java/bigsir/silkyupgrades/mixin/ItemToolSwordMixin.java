package bigsir.silkyupgrades.mixin;

import bigsir.silkyupgrades.interfaces.IItemTool;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ItemToolSword.class, remap = false)
public abstract class ItemToolSwordMixin implements IItemTool {
	@Shadow
	private ToolMaterial material;

	@Override
	public ToolMaterial silky_upgrades$getMaterial() {
		return this.material;
	}
}
