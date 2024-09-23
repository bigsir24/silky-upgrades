package bigsir.silkyupgrades.mixin;

import bigsir.silkyupgrades.interfaces.IItemTool;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ItemTool.class, remap = false)
public abstract class ItemToolMixin implements IItemTool {
	@Shadow
	public abstract ToolMaterial getMaterial();

	@Override
	public ToolMaterial silky_upgrades$getMaterial() {
		return getMaterial();
	}
}
